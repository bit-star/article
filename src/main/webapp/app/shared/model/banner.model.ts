import { IPhoto } from '@/shared/model/photo.model';

export interface IBanner {
  id?: number;
  title?: string | null;
  coverUrl?: string | null;
  name?: string | null;
  brand?: string | null;
  model?: string | null;
  specifications?: string | null;
  isExport?: boolean | null;
  supplier?: string | null;
  supplierAddress?: string | null;
  supplierCapacity?: string | null;
  photos?: IPhoto[] | null;
}

export class Banner implements IBanner {
  constructor(
    public id?: number,
    public title?: string | null,
    public coverUrl?: string | null,
    public name?: string | null,
    public brand?: string | null,
    public model?: string | null,
    public specifications?: string | null,
    public isExport?: boolean | null,
    public supplier?: string | null,
    public supplierAddress?: string | null,
    public supplierCapacity?: string | null,
    public photos?: IPhoto[] | null
  ) {
    this.isExport = this.isExport ?? false;
  }
}
