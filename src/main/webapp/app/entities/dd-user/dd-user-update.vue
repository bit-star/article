<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="articleApp.ddUser.home.createOrEditLabel"
          data-cy="DdUserCreateUpdateHeading"
          v-text="$t('articleApp.ddUser.home.createOrEditLabel')"
        >
          Create or edit a DdUser
        </h2>
        <div>
          <div class="form-group" v-if="ddUser.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ddUser.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.unionid')" for="dd-user-unionid">Unionid</label>
            <input
              type="text"
              class="form-control"
              name="unionid"
              id="dd-user-unionid"
              data-cy="unionid"
              :class="{ valid: !$v.ddUser.unionid.$invalid, invalid: $v.ddUser.unionid.$invalid }"
              v-model="$v.ddUser.unionid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.remark')" for="dd-user-remark">Remark</label>
            <input
              type="text"
              class="form-control"
              name="remark"
              id="dd-user-remark"
              data-cy="remark"
              :class="{ valid: !$v.ddUser.remark.$invalid, invalid: $v.ddUser.remark.$invalid }"
              v-model="$v.ddUser.remark.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.userid')" for="dd-user-userid">Userid</label>
            <input
              type="text"
              class="form-control"
              name="userid"
              id="dd-user-userid"
              data-cy="userid"
              :class="{ valid: !$v.ddUser.userid.$invalid, invalid: $v.ddUser.userid.$invalid }"
              v-model="$v.ddUser.userid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.isLeaderInDepts')" for="dd-user-isLeaderInDepts"
              >Is Leader In Depts</label
            >
            <input
              type="text"
              class="form-control"
              name="isLeaderInDepts"
              id="dd-user-isLeaderInDepts"
              data-cy="isLeaderInDepts"
              :class="{ valid: !$v.ddUser.isLeaderInDepts.$invalid, invalid: $v.ddUser.isLeaderInDepts.$invalid }"
              v-model="$v.ddUser.isLeaderInDepts.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.isBoss')" for="dd-user-isBoss">Is Boss</label>
            <input
              type="checkbox"
              class="form-check"
              name="isBoss"
              id="dd-user-isBoss"
              data-cy="isBoss"
              :class="{ valid: !$v.ddUser.isBoss.$invalid, invalid: $v.ddUser.isBoss.$invalid }"
              v-model="$v.ddUser.isBoss.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.hiredDate')" for="dd-user-hiredDate">Hired Date</label>
            <input
              type="number"
              class="form-control"
              name="hiredDate"
              id="dd-user-hiredDate"
              data-cy="hiredDate"
              :class="{ valid: !$v.ddUser.hiredDate.$invalid, invalid: $v.ddUser.hiredDate.$invalid }"
              v-model.number="$v.ddUser.hiredDate.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.isSenior')" for="dd-user-isSenior">Is Senior</label>
            <input
              type="checkbox"
              class="form-check"
              name="isSenior"
              id="dd-user-isSenior"
              data-cy="isSenior"
              :class="{ valid: !$v.ddUser.isSenior.$invalid, invalid: $v.ddUser.isSenior.$invalid }"
              v-model="$v.ddUser.isSenior.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.tel')" for="dd-user-tel">Tel</label>
            <input
              type="text"
              class="form-control"
              name="tel"
              id="dd-user-tel"
              data-cy="tel"
              :class="{ valid: !$v.ddUser.tel.$invalid, invalid: $v.ddUser.tel.$invalid }"
              v-model="$v.ddUser.tel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.department')" for="dd-user-department">Department</label>
            <input
              type="text"
              class="form-control"
              name="department"
              id="dd-user-department"
              data-cy="department"
              :class="{ valid: !$v.ddUser.department.$invalid, invalid: $v.ddUser.department.$invalid }"
              v-model="$v.ddUser.department.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.workPlace')" for="dd-user-workPlace">Work Place</label>
            <input
              type="text"
              class="form-control"
              name="workPlace"
              id="dd-user-workPlace"
              data-cy="workPlace"
              :class="{ valid: !$v.ddUser.workPlace.$invalid, invalid: $v.ddUser.workPlace.$invalid }"
              v-model="$v.ddUser.workPlace.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.orderInDepts')" for="dd-user-orderInDepts"
              >Order In Depts</label
            >
            <textarea
              class="form-control"
              name="orderInDepts"
              id="dd-user-orderInDepts"
              data-cy="orderInDepts"
              :class="{ valid: !$v.ddUser.orderInDepts.$invalid, invalid: $v.ddUser.orderInDepts.$invalid }"
              v-model="$v.ddUser.orderInDepts.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.mobile')" for="dd-user-mobile">Mobile</label>
            <input
              type="text"
              class="form-control"
              name="mobile"
              id="dd-user-mobile"
              data-cy="mobile"
              :class="{ valid: !$v.ddUser.mobile.$invalid, invalid: $v.ddUser.mobile.$invalid }"
              v-model="$v.ddUser.mobile.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.errmsg')" for="dd-user-errmsg">Errmsg</label>
            <input
              type="text"
              class="form-control"
              name="errmsg"
              id="dd-user-errmsg"
              data-cy="errmsg"
              :class="{ valid: !$v.ddUser.errmsg.$invalid, invalid: $v.ddUser.errmsg.$invalid }"
              v-model="$v.ddUser.errmsg.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.active')" for="dd-user-active">Active</label>
            <input
              type="checkbox"
              class="form-check"
              name="active"
              id="dd-user-active"
              data-cy="active"
              :class="{ valid: !$v.ddUser.active.$invalid, invalid: $v.ddUser.active.$invalid }"
              v-model="$v.ddUser.active.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.avatar')" for="dd-user-avatar">Avatar</label>
            <input
              type="text"
              class="form-control"
              name="avatar"
              id="dd-user-avatar"
              data-cy="avatar"
              :class="{ valid: !$v.ddUser.avatar.$invalid, invalid: $v.ddUser.avatar.$invalid }"
              v-model="$v.ddUser.avatar.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.isAdmin')" for="dd-user-isAdmin">Is Admin</label>
            <input
              type="checkbox"
              class="form-check"
              name="isAdmin"
              id="dd-user-isAdmin"
              data-cy="isAdmin"
              :class="{ valid: !$v.ddUser.isAdmin.$invalid, invalid: $v.ddUser.isAdmin.$invalid }"
              v-model="$v.ddUser.isAdmin.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.isHide')" for="dd-user-isHide">Is Hide</label>
            <input
              type="checkbox"
              class="form-check"
              name="isHide"
              id="dd-user-isHide"
              data-cy="isHide"
              :class="{ valid: !$v.ddUser.isHide.$invalid, invalid: $v.ddUser.isHide.$invalid }"
              v-model="$v.ddUser.isHide.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.jobnumber')" for="dd-user-jobnumber">Jobnumber</label>
            <input
              type="text"
              class="form-control"
              name="jobnumber"
              id="dd-user-jobnumber"
              data-cy="jobnumber"
              :class="{ valid: !$v.ddUser.jobnumber.$invalid, invalid: $v.ddUser.jobnumber.$invalid }"
              v-model="$v.ddUser.jobnumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.name')" for="dd-user-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="dd-user-name"
              data-cy="name"
              :class="{ valid: !$v.ddUser.name.$invalid, invalid: $v.ddUser.name.$invalid }"
              v-model="$v.ddUser.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.extattr')" for="dd-user-extattr">Extattr</label>
            <textarea
              class="form-control"
              name="extattr"
              id="dd-user-extattr"
              data-cy="extattr"
              :class="{ valid: !$v.ddUser.extattr.$invalid, invalid: $v.ddUser.extattr.$invalid }"
              v-model="$v.ddUser.extattr.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.stateCode')" for="dd-user-stateCode">State Code</label>
            <input
              type="text"
              class="form-control"
              name="stateCode"
              id="dd-user-stateCode"
              data-cy="stateCode"
              :class="{ valid: !$v.ddUser.stateCode.$invalid, invalid: $v.ddUser.stateCode.$invalid }"
              v-model="$v.ddUser.stateCode.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.position')" for="dd-user-position">Position</label>
            <textarea
              class="form-control"
              name="position"
              id="dd-user-position"
              data-cy="position"
              :class="{ valid: !$v.ddUser.position.$invalid, invalid: $v.ddUser.position.$invalid }"
              v-model="$v.ddUser.position.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddUser.roles')" for="dd-user-roles">Roles</label>
            <input
              type="text"
              class="form-control"
              name="roles"
              id="dd-user-roles"
              data-cy="roles"
              :class="{ valid: !$v.ddUser.roles.$invalid, invalid: $v.ddUser.roles.$invalid }"
              v-model="$v.ddUser.roles.$model"
            />
          </div>
          <div class="form-group">
            <label v-text="$t('articleApp.ddUser.modulePermission')" for="dd-user-modulePermission">Module Permission</label>
            <select
              class="form-control"
              id="dd-user-modulePermission"
              data-cy="modulePermission"
              multiple
              name="modulePermission"
              v-if="ddUser.modulePermissions !== undefined"
              v-model="ddUser.modulePermissions"
            >
              <option
                v-bind:value="getSelected(ddUser.modulePermissions, modulePermissionOption)"
                v-for="modulePermissionOption in modulePermissions"
                :key="modulePermissionOption.id"
              >
                {{ modulePermissionOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label v-text="$t('articleApp.ddUser.subType')" for="dd-user-subType">Sub Type</label>
            <select
              class="form-control"
              id="dd-user-subType"
              data-cy="subType"
              multiple
              name="subType"
              v-if="ddUser.subTypes !== undefined"
              v-model="ddUser.subTypes"
            >
              <option v-bind:value="getSelected(ddUser.subTypes, subTypeOption)" v-for="subTypeOption in subTypes" :key="subTypeOption.id">
                {{ subTypeOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.ddUser.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./dd-user-update.component.ts"></script>
