package com.kyanite.article.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kyanite.article.IntegrationTest;
import com.kyanite.article.domain.Annex;
import com.kyanite.article.domain.enumeration.StorageMode;
import com.kyanite.article.repository.AnnexRepository;
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
 * Integration tests for the {@link AnnexResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnnexResourceIT {

    private static final String DEFAULT_SPACE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SPACE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FILE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TYPE = "BBBBBBBBBB";

    private static final StorageMode DEFAULT_STORAGE_MODE = StorageMode.OSS;
    private static final StorageMode UPDATED_STORAGE_MODE = StorageMode.Dingtalk;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/annexes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnnexRepository annexRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnnexMockMvc;

    private Annex annex;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Annex createEntity(EntityManager em) {
        Annex annex = new Annex()
            .spaceId(DEFAULT_SPACE_ID)
            .fileId(DEFAULT_FILE_ID)
            .fileName(DEFAULT_FILE_NAME)
            .fileSize(DEFAULT_FILE_SIZE)
            .fileType(DEFAULT_FILE_TYPE)
            .storageMode(DEFAULT_STORAGE_MODE)
            .url(DEFAULT_URL);
        return annex;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Annex createUpdatedEntity(EntityManager em) {
        Annex annex = new Annex()
            .spaceId(UPDATED_SPACE_ID)
            .fileId(UPDATED_FILE_ID)
            .fileName(UPDATED_FILE_NAME)
            .fileSize(UPDATED_FILE_SIZE)
            .fileType(UPDATED_FILE_TYPE)
            .storageMode(UPDATED_STORAGE_MODE)
            .url(UPDATED_URL);
        return annex;
    }

    @BeforeEach
    public void initTest() {
        annex = createEntity(em);
    }

    @Test
    @Transactional
    void createAnnex() throws Exception {
        int databaseSizeBeforeCreate = annexRepository.findAll().size();
        // Create the Annex
        restAnnexMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annex)))
            .andExpect(status().isCreated());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeCreate + 1);
        Annex testAnnex = annexList.get(annexList.size() - 1);
        assertThat(testAnnex.getSpaceId()).isEqualTo(DEFAULT_SPACE_ID);
        assertThat(testAnnex.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testAnnex.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testAnnex.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testAnnex.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testAnnex.getStorageMode()).isEqualTo(DEFAULT_STORAGE_MODE);
        assertThat(testAnnex.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    void createAnnexWithExistingId() throws Exception {
        // Create the Annex with an existing ID
        annex.setId(1L);

        int databaseSizeBeforeCreate = annexRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnexMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annex)))
            .andExpect(status().isBadRequest());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnnexes() throws Exception {
        // Initialize the database
        annexRepository.saveAndFlush(annex);

        // Get all the annexList
        restAnnexMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annex.getId().intValue())))
            .andExpect(jsonPath("$.[*].spaceId").value(hasItem(DEFAULT_SPACE_ID)))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].storageMode").value(hasItem(DEFAULT_STORAGE_MODE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }

    @Test
    @Transactional
    void getAnnex() throws Exception {
        // Initialize the database
        annexRepository.saveAndFlush(annex);

        // Get the annex
        restAnnexMockMvc
            .perform(get(ENTITY_API_URL_ID, annex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(annex.getId().intValue()))
            .andExpect(jsonPath("$.spaceId").value(DEFAULT_SPACE_ID))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileSize").value(DEFAULT_FILE_SIZE))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE))
            .andExpect(jsonPath("$.storageMode").value(DEFAULT_STORAGE_MODE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    @Transactional
    void getNonExistingAnnex() throws Exception {
        // Get the annex
        restAnnexMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnnex() throws Exception {
        // Initialize the database
        annexRepository.saveAndFlush(annex);

        int databaseSizeBeforeUpdate = annexRepository.findAll().size();

        // Update the annex
        Annex updatedAnnex = annexRepository.findById(annex.getId()).get();
        // Disconnect from session so that the updates on updatedAnnex are not directly saved in db
        em.detach(updatedAnnex);
        updatedAnnex
            .spaceId(UPDATED_SPACE_ID)
            .fileId(UPDATED_FILE_ID)
            .fileName(UPDATED_FILE_NAME)
            .fileSize(UPDATED_FILE_SIZE)
            .fileType(UPDATED_FILE_TYPE)
            .storageMode(UPDATED_STORAGE_MODE)
            .url(UPDATED_URL);

        restAnnexMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnnex.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAnnex))
            )
            .andExpect(status().isOk());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeUpdate);
        Annex testAnnex = annexList.get(annexList.size() - 1);
        assertThat(testAnnex.getSpaceId()).isEqualTo(UPDATED_SPACE_ID);
        assertThat(testAnnex.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testAnnex.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testAnnex.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testAnnex.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testAnnex.getStorageMode()).isEqualTo(UPDATED_STORAGE_MODE);
        assertThat(testAnnex.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    void putNonExistingAnnex() throws Exception {
        int databaseSizeBeforeUpdate = annexRepository.findAll().size();
        annex.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnexMockMvc
            .perform(
                put(ENTITY_API_URL_ID, annex.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(annex))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnnex() throws Exception {
        int databaseSizeBeforeUpdate = annexRepository.findAll().size();
        annex.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnexMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(annex))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnnex() throws Exception {
        int databaseSizeBeforeUpdate = annexRepository.findAll().size();
        annex.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnexMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annex)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnnexWithPatch() throws Exception {
        // Initialize the database
        annexRepository.saveAndFlush(annex);

        int databaseSizeBeforeUpdate = annexRepository.findAll().size();

        // Update the annex using partial update
        Annex partialUpdatedAnnex = new Annex();
        partialUpdatedAnnex.setId(annex.getId());

        partialUpdatedAnnex.fileId(UPDATED_FILE_ID).fileType(UPDATED_FILE_TYPE).storageMode(UPDATED_STORAGE_MODE);

        restAnnexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnex.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnex))
            )
            .andExpect(status().isOk());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeUpdate);
        Annex testAnnex = annexList.get(annexList.size() - 1);
        assertThat(testAnnex.getSpaceId()).isEqualTo(DEFAULT_SPACE_ID);
        assertThat(testAnnex.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testAnnex.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testAnnex.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testAnnex.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testAnnex.getStorageMode()).isEqualTo(UPDATED_STORAGE_MODE);
        assertThat(testAnnex.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    void fullUpdateAnnexWithPatch() throws Exception {
        // Initialize the database
        annexRepository.saveAndFlush(annex);

        int databaseSizeBeforeUpdate = annexRepository.findAll().size();

        // Update the annex using partial update
        Annex partialUpdatedAnnex = new Annex();
        partialUpdatedAnnex.setId(annex.getId());

        partialUpdatedAnnex
            .spaceId(UPDATED_SPACE_ID)
            .fileId(UPDATED_FILE_ID)
            .fileName(UPDATED_FILE_NAME)
            .fileSize(UPDATED_FILE_SIZE)
            .fileType(UPDATED_FILE_TYPE)
            .storageMode(UPDATED_STORAGE_MODE)
            .url(UPDATED_URL);

        restAnnexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnex.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnex))
            )
            .andExpect(status().isOk());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeUpdate);
        Annex testAnnex = annexList.get(annexList.size() - 1);
        assertThat(testAnnex.getSpaceId()).isEqualTo(UPDATED_SPACE_ID);
        assertThat(testAnnex.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testAnnex.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testAnnex.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testAnnex.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testAnnex.getStorageMode()).isEqualTo(UPDATED_STORAGE_MODE);
        assertThat(testAnnex.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    void patchNonExistingAnnex() throws Exception {
        int databaseSizeBeforeUpdate = annexRepository.findAll().size();
        annex.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, annex.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(annex))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnnex() throws Exception {
        int databaseSizeBeforeUpdate = annexRepository.findAll().size();
        annex.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnexMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(annex))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnnex() throws Exception {
        int databaseSizeBeforeUpdate = annexRepository.findAll().size();
        annex.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnexMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(annex)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnnex() throws Exception {
        // Initialize the database
        annexRepository.saveAndFlush(annex);

        int databaseSizeBeforeDelete = annexRepository.findAll().size();

        // Delete the annex
        restAnnexMockMvc
            .perform(delete(ENTITY_API_URL_ID, annex.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
