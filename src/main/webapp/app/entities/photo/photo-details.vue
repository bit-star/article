<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="photo">
        <h2 class="jh-entity-heading" data-cy="photoDetailsHeading">
          <span v-text="$t('articleApp.photo.detail.title')">Photo</span> {{ photo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('articleApp.photo.url')">Url</span>
          </dt>
          <dd>
            <span>{{ photo.url }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.photo.banner')">Banner</span>
          </dt>
          <dd>
            <div v-if="photo.banner">
              <router-link :to="{ name: 'BannerView', params: { bannerId: photo.banner.id } }">{{ photo.banner.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="photo.id" :to="{ name: 'PhotoEdit', params: { photoId: photo.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./photo-details.component.ts"></script>
