# GemFire Functions Example

A GemFire function is a body of code that resides on a server and that an application can invoke from a client or from another server without the need to send the function code itself. The caller can direct a data-dependent function to operate on a particular dataset, or can direct a data-independent function to operate on a particular server, member, or member group.


You can deploy function thru a server GemFire cache XML or spring context.

You can also use the deploy jar gfsh command to auto register functions.


	deploy --jar=/Projects/solutions/gedi/dev/playground/spring-geode-showcase/demo-functions/target/demo-functions-0.0.1-SNAPSHOT.jar

See function overview [https://gemfire.docs.pivotal.io/geode/developing/function_exec/chapter_overview.html](https://gemfire.docs.pivotal.io/geode/developing/function_exec/chapter_overview.html)


##  Example Function

See example code DownloadExampleFunction

	io.pivotal.gemfire.demo.functions.DownloadExampleFunction

This  demonstrates using a region aware function to download region data in JSON
format to a local file.


## Queries

The only way to perform a join of a participated region is by using a Function.

See example code: ExampleQueryFunction

	io.pivotal.gemfire.demo.functions.ExampleQueryFunction






