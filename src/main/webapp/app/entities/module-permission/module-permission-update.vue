<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="articleApp.modulePermission.home.createOrEditLabel"
          data-cy="ModulePermissionCreateUpdateHeading"
          v-text="$t('articleApp.modulePermission.home.createOrEditLabel')"
        >
          Create or edit a ModulePermission
        </h2>
        <div>
          <div class="form-group" v-if="modulePermission.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="modulePermission.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.modulePermission.name')" for="module-permission-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="module-permission-name"
              data-cy="name"
              :class="{ valid: !$v.modulePermission.name.$invalid, invalid: $v.modulePermission.name.$invalid }"
              v-model="$v.modulePermission.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.modulePermission.enable')" for="module-permission-enable"
              >Enable</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="enable"
              id="module-permission-enable"
              data-cy="enable"
              :class="{ valid: !$v.modulePermission.enable.$invalid, invalid: $v.modulePermission.enable.$invalid }"
              v-model="$v.modulePermission.enable.$model"
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
            :disabled="$v.modulePermission.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./module-permission-update.component.ts"></script>
