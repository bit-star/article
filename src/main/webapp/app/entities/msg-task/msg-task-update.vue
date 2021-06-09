<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="articleApp.msgTask.home.createOrEditLabel"
          data-cy="MsgTaskCreateUpdateHeading"
          v-text="$t('articleApp.msgTask.home.createOrEditLabel')"
        >
          Create or edit a MsgTask
        </h2>
        <div>
          <div class="form-group" v-if="msgTask.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="msgTask.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msgTask.useridList')" for="msg-task-useridList">Userid List</label>
            <input
              type="text"
              class="form-control"
              name="useridList"
              id="msg-task-useridList"
              data-cy="useridList"
              :class="{ valid: !$v.msgTask.useridList.$invalid, invalid: $v.msgTask.useridList.$invalid }"
              v-model="$v.msgTask.useridList.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msgTask.taskId')" for="msg-task-taskId">Task Id</label>
            <input
              type="number"
              class="form-control"
              name="taskId"
              id="msg-task-taskId"
              data-cy="taskId"
              :class="{ valid: !$v.msgTask.taskId.$invalid, invalid: $v.msgTask.taskId.$invalid }"
              v-model.number="$v.msgTask.taskId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msgTask.rspMsg')" for="msg-task-rspMsg">Rsp Msg</label>
            <input
              type="text"
              class="form-control"
              name="rspMsg"
              id="msg-task-rspMsg"
              data-cy="rspMsg"
              :class="{ valid: !$v.msgTask.rspMsg.$invalid, invalid: $v.msgTask.rspMsg.$invalid }"
              v-model="$v.msgTask.rspMsg.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msgTask.status')" for="msg-task-status">Status</label>
            <input
              type="number"
              class="form-control"
              name="status"
              id="msg-task-status"
              data-cy="status"
              :class="{ valid: !$v.msgTask.status.$invalid, invalid: $v.msgTask.status.$invalid }"
              v-model.number="$v.msgTask.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msgTask.progressInPercent')" for="msg-task-progressInPercent"
              >Progress In Percent</label
            >
            <input
              type="number"
              class="form-control"
              name="progressInPercent"
              id="msg-task-progressInPercent"
              data-cy="progressInPercent"
              :class="{ valid: !$v.msgTask.progressInPercent.$invalid, invalid: $v.msgTask.progressInPercent.$invalid }"
              v-model.number="$v.msgTask.progressInPercent.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msgTask.invalidUserIdList')" for="msg-task-invalidUserIdList"
              >Invalid User Id List</label
            >
            <input
              type="text"
              class="form-control"
              name="invalidUserIdList"
              id="msg-task-invalidUserIdList"
              data-cy="invalidUserIdList"
              :class="{ valid: !$v.msgTask.invalidUserIdList.$invalid, invalid: $v.msgTask.invalidUserIdList.$invalid }"
              v-model="$v.msgTask.invalidUserIdList.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msgTask.forbiddenUserIdList')" for="msg-task-forbiddenUserIdList"
              >Forbidden User Id List</label
            >
            <input
              type="text"
              class="form-control"
              name="forbiddenUserIdList"
              id="msg-task-forbiddenUserIdList"
              data-cy="forbiddenUserIdList"
              :class="{ valid: !$v.msgTask.forbiddenUserIdList.$invalid, invalid: $v.msgTask.forbiddenUserIdList.$invalid }"
              v-model="$v.msgTask.forbiddenUserIdList.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msgTask.failedUserIdList')" for="msg-task-failedUserIdList"
              >Failed User Id List</label
            >
            <input
              type="text"
              class="form-control"
              name="failedUserIdList"
              id="msg-task-failedUserIdList"
              data-cy="failedUserIdList"
              :class="{ valid: !$v.msgTask.failedUserIdList.$invalid, invalid: $v.msgTask.failedUserIdList.$invalid }"
              v-model="$v.msgTask.failedUserIdList.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msgTask.readUserIdList')" for="msg-task-readUserIdList"
              >Read User Id List</label
            >
            <input
              type="text"
              class="form-control"
              name="readUserIdList"
              id="msg-task-readUserIdList"
              data-cy="readUserIdList"
              :class="{ valid: !$v.msgTask.readUserIdList.$invalid, invalid: $v.msgTask.readUserIdList.$invalid }"
              v-model="$v.msgTask.readUserIdList.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msgTask.unreadUserIdList')" for="msg-task-unreadUserIdList"
              >Unread User Id List</label
            >
            <input
              type="text"
              class="form-control"
              name="unreadUserIdList"
              id="msg-task-unreadUserIdList"
              data-cy="unreadUserIdList"
              :class="{ valid: !$v.msgTask.unreadUserIdList.$invalid, invalid: $v.msgTask.unreadUserIdList.$invalid }"
              v-model="$v.msgTask.unreadUserIdList.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msgTask.invalidDeptIdList')" for="msg-task-invalidDeptIdList"
              >Invalid Dept Id List</label
            >
            <input
              type="text"
              class="form-control"
              name="invalidDeptIdList"
              id="msg-task-invalidDeptIdList"
              data-cy="invalidDeptIdList"
              :class="{ valid: !$v.msgTask.invalidDeptIdList.$invalid, invalid: $v.msgTask.invalidDeptIdList.$invalid }"
              v-model="$v.msgTask.invalidDeptIdList.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msgTask.withdraw')" for="msg-task-withdraw">Withdraw</label>
            <input
              type="checkbox"
              class="form-check"
              name="withdraw"
              id="msg-task-withdraw"
              data-cy="withdraw"
              :class="{ valid: !$v.msgTask.withdraw.$invalid, invalid: $v.msgTask.withdraw.$invalid }"
              v-model="$v.msgTask.withdraw.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msgTask.msg')" for="msg-task-msg">Msg</label>
            <select class="form-control" id="msg-task-msg" data-cy="msg" name="msg" v-model="msgTask.msg">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="msgTask.msg && msgOption.id === msgTask.msg.id ? msgTask.msg : msgOption"
                v-for="msgOption in msgs"
                :key="msgOption.id"
              >
                {{ msgOption.id }}
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
            :disabled="$v.msgTask.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./msg-task-update.component.ts"></script>
