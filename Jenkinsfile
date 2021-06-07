def remote = [:]
remote.host = '192.168.160.87'
remote.name = 'playground'

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
        stage('Testing Stage') {
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
        }

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
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Creating Docker Image on InterFlight"'
                                script {
                                    docker.withRegistry("http://192.168.160.48:5000") {
                                        dockerImage2 = docker.build("es_interflight/interflight", "InterFlight")
                                        
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
        stage('Deploying') {
            steps {
                sh "chmod +x -R ${env.WORKSPACE}"
                sh 'echo "Deploying"'
                withCredentials([usernamePassword(credentialsId: 'esp12_ssh', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {

                    echo "$remote.host"

                    script {
                        remote.user = USERNAME
                        remote.password = PASSWORD
                        remote.allowAnyHosts = true

                    }

                    echo "$remote.user"

                    sshCommand remote: remote, command: 'docker stop esp12_sensors || echo "Do not have that image"'
                    sshCommand remote: remote, command: 'docker rm esp12_sensors || echo "Do not have that image"'
                    sshCommand remote: remote, command: 'docker rmi 192.168.160.48:5000/es_interflight/sensors || echo "Do not have that image"'
                    sshCommand remote: remote, command: "docker pull 192.168.160.48:5000/es_interflight/sensors"
                    sshCommand remote: remote, command: "docker create -p 12025:12025 --name esp12_sensors 192.168.160.48:5000/es_interflight/sensors"
                    sshCommand remote: remote, command: "docker start esp12_sensors"
                    
                    
                    sshCommand remote: remote, command: 'docker stop esp12_interflight || echo "Do not have that image"'
                    sshCommand remote: remote, command: 'docker rm esp12_interflight || echo "Do not have that image"'
                    sshCommand remote: remote, command: 'docker rmi 192.168.160.48:5000/es_interflight/interflight || echo "Do not have that image"'
                    sshCommand remote: remote, command: "docker pull 192.168.160.48:5000/es_interflight/interflight"
                    sshCommand remote: remote, command: "docker create -p 12025:12025 --name esp12_interflight 192.168.160.48:5000/es_interflight/interflight"
                    sshCommand remote: remote, command: "docker start esp12_interflight"

                    
                }
            }
        }
    }
}


