package com.kyanite.article.repository;

import com.kyanite.article.domain.SubType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SubType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubTypeRepository extends JpaRepository<SubType, Long> {}
