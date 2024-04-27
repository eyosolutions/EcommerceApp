pipeline {
    agent any

    tools {
        maven "maven3"
    } 
    
    stages {
        stage('Git clone') {
            steps {
                git 'https://github.com/Chriscloudaz/EcommerceApp.git'
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

        // stage('SonarCloud analysis') {
        //     steps {
        //         dir ('EcommerceApp') {
        //             withSonarQubeEnv(credentialsId: 'sonar-cloud-token', installationName: 'SonarCloud') {
        //                 sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.11.0.3922:sonar -Dsonar.organization=parbtechsolutions -Dsonar.java.binaries=."
        //             }    
        //         }
        //     }
        // }

        stage('Build & Push to Docker Registry') {
            steps {
                dir ('EcommerceApp') {
                    script {
                    withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
                        sh "docker build -t chriscloudaz/ecommerceapp:latest ."
                        sh "docker push chriscloudaz/ecommerceapp:latest"
                        }
                    }
                }
            }
        }
    }
}