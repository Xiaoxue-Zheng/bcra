# BCRA - Breast Cancer Risk Assesment in Young Women

## Database

### Configure Postgres for Local Development
Install postgres on your machine, check it is running, and create the database and user with the following commands:
```
psql -U postgres
create database bcra;
create user bcra with encrypted password 'bcra123';
grant all privileges on database bcra to bcra;
```

### Clearing the Database
```
psql -U postgres
drop database bcra;
create database bcra;
grant all privileges on database bcra to bcra;
\q
```

### Recreate Database
 * Clear the database (see above)
 * Copy .yo-rc.json and database-model.jdl to a new folder
 * `jhipster`
 * `jhipster import-jdl database-model.jdl`
 * copy to project: `/src/main/resources/config/liquibase/`

### Create Liquibase Changeset for Hibernate Changeset
 * Clear database
 * `mvnw` (creates liquibase DB)
 * Make hibernate changes
 * `mvnw compile`
 * `mvnw liquibase:diff`
 
 
## Application

The **application** project contains the back-end server, and the interface for all users except participants. It is generated by JHipster.

### Launch Local Build
Install packages: `npm install`

Build & run back-end: `mvnw`

Launch front-end: `npm start`

### Run Back-End Tests
`mvnw verify`

#### Run a Single Back-End Tests
`mvnw surefire:test -Dtest=ClassNameOfTest`

#### Run all Back-End Tests in a Package
`mvnw surefire:test -Dtest="uk.ac.herc.bcra.algorithm.**"`

### Run Front-End Tests
`npm test`

### Build for Production
`mvnw -Pprod,war clean verify`

### Add New Entity to Model
`jhipster entity Question`

## Questionnaire

The **questionniare** project contains the front-end for participants. It is generated by vue-cli.

### Launch Local Build
Install packages: `npm install`

Launch front-end: `npm run serve`

### Build for Production

Build front-end: `npm run build`

<del>This will place a **.war** file in the **dist** folder.</del>

(The warfile plugin is temporarily deactivated, because it conflicts with Cypress end-to-end tests.

### Run Tests

#### Unit Tests
`npm run test:unit`

#### End-to-End Tests
0. Clear the database (see above) if there have been any model changes.
1. Launch back-end with `mvnw`(see above)
2. Open the Admin Interface (localhost:8080) and import **TestParticipantCsv.csv**
3. `npm run test:e2e`

To run end-to-end tests in a headless environment, use:

`npm run test:headless`




