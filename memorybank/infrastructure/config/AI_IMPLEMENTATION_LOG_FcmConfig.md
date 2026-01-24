# AI Implementation Log - FcmConfig

## Context
- 기능 / 모듈 이름: Firebase 설정
- 패키지: `com.book.Archiving.infrastructure.config`
- 파일: `FcmConfig.kt`
- 작성 일자: 2025-01-24
- 작성 주체: AI (인간 리뷰 예정)

---

## Problem Statement
이 변경으로 해결하려는 문제는 무엇인가?
- Firebase Admin SDK 초기화 및 Bean 등록
- Service Account JSON 파일을 통한 인증 설정

주어진 요구사항 또는 제약 조건은 무엇이었는가?
- 1 메서드 = 15줄 이하 규칙
- 환경별 설정 분리 (application.yml)

---

## Design Decisions
### 고려했던 선택지들
- 선택지 A: 환경변수에서 직접 credentials 읽기
- 선택지 B: ClassPathResource로 JSON 파일 읽기
- 선택지 C: 환경변수로 파일 경로 지정하고 ClassPathResource로 읽기

### 최종 선택
- 선택지 C 선택
- 이유: 개발/운영 환경별 다른 credentials 파일 사용 가능, 보안상 JSON 파일을 Git에 포함하지 않음

---

## Trade-offs
- 얻은 장점: 환경별 유연한 설정, 보안 파일 분리
- 감수한 단점: 배포 시 별도 파일 관리 필요
- 장기적으로 발생할 수 있는 리스크: 파일 누락 시 앱 시작 실패

---

## Key Implementation Notes
### 핵심 구조
```kotlin
@Configuration
class FcmConfig {
    @Value("\${firebase.credentials-path}")
    private lateinit var credentialsPath: String

    @Bean
    fun firebaseApp(): FirebaseApp {
        if (FirebaseApp.getApps().isNotEmpty()) {
            return FirebaseApp.getInstance()
        }
        val options = buildFirebaseOptions()
        return FirebaseApp.initializeApp(options)
    }
}
```

### 설정 위치
- `application.yml`: `firebase.credentials-path` 설정
- 실제 credentials 파일: `src/main/resources/firebase/service-account.json` (gitignore 권장)

### Kotlin 컨벤션 관련 주의한 부분
- 15줄 규칙 준수를 위해 buildFirebaseOptions, loadCredentials 메서드 분리
- lateinit var 사용 (Spring @Value 주입)

---

## Future Evolution Points
- 향후 변경 가능성이 높은 지점: Secret Manager 연동
- 남겨둔 TODO 또는 기술 부채: 초기화 실패 시 graceful degradation

---

## AI Reflection
- 구현 중 확신이 없었던 부분: FirebaseApp 중복 초기화 처리 방법
- 인간 리뷰가 반드시 필요한 판단: credentials 파일 관리 방안 (AWS Secrets Manager, GCP Secret Manager 등)
- 다음 번에 개선할 수 있는 아이디어: @ConditionalOnProperty로 FCM 비활성화 옵션 추가
