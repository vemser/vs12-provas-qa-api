pipeline {
    agent any

    tools {
        maven "MAVEN"
        nodejs "NODE"
        git "GIT"
    }

    environment {
        EMAIL_ADM=
        SENHA_ADM=

        LOGIN_ADMIN=
        SENHA_ADMIN=

        LOGIN_MODERADOR=
        SENHA_MODERADOR=

        LOGIN_CANDIDATO=
        SENHA_CANDIDATO=

        LOGIN_JENK=
        SENHA_JENK=

        LOGIN_GESTOR=
        EMAIL_GESTOR=
        SENHA_GESTOR=

        IMGUR_LINK=
        IMGUR_CLIENT_ID=

        DOMINIO_APP=
        DOMINIO_APP_LOGIN=

        DISCORD_WEBHOOK_URL=
    }

    stages {
        stage('Rodar testes UI') {
            steps {
                script {
                    try {
                        checkout([$class: 'GitSCM', branches: [[name: 'refactor/estrutura-projeto']], userRemoteConfigs: [[url: 'https://github.com/vemser/vs12-provas-qa-ui']]])
                        sh "npm i"
                        sh "npm i -g allure-commandline"
                        sh "npx cypress run --reporter mocha-allure-reporter"
                        sh "npx allure generate --clean"
                        def resultUI = currentBuild.result
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        echo "Erro ao executar testes UI: ${e.message}"
                    }
                }
            }
        }
        stage('Rodar testes API') {
            steps {
                script {
                    try {
                        dir('vs-12-provas-qa-api') {
                            checkout([$class: 'GitSCM', branches: [[name: 'develop']], userRemoteConfigs: [[url: 'https://github.com/vemser/vs12-provas-qa-api']]])
                            sh "mvn clean test"
                            sh "allure generate --clean -o allure-results"
                            def resultAPI = currentBuild.result
                        }
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        echo "Erro ao executar testes API: ${e.message}"
                    }
                }
            }
        }
    }

    post {
        always {

            allure([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'allure-results'],
                        [path: 'vs-12-provas-qa-api/allure-results']]
            ])
            script {
                def imgurLink = sh(script: "node capture.js ${BUILD_NUMBER} ${JOB_NAME}", returnStdout: true).trim()
                IMGUR_LINK = imgurLink
                def fowardNgrok = DOMINIO_APP + "/job/" + JOB_NAME + "/" + BUILD_NUMBER + "/allure/"
                discordSend(
                    description: "Report atualizado - Clique no link",
                        link: fowardNgrok,
                        result: currentBuild.currentResult,
                        title: "[LINK] Pipeline: Testes API & UI | Job: develop | Build: #${BUILD_NUMBER}",
                        webhookURL: DISCORD_WEBHOOK_URL,
                        image: "${IMGUR_LINK}"
                )
            }
        }
    }
}