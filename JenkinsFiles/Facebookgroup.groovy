pipeline{
    agent {
        label 'maven'
    }
    stages {
        stage('Test') {
            steps {
                script {
                    echo 'Test Execution'
                    sh 'mvn test-f pom.xml'
                }
            }
        }
    }
}