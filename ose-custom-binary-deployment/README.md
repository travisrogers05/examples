## Example of creating an Openshift container using s2i based on JBoss EAP that includes a binary (WAR) file, custom configuration xml and a custom module.

Deployment file used from EAP cluster testing demo:
https://access.redhat.com/solutions/219213

#### Steps to build the EAP container with OpenShift using the basic EAP 6.4 template:

```
oc new-app jboss-eap64-openshift \
--template=eap64-basic-s2i \
--param=SOURCE_REPOSITORY_URL=https://github.com/travisrogers05/examples.git \
--param=SOURCE_REPOSITORY_REF=master \
--param=CONTEXT_DIR=ose-custom-binary-deployment \
--param=APPLICATION_NAME=binary-eap-app

oc env dc/binary-eap-app -e OPENSHIFT_KUBE_PING_NAMESPACE=eap-binary OPENSHIFT_KUBE_PING_LABELS=application=binary-eap-app
```



#### Issue these commands from CLI to ensure you are not the system:admin, unless you mean to be.  Also that you are not operating on the default project.
```
oc login -u <some_user>
oc whoami
oc project
```

#### Create a project containing an EAP based application.  Use the web console or the supplied CLI commands for 1-3.  Use the CLI for 4.

1.  Create a new project (replace **project-name** with a name you choose)

  ```
  oc new-project <project-name>
  ```

2.  Add an application to the project.  Use the EAP 6.4 template image.  Provide the following fields for this example app:

  ```
  SOURCE_REPOSITORY_URL = https://github.com/travisrogers05/examples.git
  SOURCE_REPOSITORY_REF = master
  CONTEXT_DIR = ose-custom-binary-deployment
  ```

3.  Click on the "Create" button or use this CL command (replace **app-name** with a name you choose):

  ```
  oc process eap64-basic-s2i -n openshift \
  -v APPLICATION_NAME=<app-name>,SOURCE_REPOSITORY_URL=https://github.com/travisrogers05/examples.git,SOURCE_REPOSITORY_REF=master,CONTEXT_DIR=ose-custom-binary-deployment \
  | oc create -f -
  ```

4.  Add the following service account and roles. (replace **project-name** and **app-name** with a names you choose)

  ```
  oc project <project-name>
  oc create serviceaccount eap-service-account -n $(oc project -q)
  oc policy add-role-to-user view system:serviceaccount:$(oc project -q):default -n $(oc project -q)
  oc policy add-role-to-user view system:serviceaccount:$(oc project -q):eap-service-account -n $(oc project -q)
  oc env dc/<app-name> -e OPENSHIFT_KUBE_PING_NAMESPACE=<project-name> OPENSHIFT_KUBE_PING_LABELS=application=<app-name>
  ```
