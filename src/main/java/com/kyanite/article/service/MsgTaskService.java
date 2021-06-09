package com.kyanite.article.service;

import com.kyanite.article.domain.MsgTask;
import com.kyanite.article.repository.MsgTaskRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MsgTask}.
 */
@Service
@Transactional
public class MsgTaskService {

    private final Logger log = LoggerFactory.getLogger(MsgTaskService.class);

    private final MsgTaskRepository msgTaskRepository;

    public MsgTaskService(MsgTaskRepository msgTaskRepository) {
        this.msgTaskRepository = msgTaskRepository;
    }

    /**
     * Save a msgTask.
     *
     * @param msgTask the entity to save.
     * @return the persisted entity.
     */
    public MsgTask save(MsgTask msgTask) {
        log.debug("Request to save MsgTask : {}", msgTask);
        return msgTaskRepository.save(msgTask);
    }

    /**
     * Partially update a msgTask.
     *
     * @param msgTask the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MsgTask> partialUpdate(MsgTask msgTask) {
        log.debug("Request to partially update MsgTask : {}", msgTask);

        return msgTaskRepository
            .findById(msgTask.getId())
            .map(
                existingMsgTask -> {
                    if (msgTask.getUseridList() != null) {
                        existingMsgTask.setUseridList(msgTask.getUseridList());
                    }
                    if (msgTask.getTaskId() != null) {
                        existingMsgTask.setTaskId(msgTask.getTaskId());
                    }
                    if (msgTask.getRspMsg() != null) {
                        existingMsgTask.setRspMsg(msgTask.getRspMsg());
                    }
                    if (msgTask.getStatus() != null) {
                        existingMsgTask.setStatus(msgTask.getStatus());
                    }
                    if (msgTask.getProgressInPercent() != null) {
                        existingMsgTask.setProgressInPercent(msgTask.getProgressInPercent());
                    }
                    if (msgTask.getInvalidUserIdList() != null) {
                        existingMsgTask.setInvalidUserIdList(msgTask.getInvalidUserIdList());
                    }
                    if (msgTask.getForbiddenUserIdList() != null) {
                        existingMsgTask.setForbiddenUserIdList(msgTask.getForbiddenUserIdList());
                    }
                    if (msgTask.getFailedUserIdList() != null) {
                        existingMsgTask.setFailedUserIdList(msgTask.getFailedUserIdList());
                    }
                    if (msgTask.getReadUserIdList() != null) {
                        existingMsgTask.setReadUserIdList(msgTask.getReadUserIdList());
                    }
                    if (msgTask.getUnreadUserIdList() != null) {
                        existingMsgTask.setUnreadUserIdList(msgTask.getUnreadUserIdList());
                    }
                    if (msgTask.getInvalidDeptIdList() != null) {
                        existingMsgTask.setInvalidDeptIdList(msgTask.getInvalidDeptIdList());
                    }
                    if (msgTask.getWithdraw() != null) {
                        existingMsgTask.setWithdraw(msgTask.getWithdraw());
                    }

                    return existingMsgTask;
                }
            )
            .map(msgTaskRepository::save);
    }

    /**
     * Get all the msgTasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MsgTask> findAll(Pageable pageable) {
        log.debug("Request to get all MsgTasks");
        return msgTaskRepository.findAll(pageable);
    }

    /**
     * Get one msgTask by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MsgTask> findOne(Long id) {
        log.debug("Request to get MsgTask : {}", id);
        return msgTaskRepository.findById(id);
    }

    /**
     * Delete the msgTask by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MsgTask : {}", id);
        msgTaskRepository.deleteById(id);
    }
}
