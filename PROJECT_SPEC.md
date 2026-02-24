# VibeApp 프로젝트 명세서 (PROJECT_SPEC)

이 문서는 프리미엄 디자인과 기능적 구조를 갖춘 스프링부트 애플리케이션 'VibeApp'의 상세 명세서입니다.

## 1. 기술 스택
- **Back-end**: Java 25, Spring Boot 4.0.1, Gradle 9.3.0
- **Front-end**: Thymeleaf, Tailwind CSS (CDN), Material Symbols Outlined
- **Architecture**: 도메인/기능 중심 패키지 구조 (Functional Package Structure)

## 2. 프로젝트 아키텍처 및 구조

### 패키지 구조 (Backend)
- `com.example.vibeapp.home`: 홈 화면 관련 컨트롤러 및 로직
- `com.example.vibeapp.post`: 게시글 CRUD, 페이징 처리를 포함한 핵심 도메인 로직
  - `Post`: Entity 클래스 (`id` 식별자 사용)
  - `PostRepository`: 데이터 영속성 관리 (In-memory List 기반)
  - `PostService`: 비즈니스 로직 및 트랜잭션 처리
  - `PostController`: 표준 RESTful 관례에 따른 엔드포인트 관리

### 템플릿 구조 (Frontend)
- `src/main/resources/templates/home/`: 홈 화면 템플릿 (`home.html`)
- `src/main/resources/templates/post/`: 게시글 관련 템플릿
  - `posts.html`: 페이징이 적용된 게시글 목록
  - `post_detail.html`: 게시글 상세 조회
  - `post_new_form.html`: 게시글 등록 폼
  - `post_edit_form.html`: 게시글 수정 폼

## 3. 핵심 컨벤션 및 규칙
- **식별자 명명**: 모든 도메인 객체의 식별자는 `id`를 사용합니다 (기존 `no`에서 리팩토링).
- **메서드 명명 표준**:
  - Controller: `list`, `detail`, `save`, `update`, `delete`
  - Service: `findAll`, `findById`, `save`, `update`, `delete`
- **디자인 시스템**: 삼성(Samsung) 스타일의 미니멀리즘과 프리미엄 감성을 지향하며, rounded-full, grayscale 팔레트, accent blue(#135bec)를 활용합니다.

## 4. 주요 기능
- **게시글 관리 (CRUD)**: 게시글의 등록, 조회, 수정, 삭제 기능을 완벽하게 지원합니다.
- **조회수 트래킹**: 게시글 상세 조회 시 자동으로 조회수가 증가합니다.
- **페이징(Pagination)**: 게시글 목록을 페이지당 5개씩 나누어 표시하며, 'Prev/Next' 라벨이 포함된 네비게이션을 제공합니다.
- **애니메이션 및 미세 상호작용**: Hover 효과, 스무스한 전환 등 사용자 경험(UX) 최적화가 적용되어 있습니다.

## 5. 빌드 및 실행
- **Build**: `./gradlew build`
- **Run**: `./gradlew bootRun` (Port: 8080)
