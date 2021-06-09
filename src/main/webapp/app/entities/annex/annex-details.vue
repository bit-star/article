<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="annex">
        <h2 class="jh-entity-heading" data-cy="annexDetailsHeading">
          <span v-text="$t('articleApp.annex.detail.title')">Annex</span> {{ annex.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('articleApp.annex.spaceId')">Space Id</span>
          </dt>
          <dd>
            <span>{{ annex.spaceId }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.annex.fileId')">File Id</span>
          </dt>
          <dd>
            <span>{{ annex.fileId }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.annex.fileName')">File Name</span>
          </dt>
          <dd>
            <span>{{ annex.fileName }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.annex.fileSize')">File Size</span>
          </dt>
          <dd>
            <span>{{ annex.fileSize }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.annex.fileType')">File Type</span>
          </dt>
          <dd>
            <span>{{ annex.fileType }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.annex.storageMode')">Storage Mode</span>
          </dt>
          <dd>
            <span v-text="$t('articleApp.StorageMode.' + annex.storageMode)">{{ annex.storageMode }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.annex.url')">Url</span>
          </dt>
          <dd>
            <span>{{ annex.url }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.annex.article')">Article</span>
          </dt>
          <dd>
            <div v-if="annex.article">
              <router-link :to="{ name: 'ArticleView', params: { articleId: annex.article.id } }">{{ annex.article.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="annex.id" :to="{ name: 'AnnexEdit', params: { annexId: annex.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./annex-details.component.ts"></script>
