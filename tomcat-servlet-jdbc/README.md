## Example for dynamically providing datasource parameters to a Tomcat based servlet in Openshift

Credit to The Open Tutorials for the example servlet application that has been slightly modified:
- http://theopentutorials.com/examples/java-ee/servlet/servlet-jndi-datasource-in-tomcat/

Template source for jws30-tomcat8-servlet-example-s2i derived from:
- [jws30-tomcat8-basic-s2i](https://github.com/jboss-openshift/application-templates/blob/master/webserver/jws30-tomcat8-basic-s2i.json)
- [jws30-tomcat8-mysql-s2i](https://github.com/jboss-openshift/application-templates/blob/master/webserver/jws30-tomcat8-mysql-s2i.json)


#### Issue these commands from CLI to ensure you are not the system:admin, unless you mean to be.  Also that you are not operating on the default project.
```
oc login -u <some_user>
oc whoami
oc project
```

#### Create a project containing a Tomcat 8 based application.  Use the web console or the supplied CLI commands for 1-3.  Use the CLI for 4.

1.  Create a new project (replace **project-name** with a name you choose)

  ```
  oc new-project <project-name>
  ```

2.  Create a new template to create the application pod

  ```
  oc create -f jws30-tomcat8-servlet-example-s2i.json
  ```

3.  Add an application to the project selecting the jws30-tomcat8-servlet-example-s2i template.  Provide the following fields for this example app:

  ```
  DB_DATABASE=exampledb
  DB_USERNAME=joe
  DB_PASSWORD=redhat
  ```

4.  Click on the "Create" button or use this CLI command (replace **app-name** with a name you choose):

  ```
  oc process jws30-tomcat8-servlet-example-s2i -n openshift \
  -v APPLICATION_NAME=jws-app,DB_DATABASE=exampledb,DB_USERNAME=joe,DB_PASSWORD=redhat,CATALINA_OPTS_APPEND=-DresourceRefName=$DB_JNDI \
  | oc create -f -
  ```

#### Two approaches to try
##### Use the default mechanisms around the DB_SERVICE_PREFIX_MAPPING environment variable which creates predetermined datasources based on environment variables
Consider DB_SERVICE_PREFIX_MAPPING=test-mysql=DB

This will create a datasource with java:jboss/datasources/test_mysql name. Additionally all the required settings like password and username will be expected to be provided as env variables with the DB_ prefix, for example DB_USERNAME and DB_PASSWORD.

Advantages to this approach
- A context.xml file is created dynamically that contains appropriate datasource settings based on environment variable provided via deploymentconfig
- Multiple datasources can can configured. Ex DB_SERVICE_PREFIX_MAPPING=test-mysql=DB,second-postgresql=SECOND,...
- Drivers are already provided for MySQL, Postgres and MongoDB

##### Supply context.xml with application WAR file
- This would simply include a META-INF/context.xml that includes specific datasource parameters


#### Future Additions...
- SSL assets, secrets and service account(s) needed for Tomcat 8 via SSL


