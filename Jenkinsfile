pipeline {
    agent any

    tools {
        maven "maven3"
    } 

    environment {
                ScannerHome = tool 'sonar-scanner'
    }
    
    stages {
        stage ('Git clone') {
            steps {
                sh 'echo "Git cloning.."'
                git branch: 'master', url: 'https://github.com/Chriscloudaz/EcommerceApp.git'
            }
        }

        stage ('Build and Test') {
            steps {
                // Specify the directory containing the pom.xml file
                dir('EcommerceApp') {
                    sh ''' 
                    echo "Build and testing.."
                    mvn clean test package
                    '''
                }
            }
        }

        stage ('SonarQube Analysis') {
            steps {
                echo "Analyzing with SonarQube.."
                script {
                    dir ('EcommerceApp') {
                        compiledClassesDir = sh(script: 'mvn help:evaluate -Dexpression=project.build.outputDirectory -q -DforceStdout', returnStdout: true).trim()

                        withSonarQubeEnv(credentialsId: 'sonar-token') {
                            sh "${ScannerHome}/bin/sonar-scanner -Dsonar.projectKey=ecommercefrontend -Dsonar.java.binaries=${compiledClassesDir}"
                        }
                    }
                }
            }
        }

        stage ('Upload to Nexus') {
            steps {
                dir('EcommerceApp') {
                    echo "Uploading to Nexus.."
                    withMaven(globalMavenSettingsConfig: 'global-maven', jdk: 'jdk17', maven: 'maven3', mavenSettingsConfig: '', traceability: true) {
                    sh "mvn deploy -DskipTests=true"
                    }
                } 
            }
        }

        stage ('Deploy to Tomcat') {
            steps {
                echo "Deploying to Tomcat.."
                dir('EcommerceApp') {
                    deploy adapters: [tomcat9(credentialsId: 'tomcat-server', path: '', url: 'http://16.171.39.72:8080/')], contextPath: null, war: 'target/*.war'
                }
            }
        }
    }
}