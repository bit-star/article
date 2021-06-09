package com.kyanite.article.web.rest;

import com.kyanite.article.domain.ModulePermission;
import com.kyanite.article.repository.ModulePermissionRepository;
import com.kyanite.article.service.ModulePermissionService;
import com.kyanite.article.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.kyanite.article.domain.ModulePermission}.
 */
@RestController
@RequestMapping("/api")
public class ModulePermissionResource {

    private final Logger log = LoggerFactory.getLogger(ModulePermissionResource.class);

    private static final String ENTITY_NAME = "modulePermission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModulePermissionService modulePermissionService;

    private final ModulePermissionRepository modulePermissionRepository;

    public ModulePermissionResource(
        ModulePermissionService modulePermissionService,
        ModulePermissionRepository modulePermissionRepository
    ) {
        this.modulePermissionService = modulePermissionService;
        this.modulePermissionRepository = modulePermissionRepository;
    }

    /**
     * {@code POST  /module-permissions} : Create a new modulePermission.
     *
     * @param modulePermission the modulePermission to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modulePermission, or with status {@code 400 (Bad Request)} if the modulePermission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/module-permissions")
    public ResponseEntity<ModulePermission> createModulePermission(@RequestBody ModulePermission modulePermission)
        throws URISyntaxException {
        log.debug("REST request to save ModulePermission : {}", modulePermission);
        if (modulePermission.getId() != null) {
            throw new BadRequestAlertException("A new modulePermission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModulePermission result = modulePermissionService.save(modulePermission);
        return ResponseEntity
            .created(new URI("/api/module-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /module-permissions/:id} : Updates an existing modulePermission.
     *
     * @param id the id of the modulePermission to save.
     * @param modulePermission the modulePermission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modulePermission,
     * or with status {@code 400 (Bad Request)} if the modulePermission is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modulePermission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/module-permissions/{id}")
    public ResponseEntity<ModulePermission> updateModulePermission(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ModulePermission modulePermission
    ) throws URISyntaxException {
        log.debug("REST request to update ModulePermission : {}, {}", id, modulePermission);
        if (modulePermission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modulePermission.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modulePermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ModulePermission result = modulePermissionService.save(modulePermission);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modulePermission.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /module-permissions/:id} : Partial updates given fields of an existing modulePermission, field will ignore if it is null
     *
     * @param id the id of the modulePermission to save.
     * @param modulePermission the modulePermission to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modulePermission,
     * or with status {@code 400 (Bad Request)} if the modulePermission is not valid,
     * or with status {@code 404 (Not Found)} if the modulePermission is not found,
     * or with status {@code 500 (Internal Server Error)} if the modulePermission couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/module-permissions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ModulePermission> partialUpdateModulePermission(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ModulePermission modulePermission
    ) throws URISyntaxException {
        log.debug("REST request to partial update ModulePermission partially : {}, {}", id, modulePermission);
        if (modulePermission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modulePermission.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modulePermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModulePermission> result = modulePermissionService.partialUpdate(modulePermission);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modulePermission.getId().toString())
        );
    }

    /**
     * {@code GET  /module-permissions} : get all the modulePermissions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modulePermissions in body.
     */
    @GetMapping("/module-permissions")
    public ResponseEntity<List<ModulePermission>> getAllModulePermissions(Pageable pageable) {
        log.debug("REST request to get a page of ModulePermissions");
        Page<ModulePermission> page = modulePermissionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /module-permissions/:id} : get the "id" modulePermission.
     *
     * @param id the id of the modulePermission to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modulePermission, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/module-permissions/{id}")
    public ResponseEntity<ModulePermission> getModulePermission(@PathVariable Long id) {
        log.debug("REST request to get ModulePermission : {}", id);
        Optional<ModulePermission> modulePermission = modulePermissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modulePermission);
    }

    /**
     * {@code DELETE  /module-permissions/:id} : delete the "id" modulePermission.
     *
     * @param id the id of the modulePermission to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/module-permissions/{id}")
    public ResponseEntity<Void> deleteModulePermission(@PathVariable Long id) {
        log.debug("REST request to delete ModulePermission : {}", id);
        modulePermissionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
