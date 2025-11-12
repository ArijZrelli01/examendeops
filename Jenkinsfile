pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'arijzrelli'
    }

    stages {
        stage('Checkout Git') {
            steps {
                checkout scmGit(
                    branches: [[name: '*/main']],  // ou '*/master' selon ta branche
                    extensions: [],
                    userRemoteConfigs: [[
                        url: 'https://github.com/ArijZrelli01/ton-repo.git',
                        credentialsId: 'github-arij-token'
                    ]]
                )
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    sh 'docker build -t ${DOCKER_REGISTRY}/hotel-service:devops ./hotel-service'
                    sh 'docker build -t ${DOCKER_REGISTRY}/client-service:devops ./client-service'
                    sh 'docker build -t ${DOCKER_REGISTRY}/booking-service:devops ./booking-service'
                }
            }
        }

        stage('Scan Images with Trivy') {
            steps {
                script {
                    sh 'trivy image ${DOCKER_REGISTRY}/hotel-service:devops'
                    sh 'trivy image ${DOCKER_REGISTRY}/client-service:devops'
                    sh 'trivy image ${DOCKER_REGISTRY}/booking-service:devops'
                }
            }
        }

        stage('Login to DockerHub') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )]) {
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    }
                }
            }
        }

        stage('Push Images to DockerHub') {
            steps {
                script {
                    sh 'docker push ${DOCKER_REGISTRY}/hotel-service:devops'
                    sh 'docker push ${DOCKER_REGISTRY}/client-service:devops'
                    sh 'docker push ${DOCKER_REGISTRY}/booking-service:devops'
                }
            }
        }

        stage('List Docker Images') {
            steps {
                sh 'docker images'
            }
        }
    }

    post {
        always {
            sh 'docker logout'
        }
    }
}