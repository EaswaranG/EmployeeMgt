pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = "easwarang/employee-mgt" // E.g., your-app-image
        AWS_REGION = "us-east-2"
        AWS_INSTANCE_IP = "13.58.182.14"
        DOCKER_IMAGE_TAG = "${DOCKER_IMAGE_NAME}:latest"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE_NAME}")
                }
            }
        }

      stage('Push to Docker Hub') {
                  steps {
                      script {
                          withDockerRegistry(
                              credentialsId: "docker-credentials",
                              url: 'https://index.docker.io/v1/'
                          ) {
                              docker.image("${DOCKER_IMAGE_NAME}").push()
                          }
                      }
                  }
              }


    stage('Deploy to EC2') {

        steps {
            script {
                sshagent(['ec2-ssh']) {
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@${AWS_INSTANCE_IP}"
                    sh "docker container ls"
                    sh "docker container ps"
                    sh "docker container ls -a"
                    sh "docker container ps -q --filter name=emp-mgt-be-container"
                    sh "if docker ps -q --filter name=emp-mgt-be-container | grep -q .; then docker stop emp-mgt-be-container; else echo \"Container emp-mgt-be-container is not running.\"; fi"
                    sh "if docker container ls -a -q --filter name=emp-mgt-be-container | grep -q .; then docker rm emp-mgt-be-container; else echo \"Container emp-mgt-be-container is not running.\"; fi"
                    sh "docker pull ${DOCKER_IMAGE_NAME}"
                    sh "docker run -d -p 8081:8080 --name emp-mgt-be-container ${DOCKER_IMAGE_NAME}"
                }
            }
        }
    }
    }
}
