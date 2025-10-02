# User Card Service Application
## This is a Spring Boot application that manages users and their card information.

### Prerequisites
- Ensure to install WSL
  Run powershell under Administrative rights:
```sh
# Enable and Install HyperV as Windows feature
Enable-WindowsOptionalFeature -Online -FeatureName:Microsoft-Hyper-V -All
Set-ItemProperty -Path HKLM:\system\CurrentControlSet\services\Tcpip\Parameters -Name IpEnableRouter -Value 1
# Enable and Install Windows-Subsystem-Linux as Windows feature
dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart
dism.exe /Online /Enable-Feature /FeatureName:Microsoft-Hyper-V /All /norestart
# Update WSL service
wsl.exe --update
## Show available destributives
wsl --list --online
# wsl --install -d Ubuntu-22.04
# Download Ubuntu 22.04 distributive
Invoke-WebRequest -Uri https://aka.ms/wslubuntu2004 -OutFile ubuntu2204.appx -UseBasicParsing
# install downloaded *.appx file
c:\work\Projects\EPAM\WSL\ubuntu2204.appx
Add-AppxPackage .\ubuntu2204.appx
wsl --set-default-version 2
# Run WSL
wsl
## In WSL create new user
sudo su
user: vagrant
password: vagrant

```

- Java 17 or later
  ```sh WSL
  sudo apt update
  sudo apt install openjdk-17-jdk
  java -version
  echo 'JAVA_HOME="/usr/lib/jvm/java-17-openjdk-amd64"' | sudo tee /etc/profile.d/java-home.sh
  echo 'export JAVA_HOME' | sudo tee -a /etc/profile.d/java-home.sh
  echo 'export PATH=$PATH:$JAVA_HOME/bin' | sudo tee -a /etc/profile.d/java-home.sh
  source /etc/profile.d/java-home.sh
  echo $JAVA_HOME
  java -version
  ```

- Maven 3.6 or later
  ```sh WSL
  sudo apt update
  cd /tmp
  wget https://archive.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
  sudo tar xf apache-maven-3.6.3-bin.tar.gz -C /opt
  sudo ln -s /opt/apache-maven-3.6.3 /opt/maven
  echo 'export M2_HOME=/opt/maven' /etc/profile.d/maven.sh
  echo 'export MAVEN_HOME=/opt/maven' /etc/profile.d/maven.sh
  echo 'export PATH=$PATH:$M2_HOME/bin' /etc/profile.d/maven.sh
  sudo chmod +x /etc/profile.d/maven.sh
  source /etc/profile.d/maven.sh
  echo $M2_HOME
  mvn -version
  ```

- Docker and Docker Compose (to run PostgreSQL and Redis)
  ```sh WSL
  sudo apt-get update
  sudo apt-get install apt-transport-https ca-certificates curl gnupg lsb-release
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
  echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
     $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
  sudo apt-get update
  sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose
  sudo apt-get install docker-compose-plugin
  docker --version
  ```

- To install Liquibase on Ubuntu within WSL, follow these steps
  ```sh
  sudo apt-get update
  wget -O- https://repo.liquibase.com/liquibase.asc | gpg --dearmor > liquibase-keyring.gpg && \
  cat liquibase-keyring.gpg | sudo tee /usr/share/keyrings/liquibase-keyring.gpg > /dev/null && \
  echo 'deb [arch=amd64 signed-by=/usr/share/keyrings/liquibase-keyring.gpg] https://repo.liquibase.com stable main' | sudo tee /etc/apt/sources.list.d/liquibase.list
  sudo apt update
  sudo apt install liquibase
  ```

### Build the Application
To build the application from the command line, navigate to the root directory of the project (where the pom.xml is located) and run the following Maven command:

```sh
mvn clean package
# or
mvn clean package -DskipTests
mvn clean install -DskipTests
```

### Start PostgreSQL and Redis using Docker:
Then run the command:

```sh WSL
sudo su
## Start as daemon
docker-compose up -d
## See current status
docker-compose ps -a
 docker ps -a
docker-compose logs -f fartapp_card-service_1

## Stop
docker-compose down -v
```

### Commands Liquibase CLI (update date of database)

```bash
liquibase --changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml \
          --url=jdbc:postgresql://localhost:5432/carddb --username=postgres --password=password update

          src/main/resources/db/changelog/db.changelog-master.yaml
```

This command will compile the source code, run the tests, and package the application into a JAR file in the target/ directory.

Run the Application
Once the build is successful, you can run the application using:

```sh
java -jar target/card-service-1.0.0.jar
```

The application will start on the default port 8080.

### API Endpoints
You can interact with the REST API using tools like curl or Postman.

- Users: /api/users
- Cards: /api/users/{userId}/cards

## API Endpoints Summary

### User Endpoints:
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `GET /api/users/email/{email}` - Get user by email
- `GET /api/users/surname/{surname}` - Get users by surname
- `GET /api/users/born-after?date={date}` - Get users born after date
- `GET /api/users/born-between?startDate={}&endDate={}` - Get users born between dates
- `GET /api/users/search?name={name}` - Search users by name

### Card Endpoints:
- `GET /api/cards` - Get all cards
- `GET /api/cards/{id}` - Get card by ID
- `POST /api/cards` - Create new card
- `PUT /api/cards/{id}` - Update card
- `DELETE /api/cards/{id}` - Delete card
- `GET /api/cards/user/{userId}` - Get cards by user ID
- `GET /api/cards/expiring-before?date={date}` - Get cards expiring before date
- `GET /api/cards/expiring-between?startDate={}&endDate={}` - Get cards expiring between dates
- `GET /api/cards/user-email/{email}` - Get cards by user email
- `GET /api/cards/holder/{holder}` - Get cards by holder name
- `PATCH /api/cards/{id}/holder?holder={name}` - Update card holder


```sh
curl -X GET http://localhost:8080/api/users
```
