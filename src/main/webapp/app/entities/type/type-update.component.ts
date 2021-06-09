import { Component, Vue, Inject } from 'vue-property-decorator';

import SubTypeService from '@/entities/sub-type/sub-type.service';
import { ISubType } from '@/shared/model/sub-type.model';

import { IType, Type } from '@/shared/model/type.model';
import TypeService from './type.service';

const validations: any = {
  type: {
    name: {},
  },
};

@Component({
  validations,
})
export default class TypeUpdate extends Vue {
  @Inject('typeService') private typeService: () => TypeService;
  public type: IType = new Type();

  @Inject('subTypeService') private subTypeService: () => SubTypeService;

  public subTypes: ISubType[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.typeId) {
        vm.retrieveType(to.params.typeId);
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
    if (this.type.id) {
      this.typeService()
        .update(this.type)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.type.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.typeService()
        .create(this.type)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.type.created', { param: param.id });
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

  public retrieveType(typeId): void {
    this.typeService()
      .find(typeId)
      .then(res => {
        this.type = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.subTypeService()
      .retrieve()
      .then(res => {
        this.subTypes = res.data;
      });
  }
}
