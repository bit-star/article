import { Component, Vue, Inject } from 'vue-property-decorator';

import ArticleService from '@/entities/article/article.service';
import { IArticle } from '@/shared/model/article.model';

import TypeService from '@/entities/type/type.service';
import { IType } from '@/shared/model/type.model';

import DdUserService from '@/entities/dd-user/dd-user.service';
import { IDdUser } from '@/shared/model/dd-user.model';

import { ISubType, SubType } from '@/shared/model/sub-type.model';
import SubTypeService from './sub-type.service';

const validations: any = {
  subType: {
    name: {},
  },
};

@Component({
  validations,
})
export default class SubTypeUpdate extends Vue {
  @Inject('subTypeService') private subTypeService: () => SubTypeService;
  public subType: ISubType = new SubType();

  @Inject('articleService') private articleService: () => ArticleService;

  public articles: IArticle[] = [];

  @Inject('typeService') private typeService: () => TypeService;

  public types: IType[] = [];

  @Inject('ddUserService') private ddUserService: () => DdUserService;

  public ddUsers: IDdUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.subTypeId) {
        vm.retrieveSubType(to.params.subTypeId);
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
    if (this.subType.id) {
      this.subTypeService()
        .update(this.subType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.subType.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.subTypeService()
        .create(this.subType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.subType.created', { param: param.id });
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

  public retrieveSubType(subTypeId): void {
    this.subTypeService()
      .find(subTypeId)
      .then(res => {
        this.subType = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.articleService()
      .retrieve()
      .then(res => {
        this.articles = res.data;
      });
    this.typeService()
      .retrieve()
      .then(res => {
        this.types = res.data;
      });
    this.ddUserService()
      .retrieve()
      .then(res => {
        this.ddUsers = res.data;
      });
  }
}
