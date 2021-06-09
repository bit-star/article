<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="articleApp.article.home.createOrEditLabel"
          data-cy="ArticleCreateUpdateHeading"
          v-text="$t('articleApp.article.home.createOrEditLabel')"
        >
          Create or edit a Article
        </h2>
        <div>
          <div class="form-group" v-if="article.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="article.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.article.title')" for="article-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="article-title"
              data-cy="title"
              :class="{ valid: !$v.article.title.$invalid, invalid: $v.article.title.$invalid }"
              v-model="$v.article.title.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.article.time')" for="article-time">Time</label>
            <div class="d-flex">
              <input
                id="article-time"
                data-cy="time"
                type="datetime-local"
                class="form-control"
                name="time"
                :class="{ valid: !$v.article.time.$invalid, invalid: $v.article.time.$invalid }"
                :value="convertDateTimeFromServer($v.article.time.$model)"
                @change="updateInstantField('time', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.article.text')" for="article-text">Text</label>
            <input
              type="text"
              class="form-control"
              name="text"
              id="article-text"
              data-cy="text"
              :class="{ valid: !$v.article.text.$invalid, invalid: $v.article.text.$invalid }"
              v-model="$v.article.text.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.article.contributors')" for="article-contributors">Contributors</label>
            <input
              type="text"
              class="form-control"
              name="contributors"
              id="article-contributors"
              data-cy="contributors"
              :class="{ valid: !$v.article.contributors.$invalid, invalid: $v.article.contributors.$invalid }"
              v-model="$v.article.contributors.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.article.subType')" for="article-subType">Sub Type</label>
            <select class="form-control" id="article-subType" data-cy="subType" name="subType" v-model="article.subType">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="article.subType && subTypeOption.id === article.subType.id ? article.subType : subTypeOption"
                v-for="subTypeOption in subTypes"
                :key="subTypeOption.id"
              >
                {{ subTypeOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.article.ddUser')" for="article-ddUser">Dd User</label>
            <select class="form-control" id="article-ddUser" data-cy="ddUser" name="ddUser" v-model="article.ddUser">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="article.ddUser && ddUserOption.id === article.ddUser.id ? article.ddUser : ddUserOption"
                v-for="ddUserOption in ddUsers"
                :key="ddUserOption.id"
              >
                {{ ddUserOption.id }}
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
            :disabled="$v.article.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./article-update.component.ts"></script>
