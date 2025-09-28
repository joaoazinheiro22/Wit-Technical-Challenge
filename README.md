How to Build and Run
1. Start Kafka Infrastructure
In the project's root and with Docker Desktop running.

bash
docker-compose up -d
2. Build Project
Also on the project's root.

mvn clean install
3. Start Rest Module
cd rest
mvn spring-boot:run
The API will start on port 8080.

4. Start Calculator
In a new terminal tab (starting from the project's root).

cd calculator
mvn spring-boot:run
The Calculator will start on port 8081.

Usage
The API exposes the following endpoints:

GET /sum?a={number}&b={number} - Addition
GET /subtraction?a={number}&b={number} - Subtraction
GET /multiplication?a={number}&b={number} - Multiplication
GET /division?a={number}&b={number} - Division
You can test the endpoints using cURL:

bash
curl "http://localhost:8080/sum?a=1&b=2"
curl "http://localhost:8080/subtraction?a=10&b=3"
curl "http://localhost:8080/multiplication?a=4&b=6"
curl "http://localhost:8080/division?a=15&b=3"
