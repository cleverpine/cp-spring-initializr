# CP-Spring-Initializr v0.0.6

## Introduction
CP-Spring-Initializr is a versatile tool for creating ready-to-use Spring Boot projects. Tailor your new project by specifying its name, required dependencies, and whether to include API support, all through simple command-line arguments.

## Features
- Automated Spring Boot project setup
- Customizable project configurations through arguments:
    - `name`
    - `dependencies`
    - `includeApi`

## Prerequisites
- Java JDK 17
- Spring Boot 3.2.0

## Installation and Setup
1. Clone the repository: `git clone git@github.com:cleverpine/cp-spring-initializr.git`
2. Navigate to the project directory: `cd cp-spring-initializr`
3. Build the application: `mvn clean install`

## Understanding the Arguments
- `name`: Defines the name of your new Spring Boot project. This name will be used as the base directory name and in the project metadata.
- `dependencies`: A comma-separated list of Maven dependency IDs to be included in your project's `pom.xml`.
- `includeApi`: A boolean flag (`true` or `false`). When set to `true`, it adds the `swagger-codegen-maven-plugin` to your project.
- `verbose`: A boolean flag (`true` or `false`). When set to `true`, it prints out stack traces and other debugging information.

## Usage
Run the application, passing desired arguments: `java -jar <application-jar-file> --name=<project_name> --dependencies=<dependency1,dependency2> --includeApi=<true/false>`