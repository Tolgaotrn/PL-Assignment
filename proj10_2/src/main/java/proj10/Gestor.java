package proj10;
import java.util.Scanner;
import java.util.List;
import org.hibernate.query.Query;

import com.mysql.cj.xdevapi.Schema;

import proj10.Recursos.TipoRecurso;

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
			System.out.println("\n" + "----------------------");
			System.out.println("| Lista de hospitais |");
			System.out.println(  "----------------------");
			for (Hospital hospital : hospitais) {
				//System.out.println("Id: " + hospital.getIdHospital());
				
				System.out.println("\n"+"Nome: " + hospital.getRecurso().getNome());
				System.out.println("Especialidades: " + hospital.getEspecialidades());
				System.out.println("Vagas: " + hospital.getVagas());
				System.out.println("Custos Acrescidos: " + hospital.getCustosAcrescidos());
				System.out.println("-------------------------------");
			}
				System.out.print("\n");
				break;
			case 3:
				Query<Utilizador> query2 = session.createQuery("FROM Utilizador", Utilizador.class);
				List<Utilizador> utilizadores = query2.getResultList();
				System.out.println("\n" + "-------------------------");
				System.out.println("| Lista de utilizadores |");
				System.out.println(  "-------------------------");
				if (utilizadores.isEmpty()) {
					System.out.println("Não existem utilizadores");
				} else {
					for (Utilizador utilizador : utilizadores) {
						System.out.println("Id: " + utilizador.getIdUtilizador());
						System.out.println("Nome: " + utilizador.getNome());
						System.out.println("Username: " + utilizador.getUsername());
						System.out.println("Password: " + utilizador.getPassword());
						System.out.println("Telemovel: " + utilizador.getTelemovel());
						System.out.println("Localizacao: " + utilizador.getLocalizacao());
						System.out.println("Tipo de Utilizador: " + utilizador.getTipoUtilizador());
						System.out.println("-------------------------------");
					}
					System.out.print("\n");
				}	
				break;

			default:
				break;
		}
		session.close();
	   }
	
    public void admin() {
		int opcao = -1;
		while (opcao != 0) {
			System.out.println("\n" + "-------------------------");
			System.out.println("| Menu de administrador |");
			System.out.println(  "-------------------------");
			//System.out.println("1 - Adicionar Hospital");
			System.out.println("1 - Ver utilizadores");
			System.out.println("2 - Eliminar utilizador");
			System.out.println("3 - Atualizar utilizador");
			System.out.print("0 - Sair" +"\n" + "Opção: ");
			opcao = scanner.nextInt();
			switch (opcao) {
				case 1:
					read(3);
					break;
				case 2:
					apagarUser();
					break;
				case 3:
					atualizaUser();
					break;
				case 0:
					break;
				default:
					System.out.println("Opção inválida");
					break;
			}
		}
    }
	private void atualizaUser() {
		read(3);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.print("Id do utilizador a atualizar: ");
		int id = scanner.nextInt();
		Utilizador utilizador = session.get(Utilizador.class, id);
		System.out.println("O que deseja atualizar? ");
		System.out.print("1 - Nome"+"\n"+"2 - Username"+"\n"+"3 - Telemóvel" + "\n" + "4 - Localização" + "\n" + "0 - Sair " +"\n" + "Opção: ");
		int opcao = scanner.nextInt();
		switch (opcao) {
			case 1:
				System.out.print("Novo nome: ");
				String nome = scanner.next();
				utilizador.setNome(nome);
				break;
			case 2:
				System.out.print("Novo username: ");
				String username = scanner.next();
				utilizador.setUsername(username);
				break;
			case 3:
				System.out.print("Novo telemóvel: ");
				String telemovel = scanner.next();
				utilizador.setTelemovel(telemovel);
				break;
			case 4:
				System.out.print("Nova localização: ");
				String localizacao = scanner.next();
				utilizador.setLocalizacao(localizacao);
				break;
			case 0:
				break;
			default:
				System.out.println("Opção inválida");
				break;
		}
		session.update(utilizador);
		session.getTransaction().commit();
		System.out.println("Utilizador atualizado com sucesso!");
		session.close();
	}
	private void apagarUser() {
		read(3);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.print("Id do utilizador a eliminar: ");
		int id = scanner.nextInt();
		Utilizador utilizador = session.get(Utilizador.class, id);
		session.delete(utilizador);
		session.getTransaction().commit();
		System.out.println("Utilizador eliminado com sucesso!");
		session.close();
	}
	private void addRecurso() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Recursos recurso = new Recursos();
		System.out.println("Nome do recurso: ");
		String nome = scanner.nextLine();
		recurso.setNome(nome);

		System.out.println("Localização: ");
		String localizacao = scanner.nextLine();
		recurso.setLocalizacao(localizacao);

		System.out.println("Telefone: ");
		String telefone = scanner.nextLine();
		recurso.setTelefone(telefone);

		System.out.println("Tipo de recurso: ");
		String tipoRecursoString = scanner.next();

		try {
    	TipoRecurso tipoRecurso = TipoRecurso.valueOf(tipoRecursoString.toLowerCase());
    	recurso.setTipoRecurso(tipoRecurso);
		} catch (IllegalArgumentException e) {
   		System.out.println("Tipo de recurso inválido. Tente novamente.");
		}

		session.persist(recurso);
		session.getTransaction().commit();
		if (recurso.getTipoRecurso().toString() == "hospital" ) {
			addHospital(recurso.getIdRecurso());
		}
		System.out.println("Recurso adicionado com sucesso!");
		session.close();
	}


	private void addHospital(int id_recurso) {
		System.out.println("Entrei no addHospital");
		System.out.println("Id do recurso: " + id_recurso);
		Session session = sessionFactory.openSession();
		Hospital hospital = new Hospital();
		hospital.setIdHospital(id_recurso);
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
        session.merge(hospital);
        session.getTransaction().commit();
        System.out.println("Hospital adicionado com sucesso!");
		session.close();
	}
}
