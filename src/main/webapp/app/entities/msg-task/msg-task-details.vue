<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="msgTask">
        <h2 class="jh-entity-heading" data-cy="msgTaskDetailsHeading">
          <span v-text="$t('articleApp.msgTask.detail.title')">MsgTask</span> {{ msgTask.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('articleApp.msgTask.useridList')">Userid List</span>
          </dt>
          <dd>
            <span>{{ msgTask.useridList }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msgTask.taskId')">Task Id</span>
          </dt>
          <dd>
            <span>{{ msgTask.taskId }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msgTask.rspMsg')">Rsp Msg</span>
          </dt>
          <dd>
            <span>{{ msgTask.rspMsg }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msgTask.status')">Status</span>
          </dt>
          <dd>
            <span>{{ msgTask.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msgTask.progressInPercent')">Progress In Percent</span>
          </dt>
          <dd>
            <span>{{ msgTask.progressInPercent }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msgTask.invalidUserIdList')">Invalid User Id List</span>
          </dt>
          <dd>
            <span>{{ msgTask.invalidUserIdList }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msgTask.forbiddenUserIdList')">Forbidden User Id List</span>
          </dt>
          <dd>
            <span>{{ msgTask.forbiddenUserIdList }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msgTask.failedUserIdList')">Failed User Id List</span>
          </dt>
          <dd>
            <span>{{ msgTask.failedUserIdList }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msgTask.readUserIdList')">Read User Id List</span>
          </dt>
          <dd>
            <span>{{ msgTask.readUserIdList }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msgTask.unreadUserIdList')">Unread User Id List</span>
          </dt>
          <dd>
            <span>{{ msgTask.unreadUserIdList }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msgTask.invalidDeptIdList')">Invalid Dept Id List</span>
          </dt>
          <dd>
            <span>{{ msgTask.invalidDeptIdList }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msgTask.withdraw')">Withdraw</span>
          </dt>
          <dd>
            <span>{{ msgTask.withdraw }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msgTask.msg')">Msg</span>
          </dt>
          <dd>
            <div v-if="msgTask.msg">
              <router-link :to="{ name: 'MsgView', params: { msgId: msgTask.msg.id } }">{{ msgTask.msg.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="msgTask.id" :to="{ name: 'MsgTaskEdit', params: { msgTaskId: msgTask.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./msg-task-details.component.ts"></script>
