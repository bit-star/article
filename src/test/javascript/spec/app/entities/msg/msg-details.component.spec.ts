/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MsgDetailComponent from '@/entities/msg/msg-details.vue';
import MsgClass from '@/entities/msg/msg-details.component';
import MsgService from '@/entities/msg/msg.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Msg Management Detail Component', () => {
    let wrapper: Wrapper<MsgClass>;
    let comp: MsgClass;
    let msgServiceStub: SinonStubbedInstance<MsgService>;

    beforeEach(() => {
      msgServiceStub = sinon.createStubInstance<MsgService>(MsgService);

      wrapper = shallowMount<MsgClass>(MsgDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { msgService: () => msgServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMsg = { id: 123 };
        msgServiceStub.find.resolves(foundMsg);

        // WHEN
        comp.retrieveMsg(123);
        await comp.$nextTick();

        // THEN
        expect(comp.msg).toBe(foundMsg);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMsg = { id: 123 };
        msgServiceStub.find.resolves(foundMsg);

        // WHEN
        comp.beforeRouteEnter({ params: { msgId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.msg).toBe(foundMsg);
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
