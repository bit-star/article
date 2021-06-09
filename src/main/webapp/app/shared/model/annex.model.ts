import { IArticle } from '@/shared/model/article.model';

import { StorageMode } from '@/shared/model/enumerations/storage-mode.model';
export interface IAnnex {
  id?: number;
  spaceId?: string | null;
  fileId?: string | null;
  fileName?: string | null;
  fileSize?: string | null;
  fileType?: string | null;
  storageMode?: StorageMode | null;
  url?: string | null;
  article?: IArticle | null;
}

export class Annex implements IAnnex {
  constructor(
    public id?: number,
    public spaceId?: string | null,
    public fileId?: string | null,
    public fileName?: string | null,
    public fileSize?: string | null,
    public fileType?: string | null,
    public storageMode?: StorageMode | null,
    public url?: string | null,
    public article?: IArticle | null
  ) {}
}
