## Example for dynamically providing datasource parameters to a Tomcat based servlet in Openshift

Template source derived from:
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
  SOURCE_REPOSITORY_URL = https://github.com/travisrogers05/examples
  SOURCE_REPOSITORY_REF = master
  CONTEXT_DIR = tomcat-servlet-jdbc
  DB_JNDI=jdbc/testDB
  DB_DATABASE=exampledb
  ```

4.  Click on the "Create" button or use this CLI command (replace **app-name** with a name you choose):

  ```
  oc process jws30-tomcat8-servlet-example-s2i -n openshift \
  -v APPLICATION_NAME=<app-name>,SOURCE_REPOSITORY_URL=https://github.com/travisrogers05/examples,SOURCE_REPOSITORY_REF=master,CONTEXT_DIR=tomcat-servlet-jdbc,DB_JNDI=jdbc/testDB,DB_DATABASE=exampledb \
  | oc create -f -
  ```

to be finished...

