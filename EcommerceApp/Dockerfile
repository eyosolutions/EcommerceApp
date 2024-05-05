FROM maven AS BUILD
WORKDIR /app
COPY . .
RUN mvn clean package

FROM tomcat:jre8-alpine
WORKDIR /usr/local/tomcat/
# COPY target/*.war webapps
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=BUILD /app/target/*.war webapps/ROOT.war
