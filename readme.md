# Build and Run

Follow the steps below to package the Java application and run it inside a Docker container.

### 1. Clean and Package the Application

First, clean and build the Java project using Maven. This will generate a JAR file in the target/ directory.

```shell
mvn clean package
```

### 2. Build Docker Image

Once the JAR file is created, you can build the Docker image for the application. Use the following command to build the image, replacing <image-name> with the name you want for the Docker image.

```shell
docker build -t ad-event-tracker:latest .
```

### 3. Run the Application with Docker

After successfully building the Docker image, you can run the application in a Docker container using the following command:

```shell
docker run -d --name ad-event-tracker -p 8000:8000 ad-event-tracker:latest
```
### 4. Verify

You can verify that the Docker container is running by checking the logs.

```shell
docker logs ad-event-tracker
```

### 5. API Endpoints

Once the application is running, you can interact with the following API endpoints:
#### 4.1 Goal 2

```shell
curl -X GET http://localhost:8000/api/v1/metrics
```
#### 4.2 Goal 3

```shell
curl -X GET http://localhost:8000/api/v1/performance
```