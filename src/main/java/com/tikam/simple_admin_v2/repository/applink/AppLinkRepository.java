package com.tikam.simple_admin_v2.repository.applink;

import com.tikam.simple_admin_v2.entity.applink.AppLink;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppLinkRepository extends JpaRepository<AppLink, Long> {

    //  using native sql qquery (not recomended error prone)
//    @Query(value = "SELECT * FROM applink WHERE app_id > :cursor ORDER BY app_id ASC", nativeQuery = true)
//    List<AppLink> findNextPagePage(@Param("cursor") Long cursor, Pageable pageable);

    // using jpql (less error prone as using className -> not depend on db table name)
    @Query("SELECT a FROM AppLink a WHERE a.appId > :cursor ORDER BY a.appId ASC")
    List<AppLink> findNextPage(@Param("cursor") Long cursor, Pageable pageable);


    //    List<AppLink> findByAppIdGreaterThanOrderByAppIdAsc(Long appId, Pageable pageable);

}
