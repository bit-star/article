<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="modulePermission">
        <h2 class="jh-entity-heading" data-cy="modulePermissionDetailsHeading">
          <span v-text="$t('articleApp.modulePermission.detail.title')">ModulePermission</span> {{ modulePermission.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('articleApp.modulePermission.name')">Name</span>
          </dt>
          <dd>
            <span>{{ modulePermission.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.modulePermission.enable')">Enable</span>
          </dt>
          <dd>
            <span>{{ modulePermission.enable }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="modulePermission.id"
          :to="{ name: 'ModulePermissionEdit', params: { modulePermissionId: modulePermission.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./module-permission-details.component.ts"></script>
