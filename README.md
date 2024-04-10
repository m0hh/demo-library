# Demo Library Project

## Installation
To install your Java application along with setting up the required PostgreSQL database and configuration files, follow the steps below:

1. Clone Repository
   Clone the repository containing your Java application.


2. Install Dependencies
   Make sure you have Java and Maven installed. If not, install them.

3. Create PostgreSQL Database
   Create a PostgreSQL database named "demo".


4. Configure Database
   Create a file named db.yaml in the same directory as app.yaml with the following content:

```yaml
app:
    secret:
        key: example secret key for jwt
    user:
        key: example key for signing up
spring:
  datasource:
    password: example postgres password
    username: postgresql user
```

5. Run Tests 

```bash
mvn test
```

6. Build and Run the Application

```bash
mvn clean install
mvn spring-boot:run
```
6. Access Your Application
   Access your application through the specified URLs.


## Important points

1. I didn't make my Ids long because a library won't likely exceed the number for an Integer , so I opted to using Integer for memory saving
2. Most of the validation for non null is made only on application side and not the db side, because for a minimal project like that we don't need two layers of validation
3. I made the users table extensive and made roles for each user because maybe the library would allow patrons to be users one day and do the whole cycle of borrowing from an app. so the way users are handled now will make it very easy to just give the patron users a role of patron and decrease refactoring
4. I used contain in my search because the number of rows is not huge so it should not be too much of a hit on performance
5. I only unit tested my service layer for the sake of simplicity but in a real application, I would've unit tested all my components and made integration and end to end tests
## User

### Sign Up

To create a new user send a POST request to /user/signup_admin the key is the key saved in db.yaml and must be entered when signing up because this is a closed 
system and can't have any one not working at the library to sign up

body
```json
{
    "username": "test_user",
    "password": "test4321",
    "email": "example@gmail.com",
    "phone": "01000000000",
    "name": "test",
    "key":"12345"
}
```

response:
User with id 'user ID' saved succssfully!

if any of the fileds are missing you will receive a list of all the required fields missing

```json
{
    "message": "Must Enter a phone, Must Enter a password, Must Enter a key, Must Enter an email, Must Enter a name, Must Enter a username",
    "httpStatus": "BAD_REQUEST",
    "timestamp": "2024-04-10T16:56:12.804504189Z"
}
```

if the email is invalid  or phone is not digits

```json
{
    "message": "must enter a valid email",
    "httpStatus": "BAD_REQUEST",
    "timestamp": "2024-04-10T17:07:19.23806905Z"
}
```

```json
{
    "message": "must enter only digits",
    "httpStatus": "BAD_REQUEST",
    "timestamp": "2024-04-10T17:07:48.489072141Z"
}
```

if username exists
```json
{
    "message": "username already exists",
    "httpStatus": "BAD_REQUEST",
    "timestamp": "2024-04-10T16:56:58.645988859Z"
}
```

### Login

To login send a POST request to /user/login_user

body
```json
{
    "username": "test",
    "password": "test4321"
}
```

response

```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI4ODAyIiwic3ViIjoidGVzdCIsImlzcyI6IkFCQ19MdGQiLCJhdWQiOiJYWVpfTHRkIiwiaWF0IjoxNzEyNzY5MDA0LCJleHAiOjE3MTI3NzI2MDR9.69AUXMWAQU4SsTKAMOlw4G9DNlgEMqL_dwaW8M4S7OgUo9qZbOcmjbkBLKrX9ORIvM-fynTNpfGM6VmwASkzdQ",
    "message": "Token generated successfully!"
}
```
any invalid credentials

```json
{
    "message": "Bad Credentials",
    "httpStatus": "UNAUTHORIZED",
    "timestamp": "2024-04-10T17:14:21.959195485Z"
}
```

## Book

### Add Book

to add a book send a POST request to this endpoint /api/books

body:
```json
{
    "title":"Test",
    "author": "Test",
    "publicationYear": "1996-04-03",
    "isbn":"22453466"
}
```

response

```json
{
    "id": 4,
    "title": "Test",
    "author": "Test",
    "publicationYear": "1996-04-03",
    "isbn": "22453466"
}
```

non null fields are not entered

```json
{
    "message": "must enter a publication year, Must enter a title, Must enter an author, must enter an ISBN",
    "httpStatus": "BAD_REQUEST",
    "timestamp": "2024-04-10T17:20:15.185452507Z"
}
```

failed to deserialize a field

```json
{
    "message": "JSON parse error: Cannot deserialize value of type `java.time.LocalDate` from String \"scd1996-04-03\": Failed to deserialize java.time.LocalDate: (java.time.format.DateTimeParseException) Text 'scd1996-04-03' could not be parsed at index 0",
    "httpStatus": "UNPROCESSABLE_ENTITY",
    "timestamp": "2024-04-10T17:21:01.252407896Z"
}
```


### List Books

to list books send a GET request to /api/books?page=(page number)
```json
{
    "content": [
        {
            "id": 2,
            "title": "Test",
            "author": "Test",
            "publicationYear": "1996-04-03",
            "isbn": "edfw"
        },
        {
            "id": 3,
            "title": "Test",
            "author": "Test",
            "publicationYear": "1996-04-03",
            "isbn": "edfw"
        },
        {
            "id": 4,
            "title": "Test",
            "author": "Test",
            "publicationYear": "1996-04-03",
            "isbn": "22453466"
        },
        {
            "id": 5,
            "title": "1233",
            "author": "Test",
            "publicationYear": "1996-04-03",
            "isbn": "22453466"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "sorted": false,
            "empty": true,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 4,
    "last": true,
    "size": 10,
    "number": 0,
    "sort": {
        "sorted": false,
        "empty": true,
        "unsorted": true
    },
    "numberOfElements": 4,
    "first": true,
    "empty": false
}
```

if sent without page number

```json
{
    "message": "Specify the page",
    "httpStatus": "BAD_REQUEST",
    "timestamp": "2024-04-10T17:53:26.973737811Z"
}
```

there is also an optional query param title to search books by title

### Get Book

GET /api/books/{bookId}
response
```json
{
    "id": 2,
    "title": "Test",
    "author": "Test",
    "publicationYear": "1996-04-03",
    "isbn": "edfw"
}
```

if not found

```json
{
    "message": "The requested resource could not be found",
    "httpStatus": "NOT_FOUND",
    "timestamp": "2024-04-10T17:55:28.334985076Z"
}
```

### Update a book

PUT /api/books/{bookId}

you can update only the fields you want. It's more appropriate to put PATCH here, but I am following the assignment guidelines

body 

```json
{
    "title": "Updated title"
}
```

response

```json
{
    "id": 2,
    "title": "Updated title",
    "author": "Test",
    "publicationYear": "1996-04-03",
    "isbn": "edfw"
}
```

not found

```json
{
    "message": "The requested resource could not be found",
    "httpStatus": "NOT_FOUND",
    "timestamp": "2024-04-10T17:59:08.867571317Z"
}
```

### Delete a Book 

DELETE /api/books/{bookID}

response : 204 No content


## Patron

### Add Patron

to add a book send a POST request to this endpoint /api/patrons

body:
```json
{
   "name":"Test",
   "email": "example@gmail.com",
   "phone": "034254"
}
```

response

```json
{
   "id": 3,
   "name": "Test",
   "email": "example@gmail.com",
   "phone": "034254",
   "borrows": []
}
```

non null fields are not entered

```json
{
   "message": "You must enter a name, You must enter a phone, You must enter an email",
   "httpStatus": "BAD_REQUEST",
   "timestamp": "2024-04-10T18:06:09.922117184Z"
}
```

email is invalid or non-numeric chars in phone

```json
{
   "message": "Must enter a valid email",
   "httpStatus": "BAD_REQUEST",
   "timestamp": "2024-04-10T18:07:17.01827517Z"
}
```

```json
{
    "message": "must enter only digits",
    "httpStatus": "BAD_REQUEST",
    "timestamp": "2024-04-10T18:07:32.336545889Z"
}
```


### List Patrons

to list books send a GET request to /api/patrons?page=(page number)
here we use a dto to avoid N+1 which will arise when listing borrows in every patron
```json
[
   {
      "id": 16,
      "name": "Test",
      "email": "Test",
      "phone": "1996-04-03"
   },
   {
      "id": 3,
      "name": "Test",
      "email": "example@gmail.com",
      "phone": "034254"
   },
   {
      "id": 2,
      "name": "new name",
      "email": "example@gmail.com",
      "phone": "034254"
   }
]
```

if sent without page number

```json
{
    "message": "Specify the page",
    "httpStatus": "BAD_REQUEST",
    "timestamp": "2024-04-10T17:53:26.973737811Z"
}
```

there is also an optional query param name to search patrons by name

### Get Patron

GET /api/patrons/{PatronId}
response
```json
{
   "id": 16,
   "name": "Test",
   "email": "Test",
   "phone": "1996-04-03",
   "borrows": [
      {
         "id": 2,
         "book": {
            "id": 3,
            "title": "Test",
            "author": "Test",
            "publicationYear": "1996-04-03",
            "isbn": "edfw"
         },
         "borrowedAt": "2024-04-09T22:16:11.965189",
         "returnedAt": "2024-04-09T22:54:07.190813"
      },
      {
         "id": 3,
         "book": {
            "id": 3,
            "title": "Test",
            "author": "Test",
            "publicationYear": "1996-04-03",
            "isbn": "edfw"
         },
         "borrowedAt": "2024-04-10T02:21:45.101805",
         "returnedAt": "2024-04-10T02:21:59.972575"
      },
      {
         "id": 4,
         "book": {
            "id": 3,
            "title": "Test",
            "author": "Test",
            "publicationYear": "1996-04-03",
            "isbn": "edfw"
         },
         "borrowedAt": "2024-04-10T02:22:06.189644",
         "returnedAt": null
      }
   ]
}
```

if not found

```json
{
    "message": "The requested resource could not be found",
    "httpStatus": "NOT_FOUND",
    "timestamp": "2024-04-10T17:55:28.334985076Z"
}
```

### Update a patron

PUT /api/patrons/{patronId}

you can update only the fields you want. It's more appropriate to put PATCH here, but I am following the assignment guidelines

body

```json
{
    "name": "new name"
}
```

response

```json
{
   "id": 2,
   "name": "new name",
   "email": "example@gmail.com",
   "phone": "034254",
   "borrows": []
}
```

not found

```json
{
    "message": "The requested resource could not be found",
    "httpStatus": "NOT_FOUND",
    "timestamp": "2024-04-10T17:59:08.867571317Z"
}
```

### Delete a Patron

DELETE /api/patrons/{patronId}

response : 204 No content


## Borrows

### Borrow a Book

POST to /api/borrow/{bookId}/patron/{patronId}

with empty body

response
```json
{
    "id": 5,
    "book": {
        "id": 3,
        "title": "Test",
        "author": "Test",
        "publicationYear": "1996-04-03",
        "isbn": "edfw"
    },
    "borrowedAt": "2024-04-10T20:47:15.868704939",
    "returnedAt": null
}
```

notice that patron info is not present here because it's JsonBackReference to avoid circular json referencing and the patron info is already present to the party making the
request, since they just sent his id

if the patron already borrowed the same book and the return is null

```json
{
    "message": "The patron has already borrowed this book and needs to return it first",
    "httpStatus": "BAD_REQUEST",
    "timestamp": "2024-04-10T18:46:30.995597832Z"
}
```

not found book or patron
```json
{
    "message": "The requested resource could not be found",
    "httpStatus": "NOT_FOUND",
    "timestamp": "2024-04-10T18:50:00.041686376Z"
}
```

### Return a Book 

PUT request /api/return/{bookId}/patron/{PatronId}

no body

response
```json
{
    "id": 5,
    "book": {
        "id": 3,
        "title": "Test",
        "author": "Test",
        "publicationYear": "1996-04-03",
        "isbn": "edfw"
    },
    "borrowedAt": "2024-04-10T20:47:15.868705",
    "returnedAt": "2024-04-10T20:55:34.469213722"
}
```

if there is no book or patron or borrow 

```json
{
    "message": "The requested resource could not be found",
    "httpStatus": "NOT_FOUND",
    "timestamp": "2024-04-10T18:54:35.036772042Z"
}
```
