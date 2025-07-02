MEDILABO - DIABETES RISK ASSESSMENT APPLICATION
=============================================

A microservices-based application for assessing diabetes risk in patients.

![Image](https://github.com/user-attachments/assets/e25d3f24-34a1-4b36-b89f-d91f0e3f5284)

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

Database Credentials
MYSQL_ROOT_PASSWORD
MYSQL_ROOT_USERNAME
MONGO_ROOT_USERNAME
MONGO_ROOT_PASSWORD

API Credentials (also used for webapp login with username/password)
API_USERNAME
API_PASSWORD

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

GREEN CODE
-------------
## What is Green Code?

Green code (or software eco-design) is a development approach that aims to reduce the environmental impact of computer applications. It involves optimizing code to:
• Reduce energy consumption
• Minimize resource usage (CPU, memory, storage, network)
• Improve algorithm efficiency
• Extend equipment lifespan
• Reduce software carbon footprint

Green code is part of a broader sustainable development approach applied to IT.

## What are the best practices for Green Code?

1. Query and Data Optimization
   - Limit network requests
   - Optimize SQL queries
   - Cache frequently used data
   - Compress transmitted data

2. Efficient Resource Management
   - Release unused resources

3. Architecture and Design
   - Favor efficient algorithms
   - Avoid dead and redundant code
   - Use lightweight microservices

4. Coding Best Practices
   - Optimize loops and conditions
   - Avoid unnecessary calculations
   - Use appropriate data structures

5. Infrastructure and Deployment
   - Properly size resources
   - Use lightweight containers

## What are the improvement areas for the MediLabo project to comply with Green Code?

1. Microservices Optimization
   - Implement a distributed cache system for frequent data

2. Data Management
   - Implement pagination to limit data volume

3. Architecture
   - Implement a sleep mode system for rarely used services

4. Specific Code
   - Optimize algorithms
   - Review log management to reduce their impact

5. Infrastructure
   - Optimize persisted data volumes
   - Implement consumption metrics

These improvements would significantly reduce the project's environmental footprint while maintaining its functionality.
