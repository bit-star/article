/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import BannerUpdateComponent from '@/entities/banner/banner-update.vue';
import BannerClass from '@/entities/banner/banner-update.component';
import BannerService from '@/entities/banner/banner.service';

import PhotoService from '@/entities/photo/photo.service';

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
  describe('Banner Management Update Component', () => {
    let wrapper: Wrapper<BannerClass>;
    let comp: BannerClass;
    let bannerServiceStub: SinonStubbedInstance<BannerService>;

    beforeEach(() => {
      bannerServiceStub = sinon.createStubInstance<BannerService>(BannerService);

      wrapper = shallowMount<BannerClass>(BannerUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          bannerService: () => bannerServiceStub,

          photoService: () => new PhotoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.banner = entity;
        bannerServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(bannerServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.banner = entity;
        bannerServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(bannerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBanner = { id: 123 };
        bannerServiceStub.find.resolves(foundBanner);
        bannerServiceStub.retrieve.resolves([foundBanner]);

        // WHEN
        comp.beforeRouteEnter({ params: { bannerId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.banner).toBe(foundBanner);
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
