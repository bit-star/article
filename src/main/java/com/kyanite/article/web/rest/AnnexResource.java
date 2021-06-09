package com.kyanite.article.web.rest;

import com.kyanite.article.domain.Annex;
import com.kyanite.article.repository.AnnexRepository;
import com.kyanite.article.service.AnnexService;
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
 * REST controller for managing {@link com.kyanite.article.domain.Annex}.
 */
@RestController
@RequestMapping("/api")
public class AnnexResource {

    private final Logger log = LoggerFactory.getLogger(AnnexResource.class);

    private static final String ENTITY_NAME = "annex";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnnexService annexService;

    private final AnnexRepository annexRepository;

    public AnnexResource(AnnexService annexService, AnnexRepository annexRepository) {
        this.annexService = annexService;
        this.annexRepository = annexRepository;
    }

    /**
     * {@code POST  /annexes} : Create a new annex.
     *
     * @param annex the annex to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new annex, or with status {@code 400 (Bad Request)} if the annex has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/annexes")
    public ResponseEntity<Annex> createAnnex(@RequestBody Annex annex) throws URISyntaxException {
        log.debug("REST request to save Annex : {}", annex);
        if (annex.getId() != null) {
            throw new BadRequestAlertException("A new annex cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Annex result = annexService.save(annex);
        return ResponseEntity
            .created(new URI("/api/annexes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /annexes/:id} : Updates an existing annex.
     *
     * @param id the id of the annex to save.
     * @param annex the annex to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annex,
     * or with status {@code 400 (Bad Request)} if the annex is not valid,
     * or with status {@code 500 (Internal Server Error)} if the annex couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/annexes/{id}")
    public ResponseEntity<Annex> updateAnnex(@PathVariable(value = "id", required = false) final Long id, @RequestBody Annex annex)
        throws URISyntaxException {
        log.debug("REST request to update Annex : {}, {}", id, annex);
        if (annex.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, annex.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!annexRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Annex result = annexService.save(annex);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, annex.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /annexes/:id} : Partial updates given fields of an existing annex, field will ignore if it is null
     *
     * @param id the id of the annex to save.
     * @param annex the annex to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annex,
     * or with status {@code 400 (Bad Request)} if the annex is not valid,
     * or with status {@code 404 (Not Found)} if the annex is not found,
     * or with status {@code 500 (Internal Server Error)} if the annex couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/annexes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Annex> partialUpdateAnnex(@PathVariable(value = "id", required = false) final Long id, @RequestBody Annex annex)
        throws URISyntaxException {
        log.debug("REST request to partial update Annex partially : {}, {}", id, annex);
        if (annex.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, annex.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!annexRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Annex> result = annexService.partialUpdate(annex);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, annex.getId().toString())
        );
    }

    /**
     * {@code GET  /annexes} : get all the annexes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of annexes in body.
     */
    @GetMapping("/annexes")
    public ResponseEntity<List<Annex>> getAllAnnexes(Pageable pageable) {
        log.debug("REST request to get a page of Annexes");
        Page<Annex> page = annexService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /annexes/:id} : get the "id" annex.
     *
     * @param id the id of the annex to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the annex, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/annexes/{id}")
    public ResponseEntity<Annex> getAnnex(@PathVariable Long id) {
        log.debug("REST request to get Annex : {}", id);
        Optional<Annex> annex = annexService.findOne(id);
        return ResponseUtil.wrapOrNotFound(annex);
    }

    /**
     * {@code DELETE  /annexes/:id} : delete the "id" annex.
     *
     * @param id the id of the annex to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/annexes/{id}")
    public ResponseEntity<Void> deleteAnnex(@PathVariable Long id) {
        log.debug("REST request to delete Annex : {}", id);
        annexService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
