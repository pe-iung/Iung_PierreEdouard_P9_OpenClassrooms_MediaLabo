MEDILABO - DIABETES RISK ASSESSMENT APPLICATION
=============================================

A microservices-based application for assessing diabetes risk in patients.

SERVICES
--------
Frontend (port 8082): Web interface
Gateway (port 8080): API Gateway
Patient Service (port 8081): Patient management with MySQL
Note Service (port 8083): Patient notes with MongoDB
Risk Assessment Service (port 8084): Diabetes risk evaluation

PREREQUISITES
------------
- Docker and Docker Compose
- Java 21
- MySQL 8.0
- MongoDB

CONFIGURATION
------------
1. Create .env file from .env.example with:

# Database Credentials
MYSQL_ROOT_PASSWORD=your_mysql_password
MYSQL_ROOT_USERNAME=your_mysql_username
MONGO_ROOT_USERNAME=your_mongo_username
MONGO_ROOT_PASSWORD=your_mongo_password

# API Credentials
API_USERNAME=your_api_username
API_PASSWORD=your_api_password

RUNNING THE APPLICATION
---------------------
1. Build and start all services:
   docker-compose up --build

2. Access points:
   - Frontend: http://localhost:8082
   - API Gateway: http://localhost:8080

TEST DATA
---------
The application loads automatically:
- 4 test patients with different risk profiles
- 9 test notes with various medical observations

API documentation and Testing
---------
Use the API credentials configured in .env file to log in.

Patient microservice API documentation
http://localhost:8081/swagger-ui/index.html

Note microservice API documentation
http://localhost:8083/swagger-ui/index.html

Risk microservice API documentation
http://localhost:8084/swagger-ui/index.html

AUTHENTICATION
-------------
Use the API credentials configured in .env file to log in.
