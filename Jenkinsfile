pipeline {
    agent any

    environment {
        AWS_DEFAULT_REGION = 'us-east-2'
        ECR_REGISTRY = 'public.ecr.aws/q8p3p8k4/emp'
        ECS_CLUSTER = 'devCluster'
        ECS_SERVICE = 'empClusterService'
        DOCKER_IMAGE_NAME = 'employeemanagement'
    }

   stages {
        stage('Checkout') {
            steps {
                // Checkout your source code from version control here
                // For example: git 'your-repo-url'
				checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                       sh 'mvn clean install'
                }
            }
        }

        stage('Push to ECR') {
            steps {
                script {
                    def awsCredentials = credentials('aws-access-key')
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: awsCredentials]]) {
                        sh "aws ecr get-login-password --region $AWS_DEFAULT_REGION | docker login --username AWS --password-stdin $ECR_REGISTRY"
                        sh "docker build -t $ECR_REGISTRY/my-java-app ."
                        sh "docker push $ECR_REGISTRY/my-java-app"
                    }
                }
            }
        }

        stage('Deploy to ECS') {
            steps {
                script {
                    def awsCredentials = credentials('aws-access-key')
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: awsCredentials]]) {
                        sh "ecs-cli configure profile --access-key $AWS_ACCESS_KEY_ID --secret-key $AWS_SECRET_ACCESS_KEY"
                        sh "ecs-cli compose --file docker-compose.yml --ecs-params ecs-params.yml up"
                    }
                }
            }
        }
    }
}