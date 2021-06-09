<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="articleApp.annex.home.createOrEditLabel"
          data-cy="AnnexCreateUpdateHeading"
          v-text="$t('articleApp.annex.home.createOrEditLabel')"
        >
          Create or edit a Annex
        </h2>
        <div>
          <div class="form-group" v-if="annex.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="annex.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.annex.spaceId')" for="annex-spaceId">Space Id</label>
            <input
              type="text"
              class="form-control"
              name="spaceId"
              id="annex-spaceId"
              data-cy="spaceId"
              :class="{ valid: !$v.annex.spaceId.$invalid, invalid: $v.annex.spaceId.$invalid }"
              v-model="$v.annex.spaceId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.annex.fileId')" for="annex-fileId">File Id</label>
            <input
              type="text"
              class="form-control"
              name="fileId"
              id="annex-fileId"
              data-cy="fileId"
              :class="{ valid: !$v.annex.fileId.$invalid, invalid: $v.annex.fileId.$invalid }"
              v-model="$v.annex.fileId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.annex.fileName')" for="annex-fileName">File Name</label>
            <input
              type="text"
              class="form-control"
              name="fileName"
              id="annex-fileName"
              data-cy="fileName"
              :class="{ valid: !$v.annex.fileName.$invalid, invalid: $v.annex.fileName.$invalid }"
              v-model="$v.annex.fileName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.annex.fileSize')" for="annex-fileSize">File Size</label>
            <input
              type="text"
              class="form-control"
              name="fileSize"
              id="annex-fileSize"
              data-cy="fileSize"
              :class="{ valid: !$v.annex.fileSize.$invalid, invalid: $v.annex.fileSize.$invalid }"
              v-model="$v.annex.fileSize.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.annex.fileType')" for="annex-fileType">File Type</label>
            <input
              type="text"
              class="form-control"
              name="fileType"
              id="annex-fileType"
              data-cy="fileType"
              :class="{ valid: !$v.annex.fileType.$invalid, invalid: $v.annex.fileType.$invalid }"
              v-model="$v.annex.fileType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.annex.storageMode')" for="annex-storageMode">Storage Mode</label>
            <select
              class="form-control"
              name="storageMode"
              :class="{ valid: !$v.annex.storageMode.$invalid, invalid: $v.annex.storageMode.$invalid }"
              v-model="$v.annex.storageMode.$model"
              id="annex-storageMode"
              data-cy="storageMode"
            >
              <option value="OSS" v-bind:label="$t('articleApp.StorageMode.OSS')">OSS</option>
              <option value="Dingtalk" v-bind:label="$t('articleApp.StorageMode.Dingtalk')">Dingtalk</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.annex.url')" for="annex-url">Url</label>
            <input
              type="text"
              class="form-control"
              name="url"
              id="annex-url"
              data-cy="url"
              :class="{ valid: !$v.annex.url.$invalid, invalid: $v.annex.url.$invalid }"
              v-model="$v.annex.url.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.annex.article')" for="annex-article">Article</label>
            <select class="form-control" id="annex-article" data-cy="article" name="article" v-model="annex.article">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="annex.article && articleOption.id === annex.article.id ? annex.article : articleOption"
                v-for="articleOption in articles"
                :key="articleOption.id"
              >
                {{ articleOption.id }}
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
            :disabled="$v.annex.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./annex-update.component.ts"></script>
