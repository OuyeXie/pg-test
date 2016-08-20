# Who am I 

# Junit

mvn test

# How to run

## config

add following attributes and correct values in to a file called config.properties under route resources

<pre>
<code>
param.postgresql.host=xxx
param.postgresql.port=xxx
param.postgresql.database=xxx
param.postgresql.user=xxx
param.postgresql.password=xxx
</code>
</pre>

mvn clean package
mvn jetty:run

# How to use

curl http://0.0.0.0:8080/pg-test/ping

# References

 - https://github.com/jersey/jersey/tree/2.23.2/examples