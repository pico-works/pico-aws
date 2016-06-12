# pico-aws
[![CircleCI](https://circleci.com/gh/pico-works/pico-aws/tree/develop.svg?style=svg)](https://circleci.com/gh/pico-works/pico-aws/tree/develop)

Shim libraries for aws.

## Getting started

Add this to your SBT project:

```
resolvers += "dl-john-ky-releases" at "http://dl.john-ky.io/maven/releases"

libraryDependencies += "org.pico" %%  "pico-aws-dynamodb" % "0.0.1-2"
libraryDependencies += "org.pico" %%  "pico-aws-sqs"      % "0.0.1-2"
```
