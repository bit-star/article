/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TypeDetailComponent from '@/entities/type/type-details.vue';
import TypeClass from '@/entities/type/type-details.component';
import TypeService from '@/entities/type/type.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Type Management Detail Component', () => {
    let wrapper: Wrapper<TypeClass>;
    let comp: TypeClass;
    let typeServiceStub: SinonStubbedInstance<TypeService>;

    beforeEach(() => {
      typeServiceStub = sinon.createStubInstance<TypeService>(TypeService);

      wrapper = shallowMount<TypeClass>(TypeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { typeService: () => typeServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundType = { id: 123 };
        typeServiceStub.find.resolves(foundType);

        // WHEN
        comp.retrieveType(123);
        await comp.$nextTick();

        // THEN
        expect(comp.type).toBe(foundType);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundType = { id: 123 };
        typeServiceStub.find.resolves(foundType);

        // WHEN
        comp.beforeRouteEnter({ params: { typeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.type).toBe(foundType);
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
