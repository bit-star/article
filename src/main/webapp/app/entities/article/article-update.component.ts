import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AnnexService from '@/entities/annex/annex.service';
import { IAnnex } from '@/shared/model/annex.model';

import SubTypeService from '@/entities/sub-type/sub-type.service';
import { ISubType } from '@/shared/model/sub-type.model';

import DdUserService from '@/entities/dd-user/dd-user.service';
import { IDdUser } from '@/shared/model/dd-user.model';

import DdDeptService from '@/entities/dd-dept/dd-dept.service';
import { IDdDept } from '@/shared/model/dd-dept.model';

import { IArticle, Article } from '@/shared/model/article.model';
import ArticleService from './article.service';

const validations: any = {
  article: {
    title: {},
    time: {},
    text: {},
    contributors: {},
  },
};

@Component({
  validations,
})
export default class ArticleUpdate extends Vue {
  @Inject('articleService') private articleService: () => ArticleService;
  public article: IArticle = new Article();

  @Inject('annexService') private annexService: () => AnnexService;

  public annexes: IAnnex[] = [];

  @Inject('subTypeService') private subTypeService: () => SubTypeService;

  public subTypes: ISubType[] = [];

  @Inject('ddUserService') private ddUserService: () => DdUserService;

  public ddUsers: IDdUser[] = [];

  @Inject('ddDeptService') private ddDeptService: () => DdDeptService;

  public ddDepts: IDdDept[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.articleId) {
        vm.retrieveArticle(to.params.articleId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.article.id) {
      this.articleService()
        .update(this.article)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.article.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.articleService()
        .create(this.article)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.article.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.article[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.article[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.article[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.article[field] = null;
    }
  }

  public retrieveArticle(articleId): void {
    this.articleService()
      .find(articleId)
      .then(res => {
        res.time = new Date(res.time);
        this.article = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.annexService()
      .retrieve()
      .then(res => {
        this.annexes = res.data;
      });
    this.subTypeService()
      .retrieve()
      .then(res => {
        this.subTypes = res.data;
      });
    this.ddUserService()
      .retrieve()
      .then(res => {
        this.ddUsers = res.data;
      });
    this.ddDeptService()
      .retrieve()
      .then(res => {
        this.ddDepts = res.data;
      });
  }
}
