pipeline {

    agent any

    tools {
        jdk 'jdk-17' // Jenkins에서 설정한 이름과 일치해야 함
    }

    environment {
        APP_REPO_URL = 'https://github.com/f-lab-edu/dev-tribe-feed-service.git'
        DOCKERHUB_CREDENTIALS = credentials('docker-hub-access-token') // jenkins에 등록해 놓은 docker hub credentials 이름
        DOCKERHUB_REPOSITORY = "codongmin/dev-tribe-api-server"  //docker hub id와 repository 이름
        TARGET_HOST = credentials('api-host')
    }

    stages {
        stage('Checkout Git As BranchName'){
            steps {
                sh "pwd"
                echo "git clone"
                script {
                    try {
                        if("${env.BRANCH_NAME}" == "origin/main"){
                            print("selected origin/main")
                            throw new Exception("Branch selection is required")
                        }

                    }catch (err) {
                        echo "Caught: ${err}"
                        currentBuild.result = 'FAILURE'
                    }
                }

                checkout scm: [
                        $class: 'GitSCM',
                        userRemoteConfigs: [[url: "${env.APP_REPO_URL}"]],
                        branches: [[name: "${params.BRANCH_NAME}"]]
                ],
                        poll: false
            }
        }

        stage('Build Source Code'){
            steps {
                sh "pwd"
                sh 'echo $JAVA_HOME'
                sh './gradlew :devtribe-feed-api:clean :devtribe-feed-api:bootJar'
            }
        }

        stage('Build docker image') {
            steps {
                dir('devtribe-feed-api') {
                    sh "docker build --no-cache --platform linux/amd64 -t $DOCKERHUB_REPOSITORY ."
                }
            }
        }

        stage('Login to Docker Hub') {
            steps {
                sh "echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin"
            }
        }

        stage('Deploy Docker image') {
            steps {
                script {
                    sh 'docker push $DOCKERHUB_REPOSITORY' //docker push
                }
            }
        }

        stage('Cleaning up') {
            steps {
                sh "docker rmi $DOCKERHUB_REPOSITORY:latest" // docker image 제거
            }
        }

        stage('Start Application as Docker') {
            steps {
                sshagent (credentials: ['jenkins-controller']) {
                    sh """
                    ssh -o StrictHostKeyChecking=no ubuntu@${TARGET_HOST} '
                    pwd
                    docker compose -f docker-compose.dev.yml down
                    docker rmi ${DOCKERHUB_REPOSITORY}:latest
                    docker pull ${DOCKERHUB_REPOSITORY}
                    docker compose -f docker-compose.dev.yml up -d
                    '
                """
                }
            }
        }
    }

}