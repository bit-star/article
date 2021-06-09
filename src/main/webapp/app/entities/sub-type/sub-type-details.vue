<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="subType">
        <h2 class="jh-entity-heading" data-cy="subTypeDetailsHeading">
          <span v-text="$t('articleApp.subType.detail.title')">SubType</span> {{ subType.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('articleApp.subType.name')">Name</span>
          </dt>
          <dd>
            <span>{{ subType.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.subType.type')">Type</span>
          </dt>
          <dd>
            <div v-if="subType.type">
              <router-link :to="{ name: 'TypeView', params: { typeId: subType.type.id } }">{{ subType.type.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="subType.id" :to="{ name: 'SubTypeEdit', params: { subTypeId: subType.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./sub-type-details.component.ts"></script>
