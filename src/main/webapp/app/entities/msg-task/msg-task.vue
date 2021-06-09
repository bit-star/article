<template>
  <div>
    <h2 id="page-heading" data-cy="MsgTaskHeading">
      <span v-text="$t('articleApp.msgTask.home.title')" id="msg-task-heading">Msg Tasks</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('articleApp.msgTask.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MsgTaskCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-msg-task"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('articleApp.msgTask.home.createLabel')"> Create a new Msg Task </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && msgTasks && msgTasks.length === 0">
      <span v-text="$t('articleApp.msgTask.home.notFound')">No msgTasks found</span>
    </div>
    <div class="table-responsive" v-if="msgTasks && msgTasks.length > 0">
      <table class="table table-striped" aria-describedby="msgTasks">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('useridList')">
              <span v-text="$t('articleApp.msgTask.useridList')">Userid List</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'useridList'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('taskId')">
              <span v-text="$t('articleApp.msgTask.taskId')">Task Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'taskId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('rspMsg')">
              <span v-text="$t('articleApp.msgTask.rspMsg')">Rsp Msg</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rspMsg'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="$t('articleApp.msgTask.status')">Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('progressInPercent')">
              <span v-text="$t('articleApp.msgTask.progressInPercent')">Progress In Percent</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'progressInPercent'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('invalidUserIdList')">
              <span v-text="$t('articleApp.msgTask.invalidUserIdList')">Invalid User Id List</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'invalidUserIdList'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('forbiddenUserIdList')">
              <span v-text="$t('articleApp.msgTask.forbiddenUserIdList')">Forbidden User Id List</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'forbiddenUserIdList'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('failedUserIdList')">
              <span v-text="$t('articleApp.msgTask.failedUserIdList')">Failed User Id List</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'failedUserIdList'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('readUserIdList')">
              <span v-text="$t('articleApp.msgTask.readUserIdList')">Read User Id List</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'readUserIdList'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('unreadUserIdList')">
              <span v-text="$t('articleApp.msgTask.unreadUserIdList')">Unread User Id List</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'unreadUserIdList'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('invalidDeptIdList')">
              <span v-text="$t('articleApp.msgTask.invalidDeptIdList')">Invalid Dept Id List</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'invalidDeptIdList'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('withdraw')">
              <span v-text="$t('articleApp.msgTask.withdraw')">Withdraw</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'withdraw'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('msg.id')">
              <span v-text="$t('articleApp.msgTask.msg')">Msg</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'msg.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="msgTask in msgTasks" :key="msgTask.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MsgTaskView', params: { msgTaskId: msgTask.id } }">{{ msgTask.id }}</router-link>
            </td>
            <td>{{ msgTask.useridList }}</td>
            <td>{{ msgTask.taskId }}</td>
            <td>{{ msgTask.rspMsg }}</td>
            <td>{{ msgTask.status }}</td>
            <td>{{ msgTask.progressInPercent }}</td>
            <td>{{ msgTask.invalidUserIdList }}</td>
            <td>{{ msgTask.forbiddenUserIdList }}</td>
            <td>{{ msgTask.failedUserIdList }}</td>
            <td>{{ msgTask.readUserIdList }}</td>
            <td>{{ msgTask.unreadUserIdList }}</td>
            <td>{{ msgTask.invalidDeptIdList }}</td>
            <td>{{ msgTask.withdraw }}</td>
            <td>
              <div v-if="msgTask.msg">
                <router-link :to="{ name: 'MsgView', params: { msgId: msgTask.msg.id } }">{{ msgTask.msg.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MsgTaskView', params: { msgTaskId: msgTask.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MsgTaskEdit', params: { msgTaskId: msgTask.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(msgTask)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="articleApp.msgTask.delete.question" data-cy="msgTaskDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-msgTask-heading" v-text="$t('articleApp.msgTask.delete.question', { id: removeId })">
          Are you sure you want to delete this Msg Task?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-msgTask"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMsgTask()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="msgTasks && msgTasks.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./msg-task.component.ts"></script>
