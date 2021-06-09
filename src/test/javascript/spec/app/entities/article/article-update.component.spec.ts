/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import ArticleUpdateComponent from '@/entities/article/article-update.vue';
import ArticleClass from '@/entities/article/article-update.component';
import ArticleService from '@/entities/article/article.service';

import AnnexService from '@/entities/annex/annex.service';

import SubTypeService from '@/entities/sub-type/sub-type.service';

import DdUserService from '@/entities/dd-user/dd-user.service';

import DdDeptService from '@/entities/dd-dept/dd-dept.service';

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
  describe('Article Management Update Component', () => {
    let wrapper: Wrapper<ArticleClass>;
    let comp: ArticleClass;
    let articleServiceStub: SinonStubbedInstance<ArticleService>;

    beforeEach(() => {
      articleServiceStub = sinon.createStubInstance<ArticleService>(ArticleService);

      wrapper = shallowMount<ArticleClass>(ArticleUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          articleService: () => articleServiceStub,

          annexService: () => new AnnexService(),

          subTypeService: () => new SubTypeService(),

          ddUserService: () => new DdUserService(),

          ddDeptService: () => new DdDeptService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.article = entity;
        articleServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(articleServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.article = entity;
        articleServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(articleServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundArticle = { id: 123 };
        articleServiceStub.find.resolves(foundArticle);
        articleServiceStub.retrieve.resolves([foundArticle]);

        // WHEN
        comp.beforeRouteEnter({ params: { articleId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.article).toBe(foundArticle);
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
