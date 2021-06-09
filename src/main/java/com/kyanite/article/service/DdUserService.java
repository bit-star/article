package com.kyanite.article.service;

import com.kyanite.article.domain.DdUser;
import com.kyanite.article.repository.DdUserRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DdUser}.
 */
@Service
@Transactional
public class DdUserService {

    private final Logger log = LoggerFactory.getLogger(DdUserService.class);

    private final DdUserRepository ddUserRepository;

    public DdUserService(DdUserRepository ddUserRepository) {
        this.ddUserRepository = ddUserRepository;
    }

    /**
     * Save a ddUser.
     *
     * @param ddUser the entity to save.
     * @return the persisted entity.
     */
    public DdUser save(DdUser ddUser) {
        log.debug("Request to save DdUser : {}", ddUser);
        return ddUserRepository.save(ddUser);
    }

    /**
     * Partially update a ddUser.
     *
     * @param ddUser the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DdUser> partialUpdate(DdUser ddUser) {
        log.debug("Request to partially update DdUser : {}", ddUser);

        return ddUserRepository
            .findById(ddUser.getId())
            .map(
                existingDdUser -> {
                    if (ddUser.getUnionid() != null) {
                        existingDdUser.setUnionid(ddUser.getUnionid());
                    }
                    if (ddUser.getRemark() != null) {
                        existingDdUser.setRemark(ddUser.getRemark());
                    }
                    if (ddUser.getUserid() != null) {
                        existingDdUser.setUserid(ddUser.getUserid());
                    }
                    if (ddUser.getIsLeaderInDepts() != null) {
                        existingDdUser.setIsLeaderInDepts(ddUser.getIsLeaderInDepts());
                    }
                    if (ddUser.getIsBoss() != null) {
                        existingDdUser.setIsBoss(ddUser.getIsBoss());
                    }
                    if (ddUser.getHiredDate() != null) {
                        existingDdUser.setHiredDate(ddUser.getHiredDate());
                    }
                    if (ddUser.getIsSenior() != null) {
                        existingDdUser.setIsSenior(ddUser.getIsSenior());
                    }
                    if (ddUser.getTel() != null) {
                        existingDdUser.setTel(ddUser.getTel());
                    }
                    if (ddUser.getDepartment() != null) {
                        existingDdUser.setDepartment(ddUser.getDepartment());
                    }
                    if (ddUser.getWorkPlace() != null) {
                        existingDdUser.setWorkPlace(ddUser.getWorkPlace());
                    }
                    if (ddUser.getOrderInDepts() != null) {
                        existingDdUser.setOrderInDepts(ddUser.getOrderInDepts());
                    }
                    if (ddUser.getMobile() != null) {
                        existingDdUser.setMobile(ddUser.getMobile());
                    }
                    if (ddUser.getErrmsg() != null) {
                        existingDdUser.setErrmsg(ddUser.getErrmsg());
                    }
                    if (ddUser.getActive() != null) {
                        existingDdUser.setActive(ddUser.getActive());
                    }
                    if (ddUser.getAvatar() != null) {
                        existingDdUser.setAvatar(ddUser.getAvatar());
                    }
                    if (ddUser.getIsAdmin() != null) {
                        existingDdUser.setIsAdmin(ddUser.getIsAdmin());
                    }
                    if (ddUser.getIsHide() != null) {
                        existingDdUser.setIsHide(ddUser.getIsHide());
                    }
                    if (ddUser.getJobnumber() != null) {
                        existingDdUser.setJobnumber(ddUser.getJobnumber());
                    }
                    if (ddUser.getName() != null) {
                        existingDdUser.setName(ddUser.getName());
                    }
                    if (ddUser.getExtattr() != null) {
                        existingDdUser.setExtattr(ddUser.getExtattr());
                    }
                    if (ddUser.getStateCode() != null) {
                        existingDdUser.setStateCode(ddUser.getStateCode());
                    }
                    if (ddUser.getPosition() != null) {
                        existingDdUser.setPosition(ddUser.getPosition());
                    }
                    if (ddUser.getRoles() != null) {
                        existingDdUser.setRoles(ddUser.getRoles());
                    }

                    return existingDdUser;
                }
            )
            .map(ddUserRepository::save);
    }

    /**
     * Get all the ddUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DdUser> findAll(Pageable pageable) {
        log.debug("Request to get all DdUsers");
        return ddUserRepository.findAll(pageable);
    }

    /**
     * Get all the ddUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DdUser> findAllWithEagerRelationships(Pageable pageable) {
        return ddUserRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one ddUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DdUser> findOne(Long id) {
        log.debug("Request to get DdUser : {}", id);
        return ddUserRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the ddUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DdUser : {}", id);
        ddUserRepository.deleteById(id);
    }
}
