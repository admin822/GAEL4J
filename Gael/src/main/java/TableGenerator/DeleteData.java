package TableGenerator;

import org.hibernate.Session;

public class DeleteData {
	public static void delete(Session session,Class<?> entityClass, String id) {
		session.beginTransaction();
		Object obj=session.load(entityClass, id);
		session.delete(obj);
		session.getTransaction().commit();
	}
}
