# APICensus

The main functionality of this application is search persons via rest camel endpoints.

## Contents
*  [First steps](#first-steps)
*  [After run](#after-run)
    *  [Ask for allowed countries](#ask-for-the-allowed-countries)
    *  [Search people data](#search-people-data)
    *  [Add people into the database](#add-people-into-the-database)
  

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