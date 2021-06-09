import { Component, Vue, Inject } from 'vue-property-decorator';

import { IModulePermission } from '@/shared/model/module-permission.model';
import ModulePermissionService from './module-permission.service';

@Component
export default class ModulePermissionDetails extends Vue {
  @Inject('modulePermissionService') private modulePermissionService: () => ModulePermissionService;
  public modulePermission: IModulePermission = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.modulePermissionId) {
        vm.retrieveModulePermission(to.params.modulePermissionId);
      }
    });
  }

  public retrieveModulePermission(modulePermissionId) {
    this.modulePermissionService()
      .find(modulePermissionId)
      .then(res => {
        this.modulePermission = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
