package com.tikam.simple_admin_v2.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikam.simple_admin_v2.dto.device.DeviceStatQueryDto;
import com.tikam.simple_admin_v2.dto.device.DeviceStateDto;
import com.tikam.simple_admin_v2.dto.device.QDeviceStateDto;
import com.tikam.simple_admin_v2.entity.*;
import com.tikam.simple_admin_v2.entity.theme.QThemeVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class DeviceQueryRepository {
    private  final JPAQueryFactory jpaQueryFactory;

    private QDeviceTbt themeVersion = QDeviceTbt.deviceTbt;
    private QUserDevice userDevice = QUserDevice.userDevice;
    private QUser user = QUser.user;
    private QMsgPrxConn msgPrxConn = QMsgPrxConn.msgPrxConn;
    private QCSQ_TBT qcsqTbt = QCSQ_TBT.cSQ_TBT;
    private QDeviceTbt deviceTbt = QDeviceTbt.deviceTbt;

    public long getDeviceCount(DeviceStatQueryDto deviceStatQueryDto) {
        String companyCode = deviceStatQueryDto.getCompanyCode();
        String subOrgCode = deviceStatQueryDto.getSubOrgCode();
        List<Integer> deviceTypeCode = deviceStatQueryDto.getOsTypes();
        String appVersion = deviceStatQueryDto.getAppVersion();
        System.out.println(companyCode);
        System.out.println(subOrgCode);
        System.out.println(appVersion);

        long deviceCount =  jpaQueryFactory.select(deviceTbt.count())
                .from(user)
                .innerJoin(qcsqTbt)
                .on(user.userEpId.eq(qcsqTbt.employeeId))
                .innerJoin(userDevice)
                .on(userDevice.userId.eq(user.userId))
                .innerJoin(deviceTbt)
                .on(deviceTbt.dvcId.eq(userDevice.dvcId))
                .leftJoin(msgPrxConn)
                .on(userDevice.dvcId.eq(msgPrxConn.dvcId))
                .where(qcsqTbt.companyCode.eq(companyCode))
                .where(qcsqTbt.subOrgCode.eq(subOrgCode))
//                .where(deviceTbt.appVersion.eq(appVersion))
//                .where(eqOrgCode(qcsqTbt.companyCode,companyCode))
//                .where(eqOrgCode(qcsqTbt.subOrgCode,subOrgCode))
//                .where(containsNullAndSafe(deviceTbt.appVersion,appVersion))
                .fetchOne();
        return deviceCount;
    }

    private BooleanExpression inEmptySafe(NumberPath<Integer> numberPath, Collection<Integer> collections) {
        return CollectionUtils.isEmpty(collections) ? null : numberPath.in(collections);
    }

    private BooleanExpression eqOrgCode(StringPath stringPath, String word) {
        return StringUtils.hasText(word) && !"ALL".equals(word) ? stringPath.eq(word) : null;
    }

    private BooleanExpression containsNullAndSafe(StringPath stringPath, String word) {
        return StringUtils.hasText(word) ? stringPath.contains(word) : null;
    }


    public List<DeviceStateDto> getDeviceStats(DeviceStatQueryDto deviceStatQueryDto, long offset, int limit) {
        String companyCode = deviceStatQueryDto.getCompanyCode();
        String subOrgCode = deviceStatQueryDto.getSubOrgCode();
        List<Integer> deviceTypeCode = deviceStatQueryDto.getDeviceType();
        List<Integer> osTypes = deviceStatQueryDto.getOsTypes();
//         StringPath position = new StringPath("senior executive"); // Not needed if passing literal

        return jpaQueryFactory.select(
                new QDeviceStateDto(
                        qcsqTbt.companyName,      // 1. companyName
                        qcsqTbt.companyCode,      // 2. companyCode
                        qcsqTbt.subOrgCode,       // 3. subOrgCode
                        qcsqTbt.subOrgCode,       // 4. subOrgName (mapped from subOrgCode as per entity)
                        qcsqTbt.departmentCode,   // 5. deptName (mapped from departmentCode as per entity)
                        user.localName,           // 6. localName
                        user.singleId,       // 7. position (literal)
                        user.globalName,          // 8. globalName
                        deviceTbt.appVersion,     // 9. appVersion
                        deviceTbt.osVersion,     // 10. osType (from DeviceTbt entity)
                        deviceTbt.osVersion       // 11. osVersion
                )
        ) .from(user)
                .innerJoin(qcsqTbt)
                .on(user.userEpId.eq(qcsqTbt.employeeId))
                .innerJoin(userDevice)
                .on(userDevice.userId.eq(user.userId))
                .innerJoin(deviceTbt)
                .on(deviceTbt.dvcId.eq(userDevice.dvcId))
                .leftJoin(msgPrxConn)
                .on(userDevice.dvcId.eq(msgPrxConn.dvcId))
                .where(qcsqTbt.companyCode.eq(companyCode))
                .where(qcsqTbt.subOrgCode.eq(subOrgCode))
                .orderBy(qcsqTbt.companyCode.asc(),
                        qcsqTbt.subOrgCode.asc(),
                        user.localName.asc(),
                        deviceTbt.deviceType.asc()
                        )
                .offset(offset)
                .limit(limit).
                fetch();
    }
}
