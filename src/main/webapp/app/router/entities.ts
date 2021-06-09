import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Article = () => import('@/entities/article/article.vue');
// prettier-ignore
const ArticleUpdate = () => import('@/entities/article/article-update.vue');
// prettier-ignore
const ArticleDetails = () => import('@/entities/article/article-details.vue');
// prettier-ignore
const Banner = () => import('@/entities/banner/banner.vue');
// prettier-ignore
const BannerUpdate = () => import('@/entities/banner/banner-update.vue');
// prettier-ignore
const BannerDetails = () => import('@/entities/banner/banner-details.vue');
// prettier-ignore
const Type = () => import('@/entities/type/type.vue');
// prettier-ignore
const TypeUpdate = () => import('@/entities/type/type-update.vue');
// prettier-ignore
const TypeDetails = () => import('@/entities/type/type-details.vue');
// prettier-ignore
const SubType = () => import('@/entities/sub-type/sub-type.vue');
// prettier-ignore
const SubTypeUpdate = () => import('@/entities/sub-type/sub-type-update.vue');
// prettier-ignore
const SubTypeDetails = () => import('@/entities/sub-type/sub-type-details.vue');
// prettier-ignore
const Annex = () => import('@/entities/annex/annex.vue');
// prettier-ignore
const AnnexUpdate = () => import('@/entities/annex/annex-update.vue');
// prettier-ignore
const AnnexDetails = () => import('@/entities/annex/annex-details.vue');
// prettier-ignore
const Photo = () => import('@/entities/photo/photo.vue');
// prettier-ignore
const PhotoUpdate = () => import('@/entities/photo/photo-update.vue');
// prettier-ignore
const PhotoDetails = () => import('@/entities/photo/photo-details.vue');
// prettier-ignore
const ModulePermission = () => import('@/entities/module-permission/module-permission.vue');
// prettier-ignore
const ModulePermissionUpdate = () => import('@/entities/module-permission/module-permission-update.vue');
// prettier-ignore
const ModulePermissionDetails = () => import('@/entities/module-permission/module-permission-details.vue');
// prettier-ignore
const DdDept = () => import('@/entities/dd-dept/dd-dept.vue');
// prettier-ignore
const DdDeptUpdate = () => import('@/entities/dd-dept/dd-dept-update.vue');
// prettier-ignore
const DdDeptDetails = () => import('@/entities/dd-dept/dd-dept-details.vue');
// prettier-ignore
const DdUser = () => import('@/entities/dd-user/dd-user.vue');
// prettier-ignore
const DdUserUpdate = () => import('@/entities/dd-user/dd-user-update.vue');
// prettier-ignore
const DdUserDetails = () => import('@/entities/dd-user/dd-user-details.vue');
// prettier-ignore
const Msg = () => import('@/entities/msg/msg.vue');
// prettier-ignore
const MsgUpdate = () => import('@/entities/msg/msg-update.vue');
// prettier-ignore
const MsgDetails = () => import('@/entities/msg/msg-details.vue');
// prettier-ignore
const MsgTask = () => import('@/entities/msg-task/msg-task.vue');
// prettier-ignore
const MsgTaskUpdate = () => import('@/entities/msg-task/msg-task-update.vue');
// prettier-ignore
const MsgTaskDetails = () => import('@/entities/msg-task/msg-task-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/article',
    name: 'Article',
    component: Article,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/article/new',
    name: 'ArticleCreate',
    component: ArticleUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/article/:articleId/edit',
    name: 'ArticleEdit',
    component: ArticleUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/article/:articleId/view',
    name: 'ArticleView',
    component: ArticleDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/banner',
    name: 'Banner',
    component: Banner,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/banner/new',
    name: 'BannerCreate',
    component: BannerUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/banner/:bannerId/edit',
    name: 'BannerEdit',
    component: BannerUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/banner/:bannerId/view',
    name: 'BannerView',
    component: BannerDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/type',
    name: 'Type',
    component: Type,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/type/new',
    name: 'TypeCreate',
    component: TypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/type/:typeId/edit',
    name: 'TypeEdit',
    component: TypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/type/:typeId/view',
    name: 'TypeView',
    component: TypeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sub-type',
    name: 'SubType',
    component: SubType,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sub-type/new',
    name: 'SubTypeCreate',
    component: SubTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sub-type/:subTypeId/edit',
    name: 'SubTypeEdit',
    component: SubTypeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/sub-type/:subTypeId/view',
    name: 'SubTypeView',
    component: SubTypeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/annex',
    name: 'Annex',
    component: Annex,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/annex/new',
    name: 'AnnexCreate',
    component: AnnexUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/annex/:annexId/edit',
    name: 'AnnexEdit',
    component: AnnexUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/annex/:annexId/view',
    name: 'AnnexView',
    component: AnnexDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/photo',
    name: 'Photo',
    component: Photo,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/photo/new',
    name: 'PhotoCreate',
    component: PhotoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/photo/:photoId/edit',
    name: 'PhotoEdit',
    component: PhotoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/photo/:photoId/view',
    name: 'PhotoView',
    component: PhotoDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/module-permission',
    name: 'ModulePermission',
    component: ModulePermission,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/module-permission/new',
    name: 'ModulePermissionCreate',
    component: ModulePermissionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/module-permission/:modulePermissionId/edit',
    name: 'ModulePermissionEdit',
    component: ModulePermissionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/module-permission/:modulePermissionId/view',
    name: 'ModulePermissionView',
    component: ModulePermissionDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-dept',
    name: 'DdDept',
    component: DdDept,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-dept/new',
    name: 'DdDeptCreate',
    component: DdDeptUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-dept/:ddDeptId/edit',
    name: 'DdDeptEdit',
    component: DdDeptUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-dept/:ddDeptId/view',
    name: 'DdDeptView',
    component: DdDeptDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user',
    name: 'DdUser',
    component: DdUser,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user/new',
    name: 'DdUserCreate',
    component: DdUserUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user/:ddUserId/edit',
    name: 'DdUserEdit',
    component: DdUserUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dd-user/:ddUserId/view',
    name: 'DdUserView',
    component: DdUserDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/msg',
    name: 'Msg',
    component: Msg,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/msg/new',
    name: 'MsgCreate',
    component: MsgUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/msg/:msgId/edit',
    name: 'MsgEdit',
    component: MsgUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/msg/:msgId/view',
    name: 'MsgView',
    component: MsgDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/msg-task',
    name: 'MsgTask',
    component: MsgTask,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/msg-task/new',
    name: 'MsgTaskCreate',
    component: MsgTaskUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/msg-task/:msgTaskId/edit',
    name: 'MsgTaskEdit',
    component: MsgTaskUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/msg-task/:msgTaskId/view',
    name: 'MsgTaskView',
    component: MsgTaskDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
