# spring-data-dynamodb-issue-118
## Branch: `springboot-1.5.9`

This repo contains a sample application that demonstrates [derjust/spring-data-dynamodb issues #118](https://github.com/derjust/spring-data-dynamodb/issues/118) working with SpringBoot 1.5.9 and `com.github.derjust:spring-data-dynamodb:4.5.0`.

## Running with Gradle

```
export AWS_ACCESSKEY={your-accesskey}
export AWS_SECRETKEY={your-secretkey}
cd {repo-root}
./gradlew clean bootRun
```

## Running Directly

1. Provide your `aws.accesskey` and `aws.secretkey` directly in the `/src/main/resources/application.yml` or any other way SpringBoot supports.
1. Run the `example.ExampleApplication` class.

## What the Demo App Does

1. Creates a table called `CustomerDocuments` if it doesn't already exist.
1. Creates a single row, where the hashkey is `1|terms` and rangekey is `0.0.1`.
1. Executes a query based on the hashkey `1|terms` and writes all rows to the log.