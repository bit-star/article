import { Component, Vue, Inject } from 'vue-property-decorator';

import { IArticle } from '@/shared/model/article.model';
import ArticleService from './article.service';

@Component
export default class ArticleDetails extends Vue {
  @Inject('articleService') private articleService: () => ArticleService;
  public article: IArticle = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.articleId) {
        vm.retrieveArticle(to.params.articleId);
      }
    });
  }

  public retrieveArticle(articleId) {
    this.articleService()
      .find(articleId)
      .then(res => {
        this.article = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
