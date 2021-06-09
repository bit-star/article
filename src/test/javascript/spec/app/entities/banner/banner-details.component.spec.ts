/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import BannerDetailComponent from '@/entities/banner/banner-details.vue';
import BannerClass from '@/entities/banner/banner-details.component';
import BannerService from '@/entities/banner/banner.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Banner Management Detail Component', () => {
    let wrapper: Wrapper<BannerClass>;
    let comp: BannerClass;
    let bannerServiceStub: SinonStubbedInstance<BannerService>;

    beforeEach(() => {
      bannerServiceStub = sinon.createStubInstance<BannerService>(BannerService);

      wrapper = shallowMount<BannerClass>(BannerDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { bannerService: () => bannerServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBanner = { id: 123 };
        bannerServiceStub.find.resolves(foundBanner);

        // WHEN
        comp.retrieveBanner(123);
        await comp.$nextTick();

        // THEN
        expect(comp.banner).toBe(foundBanner);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBanner = { id: 123 };
        bannerServiceStub.find.resolves(foundBanner);

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
