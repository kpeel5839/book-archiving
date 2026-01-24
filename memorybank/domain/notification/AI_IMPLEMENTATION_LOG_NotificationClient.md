# AI Implementation Log - NotificationClient

## Context
- 기능 / 모듈 이름: 알림 도메인 모델
- 패키지: `com.book.Archiving.domain.notification`
- 파일: `NotificationClient.kt`, `NotificationMessage.kt`, `NotificationResult.kt`
- 작성 일자: 2025-01-24
- 작성 주체: AI (인간 리뷰 예정)

---

## Problem Statement
이 변경으로 해결하려는 문제는 무엇인가?
- FCM을 통한 푸시 알림 발송 기능 구현
- 도메인 계층에서 알림 발송 인터페이스 정의

주어진 요구사항 또는 제약 조건은 무엇이었는가?
- 도메인 계층은 POJO로 유지 (infrastructure 의존성 없음)
- 1 파일 = 1 클래스 규칙
- init 블록을 통한 validation

---

## Design Decisions
### 고려했던 선택지들
- 선택지 A: 알림 발송을 직접 Service에서 FirebaseMessaging 호출
- 선택지 B: NotificationClient 인터페이스를 도메인에 정의하고 infrastructure에서 구현

### 최종 선택
- 선택지 B 선택
- 이유: 도메인 계층의 독립성 유지, 테스트 용이성, DIP 원칙 준수

---

## Trade-offs
- 얻은 장점: 도메인 계층이 FCM에 의존하지 않음, Mock을 통한 테스트 가능
- 감수한 단점: 인터페이스와 구현체 분리로 인한 약간의 복잡성 증가
- 장기적으로 발생할 수 있는 리스크: 없음

---

## Key Implementation Notes
### 핵심 구조
```kotlin
// Interface
interface NotificationClient {
    fun send(message: NotificationMessage): NotificationResult
    fun sendToMultiple(messages: List<NotificationMessage>): List<NotificationResult>
}

// Domain Object with validation
data class NotificationMessage(
    val token: String,
    val title: String,
    val body: String,
    val data: Map<String, String> = emptyMap()
) {
    init {
        require(token.isNotBlank()) { "FCM token must not be blank" }
        require(title.isNotBlank()) { "Notification title must not be blank" }
        require(body.isNotBlank()) { "Notification body must not be blank" }
    }
}
```

### Kotlin 컨벤션 관련 주의한 부분
- data class 사용으로 불변성 보장
- default parameter 사용 (`data: Map<String, String> = emptyMap()`)
- init 블록에서 require를 통한 validation

---

## Future Evolution Points
- 향후 변경 가능성이 높은 지점: Topic 기반 알림 발송 추가
- 남겨둔 TODO 또는 기술 부채: 배치 발송 시 실패 처리 전략 필요

---

## AI Reflection
- 구현 중 확신이 없었던 부분: sendToMultiple의 반환 타입 (BatchResponse vs List<NotificationResult>)
- 인간 리뷰가 반드시 필요한 판단: 대량 발송 시 성능 최적화 방안
- 다음 번에 개선할 수 있는 아이디어: Firebase BatchResponse 활용 검토
