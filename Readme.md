https://github.com/SAP/cloud-security-xsuaa-integration/blob/main/samples/spring-security-hybrid-usage/src/main/java/sample/spring/security/SecurityConfiguration.java
https://github.com/daniel-mader/blog-post-spring-multi-jwt/blob/master/src/main/java/com/example/demo/security/OAuthJwtAuthenticationConverter.java
https://www.novatec-gmbh.de/en/blog/how-to-support-different-jwts-in-your-spring-boot-application/

https://www.tutorialspoint.com/spring_boot/spring_boot_oauth2_with_jwt.htm

https://www.javadevjournal.com/spring-security/spring-security-custom-authentication-provider

https://bytemeta.vip/repo/SAP/cloud-security-xsuaa-integration/issues/649

https://danielblancocuadrado.medium.com/authentication-with-spring-boot-and-jwt-2cb43ed0b6ef

Debug:
 
 
 Enable Debug Port:
 
 JAVA (Cloud Platform SAP BTP environemnt with sap java buildpack !!!)
 
cf ssh <app_name> -c 'export JAVA_PID=`ps -C java -o pid=` && app/META-INF/.sap_java_buildpack/sapjvm/bin/jvmmon -pid $JAVA_PID -c "set debugging port range fromPort=8000 toPort=8000" && app/META-INF/.sap_java_buildpack/sapjvm/bin/jvmmon -pid $JAVA_PID -c "start debugging local=true"'

cf ssh <app_name> -c "export JAVA_PID=`ps -C java -o pid=` && app/META-INF/.sap_java_buildpack/sapjvm/bin/jvmmon -pid $JAVA_PID -c 'set debugging port range fromPort=8000 toPort=8000' && app/META-INF/.sap_java_buildpack/sapjvm/bin/jvmmon -pid $JAVA_PID -c 'start debugging local=true'"

cf ssh <app_name> -i 1 -c 'export JAVA_PID=`ps -C java -o pid=` && app/META-INF/.sap_java_buildpack/sapjvm/bin/jvmmon -pid $JAVA_PID -c "set debugging port range fromPort=8000 toPort=8000" && app/META-INF/.sap_java_buildpack/sapjvm/bin/jvmmon -pid $JAVA_PID -c "start debugging local=true"'

https://docs.cloudfoundry.org/buildpacks/java/java-tips.html#debugging

NODE:

Create SSH Tunnel:


cf ssh <app_name> -N -T -L 8000:localhost:8000
cf ssh -N -T -L <LOCAL_PORT>:localhost:<REMOTE_PORT> <APPLICATION_NAME>
REMOTE_PORT should match the port configuration for the application 


Connect the Debugger
host (localhost)
port (e.g. 8000) in the Connection Properties

conditional breakpoints:
create a condition like:

(SAP BTP Platform)
if (com.sap.xs2.security.container.SecurityContext.getUserInfo().getEmail().equals("hugo.nase@obiwan.com")) {
    return true;
}

 

Disable Debug Port:
 
JAVA:

cf ssh <app_name> -c 'export JAVA_PID=`ps -C java -o pid=` && app/META-INF/.sap_java_buildpack/sapjvm/bin/jvmmon -pid $JAVA_PID -c "stop debugging"'

cf ssh <app_name> -c "export JAVA_PID=`ps -C java -o pid=` && app/META-INF/.sap_java_buildpack/sapjvm/bin/jvmmon -pid $JAVA_PID -c 'stop debugging'"

https://docs.cloudfoundry.org/buildpacks/java/java-tips.html#debugging


NODE:
