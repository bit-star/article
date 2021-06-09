package com.kyanite.article.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.article.IntegrationTest;
import com.kyanite.article.domain.ModulePermission;
import com.kyanite.article.repository.ModulePermissionRepository;
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
 * Integration tests for the {@link ModulePermissionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ModulePermissionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE = false;
    private static final Boolean UPDATED_ENABLE = true;

    private static final String ENTITY_API_URL = "/api/module-permissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ModulePermissionRepository modulePermissionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModulePermissionMockMvc;

    private ModulePermission modulePermission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModulePermission createEntity(EntityManager em) {
        ModulePermission modulePermission = new ModulePermission().name(DEFAULT_NAME).enable(DEFAULT_ENABLE);
        return modulePermission;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModulePermission createUpdatedEntity(EntityManager em) {
        ModulePermission modulePermission = new ModulePermission().name(UPDATED_NAME).enable(UPDATED_ENABLE);
        return modulePermission;
    }

    @BeforeEach
    public void initTest() {
        modulePermission = createEntity(em);
    }

    @Test
    @Transactional
    void createModulePermission() throws Exception {
        int databaseSizeBeforeCreate = modulePermissionRepository.findAll().size();
        // Create the ModulePermission
        restModulePermissionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modulePermission))
            )
            .andExpect(status().isCreated());

        // Validate the ModulePermission in the database
        List<ModulePermission> modulePermissionList = modulePermissionRepository.findAll();
        assertThat(modulePermissionList).hasSize(databaseSizeBeforeCreate + 1);
        ModulePermission testModulePermission = modulePermissionList.get(modulePermissionList.size() - 1);
        assertThat(testModulePermission.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testModulePermission.getEnable()).isEqualTo(DEFAULT_ENABLE);
    }

    @Test
    @Transactional
    void createModulePermissionWithExistingId() throws Exception {
        // Create the ModulePermission with an existing ID
        modulePermission.setId(1L);

        int databaseSizeBeforeCreate = modulePermissionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModulePermissionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modulePermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModulePermission in the database
        List<ModulePermission> modulePermissionList = modulePermissionRepository.findAll();
        assertThat(modulePermissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllModulePermissions() throws Exception {
        // Initialize the database
        modulePermissionRepository.saveAndFlush(modulePermission);

        // Get all the modulePermissionList
        restModulePermissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modulePermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())));
    }

    @Test
    @Transactional
    void getModulePermission() throws Exception {
        // Initialize the database
        modulePermissionRepository.saveAndFlush(modulePermission);

        // Get the modulePermission
        restModulePermissionMockMvc
            .perform(get(ENTITY_API_URL_ID, modulePermission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modulePermission.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingModulePermission() throws Exception {
        // Get the modulePermission
        restModulePermissionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewModulePermission() throws Exception {
        // Initialize the database
        modulePermissionRepository.saveAndFlush(modulePermission);

        int databaseSizeBeforeUpdate = modulePermissionRepository.findAll().size();

        // Update the modulePermission
        ModulePermission updatedModulePermission = modulePermissionRepository.findById(modulePermission.getId()).get();
        // Disconnect from session so that the updates on updatedModulePermission are not directly saved in db
        em.detach(updatedModulePermission);
        updatedModulePermission.name(UPDATED_NAME).enable(UPDATED_ENABLE);

        restModulePermissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedModulePermission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedModulePermission))
            )
            .andExpect(status().isOk());

        // Validate the ModulePermission in the database
        List<ModulePermission> modulePermissionList = modulePermissionRepository.findAll();
        assertThat(modulePermissionList).hasSize(databaseSizeBeforeUpdate);
        ModulePermission testModulePermission = modulePermissionList.get(modulePermissionList.size() - 1);
        assertThat(testModulePermission.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModulePermission.getEnable()).isEqualTo(UPDATED_ENABLE);
    }

    @Test
    @Transactional
    void putNonExistingModulePermission() throws Exception {
        int databaseSizeBeforeUpdate = modulePermissionRepository.findAll().size();
        modulePermission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModulePermissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modulePermission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modulePermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModulePermission in the database
        List<ModulePermission> modulePermissionList = modulePermissionRepository.findAll();
        assertThat(modulePermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModulePermission() throws Exception {
        int databaseSizeBeforeUpdate = modulePermissionRepository.findAll().size();
        modulePermission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModulePermissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modulePermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModulePermission in the database
        List<ModulePermission> modulePermissionList = modulePermissionRepository.findAll();
        assertThat(modulePermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModulePermission() throws Exception {
        int databaseSizeBeforeUpdate = modulePermissionRepository.findAll().size();
        modulePermission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModulePermissionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modulePermission))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModulePermission in the database
        List<ModulePermission> modulePermissionList = modulePermissionRepository.findAll();
        assertThat(modulePermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModulePermissionWithPatch() throws Exception {
        // Initialize the database
        modulePermissionRepository.saveAndFlush(modulePermission);

        int databaseSizeBeforeUpdate = modulePermissionRepository.findAll().size();

        // Update the modulePermission using partial update
        ModulePermission partialUpdatedModulePermission = new ModulePermission();
        partialUpdatedModulePermission.setId(modulePermission.getId());

        partialUpdatedModulePermission.name(UPDATED_NAME);

        restModulePermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModulePermission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModulePermission))
            )
            .andExpect(status().isOk());

        // Validate the ModulePermission in the database
        List<ModulePermission> modulePermissionList = modulePermissionRepository.findAll();
        assertThat(modulePermissionList).hasSize(databaseSizeBeforeUpdate);
        ModulePermission testModulePermission = modulePermissionList.get(modulePermissionList.size() - 1);
        assertThat(testModulePermission.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModulePermission.getEnable()).isEqualTo(DEFAULT_ENABLE);
    }

    @Test
    @Transactional
    void fullUpdateModulePermissionWithPatch() throws Exception {
        // Initialize the database
        modulePermissionRepository.saveAndFlush(modulePermission);

        int databaseSizeBeforeUpdate = modulePermissionRepository.findAll().size();

        // Update the modulePermission using partial update
        ModulePermission partialUpdatedModulePermission = new ModulePermission();
        partialUpdatedModulePermission.setId(modulePermission.getId());

        partialUpdatedModulePermission.name(UPDATED_NAME).enable(UPDATED_ENABLE);

        restModulePermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModulePermission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModulePermission))
            )
            .andExpect(status().isOk());

        // Validate the ModulePermission in the database
        List<ModulePermission> modulePermissionList = modulePermissionRepository.findAll();
        assertThat(modulePermissionList).hasSize(databaseSizeBeforeUpdate);
        ModulePermission testModulePermission = modulePermissionList.get(modulePermissionList.size() - 1);
        assertThat(testModulePermission.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModulePermission.getEnable()).isEqualTo(UPDATED_ENABLE);
    }

    @Test
    @Transactional
    void patchNonExistingModulePermission() throws Exception {
        int databaseSizeBeforeUpdate = modulePermissionRepository.findAll().size();
        modulePermission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModulePermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, modulePermission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modulePermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModulePermission in the database
        List<ModulePermission> modulePermissionList = modulePermissionRepository.findAll();
        assertThat(modulePermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModulePermission() throws Exception {
        int databaseSizeBeforeUpdate = modulePermissionRepository.findAll().size();
        modulePermission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModulePermissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modulePermission))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModulePermission in the database
        List<ModulePermission> modulePermissionList = modulePermissionRepository.findAll();
        assertThat(modulePermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModulePermission() throws Exception {
        int databaseSizeBeforeUpdate = modulePermissionRepository.findAll().size();
        modulePermission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModulePermissionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modulePermission))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModulePermission in the database
        List<ModulePermission> modulePermissionList = modulePermissionRepository.findAll();
        assertThat(modulePermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModulePermission() throws Exception {
        // Initialize the database
        modulePermissionRepository.saveAndFlush(modulePermission);

        int databaseSizeBeforeDelete = modulePermissionRepository.findAll().size();

        // Delete the modulePermission
        restModulePermissionMockMvc
            .perform(delete(ENTITY_API_URL_ID, modulePermission.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModulePermission> modulePermissionList = modulePermissionRepository.findAll();
        assertThat(modulePermissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
