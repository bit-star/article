import { IArticle } from '@/shared/model/article.model';
import { IType } from '@/shared/model/type.model';
import { IDdUser } from '@/shared/model/dd-user.model';

export interface ISubType {
  id?: number;
  name?: string | null;
  articles?: IArticle[] | null;
  type?: IType | null;
  ddUsers?: IDdUser[] | null;
}

export class SubType implements ISubType {
  constructor(
    public id?: number,
    public name?: string | null,
    public articles?: IArticle[] | null,
    public type?: IType | null,
    public ddUsers?: IDdUser[] | null
  ) {}
}
