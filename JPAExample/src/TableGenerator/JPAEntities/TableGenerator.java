package TableGenerator.JPAEntities;

import com.gael4j.Gael.Util.JPAUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TableGenerator {
    public static void createTableAndInsertJPA() {
        Student firstStudent = new Student("1");
        Submission submission1 = new Submission("1", firstStudent, "answer1");
        Submission submission2 = new Submission("2", firstStudent, "answer2");
        Grade grade1 = new Grade("1", "1", 90, submission1);
        Grade grade2 = new Grade("2", "2", 95, submission1);
        Grade grade3 = new Grade("3", "1", 91, submission2);
        Project project1 = new Project("1", firstStudent, "project1");
        Project project2 = new Project("2", firstStudent, "project2");
        Presentation presentation1 = new Presentation("1", 15, project1);
        Presentation presentation2 = new Presentation("2", 12, project2);
        Presentation presentation3 = new Presentation("3", 10, project2);

        Student secondStudent = new Student("2");
        Submission submission3 = new Submission("3", secondStudent, "answer3");
        Submission submission4 = new Submission("4", secondStudent, "answer4");
        Grade grade4 = new Grade("4", "1", 90, submission3);
        Grade grade5 = new Grade("5", "2", 95, submission4);
        Grade grade6 = new Grade("6", "1", 91, submission4);

        EntityManager manager = JPAUtils.getEntityManger();
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();

        manager.persist(firstStudent);
        manager.persist(submission1);
        manager.persist(submission2);
        manager.persist(grade1);
        manager.persist(grade2);
        manager.persist(grade3);
        manager.persist(project1);
        manager.persist(project2);
        manager.persist(presentation1);
        manager.persist(presentation2);
        manager.persist(presentation3);

        manager.persist(secondStudent);
        manager.persist(submission3);
        manager.persist(submission4);
        manager.persist(grade4);
        manager.persist(grade5);
        manager.persist(grade6);

        transaction.commit();
        manager.close();
    }
}
