# Use the official Jenkins LTS image as the base
FROM jenkins/jenkins:lts

# Switch to root user to install packages
USER root

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# (Optional) Verify Maven installation
RUN mvn -version

# Switch back to the Jenkins user
USER jenkins
