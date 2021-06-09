<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="articleApp.type.home.createOrEditLabel"
          data-cy="TypeCreateUpdateHeading"
          v-text="$t('articleApp.type.home.createOrEditLabel')"
        >
          Create or edit a Type
        </h2>
        <div>
          <div class="form-group" v-if="type.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="type.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.type.name')" for="type-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="type-name"
              data-cy="name"
              :class="{ valid: !$v.type.name.$invalid, invalid: $v.type.name.$invalid }"
              v-model="$v.type.name.$model"
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
            :disabled="$v.type.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./type-update.component.ts"></script>
