FROM maven:3.9.6 as builder
COPY . /wishlist/app
WORKDIR /wishlist/app
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /wishlist/app
COPY --from=builder /wishlist/app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
