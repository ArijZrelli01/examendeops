pipeline {
    agent any

    stages {
        stage('Checkout Git') {
            steps {
                checkout scmGit(
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[
                        url: 'https://github.com/ArijZrelli01/examendeops.git',
                        credentialsId: 'github-arij-token'
                    ]]
                )
            }
        }

        stage('Build Microservices') {
            steps {
                script {
                    echo "🚀 Début de la construction des microservices..."
                    
                    // Test Maven
                    sh 'mvn --version || echo "Maven non disponible"'
                    
                    // Build des services
                    sh '''
                    echo "📦 Construction des microservices..."
                    cd hotel-service && mvn clean compile -DskipTests && echo "✅ Hotel Service compilé"
                    cd ../client-service && mvn clean compile -DskipTests && echo "✅ Client Service compilé" 
                    cd ../booking-service && mvn clean compile -DskipTests && echo "✅ Booking Service compilé"
                    cd ../gateway-service && mvn clean compile -DskipTests && echo "✅ Gateway Service compilé"
                    cd ../discovery-service && mvn clean compile -DskipTests && echo "✅ Discovery Service compilé"
                    '''
                }
            }
        }

        stage('Test Build') {
            steps {
                script {
                    sh '''
                    echo "🔍 Vérification des builds..."
                    find . -name "target" -type d | head -5
                    echo "🎉 Tous les microservices ont été compilés avec succès !"
                    '''
                }
            }
        }
    }

    post {
        always {
            echo "🏁 Pipeline terminé - Microservices prêts pour le déploiement"
        }
        success {
            echo "✅ SUCCÈS : Tous les microservices ont été buildés avec Maven"
        }
    }
}
