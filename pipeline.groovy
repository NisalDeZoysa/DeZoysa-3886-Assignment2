pipeline {
    agent any 
    
    stages { 
        stage('SCM Checkout') {
            steps {
                retry(3) {
                    git branch: 'master', url: 'https://github.com/NisalDeZoysa/DeZoysa-3886-Assignment2'
                }
            }
        }
        stage('Build Docker Image') {
            steps {  
                bat 'docker build -t nisal2001/3886-node-app:%BUILD_NUMBER% .'
            }
        }
        stage('Login to Docker Hub') {
            steps {
                withCredentials([string(credentialsId: 'test-docker-hub-password', variable: 'test-docker')]) {
                    script {
                        bat "docker login -u nisal2001 -p ${test-docker}"
                    }
                }
            }
        }
        stage('Push Image') {
            steps {
                bat 'docker push nisal2001/3886-node-app:%BUILD_NUMBER%'
            }
        }
    }
    post {
        always {
            bat 'docker logout'
        }
    }
}