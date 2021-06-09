import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISubType } from '@/shared/model/sub-type.model';
import SubTypeService from './sub-type.service';

@Component
export default class SubTypeDetails extends Vue {
  @Inject('subTypeService') private subTypeService: () => SubTypeService;
  public subType: ISubType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.subTypeId) {
        vm.retrieveSubType(to.params.subTypeId);
      }
    });
  }

  public retrieveSubType(subTypeId) {
    this.subTypeService()
      .find(subTypeId)
      .then(res => {
        this.subType = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
