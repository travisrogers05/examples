// initialize variables
def var1
def var2

stage name: 'Commit Stage'

node('master') {
   // Checkout the code from GitHub repository
   //git url: 'https://github.com/Inmarsat-itcloudservices/contacts-example.git'
   checkout scm

   // Get the maven tool.
   // ** NOTE: The 'Maven 3.3.9' maven tool is configured in the global configuration for Jenkins.
   def mvnHome = tool 'Maven 3.3.9'

   // Run the maven build
   sh "${mvnHome}/bin/mvn clean install"
}

stage name: 'Acceptance Tests Stage'
node('master')
{
	echo 'Acceptance tests scripting goes here'
}

stage name: 'Deployment Stage'
node('master')
{
	echo 'Deployment scripting goes here'
}

stage name: 'Pipeline Complete Stage'
node('master')
{
   echo 'Pipeline Complete'
}
