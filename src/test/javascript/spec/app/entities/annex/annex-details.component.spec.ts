/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AnnexDetailComponent from '@/entities/annex/annex-details.vue';
import AnnexClass from '@/entities/annex/annex-details.component';
import AnnexService from '@/entities/annex/annex.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Annex Management Detail Component', () => {
    let wrapper: Wrapper<AnnexClass>;
    let comp: AnnexClass;
    let annexServiceStub: SinonStubbedInstance<AnnexService>;

    beforeEach(() => {
      annexServiceStub = sinon.createStubInstance<AnnexService>(AnnexService);

      wrapper = shallowMount<AnnexClass>(AnnexDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { annexService: () => annexServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAnnex = { id: 123 };
        annexServiceStub.find.resolves(foundAnnex);

        // WHEN
        comp.retrieveAnnex(123);
        await comp.$nextTick();

        // THEN
        expect(comp.annex).toBe(foundAnnex);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnnex = { id: 123 };
        annexServiceStub.find.resolves(foundAnnex);

        // WHEN
        comp.beforeRouteEnter({ params: { annexId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.annex).toBe(foundAnnex);
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
