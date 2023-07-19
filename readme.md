# Book Mingle Backend Application
## About Book Mingle
Book Mingle is a book exchange platform that aims to facilitate book
sharing and exchange among book readers, bringing people together and stimulating book swapping.

## Overview
This application is developed in order to handle incoming requests from Book Mingle Client(s) which is a mobile application for the moment. 

It acts a backbone of the platform, handling various operations and data management related to book listing, user profiles, transactional activities along with handling real time messaging among users. 

### Key Features
- User & Library Management: The application handles requests about user login & sign up, and some requests about user profile and general information for client to use. It also handles request related to books that users made in order to add books to their library they want to exchange.
- Search & Recommendations: The backend application provides search functionality, enabling users to search for books by their title, author, and publisher. It also filters results based on users' locations and returns books within a specified radius. The application also takes recommendations from another application which is a recommendation system runs separately from this application.
- Messaging & Exchange Requests: The application handles exchange requests made by one user to another. When a request is accepted by the requested user, a new chat will be created by the application. Users then can chat in real time related to that request. The application also manages lifecycle of book exchanges. It handles the process of requesting, accepting or denying and confirming exchange requests between users and maintains a transaction history.
- Security: The application uses JWT token for authenticating users and incoming requests.

## Installation
### Database Creation
Before installation and running the program a database needs to be created. The application uses PostgreSQL database, so you can either create an empty PostgreSQL database or can create a database with any other RDMS. However, you need to update the pom.xml and include related RDMS driver, if you choose to not use PostgreSQL. 

After creating your database you need to configure 'application.yml' file's related section which can be found in below code snippet.
```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bookmingle
    username: #your db username
    password: #your password
    driver-class-name: org.postgresql.Driver
```
- **Note!** You can use [this](https://drive.google.com/file/d/11JYNKZ90axwxY5_x2MJNK3LfXCtTRVRY/view?usp=sharing) sql script for adding books to your database

### Running The Application
You can use an IDE to open the application and run it, or you can build the project with maven and run it from command line.