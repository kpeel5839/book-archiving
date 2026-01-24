package com.book.Archiving.domain.fixture

import com.book.Archiving.domain.common.RandomGenerator
import com.book.Archiving.domain.notification.NotificationMessage

object NotificationMessageFixture {

    fun generate(
        token: String = RandomGenerator.generateNonNullString(20),
        title: String = RandomGenerator.generateNonNullString(10),
        body: String = RandomGenerator.generateNonNullString(50),
        data: Map<String, String> = emptyMap()
    ): NotificationMessage {
        return NotificationMessage(
            token = token,
            title = title,
            body = body,
            data = data
        )
    }

}
