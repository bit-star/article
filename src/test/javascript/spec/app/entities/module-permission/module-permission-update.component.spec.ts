/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ModulePermissionUpdateComponent from '@/entities/module-permission/module-permission-update.vue';
import ModulePermissionClass from '@/entities/module-permission/module-permission-update.component';
import ModulePermissionService from '@/entities/module-permission/module-permission.service';

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
  describe('ModulePermission Management Update Component', () => {
    let wrapper: Wrapper<ModulePermissionClass>;
    let comp: ModulePermissionClass;
    let modulePermissionServiceStub: SinonStubbedInstance<ModulePermissionService>;

    beforeEach(() => {
      modulePermissionServiceStub = sinon.createStubInstance<ModulePermissionService>(ModulePermissionService);

      wrapper = shallowMount<ModulePermissionClass>(ModulePermissionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          modulePermissionService: () => modulePermissionServiceStub,

          ddUserService: () => new DdUserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.modulePermission = entity;
        modulePermissionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(modulePermissionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.modulePermission = entity;
        modulePermissionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(modulePermissionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundModulePermission = { id: 123 };
        modulePermissionServiceStub.find.resolves(foundModulePermission);
        modulePermissionServiceStub.retrieve.resolves([foundModulePermission]);

        // WHEN
        comp.beforeRouteEnter({ params: { modulePermissionId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.modulePermission).toBe(foundModulePermission);
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
