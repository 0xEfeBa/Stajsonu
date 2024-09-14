
# Java 17 tabanlı bir resmi image kullanıyoruz
FROM openjdk:17-jdk-slim

# Çalışma dizinini ayarlıyoruz
WORKDIR /app

# Maven veya Gradle kullanıyorsanız, bu satırları kullanarak bağımlılıkları indirir ve cache'leriz
COPY target/messagingapp-0.0.1-SNAPSHOT.jar app.jar

# Uygulamayı çalıştırıyoruz
ENTRYPOINT ["java", "-jar", "app.jar"]
