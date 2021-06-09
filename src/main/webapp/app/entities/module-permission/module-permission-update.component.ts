import { Component, Vue, Inject } from 'vue-property-decorator';

import DdUserService from '@/entities/dd-user/dd-user.service';
import { IDdUser } from '@/shared/model/dd-user.model';

import { IModulePermission, ModulePermission } from '@/shared/model/module-permission.model';
import ModulePermissionService from './module-permission.service';

const validations: any = {
  modulePermission: {
    name: {},
    enable: {},
  },
};

@Component({
  validations,
})
export default class ModulePermissionUpdate extends Vue {
  @Inject('modulePermissionService') private modulePermissionService: () => ModulePermissionService;
  public modulePermission: IModulePermission = new ModulePermission();

  @Inject('ddUserService') private ddUserService: () => DdUserService;

  public ddUsers: IDdUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.modulePermissionId) {
        vm.retrieveModulePermission(to.params.modulePermissionId);
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
    if (this.modulePermission.id) {
      this.modulePermissionService()
        .update(this.modulePermission)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.modulePermission.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.modulePermissionService()
        .create(this.modulePermission)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.modulePermission.created', { param: param.id });
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

  public retrieveModulePermission(modulePermissionId): void {
    this.modulePermissionService()
      .find(modulePermissionId)
      .then(res => {
        this.modulePermission = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.ddUserService()
      .retrieve()
      .then(res => {
        this.ddUsers = res.data;
      });
  }
}
