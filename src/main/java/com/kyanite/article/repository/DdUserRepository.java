package com.kyanite.article.repository;

import com.kyanite.article.domain.DdUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DdUser entity.
 */
@Repository
public interface DdUserRepository extends JpaRepository<DdUser, Long> {
    @Query(
        value = "select distinct ddUser from DdUser ddUser left join fetch ddUser.modulePermissions left join fetch ddUser.subTypes",
        countQuery = "select count(distinct ddUser) from DdUser ddUser"
    )
    Page<DdUser> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct ddUser from DdUser ddUser left join fetch ddUser.modulePermissions left join fetch ddUser.subTypes")
    List<DdUser> findAllWithEagerRelationships();

    @Query("select ddUser from DdUser ddUser left join fetch ddUser.modulePermissions left join fetch ddUser.subTypes where ddUser.id =:id")
    Optional<DdUser> findOneWithEagerRelationships(@Param("id") Long id);
}
