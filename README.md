# GAEL4J
An external, non-invasive GDPR-compliant Data Access and Erasure Tool for Java

## Code Structure:
Inside src/main/java folder:
### com.gael4j.Gael
  - **Annotations**: All the custom annotations are located here.  
  - **AnnotationProcessing**
    - JPA: Annotation processing logic for JPA setting
    - NonJPA: Annotation processing logic for Non-JPA setting
  - **Gael.java**: In the constructor, if ```usingJpa==true```, then use what is provided in **JPAProcessing**, otherwise, **NonJPA processing**; but either way, the result of constructor is to get ```directedGraph``` which contains a directed graph that indicates the associations between different tables)
 

### com.gael4j.Entity
  - **DBConfig**: Includes database configuration like database name, table name, matching class names etc.
  - **ChildNode**: Node class used in the directed graph mentioned above.

### com.gael4j.DAO
  - **JPA/Hibernate:** Includes all the Hibernate handling logic for JPA setting
  - **NonJPA/Hibernate:** Includes all the Hibernate handling logic for Non-JPA setting
  - **DAOManager:** public interface that needs to be implemented by all other DAO managers.

in test folder:

- **com.gael4j.JPA:** JPA setting tests
- **com.gael4j.NonJPA:** this should include everything needed for testing the Non-JPA branch

## Roadmap

- [x] Achieve **class level** *userdata* annotation **without** the *exclude* annotation. Application should be able to retrieve and delete designated userdata from database tables **without** *foreign key*

- [x] Develope two branches: one with JPA annotation, the other without
- [x] Add in complex query and deletion logic when multiple tables connected with foreign keys are presented.
- [x] Support uni/bidirectional one-to-one, many-to-one, one-to-many associations between tables. 
- [ ] Consider more complex scenarios like uni/bidirectional many-to-many associations between tables
- [ ] consider adding ```@exclude``` annotation into the annotation set to indicate which field in a **privacy class** should be left out when queried.
- [x] Develope a Demo( a toy project, package up GAEL, add GAEL into the toy project, test it) (<b><span style="color:red" >MANDATORY</span></b>)
- [x] Write a report (<b><span style="color:red" >MANDATORY</span></b>)


## FIX ME
- [x] user needs to tell GAEL what the **primary key** for each table is, otherwise, even hibernate would not be able to act correctly; This means annotations, annotation processing, DBConfig all require changing
- [x] user needs to be able to tell GAEL all the information we need to build the JDBC url, like what is their db's url, what is their port.

   
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
      
3. > When user click the delete button, are we going to delete them immediately? Is there any recover method?
                
        Considering the recovering process, we may not delete
        the data immediately. Instead we can make these data 
        unaccessable, and remove them after a period of time (30 days).



