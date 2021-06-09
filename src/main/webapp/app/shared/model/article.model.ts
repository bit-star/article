import { IAnnex } from '@/shared/model/annex.model';
import { ISubType } from '@/shared/model/sub-type.model';
import { IDdUser } from '@/shared/model/dd-user.model';
import { IDdDept } from '@/shared/model/dd-dept.model';

export interface IArticle {
  id?: number;
  title?: string | null;
  time?: Date | null;
  text?: string | null;
  contributors?: string | null;
  annexes?: IAnnex[] | null;
  subType?: ISubType | null;
  ddUser?: IDdUser | null;
  ddDepts?: IDdDept[] | null;
}

export class Article implements IArticle {
  constructor(
    public id?: number,
    public title?: string | null,
    public time?: Date | null,
    public text?: string | null,
    public contributors?: string | null,
    public annexes?: IAnnex[] | null,
    public subType?: ISubType | null,
    public ddUser?: IDdUser | null,
    public ddDepts?: IDdDept[] | null
  ) {}
}
