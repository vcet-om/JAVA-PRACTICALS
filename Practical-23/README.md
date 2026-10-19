# Practical-23 - Hibernate / ORM Program to Write to Database

## Aim
Demonstrates Object-Relational Mapping (ORM) principles using entity annotations (@Entity, @Table, @Id) and an EntityManager to persist object state directly to a relational database.

## Files
- `HibernateOrmSimulation.java`: Source code implementation.

## Compilation and Execution

```bash
# Compile
javac -cp ../lib/h2.jar:. HibernateOrmSimulation.java

# Run
java -cp ../lib/h2.jar:. HibernateOrmSimulation
```

## Output
```text
Hibernate/ORM Persisted Entity -> Product into table 'products'
Hibernate/ORM Persisted Entity -> Product into table 'products'

Reading back saved records from database table 'products':
Product ID: 101, Name: Mechanical Keyboard, Price: Rs.3499.0
Product ID: 102, Name: Wireless Gaming Mouse, Price: Rs.1999.0
```
