pipeline {
    agent any
    tools {
        maven 'MVN_HOME' // Must match the name in Global Tool Configuration
        jdk 'JAVA_HOME'
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/jadhavmahesh1170/AutomationSeleniumJava', branch: 'main'
            }
        }
        stage('Build & Test') {
            steps {
                // Runs maven clean and executes tests
                sh 'mvn clean test' 
            }
        }
    }
    post {
        always {
            // Publishes test results even if the build fails
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
