#Dockerfile
FROM openjdk:21-ea-24-oreacle

# directorio de trabajo
WORKDIR /app
# jar
COPY target/hotel_reservas-0.0.1-SNAPSHOT.jar app.jar
# wallet
COPY Wallet_HKH8NOR6ID4IU8L4 /app/oracle_wallet/
EXPOSE 8081