pipeline { 
    agent any 
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') { 
            steps { 
                withMaven(
                    maven: 'maven3.2.2'
                ) {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
    }
}
