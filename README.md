# Liftinator Monorepo


### Table of Contents
- [Tech Stack](#-tech-stack)
- [About](#-about)
- [Architecture](#diagram)
- [Links](#links)
- [Basic Setup](#-basic-setup)
- [Docker Setup](#-docker-setup)
- [Testing](#-test)


## 💡 Tech Stack <a name="tech-stack"></a>
[![My Skills](https://skillicons.dev/icons?i=spring,java,docker,maven)](https://skillicons.dev)

## 🚀 About <a name="about"></a>

**Liftinator** is a fun opportunity to design, build, and test an elevator system. The concept seemed simple at first—just a basic elevator that could move between different floors—but I quickly realized it was an engaging exercise that required deep problem-solving, technical knowledge, and innovation. I would suggest starting with the [design document](./files/Liftinator_HLD.docx), which gives an overview of the design and thought process when building the Lifinator. After cloning the repository, there are two ways to run the project.

- **Basic Setup**: Basic setup is running install on the base **pom.xml** and then starting the services one at a time. This would get everything up and running fairly quickly.
- **Docker Setup**: Docker setup is deploying via Docker. An added bonus of running via the Docker container is that you can view the system via a Splunk dashboard.

### 🏛️ The Solution Architecture <a name="diagram"></a>
<p align="center">
  <img src="./files/archDiagram.png" alt="Size Limit CLI" width="738">
</p>

### 🔎 Links <a name="links"></a>

| Link                                                                                          | Description                                       |
|-----------------------------------------------------------------------------------------------|---------------------------------------------------|
| [High Level Design Document](./files/Liftinator_HLD.docx)                                     | High level design document for Liftinator project |
| [Spunk](http://localhost:8000/)                                                               | Local running Splunk login                        |
| [Swagger API](http://localhost:8081/swagger-ui/index.html)                                    | Local running swagger for API endpoints           |

## 📝 Basic Setup <a name="basic-setup"></a>
For basic setup, follow these steps:

* Clone the repository.
* Run **maven install** on the **pom.xml** the root directory of the project. This should first build **common-libs** and then build the **capacity-coordinator-service** and **edge-service**. If everything builds correctly you should see:
<p align="center">
  <img src="./files/mavenbuild.png" alt="Size Limit CLI" width="738">
</p>

* Run the spring services **capacity-coordinator-service** and **edge-service** under the **/apps** folder.
* With the services up you can now use the **http-request_demo.http** file in the base directory to send API commands to run elevator requests and view the log output.

## [![My Skills](https://skillicons.dev/icons?i=docker)](https://skillicons.dev) Docker Setup <a name="docker-setup"></a>
For docker setup, you must have Docker installed. 
* Clone the repository.
* Run the **docker-compose.yaml** in the base directory. This will:
    1. Pull the docker images for the **capacity-coordinator-service** and **edge-service**
    2. Pull the docker images for **splunk** and **splunk/universalforwarder**
    3. Start all four containers. <br>
       ⭐ **Note:** Splunk server usually takes a few minutues to come up. Please be patient.
* Once Splunk server comes and is running in Docker you can [login](http://localhost:8000/). (admin/password)
* With the services up you can now use the **http-request_demo.http** file in the base directory to send API commonds to run elevator requests.

<p align="center">
  <img src="./files/splunkTable.png" alt="Size Limit CLI" width="738">
</p>

### 🧪 Testing <a name="test"></a>
**Liftinator** is a fun opportunity to design, build, and test an elevator system. The concept seemed simple at first—just a basic elevator that could move between different floors—but I quickly realized it was an engaging exercise that required deep problem-solving, technical knowledge, and innovation. I would suggest starting with the [design document](./files/Liftinator_HLD.docx), which gives an overview of the design and thought process when building the Lifinator. After cloning the repository, there are two ways to run the project.

- **Basic Setup**: Basic setup is running install on the base **pom.xml** and then starting the services one at a time. This would get everything up and running fairly quickly.
- **Docker Setup**: Docker setup is deploying via Docker. An added bonus of running via the Docker container is that you can view the system via a Splunk dashboard.

