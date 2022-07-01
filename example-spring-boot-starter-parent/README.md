# Example spring boot starter

To create our own custom starter, we require following components

- The auto-configure module with auto configuration class.
- The stater module which will bring all required dependencies using pom.xml

The example-spring-boot-autoconfigure will have the following classes and configurations
- MailProperties file for default name.
- HelloService interface and HelloServiceImpl class.
- HelloServiceAutoConfiguration to create HelloService Bean.

The example-spring-boot-starter will have only one pom
- The pom.xml file for bringing required dependencies to our custom starter. 