pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                script {
                    checkout scm
                }
            }
        }
        // stage('Build') {
        //     steps {
        //         script {
        //             sh 'mvn clean compile'
        //         }
        //     }
        // }
        stage('Test') {
            steps {
                script {
                    // Execute os testes e gera relatórios do Allure
                    // sh 'mvn test allure:report'
                    bat 'mvn clean test'
                }
            }
        }
        stage('Publish Allure Report') {
            steps {
                script {
                    sh 'allure generate target/allure-results -o target/allure-report'
                    archiveArtifacts 'target/allure-report'
                }
            }
        }
    }

    post {
        always {
            allure(
                includeProperties: false,
                jdk: '',
                results: [[path: 'target/allure-results']]
            )
        }
    }
}

