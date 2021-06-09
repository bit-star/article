package com.kyanite.article.service;

import com.kyanite.article.domain.Msg;
import com.kyanite.article.repository.MsgRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Msg}.
 */
@Service
@Transactional
public class MsgService {

    private final Logger log = LoggerFactory.getLogger(MsgService.class);

    private final MsgRepository msgRepository;

    public MsgService(MsgRepository msgRepository) {
        this.msgRepository = msgRepository;
    }

    /**
     * Save a msg.
     *
     * @param msg the entity to save.
     * @return the persisted entity.
     */
    public Msg save(Msg msg) {
        log.debug("Request to save Msg : {}", msg);
        return msgRepository.save(msg);
    }

    /**
     * Partially update a msg.
     *
     * @param msg the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Msg> partialUpdate(Msg msg) {
        log.debug("Request to partially update Msg : {}", msg);

        return msgRepository
            .findById(msg.getId())
            .map(
                existingMsg -> {
                    if (msg.getDeptIdList() != null) {
                        existingMsg.setDeptIdList(msg.getDeptIdList());
                    }
                    if (msg.getUseridList() != null) {
                        existingMsg.setUseridList(msg.getUseridList());
                    }
                    if (msg.getToAllUser() != null) {
                        existingMsg.setToAllUser(msg.getToAllUser());
                    }
                    if (msg.getProgressInPercent() != null) {
                        existingMsg.setProgressInPercent(msg.getProgressInPercent());
                    }
                    if (msg.getTitle() != null) {
                        existingMsg.setTitle(msg.getTitle());
                    }
                    if (msg.getSingleTitle() != null) {
                        existingMsg.setSingleTitle(msg.getSingleTitle());
                    }
                    if (msg.getSingleUrl() != null) {
                        existingMsg.setSingleUrl(msg.getSingleUrl());
                    }
                    if (msg.getCoverUrl() != null) {
                        existingMsg.setCoverUrl(msg.getCoverUrl());
                    }
                    if (msg.getNumberOfShards() != null) {
                        existingMsg.setNumberOfShards(msg.getNumberOfShards());
                    }
                    if (msg.getCompleteSharding() != null) {
                        existingMsg.setCompleteSharding(msg.getCompleteSharding());
                    }
                    if (msg.getMsg() != null) {
                        existingMsg.setMsg(msg.getMsg());
                    }
                    if (msg.getExecuteTime() != null) {
                        existingMsg.setExecuteTime(msg.getExecuteTime());
                    }
                    if (msg.getAgentId() != null) {
                        existingMsg.setAgentId(msg.getAgentId());
                    }
                    if (msg.getType() != null) {
                        existingMsg.setType(msg.getType());
                    }
                    if (msg.getStatus() != null) {
                        existingMsg.setStatus(msg.getStatus());
                    }

                    return existingMsg;
                }
            )
            .map(msgRepository::save);
    }

    /**
     * Get all the msgs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Msg> findAll(Pageable pageable) {
        log.debug("Request to get all Msgs");
        return msgRepository.findAll(pageable);
    }

    /**
     * Get one msg by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Msg> findOne(Long id) {
        log.debug("Request to get Msg : {}", id);
        return msgRepository.findById(id);
    }

    /**
     * Delete the msg by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Msg : {}", id);
        msgRepository.deleteById(id);
    }
}
