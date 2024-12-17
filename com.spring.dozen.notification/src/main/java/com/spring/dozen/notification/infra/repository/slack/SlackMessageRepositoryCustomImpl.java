package com.spring.dozen.notification.infra.repository.slack;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.dozen.notification.domain.entity.QSlackMessage;
import com.spring.dozen.notification.domain.entity.SlackMessage;
import com.spring.dozen.notification.domain.repository.SlackMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.spring.dozen.notification.domain.entity.QSlackMessage.*;

@RequiredArgsConstructor
public class SlackMessageRepositoryCustomImpl implements SlackMessageRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SlackMessage> searchWithPage(Long senderUserId, Long receiverUserId, Pageable pageable) {
        BooleanBuilder booleanBuilder = toBooleanBuilder(senderUserId, receiverUserId);

        List<SlackMessage> contents = queryFactory.select(slackMessage)
                .from(slackMessage)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(slackMessage.countDistinct())
                .from(slackMessage)
                .where(booleanBuilder);

        return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
    }

    private BooleanBuilder toBooleanBuilder(Long senderUserId, Long receiverUserId) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(eqSenderUserId(senderUserId));
        booleanBuilder.and(eqReceiverUserId(receiverUserId));
        return booleanBuilder;
    }

    private BooleanExpression eqSenderUserId(Long senderUserId) {
        return senderUserId != null ? slackMessage.senderUserId.eq(senderUserId) : null;
    }

    private BooleanExpression eqReceiverUserId(Long receiverUserId) {
        return receiverUserId != null ? slackMessage.receiverUserId.eq(receiverUserId) : null;
    }
}
