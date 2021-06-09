/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import AnnexUpdateComponent from '@/entities/annex/annex-update.vue';
import AnnexClass from '@/entities/annex/annex-update.component';
import AnnexService from '@/entities/annex/annex.service';

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
  describe('Annex Management Update Component', () => {
    let wrapper: Wrapper<AnnexClass>;
    let comp: AnnexClass;
    let annexServiceStub: SinonStubbedInstance<AnnexService>;

    beforeEach(() => {
      annexServiceStub = sinon.createStubInstance<AnnexService>(AnnexService);

      wrapper = shallowMount<AnnexClass>(AnnexUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          annexService: () => annexServiceStub,

          articleService: () => new ArticleService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.annex = entity;
        annexServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(annexServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.annex = entity;
        annexServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(annexServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnnex = { id: 123 };
        annexServiceStub.find.resolves(foundAnnex);
        annexServiceStub.retrieve.resolves([foundAnnex]);

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
