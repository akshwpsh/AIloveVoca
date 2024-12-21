# 1. Java 베이스 이미지 설정 (JDK 17 사용 예)
FROM eclipse-temurin:17-jdk-alpine

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. JAR 파일 복사
COPY build/libs/Hello_AI-0.0.1-SNAPSHOT.jar app.jar

# 4. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
