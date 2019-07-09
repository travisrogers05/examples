## Service Mesh

To add this application to a service mesh, be sure to add the following annotation into the applications deploymentconfig:
```
spec:
  template:
    metadata:
      annotations:
        sidecar.istio.io/inject: "true"
```

This can be accomplished using the oc edit command.  For example  (assumes the current project is proper):
```
oc edit -n $(oc project -q) dc eap-app -o yaml 
```

Then add the gateway definition for ingress to the service (assumes the current project is proper):
```
oc apply -n $(oc project -q) -f counter-gateway.yml
```
