import { Component, Vue, Inject } from 'vue-property-decorator';

import PhotoService from '@/entities/photo/photo.service';
import { IPhoto } from '@/shared/model/photo.model';

import { IBanner, Banner } from '@/shared/model/banner.model';
import BannerService from './banner.service';

const validations: any = {
  banner: {
    title: {},
    coverUrl: {},
    name: {},
    brand: {},
    model: {},
    specifications: {},
    isExport: {},
    supplier: {},
    supplierAddress: {},
    supplierCapacity: {},
  },
};

@Component({
  validations,
})
export default class BannerUpdate extends Vue {
  @Inject('bannerService') private bannerService: () => BannerService;
  public banner: IBanner = new Banner();

  @Inject('photoService') private photoService: () => PhotoService;

  public photos: IPhoto[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.bannerId) {
        vm.retrieveBanner(to.params.bannerId);
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
    if (this.banner.id) {
      this.bannerService()
        .update(this.banner)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.banner.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.bannerService()
        .create(this.banner)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.banner.created', { param: param.id });
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

  public retrieveBanner(bannerId): void {
    this.bannerService()
      .find(bannerId)
      .then(res => {
        this.banner = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.photoService()
      .retrieve()
      .then(res => {
        this.photos = res.data;
      });
  }
}
