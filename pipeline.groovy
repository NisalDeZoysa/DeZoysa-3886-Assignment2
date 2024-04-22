pipeline {
  agent any
  triggers{
    githubPush()
  }
  stages {
    stage('build'){
      steps {
        bat 'docker build -t nisal2001/3886-node-app .'
      }
    }
    stage('run'){
      steps{
        bat 'docker run -d -p 5000:3000 nisal2001/3886-node-app'
      }
    }
    stage('status'){
      steps{
        bat 'docker ps'
      }
    }
  }
}