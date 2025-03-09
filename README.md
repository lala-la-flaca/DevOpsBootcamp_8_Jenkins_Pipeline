# ![jenkins](https://github.com/user-attachments/assets/0a854b64-7e42-4941-af78-bea35ccd2f6f) Creating a CI Pipeline with Jenkins  

## Description

This demo project is part of **Module 8: Build Automation & CI/CD with Jenkins** from the **Nana DevOps Bootcamp**. It focuses on setting up a **Continuous Integration (CI) pipeline** for a Java Maven application in Jenkins using different job types:
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
   
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/1%20Build%20tools.png" width=800 />
  
2. Navigate to the Maven installations section, then click add Maven.

3. Enter the name and version for Maven.
   
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/2%20build%20tools%20maven.png" width=800 />

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
    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/2%20Checking%20current%20user.png" width=800 />
   
5. Enter the Jenkins container using the Root user.

   ```bash
   docker exec -u 0 -it d4d4ccb59734 bash
   ```
    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/2%20entering%20container%20as%20root%20user.png" width=800 />
   
6. Check the Linux distribution inside the Jenkins container.

   ```bash
   cat /etc/issue
   ```
    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/2%20Checking%20Linux%20Distribution.png" width=800 />

7. Update the package manager inside the Jenkins container.

   ```bash
   apt update
   ```

8. Install the curl command inside the Jenkins container.

   ```bash
   apt install curl
   ``` 
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/2%20installing%20curl%20and%20updating%20package%20manager.png" width=800 />

9. Use the script to install NodeJS and npm inside the Jenkins container.

   ```bash
   curl -sL https://deb.nodesource.com/setup_20.x -o nodesource_setup.sh
   ls
   bash nodesource_setup.sh
   ```
    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/2%20installig%20scrpt.png" width=800 />

    <details><summary><strong> ⚠️ If the script does not install NodeJS: </strong></summary>
    
    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/2%20nodejs%20not%20installed.png" width=800 />

    If the script does not work as expected, then install Node.js and npm directly inside the Jenkins container using the  shell commands.

     ```bash
    apt-get install nodejs -y
     ```
     <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/2%20installing%20nodejs%20manually%20a.png" width=800 />
    
</details>
  
10. Verify that NodeJS and npm are installed inside the Jenkins container.

     ```bash
    node -v
    npm -v
     ```
      <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/2%20i%20nstalling%20nodejs%20manually.png" width=800 />

### Enabling NodeJS in Jenkins via WebUI
To enable Jenkins through the webUI, we must install the NodeJS pluging as follows and enable it in the Tools:
1. Navigate to the Jenkins Main Menu and click on Manage Jenkins.
  
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/3%20Installing%20pluggins.png" width=800 />
   
3. Click on Plugins, then go to the Available Plugins tab.
   
5. In the search bar, type NodeJS, select the plugin, and click Install.
   
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/3%20installing%20nojdejs%20pluggins.png" width=800 />
   
7. To enable NodeJS in the tools section, navigate to Manage Jenkins and click on Tools.

9. Locate the NodeJS Installation section and click Add NodeJS.
   
11. Enter a Name and specify the Version of NodeJS.
    
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/4%20configuring%20nodejs%20tool.png" width=800 />
   

### Enabling Docker in Jenkins
To execute docker commands inside the Jenkins container, we must enable docker commands
1. Stop the currently running Jenkins container.

   ```bash
   docker ps
   docker stop d4d4ccb59734
   ```
   
3. Run the Jenkins container and attach a second volume to enable Docker integration.
   
   ```bash
    docker run \
    > -p 8080:8080 \
    > -p 50000:50000 \
    > -d \
    > -v jenkins_home:/var/jenkins_home \
    > -v /var/run/docker.sock:/var/run/docker.sock \
    > jenkins/jenkins:lts
   
   ```
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/attching%20docker%20volume%20to%20jenkins%20container.png" width=800 />
   
5. Enter the Jenkins container as the root user.
   
   ```bash
   docker ps
   docker exec -u 0 -it 3c78b942716a bash
   ```
   
7. Use the curl command to fetch the latest Docker version, grant execute permissions to the Docker install script, and then run the Docker install.
  
   ```bash
   curl https://get.docker.com > dockerinstall && chmod 777 dockerinstall && ./dockerinstall
   ```
   
8. Set the appropriate permissions on the docker.sock file to allow Docker commands inside the Jenkins container using the Jenkins user.

    <details><summary><strong>ℹ️ docker.sock file: </strong></summary>
   It is a unix socker file used by docker daemon (dockerd) to communicate with docker client, allowing you to manage containers, images, and networks. It's at /var/run/docker.sock
    </details>
    
   ```bash
   ls -l /var/run/docker.sock
   chmod 666 /var/run/docker.sock
   ```
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/Adding%20permission%20to%20w%20and%20r.png" width=800 />
   

### Creating Jenkins Credentials to Access Git Using a Job
The Git credentials can be added while creating the Job as follows:
1. Open Jenkins and navigate to the Main Menu.
   
2. Click New Item, enter a name, and select Freestyle Project.

3. In the Source Code Management section, choose Git and enter the repository URL.
   
4. Under Credentials, click Add → Jenkins..

   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/1%20adding%20git%20repository.png" width=800 />
  
5. Keep the default Domain and select Username with password in the Kind section.
  
6. Enter your Git username and password.

   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/2%20adding%20credentials%20and%20id.png" width=800 />
    
7. Assign an ID to the credentials and click Add.
  
8. Save the Job


### Adding or Modifying Credentials in Jenkins Security Settings
The credentials can also be added or modified from the Security section under Credentials as follows:
1. Navigate to Manage Jenkins → Security → Credentials.
   
    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/add%20dockerhub%20credentials%20to%20jenkins%201.png" width=800 />
    
2. Select the store.
   
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/select%20store.png" width=800 />
   
3. Select the appropriate Domain or create a new one if needed.
      
    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/domain.png" width=800 />
    
4. Click Add Credentials.
      
    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/add%20credntials.png" width=800 />
    
5. Enter a username and password in the kind section.
   
6. Assign an ID and a Description for easy identification.
    
7. Click OK to save the credentials.
       
    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/creating%20credentials.png" width=800 />
    
8. We followed the same steps to add the Docker Hub and Nexus credentials. These credentials are now available in Jenkins jobs when configuring repository access.

   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/credentials%20for%20repos.png" width= 800 />


### Creating a Freestyle Job for a Java Maven Application
1. Open Jenkins and navigate to the Main Menu.   
2. Click New Item, enter a Job Name, and select Freestyle Project.
3. In the Source Code Management section, connect the job to the Git repository where the application is stored.
4. Configure the Build Steps to compile the application and generate a JAR file.
5. Add a step to build a Docker image using the generated JAR file.
6. Configure the job to push the Docker image to the DockerHub repository.

   <img src="" width=800 />

   
   
      
