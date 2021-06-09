<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="banner">
        <h2 class="jh-entity-heading" data-cy="bannerDetailsHeading">
          <span v-text="$t('articleApp.banner.detail.title')">Banner</span> {{ banner.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('articleApp.banner.title')">Title</span>
          </dt>
          <dd>
            <span>{{ banner.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.banner.coverUrl')">Cover Url</span>
          </dt>
          <dd>
            <span>{{ banner.coverUrl }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.banner.name')">Name</span>
          </dt>
          <dd>
            <span>{{ banner.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.banner.brand')">Brand</span>
          </dt>
          <dd>
            <span>{{ banner.brand }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.banner.model')">Model</span>
          </dt>
          <dd>
            <span>{{ banner.model }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.banner.specifications')">Specifications</span>
          </dt>
          <dd>
            <span>{{ banner.specifications }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.banner.isExport')">Is Export</span>
          </dt>
          <dd>
            <span>{{ banner.isExport }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.banner.supplier')">Supplier</span>
          </dt>
          <dd>
            <span>{{ banner.supplier }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.banner.supplierAddress')">Supplier Address</span>
          </dt>
          <dd>
            <span>{{ banner.supplierAddress }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.banner.supplierCapacity')">Supplier Capacity</span>
          </dt>
          <dd>
            <span>{{ banner.supplierCapacity }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="banner.id" :to="{ name: 'BannerEdit', params: { bannerId: banner.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./banner-details.component.ts"></script>
