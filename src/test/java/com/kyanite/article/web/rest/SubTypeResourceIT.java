package com.kyanite.article.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.article.IntegrationTest;
import com.kyanite.article.domain.SubType;
import com.kyanite.article.repository.SubTypeRepository;
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
 * Integration tests for the {@link SubTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sub-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubTypeRepository subTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubTypeMockMvc;

    private SubType subType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubType createEntity(EntityManager em) {
        SubType subType = new SubType().name(DEFAULT_NAME);
        return subType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubType createUpdatedEntity(EntityManager em) {
        SubType subType = new SubType().name(UPDATED_NAME);
        return subType;
    }

    @BeforeEach
    public void initTest() {
        subType = createEntity(em);
    }

    @Test
    @Transactional
    void createSubType() throws Exception {
        int databaseSizeBeforeCreate = subTypeRepository.findAll().size();
        // Create the SubType
        restSubTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subType)))
            .andExpect(status().isCreated());

        // Validate the SubType in the database
        List<SubType> subTypeList = subTypeRepository.findAll();
        assertThat(subTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SubType testSubType = subTypeList.get(subTypeList.size() - 1);
        assertThat(testSubType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createSubTypeWithExistingId() throws Exception {
        // Create the SubType with an existing ID
        subType.setId(1L);

        int databaseSizeBeforeCreate = subTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subType)))
            .andExpect(status().isBadRequest());

        // Validate the SubType in the database
        List<SubType> subTypeList = subTypeRepository.findAll();
        assertThat(subTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubTypes() throws Exception {
        // Initialize the database
        subTypeRepository.saveAndFlush(subType);

        // Get all the subTypeList
        restSubTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getSubType() throws Exception {
        // Initialize the database
        subTypeRepository.saveAndFlush(subType);

        // Get the subType
        restSubTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, subType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingSubType() throws Exception {
        // Get the subType
        restSubTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubType() throws Exception {
        // Initialize the database
        subTypeRepository.saveAndFlush(subType);

        int databaseSizeBeforeUpdate = subTypeRepository.findAll().size();

        // Update the subType
        SubType updatedSubType = subTypeRepository.findById(subType.getId()).get();
        // Disconnect from session so that the updates on updatedSubType are not directly saved in db
        em.detach(updatedSubType);
        updatedSubType.name(UPDATED_NAME);

        restSubTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSubType))
            )
            .andExpect(status().isOk());

        // Validate the SubType in the database
        List<SubType> subTypeList = subTypeRepository.findAll();
        assertThat(subTypeList).hasSize(databaseSizeBeforeUpdate);
        SubType testSubType = subTypeList.get(subTypeList.size() - 1);
        assertThat(testSubType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSubType() throws Exception {
        int databaseSizeBeforeUpdate = subTypeRepository.findAll().size();
        subType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subType))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubType in the database
        List<SubType> subTypeList = subTypeRepository.findAll();
        assertThat(subTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubType() throws Exception {
        int databaseSizeBeforeUpdate = subTypeRepository.findAll().size();
        subType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subType))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubType in the database
        List<SubType> subTypeList = subTypeRepository.findAll();
        assertThat(subTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubType() throws Exception {
        int databaseSizeBeforeUpdate = subTypeRepository.findAll().size();
        subType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubType in the database
        List<SubType> subTypeList = subTypeRepository.findAll();
        assertThat(subTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubTypeWithPatch() throws Exception {
        // Initialize the database
        subTypeRepository.saveAndFlush(subType);

        int databaseSizeBeforeUpdate = subTypeRepository.findAll().size();

        // Update the subType using partial update
        SubType partialUpdatedSubType = new SubType();
        partialUpdatedSubType.setId(subType.getId());

        partialUpdatedSubType.name(UPDATED_NAME);

        restSubTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubType))
            )
            .andExpect(status().isOk());

        // Validate the SubType in the database
        List<SubType> subTypeList = subTypeRepository.findAll();
        assertThat(subTypeList).hasSize(databaseSizeBeforeUpdate);
        SubType testSubType = subTypeList.get(subTypeList.size() - 1);
        assertThat(testSubType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSubTypeWithPatch() throws Exception {
        // Initialize the database
        subTypeRepository.saveAndFlush(subType);

        int databaseSizeBeforeUpdate = subTypeRepository.findAll().size();

        // Update the subType using partial update
        SubType partialUpdatedSubType = new SubType();
        partialUpdatedSubType.setId(subType.getId());

        partialUpdatedSubType.name(UPDATED_NAME);

        restSubTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubType))
            )
            .andExpect(status().isOk());

        // Validate the SubType in the database
        List<SubType> subTypeList = subTypeRepository.findAll();
        assertThat(subTypeList).hasSize(databaseSizeBeforeUpdate);
        SubType testSubType = subTypeList.get(subTypeList.size() - 1);
        assertThat(testSubType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSubType() throws Exception {
        int databaseSizeBeforeUpdate = subTypeRepository.findAll().size();
        subType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subType))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubType in the database
        List<SubType> subTypeList = subTypeRepository.findAll();
        assertThat(subTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubType() throws Exception {
        int databaseSizeBeforeUpdate = subTypeRepository.findAll().size();
        subType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subType))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubType in the database
        List<SubType> subTypeList = subTypeRepository.findAll();
        assertThat(subTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubType() throws Exception {
        int databaseSizeBeforeUpdate = subTypeRepository.findAll().size();
        subType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(subType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubType in the database
        List<SubType> subTypeList = subTypeRepository.findAll();
        assertThat(subTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubType() throws Exception {
        // Initialize the database
        subTypeRepository.saveAndFlush(subType);

        int databaseSizeBeforeDelete = subTypeRepository.findAll().size();

        // Delete the subType
        restSubTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, subType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubType> subTypeList = subTypeRepository.findAll();
        assertThat(subTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
