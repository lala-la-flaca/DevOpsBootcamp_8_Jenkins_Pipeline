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
- <b>GitHub: repository where the application is stored</b>
- <b>DockerHub & Nexus: Docker private repository</b>
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
  - <b>Push the image to the private Nexus Docker repository</b>

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
To enable NodeJS through the webUI, we must install the NodeJS pluging as follows and enable it in the Tools:
1. Navigate to the Jenkins Main Menu and click on Manage Jenkins.
  
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/3%20Installing%20pluggins.png" width=800 />
   
2. Click on Plugins, then go to the Available Plugins tab.
   
3. In the search bar, type NodeJS, select the plugin, and click Install.
   
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/3%20installing%20nojdejs%20pluggins.png" width=800 />
   
4. To enable NodeJS in the tools section, navigate to Manage Jenkins and click on Tools.

5. Locate the NodeJS Installation section and click Add NodeJS.
   
6. Enter a Name and specify the Version of NodeJS.

    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/4%20configuring%20nodejs%20tool.png" width=800 />
   


### Enabling Docker in Jenkins
To execute docker commands inside the Jenkins container, we must enable docker commands
1. Stop the currently running Jenkins container.

   ```bash
   docker ps
   docker stop d4d4ccb59734
   ```
   
2. Run the Jenkins container and attach a second volume to enable Docker integration.
   
   ```bash
    docker run \
    -p 8080:8080 \
    -p 50000:50000 \
    -d \
    -v jenkins_home:/var/jenkins_home \
    -v /var/run/docker.sock:/var/run/docker.sock \
    jenkins/jenkins:lts
   
   ```
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/attching%20docker%20volume%20to%20jenkins%20container.png" width=800 />
   
3. Enter the Jenkins container as the root user.
   
   ```bash
   docker ps
   docker exec -u 0 -it 3c78b942716a bash
   ```
   
4. Use the curl command to fetch the latest Docker version, grant execute permissions to the Docker install script, and then run the Docker install.
  
   ```bash
   curl https://get.docker.com > dockerinstall && chmod 777 dockerinstall && ./dockerinstall
   ```
   
5. Set the appropriate permissions on the docker.sock file to allow Docker commands inside the Jenkins container using the Jenkins user.

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
    
8. We followed the same steps to add the Docker Hub and Nexus credentials. These credentials are now available in Jenkins jobs.

   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/credentials%20for%20repos.png" width= 800 />



### Creating a Freestyle Job for a Java Maven Application
1. Create an account on Docker Hub and create a repository.

   [DockerHub](https://hub.docker.com/)

   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/create%20a%20docker%20repository%20on%20docker%20hub.PNG" width=800 />
   
2. Open Jenkins and navigate to the Main Menu.   
3. Click New Item, enter a Job Name, and select Freestyle Project.

   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/FreeStyleJobMaven.png" width=800 />
   
4. In the Source Code Management section, connect the job to the Git repository where the application is stored.

   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/configuring%20git.png" width=800 />
   
5. Map the environment variables to the stored GitHub credentials by selecting Use secret text or file, adding username and password variables, and choosing the credentials.

   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/credentials%20github%20env%20file%20jenkins.png" width=800 />
   
6. Configure the build steps in Jenkins to compile the application and generate a JAR file, go to the Build section in the job configuration. Click Add Build Step and select Invoke top-level Maven targets. From the Maven Version drop-down, choose the appropriate Maven installation configured in Jenkins. In the Goals field, enter package to compile the application and generate a JAR file.

    ```bash
     -f java-maven-app/pom.xml package
     ```

    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/Maven%20build%20step.png" width=800 />
   
   <details><summary><strong> ❌ Issue  </strong></summary>
   Jenkins cannot find the pom.xml file when using the package command. For more information, refer to the Troubleshooting section.
   </details>

7. Verify that the jar file was successfuly created on the droplet. The jar file is located in the target folder workspace>java-maven-build>java-maven-app>target.

   ```bash
   docker volume ls
   docker inspect jenkins_home
   cd /var/snap/docker/common/var-lib-docker/volumes/jenkins_home/_data/workspace/java-maven-build/java-maven-app/target
   ```

   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/jar%20file%20availabe%20in%20target%20folder.png"  width=800 />
   
9. Add a step to build a Docker image.

    ```bash
    cd java-maven-app/
    docker build -t lala011/demo-app:jma-1.0 .
    docker login -u $USERNAME -p PASSWORD
    docker push lala011/demo-app:jma-1.0
    ```
    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/pushing%20image%20to%20dockerhub.PNG" width=800 />

    <details><summary><strong> ❌ Issue  </strong></summary>
     Jenkins cannot log in to Docker Hub. For more information, refer to the Troubleshooting section.
   </details>
   
10. Push the image to the DockerHub repository.

    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/image%20available%20on%20docker%20hub.PNG" width=800 />

### Pushing image to Docker Nexus Repository
1. Ensure that Nexus from Module 6 is running.
2. Add insecure registries to the daemon.json file on the droplet hosting Jenkins. If the file does not exist, then it must be created.

   ```bash
   cd /var/snap/docker/2976/config
   ls
   vim daemon.json
   ```
   ```bash
   {
    "log-level":        "error",
    "insecure-registries": ["157.230.56.153:8083","157.230.56.153:8084"]
   }   
   ```

   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/adding%20insecure%20registries%20nexus.png" width=800 />
   
3. Restart the docker daemon using the following command.

   ```bash
   sudo snap restart docker
   ```
   
4. Run the Jenkins container using the following command.
   
   ```bash
   docker ps -la
   docker start 63c78b942716a
   ```
   
5. Access the Jenkins container and grant permission to the docker socket (docker.sock) as follows

    ```bash
   docker exec -u 0 -it 3c78b942716a bash
   chmod 666 /var/run/docker.sock
   ls -l /var/run/docker.sock
   exit
   ```
    
6. Access Jenkins WebUI.
7. Add the Nexus credentials as explained in the "Adding or Modifying Credentials in Jenkins Security Settings" section.
8. Navigate to the Dashboard, select the java-maven-build job, and click Configure.

     <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/dashboard%20configure.png" width=800 />
     
9. Go to the environment section and update the credentials from DockerHub to Nexus-repository.
10. Keep the Invoke top-level Maven Targets.
11. Modify the Build step Execute shell to update the docker commands.

    ```bash
    cd java-maven-app/
    docker build -t java-maven-app:1.1 .
    docker tag java-maven-app:1.1 157.230.56.153:8083/java-maven-app:1.1
    echo $PASSWORD | docker login -u $USERNAME --password-stdin 157.230.56.153:8083
    docker push 157.230.56.153:8083/java-maven-app:1.1
    ```
12. Image Available in Nexus repository

     <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/image%20available%20nexus%20docker%20repo.png" width=800 />



### Creating a Pipeline Job for a Java Maven Application 
1. Open Jenkins and go to the Main Menu.
2. Click New Item, enter a Job Name, and select Pipeline Project.

   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/1%20create%20pipeline%20job.png" width=800 />
   
3. In the Source Code Management section, connect the job to the Git repository where the application is stored.

   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/2%20setting%20up%20job%20-%20git.png" width=800 />
   
4. Ensure the Script Path field contains the correct path to locate the Jenkinsfile.
   
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/3%20setting%20up%20job%20-%20git%20script%20path%20using%20jenkins%20file%20.png" width=800 />
   
5. Save Changes.
   
6. Create a Jenkinsfile in the root folder of the application.
   
7. Create a script.groovy file in the root folder of the application.

   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/9%20groovy%20script.png" width=800 />
   
8. Edit the Jenkinsfile and define the pipeline, tools, and stages as follows:
   ```bash
      def gv
      
      pipeline {   
          agent any
          tools{
              maven 'maven-3.9'
          }
      
          stages{ 
      
             stage("init"){
                   steps{
                       script{
                           gv = load "java-maven-app/script.groovy"    
                       }
                    }
              }
      
              
              stage("build jar") {
                      steps {
                          script{
                            gv.buildJar()
                         }
                      }
              }
          
              stage("build image") {
                      steps {
                          script{
                            gv.buildImage()
                             
                          }
                      }
              }
                       
              
          
              stage("deploy") {
                  steps {
                      script {
                          gv.deployApp()
                      }
                  }
              }              
          }
      } 
   ```
   <details><summary><strong> 💡 Loading a Groovy Script in a Nested Application Directory </strong></summary>
   When loading the Groovy script in the init section, ensure that the variable points to the correct path. Since the checkout branch does not default to the application root folder, instruct Jenkins and Groovy to use the maven-java-app folder when loading the script.
   </details>
  

9. Edit the script.groovy file and reference it from the Jenkinsfile using a global variable called gv.
    ```bash
          def buildJar() {
          echo "building the application..."
          dir('java-maven-app') {
              sh 'mvn package'
              }
      }
      
      def buildImage() {
          echo "building the docker Image..."
          withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PWD', usernameVariable: 'USER')]){
              sh """
                  cd java-maven-app/
                  docker build -t lala011/demo-app:jma-2.0 .
                  echo $PWD | docker login -u $USER --password-stdin
                  docker push lala011/demo-app:jma-2.0
              """                               
          }    
      }
      
      
      def deployApp() {
          echo 'deploying the application...'
      }
      
      return this
    
    ```
   
   <details><summary><strong> ❌ Issue  </strong></summary>
     <ul>
        <li>The shell session does not change the directory for the subsequent commands.</li>
        <li>The Docker login fails with variables because of incorrect string interpolation.</li>
    </ul>
   </details>
   
10. Pipeline Job OK
    
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/commit/5f499c3bc161efad811c3f1be4d0bcd109fb1dfc" width=800 />

12. Image available in Docker Hub
    <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/15%20Image%20available%20dockerhub.PNG" width=800 />




## ❌ Troubleshooting & Fixes

### 🔴 **Issue**: Freestyle Job - Jenkins cannot access the pom.xml file

<b>📄 Description: </b></br>

When using the package command to build the JAR file, Jenkins was unable to locate the pom.xml file. This occurred because Jenkins executed the command from the root directory where the Git branch was checked out. In this case, the branch contained additional files, and the pom.xml file was located inside the java-maven-app directory.

<b>✅ Solution: </b></br>

To resolve this issue, we explicitly specified the path to the pom.xml file using the -f option. This directs Jenkins to the correct location of the pom.xml file within the java-maven-app directory.

Use the following command in the top-level Maven targets field:

```bash
  -f java-maven-app/pom.xml package
```
This ensures Jenkins can successfully find and execute the Maven build process from the correct location.
<details><summary><strong> Jenkins Console Output  </strong></summary>
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/Build%20nok%20before%20modifications.PNG" width=800 />
</details>





### 🔴 **Issue**: Freestyle Job - Jenkins cannot log in to Docker Hub

<b>📄 Description: </b></br>

When attempting to log in to Docker Hub using the docker login command, Jenkins was unable to access the environment variables containing the credentials. This issue occurred because the environment variables were defined in lowercase, while the command referenced them using uppercase, and Jenkins treats environment variables as case-sensitive.

<b>✅ Solution: </b></br>

Ensure that the environment variable names match exactly between the Jenkins configuration and the docker login command. Since Jenkins is case-sensitive, updating the variable names to uppercase resolved the issue.

<details><summary><strong> Jenkins Console Output  </strong></summary>
   <img src="https://github.com/lala-la-flaca/DevOpsBootcamp_8_Jenkins_Pipeline/blob/main/Img/error%20when%20using%20env%20variables.png" width=800 />
</details>



### 🔴 **Issue**: Pipeline Job - The Shell session does not change the directory for the subsequent commands.


<b>📄 Description: </b></br>

The mvn package command fails because the directory change occurs in a separate shell session using the sh command. Each sh command runs in its own shell, so cd does not persist between steps. Use dir to maintain the working directory across sh commands.

<b>✅ Solution: </b></br>

To fix this issue, we had to use the dir to keep the directory between steps:
 
  ```bash
    dir('java-maven-app'){
    sh 'mvn package'
    }
  ```

### 🔴 **Issue**: Pipeline Job - The Docker login fails with variables because of incorrect string interpolation.

<b>📄 Description: </b></br>

The Docker login command fails to pass the username and password correctly. This occurs because the shell block uses single quotes instead of double quotes, preventing the variables from expanding to their values.

<b>✅ Solution: </b></br>

Use double quotes instead of single quotes in the shell block to enable proper string interpolation.


<details><summary><strong> 💡String Interpolation💡</strong></summary>

[String Interpolation](https://www.jenkins.io/doc/book/pipeline/jenkinsfile/#string-interpolation)

***String interpolation***: Jenkins Pipeline uses rules identical to Groovy for string interpolation. Groovy’s String interpolation support can be confusing to many newcomers to the language. While Groovy supports declaring a string with either single quotes, or double quotes, for example:
  
  ```bash
  def singlyQuoted = 'Hello'
  def doublyQuoted = "World"
  ```
  Only the latter string will support the dollar-sign ($) based string interpolation, for example:
  
  ```bash
  def username = 'Jenkins'
  echo 'Hello Mr. ${username}'
  echo "I said, Hello Mr. ${username}"
  ```
  
  Would result in:
  
  ```bash
  Hello Mr. ${username}
  I said, Hello Mr. Jenkins
  ```
  Understanding how to use string interpolation is vital for using some of Pipeline’s more advanced features."
     
</details>


```bash
  echo "building the docker image..."
  withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PWD', usernameVariable: 'USER')]){
    sh """
      cd java-maven-app/
      docker build -t lala011/demo-app:jma-2.0 .
      echo $PWD | docker login -u $USER --password-stdin
      docker push lala011/demo-app:jma-2.0
    """                               
}
                    
```
