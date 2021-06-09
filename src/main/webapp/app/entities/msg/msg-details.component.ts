import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMsg } from '@/shared/model/msg.model';
import MsgService from './msg.service';

@Component
export default class MsgDetails extends Vue {
  @Inject('msgService') private msgService: () => MsgService;
  public msg: IMsg = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.msgId) {
        vm.retrieveMsg(to.params.msgId);
      }
    });
  }

  public retrieveMsg(msgId) {
    this.msgService()
      .find(msgId)
      .then(res => {
        this.msg = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
