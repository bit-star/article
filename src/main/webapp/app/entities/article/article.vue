<template>
  <div>
    <h2 id="page-heading" data-cy="ArticleHeading">
      <span v-text="$t('articleApp.article.home.title')" id="article-heading">Articles</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('articleApp.article.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ArticleCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-article"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('articleApp.article.home.createLabel')"> Create a new Article </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && articles && articles.length === 0">
      <span v-text="$t('articleApp.article.home.notFound')">No articles found</span>
    </div>
    <div class="table-responsive" v-if="articles && articles.length > 0">
      <table class="table table-striped" aria-describedby="articles">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="$t('articleApp.article.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('time')">
              <span v-text="$t('articleApp.article.time')">Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'time'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('text')">
              <span v-text="$t('articleApp.article.text')">Text</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'text'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contributors')">
              <span v-text="$t('articleApp.article.contributors')">Contributors</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contributors'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('subType.id')">
              <span v-text="$t('articleApp.article.subType')">Sub Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subType.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ddUser.id')">
              <span v-text="$t('articleApp.article.ddUser')">Dd User</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ddUser.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="article in articles" :key="article.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ArticleView', params: { articleId: article.id } }">{{ article.id }}</router-link>
            </td>
            <td>{{ article.title }}</td>
            <td>{{ article.time ? $d(Date.parse(article.time), 'short') : '' }}</td>
            <td>{{ article.text }}</td>
            <td>{{ article.contributors }}</td>
            <td>
              <div v-if="article.subType">
                <router-link :to="{ name: 'SubTypeView', params: { subTypeId: article.subType.id } }">{{ article.subType.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="article.ddUser">
                <router-link :to="{ name: 'DdUserView', params: { ddUserId: article.ddUser.id } }">{{ article.ddUser.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ArticleView', params: { articleId: article.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ArticleEdit', params: { articleId: article.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(article)"
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
        ><span id="articleApp.article.delete.question" data-cy="articleDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-article-heading" v-text="$t('articleApp.article.delete.question', { id: removeId })">
          Are you sure you want to delete this Article?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-article"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeArticle()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="articles && articles.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./article.component.ts"></script>
