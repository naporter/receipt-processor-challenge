# Receipt Processor Challenge

## Overview
An application for storing receipts, their calculated point values, and retrieving receipt points.

## How to Run

### Gradle
If you have gradle installed on your machine, you should be able to run this application using `gradle bootRun`.
You must have Java 17 or higher installed.
### Docker
I have created a [dockerfile](Dockerfile) for your convenience.
This application requires that you have gradle installed locally. Instructions for installing gradle can be found [here](https://gradle.org/install/).
Gradle 8.10.2 is what I used to build locally. Once gradle is installed you should be able to build the application by running
`gradle build`. You will now have a jar file located in `/build/libs`.

To build the docker image, run `docker build -t receipt-processor-challenge .`
To run the docker image, run `docker run -p 8080:8080 receipt-processor-challenge`
The app should now be running in a local docker container.