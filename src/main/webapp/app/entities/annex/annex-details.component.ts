import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAnnex } from '@/shared/model/annex.model';
import AnnexService from './annex.service';

@Component
export default class AnnexDetails extends Vue {
  @Inject('annexService') private annexService: () => AnnexService;
  public annex: IAnnex = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.annexId) {
        vm.retrieveAnnex(to.params.annexId);
      }
    });
  }

  public retrieveAnnex(annexId) {
    this.annexService()
      .find(annexId)
      .then(res => {
        this.annex = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
