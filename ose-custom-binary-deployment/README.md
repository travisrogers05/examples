## Example of creating an Openshift container using s2i based on JBoss EAP that includes a binary (WAR) file, custom configuration xml and a custom module.

Deployment file used from EAP cluster testing demo:
https://access.redhat.com/solutions/219213

Custom JSON logging configuration borrowed from:
https://github.com/knrc/openshift-examples/tree/json-logging-eap6/json-logging-eap6


Steps to build the EAP container with OpenShift using the basic EAP 6.4 template:
oc process -f eap64-basic-s2i.json -v SOURCE_REPOSITORY_URL=https://github.com/travisrogers05/examples.git,SOURCE_REPOSITORY_REF=ose-custom-binary-deployment,CONTEXT_DIR=ose-custom-binary-deployment | oc create -f -
