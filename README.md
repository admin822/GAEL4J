# GAEL4J
GDPR-compliant Data Access and Erasure Tool for Java

## Some ideas for implementation

### 1. Idea one
> since spring JPA use annotation like @ID, @Column to specify columns in the database, is it possible for us to design annotation like @useraccess, @usererasure; then we define two functions like useraccess() and usererasure(), it will automatically delete/retrieve all of this data from the databse.

#### Process

GAEL will scan all the specified packages for our annotations, then when useraccess() or usererasure() is called, GAEL will either retrieve all the data from the DB and pack em up in a Json/Zip or erase them from the DB.

#### Questions

1. > how to scan these annotations?  

        This is easy(I think so at least), after JDK 5,
        Java inherently support defining and processing
        cutomized annotations.

2. > how does GAEL know where these fields are stored at the database?  

        well this one is easy, we just add more annotations
        or more annotation params like @database(schema=, table=, column=)

3. > what if there is too much private user information, do programmers have to add annotation one at a time?

        This is where the .xml comes in, programmers should
        be able to define a whole table to be private 
        information with options like 'exclude', so that if
        99 out of 100 columns in the table are private, they
        can exclude that one column that is not.

4. > what are the erasure policies? specificly, when there is a row in the database, 4 columns of that row are private data, the rest are not, what then? Delete the whole row? Render private data as null?
5. > Do we need to put GAEL's objects into Spring Container? what are the pros and cons?
6. > If the application uses ORMs(hibernate, mybatis etc.) to handle DB interactions, do we need to follow this custom, what are the pros and cons?

