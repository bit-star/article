<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="msg">
        <h2 class="jh-entity-heading" data-cy="msgDetailsHeading">
          <span v-text="$t('articleApp.msg.detail.title')">Msg</span> {{ msg.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('articleApp.msg.deptIdList')">Dept Id List</span>
          </dt>
          <dd>
            <span>{{ msg.deptIdList }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.useridList')">Userid List</span>
          </dt>
          <dd>
            <span>{{ msg.useridList }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.toAllUser')">To All User</span>
          </dt>
          <dd>
            <span>{{ msg.toAllUser }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.progressInPercent')">Progress In Percent</span>
          </dt>
          <dd>
            <span>{{ msg.progressInPercent }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.title')">Title</span>
          </dt>
          <dd>
            <span>{{ msg.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.singleTitle')">Single Title</span>
          </dt>
          <dd>
            <span>{{ msg.singleTitle }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.singleUrl')">Single Url</span>
          </dt>
          <dd>
            <span>{{ msg.singleUrl }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.coverUrl')">Cover Url</span>
          </dt>
          <dd>
            <span>{{ msg.coverUrl }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.numberOfShards')">Number Of Shards</span>
          </dt>
          <dd>
            <span>{{ msg.numberOfShards }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.completeSharding')">Complete Sharding</span>
          </dt>
          <dd>
            <span>{{ msg.completeSharding }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.msg')">Msg</span>
          </dt>
          <dd>
            <span>{{ msg.msg }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.executeTime')">Execute Time</span>
          </dt>
          <dd>
            <span v-if="msg.executeTime">{{ $d(Date.parse(msg.executeTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.agentId')">Agent Id</span>
          </dt>
          <dd>
            <span>{{ msg.agentId }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('articleApp.DdMessageType.' + msg.type)">{{ msg.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('articleApp.msg.status')">Status</span>
          </dt>
          <dd>
            <span v-text="$t('articleApp.MessageStatus.' + msg.status)">{{ msg.status }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="msg.id" :to="{ name: 'MsgEdit', params: { msgId: msg.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./msg-details.component.ts"></script>
