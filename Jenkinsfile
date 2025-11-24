pipeline {
    agent any
 
    environment {
        DOCKER_REGISTRY = 'arijzrelli'
    }
 
    stages {
        stage('Checkout Git') {
            steps {
                checkout scmGit(
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[
                        url: 'https://github.com/ArijZrelli01/examendeops.git',
                        credentialsId: 'github-arij-token'
                    ]]
                )
            }
        }
 
 
        stage('Build Hotel Service') {
            steps {
                script {
                    sh 'docker build -t ${DOCKER_REGISTRY}/hotel-service:devops ./hotel-service'
                }
            }
        }
 
        stage('Build Client Service') {
            steps {
                script {
                    sh 'docker build -t ${DOCKER_REGISTRY}/client-service:devops ./client-service'
                }
            }
        }
 
        stage('Build Booking Service') {
            steps {
                script {
                    sh 'docker build -t ${DOCKER_REGISTRY}/booking-service:devops ./booking-service'
                }
            }
        }
 
        stage('Build Gateway Service') {
            steps {
                script {
                    sh 'docker build -t ${DOCKER_REGISTRY}/gateway-service:devops ./gateway-service'
                }
            }
        }
 
        stage('Build Discovery Service') {
            steps {
                script {
                    sh 'docker build -t ${DOCKER_REGISTRY}/discovery-service:devops ./discovery-service'
                }
            }
        }
 
 
        stage('Scan Hotel Service') {
            steps {
                script {
                    sh 'trivy image ${DOCKER_REGISTRY}/hotel-service:devops'
                }
            }
        }
 
        stage('Scan Client Service') {
            steps {
                script {
                    sh 'trivy image ${DOCKER_REGISTRY}/client-service:devops'
                }
            }
        }
 
        stage('Scan Booking Service') {
            steps {
                script {
                    sh 'trivy image ${DOCKER_REGISTRY}/booking-service:devops'
                }
            }
        }
 
        stage('Scan Gateway Service') {
            steps {
                script {
                    sh 'trivy image ${DOCKER_REGISTRY}/gateway-service:devops'
                }
            }
        }
 
        stage('Scan Discovery Service') {
            steps {
                script {
                    sh 'trivy image ${DOCKER_REGISTRY}/discovery-service:devops'
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
 
 
        stage('Push Hotel Service') {
            steps {
                script {
                    sh 'docker push ${DOCKER_REGISTRY}/hotel-service:devops'
                }
            }
        }
 
        stage('Push Client Service') {
            steps {
                script {
                    sh 'docker push ${DOCKER_REGISTRY}/client-service:devops'
                }
            }
        }
 
        stage('Push Booking Service') {
            steps {
                script {
                    sh 'docker push ${DOCKER_REGISTRY}/booking-service:devops'
                }
            }
        }
 
        stage('Push Gateway Service') {
            steps {
                script {
                    sh 'docker push ${DOCKER_REGISTRY}/gateway-service:devops'
                }
            }
        }
 
        stage('Push Discovery Service') {
            steps {
                script {
                    sh 'docker push ${DOCKER_REGISTRY}/discovery-service:devops'
                }
            }
        }
 
    }
 
 
}
