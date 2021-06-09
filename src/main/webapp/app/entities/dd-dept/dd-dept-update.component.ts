import { Component, Vue, Inject } from 'vue-property-decorator';

import ArticleService from '@/entities/article/article.service';
import { IArticle } from '@/shared/model/article.model';

import { IDdDept, DdDept } from '@/shared/model/dd-dept.model';
import DdDeptService from './dd-dept.service';

const validations: any = {
  ddDept: {
    name: {},
    parentId: {},
  },
};

@Component({
  validations,
})
export default class DdDeptUpdate extends Vue {
  @Inject('ddDeptService') private ddDeptService: () => DdDeptService;
  public ddDept: IDdDept = new DdDept();

  @Inject('articleService') private articleService: () => ArticleService;

  public articles: IArticle[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ddDeptId) {
        vm.retrieveDdDept(to.params.ddDeptId);
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
    if (this.ddDept.id) {
      this.ddDeptService()
        .update(this.ddDept)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.ddDept.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.ddDeptService()
        .create(this.ddDept)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.ddDept.created', { param: param.id });
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

  public retrieveDdDept(ddDeptId): void {
    this.ddDeptService()
      .find(ddDeptId)
      .then(res => {
        this.ddDept = res;
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
  }
}
