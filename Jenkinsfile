pipeline {
    agent any

       environment {
           // Retrieve each secret using Jenkins credentials
           BASE_URI         = credentials('BASE_URI')
           ACCOUNT_BASE_URI = credentials('ACCOUNT_BASE_URI')
           CLIENT_ID        = credentials('CLIENT_ID')
           CLIENT_SECRET    = credentials('CLIENT_SECRET')
           REFRESH_TOKEN    = credentials('REFRESH_TOKEN')
           USER_ID          = credentials('USER_ID')
       }

    stages {

        stage('Build and Test') {
            steps {
                echo "Using environment variables in Maven test stage"
                sh """
                    mvn clean test  \
                        -DBASE_URI=$BASE_URI  \
                        -DACCOUNT_BASE_URI=$ACCOUNT_BASE_URI \
                        -Dclient_id=$CLIENT_ID    \
                        -Dclient_secret=$CLIENT_SECRET    \
                        -Dgrant_type=refresh_token  \
                        -Drefresh_token=$REFRESH_TOKEN \
                        -Duser_id=$USER_ID
                """
            }
        }
    }
}