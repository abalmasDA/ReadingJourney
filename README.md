# Project: ReadingJourney

The ReadingJourney API is a interface designed to empower the management of users, books, authors.
Crafted with the intent to enrich user experience, this API facilitates onboarding, allows for
meticulous cataloging of literary works, and manages intricate associations between books, their
authors, and readers

![logo ReadingJourney](src/main/resources/media/ReadingJourney_Logo.png)


## Tech Stack

**Client:** Postman

**Server:** Java 21, Spring (Boot, Web, Data Jpa, Security, Test, Validation), Hibernate, OpenApi,
Actuator, Mapstruct, Liquibase, Lombok, Assertj, JWT

**Database:** Postgresql, H2

## Getting Started Locally

## Prerequisites:

- Docker with Buildkit enabled:
    - **Windows or macOS**: install Docker Desktop
    - **Linux**: install Docker Engine and Docker Compose. Follow the post-installation guide to add
      your user to the docker group
- Clone the repository from GitHub

## Instructions:

1. Start Docker engine (Linux) or Docker Desktop (macOS or Windows).
2. If you're using an Apple silicon chip (e.g., M1), you'll need to uncomment this line.
3. In the root of the project, run `docker-compose build` to build the database and backend
   services.
4. Run `docker-compose up` to start the containers.
5. Once the development server has started (you'll get notified in the output
   of `docker-compose up`), utilize `localhost:8000` the client-side uses POSTMAN for interaction
   with the server.
6. When finished, run `docker-compose down` to stop and remove the containers.

## Log in with Our Test User

When running the backend, you can use the following test user:

- **Email address:** user@user.user
- **Password:** passwordUser

Note: If you're running the backend, you will need a JWT token for subsequent requests after logging
in or creating an account.

## Access Database (optional)

Using your SQL client, use the following settings:

- **Host:** localhost
- **Port:** 5433
- **User:** postgres
- **Password:** root
- **Database name:** postgres

## Feedback

If you have any feedback, please reach out to me at abalmasjava@gmail.com

## Authors

- [@abalmasDA](https://github.com/abalmasDA)