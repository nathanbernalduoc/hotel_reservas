#Dockerfile
#FROM openjdk:22-ea-jdk
FROM openjdk:21-ea-24-oracle

# directorio de trabajo
WORKDIR /app
# jar
COPY target/hotel_reservas-0.0.1-SNAPSHOT.jar /app/app.jar
# wallet
COPY Wallet_QSYVMD9J8GOANRS4 /app/oracle_wallet/
EXPOSE 8081

