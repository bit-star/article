/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DdUserDetailComponent from '@/entities/dd-user/dd-user-details.vue';
import DdUserClass from '@/entities/dd-user/dd-user-details.component';
import DdUserService from '@/entities/dd-user/dd-user.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DdUser Management Detail Component', () => {
    let wrapper: Wrapper<DdUserClass>;
    let comp: DdUserClass;
    let ddUserServiceStub: SinonStubbedInstance<DdUserService>;

    beforeEach(() => {
      ddUserServiceStub = sinon.createStubInstance<DdUserService>(DdUserService);

      wrapper = shallowMount<DdUserClass>(DdUserDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { ddUserService: () => ddUserServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDdUser = { id: 123 };
        ddUserServiceStub.find.resolves(foundDdUser);

        // WHEN
        comp.retrieveDdUser(123);
        await comp.$nextTick();

        // THEN
        expect(comp.ddUser).toBe(foundDdUser);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDdUser = { id: 123 };
        ddUserServiceStub.find.resolves(foundDdUser);

        // WHEN
        comp.beforeRouteEnter({ params: { ddUserId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.ddUser).toBe(foundDdUser);
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
