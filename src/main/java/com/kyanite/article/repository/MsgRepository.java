package com.kyanite.article.repository;

import com.kyanite.article.domain.Msg;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Msg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MsgRepository extends JpaRepository<Msg, Long> {}
