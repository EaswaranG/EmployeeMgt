pipeline {
    agent any

    environment {
        ACR_NAME = 'employeemgt'
        ACR_IMAGE_NAME = 'employee-mgt'
        VM_USER = 'azureuser'
        VM_USERNAME = 'azureuser'
        VM_IP_ADDRESS = '4.205.87.153'
        ACR_USERNAME = 'employeemgt'
        ACR_PASSWORD = 'U9Jz38QVnCKToswkCGgDHIflu2xezCLc+JYkreF7Gw+ACRAEeJk0'
    }

     stages {
            stage('Pull Docker Image') {
                steps {
                    script {
                        docker.withRegistry("https://${ACR_NAME}.azurecr.io", 'acr-credentials') {
                            def image = docker.image("${ACR_NAME}.azurecr.io/${ACR_IMAGE_NAME}")
                            image.pull()
                        }
                    }
                }
            }

            stage('Deploy to Azure VM') {
                steps {
                    script {
                        sh "docker save -o ${ACR_IMAGE_NAME}.tar.gz ${ACR_NAME}.azurecr.io/${ACR_IMAGE_NAME}" // Save image as tar.gz file

                        sh "sshpass -p ${VM_PASSWORD} scp ${ACR_IMAGE_NAME}.tar.gz ${VM_USERNAME}@${VM_IP_ADDRESS}:~/" // Copy image to VM

                        sshagent(credentials: ['vm-ssh-credentials']) {
                            sh "ssh -o StrictHostKeyChecking=no ${VM_USERNAME}@${VM_IP_ADDRESS} 'docker load -i ${ACR_IMAGE_NAME}.tar.gz && docker run -d -p 80:80 ${ACR_IMAGE_NAME}'" // Load and run image on VM
                        }
                    }
                }
            }
        }
    }
