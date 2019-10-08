# APICensus

The main functionality of this application is search persons via rest camel endpoints.

## Contents
*  [Prerequisites](#prerequisites)
*  [First steps](#first-steps)
*  [After run](#after-run)
    *  [Ask for allowed countries](#ask-for-the-allowed-countries)
    *  [Search people data](#search-people-data)
    *  [Add people into the database](#add-people-into-the-database)
  

## Prerequisites
*  Java 8
    1.  Download the file from [Oracle site](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
    2.  Install the downloaded file.
    3.  Set up `JAVA_HOME` environment variable:
        1. Control Panel > System > Advanced system settings > Environment variables
        2. Go to System variables section and make sure the JAVA_HOME variable is found.
        3. If it wasn't found, create it.
        4. New > Variable Name: JAVA_HOME > Browse Directory... > Search your java installation folder
        5. Click OK
        6. Edit the `Path` variable and add the next line `%JAVA_HOME%\bin`
        7. Done
    4.  Check your java version with `javac --version` 

<br>

*  Apache Maven Project
    1.  Is mandatory have Java already installed
    2.  Download the file from [Apache Maven Project site](https://maven.apache.org/download.cgi)
    3.  Extract the downloaded .zip file
    4.  Set up `M2_HOME` environment variable:
        1. Control Panel > System > Advanced system settings > Environment variables
        2. Go to System variables section and make sure the M2_HOME variable is found.
        3. If it wasn't found, create it.
        4. New > Variable Name: M2_HOME > Browse Directory... > Search your maven installation folder
        5. Click OK
        6. Edit the `Path` variable and add the next line `%M2_HOME%\bin`
        7. Done
    5.  Check your maven version with `mvn --version` 

<br>

*  Git
    1.  Install Git from [Git-scm site](https://git-scm.com/downloads)
    2.  Check your git version `git --version`
    3.  Then configure your git credentials with
        ```
            git config --global user.name "Your name"
            git config --global user.email "email@email"
        ```
    4.  Clone this repository using the next command `git clone https://gitlab.com/gian.franco.stabile/apicensus.git`

<br>

*  MongoDB
    1.  Install MongoDB from [MongoDB Download Center](https://www.mongodb.com/download-center/community?jmp=docs)
    2.  Run the installer
    3.  Set up `MONGODB_HOME` environment variable:
        1. Control Panel > System > Advanced system settings > Environment variables
        2. Go to System variables section and make sure the MONGODB_HOME variable is found.
        3. If it wasn't found, create it.
        4. New > Variable Name: MONGODB_HOME > Browse Directory... > Search your maven installation folder
        5. Click OK
        6. Edit the `Path` variable and add the next line `%MONGODB_HOME%\bin`
        7. Done
    5.  Check your mongodb version with `mongo --version` 

<br>

*  Integrated Development Environment (IDE)
    *  [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
    *  [Eclipse](https://www.eclipse.org/downloads/)
    *  [Apache NetBeans](https://netbeans.apache.org/download/index.html)
    *  ...

<br>
    
*  [Lombok](https://projectlombok.org/download)

## First steps
You need to add 3 resources files, each one have to be added into 
`src\main\resources` folder. Now i will enum a list of files with its name
and content:

**database.properties**
```
spring.data.mongodb.database= #Your database name
spring.data.mongodb.host= #Hostname of your connection
spring.data.mongodb.port= #Port of your connection
```

**file.properties**
```   
file.people.ftp.folder= #src/main/resources
file.people.ftp.file= #people.json
```

With those files you can run the application.

**Note:** the database.properties and context.xml are used to create a default
MongoDB bean.

## After run
Now you can do three things.

1.  Ask for allowed countries
2.  Search people data
3.  Add people into the database

### Ask for allowed countries.
```
REQUEST:
    METHOD: GET
    HEADER: NONE
    BODY: NONE
    
RESPONSE:
    BODY: 
        List of Countries (String[])
```
        
### Search people data
```
REQUEST:
    METHOD: POST
    HEADER: 
        country: String
    BODY:
        List of Ssn (Integer[])
        
RESPONSE:
    STATUS_CODE:
        200: SuccessList has values and ErrorList has no values
        207: SuccessList has values and ErrorList has values
        400: SuccessList has no values and ErrorList has no values
        404: SuccessList has no values and ErrorList has values
    BODY:
        ResponseList:
            successList: Person[],
            errorList: Object[]
```                

### Add people into the database
You have to create a file with the naming convention used in 
`${file.people.ftp.file}` property with the next format:

```
[
    {
        "ssn": 00000000,
        "name": "Gian F.",
        "surname": "S.",
        "bornDate": "01-01-1900",
        "country": "AR",
        "genre": "Male"
    }, 
    {},
    {},
    {}
]
```


and add it into the folder used in `${file.people.ftp.folder}` property.
This will add the people from the file into the database.