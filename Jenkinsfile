pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = "employee-mgt" // E.g., your-app-image
        AWS_REGION = "us-east-2"
        AWS_ECR_REPO = "public.ecr.aws/q8p3p8k4/employee-mgt" // E.g., your-ecr-repo
        AWS_INSTANCE_IP = "3.143.226.28"
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

        stage('Push to ECR') {
            steps {
                script {
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-credentials']]) {
                        docker.withRegistry("https://${AWS_REGION}.dkr.ecr.${AWS_REGION}.amazonaws.com") {
                            def taggedImage = docker.image("${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}")
                            def latestImage = docker.image("${DOCKER_IMAGE_NAME}:latest")

                            taggedImage.push()
                            latestImage.push()

                            sh "aws ecr put-image-scanning-configuration --repository-name ${AWS_ECR_REPO} --image-scanning-configuration scanOnPush=true"
                        }
                    }
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                script {
                    sshagent(['aws-credentials']) {
                        sh "ssh -o StrictHostKeyChecking=no ec2-user@${AWS_INSTANCE_IP} 'docker stop <container-name> || true && docker rm <container-name> || true && docker pull ${AWS_ECR_REPO}/${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER} && docker run -d --name <container-name> -p <host-port>:<container-port> ${AWS_ECR_REPO}/${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}'"
                    }
                }
            }
        }
    }
}
