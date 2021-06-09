/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import DdDeptUpdateComponent from '@/entities/dd-dept/dd-dept-update.vue';
import DdDeptClass from '@/entities/dd-dept/dd-dept-update.component';
import DdDeptService from '@/entities/dd-dept/dd-dept.service';

import ArticleService from '@/entities/article/article.service';

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
  describe('DdDept Management Update Component', () => {
    let wrapper: Wrapper<DdDeptClass>;
    let comp: DdDeptClass;
    let ddDeptServiceStub: SinonStubbedInstance<DdDeptService>;

    beforeEach(() => {
      ddDeptServiceStub = sinon.createStubInstance<DdDeptService>(DdDeptService);

      wrapper = shallowMount<DdDeptClass>(DdDeptUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          ddDeptService: () => ddDeptServiceStub,

          articleService: () => new ArticleService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.ddDept = entity;
        ddDeptServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ddDeptServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.ddDept = entity;
        ddDeptServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ddDeptServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDdDept = { id: 123 };
        ddDeptServiceStub.find.resolves(foundDdDept);
        ddDeptServiceStub.retrieve.resolves([foundDdDept]);

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
