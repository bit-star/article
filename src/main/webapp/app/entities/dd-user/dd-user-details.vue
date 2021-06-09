<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ddUser">
        <h2 class="jh-entity-heading" data-cy="ddUserDetailsHeading">
          <span v-text="$t('articleApp.ddUser.detail.title')">DdUser</span> {{ ddUser.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('articleApp.ddUser.unionid')">Unionid</span>
          </dt>
          <dd>
            <span>{{ ddUser.unionid }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.remark')">Remark</span>
          </dt>
          <dd>
            <span>{{ ddUser.remark }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.userid')">Userid</span>
          </dt>
          <dd>
            <span>{{ ddUser.userid }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.isLeaderInDepts')">Is Leader In Depts</span>
          </dt>
          <dd>
            <span>{{ ddUser.isLeaderInDepts }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.isBoss')">Is Boss</span>
          </dt>
          <dd>
            <span>{{ ddUser.isBoss }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.hiredDate')">Hired Date</span>
          </dt>
          <dd>
            <span>{{ ddUser.hiredDate }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.isSenior')">Is Senior</span>
          </dt>
          <dd>
            <span>{{ ddUser.isSenior }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.tel')">Tel</span>
          </dt>
          <dd>
            <span>{{ ddUser.tel }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.department')">Department</span>
          </dt>
          <dd>
            <span>{{ ddUser.department }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.workPlace')">Work Place</span>
          </dt>
          <dd>
            <span>{{ ddUser.workPlace }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.orderInDepts')">Order In Depts</span>
          </dt>
          <dd>
            <span>{{ ddUser.orderInDepts }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.mobile')">Mobile</span>
          </dt>
          <dd>
            <span>{{ ddUser.mobile }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.errmsg')">Errmsg</span>
          </dt>
          <dd>
            <span>{{ ddUser.errmsg }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.active')">Active</span>
          </dt>
          <dd>
            <span>{{ ddUser.active }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.avatar')">Avatar</span>
          </dt>
          <dd>
            <span>{{ ddUser.avatar }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.isAdmin')">Is Admin</span>
          </dt>
          <dd>
            <span>{{ ddUser.isAdmin }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.isHide')">Is Hide</span>
          </dt>
          <dd>
            <span>{{ ddUser.isHide }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.jobnumber')">Jobnumber</span>
          </dt>
          <dd>
            <span>{{ ddUser.jobnumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.name')">Name</span>
          </dt>
          <dd>
            <span>{{ ddUser.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.extattr')">Extattr</span>
          </dt>
          <dd>
            <span>{{ ddUser.extattr }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.stateCode')">State Code</span>
          </dt>
          <dd>
            <span>{{ ddUser.stateCode }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.position')">Position</span>
          </dt>
          <dd>
            <span>{{ ddUser.position }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.roles')">Roles</span>
          </dt>
          <dd>
            <span>{{ ddUser.roles }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.modulePermission')">Module Permission</span>
          </dt>
          <dd>
            <span v-for="(modulePermission, i) in ddUser.modulePermissions" :key="modulePermission.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'ModulePermissionView', params: { modulePermissionId: modulePermission.id } }">{{
                modulePermission.id
              }}</router-link>
            </span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddUser.subType')">Sub Type</span>
          </dt>
          <dd>
            <span v-for="(subType, i) in ddUser.subTypes" :key="subType.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'SubTypeView', params: { subTypeId: subType.id } }">{{ subType.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="ddUser.id" :to="{ name: 'DdUserEdit', params: { ddUserId: ddUser.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./dd-user-details.component.ts"></script>
