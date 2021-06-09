<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="articleApp.banner.home.createOrEditLabel"
          data-cy="BannerCreateUpdateHeading"
          v-text="$t('articleApp.banner.home.createOrEditLabel')"
        >
          Create or edit a Banner
        </h2>
        <div>
          <div class="form-group" v-if="banner.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="banner.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.banner.title')" for="banner-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="banner-title"
              data-cy="title"
              :class="{ valid: !$v.banner.title.$invalid, invalid: $v.banner.title.$invalid }"
              v-model="$v.banner.title.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.banner.coverUrl')" for="banner-coverUrl">Cover Url</label>
            <input
              type="text"
              class="form-control"
              name="coverUrl"
              id="banner-coverUrl"
              data-cy="coverUrl"
              :class="{ valid: !$v.banner.coverUrl.$invalid, invalid: $v.banner.coverUrl.$invalid }"
              v-model="$v.banner.coverUrl.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.banner.name')" for="banner-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="banner-name"
              data-cy="name"
              :class="{ valid: !$v.banner.name.$invalid, invalid: $v.banner.name.$invalid }"
              v-model="$v.banner.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.banner.brand')" for="banner-brand">Brand</label>
            <input
              type="text"
              class="form-control"
              name="brand"
              id="banner-brand"
              data-cy="brand"
              :class="{ valid: !$v.banner.brand.$invalid, invalid: $v.banner.brand.$invalid }"
              v-model="$v.banner.brand.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.banner.model')" for="banner-model">Model</label>
            <input
              type="text"
              class="form-control"
              name="model"
              id="banner-model"
              data-cy="model"
              :class="{ valid: !$v.banner.model.$invalid, invalid: $v.banner.model.$invalid }"
              v-model="$v.banner.model.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.banner.specifications')" for="banner-specifications"
              >Specifications</label
            >
            <input
              type="text"
              class="form-control"
              name="specifications"
              id="banner-specifications"
              data-cy="specifications"
              :class="{ valid: !$v.banner.specifications.$invalid, invalid: $v.banner.specifications.$invalid }"
              v-model="$v.banner.specifications.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.banner.isExport')" for="banner-isExport">Is Export</label>
            <input
              type="checkbox"
              class="form-check"
              name="isExport"
              id="banner-isExport"
              data-cy="isExport"
              :class="{ valid: !$v.banner.isExport.$invalid, invalid: $v.banner.isExport.$invalid }"
              v-model="$v.banner.isExport.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.banner.supplier')" for="banner-supplier">Supplier</label>
            <input
              type="text"
              class="form-control"
              name="supplier"
              id="banner-supplier"
              data-cy="supplier"
              :class="{ valid: !$v.banner.supplier.$invalid, invalid: $v.banner.supplier.$invalid }"
              v-model="$v.banner.supplier.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.banner.supplierAddress')" for="banner-supplierAddress"
              >Supplier Address</label
            >
            <input
              type="text"
              class="form-control"
              name="supplierAddress"
              id="banner-supplierAddress"
              data-cy="supplierAddress"
              :class="{ valid: !$v.banner.supplierAddress.$invalid, invalid: $v.banner.supplierAddress.$invalid }"
              v-model="$v.banner.supplierAddress.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.banner.supplierCapacity')" for="banner-supplierCapacity"
              >Supplier Capacity</label
            >
            <input
              type="text"
              class="form-control"
              name="supplierCapacity"
              id="banner-supplierCapacity"
              data-cy="supplierCapacity"
              :class="{ valid: !$v.banner.supplierCapacity.$invalid, invalid: $v.banner.supplierCapacity.$invalid }"
              v-model="$v.banner.supplierCapacity.$model"
            />
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
            :disabled="$v.banner.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./banner-update.component.ts"></script>
