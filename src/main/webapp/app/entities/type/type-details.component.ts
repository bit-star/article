import { Component, Vue, Inject } from 'vue-property-decorator';

import { IType } from '@/shared/model/type.model';
import TypeService from './type.service';

@Component
export default class TypeDetails extends Vue {
  @Inject('typeService') private typeService: () => TypeService;
  public type: IType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.typeId) {
        vm.retrieveType(to.params.typeId);
      }
    });
  }

  public retrieveType(typeId) {
    this.typeService()
      .find(typeId)
      .then(res => {
        this.type = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
