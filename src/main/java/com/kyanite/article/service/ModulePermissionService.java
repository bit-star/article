package com.kyanite.article.service;

import com.kyanite.article.domain.ModulePermission;
import com.kyanite.article.repository.ModulePermissionRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ModulePermission}.
 */
@Service
@Transactional
public class ModulePermissionService {

    private final Logger log = LoggerFactory.getLogger(ModulePermissionService.class);

    private final ModulePermissionRepository modulePermissionRepository;

    public ModulePermissionService(ModulePermissionRepository modulePermissionRepository) {
        this.modulePermissionRepository = modulePermissionRepository;
    }

    /**
     * Save a modulePermission.
     *
     * @param modulePermission the entity to save.
     * @return the persisted entity.
     */
    public ModulePermission save(ModulePermission modulePermission) {
        log.debug("Request to save ModulePermission : {}", modulePermission);
        return modulePermissionRepository.save(modulePermission);
    }

    /**
     * Partially update a modulePermission.
     *
     * @param modulePermission the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ModulePermission> partialUpdate(ModulePermission modulePermission) {
        log.debug("Request to partially update ModulePermission : {}", modulePermission);

        return modulePermissionRepository
            .findById(modulePermission.getId())
            .map(
                existingModulePermission -> {
                    if (modulePermission.getName() != null) {
                        existingModulePermission.setName(modulePermission.getName());
                    }
                    if (modulePermission.getEnable() != null) {
                        existingModulePermission.setEnable(modulePermission.getEnable());
                    }

                    return existingModulePermission;
                }
            )
            .map(modulePermissionRepository::save);
    }

    /**
     * Get all the modulePermissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ModulePermission> findAll(Pageable pageable) {
        log.debug("Request to get all ModulePermissions");
        return modulePermissionRepository.findAll(pageable);
    }

    /**
     * Get one modulePermission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ModulePermission> findOne(Long id) {
        log.debug("Request to get ModulePermission : {}", id);
        return modulePermissionRepository.findById(id);
    }

    /**
     * Delete the modulePermission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ModulePermission : {}", id);
        modulePermissionRepository.deleteById(id);
    }
}
