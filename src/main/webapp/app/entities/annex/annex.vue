<template>
  <div>
    <h2 id="page-heading" data-cy="AnnexHeading">
      <span v-text="$t('articleApp.annex.home.title')" id="annex-heading">Annexes</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('articleApp.annex.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AnnexCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-annex"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('articleApp.annex.home.createLabel')"> Create a new Annex </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && annexes && annexes.length === 0">
      <span v-text="$t('articleApp.annex.home.notFound')">No annexes found</span>
    </div>
    <div class="table-responsive" v-if="annexes && annexes.length > 0">
      <table class="table table-striped" aria-describedby="annexes">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('spaceId')">
              <span v-text="$t('articleApp.annex.spaceId')">Space Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'spaceId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileId')">
              <span v-text="$t('articleApp.annex.fileId')">File Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileName')">
              <span v-text="$t('articleApp.annex.fileName')">File Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileSize')">
              <span v-text="$t('articleApp.annex.fileSize')">File Size</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileSize'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileType')">
              <span v-text="$t('articleApp.annex.fileType')">File Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('storageMode')">
              <span v-text="$t('articleApp.annex.storageMode')">Storage Mode</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'storageMode'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('url')">
              <span v-text="$t('articleApp.annex.url')">Url</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'url'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('article.id')">
              <span v-text="$t('articleApp.annex.article')">Article</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'article.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="annex in annexes" :key="annex.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AnnexView', params: { annexId: annex.id } }">{{ annex.id }}</router-link>
            </td>
            <td>{{ annex.spaceId }}</td>
            <td>{{ annex.fileId }}</td>
            <td>{{ annex.fileName }}</td>
            <td>{{ annex.fileSize }}</td>
            <td>{{ annex.fileType }}</td>
            <td v-text="$t('articleApp.StorageMode.' + annex.storageMode)">{{ annex.storageMode }}</td>
            <td>{{ annex.url }}</td>
            <td>
              <div v-if="annex.article">
                <router-link :to="{ name: 'ArticleView', params: { articleId: annex.article.id } }">{{ annex.article.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AnnexView', params: { annexId: annex.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AnnexEdit', params: { annexId: annex.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(annex)"
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
        ><span id="articleApp.annex.delete.question" data-cy="annexDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-annex-heading" v-text="$t('articleApp.annex.delete.question', { id: removeId })">
          Are you sure you want to delete this Annex?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-annex"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAnnex()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="annexes && annexes.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./annex.component.ts"></script>
