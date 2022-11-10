#Sử dụng base image
FROM --platform=amd64 openjdk:17.0.2-oraclelinux8

#Copy file jar vào trong container
COPY target/user-backend-0.0.1-SNAPSHOT.jar user-backend-0.0.1-SNAPSHOT.jar

#Run ứng dụng
ENTRYPOINT ["java","-jar","/user-backend-0.0.1-SNAPSHOT.jar"]

#Build image

