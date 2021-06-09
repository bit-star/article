import { Component, Vue, Inject } from 'vue-property-decorator';

import MsgService from '@/entities/msg/msg.service';
import { IMsg } from '@/shared/model/msg.model';

import { IMsgTask, MsgTask } from '@/shared/model/msg-task.model';
import MsgTaskService from './msg-task.service';

const validations: any = {
  msgTask: {
    useridList: {},
    taskId: {},
    rspMsg: {},
    status: {},
    progressInPercent: {},
    invalidUserIdList: {},
    forbiddenUserIdList: {},
    failedUserIdList: {},
    readUserIdList: {},
    unreadUserIdList: {},
    invalidDeptIdList: {},
    withdraw: {},
  },
};

@Component({
  validations,
})
export default class MsgTaskUpdate extends Vue {
  @Inject('msgTaskService') private msgTaskService: () => MsgTaskService;
  public msgTask: IMsgTask = new MsgTask();

  @Inject('msgService') private msgService: () => MsgService;

  public msgs: IMsg[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.msgTaskId) {
        vm.retrieveMsgTask(to.params.msgTaskId);
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
    if (this.msgTask.id) {
      this.msgTaskService()
        .update(this.msgTask)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.msgTask.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.msgTaskService()
        .create(this.msgTask)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.msgTask.created', { param: param.id });
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

  public retrieveMsgTask(msgTaskId): void {
    this.msgTaskService()
      .find(msgTaskId)
      .then(res => {
        this.msgTask = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.msgService()
      .retrieve()
      .then(res => {
        this.msgs = res.data;
      });
  }
}
