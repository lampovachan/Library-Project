# Library-Project

Library API with role-based access using JWT. 

Admin can add book title, author, genre, description, load its picture and book file. 

User can see all books available, find nesseccary book by id, use filtering and sorting, search for books by its title, description and author, download book file and picture.

The books entities are kept in MongoDB, book files and pictures are kept in AWS S3 LocalStack.

To test an API, access:
```
http://localhost:8171/swagger-ui/
```
