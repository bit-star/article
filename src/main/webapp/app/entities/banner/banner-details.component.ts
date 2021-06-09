import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBanner } from '@/shared/model/banner.model';
import BannerService from './banner.service';

@Component
export default class BannerDetails extends Vue {
  @Inject('bannerService') private bannerService: () => BannerService;
  public banner: IBanner = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.bannerId) {
        vm.retrieveBanner(to.params.bannerId);
      }
    });
  }

  public retrieveBanner(bannerId) {
    this.bannerService()
      .find(bannerId)
      .then(res => {
        this.banner = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
