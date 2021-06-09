import { IMsgTask } from '@/shared/model/msg-task.model';

import { DdMessageType } from '@/shared/model/enumerations/dd-message-type.model';
import { MessageStatus } from '@/shared/model/enumerations/message-status.model';
export interface IMsg {
  id?: number;
  deptIdList?: string | null;
  useridList?: string | null;
  toAllUser?: boolean | null;
  progressInPercent?: number | null;
  title?: string | null;
  singleTitle?: string | null;
  singleUrl?: string | null;
  coverUrl?: string | null;
  numberOfShards?: number | null;
  completeSharding?: boolean | null;
  msg?: string | null;
  executeTime?: Date | null;
  agentId?: number | null;
  type?: DdMessageType | null;
  status?: MessageStatus | null;
  msgTasks?: IMsgTask[] | null;
}

export class Msg implements IMsg {
  constructor(
    public id?: number,
    public deptIdList?: string | null,
    public useridList?: string | null,
    public toAllUser?: boolean | null,
    public progressInPercent?: number | null,
    public title?: string | null,
    public singleTitle?: string | null,
    public singleUrl?: string | null,
    public coverUrl?: string | null,
    public numberOfShards?: number | null,
    public completeSharding?: boolean | null,
    public msg?: string | null,
    public executeTime?: Date | null,
    public agentId?: number | null,
    public type?: DdMessageType | null,
    public status?: MessageStatus | null,
    public msgTasks?: IMsgTask[] | null
  ) {
    this.toAllUser = this.toAllUser ?? false;
    this.completeSharding = this.completeSharding ?? false;
  }
}
