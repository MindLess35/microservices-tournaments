pipeline {
    agent any

    environment {
        IMAGE_NAME = 'mindless35/tournaments:user-service-${BUILD_NUMBER}'
        DOCKER_CONTEXT = '.'
        DOCKERFILE = 'user/Dockerfile'
    }

    stages {
        stage('Clone') {
            steps {
                git url: 'https://твой-репозиторий.git', branch: 'main'
            }
        }

        stage('Build common-exception') {
            steps {
                dir('common-exception') {
                    sh './mvnw clean install -DskipTests'
                }
            }
        }

        stage('Build Docker image') {
            steps {
                sh """
                    docker build \
                      -t $IMAGE_NAME \
                      --build-arg JAR_VERSION=1.0 \
                      -f ${DOCKERFILE} \
                      ${DOCKER_CONTEXT}
                """
            }
        }

        stage('Push Docker image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh """
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push $IMAGE_NAME
                    """
                }
            }
        }

        stage('Deploy to Minikube') {
            steps {
                sh """
                    kubectl set image deployment/user-deployment \
                      user-service=$IMAGE_NAME \
                      --namespace=default
                """
            }
        }
    }
}
