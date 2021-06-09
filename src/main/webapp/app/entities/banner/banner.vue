<template>
  <div>
    <h2 id="page-heading" data-cy="BannerHeading">
      <span v-text="$t('articleApp.banner.home.title')" id="banner-heading">Banners</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('articleApp.banner.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'BannerCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-banner"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('articleApp.banner.home.createLabel')"> Create a new Banner </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && banners && banners.length === 0">
      <span v-text="$t('articleApp.banner.home.notFound')">No banners found</span>
    </div>
    <div class="table-responsive" v-if="banners && banners.length > 0">
      <table class="table table-striped" aria-describedby="banners">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="$t('articleApp.banner.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('coverUrl')">
              <span v-text="$t('articleApp.banner.coverUrl')">Cover Url</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'coverUrl'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('articleApp.banner.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('brand')">
              <span v-text="$t('articleApp.banner.brand')">Brand</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'brand'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('model')">
              <span v-text="$t('articleApp.banner.model')">Model</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'model'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('specifications')">
              <span v-text="$t('articleApp.banner.specifications')">Specifications</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'specifications'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isExport')">
              <span v-text="$t('articleApp.banner.isExport')">Is Export</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isExport'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('supplier')">
              <span v-text="$t('articleApp.banner.supplier')">Supplier</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'supplier'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('supplierAddress')">
              <span v-text="$t('articleApp.banner.supplierAddress')">Supplier Address</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'supplierAddress'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('supplierCapacity')">
              <span v-text="$t('articleApp.banner.supplierCapacity')">Supplier Capacity</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'supplierCapacity'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="banner in banners" :key="banner.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'BannerView', params: { bannerId: banner.id } }">{{ banner.id }}</router-link>
            </td>
            <td>{{ banner.title }}</td>
            <td>{{ banner.coverUrl }}</td>
            <td>{{ banner.name }}</td>
            <td>{{ banner.brand }}</td>
            <td>{{ banner.model }}</td>
            <td>{{ banner.specifications }}</td>
            <td>{{ banner.isExport }}</td>
            <td>{{ banner.supplier }}</td>
            <td>{{ banner.supplierAddress }}</td>
            <td>{{ banner.supplierCapacity }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'BannerView', params: { bannerId: banner.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'BannerEdit', params: { bannerId: banner.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(banner)"
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
        ><span id="articleApp.banner.delete.question" data-cy="bannerDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-banner-heading" v-text="$t('articleApp.banner.delete.question', { id: removeId })">
          Are you sure you want to delete this Banner?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-banner"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeBanner()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="banners && banners.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./banner.component.ts"></script>
