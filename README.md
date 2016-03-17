# JIRA-Gitlab-integration [![Build Status](https://travis-ci.org/Augustyn/JIRA-Gitlab-integration.svg?branch=master)](https://travis-ci.org/Augustyn/JIRA-Gitlab-integration)
Atlassian JIRA to Gitlab integration

# Building
## Prerequisites:
Packages:

    <groupId>jta</groupId>
    <artifactId>jta</artifactId>
and:

    <groupId>jndi</groupId>
    <artifactId>jndi</artifactId>

You can get them with [Atlassian SDK, here](https://developer.atlassian.com/docs/getting-started/downloads)
## building with pure maven:
If prerequisites are met, standard build command will suffice:
`mvn clean package`
## building with Atlassian SDK:
Does not require prerequisites, as Atlassian SDK bundles them. Build with:
`atlas-mvn clean package`

# Installing:
As any other plugin, from JIRA Universal Plugin Manager.
