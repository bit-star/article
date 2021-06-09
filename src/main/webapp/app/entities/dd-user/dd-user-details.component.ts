import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IDdUser } from '@/shared/model/dd-user.model';
import DdUserService from './dd-user.service';

@Component
export default class DdUserDetails extends mixins(JhiDataUtils) {
  @Inject('ddUserService') private ddUserService: () => DdUserService;
  public ddUser: IDdUser = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ddUserId) {
        vm.retrieveDdUser(to.params.ddUserId);
      }
    });
  }

  public retrieveDdUser(ddUserId) {
    this.ddUserService()
      .find(ddUserId)
      .then(res => {
        this.ddUser = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
