<template>
  <div>
    <h2 id="page-heading" data-cy="DdDeptHeading">
      <span v-text="$t('articleApp.ddDept.home.title')" id="dd-dept-heading">Dd Depts</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('articleApp.ddDept.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DdDeptCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-dd-dept"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('articleApp.ddDept.home.createLabel')"> Create a new Dd Dept </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && ddDepts && ddDepts.length === 0">
      <span v-text="$t('articleApp.ddDept.home.notFound')">No ddDepts found</span>
    </div>
    <div class="table-responsive" v-if="ddDepts && ddDepts.length > 0">
      <table class="table table-striped" aria-describedby="ddDepts">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('articleApp.ddDept.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('parentId')">
              <span v-text="$t('articleApp.ddDept.parentId')">Parent Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'parentId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('article.id')">
              <span v-text="$t('articleApp.ddDept.article')">Article</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'article.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ddDept in ddDepts" :key="ddDept.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DdDeptView', params: { ddDeptId: ddDept.id } }">{{ ddDept.id }}</router-link>
            </td>
            <td>{{ ddDept.name }}</td>
            <td>{{ ddDept.parentId }}</td>
            <td>
              <div v-if="ddDept.article">
                <router-link :to="{ name: 'ArticleView', params: { articleId: ddDept.article.id } }">{{ ddDept.article.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DdDeptView', params: { ddDeptId: ddDept.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DdDeptEdit', params: { ddDeptId: ddDept.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(ddDept)"
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
        ><span id="articleApp.ddDept.delete.question" data-cy="ddDeptDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-ddDept-heading" v-text="$t('articleApp.ddDept.delete.question', { id: removeId })">
          Are you sure you want to delete this Dd Dept?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-ddDept"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDdDept()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="ddDepts && ddDepts.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./dd-dept.component.ts"></script>
