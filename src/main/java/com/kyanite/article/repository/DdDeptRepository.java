package com.kyanite.article.repository;

import com.kyanite.article.domain.DdDept;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DdDept entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DdDeptRepository extends JpaRepository<DdDept, Long> {}
