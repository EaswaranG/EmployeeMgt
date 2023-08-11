pipeline {
    agent any

    environment {
        ACR_NAME = 'employeemgt'
        IMAGE_NAME = 'employee-mgt'
        CONTAINER_NAME = 'employee-mgt-container'
        RESOURCE_GROUP = 'EmployeeMgt-resourcegroup'
        LOCATION = 'Canada Central'
        ACR_USERNAME = 'employeemgt'
        ACR_PASSWORD = 'U9Jz38QVnCKToswkCGgDHIflu2xezCLc+JYkreF7Gw+ACRAEeJk0'
        ACR_REGISTRY = 'employeemgt.azurecr.io'
    }

    stages {
        stage('Build and Push Docker Image') {
            steps {
                script {
                      docker.withRegistry("${ACR_REGISTRY}", "${ACR_USERNAME}", "${ACR_PASSWORD}") {
                      def appImage = docker.build("${ACR_REGISTRY}/${IMAGE_NAME}:${env.BUILD_NUMBER}", "-f Dockerfile .")
                      appImage.push()
                      }
                }
            }
        }

        stage('Deploy to Azure Container Instance') {
            steps {
                script {
                    def acrCreds = credentials('acr-credentials')

                    azureContainerInstance(
                        authentication: acrCreds,
                        resourceGroup: "${RESOURCE_GROUP}",
                        location: "${LOCATION}",
                        osType: 'Linux',
                        containers: [[
                            name: "${CONTAINER_NAME}",
                            image: "${ACR_NAME}.azurecr.io/${IMAGE_NAME}:${env.BUILD_NUMBER}",
                            cpu: '0.5',
                            memory: '1.5Gi',
                            ports: [[port: 80, protocol: 'TCP']]
                        ]]
                    )
                }
            }
        }
    }
}
