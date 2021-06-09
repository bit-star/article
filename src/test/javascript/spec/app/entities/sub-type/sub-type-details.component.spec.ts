/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SubTypeDetailComponent from '@/entities/sub-type/sub-type-details.vue';
import SubTypeClass from '@/entities/sub-type/sub-type-details.component';
import SubTypeService from '@/entities/sub-type/sub-type.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('SubType Management Detail Component', () => {
    let wrapper: Wrapper<SubTypeClass>;
    let comp: SubTypeClass;
    let subTypeServiceStub: SinonStubbedInstance<SubTypeService>;

    beforeEach(() => {
      subTypeServiceStub = sinon.createStubInstance<SubTypeService>(SubTypeService);

      wrapper = shallowMount<SubTypeClass>(SubTypeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { subTypeService: () => subTypeServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSubType = { id: 123 };
        subTypeServiceStub.find.resolves(foundSubType);

        // WHEN
        comp.retrieveSubType(123);
        await comp.$nextTick();

        // THEN
        expect(comp.subType).toBe(foundSubType);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSubType = { id: 123 };
        subTypeServiceStub.find.resolves(foundSubType);

        // WHEN
        comp.beforeRouteEnter({ params: { subTypeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.subType).toBe(foundSubType);
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
