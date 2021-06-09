import { IBanner } from '@/shared/model/banner.model';

export interface IPhoto {
  id?: number;
  url?: string | null;
  banner?: IBanner | null;
}

export class Photo implements IPhoto {
  constructor(public id?: number, public url?: string | null, public banner?: IBanner | null) {}
}
