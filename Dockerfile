FROM openjdk:8
copy ./target/bankbalanceValidation-0.0.1-SNAPSHOT.jar bankbalanceValidation-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","bankbalanceValidation-0.0.1-SNAPSHOT.jar"]