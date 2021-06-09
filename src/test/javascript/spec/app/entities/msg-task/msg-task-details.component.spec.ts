/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MsgTaskDetailComponent from '@/entities/msg-task/msg-task-details.vue';
import MsgTaskClass from '@/entities/msg-task/msg-task-details.component';
import MsgTaskService from '@/entities/msg-task/msg-task.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MsgTask Management Detail Component', () => {
    let wrapper: Wrapper<MsgTaskClass>;
    let comp: MsgTaskClass;
    let msgTaskServiceStub: SinonStubbedInstance<MsgTaskService>;

    beforeEach(() => {
      msgTaskServiceStub = sinon.createStubInstance<MsgTaskService>(MsgTaskService);

      wrapper = shallowMount<MsgTaskClass>(MsgTaskDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { msgTaskService: () => msgTaskServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMsgTask = { id: 123 };
        msgTaskServiceStub.find.resolves(foundMsgTask);

        // WHEN
        comp.retrieveMsgTask(123);
        await comp.$nextTick();

        // THEN
        expect(comp.msgTask).toBe(foundMsgTask);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMsgTask = { id: 123 };
        msgTaskServiceStub.find.resolves(foundMsgTask);

        // WHEN
        comp.beforeRouteEnter({ params: { msgTaskId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.msgTask).toBe(foundMsgTask);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
