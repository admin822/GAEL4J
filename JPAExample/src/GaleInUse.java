import TableGenerator.JPAEntities.Student;
import TableGenerator.JPAEntities.TableGenerator;
import com.gael4j.Gael.Gael;

import java.util.List;

public class GaleInUse {
    public static void main(String[] args) {
        // Populating database
        TableGenerator.createTableAndInsertJPA();

        // Initialization
        Gael gael = new Gael("TableGenerator.JPAEntities",
                true,
                "mysql-2390finalsample",
                "");
        // Query
        List<Object> result = gael.query(Student.class, "1");
        result.forEach(System.out::println);

        // Delete.
        gael.delete(Student.class, "1");
    }

}
