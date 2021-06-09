package com.kyanite.article.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.article.IntegrationTest;
import com.kyanite.article.domain.Msg;
import com.kyanite.article.domain.enumeration.DdMessageType;
import com.kyanite.article.domain.enumeration.MessageStatus;
import com.kyanite.article.repository.MsgRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MsgResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MsgResourceIT {

    private static final String DEFAULT_DEPT_ID_LIST = "AAAAAAAAAA";
    private static final String UPDATED_DEPT_ID_LIST = "BBBBBBBBBB";

    private static final String DEFAULT_USERID_LIST = "AAAAAAAAAA";
    private static final String UPDATED_USERID_LIST = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TO_ALL_USER = false;
    private static final Boolean UPDATED_TO_ALL_USER = true;

    private static final Integer DEFAULT_PROGRESS_IN_PERCENT = 1;
    private static final Integer UPDATED_PROGRESS_IN_PERCENT = 2;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SINGLE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SINGLE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SINGLE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SINGLE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_COVER_URL = "AAAAAAAAAA";
    private static final String UPDATED_COVER_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_SHARDS = 1;
    private static final Integer UPDATED_NUMBER_OF_SHARDS = 2;

    private static final Boolean DEFAULT_COMPLETE_SHARDING = false;
    private static final Boolean UPDATED_COMPLETE_SHARDING = true;

    private static final String DEFAULT_MSG = "AAAAAAAAAA";
    private static final String UPDATED_MSG = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXECUTE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_AGENT_ID = 1L;
    private static final Long UPDATED_AGENT_ID = 2L;

    private static final DdMessageType DEFAULT_TYPE = DdMessageType.Voice;
    private static final DdMessageType UPDATED_TYPE = DdMessageType.ActionCard;

    private static final MessageStatus DEFAULT_STATUS = MessageStatus.SentSuccessfully;
    private static final MessageStatus UPDATED_STATUS = MessageStatus.Sending;

    private static final String ENTITY_API_URL = "/api/msgs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MsgRepository msgRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMsgMockMvc;

    private Msg msg;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Msg createEntity(EntityManager em) {
        Msg msg = new Msg()
            .deptIdList(DEFAULT_DEPT_ID_LIST)
            .useridList(DEFAULT_USERID_LIST)
            .toAllUser(DEFAULT_TO_ALL_USER)
            .progressInPercent(DEFAULT_PROGRESS_IN_PERCENT)
            .title(DEFAULT_TITLE)
            .singleTitle(DEFAULT_SINGLE_TITLE)
            .singleUrl(DEFAULT_SINGLE_URL)
            .coverUrl(DEFAULT_COVER_URL)
            .numberOfShards(DEFAULT_NUMBER_OF_SHARDS)
            .completeSharding(DEFAULT_COMPLETE_SHARDING)
            .msg(DEFAULT_MSG)
            .executeTime(DEFAULT_EXECUTE_TIME)
            .agentId(DEFAULT_AGENT_ID)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS);
        return msg;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Msg createUpdatedEntity(EntityManager em) {
        Msg msg = new Msg()
            .deptIdList(UPDATED_DEPT_ID_LIST)
            .useridList(UPDATED_USERID_LIST)
            .toAllUser(UPDATED_TO_ALL_USER)
            .progressInPercent(UPDATED_PROGRESS_IN_PERCENT)
            .title(UPDATED_TITLE)
            .singleTitle(UPDATED_SINGLE_TITLE)
            .singleUrl(UPDATED_SINGLE_URL)
            .coverUrl(UPDATED_COVER_URL)
            .numberOfShards(UPDATED_NUMBER_OF_SHARDS)
            .completeSharding(UPDATED_COMPLETE_SHARDING)
            .msg(UPDATED_MSG)
            .executeTime(UPDATED_EXECUTE_TIME)
            .agentId(UPDATED_AGENT_ID)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS);
        return msg;
    }

    @BeforeEach
    public void initTest() {
        msg = createEntity(em);
    }

    @Test
    @Transactional
    void createMsg() throws Exception {
        int databaseSizeBeforeCreate = msgRepository.findAll().size();
        // Create the Msg
        restMsgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(msg)))
            .andExpect(status().isCreated());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeCreate + 1);
        Msg testMsg = msgList.get(msgList.size() - 1);
        assertThat(testMsg.getDeptIdList()).isEqualTo(DEFAULT_DEPT_ID_LIST);
        assertThat(testMsg.getUseridList()).isEqualTo(DEFAULT_USERID_LIST);
        assertThat(testMsg.getToAllUser()).isEqualTo(DEFAULT_TO_ALL_USER);
        assertThat(testMsg.getProgressInPercent()).isEqualTo(DEFAULT_PROGRESS_IN_PERCENT);
        assertThat(testMsg.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMsg.getSingleTitle()).isEqualTo(DEFAULT_SINGLE_TITLE);
        assertThat(testMsg.getSingleUrl()).isEqualTo(DEFAULT_SINGLE_URL);
        assertThat(testMsg.getCoverUrl()).isEqualTo(DEFAULT_COVER_URL);
        assertThat(testMsg.getNumberOfShards()).isEqualTo(DEFAULT_NUMBER_OF_SHARDS);
        assertThat(testMsg.getCompleteSharding()).isEqualTo(DEFAULT_COMPLETE_SHARDING);
        assertThat(testMsg.getMsg()).isEqualTo(DEFAULT_MSG);
        assertThat(testMsg.getExecuteTime()).isEqualTo(DEFAULT_EXECUTE_TIME);
        assertThat(testMsg.getAgentId()).isEqualTo(DEFAULT_AGENT_ID);
        assertThat(testMsg.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMsg.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createMsgWithExistingId() throws Exception {
        // Create the Msg with an existing ID
        msg.setId(1L);

        int databaseSizeBeforeCreate = msgRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMsgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(msg)))
            .andExpect(status().isBadRequest());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMsgs() throws Exception {
        // Initialize the database
        msgRepository.saveAndFlush(msg);

        // Get all the msgList
        restMsgMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(msg.getId().intValue())))
            .andExpect(jsonPath("$.[*].deptIdList").value(hasItem(DEFAULT_DEPT_ID_LIST)))
            .andExpect(jsonPath("$.[*].useridList").value(hasItem(DEFAULT_USERID_LIST)))
            .andExpect(jsonPath("$.[*].toAllUser").value(hasItem(DEFAULT_TO_ALL_USER.booleanValue())))
            .andExpect(jsonPath("$.[*].progressInPercent").value(hasItem(DEFAULT_PROGRESS_IN_PERCENT)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].singleTitle").value(hasItem(DEFAULT_SINGLE_TITLE)))
            .andExpect(jsonPath("$.[*].singleUrl").value(hasItem(DEFAULT_SINGLE_URL)))
            .andExpect(jsonPath("$.[*].coverUrl").value(hasItem(DEFAULT_COVER_URL)))
            .andExpect(jsonPath("$.[*].numberOfShards").value(hasItem(DEFAULT_NUMBER_OF_SHARDS)))
            .andExpect(jsonPath("$.[*].completeSharding").value(hasItem(DEFAULT_COMPLETE_SHARDING.booleanValue())))
            .andExpect(jsonPath("$.[*].msg").value(hasItem(DEFAULT_MSG)))
            .andExpect(jsonPath("$.[*].executeTime").value(hasItem(DEFAULT_EXECUTE_TIME.toString())))
            .andExpect(jsonPath("$.[*].agentId").value(hasItem(DEFAULT_AGENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getMsg() throws Exception {
        // Initialize the database
        msgRepository.saveAndFlush(msg);

        // Get the msg
        restMsgMockMvc
            .perform(get(ENTITY_API_URL_ID, msg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(msg.getId().intValue()))
            .andExpect(jsonPath("$.deptIdList").value(DEFAULT_DEPT_ID_LIST))
            .andExpect(jsonPath("$.useridList").value(DEFAULT_USERID_LIST))
            .andExpect(jsonPath("$.toAllUser").value(DEFAULT_TO_ALL_USER.booleanValue()))
            .andExpect(jsonPath("$.progressInPercent").value(DEFAULT_PROGRESS_IN_PERCENT))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.singleTitle").value(DEFAULT_SINGLE_TITLE))
            .andExpect(jsonPath("$.singleUrl").value(DEFAULT_SINGLE_URL))
            .andExpect(jsonPath("$.coverUrl").value(DEFAULT_COVER_URL))
            .andExpect(jsonPath("$.numberOfShards").value(DEFAULT_NUMBER_OF_SHARDS))
            .andExpect(jsonPath("$.completeSharding").value(DEFAULT_COMPLETE_SHARDING.booleanValue()))
            .andExpect(jsonPath("$.msg").value(DEFAULT_MSG))
            .andExpect(jsonPath("$.executeTime").value(DEFAULT_EXECUTE_TIME.toString()))
            .andExpect(jsonPath("$.agentId").value(DEFAULT_AGENT_ID.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMsg() throws Exception {
        // Get the msg
        restMsgMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMsg() throws Exception {
        // Initialize the database
        msgRepository.saveAndFlush(msg);

        int databaseSizeBeforeUpdate = msgRepository.findAll().size();

        // Update the msg
        Msg updatedMsg = msgRepository.findById(msg.getId()).get();
        // Disconnect from session so that the updates on updatedMsg are not directly saved in db
        em.detach(updatedMsg);
        updatedMsg
            .deptIdList(UPDATED_DEPT_ID_LIST)
            .useridList(UPDATED_USERID_LIST)
            .toAllUser(UPDATED_TO_ALL_USER)
            .progressInPercent(UPDATED_PROGRESS_IN_PERCENT)
            .title(UPDATED_TITLE)
            .singleTitle(UPDATED_SINGLE_TITLE)
            .singleUrl(UPDATED_SINGLE_URL)
            .coverUrl(UPDATED_COVER_URL)
            .numberOfShards(UPDATED_NUMBER_OF_SHARDS)
            .completeSharding(UPDATED_COMPLETE_SHARDING)
            .msg(UPDATED_MSG)
            .executeTime(UPDATED_EXECUTE_TIME)
            .agentId(UPDATED_AGENT_ID)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS);

        restMsgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMsg.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMsg))
            )
            .andExpect(status().isOk());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
        Msg testMsg = msgList.get(msgList.size() - 1);
        assertThat(testMsg.getDeptIdList()).isEqualTo(UPDATED_DEPT_ID_LIST);
        assertThat(testMsg.getUseridList()).isEqualTo(UPDATED_USERID_LIST);
        assertThat(testMsg.getToAllUser()).isEqualTo(UPDATED_TO_ALL_USER);
        assertThat(testMsg.getProgressInPercent()).isEqualTo(UPDATED_PROGRESS_IN_PERCENT);
        assertThat(testMsg.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMsg.getSingleTitle()).isEqualTo(UPDATED_SINGLE_TITLE);
        assertThat(testMsg.getSingleUrl()).isEqualTo(UPDATED_SINGLE_URL);
        assertThat(testMsg.getCoverUrl()).isEqualTo(UPDATED_COVER_URL);
        assertThat(testMsg.getNumberOfShards()).isEqualTo(UPDATED_NUMBER_OF_SHARDS);
        assertThat(testMsg.getCompleteSharding()).isEqualTo(UPDATED_COMPLETE_SHARDING);
        assertThat(testMsg.getMsg()).isEqualTo(UPDATED_MSG);
        assertThat(testMsg.getExecuteTime()).isEqualTo(UPDATED_EXECUTE_TIME);
        assertThat(testMsg.getAgentId()).isEqualTo(UPDATED_AGENT_ID);
        assertThat(testMsg.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMsg.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingMsg() throws Exception {
        int databaseSizeBeforeUpdate = msgRepository.findAll().size();
        msg.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMsgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, msg.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(msg))
            )
            .andExpect(status().isBadRequest());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMsg() throws Exception {
        int databaseSizeBeforeUpdate = msgRepository.findAll().size();
        msg.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMsgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(msg))
            )
            .andExpect(status().isBadRequest());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMsg() throws Exception {
        int databaseSizeBeforeUpdate = msgRepository.findAll().size();
        msg.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMsgMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(msg)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMsgWithPatch() throws Exception {
        // Initialize the database
        msgRepository.saveAndFlush(msg);

        int databaseSizeBeforeUpdate = msgRepository.findAll().size();

        // Update the msg using partial update
        Msg partialUpdatedMsg = new Msg();
        partialUpdatedMsg.setId(msg.getId());

        partialUpdatedMsg
            .deptIdList(UPDATED_DEPT_ID_LIST)
            .progressInPercent(UPDATED_PROGRESS_IN_PERCENT)
            .title(UPDATED_TITLE)
            .singleTitle(UPDATED_SINGLE_TITLE)
            .coverUrl(UPDATED_COVER_URL)
            .executeTime(UPDATED_EXECUTE_TIME)
            .agentId(UPDATED_AGENT_ID)
            .type(UPDATED_TYPE);

        restMsgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMsg.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMsg))
            )
            .andExpect(status().isOk());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
        Msg testMsg = msgList.get(msgList.size() - 1);
        assertThat(testMsg.getDeptIdList()).isEqualTo(UPDATED_DEPT_ID_LIST);
        assertThat(testMsg.getUseridList()).isEqualTo(DEFAULT_USERID_LIST);
        assertThat(testMsg.getToAllUser()).isEqualTo(DEFAULT_TO_ALL_USER);
        assertThat(testMsg.getProgressInPercent()).isEqualTo(UPDATED_PROGRESS_IN_PERCENT);
        assertThat(testMsg.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMsg.getSingleTitle()).isEqualTo(UPDATED_SINGLE_TITLE);
        assertThat(testMsg.getSingleUrl()).isEqualTo(DEFAULT_SINGLE_URL);
        assertThat(testMsg.getCoverUrl()).isEqualTo(UPDATED_COVER_URL);
        assertThat(testMsg.getNumberOfShards()).isEqualTo(DEFAULT_NUMBER_OF_SHARDS);
        assertThat(testMsg.getCompleteSharding()).isEqualTo(DEFAULT_COMPLETE_SHARDING);
        assertThat(testMsg.getMsg()).isEqualTo(DEFAULT_MSG);
        assertThat(testMsg.getExecuteTime()).isEqualTo(UPDATED_EXECUTE_TIME);
        assertThat(testMsg.getAgentId()).isEqualTo(UPDATED_AGENT_ID);
        assertThat(testMsg.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMsg.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateMsgWithPatch() throws Exception {
        // Initialize the database
        msgRepository.saveAndFlush(msg);

        int databaseSizeBeforeUpdate = msgRepository.findAll().size();

        // Update the msg using partial update
        Msg partialUpdatedMsg = new Msg();
        partialUpdatedMsg.setId(msg.getId());

        partialUpdatedMsg
            .deptIdList(UPDATED_DEPT_ID_LIST)
            .useridList(UPDATED_USERID_LIST)
            .toAllUser(UPDATED_TO_ALL_USER)
            .progressInPercent(UPDATED_PROGRESS_IN_PERCENT)
            .title(UPDATED_TITLE)
            .singleTitle(UPDATED_SINGLE_TITLE)
            .singleUrl(UPDATED_SINGLE_URL)
            .coverUrl(UPDATED_COVER_URL)
            .numberOfShards(UPDATED_NUMBER_OF_SHARDS)
            .completeSharding(UPDATED_COMPLETE_SHARDING)
            .msg(UPDATED_MSG)
            .executeTime(UPDATED_EXECUTE_TIME)
            .agentId(UPDATED_AGENT_ID)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS);

        restMsgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMsg.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMsg))
            )
            .andExpect(status().isOk());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
        Msg testMsg = msgList.get(msgList.size() - 1);
        assertThat(testMsg.getDeptIdList()).isEqualTo(UPDATED_DEPT_ID_LIST);
        assertThat(testMsg.getUseridList()).isEqualTo(UPDATED_USERID_LIST);
        assertThat(testMsg.getToAllUser()).isEqualTo(UPDATED_TO_ALL_USER);
        assertThat(testMsg.getProgressInPercent()).isEqualTo(UPDATED_PROGRESS_IN_PERCENT);
        assertThat(testMsg.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMsg.getSingleTitle()).isEqualTo(UPDATED_SINGLE_TITLE);
        assertThat(testMsg.getSingleUrl()).isEqualTo(UPDATED_SINGLE_URL);
        assertThat(testMsg.getCoverUrl()).isEqualTo(UPDATED_COVER_URL);
        assertThat(testMsg.getNumberOfShards()).isEqualTo(UPDATED_NUMBER_OF_SHARDS);
        assertThat(testMsg.getCompleteSharding()).isEqualTo(UPDATED_COMPLETE_SHARDING);
        assertThat(testMsg.getMsg()).isEqualTo(UPDATED_MSG);
        assertThat(testMsg.getExecuteTime()).isEqualTo(UPDATED_EXECUTE_TIME);
        assertThat(testMsg.getAgentId()).isEqualTo(UPDATED_AGENT_ID);
        assertThat(testMsg.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMsg.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingMsg() throws Exception {
        int databaseSizeBeforeUpdate = msgRepository.findAll().size();
        msg.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMsgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, msg.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(msg))
            )
            .andExpect(status().isBadRequest());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMsg() throws Exception {
        int databaseSizeBeforeUpdate = msgRepository.findAll().size();
        msg.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMsgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(msg))
            )
            .andExpect(status().isBadRequest());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMsg() throws Exception {
        int databaseSizeBeforeUpdate = msgRepository.findAll().size();
        msg.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMsgMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(msg)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMsg() throws Exception {
        // Initialize the database
        msgRepository.saveAndFlush(msg);

        int databaseSizeBeforeDelete = msgRepository.findAll().size();

        // Delete the msg
        restMsgMockMvc.perform(delete(ENTITY_API_URL_ID, msg.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
