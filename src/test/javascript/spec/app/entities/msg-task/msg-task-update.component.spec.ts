/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import MsgTaskUpdateComponent from '@/entities/msg-task/msg-task-update.vue';
import MsgTaskClass from '@/entities/msg-task/msg-task-update.component';
import MsgTaskService from '@/entities/msg-task/msg-task.service';

import MsgService from '@/entities/msg/msg.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('MsgTask Management Update Component', () => {
    let wrapper: Wrapper<MsgTaskClass>;
    let comp: MsgTaskClass;
    let msgTaskServiceStub: SinonStubbedInstance<MsgTaskService>;

    beforeEach(() => {
      msgTaskServiceStub = sinon.createStubInstance<MsgTaskService>(MsgTaskService);

      wrapper = shallowMount<MsgTaskClass>(MsgTaskUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          msgTaskService: () => msgTaskServiceStub,

          msgService: () => new MsgService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.msgTask = entity;
        msgTaskServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(msgTaskServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.msgTask = entity;
        msgTaskServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(msgTaskServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMsgTask = { id: 123 };
        msgTaskServiceStub.find.resolves(foundMsgTask);
        msgTaskServiceStub.retrieve.resolves([foundMsgTask]);

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
