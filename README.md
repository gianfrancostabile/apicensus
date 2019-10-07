# APICensus

The main functionality of this application is search persons via rest camel endpoints.

## First steps
You need to add 3 resources files, each one have to be added into 
`src\main\resources` folder. Then i will describe list each file with its name
and content:

1.  database.properties (the next fields are mandatory)
`
    spring.data.mongodb.database=
    spring.data.mongodb.host=
    spring.data.mongodb.port=

    ...
`
2.  file.properties
`
    file.people.ftp.folder=
    file.people.ftp.file=
`

With those files you can run the application.

## After run
Now you can do three things.

1.  Ask for the allowed countries
2.  Search people data
3.  Add people into the database

### Ask for the allowed countries.
    REQUEST:
        METHOD: GET
        HEADER: NONE
        BODY: NONE
        
    RESPONSE:
        BODY: 
            List of Countries (String[])
        
        
### Search people data
    REQUEST:
        METHOD: POST
        HEADER: 
            country: String
        BODY:
            List of Ssn (Integer[])
            
    RESPONSE:
        STATUS_CODE:
            **200:** SuccessList has values and ErrorList has no values
            **207:** SuccessList has values and ErrorList has values
            **400:** SuccessList has no values and ErrorList has no values
            **404:** SuccessList has no values and ErrorList has values
        BODY:
            ResponseList:
                successList: Person[],
                errorList: Object[]
                

### Add people into the database
You have to create a file with the naming convention used in 
`${file.people.ftp.file}` property with the next format:

`
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
`


and add it into the folder used in `${file.people.ftp.folder}` property.
This will add the people from the file into the database.