/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import TypeUpdateComponent from '@/entities/type/type-update.vue';
import TypeClass from '@/entities/type/type-update.component';
import TypeService from '@/entities/type/type.service';

import SubTypeService from '@/entities/sub-type/sub-type.service';

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
  describe('Type Management Update Component', () => {
    let wrapper: Wrapper<TypeClass>;
    let comp: TypeClass;
    let typeServiceStub: SinonStubbedInstance<TypeService>;

    beforeEach(() => {
      typeServiceStub = sinon.createStubInstance<TypeService>(TypeService);

      wrapper = shallowMount<TypeClass>(TypeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          typeService: () => typeServiceStub,

          subTypeService: () => new SubTypeService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.type = entity;
        typeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.type = entity;
        typeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundType = { id: 123 };
        typeServiceStub.find.resolves(foundType);
        typeServiceStub.retrieve.resolves([foundType]);

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
