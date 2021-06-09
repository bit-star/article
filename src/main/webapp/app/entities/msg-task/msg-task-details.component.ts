import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMsgTask } from '@/shared/model/msg-task.model';
import MsgTaskService from './msg-task.service';

@Component
export default class MsgTaskDetails extends Vue {
  @Inject('msgTaskService') private msgTaskService: () => MsgTaskService;
  public msgTask: IMsgTask = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.msgTaskId) {
        vm.retrieveMsgTask(to.params.msgTaskId);
      }
    });
  }

  public retrieveMsgTask(msgTaskId) {
    this.msgTaskService()
      .find(msgTaskId)
      .then(res => {
        this.msgTask = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
