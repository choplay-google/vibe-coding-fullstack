# VibeApp 프로젝트 명세서 (PROJECT_SPEC)

이 문서는 스프링부트 애플리케이션 'VibeApp'의 현재 상태를 반영한 상세 명세서입니다.

---

## 1. 기술 스택

| 분류 | 기술 |
|------|------|
| **Language** | Java 21 (Eclipse Temurin 21.0.9 LTS) |
| **Framework** | Spring Boot 4.0.1 |
| **Build Tool** | Gradle 9.3.1 (wrapper), JDK 21 데몬 |
| **ORM / DB 접근** | MyBatis 4.0.0 (mybatis-spring-boot-starter) |
| **Database** | H2 (In-memory, 개발용) |
| **Validation** | Spring Boot Starter Validation (Jakarta Bean Validation) |
| **Front-end** | Thymeleaf, Tailwind CSS (CDN), Material Symbols Outlined |
| **아키텍처** | 도메인/기능 중심 패키지 구조 (Functional Package Structure) |

---

## 2. 프로젝트 아키텍처 및 구조

### 백엔드 패키지 구조

```
com.example.vibeapp
├── VibeApp.java                    # Spring Boot 진입점 (@SpringBootApplication)
├── config/
│   ├── MyBatisConfig.java          # MyBatis 설정, @EnableTransactionManagement,
│   │                               #   DataSourceTransactionManager 빈 등록
│   └── H2ConsoleConfig.java        # H2 콘솔 보안 설정
├── home/
│   └── HomeController.java         # 홈 화면 라우팅
└── post/
    ├── Post.java                   # 게시글 Entity
    ├── PostTag.java                # 태그 Entity
    ├── PostRepository.java         # MyBatis @Mapper (게시글 CRUD + 페이징)
    ├── PostTagRepository.java      # MyBatis @Mapper (태그 CRUD)
    ├── PostService.java            # 비즈니스 로직 (@Transactional 적용)
    ├── PostController.java         # RESTful 엔드포인트
    └── dto/
        ├── PostCreateDto.java      # 게시글 등록 DTO (Bean Validation)
        ├── PostUpdateDto.java      # 게시글 수정 DTO
        ├── PostResponseDTO.java    # 게시글 응답 DTO (Record)
        └── PostListDto.java        # 목록 조회 DTO
```

### 리소스 구조

```
src/main/resources
├── mapper/
│   └── post/
│       ├── PostMapper.xml          # 게시글 SQL 매핑
│       └── PostTagMapper.xml       # 태그 SQL 매핑
└── templates/
    ├── home/home.html              # 홈 화면
    └── post/
        ├── posts.html              # 게시글 목록 (페이징)
        ├── post_detail.html        # 게시글 상세 (태그 표시)
        ├── post_new_form.html      # 게시글 등록 폼 (태그 입력)
        └── post_edit_form.html     # 게시글 수정 폼 (태그 수정)
```

---

## 3. 도메인 모델

### Post (게시글)
| 필드 | 타입 | 설명 |
|------|------|------|
| `no` | `Long` | 게시글 식별자 (PK) |
| `title` | `String` | 제목 (필수, 최대 100자) |
| `content` | `String` | 내용 |
| `createdAt` | `LocalDateTime` | 등록일시 |
| `updatedAt` | `LocalDateTime` | 수정일시 |
| `views` | `int` | 조회수 |

### PostTag (게시글 태그)
| 필드 | 타입 | 설명 |
|------|------|------|
| `id` | `Long` | 태그 식별자 (PK) |
| `postNo` | `Long` | 게시글 FK |
| `tagName` | `String` | 태그명 |

---

## 4. 핵심 기능

### 게시글 관리 (CRUD)
- **등록** `POST /posts/add`: `PostCreateDto` 수신, Bean Validation 적용, `@Transactional`로 게시글+태그 원자적 저장
- **목록** `GET /posts`: 페이지당 5개 페이징, `PostListDto` 반환
- **상세** `GET /posts/{no}`: 조회수 자동 증가, 태그 함께 반환
- **수정** `PUT /posts/{no}`: `PostUpdateDto` 수신, `@Transactional`로 게시글+태그 원자적 갱신
- **삭제** `DELETE /posts/{no}`: 게시글 삭제

### 트랜잭션 관리
- `MyBatisConfig`에 `DataSourceTransactionManager` 빈 등록, `@EnableTransactionManagement` 활성화
- `PostService.save()`: 게시글 저장 + 태그 저장 → 하나의 트랜잭션 (실패 시 전체 롤백)
- `PostService.update()`: 게시글 수정 + 태그 삭제/재저장 → 하나의 트랜잭션 (실패 시 전체 롤백)

### 태그 기능
- 쉼표(`,`) 구분으로 복수 태그 입력
- 게시글 상세 페이지에서 태그 표시
- 수정 시 기존 태그 전체 삭제 후 재저장

### 기타
- **조회수 트래킹**: 상세 조회 시 자동 증가 (`incrementViews`)
- **페이징**: 목록 5개/페이지, Prev/Next 네비게이션

---

## 5. DTO 패턴 및 유효성 검사

- **`PostCreateDto`**: `@NotBlank`, `@Size(max=100)` 등 Bean Validation 적용
- **`PostResponseDTO`**: Java Record 타입 (불변)
- **`PostListDto`**, **`PostUpdateDto`**: 일반 DTO 클래스

---

## 6. 핵심 컨벤션

| 항목 | 규칙 |
|------|------|
| 식별자 | 게시글: `no` / 태그: `id` |
| Controller 메서드 | `list`, `detail`, `save`, `update`, `delete` |
| Service 메서드 | `findAll`, `findById`, `save`, `update`, `delete` |
| 디자인 시스템 | 삼성 스타일 미니멀리즘, rounded-full, grayscale 팔레트, accent blue `#135bec` |

---

## 7. 빌드 및 실행

```powershell
# 빌드 (테스트 제외)
.\gradlew build -x test

# 서버 실행 (Port: 8080)
.\gradlew bootRun

# 접속
http://localhost:8080
```

### Gradle 환경
```properties
# gradle.properties
org.gradle.java.home=C:/Users/K/.vscode/extensions/redhat.java-1.52.0-win32-x64/jre/21.0.9-win32-x86_64

# gradle/wrapper/gradle-wrapper.properties
distributionUrl=https\://services.gradle.org/distributions/gradle-9.3.1-bin.zip
```
