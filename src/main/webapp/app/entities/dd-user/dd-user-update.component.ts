import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import ArticleService from '@/entities/article/article.service';
import { IArticle } from '@/shared/model/article.model';

import ModulePermissionService from '@/entities/module-permission/module-permission.service';
import { IModulePermission } from '@/shared/model/module-permission.model';

import SubTypeService from '@/entities/sub-type/sub-type.service';
import { ISubType } from '@/shared/model/sub-type.model';

import { IDdUser, DdUser } from '@/shared/model/dd-user.model';
import DdUserService from './dd-user.service';

const validations: any = {
  ddUser: {
    unionid: {},
    remark: {},
    userid: {},
    isLeaderInDepts: {},
    isBoss: {},
    hiredDate: {},
    isSenior: {},
    tel: {},
    department: {},
    workPlace: {},
    orderInDepts: {},
    mobile: {},
    errmsg: {},
    active: {},
    avatar: {},
    isAdmin: {},
    isHide: {},
    jobnumber: {},
    name: {},
    extattr: {},
    stateCode: {},
    position: {},
    roles: {},
  },
};

@Component({
  validations,
})
export default class DdUserUpdate extends mixins(JhiDataUtils) {
  @Inject('ddUserService') private ddUserService: () => DdUserService;
  public ddUser: IDdUser = new DdUser();

  @Inject('articleService') private articleService: () => ArticleService;

  public articles: IArticle[] = [];

  @Inject('modulePermissionService') private modulePermissionService: () => ModulePermissionService;

  public modulePermissions: IModulePermission[] = [];

  @Inject('subTypeService') private subTypeService: () => SubTypeService;

  public subTypes: ISubType[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ddUserId) {
        vm.retrieveDdUser(to.params.ddUserId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
    this.ddUser.modulePermissions = [];
    this.ddUser.subTypes = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.ddUser.id) {
      this.ddUserService()
        .update(this.ddUser)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.ddUser.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.ddUserService()
        .create(this.ddUser)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('articleApp.ddUser.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveDdUser(ddUserId): void {
    this.ddUserService()
      .find(ddUserId)
      .then(res => {
        this.ddUser = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.articleService()
      .retrieve()
      .then(res => {
        this.articles = res.data;
      });
    this.modulePermissionService()
      .retrieve()
      .then(res => {
        this.modulePermissions = res.data;
      });
    this.subTypeService()
      .retrieve()
      .then(res => {
        this.subTypes = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
