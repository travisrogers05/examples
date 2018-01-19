## Example of creating an Openshift container using s2i based on JBoss EAP and a binary (WAR) file

#### Issue these commands from CLI to ensure you are not the system:admin, unless you mean to be.  Also that you are not operating on the default project.
```
oc login -u <some_user>
oc whoami
oc project
```

#### Create a project containing an EAP based application.  Use the web console or the supplied CLI commands for 1-3.  Use the CLI for 4.

1.  Create a new project (replace **project-name** with a name you choose)

  ```
  oc new-project project-name
  ```

2.  Add the following service account and roles. (replace **project-name** and **app-name** with a names you choose)

  ```
  oc create serviceaccount eap-service-account -n $(oc project -q)
  oc policy add-role-to-user view system:serviceaccount:$(oc project -q):default -n $(oc project -q)
  oc policy add-role-to-user view system:serviceaccount:$(oc project -q):eap-service-account -n $(oc project -q)
  ```

3.  Add an application to the project.  Provide the following fields for this example app:

  ```
  SOURCE_REPOSITORY_URL = https://github.com/travisrogers05/examples
  SOURCE_REPOSITORY_REF = master
  CONTEXT_DIR = eap-cluster-demo
  ```

4.  This example command uses an EAP 7.0 template image.  Click on the "Create" button in the web console or use this CL command (replace **app-name** with a name you choose):

  ```
oc process openshift//eap70-basic-s2i \
-v APPLICATION_NAME=app-name -v SOURCE_REPOSITORY_URL=https://github.com/travisrogers05/examples -v SOURCE_REPOSITORY_REF=master -v CONTEXT_DIR=eap-cluster-demo \
| oc create -f -
  ```

5.  View the deployed application.  (Your URL may be different based on your environment)

  ```
http://
  ```
