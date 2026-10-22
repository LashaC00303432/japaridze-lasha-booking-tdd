# Study Room Booking (TDD)

Compile:

```
javac -d out -cp junit.jar src/main/java/*.java src/test/java/*.java
```

Run all tests:

```
java -jar junit.jar execute --class-path out --scan-class-path
```

`junit.jar` is not committed. Download it from
https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/6.1.3/junit-platform-console-standalone-6.1.3.jar
and save it in the project folder as `junit.jar`.
