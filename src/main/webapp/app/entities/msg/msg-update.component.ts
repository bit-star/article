import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import MsgTaskService from '@/entities/msg-task/msg-task.service';
import { IMsgTask } from '@/shared/model/msg-task.model';

import { IMsg, Msg } from '@/shared/model/msg.model';
import MsgService from './msg.service';

const validations: any = {
  msg: {
    deptIdList: {},
    useridList: {},
    toAllUser: {},
    progressInPercent: {},
    title: {},
    singleTitle: {},
    singleUrl: {},
    coverUrl: {},
    numberOfShards: {},
    completeSharding: {},
    msg: {},
    executeTime: {},
    agentId: {},
    type: {},
    status: {},
  },
};

@Component({
  validations,
})
export default class MsgUpdate extends Vue {
  @Inject('msgService') private msgService: () => MsgService;
  public msg: IMsg = new Msg();

  @Inject('msgTaskService') private msgTaskService: () => MsgTaskService;

  public msgTasks: IMsgTask[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.msgId) {
        vm.retrieveMsg(to.params.msgId);
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
    if (this.msg.id) {
      this.msgService()
        .update(this.msg)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.msg.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.msgService()
        .create(this.msg)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.msg.created', { param: param.id });
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.msg[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.msg[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.msg[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.msg[field] = null;
    }
  }

  public retrieveMsg(msgId): void {
    this.msgService()
      .find(msgId)
      .then(res => {
        res.executeTime = new Date(res.executeTime);
        this.msg = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.msgTaskService()
      .retrieve()
      .then(res => {
        this.msgTasks = res.data;
      });
  }
}
