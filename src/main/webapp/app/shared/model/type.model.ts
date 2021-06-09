import { ISubType } from '@/shared/model/sub-type.model';

export interface IType {
  id?: number;
  name?: string | null;
  subTypes?: ISubType[] | null;
}

export class Type implements IType {
  constructor(public id?: number, public name?: string | null, public subTypes?: ISubType[] | null) {}
}
