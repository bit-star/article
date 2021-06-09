package com.kyanite.article.service;

import com.kyanite.article.domain.Annex;
import com.kyanite.article.repository.AnnexRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Annex}.
 */
@Service
@Transactional
public class AnnexService {

    private final Logger log = LoggerFactory.getLogger(AnnexService.class);

    private final AnnexRepository annexRepository;

    public AnnexService(AnnexRepository annexRepository) {
        this.annexRepository = annexRepository;
    }

    /**
     * Save a annex.
     *
     * @param annex the entity to save.
     * @return the persisted entity.
     */
    public Annex save(Annex annex) {
        log.debug("Request to save Annex : {}", annex);
        return annexRepository.save(annex);
    }

    /**
     * Partially update a annex.
     *
     * @param annex the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Annex> partialUpdate(Annex annex) {
        log.debug("Request to partially update Annex : {}", annex);

        return annexRepository
            .findById(annex.getId())
            .map(
                existingAnnex -> {
                    if (annex.getSpaceId() != null) {
                        existingAnnex.setSpaceId(annex.getSpaceId());
                    }
                    if (annex.getFileId() != null) {
                        existingAnnex.setFileId(annex.getFileId());
                    }
                    if (annex.getFileName() != null) {
                        existingAnnex.setFileName(annex.getFileName());
                    }
                    if (annex.getFileSize() != null) {
                        existingAnnex.setFileSize(annex.getFileSize());
                    }
                    if (annex.getFileType() != null) {
                        existingAnnex.setFileType(annex.getFileType());
                    }
                    if (annex.getStorageMode() != null) {
                        existingAnnex.setStorageMode(annex.getStorageMode());
                    }
                    if (annex.getUrl() != null) {
                        existingAnnex.setUrl(annex.getUrl());
                    }

                    return existingAnnex;
                }
            )
            .map(annexRepository::save);
    }

    /**
     * Get all the annexes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Annex> findAll(Pageable pageable) {
        log.debug("Request to get all Annexes");
        return annexRepository.findAll(pageable);
    }

    /**
     * Get one annex by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Annex> findOne(Long id) {
        log.debug("Request to get Annex : {}", id);
        return annexRepository.findById(id);
    }

    /**
     * Delete the annex by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Annex : {}", id);
        annexRepository.deleteById(id);
    }
}
