/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ModulePermissionDetailComponent from '@/entities/module-permission/module-permission-details.vue';
import ModulePermissionClass from '@/entities/module-permission/module-permission-details.component';
import ModulePermissionService from '@/entities/module-permission/module-permission.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ModulePermission Management Detail Component', () => {
    let wrapper: Wrapper<ModulePermissionClass>;
    let comp: ModulePermissionClass;
    let modulePermissionServiceStub: SinonStubbedInstance<ModulePermissionService>;

    beforeEach(() => {
      modulePermissionServiceStub = sinon.createStubInstance<ModulePermissionService>(ModulePermissionService);

      wrapper = shallowMount<ModulePermissionClass>(ModulePermissionDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { modulePermissionService: () => modulePermissionServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundModulePermission = { id: 123 };
        modulePermissionServiceStub.find.resolves(foundModulePermission);

        // WHEN
        comp.retrieveModulePermission(123);
        await comp.$nextTick();

        // THEN
        expect(comp.modulePermission).toBe(foundModulePermission);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundModulePermission = { id: 123 };
        modulePermissionServiceStub.find.resolves(foundModulePermission);

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
