<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ddDept">
        <h2 class="jh-entity-heading" data-cy="ddDeptDetailsHeading">
          <span v-text="$t('articleApp.ddDept.detail.title')">DdDept</span> {{ ddDept.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('articleApp.ddDept.name')">Name</span>
          </dt>
          <dd>
            <span>{{ ddDept.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddDept.parentId')">Parent Id</span>
          </dt>
          <dd>
            <span>{{ ddDept.parentId }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.ddDept.article')">Article</span>
          </dt>
          <dd>
            <div v-if="ddDept.article">
              <router-link :to="{ name: 'ArticleView', params: { articleId: ddDept.article.id } }">{{ ddDept.article.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="ddDept.id" :to="{ name: 'DdDeptEdit', params: { ddDeptId: ddDept.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./dd-dept-details.component.ts"></script>
