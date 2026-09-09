# SQL Challenges

This directory will include all the files inside the sqlChallenges directory as well as how to run them.

## Chinook_postgresqlSQL.sql

This is the provided sql file to start up and populate the chinook database and is not a challenge. However, it is necessary for some of the challenges.

To execute, a connection needs to be made. This is done through WSL and docker. Ensure that both are installed and then inside of WSL execute:

```wsl
docker run -d --name chinook-postgres -p 5432:5432 -e POSTGRES_USER=chinook -e POSTGRES_PASSWORD=password -e POSTGRES_DB=chinook postgres:17
```

Make the connection through VSCode where the Sever Name is 'localhost' the username is 'chinook' the password is 'password' the database is named 'chinook' and the connection name is 'chinook'. Once the connection is made this file can be run.

## SQL_postgresql.sql

Ensure there is a connection to the chinook database and then the file can be run directly or one line at a time to test each connection. Each command should have a comment that explains what problem it is solving. These correspond to slide 57 on the SQL slides provided by Revature.
