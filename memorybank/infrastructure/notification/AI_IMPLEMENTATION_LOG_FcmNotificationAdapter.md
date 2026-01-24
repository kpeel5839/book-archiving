# AI Implementation Log - FcmNotificationAdapter

## Context
- 기능 / 모듈 이름: FCM 알림 발송 Adapter
- 패키지: `com.book.Archiving.infrastructure.notification`
- 파일: `FcmNotificationAdapter.kt`
- 작성 일자: 2025-01-24
- 작성 주체: AI (인간 리뷰 예정)

---

## Problem Statement
이 변경으로 해결하려는 문제는 무엇인가?
- NotificationClient 인터페이스의 FCM 기반 구현
- Firebase Admin SDK를 활용한 푸시 알림 발송

주어진 요구사항 또는 제약 조건은 무엇이었는가?
- 1 메서드 = 15줄 이하 규칙
- companion object는 클래스 최상단에 배치
- Named argument 사용 (2개 이상의 파라미터)

---

## Design Decisions
### 고려했던 선택지들
- 선택지 A: FirebaseMessaging을 직접 생성하여 사용
- 선택지 B: FirebaseMessaging을 Bean으로 주입받아 사용

### 최종 선택
- 선택지 B 선택
- 이유: 테스트 시 Mock 주입 용이, 설정의 중앙화

---

## Trade-offs
- 얻은 장점: DI를 통한 테스트 용이성, FcmConfig에서 설정 관리
- 감수한 단점: 설정 클래스 추가로 인한 파일 증가
- 장기적으로 발생할 수 있는 리스크: Firebase 초기화 순서 문제 가능성

---

## Key Implementation Notes
### 핵심 구조
```kotlin
@Component
class FcmNotificationAdapter(
    private val firebaseMessaging: FirebaseMessaging
) : NotificationClient {

    companion object {
        private val logger = LoggerFactory.getLogger(FcmNotificationAdapter::class.java)
    }

    override fun send(message: NotificationMessage): NotificationResult {
        return try {
            val fcmMessage = buildFcmMessage(message)
            val messageId = firebaseMessaging.send(fcmMessage)
            NotificationResult(success = true, messageId = messageId)
        } catch (e: Exception) {
            handleSendError(e)
        }
    }
}
```

### Kotlin 컨벤션 관련 주의한 부분
- companion object 최상단 배치
- 15줄 규칙 준수를 위해 buildFcmMessage, buildNotification, handleSendError 메서드 분리
- try-catch를 expression으로 사용

### 성능 / 동시성 / 확장성 고려 사항
- 현재 sendToMultiple은 순차적으로 처리 (대량 발송 시 개선 필요)

---

## Future Evolution Points
- 향후 변경 가능성이 높은 지점: sendAll API를 사용한 배치 발송 최적화
- 남겨둔 TODO 또는 기술 부채: 재시도 로직, 실패한 토큰 처리

---

## AI Reflection
- 구현 중 확신이 없었던 부분: Exception 처리 세분화 (FirebaseMessagingException vs 일반 Exception)
- 인간 리뷰가 반드시 필요한 판단: 에러 로깅 레벨 및 모니터링 방안
- 다음 번에 개선할 수 있는 아이디어: Circuit Breaker 패턴 적용 검토
