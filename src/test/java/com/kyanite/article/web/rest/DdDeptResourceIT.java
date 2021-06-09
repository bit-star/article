package com.kyanite.article.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.article.IntegrationTest;
import com.kyanite.article.domain.DdDept;
import com.kyanite.article.repository.DdDeptRepository;
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
 * Integration tests for the {@link DdDeptResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DdDeptResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dd-depts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DdDeptRepository ddDeptRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDdDeptMockMvc;

    private DdDept ddDept;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DdDept createEntity(EntityManager em) {
        DdDept ddDept = new DdDept().name(DEFAULT_NAME).parentId(DEFAULT_PARENT_ID);
        return ddDept;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DdDept createUpdatedEntity(EntityManager em) {
        DdDept ddDept = new DdDept().name(UPDATED_NAME).parentId(UPDATED_PARENT_ID);
        return ddDept;
    }

    @BeforeEach
    public void initTest() {
        ddDept = createEntity(em);
    }

    @Test
    @Transactional
    void createDdDept() throws Exception {
        int databaseSizeBeforeCreate = ddDeptRepository.findAll().size();
        // Create the DdDept
        restDdDeptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ddDept)))
            .andExpect(status().isCreated());

        // Validate the DdDept in the database
        List<DdDept> ddDeptList = ddDeptRepository.findAll();
        assertThat(ddDeptList).hasSize(databaseSizeBeforeCreate + 1);
        DdDept testDdDept = ddDeptList.get(ddDeptList.size() - 1);
        assertThat(testDdDept.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDdDept.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
    }

    @Test
    @Transactional
    void createDdDeptWithExistingId() throws Exception {
        // Create the DdDept with an existing ID
        ddDept.setId(1L);

        int databaseSizeBeforeCreate = ddDeptRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDdDeptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ddDept)))
            .andExpect(status().isBadRequest());

        // Validate the DdDept in the database
        List<DdDept> ddDeptList = ddDeptRepository.findAll();
        assertThat(ddDeptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDdDepts() throws Exception {
        // Initialize the database
        ddDeptRepository.saveAndFlush(ddDept);

        // Get all the ddDeptList
        restDdDeptMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ddDept.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID)));
    }

    @Test
    @Transactional
    void getDdDept() throws Exception {
        // Initialize the database
        ddDeptRepository.saveAndFlush(ddDept);

        // Get the ddDept
        restDdDeptMockMvc
            .perform(get(ENTITY_API_URL_ID, ddDept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ddDept.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID));
    }

    @Test
    @Transactional
    void getNonExistingDdDept() throws Exception {
        // Get the ddDept
        restDdDeptMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDdDept() throws Exception {
        // Initialize the database
        ddDeptRepository.saveAndFlush(ddDept);

        int databaseSizeBeforeUpdate = ddDeptRepository.findAll().size();

        // Update the ddDept
        DdDept updatedDdDept = ddDeptRepository.findById(ddDept.getId()).get();
        // Disconnect from session so that the updates on updatedDdDept are not directly saved in db
        em.detach(updatedDdDept);
        updatedDdDept.name(UPDATED_NAME).parentId(UPDATED_PARENT_ID);

        restDdDeptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDdDept.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDdDept))
            )
            .andExpect(status().isOk());

        // Validate the DdDept in the database
        List<DdDept> ddDeptList = ddDeptRepository.findAll();
        assertThat(ddDeptList).hasSize(databaseSizeBeforeUpdate);
        DdDept testDdDept = ddDeptList.get(ddDeptList.size() - 1);
        assertThat(testDdDept.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDdDept.getParentId()).isEqualTo(UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void putNonExistingDdDept() throws Exception {
        int databaseSizeBeforeUpdate = ddDeptRepository.findAll().size();
        ddDept.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDdDeptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ddDept.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ddDept))
            )
            .andExpect(status().isBadRequest());

        // Validate the DdDept in the database
        List<DdDept> ddDeptList = ddDeptRepository.findAll();
        assertThat(ddDeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDdDept() throws Exception {
        int databaseSizeBeforeUpdate = ddDeptRepository.findAll().size();
        ddDept.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDdDeptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ddDept))
            )
            .andExpect(status().isBadRequest());

        // Validate the DdDept in the database
        List<DdDept> ddDeptList = ddDeptRepository.findAll();
        assertThat(ddDeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDdDept() throws Exception {
        int databaseSizeBeforeUpdate = ddDeptRepository.findAll().size();
        ddDept.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDdDeptMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ddDept)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DdDept in the database
        List<DdDept> ddDeptList = ddDeptRepository.findAll();
        assertThat(ddDeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDdDeptWithPatch() throws Exception {
        // Initialize the database
        ddDeptRepository.saveAndFlush(ddDept);

        int databaseSizeBeforeUpdate = ddDeptRepository.findAll().size();

        // Update the ddDept using partial update
        DdDept partialUpdatedDdDept = new DdDept();
        partialUpdatedDdDept.setId(ddDept.getId());

        partialUpdatedDdDept.name(UPDATED_NAME).parentId(UPDATED_PARENT_ID);

        restDdDeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDdDept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDdDept))
            )
            .andExpect(status().isOk());

        // Validate the DdDept in the database
        List<DdDept> ddDeptList = ddDeptRepository.findAll();
        assertThat(ddDeptList).hasSize(databaseSizeBeforeUpdate);
        DdDept testDdDept = ddDeptList.get(ddDeptList.size() - 1);
        assertThat(testDdDept.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDdDept.getParentId()).isEqualTo(UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void fullUpdateDdDeptWithPatch() throws Exception {
        // Initialize the database
        ddDeptRepository.saveAndFlush(ddDept);

        int databaseSizeBeforeUpdate = ddDeptRepository.findAll().size();

        // Update the ddDept using partial update
        DdDept partialUpdatedDdDept = new DdDept();
        partialUpdatedDdDept.setId(ddDept.getId());

        partialUpdatedDdDept.name(UPDATED_NAME).parentId(UPDATED_PARENT_ID);

        restDdDeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDdDept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDdDept))
            )
            .andExpect(status().isOk());

        // Validate the DdDept in the database
        List<DdDept> ddDeptList = ddDeptRepository.findAll();
        assertThat(ddDeptList).hasSize(databaseSizeBeforeUpdate);
        DdDept testDdDept = ddDeptList.get(ddDeptList.size() - 1);
        assertThat(testDdDept.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDdDept.getParentId()).isEqualTo(UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingDdDept() throws Exception {
        int databaseSizeBeforeUpdate = ddDeptRepository.findAll().size();
        ddDept.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDdDeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ddDept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ddDept))
            )
            .andExpect(status().isBadRequest());

        // Validate the DdDept in the database
        List<DdDept> ddDeptList = ddDeptRepository.findAll();
        assertThat(ddDeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDdDept() throws Exception {
        int databaseSizeBeforeUpdate = ddDeptRepository.findAll().size();
        ddDept.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDdDeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ddDept))
            )
            .andExpect(status().isBadRequest());

        // Validate the DdDept in the database
        List<DdDept> ddDeptList = ddDeptRepository.findAll();
        assertThat(ddDeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDdDept() throws Exception {
        int databaseSizeBeforeUpdate = ddDeptRepository.findAll().size();
        ddDept.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDdDeptMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ddDept)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DdDept in the database
        List<DdDept> ddDeptList = ddDeptRepository.findAll();
        assertThat(ddDeptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDdDept() throws Exception {
        // Initialize the database
        ddDeptRepository.saveAndFlush(ddDept);

        int databaseSizeBeforeDelete = ddDeptRepository.findAll().size();

        // Delete the ddDept
        restDdDeptMockMvc
            .perform(delete(ENTITY_API_URL_ID, ddDept.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DdDept> ddDeptList = ddDeptRepository.findAll();
        assertThat(ddDeptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
