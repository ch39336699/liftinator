# Liftinator Monorepo


### Table of Contents
- [Tech Stack](#-tech-stack)
- [About](#-about)
- [Architecture](#-the-solution-architecture)
- [Links](#-links)
- [Basic Setup](#-basic-setup)
- [Docker Setup](#-docker-setup)
- [Splunk Setup](#-splunk-setup)
- [Testing](#-testing)



## 💡 Tech Stack
[![My Skills](https://skillicons.dev/icons?i=spring,java,docker,maven)](https://skillicons.dev)

## 🚀 About

**Liftinator** is a fun opportunity to design, build, and test an elevator system. The concept seemed simple at first—just a basic elevator that could move between different floors—but I quickly realized it was an engaging exercise that required deep problem-solving, technical knowledge, and innovation. I would suggest starting with the [design document](./files/Liftinator_HLD.docx), which gives an overview of the design and thought process when building the Lifinator. After cloning the repository, there are two ways to run the project.

- **Basic Setup**: Basic setup is running install on the base **pom.xml** and then starting the services one at a time. This would get everything up and running fairly quickly.
- **Docker Setup**: Docker setup is deploying via Docker. An added bonus of running via the Docker container is that you can view the system via a Splunk dashboard.

### 🏛️ The Solution Architecture
<p align="center">
  <img src="./files/archDiagram.png" alt="Size Limit CLI" width="738">
</p>

### 🔎 Links

| Link                                                                                          | Description                                       |
|-----------------------------------------------------------------------------------------------|---------------------------------------------------|
| [High Level Design Document](./files/Liftinator_HLD.docx)                                     | High level design document for Liftinator project |
| [Spunk](http://localhost:8000/)                                                               | Local running Splunk login                        |
| [Swagger API](http://localhost:8081/swagger-ui/index.html)                                    | Swagger API endpoints                             |

## 📝 Basic Setup
For basic setup:

* Clone the repository.
* Run **maven install** on the **pom.xml** the root directory of the project. This should first build **common-libs** and then build the **capacity-coordinator-service** and **edge-service**. If everything builds correctly you should see:
<p align="center">
  <img src="./files/mavenbuild.png" alt="Size Limit CLI" width="738">
</p>

* Run the spring services **capacity-coordinator-service** and **edge-service** under the **/apps** folder.
* With the services up you can now use the **http-request_demo.http** file in the base directory to send API commands to run elevator requests and view the log output.

## [![My Skills](https://skillicons.dev/icons?i=docker)](https://skillicons.dev) Docker Setup
For docker setup: 
* Clone the repository.
* Run the **docker-compose.yaml** in the base directory. This will:
    1. Pull the docker images for the **capacity-coordinator-service** and **edge-service**
    2. Pull the docker images for **splunk** and **splunk/universalforwarder**
    3. Start all four containers. <br>
       ⭐ **Note:** Splunk server usually takes a few minutues to come up. Please be patient.


### 📃 Splunk Setup
If you did **docker setup** you should have a splunk sever running and can [login](http://localhost:8000/). (admin/password)
* As you run the tests (below), within **Splunk** select the **Search** tab, and search for all "*".

<p align="center">
  <img src="files/splunkSearch1.png" alt="Size Limit CLI" width="738">
</p>

* For a more visual view of the data, you can also use a dashboard by going to **Search** then **Dashboards** and then:
    1. Select **Create New Dashboard** (name it whatever you like), then select **Dashboard Studio** and **Grid**.
    2. Select **Add Chart** (top bar) and choose **Table**, then in **Create Search** for the query enter the contents from **splunkQuery.txt**, then **Apply and Close**.
    3. Default rows visible are 10. To increase, select **Data Display** 
    4. I also set the table **Time Range** to 1 minute to separate the data from different runs.

* If all goes well, you should see a table, and when you run the tests below, you should see something similar to:

<p align="center">
  <img src="./files/splunkTable.png" alt="Size Limit CLI" width="738">
</p>

### 🧪 Testing

**Scenario:** The scenario for the tests is a 15-story building with 6 elevators (A-F), where each elevator can support a maximum load of 800 lbs. 
The **http-request_demo.http** file (located in the base directory) is used to post API requests specifying how many people are requesting elevators and the details for each person. 
The system should manage all the elevators based on the number of people waiting, their location, and the elevators' capacity.
For each person waiting, we specify:
```shell
  {
      "currentFloor": 0,  //Floor the person is currenty on
      "floorSelected": 3, //Floor the need to get to
      "name": "David",    //Person Name
      "weight": 145       //Persons weight (which contribues to the load in the elevator)
  }

```

