<template>
  <div>
    <h2 id="page-heading" data-cy="MsgHeading">
      <span v-text="$t('articleApp.msg.home.title')" id="msg-heading">Msgs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('articleApp.msg.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MsgCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-msg">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('articleApp.msg.home.createLabel')"> Create a new Msg </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && msgs && msgs.length === 0">
      <span v-text="$t('articleApp.msg.home.notFound')">No msgs found</span>
    </div>
    <div class="table-responsive" v-if="msgs && msgs.length > 0">
      <table class="table table-striped" aria-describedby="msgs">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('deptIdList')">
              <span v-text="$t('articleApp.msg.deptIdList')">Dept Id List</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deptIdList'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('useridList')">
              <span v-text="$t('articleApp.msg.useridList')">Userid List</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'useridList'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('toAllUser')">
              <span v-text="$t('articleApp.msg.toAllUser')">To All User</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'toAllUser'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('progressInPercent')">
              <span v-text="$t('articleApp.msg.progressInPercent')">Progress In Percent</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'progressInPercent'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="$t('articleApp.msg.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('singleTitle')">
              <span v-text="$t('articleApp.msg.singleTitle')">Single Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'singleTitle'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('singleUrl')">
              <span v-text="$t('articleApp.msg.singleUrl')">Single Url</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'singleUrl'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('coverUrl')">
              <span v-text="$t('articleApp.msg.coverUrl')">Cover Url</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'coverUrl'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('numberOfShards')">
              <span v-text="$t('articleApp.msg.numberOfShards')">Number Of Shards</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'numberOfShards'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('completeSharding')">
              <span v-text="$t('articleApp.msg.completeSharding')">Complete Sharding</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'completeSharding'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('msg')">
              <span v-text="$t('articleApp.msg.msg')">Msg</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'msg'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('executeTime')">
              <span v-text="$t('articleApp.msg.executeTime')">Execute Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'executeTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('agentId')">
              <span v-text="$t('articleApp.msg.agentId')">Agent Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'agentId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type')">
              <span v-text="$t('articleApp.msg.type')">Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="$t('articleApp.msg.status')">Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="msg in msgs" :key="msg.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MsgView', params: { msgId: msg.id } }">{{ msg.id }}</router-link>
            </td>
            <td>{{ msg.deptIdList }}</td>
            <td>{{ msg.useridList }}</td>
            <td>{{ msg.toAllUser }}</td>
            <td>{{ msg.progressInPercent }}</td>
            <td>{{ msg.title }}</td>
            <td>{{ msg.singleTitle }}</td>
            <td>{{ msg.singleUrl }}</td>
            <td>{{ msg.coverUrl }}</td>
            <td>{{ msg.numberOfShards }}</td>
            <td>{{ msg.completeSharding }}</td>
            <td>{{ msg.msg }}</td>
            <td>{{ msg.executeTime ? $d(Date.parse(msg.executeTime), 'short') : '' }}</td>
            <td>{{ msg.agentId }}</td>
            <td v-text="$t('articleApp.DdMessageType.' + msg.type)">{{ msg.type }}</td>
            <td v-text="$t('articleApp.MessageStatus.' + msg.status)">{{ msg.status }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MsgView', params: { msgId: msg.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MsgEdit', params: { msgId: msg.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(msg)"
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
        ><span id="articleApp.msg.delete.question" data-cy="msgDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-msg-heading" v-text="$t('articleApp.msg.delete.question', { id: removeId })">
          Are you sure you want to delete this Msg?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-msg"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMsg()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="msgs && msgs.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./msg.component.ts"></script>
