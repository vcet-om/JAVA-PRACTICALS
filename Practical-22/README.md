# Practical-22 - JDBC Transaction Management and Stored Procedure Execution Using CallableStatement

## Aim
Demonstrates JDBC transaction management (commit, rollback, setAutoCommit) and invokes database procedures using CallableStatement.

## Files
- `JdbcTransactionDemo.java`: Source code implementation.

## Compilation and Execution

```bash
# Compile
javac -cp ../lib/h2.jar:. JdbcTransactionDemo.java

# Run
java -cp ../lib/h2.jar:. JdbcTransactionDemo
```

## Output
```text
Executing Transaction: Transferring 300 from Alice (101) to Bob (102)
Transaction committed successfully.
Procedure Result - Alice New Balance: 700.0
Procedure Result - Bob New Balance: 800.0
```
