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
        dockerImage2 = ''
        dockerImage3 = ''
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
        }
     */



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

        stage('Deploying Artifact') {
            steps {
                parallel(
                    sensors: {
                        dir('Sensors') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                echo 'Deploying Artifact'
                                sh './mvnw deploy -f pom.xml -s ../settings.xml'
                            }
                        }
                    },
                    interFlight: {
                        dir('interFlight') {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                echo 'Deploying Artifact'
                                sh 'mvn deploy -DskipTests -f pom.xml -s ../settings.xml'
                            }
                        }
                    }
                )
            }
        }


        stage('Creating Docker Image') {
            steps {
                parallel(
                    sensors: {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                //sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Creating Docker Image on Sensors"'
                                script {
                                    docker.withRegistry("http://192.168.160.48:5000") {
                                        dockerImage = docker.build("es_interflight/sensors", "Sensors")
                                        }                                    
                                }                        
                        }
                    },
                    interFlight: {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                //sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Creating Docker Image on InterFlight"'
                                script {
                                    docker.withRegistry("http://192.168.160.48:5000") {
                                        dockerImage2 = docker.build("es_interflight/interflight", "interFlight")
                                        
                                        }
                                }
                            }
                        },
                    frontEnd: {
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                //sh "chmod +x -R ${env.WORKSPACE}"
                                sh 'echo "Creating Docker Image on frontEnd"'
                                script {
                                    docker.withRegistry("http://192.168.160.48:5000") {
                                        dockerImage3 = docker.build("es_interflight/frontend", "interflight-frontend")
                                        
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
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            //sh "chmod +x -R ${env.WORKSPACE}"
                            sh 'echo "Pushing Docker Image on Sensors"'
                            script {
                                docker.withRegistry("http://192.168.160.48:5000") {
                                    dockerImage.push()
                                    dockerImage.push('latest')
                                }
                            }
                        }
                    },
                    interFlight: {
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            //sh "chmod +x -R ${env.WORKSPACE}"
                            sh 'echo "Pushing Docker Image on InterFlight"'
                            script {
                                docker.withRegistry("http://192.168.160.48:5000") {
                                    dockerImage2.push()
                                    dockerImage2.push('latest')
                                }
                            }
                        }   
                    },
                    frontEnd: {                    
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            //sh "chmod +x -R ${env.WORKSPACE}"
                            sh 'echo "Pushing Docker Image on frontEnd"'
                            script {
                                docker.withRegistry("http://192.168.160.48:5000") {
                                    dockerImage3.push()
                                    dockerImage3.push('latest')
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
                    //sshCommand remote: remote, command: "docker create -p 12025:12025 --name esp12_sensors 192.168.160.48:5000/es_interflight/sensors"
                    //sshCommand remote: remote, command: "docker start esp12_sensors"
                    
                    
                    sshCommand remote: remote, command: 'docker stop esp12_interflight || echo "Do not have that image"'
                    sshCommand remote: remote, command: 'docker rm esp12_interflight || echo "Do not have that image"'
                    sshCommand remote: remote, command: 'docker rmi 192.168.160.48:5000/es_interflight/interflight || echo "Do not have that image"'
                    sshCommand remote: remote, command: "docker pull 192.168.160.48:5000/es_interflight/interflight"
                    //sshCommand remote: remote, command: "docker create -p 12026:12026 --name esp12_interflight 192.168.160.48:5000/es_interflight/interflight"
                    //sshCommand remote: remote, command: "docker start esp12_interflight"

                    sshCommand remote: remote, command: 'docker stop esp12_frontend || echo "Do not have that image"'
                    sshCommand remote: remote, command: 'docker rm esp12_frontend || echo "Do not have that image"'
                    sshCommand remote: remote, command: 'docker rmi 192.168.160.48:5000/es_interflight/frontend || echo "Do not have that image"'
                    sshCommand remote: remote, command: "docker pull 192.168.160.48:5000/es_interflight/frontend"
                    //sshCommand remote: remote, command: "docker run -it -d -p 12027:3000 -e CHOKIDAR_USEPOLLING=true --name esp12_frontend 192.168.160.48:5000/es_interflight/frontend"
                    sshCommand remote: remote, command: "docker create -p 12027:300 --name esp12_frontend 192.168.160.48:5000/es_interflight/frontend"
                    sshCommand remote: remote, command: "docker start esp12_frontend"
                    //
                    //sshPut(from: './logstash/pipeline/logstash.conf', remote: remote, into: '/home/esp12/logstash')
                    sshPut(from: 'docker-compose.yml', remote: remote, into: '/home/esp12')
                    sshCommand remote: remote, command: '/bin/bash -c \'docker-compose pull\''
                    sshCommand remote: remote, command: '/bin/bash -c \'docker-compose up -d\''
                    
                }
            }
        }
    }
}


