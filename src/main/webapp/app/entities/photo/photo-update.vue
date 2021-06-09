<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="articleApp.photo.home.createOrEditLabel"
          data-cy="PhotoCreateUpdateHeading"
          v-text="$t('articleApp.photo.home.createOrEditLabel')"
        >
          Create or edit a Photo
        </h2>
        <div>
          <div class="form-group" v-if="photo.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="photo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.photo.url')" for="photo-url">Url</label>
            <input
              type="text"
              class="form-control"
              name="url"
              id="photo-url"
              data-cy="url"
              :class="{ valid: !$v.photo.url.$invalid, invalid: $v.photo.url.$invalid }"
              v-model="$v.photo.url.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.photo.banner')" for="photo-banner">Banner</label>
            <select class="form-control" id="photo-banner" data-cy="banner" name="banner" v-model="photo.banner">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="photo.banner && bannerOption.id === photo.banner.id ? photo.banner : bannerOption"
                v-for="bannerOption in banners"
                :key="bannerOption.id"
              >
                {{ bannerOption.id }}
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
            :disabled="$v.photo.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./photo-update.component.ts"></script>
