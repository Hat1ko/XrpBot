def project = 'cryptex-stage-251715'
def  appName = 'wrapper-ripple'
def branchName = 'develop'
def  imageTag = "gcr.io/${project}/${appName}:${env.BRANCH_NAME}.${env.BUILD_NUMBER}"

pipeline {
  agent {
    kubernetes {
      label 'wrapper-ripple'
      defaultContainer 'jnlp'
      yaml """
apiVersion: v1
kind: Pod
metadata:
labels:
  component: ci
spec:
  # Use service account that can deploy to all namespaces
  serviceAccountName: cd-jenkins
  containers:
  - name: gradle
    image: gradle:5.4.1-jdk8
    command:
    - cat
    tty: true
  - name: gcloud
    image: gcr.io/cloud-builders/gcloud
    command:
    - cat
    tty: true
  - name: kubectl
    image: gcr.io/cloud-builders/kubectl
    command:
    - cat
    tty: true
"""
}
  }
  stages {
    stage('Test') {
      steps {
          sh """
           echo "Not necessery step as it will be run while building"
          """
      }
    }
    stage('Build and push image with Container Builder') {
      steps {
        container('gcloud') {
          sh " gcloud  builds submit -t ${imageTag} ."
        }
      }
    }

    stage('Deploy Production') {

      steps{
        container('kubectl') {
        // Change deployed image in canary to the one we just built
          sh("sed -i.bak 's#gcr.io/cloud-solutions-images/wrapper-ripple:1.0.0#${imageTag}#' ./k8s/production/*.yml")
          sh("kubectl --namespace=default apply -f k8s/services/")
          sh("kubectl --namespace=default apply -f k8s/production/")
        }
      }
    }
  }
}