<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="articleApp.subType.home.createOrEditLabel"
          data-cy="SubTypeCreateUpdateHeading"
          v-text="$t('articleApp.subType.home.createOrEditLabel')"
        >
          Create or edit a SubType
        </h2>
        <div>
          <div class="form-group" v-if="subType.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="subType.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.subType.name')" for="sub-type-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="sub-type-name"
              data-cy="name"
              :class="{ valid: !$v.subType.name.$invalid, invalid: $v.subType.name.$invalid }"
              v-model="$v.subType.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('articleApp.subType.type')" for="sub-type-type">Type</label>
            <select class="form-control" id="sub-type-type" data-cy="type" name="type" v-model="subType.type">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="subType.type && typeOption.id === subType.type.id ? subType.type : typeOption"
                v-for="typeOption in types"
                :key="typeOption.id"
              >
                {{ typeOption.id }}
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
            :disabled="$v.subType.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./sub-type-update.component.ts"></script>
