<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="article">
        <h2 class="jh-entity-heading" data-cy="articleDetailsHeading">
          <span v-text="$t('articleApp.article.detail.title')">Article</span> {{ article.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('articleApp.article.title')">Title</span>
          </dt>
          <dd>
            <span>{{ article.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.article.time')">Time</span>
          </dt>
          <dd>
            <span v-if="article.time">{{ $d(Date.parse(article.time), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.article.text')">Text</span>
          </dt>
          <dd>
            <span>{{ article.text }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.article.contributors')">Contributors</span>
          </dt>
          <dd>
            <span>{{ article.contributors }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.article.subType')">Sub Type</span>
          </dt>
          <dd>
            <div v-if="article.subType">
              <router-link :to="{ name: 'SubTypeView', params: { subTypeId: article.subType.id } }">{{ article.subType.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('articleApp.article.ddUser')">Dd User</span>
          </dt>
          <dd>
            <div v-if="article.ddUser">
              <router-link :to="{ name: 'DdUserView', params: { ddUserId: article.ddUser.id } }">{{ article.ddUser.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="article.id" :to="{ name: 'ArticleEdit', params: { articleId: article.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./article-details.component.ts"></script>
