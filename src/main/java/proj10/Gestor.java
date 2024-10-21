package proj10;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class Gestor {
 SessionFactory sessionFactory;

	public static void main(String[] args) {
		 Gestor gestor = new Gestor();
		 gestor.setup();
		 gestor.createUser();
		 gestor.exit();
		}

	private void exit() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.getTransaction().commit();
		session.close();
	}
	public void createUser() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Utilizador u= new Utilizador();
		u.setLocalizacao("2222");
		u.setNome("222");
		u.setPassword("2222");
		u.setTelemovel("222");
		u.setTipoUtilizador("utilizador");
		u.setUsername("2");
		session.persist(u);
		session.getTransaction().commit();
		session.close();
	}

	private void setup() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				 .configure() // configures settings from hibernate.cfg.xml
				 .build();
				 try {
				 sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
				 } catch (Exception ex) {
				 StandardServiceRegistryBuilder.destroy(registry);
				}
	}

}
