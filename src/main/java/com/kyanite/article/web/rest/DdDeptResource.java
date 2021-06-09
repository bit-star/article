package com.kyanite.article.web.rest;

import com.kyanite.article.domain.DdDept;
import com.kyanite.article.repository.DdDeptRepository;
import com.kyanite.article.service.DdDeptService;
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
 * REST controller for managing {@link com.kyanite.article.domain.DdDept}.
 */
@RestController
@RequestMapping("/api")
public class DdDeptResource {

    private final Logger log = LoggerFactory.getLogger(DdDeptResource.class);

    private static final String ENTITY_NAME = "ddDept";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DdDeptService ddDeptService;

    private final DdDeptRepository ddDeptRepository;

    public DdDeptResource(DdDeptService ddDeptService, DdDeptRepository ddDeptRepository) {
        this.ddDeptService = ddDeptService;
        this.ddDeptRepository = ddDeptRepository;
    }

    /**
     * {@code POST  /dd-depts} : Create a new ddDept.
     *
     * @param ddDept the ddDept to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ddDept, or with status {@code 400 (Bad Request)} if the ddDept has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dd-depts")
    public ResponseEntity<DdDept> createDdDept(@RequestBody DdDept ddDept) throws URISyntaxException {
        log.debug("REST request to save DdDept : {}", ddDept);
        if (ddDept.getId() != null) {
            throw new BadRequestAlertException("A new ddDept cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DdDept result = ddDeptService.save(ddDept);
        return ResponseEntity
            .created(new URI("/api/dd-depts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dd-depts/:id} : Updates an existing ddDept.
     *
     * @param id the id of the ddDept to save.
     * @param ddDept the ddDept to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ddDept,
     * or with status {@code 400 (Bad Request)} if the ddDept is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ddDept couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dd-depts/{id}")
    public ResponseEntity<DdDept> updateDdDept(@PathVariable(value = "id", required = false) final Long id, @RequestBody DdDept ddDept)
        throws URISyntaxException {
        log.debug("REST request to update DdDept : {}, {}", id, ddDept);
        if (ddDept.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ddDept.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ddDeptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DdDept result = ddDeptService.save(ddDept);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ddDept.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dd-depts/:id} : Partial updates given fields of an existing ddDept, field will ignore if it is null
     *
     * @param id the id of the ddDept to save.
     * @param ddDept the ddDept to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ddDept,
     * or with status {@code 400 (Bad Request)} if the ddDept is not valid,
     * or with status {@code 404 (Not Found)} if the ddDept is not found,
     * or with status {@code 500 (Internal Server Error)} if the ddDept couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dd-depts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DdDept> partialUpdateDdDept(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DdDept ddDept
    ) throws URISyntaxException {
        log.debug("REST request to partial update DdDept partially : {}, {}", id, ddDept);
        if (ddDept.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ddDept.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ddDeptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DdDept> result = ddDeptService.partialUpdate(ddDept);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ddDept.getId().toString())
        );
    }

    /**
     * {@code GET  /dd-depts} : get all the ddDepts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ddDepts in body.
     */
    @GetMapping("/dd-depts")
    public ResponseEntity<List<DdDept>> getAllDdDepts(Pageable pageable) {
        log.debug("REST request to get a page of DdDepts");
        Page<DdDept> page = ddDeptService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dd-depts/:id} : get the "id" ddDept.
     *
     * @param id the id of the ddDept to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ddDept, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dd-depts/{id}")
    public ResponseEntity<DdDept> getDdDept(@PathVariable Long id) {
        log.debug("REST request to get DdDept : {}", id);
        Optional<DdDept> ddDept = ddDeptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ddDept);
    }

    /**
     * {@code DELETE  /dd-depts/:id} : delete the "id" ddDept.
     *
     * @param id the id of the ddDept to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dd-depts/{id}")
    public ResponseEntity<Void> deleteDdDept(@PathVariable Long id) {
        log.debug("REST request to delete DdDept : {}", id);
        ddDeptService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
