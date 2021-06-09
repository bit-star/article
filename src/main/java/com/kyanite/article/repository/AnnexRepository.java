package com.kyanite.article.repository;

import com.kyanite.article.domain.Annex;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Annex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnexRepository extends JpaRepository<Annex, Long> {}
