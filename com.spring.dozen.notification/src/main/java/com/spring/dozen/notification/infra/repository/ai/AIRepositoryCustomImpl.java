package com.spring.dozen.notification.infra.repository.ai;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.dozen.notification.domain.entity.AI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.UUID;

import static com.spring.dozen.notification.domain.entity.QAI.aI;



@RequiredArgsConstructor
public class AIRepositoryCustomImpl implements AIRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AI> searchWithPage(UUID orderId, UUID departureHubId, UUID arrivalHubId, Pageable pageable) {

        BooleanBuilder booleanBuilder = toBooleanBuilder(orderId, departureHubId, arrivalHubId);
        List<AI> contents = queryFactory
                .select(aI)
                .from(aI)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(aI.countDistinct())
                .from(aI)
                .where(booleanBuilder);

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    private BooleanBuilder toBooleanBuilder(UUID orderId, UUID departureHubId, UUID arrivalHubId) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(eqOrderId(orderId));
        booleanBuilder.and(eqDepartureHubId(departureHubId));
        booleanBuilder.and(eqArrivalHubId(arrivalHubId));
        return booleanBuilder;
    }

    private BooleanExpression eqOrderId(UUID orderId) {
        return orderId != null ? aI.orderId.eq(orderId) : null;
    }

    private BooleanExpression eqDepartureHubId(UUID departureHubId) {
        return departureHubId != null ? aI.departureHubId.eq(departureHubId) : null;
    }

    private BooleanExpression eqArrivalHubId(UUID arrivalHubId) {
        return arrivalHubId != null ? aI.arrivalHubId.eq(arrivalHubId) : null;
    }


}
