package com.kyanite.article.service;

import com.kyanite.article.domain.DdDept;
import com.kyanite.article.repository.DdDeptRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DdDept}.
 */
@Service
@Transactional
public class DdDeptService {

    private final Logger log = LoggerFactory.getLogger(DdDeptService.class);

    private final DdDeptRepository ddDeptRepository;

    public DdDeptService(DdDeptRepository ddDeptRepository) {
        this.ddDeptRepository = ddDeptRepository;
    }

    /**
     * Save a ddDept.
     *
     * @param ddDept the entity to save.
     * @return the persisted entity.
     */
    public DdDept save(DdDept ddDept) {
        log.debug("Request to save DdDept : {}", ddDept);
        return ddDeptRepository.save(ddDept);
    }

    /**
     * Partially update a ddDept.
     *
     * @param ddDept the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DdDept> partialUpdate(DdDept ddDept) {
        log.debug("Request to partially update DdDept : {}", ddDept);

        return ddDeptRepository
            .findById(ddDept.getId())
            .map(
                existingDdDept -> {
                    if (ddDept.getName() != null) {
                        existingDdDept.setName(ddDept.getName());
                    }
                    if (ddDept.getParentId() != null) {
                        existingDdDept.setParentId(ddDept.getParentId());
                    }

                    return existingDdDept;
                }
            )
            .map(ddDeptRepository::save);
    }

    /**
     * Get all the ddDepts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DdDept> findAll(Pageable pageable) {
        log.debug("Request to get all DdDepts");
        return ddDeptRepository.findAll(pageable);
    }

    /**
     * Get one ddDept by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DdDept> findOne(Long id) {
        log.debug("Request to get DdDept : {}", id);
        return ddDeptRepository.findById(id);
    }

    /**
     * Delete the ddDept by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DdDept : {}", id);
        ddDeptRepository.deleteById(id);
    }
}
