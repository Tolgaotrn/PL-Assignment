package proj10;
import java.util.Scanner;
import java.util.List;
import org.hibernate.query.Query;

import com.mysql.cj.xdevapi.Schema;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class Gestor {
 SessionFactory sessionFactory;
 Scanner scanner = new Scanner(System.in);

		 
		

	public void exit() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.getTransaction().commit();
		session.close();
	}
	public void createUser() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Utilizador u= new Utilizador();

		System.out.print("Username:");
		String username = scanner.next();
		System.out.print("\n");
		System.out.print("Nome:");
		String nome = scanner.next();
		System.out.print("Password:");
		String password = scanner.next();
		System.out.print("\n");


		
		System.out.print("Telemovel:");
		String telemovel = scanner.next();
		System.out.print("\n");

		System.out.print("Localizacao:");
		String localizacao = scanner.next();
		System.out.print("\n");

		u.setNome(nome);
		u.setUsername(username);
		u.setPassword(password);
		u.setTelemovel(telemovel);
		u.setLocalizacao(localizacao);
		u.setTipoUtilizador("utilizador");
		session.persist(u);
		session.getTransaction().commit();
		session.close();
	}

	public void setup() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				 .configure() // configures settings from hibernate.cfg.xml
				 .build();
				 try {
				 sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
				 } catch (Exception ex) {
				 StandardServiceRegistryBuilder.destroy(registry);
				}
	}
	protected void read(int opcao) {
		Session session = sessionFactory.openSession();
		switch (opcao) {
			case 2:
			Query<Hospital> query = session.createQuery("FROM Hospital", Hospital.class);
			List<Hospital> hospitais = query.getResultList();

			// Percorre a lista de hospitais e imprime suas informações
			for (Hospital hospital : hospitais) {
				//System.out.println("Id: " + hospital.getIdHospital());
				System.out.println("Recurso Nome: " + hospital.getRecurso().getNome());
				System.out.println("Especialidades: " + hospital.getEspecialidades());
				System.out.println("Vagas: " + hospital.getVagas());
				System.out.println("Custos Acrescidos: " + hospital.getCustosAcrescidos());
				System.out.println("-------------------------------");
			}
	
				break;
			case 3:
				//ver utilizadores
				break;

			default:
				break;
		}
		session.close();
	   }
	
    public void admin() {
		int opcao = -1;
		while (opcao != 0) {
			System.out.println("----Menu Administrador----");
			System.out.println("1 - Adicionar Hospital");
			opcao = scanner.nextInt();
			switch (opcao) {
				case 1:
					addHospital();
					break;
				default:
					System.out.println("Opção inválida");
					break;
			}
		}
    }
	private void addHospital() {
		Session session = sessionFactory.openSession();
		Hospital hospital = new Hospital();
		System.out.println("Especialidades: ");
        String especialidades = scanner.nextLine();
        hospital.setEspecialidades(especialidades);

        System.out.println("Número de Vagas: ");
        int vagas = scanner.nextInt();
        hospital.setVagas(vagas);
        scanner.nextLine();

        System.out.println("Custos Acrescidos: ");
        String custosAcrescidos = scanner.nextLine();
        hospital.setCustosAcrescidos(custosAcrescidos);
        session.persist(hospital);
        session.getTransaction().commit();
        System.out.println("Hospital adicionado com sucesso!");
		session.close();
	}
}
