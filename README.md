# Receipt Processor Challenge

## Overview
An application for storing receipts, their calculated point values, and retrieving receipt points.

## How to Run

### Gradle
If you have gradle installed on your machine, you should be able to run this application using `gradle bootRun`.
You must have Java 17 or higher installed.
### Docker
I have created a [dockerfile](Dockerfile) for your convenience.

To build the docker image, run `docker build -t receipt-processor-challenge .`
To run the docker image, run `docker run -p 8080:8080 receipt-processor-challenge`
The app should now be running in a local docker container.