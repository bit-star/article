import { Component, Vue, Inject } from 'vue-property-decorator';

import BannerService from '@/entities/banner/banner.service';
import { IBanner } from '@/shared/model/banner.model';

import { IPhoto, Photo } from '@/shared/model/photo.model';
import PhotoService from './photo.service';

const validations: any = {
  photo: {
    url: {},
  },
};

@Component({
  validations,
})
export default class PhotoUpdate extends Vue {
  @Inject('photoService') private photoService: () => PhotoService;
  public photo: IPhoto = new Photo();

  @Inject('bannerService') private bannerService: () => BannerService;

  public banners: IBanner[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.photoId) {
        vm.retrievePhoto(to.params.photoId);
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
    if (this.photo.id) {
      this.photoService()
        .update(this.photo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.photo.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.photoService()
        .create(this.photo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.photo.created', { param: param.id });
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

  public retrievePhoto(photoId): void {
    this.photoService()
      .find(photoId)
      .then(res => {
        this.photo = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.bannerService()
      .retrieve()
      .then(res => {
        this.banners = res.data;
      });
  }
}
