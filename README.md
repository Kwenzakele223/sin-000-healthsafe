# HealthSafe

HealthSafe is a hospital system that manages information about hospital wards, emergency levels, staff schedules, and equipment problems.

The project shows how different services can communicate with each other using **REST APIs, HTTP, and ActiveMQ messaging**.

## Services

| Service                 | Port | What it does                                                   |
| ----------------------- | ---: | -------------------------------------------------------------- |
| Ingestion Service       | 7030 | Reads and cleans ward data from a CSV file                     |
| Ward Service            | 7031 | Provides ward and department information                       |
| Alert Level Service     | 7032 | Provides the current emergency level                           |
| Staffing Service        | 7033 | Provides staff schedules based on the ward and emergency level |
| Equipment Alert Service | 7034 | Receives equipment failure alerts                              |

## How the Services Work Together

```text
CSV File
   |
   v
Ingestion Service
   |
   v
Ward Service
   |
   +----> Alert Level Service
   |
   v
Staffing Service
```

The project also uses ActiveMQ for messages:

```text
Staffing Service
      |
      v
ActiveMQ Topic
      |
      v
Ward Service
```

```text
Ward Service
      |
      v
ActiveMQ Queue
      |
      v
Equipment Alert Service
```

## REST Endpoints

### Ingestion Service

```text
GET /wards
```

### Ward Service

```text
GET /wards
GET /wards/{id}
GET /departments
POST /wards/{id}/equipment-failure
```

### Alert Level Service

```text
GET /alert-level
```

### Staffing Service

```text
GET /wards/{id}/schedule
```

## Messaging

ActiveMQ is used for communication that does not need an immediate response.

**Staffing updates**

```text
staffing-events-topic
```

**Equipment failure alerts**

```text
equipment-failure-queue
```

## Technologies

* Java 17
* Maven
* Javalin
* Jackson
* Apache ActiveMQ
* JMS
* Docker
* REST APIs
* JUnit 5

## Running the Project

Start ActiveMQ:

```bash
cd common
docker compose up -d
```

Build a service:

```bash
cd <service-directory>
mvn clean package
```

Run the service:

```bash
java -jar target/<service-name>.jar
```

The services use ports **7030 to 7034**.

## Testing

The project has JUnit tests for the main services.

The ActiveMQ communication was also tested by running the services and checking that messages were successfully sent and received.

## Status

The main HealthSafe features are implemented:

* CSV data cleaning
* REST APIs
* Service-to-service communication
* Staffing updates using ActiveMQ
* Equipment failure alerts using ActiveMQ
* Automated tests
* Docker setup for ActiveMQ
