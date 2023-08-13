pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = "easwarang/employee-mgt" // E.g., your-app-image
        AWS_REGION = "us-east-2"
        AWS_ECR_REPO = "public.ecr.aws/q8p3p8k4/employee-mgt" // E.g., your-ecr-repo
        AWS_INSTANCE_IP = "3.143.226.28"
        DOCKER_IMAGE_TAG = "${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}"
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
                    docker.build("${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}")
                }
            }
        }

      stage('Push to Docker Hub') {
          environment {
              DOCKER_IMAGE_TAG = "${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}"
              LATEST_IMAGE_TAG = "${DOCKER_IMAGE_NAME}:latest"
              DOCKER_HUB_CREDENTIALS = 'docker-credentials'
          }
          steps {
              script {
                  withDockerRegistry(credentialsId: "${DOCKER_HUB_CREDENTIALS}", url: "https://index.docker.io/v1/") {
                      docker.image(DOCKER_IMAGE_TAG).push()
                      docker.image(LATEST_IMAGE_TAG).push()
                  }
              }
          }
      }


    stage('Deploy to EC2') {

        steps {
            script {
                sshagent(['ec2-ssh']) {
                    sh """ssh -o StrictHostKeyChecking=no ec2-user@${AWS_INSTANCE_IP} \
                        'docker stop <container-name> && \
                        docker rm <container-name> && \
                        docker pull ${DOCKER_IMAGE_TAG} && \
                        docker run -d --name <container-name> -p <host-port>:<container-port> ${DOCKER_IMAGE_TAG}'"""
                }
            }
        }
    }
    }
}
