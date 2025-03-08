# ![jenkins](https://github.com/user-attachments/assets/0a854b64-7e42-4941-af78-bea35ccd2f6f) Creating a CI Pipeline with Jenkins  

## Description

This demo project is part of **Module 8: Build Automation & CI/CD with Jenkins** from the **Nana DevOps Bootcamp**. It focuses on setting up a **Continuous Integration (CI) pipeline** fora java maven application in Jenkins using different job types:
- <b>Freestyle Job.</b>
- <b>Pipeline Job.</b>
- <b>Multibranch Pipeline Job.</b>
  
<br />


## 🚀 Technologies Used

- <b>Docker: Docker for containerization.</b>
- <b>Jenkins: Automation for CI/CD.</b>
- <b>Linux: Ubuntu for Server configuration and management.</b>
- <b>Digital Ocean: Cloud provider for hosting Jenkins server.</b>
- <b>GitHub & DockerHub & Nexus: Docker private repositories</b>
- <b>Maven: Build tool for Java application.</b>
- <b>Nana's Java Application: Java application developed by Nana from the Bootcamp</b>


## 🎯 Features

- <b>Install build tools on the Jenkins server.</b>
- <b>Enable Docker on Jenkins server.</b>
- <b>Create Jenkins credentials for git repository</b>
- <b>Setup Jenkins jobs ( Freestyle Job, Pipeline Job, and Multibranch Job) for the Maven Java application to:</b>
  - <b>Connect to the Git repository where the application is stored.</b>
  - <b>Build Jar file.</b>
  - <b>Create a Docker Image.</b>
  - <b>Push the image to private DockerHub repository</b>
  - <b>Push the image to private Nexus Docker repository</b>

## 📝 Prerequisites
- <b>Ensure that Nexus Repository from module 6 is running.</b>
- <b>Ensure that the Jenkins is running.</b>


## 🏗 Project Architecture

<img src=""/>


## ⚙️ Project Configuration:

### Installing Maven in Jenkins.
1. Open the Jenkins server, navigate to Manage Jenkins, and select Tools.
   <img src="" width=800 />
2. Navigate to the Maven installations section, then click add Maven.
   <img src="" width=800 />
3. Enter the name and version for Maven.
   <img src="" width=800 />

### Installing NodeJS and Npm in Jenkins Container.
1. SSH into DigitalOcean Droplet

   ```bash
   ssh root@198.199.70.18
   ```
   
2. Display the currently running containers.

   ```bash
   docker ps
   ```
   
3. Enter the Jenkins container using its container ID.

   ```bash
   docker exec  -it d4d4ccb59734 bash
   ```
   
4. Check the current user inside the Jenkins container and then exit.

   ```bash
   whoami
   exit
   ```
    <img src="" width=800 />
   
5. Enter the Jenkins container using the Root user.

   ```bash
   docker exec -u 0 -it d4d4ccb59734 bash
   ```
    <img src="" width=800 />
   
6. Check the Linux distribution inside the Jenkins container.

   ```bash
   cat /etc/issue
   ```
    <img src="" width=800 />

7. Update the package manager inside the Jenkins container.

   ```bash
   apt update
   ```

8. Install curl command inside the Jenkins container.

   ```bash
   apt install curl
   ```

9. Use the script to install NodeJS and npm inside the Jenkins container.

   ```bash
   curl -sL https://deb.nodesource.com/setup_20.x -o nodesource_setup.sh
   ls
   ```
    <img src="" width=800 />

10. If the script did not work as expected, install Node.js and npm directly inside the Jenkins container using the  shell commands.

     ```bash
    apt-get install nodejs -y
     ```
     <img src="" width=800 />

11. Verify that nodeJS and npm are installed inside the Jenkins container.

     ```bash
    node -v
    npm -v
     ```
      <img src="" width=800 />


### Enabling Docker in Jenkins

### Creating Jenkins Credentials to Access repositories

### Creating a Freestyle Job

   
   
      
