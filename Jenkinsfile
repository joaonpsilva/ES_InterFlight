pipeline {
    agent any
        tools {
        maven 'maven36'
    }

    environment {
        registry = 'esp12/sensors'
        registry2 = 'esp12/interflight'
        registryCredential = 'esp12dockerhub'
        dockerImage = ''
    }

    stages {
       /* stage('Testing Stage') {
            steps {
                parallel(
                    sensors: {
                        dir('Sensors') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Tests on Sensors"'
                                sh './mvnw test'
                            }
                        }
                    },
                    interFlight: {
                        dir('interFlight') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Tests on InterFlight"'
                                sh 'mvn test'
                                sh 'echo "Error, missing Database"'
                            }
                        }
                    }
                )
            }
        }*/

        stage('Compile Sensors Project') {
            steps {
                dir('Sensors') {
                        sh "chmod +x -R ${env.WORKSPACE}"
                        sh './mvnw clean install -DskipTests'
                }
            }
        }
        stage('Compile InterFlight Project') {
            steps {
                dir('interFlight') {
                    sh "chmod +x -R ${env.WORKSPACE}"
                    sh 'mvn -Dmaven.test.failure.ignore=true install' 
                }
            }
        }
        stage('Creating Docker Image') {
            steps {
                parallel(
                    sensors: {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                script {
                                    docker.withRegistry("http://192.168.160.48:5000") {
                                        dockerImage = docker.build("es_interflight/sensors", "Sensors")
                                        }                                    
                                }                        
                        }
                    },
                    interFlight: {
                        dir('interFlight') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Creating Docker Image on InterFlight"'
                                script {
                                    docker.withRegistry("http://192.168.160.48:5000") {
                                        dockerImage2 = docker.build registry2
                                        
                                        }
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
                    sensors: {
                        dir('Sensors') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Pushing Docker Image on Sensors"'
                                script {
                                    docker.withRegistry("http://192.168.160.48:5000") {
                                        dockerImage.push()
                                    }
                                }
                            }
                        }
                    },
                    interFlight: {
                        dir('interFlight') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Pushing Docker Image on InterFlight"'
                                script {
                                    docker.withRegistry("http://192.168.160.48:5000") {
                                        dockerImage2.push()
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
                    sensors: {
                        dir('Sensors') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Removing docker image on Sensors"'
                                sh "docker rmi $registry:$BUILD_NUMBER"
                            }
                        }
                    },
                    interflight: {
                        dir('interFlight') {
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

