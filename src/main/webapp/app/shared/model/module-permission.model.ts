import { IDdUser } from '@/shared/model/dd-user.model';

export interface IModulePermission {
  id?: number;
  name?: string | null;
  enable?: boolean | null;
  ddUsers?: IDdUser[] | null;
}

export class ModulePermission implements IModulePermission {
  constructor(public id?: number, public name?: string | null, public enable?: boolean | null, public ddUsers?: IDdUser[] | null) {
    this.enable = this.enable ?? false;
  }
}
