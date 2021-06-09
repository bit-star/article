import { IMsg } from '@/shared/model/msg.model';

export interface IMsgTask {
  id?: number;
  useridList?: string | null;
  taskId?: number | null;
  rspMsg?: string | null;
  status?: number | null;
  progressInPercent?: number | null;
  invalidUserIdList?: string | null;
  forbiddenUserIdList?: string | null;
  failedUserIdList?: string | null;
  readUserIdList?: string | null;
  unreadUserIdList?: string | null;
  invalidDeptIdList?: string | null;
  withdraw?: boolean | null;
  msg?: IMsg | null;
}

export class MsgTask implements IMsgTask {
  constructor(
    public id?: number,
    public useridList?: string | null,
    public taskId?: number | null,
    public rspMsg?: string | null,
    public status?: number | null,
    public progressInPercent?: number | null,
    public invalidUserIdList?: string | null,
    public forbiddenUserIdList?: string | null,
    public failedUserIdList?: string | null,
    public readUserIdList?: string | null,
    public unreadUserIdList?: string | null,
    public invalidDeptIdList?: string | null,
    public withdraw?: boolean | null,
    public msg?: IMsg | null
  ) {
    this.withdraw = this.withdraw ?? false;
  }
}
