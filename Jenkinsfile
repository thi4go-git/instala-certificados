pipeline {

    agent any;

    tools {
       jdk 'JDK_11'
       maven 'MAVEN_3.8.8'
    }

   stages {     
        stage('Compilando') {
            steps {
                sh 'chmod 777 ./mvnw'
                sh './mvnw clean package -DskipTests=true'
            }
        }
        stage('Sonar Analise') {
          environment{
              scannerHome = tool 'SONAR_SCANNER'
          }
          steps {
              withSonarQubeEnv('SONAR'){
                  sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=instala-certificados -Dsonar.host.url=http://cloudtecnologia.dynns.com:9000 -Dsonar.login=29404ff3d2569181a0a2157361856d71da1bc273 -Dsonar.java.binaries=target"
              }
          }
      }
      stage('Sonar QualityGate') {
          steps {
              sleep(20)
              timeout(time: 1, unit: 'MINUTES'){
                  waitForQualityGate abortPipeline: true
              }
          }
      }    
    
   }

   post{
        always {
             junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
             script {
                 if (currentBuild.result == 'FAILURE') {
                      echo "Build Com erro(s)!"
                      emailext attachLog: true, body: 'LOG:', subject: "BUILD ${BUILD_NUMBER} instala-certificados Executado com Erro(s)!", to: 'thi4go19+jenkins@gmail.com'
                 } else {
                      echo "Build bem-sucedido!"
                      emailext attachLog: true, body: 'LOG:', subject: "BUILD ${BUILD_NUMBER} instala-certificados Executado com Sucesso!", to: 'thi4go19+jenkins@gmail.com'
                 }
             }
        }
   }

}