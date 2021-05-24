pipeline {
    agent any

    environment {
        registry = 'esp12/Sensors'
        registry2 = 'esp12/InterFlight'
        registryCredential = 'esp12dockerhub'
        dockerImage = ''
    }

    stages {
        stage('Test Stage') {
            steps {
                parallel(
                    kafkaConsumer: {
                        dir('Sensors') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Tests on kafkaConsumer"'
                                sh './mvnw test'
                            }
                        }
                    },
                    projectbackend: {
                        dir('InterFlight') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Tests on projectbackend"'
                                sh './mvnw test'
                                sh 'echo "Error due to not having a database"'
                            }
                        }
                    }
                )
            }
        }
        stage('Compilation kafkaConsumer') {
            steps {
                dir('Sensors') {
                        sh "chmod +x -R ${env.WORKSPACE}"
                        sh 'echo "Clean install on kafkaConsumer"'
                        sh './mvnw clean install -DskipTests'
                }
            }
        }
        stage('Compilation projectbackend') {
            steps {
                dir('InterFlight') {
                    sh "chmod +x -R ${env.WORKSPACE}"
                    sh 'echo "Clean install on projectbackend"'
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
                                sh 'echo "Creating Docker Image on kafkaConsumer"'
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
                                sh 'echo "Creating Docker Image on projectbackend"'
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
                                sh 'echo "Pushing Docker Image on kafkaConsumer"'
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
                                sh 'echo "Pushing Docker Image on projectbackend"'
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
                                sh 'echo "Removing docker image on kafkaConsumer"'
                                sh "docker rmi $registry:$BUILD_NUMBER"
                            }
                        }
                    },
                    projectbackend: {
                        dir('InterFlight') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Removing docker image on projectbackend"'
                                sh "docker rmi $registry2:$BUILD_NUMBER"
                            }
                        }
                    }
                )
            }
        }
    }
}

