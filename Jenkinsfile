pipeline {
    agent any

    tools {
        maven "3.9.6"
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
            environment {
                ScannerHome = tool 'SonarQube 5.0'
            }

            steps {
                echo "Analyzing with SonarQube.."
                script {
                    dir ('EcommerceApp') {
                        def compiledClassesDir = sh(script: 'mvn help:evaluate -Dexpression=project.build.outputDirectory -q -DforceStdout', returnStdout: true).trim()

                        withSonarQubeEnv('SonarQube') {
                            sh "${ScannerHome}/bin/sonar-scanner -Dsonar.projectKey=ecommerceapp -Dsonar.java.binaries=${compiledClassesDir}"
                        }
                    }
                }
            }
        }

        stage ('Upload to Nexus') {
            steps {
                nexusArtifactUploader artifacts: [[artifactId: 'EcommerceApp', classifier: '', file: '/var/lib/jenkins/workspace/ecommerce-app-pipeline/EcommerceApp/target/EcommerceApp.war', type: 'war']], credentialsId: 'nexus-id', groupId: 'com', nexusUrl: '16.171.135.9:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'ecommerce-app', version: '0.0.1-SNAPSHOT'
            }
        }

        stage ('Deploy to Tomcat') {
            steps {
                dir('EcommerceApp') {
                    deploy adapters: [tomcat9(credentialsId: 'tomcat-server', path: '', url: 'http://16.170.173.216:8080/')], contextPath: null, war: 'target/*.war'
                }
                
            }
        }
    }
}