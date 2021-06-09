/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import SubTypeUpdateComponent from '@/entities/sub-type/sub-type-update.vue';
import SubTypeClass from '@/entities/sub-type/sub-type-update.component';
import SubTypeService from '@/entities/sub-type/sub-type.service';

import ArticleService from '@/entities/article/article.service';

import TypeService from '@/entities/type/type.service';

import DdUserService from '@/entities/dd-user/dd-user.service';

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
  describe('SubType Management Update Component', () => {
    let wrapper: Wrapper<SubTypeClass>;
    let comp: SubTypeClass;
    let subTypeServiceStub: SinonStubbedInstance<SubTypeService>;

    beforeEach(() => {
      subTypeServiceStub = sinon.createStubInstance<SubTypeService>(SubTypeService);

      wrapper = shallowMount<SubTypeClass>(SubTypeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          subTypeService: () => subTypeServiceStub,

          articleService: () => new ArticleService(),

          typeService: () => new TypeService(),

          ddUserService: () => new DdUserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.subType = entity;
        subTypeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(subTypeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.subType = entity;
        subTypeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(subTypeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSubType = { id: 123 };
        subTypeServiceStub.find.resolves(foundSubType);
        subTypeServiceStub.retrieve.resolves([foundSubType]);

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
