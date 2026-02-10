package com.tikam.simple_admin_v2.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikam.simple_admin_v2.entity.theme.QThemeManagement;
import com.tikam.simple_admin_v2.entity.theme.QThemeVersion;
import com.tikam.simple_admin_v2.entity.theme.ThemeVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ThemeManagementQueryRepository {
    private  final JPAQueryFactory jpaQueryFactory;
    private QThemeVersion themeVersion = QThemeVersion.themeVersion;

    public Page<ThemeVersion> findVersionsByThemeId(Long themeManagementId , int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        // 1. fetch list (with pagination)
        List<ThemeVersion> versions = jpaQueryFactory
                .selectFrom(themeVersion)
                .where(themeVersion.themeManagementId.eq(themeManagementId))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        // 2. fetch total count
        Long total = jpaQueryFactory
                .select(themeVersion.count())
                .from(themeVersion) // Added missing .from() clause
                .where(themeVersion.themeManagementId.eq(themeManagementId))
                .fetchOne();

        long totalCount = total == null ? 0 : total;

        return new PageImpl<>(versions, pageRequest, totalCount);
    }


}
