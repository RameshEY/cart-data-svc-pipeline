pipeline{
    environment {
     registry = "testdockerramesh/cart-data-service"
     registryCredential = 'dockerhub'
     dockerImage = ''
    }
    agent any
    stages{
        stage('Build and Test Application') {
            steps{
                sh 'chmod +x mvnw'
                sh './mvnw clean install'
            }
        }
        stage('Sonar Analysis') {
            steps{
                echo 'Sonar analysis need to be added'
            }
        }
       stage('Building Docker image') {
      steps{
        script {
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }
      }
    }
    stage('Push Docker Image') {
      steps{
         script {
            docker.withRegistry( '', registryCredential ) {
            dockerImage.push()
          }
        }
      }
    }
    }
}
