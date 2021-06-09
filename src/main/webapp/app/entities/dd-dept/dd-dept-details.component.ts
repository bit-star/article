import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDdDept } from '@/shared/model/dd-dept.model';
import DdDeptService from './dd-dept.service';

@Component
export default class DdDeptDetails extends Vue {
  @Inject('ddDeptService') private ddDeptService: () => DdDeptService;
  public ddDept: IDdDept = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ddDeptId) {
        vm.retrieveDdDept(to.params.ddDeptId);
      }
    });
  }

  public retrieveDdDept(ddDeptId) {
    this.ddDeptService()
      .find(ddDeptId)
      .then(res => {
        this.ddDept = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
