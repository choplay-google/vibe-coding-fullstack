# VibeApp 프로젝트 명세서 (PROJECT_SPEC)

이 문서는 최소 기능 스프링부트 애플리케이션 'VibeApp'의 상세 명세서입니다.

## 1. 프로젝트 설정
- **JDK**: JDK 25 이상 (최신 LTS 이상 권장)
- **Language**: Java
- **Spring Boot**: 4.0.1 이상
- **Build Tool**: Gradle 9.3.0 이상 (Groovy DSL 사용)
- **Dependencies**: 최소 기능 위주 (Spring Boot Starter Web 등)

## 2. 플러그인 구성
- **Spring Boot Plugin**: `org.springframework.boot`
- **Dependency Management**: `io.spring.dependency-management` (Spring Boot 버전에 최적화)

## 3. 프로젝트 메타데이터
- **Group**: `com.example`
- **Artifact**: `vibeapp`
- **Version**: `0.0.1-SNAPSHOT`
- **Main Class**: `com.example.vibeapp.VibeApp`
- **Description**: 최소 기능 스프링부트 애플리케이션을 생성하는 프로젝트다.
- **Configuration**: YAML 파일 (`application.yml`) 기반 설정

## 4. 빌드 스크립트 특이사항
- **Gradle Wrapper**: 9.3.0 이상 버전 적용
- **Toolchain**: Java 25 적용
