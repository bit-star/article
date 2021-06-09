package com.kyanite.article.repository;

import com.kyanite.article.domain.ModulePermission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ModulePermission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModulePermissionRepository extends JpaRepository<ModulePermission, Long> {}
