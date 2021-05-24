pipeline {
    agent any
        tools {
        maven 'maven36'
    }

    environment {
        registry = 'esp12/Sensors'
        registry2 = 'esp12/InterFlight'
        registryCredential = 'esp12dockerhub'
        dockerImage = ''
    }

    stages {

        stage('Compilation Sensors') {
            steps {
                dir('Sensors') {
                        sh "chmod +x -R ${env.WORKSPACE}"
                        sh 'echo "Clean install on Sensors"'
                        sh './mvnw clean install -DskipTests'
                }
            }
        }
        stage('Compilation InterFlight') {
            steps {
                dir('InterFlight') {
                    sh "chmod +x -R ${env.WORKSPACE}"
                    sh 'echo "Clean install on InterFlight"'
                    sh './mvnw clean install -DskipTests'
                }
            }
        }
        stage('Create Docker Image') {
            steps {
                parallel(
                    kafkaConsumer: {
                        dir('Sensors') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Creating Docker Image on Sensors"'
                                script {
                                    dockerImage = docker.build registry
                                }
                            }
                        }
                    },
                    projectbackend: {
                        dir('InterFlight') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Creating Docker Image on InterFlight"'
                                script {
                                    dockerImage2 = docker.build registry2
                                }
                            }
                        }
                    }
                )
            }
        }
        stage('Pushing Docker Image') {
            steps {
                parallel(
                    kafkaConsumer: {
                        dir('Sensors') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Pushing Docker Image on Sensors"'
                                script {
                                    docker.withRegistry( '', registryCredential ) {
                                        dockerImage.push("$BUILD_NUMBER")
                                        dockerImage.push('latest')
                                    }
                                }
                            }
                        }
                    },
                    projectbackend: {
                        dir('InterFlight') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Pushing Docker Image on InterFlight"'
                                script {
                                    docker.withRegistry( '', registryCredential ) {
                                        dockerImage2.push("$BUILD_NUMBER")
                                        dockerImage2.push('latest')
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
        stage('Removing Docker Image') {
            steps {
                parallel(
                    kafkaConsumer: {
                        dir('Sensors') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Removing docker image on Sensors"'
                                sh "docker rmi $registry:$BUILD_NUMBER"
                            }
                        }
                    },
                    projectbackend: {
                        dir('InterFlight') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Removing docker image on InterFlight"'
                                sh "docker rmi $registry2:$BUILD_NUMBER"
                            }
                        }
                    }
                )
            }
        }
    }
}

