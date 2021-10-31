# GAEL4J
GDPR-compliant Data Access and Erasure Tool for Java



## Code Structure:
in src folder:
- com.gael4j.Gael
  - Annotations  
  - AnnotationProcessing
    - Processing
    - JpaProcessing
  - Util
    - JPAUtils
    - Util  
  - Gael.class(```boolean usingJPa```, ```List<DBConfig> dbTableList``` in this class'constructor, if ```usingJpa==true```, then use what is provided in **JpaProcessing**, otherwise, **processing**; but either way, the result of constructor is to get ```dbTableList``` which contains all tables related)
 

- com.gael4j.Entity
  - DBConfig.class(```String db_name,table_name,List<String> column_names,className, FieldList```)

- com.gael4j.DAO
  - HibernateMaster.class
  - MybatisMaster(maybe)
  - and more...

in test folder:

- com.gael4j.JPA: this should include everything needed for testing the JPA branch.
- com.gael4j.NonJPA: this should include everything needed for testing the Non-JPA branch

## Roadmap

1. Achieve **class level** *userdata* annotation **without** the *exclude* annotation. Application should be able to retrieve and delete designated userdata from database tables **without** *foreign key*

Now we have two braches: applications that use JPA and those that don't


## FIX ME
1. user needs to tell GAEL what the **primary key** for each table is, otherwise, even hibernate would not be able to act correctly; This means annotations, annotation processing, DBConfig all require changing
2. user needs to tell GAEL all the information we need to build the JDBC url, like what is their db's url, what is their port etc.
   
## Some ideas for future implementation

### 2. Idea One
> If we are going to make GAEL4J compatible with spring-boot, one way to think about this is making it a spring plugin.
> This requires our package to be registered as a bean in spring's container. This will require more understanding about spring factory.

#### Process of idea one
GAEL can be configured as an application listener. When users try to access or erase their personal data, this event will be 
triggered and start looking for configuration file that contains the location of these data (database, table, column, ect).


## Some Questions about future improvement

1. > what if there is too much private user information, do programmers have to add annotation one at a time?

        This is where the .xml comes in, programmers should
        be able to define a whole table to be private 
        information with options like 'exclude', so that if
        99 out of 100 columns in the table are private, they
        can exclude that one column that is not.

2. > what are the erasure policies? specificly, when there is a row in the database, 4 columns of that row are private data, the rest are not, what then? Delete the whole row? Render private data as null?
3. > Do we need to put GAEL's objects into Spring Container? what are the pros and cons?       

4. > When user click the delete button, are we going to delete them immediately? Is there any recover method?
                
        Considering the recovering process, we may not delete
        the data immediately. Instead we can make these data 
        unaccessable, and remove them after a period of time (30 days).



