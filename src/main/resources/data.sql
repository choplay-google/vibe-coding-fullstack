-- Clean up existing data to avoid duplicates on restart if necessary
-- DELETE FROM posts; 

INSERT INTO posts (title, content, views) VALUES ('Welcome to Vibe Coding!', 'This is your first post. Enjoy coding with a vibe!', 10);
INSERT INTO posts (title, content, views) VALUES ('MyBatis + H2 Integration', 'Successfully integrated H2 database and MyBatis SQL mapper in Spring Boot 4.0.1.', 25);
INSERT INTO posts (title, content, views) VALUES ('DTO Pattern Best Practices', 'Using Records for DTOs makes your code clean and immutable.', 5);
INSERT INTO posts (title, content, views) VALUES ('Jakarta EE with Spring Boot 4', 'Note that Spring Boot 4 uses Jakarta EE namespaces (jakarta.*) instead of Javax.', 12);
INSERT INTO posts (title, content, views) VALUES ('Spring Boot 4.0.1 Features', 'Exploring the exciting new features of Spring Boot 4.0.1 and Spring 7.', 8);
