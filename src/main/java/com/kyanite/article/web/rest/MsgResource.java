package com.kyanite.article.web.rest;

import com.kyanite.article.domain.Msg;
import com.kyanite.article.repository.MsgRepository;
import com.kyanite.article.service.MsgService;
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
 * REST controller for managing {@link com.kyanite.article.domain.Msg}.
 */
@RestController
@RequestMapping("/api")
public class MsgResource {

    private final Logger log = LoggerFactory.getLogger(MsgResource.class);

    private static final String ENTITY_NAME = "msg";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MsgService msgService;

    private final MsgRepository msgRepository;

    public MsgResource(MsgService msgService, MsgRepository msgRepository) {
        this.msgService = msgService;
        this.msgRepository = msgRepository;
    }

    /**
     * {@code POST  /msgs} : Create a new msg.
     *
     * @param msg the msg to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new msg, or with status {@code 400 (Bad Request)} if the msg has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/msgs")
    public ResponseEntity<Msg> createMsg(@RequestBody Msg msg) throws URISyntaxException {
        log.debug("REST request to save Msg : {}", msg);
        if (msg.getId() != null) {
            throw new BadRequestAlertException("A new msg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Msg result = msgService.save(msg);
        return ResponseEntity
            .created(new URI("/api/msgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /msgs/:id} : Updates an existing msg.
     *
     * @param id the id of the msg to save.
     * @param msg the msg to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated msg,
     * or with status {@code 400 (Bad Request)} if the msg is not valid,
     * or with status {@code 500 (Internal Server Error)} if the msg couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/msgs/{id}")
    public ResponseEntity<Msg> updateMsg(@PathVariable(value = "id", required = false) final Long id, @RequestBody Msg msg)
        throws URISyntaxException {
        log.debug("REST request to update Msg : {}, {}", id, msg);
        if (msg.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, msg.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!msgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Msg result = msgService.save(msg);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, msg.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /msgs/:id} : Partial updates given fields of an existing msg, field will ignore if it is null
     *
     * @param id the id of the msg to save.
     * @param msg the msg to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated msg,
     * or with status {@code 400 (Bad Request)} if the msg is not valid,
     * or with status {@code 404 (Not Found)} if the msg is not found,
     * or with status {@code 500 (Internal Server Error)} if the msg couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/msgs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Msg> partialUpdateMsg(@PathVariable(value = "id", required = false) final Long id, @RequestBody Msg msg)
        throws URISyntaxException {
        log.debug("REST request to partial update Msg partially : {}, {}", id, msg);
        if (msg.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, msg.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!msgRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Msg> result = msgService.partialUpdate(msg);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, msg.getId().toString())
        );
    }

    /**
     * {@code GET  /msgs} : get all the msgs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of msgs in body.
     */
    @GetMapping("/msgs")
    public ResponseEntity<List<Msg>> getAllMsgs(Pageable pageable) {
        log.debug("REST request to get a page of Msgs");
        Page<Msg> page = msgService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /msgs/:id} : get the "id" msg.
     *
     * @param id the id of the msg to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the msg, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/msgs/{id}")
    public ResponseEntity<Msg> getMsg(@PathVariable Long id) {
        log.debug("REST request to get Msg : {}", id);
        Optional<Msg> msg = msgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(msg);
    }

    /**
     * {@code DELETE  /msgs/:id} : delete the "id" msg.
     *
     * @param id the id of the msg to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/msgs/{id}")
    public ResponseEntity<Void> deleteMsg(@PathVariable Long id) {
        log.debug("REST request to delete Msg : {}", id);
        msgService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
