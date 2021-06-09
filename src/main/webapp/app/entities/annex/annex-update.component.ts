import { Component, Vue, Inject } from 'vue-property-decorator';

import ArticleService from '@/entities/article/article.service';
import { IArticle } from '@/shared/model/article.model';

import { IAnnex, Annex } from '@/shared/model/annex.model';
import AnnexService from './annex.service';

const validations: any = {
  annex: {
    spaceId: {},
    fileId: {},
    fileName: {},
    fileSize: {},
    fileType: {},
    storageMode: {},
    url: {},
  },
};

@Component({
  validations,
})
export default class AnnexUpdate extends Vue {
  @Inject('annexService') private annexService: () => AnnexService;
  public annex: IAnnex = new Annex();

  @Inject('articleService') private articleService: () => ArticleService;

  public articles: IArticle[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.annexId) {
        vm.retrieveAnnex(to.params.annexId);
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
    if (this.annex.id) {
      this.annexService()
        .update(this.annex)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.annex.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.annexService()
        .create(this.annex)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.annex.created', { param: param.id });
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

  public retrieveAnnex(annexId): void {
    this.annexService()
      .find(annexId)
      .then(res => {
        this.annex = res;
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
