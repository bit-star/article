package com.kyanite.article.repository;

import com.kyanite.article.domain.MsgTask;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MsgTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MsgTaskRepository extends JpaRepository<MsgTask, Long> {}
