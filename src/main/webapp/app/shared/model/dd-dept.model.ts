import { IArticle } from '@/shared/model/article.model';

export interface IDdDept {
  id?: number;
  name?: string | null;
  parentId?: string | null;
  article?: IArticle | null;
}

export class DdDept implements IDdDept {
  constructor(public id?: number, public name?: string | null, public parentId?: string | null, public article?: IArticle | null) {}
}
