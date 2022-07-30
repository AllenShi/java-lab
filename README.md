# Java -lab

Java & Java framework PoC

## Open multiple projects in Intellij

Most IDEs provide workspaces that contain multiple projects and thus enable you to work on multiple projects in one
instance of the IDE. IntelliJ, which has become the de-facto standard for Java Devs, does not support workspaces. So, how
is it possible to open multiple projects in one IntelliJ instance?

Well, it is quite simple. In context of IntelliJ you can replace the idea of workspaces with projects and projects with
modules. An IntelliJ project can contain multiple modules.

Let’s put this to practice. I assume you have checked out multiple project, lets say at /local/projects/. Create a new 
empty project called root-project. at /local/projects/root-project in IntelliJ. Next add a new module from an existing source:

File > New > Module from Existing Sources… Select the project e.g. /local/projects/projectX If available select the
build.gradle or another file that indicates the project model. Finish the wizard Now the module/project should be loaded
in the top level of the root project folder. Depending on the project model IntelliJ should detect features and
configure it accordingly.

## Spring Boot Actuator

java -jar -Dserver.port=9999 <example spring boot web app jar> 
curl http://localhost:9999/actuator/info  
curl http://localhost:9999/actuator/health
curl http://localhost:9999/actuator/health/readiness
curl http://localhost:9999/actuator/health/liveness
curl http://localhost:9999/actuator/metrics | jq
curl http://localhost:9999/actuator/threaddump
