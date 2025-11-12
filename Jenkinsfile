pipeline {
    agent {
        docker {
            image 'docker:latest'
            args '-v /var/run/docker.sock:/var/run/docker.sock -u root'
        }
    }

    environment {
        DOCKER_REGISTRY = 'arijzrelli'
    }

    stages {
        stage('Checkout Git') {
            steps {
                git(
                    url: 'https://github.com/ArijZrelli01/examendeops.git',
                    credentialsId: 'github-arij-token',
                    branch: 'main'
                )
            }
        }

        stage('Verify Structure') {
            steps {
                script {
                    sh '''
                        echo "=== Vérification de la structure ==="
                        ls -la
                        echo "=== Dossiers de services ==="
                        find . -type d -name "*service*" 2>/dev/null || echo "Aucun dossier service trouvé"
                    '''
                }
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
                    sh 'docker run --rm aquasec/trivy image ${DOCKER_REGISTRY}/hotel-service:devops'
                    sh 'docker run --rm aquasec/trivy image ${DOCKER_REGISTRY}/client-service:devops'
                    sh 'docker run --rm aquasec/trivy image ${DOCKER_REGISTRY}/booking-service:devops'
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
    }

    post {
        always {
            sh 'docker logout'
        }
    }
}