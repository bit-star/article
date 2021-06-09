package com.kyanite.article.web.rest;

import com.kyanite.article.domain.MsgTask;
import com.kyanite.article.repository.MsgTaskRepository;
import com.kyanite.article.service.MsgTaskService;
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
 * REST controller for managing {@link com.kyanite.article.domain.MsgTask}.
 */
@RestController
@RequestMapping("/api")
public class MsgTaskResource {

    private final Logger log = LoggerFactory.getLogger(MsgTaskResource.class);

    private static final String ENTITY_NAME = "msgTask";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MsgTaskService msgTaskService;

    private final MsgTaskRepository msgTaskRepository;

    public MsgTaskResource(MsgTaskService msgTaskService, MsgTaskRepository msgTaskRepository) {
        this.msgTaskService = msgTaskService;
        this.msgTaskRepository = msgTaskRepository;
    }

    /**
     * {@code POST  /msg-tasks} : Create a new msgTask.
     *
     * @param msgTask the msgTask to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new msgTask, or with status {@code 400 (Bad Request)} if the msgTask has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/msg-tasks")
    public ResponseEntity<MsgTask> createMsgTask(@RequestBody MsgTask msgTask) throws URISyntaxException {
        log.debug("REST request to save MsgTask : {}", msgTask);
        if (msgTask.getId() != null) {
            throw new BadRequestAlertException("A new msgTask cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MsgTask result = msgTaskService.save(msgTask);
        return ResponseEntity
            .created(new URI("/api/msg-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /msg-tasks/:id} : Updates an existing msgTask.
     *
     * @param id the id of the msgTask to save.
     * @param msgTask the msgTask to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated msgTask,
     * or with status {@code 400 (Bad Request)} if the msgTask is not valid,
     * or with status {@code 500 (Internal Server Error)} if the msgTask couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/msg-tasks/{id}")
    public ResponseEntity<MsgTask> updateMsgTask(@PathVariable(value = "id", required = false) final Long id, @RequestBody MsgTask msgTask)
        throws URISyntaxException {
        log.debug("REST request to update MsgTask : {}, {}", id, msgTask);
        if (msgTask.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, msgTask.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!msgTaskRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MsgTask result = msgTaskService.save(msgTask);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, msgTask.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /msg-tasks/:id} : Partial updates given fields of an existing msgTask, field will ignore if it is null
     *
     * @param id the id of the msgTask to save.
     * @param msgTask the msgTask to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated msgTask,
     * or with status {@code 400 (Bad Request)} if the msgTask is not valid,
     * or with status {@code 404 (Not Found)} if the msgTask is not found,
     * or with status {@code 500 (Internal Server Error)} if the msgTask couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/msg-tasks/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MsgTask> partialUpdateMsgTask(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MsgTask msgTask
    ) throws URISyntaxException {
        log.debug("REST request to partial update MsgTask partially : {}, {}", id, msgTask);
        if (msgTask.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, msgTask.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!msgTaskRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MsgTask> result = msgTaskService.partialUpdate(msgTask);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, msgTask.getId().toString())
        );
    }

    /**
     * {@code GET  /msg-tasks} : get all the msgTasks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of msgTasks in body.
     */
    @GetMapping("/msg-tasks")
    public ResponseEntity<List<MsgTask>> getAllMsgTasks(Pageable pageable) {
        log.debug("REST request to get a page of MsgTasks");
        Page<MsgTask> page = msgTaskService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /msg-tasks/:id} : get the "id" msgTask.
     *
     * @param id the id of the msgTask to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the msgTask, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/msg-tasks/{id}")
    public ResponseEntity<MsgTask> getMsgTask(@PathVariable Long id) {
        log.debug("REST request to get MsgTask : {}", id);
        Optional<MsgTask> msgTask = msgTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(msgTask);
    }

    /**
     * {@code DELETE  /msg-tasks/:id} : delete the "id" msgTask.
     *
     * @param id the id of the msgTask to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/msg-tasks/{id}")
    public ResponseEntity<Void> deleteMsgTask(@PathVariable Long id) {
        log.debug("REST request to delete MsgTask : {}", id);
        msgTaskService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
