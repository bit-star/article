import { IArticle } from '@/shared/model/article.model';
import { IModulePermission } from '@/shared/model/module-permission.model';
import { ISubType } from '@/shared/model/sub-type.model';

export interface IDdUser {
  id?: number;
  unionid?: string | null;
  remark?: string | null;
  userid?: string | null;
  isLeaderInDepts?: string | null;
  isBoss?: boolean | null;
  hiredDate?: number | null;
  isSenior?: boolean | null;
  tel?: string | null;
  department?: string | null;
  workPlace?: string | null;
  orderInDepts?: string | null;
  mobile?: string | null;
  errmsg?: string | null;
  active?: boolean | null;
  avatar?: string | null;
  isAdmin?: boolean | null;
  isHide?: boolean | null;
  jobnumber?: string | null;
  name?: string | null;
  extattr?: string | null;
  stateCode?: string | null;
  position?: string | null;
  roles?: string | null;
  creators?: IArticle[] | null;
  modulePermissions?: IModulePermission[] | null;
  subTypes?: ISubType[] | null;
}

export class DdUser implements IDdUser {
  constructor(
    public id?: number,
    public unionid?: string | null,
    public remark?: string | null,
    public userid?: string | null,
    public isLeaderInDepts?: string | null,
    public isBoss?: boolean | null,
    public hiredDate?: number | null,
    public isSenior?: boolean | null,
    public tel?: string | null,
    public department?: string | null,
    public workPlace?: string | null,
    public orderInDepts?: string | null,
    public mobile?: string | null,
    public errmsg?: string | null,
    public active?: boolean | null,
    public avatar?: string | null,
    public isAdmin?: boolean | null,
    public isHide?: boolean | null,
    public jobnumber?: string | null,
    public name?: string | null,
    public extattr?: string | null,
    public stateCode?: string | null,
    public position?: string | null,
    public roles?: string | null,
    public creators?: IArticle[] | null,
    public modulePermissions?: IModulePermission[] | null,
    public subTypes?: ISubType[] | null
  ) {
    this.isBoss = this.isBoss ?? false;
    this.isSenior = this.isSenior ?? false;
    this.active = this.active ?? false;
    this.isAdmin = this.isAdmin ?? false;
    this.isHide = this.isHide ?? false;
  }
}
