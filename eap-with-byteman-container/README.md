#Example of Including Byteman in JBoss EAP Running in Openshift

##Requirement: The ability for a running JBoss EAP container to
1) Be instrumented to execute byteman when deployed
2) Support multiple agent options (multiple byteman scripts)
3) Persistent volume required to provide byteman scripts and jar file


##Possible solution:
1) Modify EAP deploymentconfig to include byteman volume for byteman collateral
2) Provide byteman JVM options via JAVA_OPTS_APPEND env var
3) Add byteman.jar file and byteman scripts to byteman volume
4) Redeploy the EAP pod


###In this example, a JBoss EAP 6.4 pod was created with the following commands:
~~~
oc new-project eap-byteman
oc create serviceaccount eap-service-account
oc policy add-role-to-user view system:serviceaccount:$(oc project -q):eap-service-account
oc policy add-role-to-user view system:serviceaccount:$(oc project -q):default
oc new-app eap64-basic-s2i \
   -p APPLICATION_NAME=eap-byteman-app \
   -p SOURCE_REPOSITORY_URL=https://github.com/travisrogers05/examples.git \
   -p SOURCE_REPOSITORY_REF=master \
   -p CONTEXT_DIR=eap-with-byteman-container
~~~


###To add byteman script(s) to a JBoss EAP pod in Openshift:

####Scale down the target JBoss EAP pod
~~~
oc scale dc/eap-byteman-app --replicas=0
~~~

####Add a volume to the JBoss EAP pod's deploymentconfig that points to a directory containing the byteman files
~~~
oc volume dc/eap-byteman-app --add --name=byteman --type=hostPath --path=/byteman --mount-path=/byteman
~~~

####Add the byteman files to this shared directory
Use whatever utilities that are required to place the byteman related files to a shared directory to be mounted in the JBoss EAP pod container using a volume.

In this simple example, a volume of type hostPath is being used.  This means a directory on the host running the JBoss EAP pod will be mounted in the container.

**NOTE:** Using a volume type of hostPath may require that certain SELinux permissions be in place in order for the JBoss EAP pod container to be able to mount and access the files in the shared directory.  In this example, a `/byteman` directory is shared on the JBoss EAP pod's host machine.  To avoid "permission denied" messages, it is necessary to execute the following command on the pod host to allow the container to access the files in the shared directory.  Do not try to use `/tmp` on the pod host for the shared directory as this will fail.  This step should not be necessary if you intend to use a network share for the volume mount point.
~~~
sudo chcon -R -u system_u -r object_r -t svirt_sandbox_file_t -l s0 /byteman/
~~~

####Add/edit the JAVA_OPTS_APPEND environment variable for the JBoss EAP pod's deploymentconfig to include the needed byteman settings
~~~
oc env dc/eap-byteman-app JAVA_OPTS_APPEND='-javaagent:/byteman/byteman.jar=script:/byteman/examplescript.btm,sys:/byteman/byteman.jar -Djboss.modules.system.pkgs=org.jboss.byteman,org.jboss.logmanager'
~~~

####Scale up the target JBoss EAP pod.  The byteman script(s) should be included as part of the container start up.
~~~
oc scale dc/eap-byteman-app --replicas=1
~~~


###To remove byteman script(s) from a JBoss EAP pod in Openshift:

####Scale down the target JBoss EAP pod
~~~
oc scale dc/eap-byteman-app --replicas=0
~~~

####Remove the byteman volume from the JBoss EAP pod's deploymentconfig
~~~
oc volume  dc/eap-byteman-app --remove --name=byteman
~~~

####Add/edit the JAVA_OPTS_APPEND environment variable for the JBoss EAP pod's deploymentconfig to remove the byteman settings
~~~
oc env dc/eap-byteman-app JAVA_OPTS_APPEND-
~~~

####Scale up the target JBoss EAP pod.  The byteman script(s) should be removed from the container start up.
~~~
oc scale dc/eap-byteman-app --replicas=1
~~~


