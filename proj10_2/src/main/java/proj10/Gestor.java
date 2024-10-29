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
/*
 * Implementar herança
 * Simplificar o processo de ediçao de utilizadores
 * Retirar o on delete cascade  (FEITO)
 * Adicionar a opção de adicionar um recurso, seja ele qual for
 * Resultados a aparecer com base na localização
 * Aplicar normas de rgpd às informações dos utilizadores e ao acesso que os administradores têm
 * Criptografar passwords dos utilizadores na base de dados
 * dividir o read por outras funções
 * retirar composição e adicionar herança entre recurso e os sub recursos
 * tabela de recursos ligar à tabela de favoritos através de uma relação 1:m, pois um recurso pode ser favorito de vários utilizadores
 * tabela de utilizadores ligar à tabela de favoritos através de uma relação 1:m, pois um utilizador pode ter vários favoritos
 * recursos 1:m----> favoritos <----1:m utilizadores
 */

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
		System.out.println("\n" + "--------------------");
        System.out.println("| Criar utilizador |");
        System.out.println(  "--------------------"+ "\n");
		Utilizador u= new Utilizador();
		System.out.print("Nome:");
		String nome = scanner.next();
		System.out.print("\n");
	
		System.out.print("Username:");
		String username = scanner.next();
		System.out.print("\n");
		
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
	public void mostrarRecursos() {
		Session session = sessionFactory.openSession();
			boolean flag = false;
			Query<Hospital> query = session.createQuery("FROM Hospital", Hospital.class);
			List<Hospital> hospitais = query.getResultList();
			System.out.println("\n" + "----------------------");
			System.out.println("| Lista de hospitais |");
			System.out.println(  "----------------------");
			for (Hospital hospital : hospitais) {
				flag = true;
				//System.out.println("Id: " + hospital.getIdHospital());
				
				System.out.println("\n"+"Nome: " + hospital.getNome());
				System.out.println("Especialidades: " + hospital.getEspecialidades());
				System.out.println("Vagas: " + hospital.getVagas());
				System.out.println("Custos Acrescidos: " + hospital.getCustosAcrescidos());
				System.out.println("Localização: " + hospital.getLocalizacao());
				System.out.println("Telefone: " + hospital.getTelefone());
				System.out.println("Informação extra: " + hospital.getInformacaoExtra());
				System.out.println("-------------------------------");
			}
				System.out.print("\n");
				if (flag == false) {
					System.out.println("Não existem hospitais na base de dados \n");
				}
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
			System.out.println("4 - Adicionar recurso");
			System.out.println("5 - Editar recursos");
			System.out.println("6 - Eliminar recursos");
			System.out.println("0 - Sair" +"\n" + "Opção: ");
			opcao = scanner.nextInt();
			switch (opcao) {
				case 1:
					mostrarUtilizadores();
					break;
				case 2:
					apagarUser();
					break;
				case 3:
					atualizaUser();
					break;
				case 4:
					addRecurso();
					break;
				case 5:
					editarRecursos();
					break;
				case 6:
					eliminarRecurso();
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
		mostrarUtilizadores();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.print("Id do utilizador a atualizar: ");
		int id = scanner.nextInt();
		Utilizador utilizador = session.get(Utilizador.class, id);

		System.out.println("Nome: ");
		String nome = scanner.next();
		utilizador.setNome(nome);

		System.out.println("Username: ");
		String username = scanner.next();
		utilizador.setUsername(username);

		System.out.println("Telemóvel: ");
		String telemovel = scanner.next();
		utilizador.setTelemovel(telemovel);

		System.out.println("Localização: ");
		String localizacao = scanner.next();
		utilizador.setLocalizacao(localizacao);
		
		session.update(utilizador);
		session.getTransaction().commit();
		System.out.println("Utilizador atualizado com sucesso!");
		session.close();
		}
		
	private void mostrarUtilizadores() {
		Session session = sessionFactory.openSession();
		Query<Utilizador> query = session.createQuery("FROM Utilizador", Utilizador.class);
		List<Utilizador> utilizadores = query.getResultList();
		System.out.println("\n" + "-------------------------");
		System.out.println("| Lista de utilizadores |");
		System.out.println(  "-------------------------");
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
		session.close();
	}
	private void apagarUser() {
			mostrarUtilizadores();
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
		
			System.out.println("Nome do recurso: ");
			String nome = scanner.next();
		
			System.out.println("Localização: ");
			String localizacao = scanner.next();
		
			System.out.println("Telefone: ");
			String telefone = scanner.next();
		
			System.out.println("Tipo de recurso: ");
			System.out.println("1 - Hospital\n2 - Abrigo\n3 - Cozinha\n4 - Centro de trocas\n5 - Banco de alimentos");
			int tipoRecursoOp = scanner.nextInt();
			TipoRecurso tipoRecurso = null;
			Recursos recurso;
			switch (tipoRecursoOp) {
				case 1:
					tipoRecurso = TipoRecurso.hospital;
					recurso = new Hospital();
					System.out.println("Especialidades: ");
					((Hospital) recurso).setEspecialidades(scanner.next());
		
					System.out.println("Vagas: ");
					((Hospital) recurso).setVagas(scanner.nextInt());
		
		
					System.out.println("Custos acrescidos: ");
					((Hospital) recurso).setCustosAcrescidos(scanner.next());
		
					System.out.println("Informação extra: ");
					((Hospital) recurso).setInformacaoExtra(scanner.next());
					break;
		
				case 2:
				tipoRecurso = TipoRecurso.abrigo;
					recurso = new Abrigo();
					System.out.println("Vagas: ");
					((Abrigo) recurso).setVagas(scanner.nextInt());
		
		
					System.out.println("Custos acrescidos: ");
					((Abrigo) recurso).setCustosAcrescidos(scanner.next());
		
					System.out.println("Informação extra: ");
					((Abrigo) recurso).setInformacaoExtra(scanner.next());
					break;
				
		
				case 3:
					tipoRecurso = TipoRecurso.cozinha;
					recurso = new CozinhaComunitaria();
					System.out.println("Tipo de comida: ");
					((CozinhaComunitaria) recurso).setTipoComida(scanner.next());
		
					System.out.println("Capacidade: ");
					((CozinhaComunitaria) recurso).setCapacidade(scanner.nextInt());
		
					System.out.println("Custos acrescidos: ");
					((CozinhaComunitaria) recurso).setCustosAcrescidos(scanner.next());
		
					System.out.println("Informação extra: ");
					((CozinhaComunitaria) recurso).setInformacaoExtra(scanner.next());
					break;
				case 4:
					tipoRecurso = TipoRecurso.centro_trocas;
					recurso = new CentroTrocaRoupa();
					System.out.println("Custos acrescidos: ");
					((CentroTrocaRoupa) recurso).setCustosAcrescidos(scanner.next());
		
					System.out.println("Informação extra: ");
					((CentroTrocaRoupa) recurso).setInformacaoExtra(scanner.next());
					break;
				case 5:
					tipoRecurso = TipoRecurso.banco_alimento;
					recurso = new BancoAlimentos();
					System.out.println("Custos acrescidos: ");
					((BancoAlimentos) recurso).setCustosAcrescidos(scanner.next());
		
					System.out.println("Informação extra: ");
					((BancoAlimentos) recurso).setInformacaoExtra(scanner.next());
					break;
				
				default:
					System.out.println("Tipo de recurso inválido.");
					session.getTransaction().rollback();
					session.close();
					return;
			}
		
			recurso.setNome(nome);
			recurso.setLocalizacao(localizacao);
			recurso.setTelefone(telefone);
			recurso.setTipoRecurso(tipoRecurso);
			session.persist(recurso);
			session.getTransaction().commit();
			System.out.println("Recurso adicionado com sucesso!");
			session.close();
		}
		
		private void eliminarRecurso() {
			mostrarRecursos();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			System.out.print("Id do recurso a eliminar: ");
			int id = scanner.nextInt();
			Recursos recurso = session.get(Recursos.class, id);
			session.delete(recurso);
			session.getTransaction().commit();
			System.out.println("Recurso eliminado com sucesso!");
			session.close();
		}	


    private void editarRecursos() {
			mostrarRecursos();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			System.out.print("Id do recurso a editar: ");
			int id = scanner.nextInt();
			Recursos recurso = session.get(Recursos.class, id);
			TipoRecurso tipoDeRecurso = recurso.getTipoRecurso();
			System.out.println("Nome: ");
			String nome = scanner.next();
			recurso.setNome(nome);
			System.out.println("Localização: ");
			String localizacao = scanner.next();
			recurso.setLocalizacao(localizacao);
			System.out.println("Telefone: ");
			String telefone = scanner.next();
			recurso.setTelefone(telefone);
			switch (tipoDeRecurso) {
				case hospital:
					System.out.println("Especialidades: ");
					((Hospital) recurso).setEspecialidades(scanner.next());
					System.out.println("Vagas: ");
					((Hospital) recurso).setVagas(scanner.nextInt());
					System.out.println("Custos acrescidos: ");
					((Hospital) recurso).setCustosAcrescidos(scanner.next());
					System.out.println("Informação extra: ");
					((Hospital) recurso).setInformacaoExtra(scanner.next());


					break;
				case abrigo:
					System.out.println("Vagas: ");
					((Abrigo) recurso).setVagas(scanner.nextInt());
					System.out.println("Custos acrescidos: ");
					((Abrigo) recurso).setCustosAcrescidos(scanner.next());
					System.out.println("Informação extra: ");
					((Abrigo) recurso).setInformacaoExtra(scanner.next());
					break;
				case cozinha:
					System.out.println("Tipo de comida: ");
					((CozinhaComunitaria) recurso).setTipoComida(scanner.next());
					System.out.println("Capacidade: ");
					((CozinhaComunitaria) recurso).setCapacidade(scanner.nextInt());
					System.out.println("Custos acrescidos: ");
					((CozinhaComunitaria) recurso).setCustosAcrescidos(scanner.next());
					System.out.println("Informação extra: ");
					((CozinhaComunitaria) recurso).setInformacaoExtra(scanner.next());
					break;
				case centro_trocas:
					System.out.println("Custos acrescidos: ");
					((CentroTrocaRoupa) recurso).setCustosAcrescidos(scanner.next());
					System.out.println("Informação extra: ");
					((CentroTrocaRoupa) recurso).setInformacaoExtra(scanner.next());
					break;
				case banco_alimento:
					System.out.println("Custos acrescidos: ");
					((BancoAlimentos) recurso).setCustosAcrescidos(scanner.next());
					System.out.println("Informação extra: ");
					((BancoAlimentos) recurso).setInformacaoExtra(scanner.next());
					break;
				
				default:
					System.out.println("Tipo de recurso inválido.");
					session.getTransaction().rollback();
					session.close();
					return;
			}
			
			session.update(recurso);
			session.getTransaction().commit();
			System.out.println("Recurso atualizado com sucesso!");
			session.close();
			
		}
    
	public void login() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'login'");
	}
    public void menuAnonimo(int i) {
        System.out.println("\n" + "--------------------");
			System.out.println("| Menu anónimo |");
			System.out.println(  "--------------------");
			//System.out.println("1 - Adicionar Hospital");
			System.out.println("1 - Ver recursos");
			System.out.print("0 - Sair" +"\n" + "Opção: ");
			int opcao = scanner.nextInt();
			switch (opcao) {
				case 1:
					System.out.println("1-hospitais \n asdasdasd ");
					break;
				case 0:
				System.out.println("A sair...");
					break;
    }
}
}
