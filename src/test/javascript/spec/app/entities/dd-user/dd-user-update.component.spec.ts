/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import DdUserUpdateComponent from '@/entities/dd-user/dd-user-update.vue';
import DdUserClass from '@/entities/dd-user/dd-user-update.component';
import DdUserService from '@/entities/dd-user/dd-user.service';

import ArticleService from '@/entities/article/article.service';

import ModulePermissionService from '@/entities/module-permission/module-permission.service';

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
  describe('DdUser Management Update Component', () => {
    let wrapper: Wrapper<DdUserClass>;
    let comp: DdUserClass;
    let ddUserServiceStub: SinonStubbedInstance<DdUserService>;

    beforeEach(() => {
      ddUserServiceStub = sinon.createStubInstance<DdUserService>(DdUserService);

      wrapper = shallowMount<DdUserClass>(DdUserUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          ddUserService: () => ddUserServiceStub,

          articleService: () => new ArticleService(),

          modulePermissionService: () => new ModulePermissionService(),

          subTypeService: () => new SubTypeService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.ddUser = entity;
        ddUserServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ddUserServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.ddUser = entity;
        ddUserServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ddUserServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDdUser = { id: 123 };
        ddUserServiceStub.find.resolves(foundDdUser);
        ddUserServiceStub.retrieve.resolves([foundDdUser]);

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
