# Practical-21 - JDBC Connection and CRUD Operations Using Statement and PreparedStatement

## Aim
Establishes a JDBC database connection, creates a table, performs parameterized CRUD operations (Create, Read, Update, Delete) using Statement and PreparedStatement.

## Files
- `JdbcCrudDemo.java`: Source code implementation.

## Compilation and Execution

```bash
# Compile
javac -cp ../lib/h2.jar:. JdbcCrudDemo.java

# Run
java -cp ../lib/h2.jar:. JdbcCrudDemo
```

## Output
```text
Database connected successfully.
Table 'students' created.
Records inserted.
Reading Records:
ID: 1, Name: Rahul, Marks: 88.5
ID: 2, Name: Priya, Marks: 92.0
Updated Rahul's marks to 95.0
Deleted record with ID 2.
Final Records in DB:
ID: 1, Name: Rahul, Marks: 95.0
```
