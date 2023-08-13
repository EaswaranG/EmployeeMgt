pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = "easwarang/employee-mgt" // E.g., your-app-image
        AWS_REGION = "us-east-2"
        AWS_ECR_REPO = "public.ecr.aws/q8p3p8k4/employee-mgt" // E.g., your-ecr-repo
        AWS_INSTANCE_IP = "3.143.226.28"
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
                    sh "docker container ls -a"
                    sh "docker stop emp-mgt-be-container"
                    sh "docker rm emp-mgt-be-container"
                    sh "docker pull ${DOCKER_IMAGE_NAME}"
                    sh "docker run -d -p 8080:8080 --name emp-mgt-be-container ${DOCKER_IMAGE_NAME}"
                }
            }
        }
    }
    }
}
