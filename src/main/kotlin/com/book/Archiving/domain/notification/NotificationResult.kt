package com.book.Archiving.domain.notification

data class NotificationResult(
    val success: Boolean,
    val messageId: String? = null,
    val errorMessage: String? = null
)
