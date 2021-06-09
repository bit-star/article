<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="articleApp.ddDept.home.createOrEditLabel"
          data-cy="DdDeptCreateUpdateHeading"
          v-text="$t('articleApp.ddDept.home.createOrEditLabel')"
        >
          Create or edit a DdDept
        </h2>
        <div>
          <div class="form-group" v-if="ddDept.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ddDept.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddDept.name')" for="dd-dept-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="dd-dept-name"
              data-cy="name"
              :class="{ valid: !$v.ddDept.name.$invalid, invalid: $v.ddDept.name.$invalid }"
              v-model="$v.ddDept.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddDept.parentId')" for="dd-dept-parentId">Parent Id</label>
            <input
              type="text"
              class="form-control"
              name="parentId"
              id="dd-dept-parentId"
              data-cy="parentId"
              :class="{ valid: !$v.ddDept.parentId.$invalid, invalid: $v.ddDept.parentId.$invalid }"
              v-model="$v.ddDept.parentId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.ddDept.article')" for="dd-dept-article">Article</label>
            <select class="form-control" id="dd-dept-article" data-cy="article" name="article" v-model="ddDept.article">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="ddDept.article && articleOption.id === ddDept.article.id ? ddDept.article : articleOption"
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
            :disabled="$v.ddDept.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./dd-dept-update.component.ts"></script>
