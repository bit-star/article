/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DdDeptDetailComponent from '@/entities/dd-dept/dd-dept-details.vue';
import DdDeptClass from '@/entities/dd-dept/dd-dept-details.component';
import DdDeptService from '@/entities/dd-dept/dd-dept.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DdDept Management Detail Component', () => {
    let wrapper: Wrapper<DdDeptClass>;
    let comp: DdDeptClass;
    let ddDeptServiceStub: SinonStubbedInstance<DdDeptService>;

    beforeEach(() => {
      ddDeptServiceStub = sinon.createStubInstance<DdDeptService>(DdDeptService);

      wrapper = shallowMount<DdDeptClass>(DdDeptDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { ddDeptService: () => ddDeptServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDdDept = { id: 123 };
        ddDeptServiceStub.find.resolves(foundDdDept);

        // WHEN
        comp.retrieveDdDept(123);
        await comp.$nextTick();

        // THEN
        expect(comp.ddDept).toBe(foundDdDept);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDdDept = { id: 123 };
        ddDeptServiceStub.find.resolves(foundDdDept);

        // WHEN
        comp.beforeRouteEnter({ params: { ddDeptId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.ddDept).toBe(foundDdDept);
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
