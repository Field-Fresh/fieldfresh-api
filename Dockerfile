FROM gradle:5.6.0-jdk11 as builder

WORKDIR /api

COPY build.gradle ./

RUN gradle dependencies

COPY . .

RUN gradle bootJar

FROM openjdk:11-jdk

ENV APP_HOME /api
ENV ENVIRONMENT=dev
ARG AWS_SECRET_ACCESS_KEY_ARG
ARG AWS_ACCESS_KEY_ID_ARG
ARG AWS_REGION
ENV AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY_ARG
ENV AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID_ARG
ENV AWS_REGION=$AWS_REGION
WORKDIR $APP_HOME

EXPOSE "9090"

COPY --from=builder /api/build/libs/* ./

ENTRYPOINT ["java", "-jar", "fieldfresh-api-1.0.jar"]
