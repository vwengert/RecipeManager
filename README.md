# RecipeManager

recipeHeader manager service

[![Build Status](http://bertolux.dynv6.net:8153/buildStatus/icon?job=RecipeManagerService)](http://bertolux.dynv6.net:8153/job/RecipeManagerService/)
[![License](https://img.shields.io/github/license/vwengert/RecipeManager)](https://github.com/vwengert/RecipeManager/blob/main/LICENSE)

# Description

Service to track food and recipes

# Testing

There are Unit and Integration Tests in that repository. The Integration Test and normal Test runs with H2 database.

If you want to run the tests against a mariadb, you just need to replace the application.properties with the
mariadocker.application.properties. You also have to comment in  the 2 "dockerCompose.isRequiredBy(...)" commands 
in the build.gradle. For that you also need to have a running docker daemon.
