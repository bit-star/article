package com.kyanite.article.service;

import com.kyanite.article.domain.SubType;
import com.kyanite.article.repository.SubTypeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubType}.
 */
@Service
@Transactional
public class SubTypeService {

    private final Logger log = LoggerFactory.getLogger(SubTypeService.class);

    private final SubTypeRepository subTypeRepository;

    public SubTypeService(SubTypeRepository subTypeRepository) {
        this.subTypeRepository = subTypeRepository;
    }

    /**
     * Save a subType.
     *
     * @param subType the entity to save.
     * @return the persisted entity.
     */
    public SubType save(SubType subType) {
        log.debug("Request to save SubType : {}", subType);
        return subTypeRepository.save(subType);
    }

    /**
     * Partially update a subType.
     *
     * @param subType the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubType> partialUpdate(SubType subType) {
        log.debug("Request to partially update SubType : {}", subType);

        return subTypeRepository
            .findById(subType.getId())
            .map(
                existingSubType -> {
                    if (subType.getName() != null) {
                        existingSubType.setName(subType.getName());
                    }

                    return existingSubType;
                }
            )
            .map(subTypeRepository::save);
    }

    /**
     * Get all the subTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SubType> findAll(Pageable pageable) {
        log.debug("Request to get all SubTypes");
        return subTypeRepository.findAll(pageable);
    }

    /**
     * Get one subType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubType> findOne(Long id) {
        log.debug("Request to get SubType : {}", id);
        return subTypeRepository.findById(id);
    }

    /**
     * Delete the subType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SubType : {}", id);
        subTypeRepository.deleteById(id);
    }
}
