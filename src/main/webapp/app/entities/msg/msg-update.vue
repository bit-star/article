<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="articleApp.msg.home.createOrEditLabel"
          data-cy="MsgCreateUpdateHeading"
          v-text="$t('articleApp.msg.home.createOrEditLabel')"
        >
          Create or edit a Msg
        </h2>
        <div>
          <div class="form-group" v-if="msg.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="msg.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.deptIdList')" for="msg-deptIdList">Dept Id List</label>
            <input
              type="text"
              class="form-control"
              name="deptIdList"
              id="msg-deptIdList"
              data-cy="deptIdList"
              :class="{ valid: !$v.msg.deptIdList.$invalid, invalid: $v.msg.deptIdList.$invalid }"
              v-model="$v.msg.deptIdList.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.useridList')" for="msg-useridList">Userid List</label>
            <input
              type="text"
              class="form-control"
              name="useridList"
              id="msg-useridList"
              data-cy="useridList"
              :class="{ valid: !$v.msg.useridList.$invalid, invalid: $v.msg.useridList.$invalid }"
              v-model="$v.msg.useridList.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.toAllUser')" for="msg-toAllUser">To All User</label>
            <input
              type="checkbox"
              class="form-check"
              name="toAllUser"
              id="msg-toAllUser"
              data-cy="toAllUser"
              :class="{ valid: !$v.msg.toAllUser.$invalid, invalid: $v.msg.toAllUser.$invalid }"
              v-model="$v.msg.toAllUser.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.progressInPercent')" for="msg-progressInPercent"
              >Progress In Percent</label
            >
            <input
              type="number"
              class="form-control"
              name="progressInPercent"
              id="msg-progressInPercent"
              data-cy="progressInPercent"
              :class="{ valid: !$v.msg.progressInPercent.$invalid, invalid: $v.msg.progressInPercent.$invalid }"
              v-model.number="$v.msg.progressInPercent.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.title')" for="msg-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="msg-title"
              data-cy="title"
              :class="{ valid: !$v.msg.title.$invalid, invalid: $v.msg.title.$invalid }"
              v-model="$v.msg.title.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.singleTitle')" for="msg-singleTitle">Single Title</label>
            <input
              type="text"
              class="form-control"
              name="singleTitle"
              id="msg-singleTitle"
              data-cy="singleTitle"
              :class="{ valid: !$v.msg.singleTitle.$invalid, invalid: $v.msg.singleTitle.$invalid }"
              v-model="$v.msg.singleTitle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.singleUrl')" for="msg-singleUrl">Single Url</label>
            <input
              type="text"
              class="form-control"
              name="singleUrl"
              id="msg-singleUrl"
              data-cy="singleUrl"
              :class="{ valid: !$v.msg.singleUrl.$invalid, invalid: $v.msg.singleUrl.$invalid }"
              v-model="$v.msg.singleUrl.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.coverUrl')" for="msg-coverUrl">Cover Url</label>
            <input
              type="text"
              class="form-control"
              name="coverUrl"
              id="msg-coverUrl"
              data-cy="coverUrl"
              :class="{ valid: !$v.msg.coverUrl.$invalid, invalid: $v.msg.coverUrl.$invalid }"
              v-model="$v.msg.coverUrl.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.numberOfShards')" for="msg-numberOfShards">Number Of Shards</label>
            <input
              type="number"
              class="form-control"
              name="numberOfShards"
              id="msg-numberOfShards"
              data-cy="numberOfShards"
              :class="{ valid: !$v.msg.numberOfShards.$invalid, invalid: $v.msg.numberOfShards.$invalid }"
              v-model.number="$v.msg.numberOfShards.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.completeSharding')" for="msg-completeSharding"
              >Complete Sharding</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="completeSharding"
              id="msg-completeSharding"
              data-cy="completeSharding"
              :class="{ valid: !$v.msg.completeSharding.$invalid, invalid: $v.msg.completeSharding.$invalid }"
              v-model="$v.msg.completeSharding.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.msg')" for="msg-msg">Msg</label>
            <input
              type="text"
              class="form-control"
              name="msg"
              id="msg-msg"
              data-cy="msg"
              :class="{ valid: !$v.msg.msg.$invalid, invalid: $v.msg.msg.$invalid }"
              v-model="$v.msg.msg.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.executeTime')" for="msg-executeTime">Execute Time</label>
            <div class="d-flex">
              <input
                id="msg-executeTime"
                data-cy="executeTime"
                type="datetime-local"
                class="form-control"
                name="executeTime"
                :class="{ valid: !$v.msg.executeTime.$invalid, invalid: $v.msg.executeTime.$invalid }"
                :value="convertDateTimeFromServer($v.msg.executeTime.$model)"
                @change="updateInstantField('executeTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.agentId')" for="msg-agentId">Agent Id</label>
            <input
              type="number"
              class="form-control"
              name="agentId"
              id="msg-agentId"
              data-cy="agentId"
              :class="{ valid: !$v.msg.agentId.$invalid, invalid: $v.msg.agentId.$invalid }"
              v-model.number="$v.msg.agentId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.type')" for="msg-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.msg.type.$invalid, invalid: $v.msg.type.$invalid }"
              v-model="$v.msg.type.$model"
              id="msg-type"
              data-cy="type"
            >
              <option value="Voice" v-bind:label="$t('articleApp.DdMessageType.Voice')">Voice</option>
              <option value="ActionCard" v-bind:label="$t('articleApp.DdMessageType.ActionCard')">ActionCard</option>
              <option value="Markdown" v-bind:label="$t('articleApp.DdMessageType.Markdown')">Markdown</option>
              <option value="Oa" v-bind:label="$t('articleApp.DdMessageType.Oa')">Oa</option>
              <option value="Link" v-bind:label="$t('articleApp.DdMessageType.Link')">Link</option>
              <option value="File" v-bind:label="$t('articleApp.DdMessageType.File')">File</option>
              <option value="Text" v-bind:label="$t('articleApp.DdMessageType.Text')">Text</option>
              <option value="Image" v-bind:label="$t('articleApp.DdMessageType.Image')">Image</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.msg.status')" for="msg-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.msg.status.$invalid, invalid: $v.msg.status.$invalid }"
              v-model="$v.msg.status.$model"
              id="msg-status"
              data-cy="status"
            >
              <option value="SentSuccessfully" v-bind:label="$t('articleApp.MessageStatus.SentSuccessfully')">SentSuccessfully</option>
              <option value="Sending" v-bind:label="$t('articleApp.MessageStatus.Sending')">Sending</option>
              <option value="NotSentYet" v-bind:label="$t('articleApp.MessageStatus.NotSentYet')">NotSentYet</option>
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
            :disabled="$v.msg.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./msg-update.component.ts"></script>
