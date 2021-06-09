package com.kyanite.article.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.article.IntegrationTest;
import com.kyanite.article.domain.MsgTask;
import com.kyanite.article.repository.MsgTaskRepository;
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
 * Integration tests for the {@link MsgTaskResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MsgTaskResourceIT {

    private static final String DEFAULT_USERID_LIST = "AAAAAAAAAA";
    private static final String UPDATED_USERID_LIST = "BBBBBBBBBB";

    private static final Long DEFAULT_TASK_ID = 1L;
    private static final Long UPDATED_TASK_ID = 2L;

    private static final String DEFAULT_RSP_MSG = "AAAAAAAAAA";
    private static final String UPDATED_RSP_MSG = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Integer DEFAULT_PROGRESS_IN_PERCENT = 1;
    private static final Integer UPDATED_PROGRESS_IN_PERCENT = 2;

    private static final String DEFAULT_INVALID_USER_ID_LIST = "AAAAAAAAAA";
    private static final String UPDATED_INVALID_USER_ID_LIST = "BBBBBBBBBB";

    private static final String DEFAULT_FORBIDDEN_USER_ID_LIST = "AAAAAAAAAA";
    private static final String UPDATED_FORBIDDEN_USER_ID_LIST = "BBBBBBBBBB";

    private static final String DEFAULT_FAILED_USER_ID_LIST = "AAAAAAAAAA";
    private static final String UPDATED_FAILED_USER_ID_LIST = "BBBBBBBBBB";

    private static final String DEFAULT_READ_USER_ID_LIST = "AAAAAAAAAA";
    private static final String UPDATED_READ_USER_ID_LIST = "BBBBBBBBBB";

    private static final String DEFAULT_UNREAD_USER_ID_LIST = "AAAAAAAAAA";
    private static final String UPDATED_UNREAD_USER_ID_LIST = "BBBBBBBBBB";

    private static final String DEFAULT_INVALID_DEPT_ID_LIST = "AAAAAAAAAA";
    private static final String UPDATED_INVALID_DEPT_ID_LIST = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WITHDRAW = false;
    private static final Boolean UPDATED_WITHDRAW = true;

    private static final String ENTITY_API_URL = "/api/msg-tasks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MsgTaskRepository msgTaskRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMsgTaskMockMvc;

    private MsgTask msgTask;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MsgTask createEntity(EntityManager em) {
        MsgTask msgTask = new MsgTask()
            .useridList(DEFAULT_USERID_LIST)
            .taskId(DEFAULT_TASK_ID)
            .rspMsg(DEFAULT_RSP_MSG)
            .status(DEFAULT_STATUS)
            .progressInPercent(DEFAULT_PROGRESS_IN_PERCENT)
            .invalidUserIdList(DEFAULT_INVALID_USER_ID_LIST)
            .forbiddenUserIdList(DEFAULT_FORBIDDEN_USER_ID_LIST)
            .failedUserIdList(DEFAULT_FAILED_USER_ID_LIST)
            .readUserIdList(DEFAULT_READ_USER_ID_LIST)
            .unreadUserIdList(DEFAULT_UNREAD_USER_ID_LIST)
            .invalidDeptIdList(DEFAULT_INVALID_DEPT_ID_LIST)
            .withdraw(DEFAULT_WITHDRAW);
        return msgTask;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MsgTask createUpdatedEntity(EntityManager em) {
        MsgTask msgTask = new MsgTask()
            .useridList(UPDATED_USERID_LIST)
            .taskId(UPDATED_TASK_ID)
            .rspMsg(UPDATED_RSP_MSG)
            .status(UPDATED_STATUS)
            .progressInPercent(UPDATED_PROGRESS_IN_PERCENT)
            .invalidUserIdList(UPDATED_INVALID_USER_ID_LIST)
            .forbiddenUserIdList(UPDATED_FORBIDDEN_USER_ID_LIST)
            .failedUserIdList(UPDATED_FAILED_USER_ID_LIST)
            .readUserIdList(UPDATED_READ_USER_ID_LIST)
            .unreadUserIdList(UPDATED_UNREAD_USER_ID_LIST)
            .invalidDeptIdList(UPDATED_INVALID_DEPT_ID_LIST)
            .withdraw(UPDATED_WITHDRAW);
        return msgTask;
    }

    @BeforeEach
    public void initTest() {
        msgTask = createEntity(em);
    }

    @Test
    @Transactional
    void createMsgTask() throws Exception {
        int databaseSizeBeforeCreate = msgTaskRepository.findAll().size();
        // Create the MsgTask
        restMsgTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(msgTask)))
            .andExpect(status().isCreated());

        // Validate the MsgTask in the database
        List<MsgTask> msgTaskList = msgTaskRepository.findAll();
        assertThat(msgTaskList).hasSize(databaseSizeBeforeCreate + 1);
        MsgTask testMsgTask = msgTaskList.get(msgTaskList.size() - 1);
        assertThat(testMsgTask.getUseridList()).isEqualTo(DEFAULT_USERID_LIST);
        assertThat(testMsgTask.getTaskId()).isEqualTo(DEFAULT_TASK_ID);
        assertThat(testMsgTask.getRspMsg()).isEqualTo(DEFAULT_RSP_MSG);
        assertThat(testMsgTask.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMsgTask.getProgressInPercent()).isEqualTo(DEFAULT_PROGRESS_IN_PERCENT);
        assertThat(testMsgTask.getInvalidUserIdList()).isEqualTo(DEFAULT_INVALID_USER_ID_LIST);
        assertThat(testMsgTask.getForbiddenUserIdList()).isEqualTo(DEFAULT_FORBIDDEN_USER_ID_LIST);
        assertThat(testMsgTask.getFailedUserIdList()).isEqualTo(DEFAULT_FAILED_USER_ID_LIST);
        assertThat(testMsgTask.getReadUserIdList()).isEqualTo(DEFAULT_READ_USER_ID_LIST);
        assertThat(testMsgTask.getUnreadUserIdList()).isEqualTo(DEFAULT_UNREAD_USER_ID_LIST);
        assertThat(testMsgTask.getInvalidDeptIdList()).isEqualTo(DEFAULT_INVALID_DEPT_ID_LIST);
        assertThat(testMsgTask.getWithdraw()).isEqualTo(DEFAULT_WITHDRAW);
    }

    @Test
    @Transactional
    void createMsgTaskWithExistingId() throws Exception {
        // Create the MsgTask with an existing ID
        msgTask.setId(1L);

        int databaseSizeBeforeCreate = msgTaskRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMsgTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(msgTask)))
            .andExpect(status().isBadRequest());

        // Validate the MsgTask in the database
        List<MsgTask> msgTaskList = msgTaskRepository.findAll();
        assertThat(msgTaskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMsgTasks() throws Exception {
        // Initialize the database
        msgTaskRepository.saveAndFlush(msgTask);

        // Get all the msgTaskList
        restMsgTaskMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(msgTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].useridList").value(hasItem(DEFAULT_USERID_LIST)))
            .andExpect(jsonPath("$.[*].taskId").value(hasItem(DEFAULT_TASK_ID.intValue())))
            .andExpect(jsonPath("$.[*].rspMsg").value(hasItem(DEFAULT_RSP_MSG)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].progressInPercent").value(hasItem(DEFAULT_PROGRESS_IN_PERCENT)))
            .andExpect(jsonPath("$.[*].invalidUserIdList").value(hasItem(DEFAULT_INVALID_USER_ID_LIST)))
            .andExpect(jsonPath("$.[*].forbiddenUserIdList").value(hasItem(DEFAULT_FORBIDDEN_USER_ID_LIST)))
            .andExpect(jsonPath("$.[*].failedUserIdList").value(hasItem(DEFAULT_FAILED_USER_ID_LIST)))
            .andExpect(jsonPath("$.[*].readUserIdList").value(hasItem(DEFAULT_READ_USER_ID_LIST)))
            .andExpect(jsonPath("$.[*].unreadUserIdList").value(hasItem(DEFAULT_UNREAD_USER_ID_LIST)))
            .andExpect(jsonPath("$.[*].invalidDeptIdList").value(hasItem(DEFAULT_INVALID_DEPT_ID_LIST)))
            .andExpect(jsonPath("$.[*].withdraw").value(hasItem(DEFAULT_WITHDRAW.booleanValue())));
    }

    @Test
    @Transactional
    void getMsgTask() throws Exception {
        // Initialize the database
        msgTaskRepository.saveAndFlush(msgTask);

        // Get the msgTask
        restMsgTaskMockMvc
            .perform(get(ENTITY_API_URL_ID, msgTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(msgTask.getId().intValue()))
            .andExpect(jsonPath("$.useridList").value(DEFAULT_USERID_LIST))
            .andExpect(jsonPath("$.taskId").value(DEFAULT_TASK_ID.intValue()))
            .andExpect(jsonPath("$.rspMsg").value(DEFAULT_RSP_MSG))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.progressInPercent").value(DEFAULT_PROGRESS_IN_PERCENT))
            .andExpect(jsonPath("$.invalidUserIdList").value(DEFAULT_INVALID_USER_ID_LIST))
            .andExpect(jsonPath("$.forbiddenUserIdList").value(DEFAULT_FORBIDDEN_USER_ID_LIST))
            .andExpect(jsonPath("$.failedUserIdList").value(DEFAULT_FAILED_USER_ID_LIST))
            .andExpect(jsonPath("$.readUserIdList").value(DEFAULT_READ_USER_ID_LIST))
            .andExpect(jsonPath("$.unreadUserIdList").value(DEFAULT_UNREAD_USER_ID_LIST))
            .andExpect(jsonPath("$.invalidDeptIdList").value(DEFAULT_INVALID_DEPT_ID_LIST))
            .andExpect(jsonPath("$.withdraw").value(DEFAULT_WITHDRAW.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMsgTask() throws Exception {
        // Get the msgTask
        restMsgTaskMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMsgTask() throws Exception {
        // Initialize the database
        msgTaskRepository.saveAndFlush(msgTask);

        int databaseSizeBeforeUpdate = msgTaskRepository.findAll().size();

        // Update the msgTask
        MsgTask updatedMsgTask = msgTaskRepository.findById(msgTask.getId()).get();
        // Disconnect from session so that the updates on updatedMsgTask are not directly saved in db
        em.detach(updatedMsgTask);
        updatedMsgTask
            .useridList(UPDATED_USERID_LIST)
            .taskId(UPDATED_TASK_ID)
            .rspMsg(UPDATED_RSP_MSG)
            .status(UPDATED_STATUS)
            .progressInPercent(UPDATED_PROGRESS_IN_PERCENT)
            .invalidUserIdList(UPDATED_INVALID_USER_ID_LIST)
            .forbiddenUserIdList(UPDATED_FORBIDDEN_USER_ID_LIST)
            .failedUserIdList(UPDATED_FAILED_USER_ID_LIST)
            .readUserIdList(UPDATED_READ_USER_ID_LIST)
            .unreadUserIdList(UPDATED_UNREAD_USER_ID_LIST)
            .invalidDeptIdList(UPDATED_INVALID_DEPT_ID_LIST)
            .withdraw(UPDATED_WITHDRAW);

        restMsgTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMsgTask.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMsgTask))
            )
            .andExpect(status().isOk());

        // Validate the MsgTask in the database
        List<MsgTask> msgTaskList = msgTaskRepository.findAll();
        assertThat(msgTaskList).hasSize(databaseSizeBeforeUpdate);
        MsgTask testMsgTask = msgTaskList.get(msgTaskList.size() - 1);
        assertThat(testMsgTask.getUseridList()).isEqualTo(UPDATED_USERID_LIST);
        assertThat(testMsgTask.getTaskId()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testMsgTask.getRspMsg()).isEqualTo(UPDATED_RSP_MSG);
        assertThat(testMsgTask.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMsgTask.getProgressInPercent()).isEqualTo(UPDATED_PROGRESS_IN_PERCENT);
        assertThat(testMsgTask.getInvalidUserIdList()).isEqualTo(UPDATED_INVALID_USER_ID_LIST);
        assertThat(testMsgTask.getForbiddenUserIdList()).isEqualTo(UPDATED_FORBIDDEN_USER_ID_LIST);
        assertThat(testMsgTask.getFailedUserIdList()).isEqualTo(UPDATED_FAILED_USER_ID_LIST);
        assertThat(testMsgTask.getReadUserIdList()).isEqualTo(UPDATED_READ_USER_ID_LIST);
        assertThat(testMsgTask.getUnreadUserIdList()).isEqualTo(UPDATED_UNREAD_USER_ID_LIST);
        assertThat(testMsgTask.getInvalidDeptIdList()).isEqualTo(UPDATED_INVALID_DEPT_ID_LIST);
        assertThat(testMsgTask.getWithdraw()).isEqualTo(UPDATED_WITHDRAW);
    }

    @Test
    @Transactional
    void putNonExistingMsgTask() throws Exception {
        int databaseSizeBeforeUpdate = msgTaskRepository.findAll().size();
        msgTask.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMsgTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, msgTask.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(msgTask))
            )
            .andExpect(status().isBadRequest());

        // Validate the MsgTask in the database
        List<MsgTask> msgTaskList = msgTaskRepository.findAll();
        assertThat(msgTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMsgTask() throws Exception {
        int databaseSizeBeforeUpdate = msgTaskRepository.findAll().size();
        msgTask.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMsgTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(msgTask))
            )
            .andExpect(status().isBadRequest());

        // Validate the MsgTask in the database
        List<MsgTask> msgTaskList = msgTaskRepository.findAll();
        assertThat(msgTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMsgTask() throws Exception {
        int databaseSizeBeforeUpdate = msgTaskRepository.findAll().size();
        msgTask.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMsgTaskMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(msgTask)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MsgTask in the database
        List<MsgTask> msgTaskList = msgTaskRepository.findAll();
        assertThat(msgTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMsgTaskWithPatch() throws Exception {
        // Initialize the database
        msgTaskRepository.saveAndFlush(msgTask);

        int databaseSizeBeforeUpdate = msgTaskRepository.findAll().size();

        // Update the msgTask using partial update
        MsgTask partialUpdatedMsgTask = new MsgTask();
        partialUpdatedMsgTask.setId(msgTask.getId());

        partialUpdatedMsgTask
            .useridList(UPDATED_USERID_LIST)
            .taskId(UPDATED_TASK_ID)
            .status(UPDATED_STATUS)
            .forbiddenUserIdList(UPDATED_FORBIDDEN_USER_ID_LIST)
            .failedUserIdList(UPDATED_FAILED_USER_ID_LIST)
            .readUserIdList(UPDATED_READ_USER_ID_LIST)
            .invalidDeptIdList(UPDATED_INVALID_DEPT_ID_LIST)
            .withdraw(UPDATED_WITHDRAW);

        restMsgTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMsgTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMsgTask))
            )
            .andExpect(status().isOk());

        // Validate the MsgTask in the database
        List<MsgTask> msgTaskList = msgTaskRepository.findAll();
        assertThat(msgTaskList).hasSize(databaseSizeBeforeUpdate);
        MsgTask testMsgTask = msgTaskList.get(msgTaskList.size() - 1);
        assertThat(testMsgTask.getUseridList()).isEqualTo(UPDATED_USERID_LIST);
        assertThat(testMsgTask.getTaskId()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testMsgTask.getRspMsg()).isEqualTo(DEFAULT_RSP_MSG);
        assertThat(testMsgTask.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMsgTask.getProgressInPercent()).isEqualTo(DEFAULT_PROGRESS_IN_PERCENT);
        assertThat(testMsgTask.getInvalidUserIdList()).isEqualTo(DEFAULT_INVALID_USER_ID_LIST);
        assertThat(testMsgTask.getForbiddenUserIdList()).isEqualTo(UPDATED_FORBIDDEN_USER_ID_LIST);
        assertThat(testMsgTask.getFailedUserIdList()).isEqualTo(UPDATED_FAILED_USER_ID_LIST);
        assertThat(testMsgTask.getReadUserIdList()).isEqualTo(UPDATED_READ_USER_ID_LIST);
        assertThat(testMsgTask.getUnreadUserIdList()).isEqualTo(DEFAULT_UNREAD_USER_ID_LIST);
        assertThat(testMsgTask.getInvalidDeptIdList()).isEqualTo(UPDATED_INVALID_DEPT_ID_LIST);
        assertThat(testMsgTask.getWithdraw()).isEqualTo(UPDATED_WITHDRAW);
    }

    @Test
    @Transactional
    void fullUpdateMsgTaskWithPatch() throws Exception {
        // Initialize the database
        msgTaskRepository.saveAndFlush(msgTask);

        int databaseSizeBeforeUpdate = msgTaskRepository.findAll().size();

        // Update the msgTask using partial update
        MsgTask partialUpdatedMsgTask = new MsgTask();
        partialUpdatedMsgTask.setId(msgTask.getId());

        partialUpdatedMsgTask
            .useridList(UPDATED_USERID_LIST)
            .taskId(UPDATED_TASK_ID)
            .rspMsg(UPDATED_RSP_MSG)
            .status(UPDATED_STATUS)
            .progressInPercent(UPDATED_PROGRESS_IN_PERCENT)
            .invalidUserIdList(UPDATED_INVALID_USER_ID_LIST)
            .forbiddenUserIdList(UPDATED_FORBIDDEN_USER_ID_LIST)
            .failedUserIdList(UPDATED_FAILED_USER_ID_LIST)
            .readUserIdList(UPDATED_READ_USER_ID_LIST)
            .unreadUserIdList(UPDATED_UNREAD_USER_ID_LIST)
            .invalidDeptIdList(UPDATED_INVALID_DEPT_ID_LIST)
            .withdraw(UPDATED_WITHDRAW);

        restMsgTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMsgTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMsgTask))
            )
            .andExpect(status().isOk());

        // Validate the MsgTask in the database
        List<MsgTask> msgTaskList = msgTaskRepository.findAll();
        assertThat(msgTaskList).hasSize(databaseSizeBeforeUpdate);
        MsgTask testMsgTask = msgTaskList.get(msgTaskList.size() - 1);
        assertThat(testMsgTask.getUseridList()).isEqualTo(UPDATED_USERID_LIST);
        assertThat(testMsgTask.getTaskId()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testMsgTask.getRspMsg()).isEqualTo(UPDATED_RSP_MSG);
        assertThat(testMsgTask.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMsgTask.getProgressInPercent()).isEqualTo(UPDATED_PROGRESS_IN_PERCENT);
        assertThat(testMsgTask.getInvalidUserIdList()).isEqualTo(UPDATED_INVALID_USER_ID_LIST);
        assertThat(testMsgTask.getForbiddenUserIdList()).isEqualTo(UPDATED_FORBIDDEN_USER_ID_LIST);
        assertThat(testMsgTask.getFailedUserIdList()).isEqualTo(UPDATED_FAILED_USER_ID_LIST);
        assertThat(testMsgTask.getReadUserIdList()).isEqualTo(UPDATED_READ_USER_ID_LIST);
        assertThat(testMsgTask.getUnreadUserIdList()).isEqualTo(UPDATED_UNREAD_USER_ID_LIST);
        assertThat(testMsgTask.getInvalidDeptIdList()).isEqualTo(UPDATED_INVALID_DEPT_ID_LIST);
        assertThat(testMsgTask.getWithdraw()).isEqualTo(UPDATED_WITHDRAW);
    }

    @Test
    @Transactional
    void patchNonExistingMsgTask() throws Exception {
        int databaseSizeBeforeUpdate = msgTaskRepository.findAll().size();
        msgTask.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMsgTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, msgTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(msgTask))
            )
            .andExpect(status().isBadRequest());

        // Validate the MsgTask in the database
        List<MsgTask> msgTaskList = msgTaskRepository.findAll();
        assertThat(msgTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMsgTask() throws Exception {
        int databaseSizeBeforeUpdate = msgTaskRepository.findAll().size();
        msgTask.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMsgTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(msgTask))
            )
            .andExpect(status().isBadRequest());

        // Validate the MsgTask in the database
        List<MsgTask> msgTaskList = msgTaskRepository.findAll();
        assertThat(msgTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMsgTask() throws Exception {
        int databaseSizeBeforeUpdate = msgTaskRepository.findAll().size();
        msgTask.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMsgTaskMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(msgTask)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MsgTask in the database
        List<MsgTask> msgTaskList = msgTaskRepository.findAll();
        assertThat(msgTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMsgTask() throws Exception {
        // Initialize the database
        msgTaskRepository.saveAndFlush(msgTask);

        int databaseSizeBeforeDelete = msgTaskRepository.findAll().size();

        // Delete the msgTask
        restMsgTaskMockMvc
            .perform(delete(ENTITY_API_URL_ID, msgTask.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MsgTask> msgTaskList = msgTaskRepository.findAll();
        assertThat(msgTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
