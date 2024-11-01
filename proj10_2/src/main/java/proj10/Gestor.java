package proj10;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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
 * Implementar herança (FEITO)
 * Simplificar o processo de ediçao de utilizadores (FEITO)
 * Retirar o on delete cascade  (FEITO)
 * Adicionar a opção de adicionar um recurso, seja ele qual for (FEITO)
 * Resultados a aparecer com base na localização (FEITO)
 * Aplicar normas de rgpd às informações dos utilizadores e ao acesso que os administradores têm (FEITO)
 * Criptografar passwords dos utilizadores na base de dados (FEITO)
 * dividir o read por outras funções (FEITO)
 * retirar composição e adicionar herança entre recurso e os sub recursos (FEITO)
 * tabela de recursos ligar à tabela de favoritos através de uma relação 1:m, pois um recurso pode ser favorito de vários utilizadores
 * tabela de utilizadores ligar à tabela de favoritos através de uma relação 1:m, pois um utilizador pode ter vários favoritos
 * recursos 1:m----> favoritos <----1:m utilizadores
 * ADICIONAR FAVORITOS (FEITO)
 * ALTERAR NEXT PARA NEXTLINE, POIS OS INPUTS SE TIVEREM ESPAÇOS NÃO FUNCIONAM
 * susbtituir por *
 * lista de localiações numa tabela diferente e outra
 * tipo passar para tabela diferente e adicionar os CRUD para o tipo
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
	public void criarUtilizador() {
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
		u.setPassword(encriptar(password));
		u.setTelemovel(telemovel);
		u.setLocalizacao(localizacao);
		u.setTipoUtilizador("utilizador");
		session.persist(u);
		session.getTransaction().commit();
		session.close();
	}
	//ENCRIPTAR PASSWORD
	public static String encriptar(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: SHA-256 algorithm not found.", e);
		}
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
		Query<Recursos> query = session.createQuery("FROM Recursos", Recursos.class);
		List<Recursos> recursos = query.getResultList();
		System.out.println("\n" + "-------------------------");
		System.out.println("| Lista de recursos |");
		System.out.println(  "-------------------------");
		for (Recursos recurso : recursos) {
			
			System.out.println("Id: " + recurso.getIdRecurso());
			System.out.println("Nome: " + recurso.getNome());
			System.out.println("Localização: " + recurso.getLocalizacao());
			System.out.println("Telefone: " + recurso.getTelefone());
			String tipo = recurso.getTipoRecurso().toString();
			System.out.println("Tipo de recurso: " + recurso.getTipoRecurso());
			switch (tipo) {
				case "hospital":
					System.out.println("Especialidades: " + ((Hospital) recurso).getEspecialidades());
					System.out.println("Vagas: " + ((Hospital) recurso).getVagas());
					System.out.println("Custos acrescidos: " + ((Hospital) recurso).getCustosAcrescidos());
					System.out.println("Informação extra: " + ((Hospital) recurso).getInformacaoExtra());
					break;
				case "abrigo":
					System.out.println("Vagas: " + ((Abrigo) recurso).getVagas());
					System.out.println("Custos acrescidos: " + ((Abrigo) recurso).getCustosAcrescidos());
					System.out.println("Informação extra: " + ((Abrigo) recurso).getInformacaoExtra());
					break;
				case "cozinha":
					System.out.println("Tipo de comida: " + ((CozinhaComunitaria) recurso).getTipoComida());
					System.out.println("Capacidade: " + ((CozinhaComunitaria) recurso).getCapacidade());
					System.out.println("Custos acrescidos: " + ((CozinhaComunitaria) recurso).getCustosAcrescidos());
					System.out.println("Informação extra: " + ((CozinhaComunitaria) recurso).getInformacaoExtra());
					break;
				case "centro_trocas":
					System.out.println("Custos acrescidos: " + ((CentroTrocaRoupa) recurso).getCustosAcrescidos());
					System.out.println("Informação extra: " + ((CentroTrocaRoupa) recurso).getInformacaoExtra());
					break;
				case "banco_alimento":
					System.out.println("Custos acrescidos: " + ((BancoAlimentos) recurso).getCustosAcrescidos());
					System.out.println("Informação extra: " + ((BancoAlimentos) recurso).getInformacaoExtra());
					break;
				
				default:
					break;
			}
			System.out.println("-------------------------------");
		}
		System.out.print("\n");
		session.close();
	}
	//ADICIONAR CRIPTAÇÃO DA PASSWORD

    public void admin() {
		int opcao = -1;
		while (opcao != 0) {
			System.out.println("\n" + "-------------------------");
			System.out.println("| Menu de administrador |");
			System.out.println(  "-------------------------");
			System.out.println("1 - Ver utilizadores");
			System.out.println("2 - Eliminar utilizador");
			System.out.println("3 - Atualizar utilizador");
			System.out.println("4 - Adicionar recurso");
			System.out.println("5 - Editar recursos");
			System.out.println("6 - Eliminar recursos");
			System.out.print("0 - Sair" +"\n" + "Opção: ");
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

		System.out.print("Nome: ");
		String nome = scanner.next();
		utilizador.setNome(nome);


		System.out.print("Telemóvel: ");
		String telemovel = scanner.next();
		utilizador.setTelemovel(telemovel);

		System.out.print("Localização: ");
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
			System.out.println("Password: " + "Devido ás normas de RGPD (Regulamento Geral de Proteção de Dados) não é possivel mostrar a password");
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
		
			System.out.print("Nome do recurso: ");
			String nome = scanner.next();
		
			System.out.print("Localização: ");
			String localizacao = scanner.next();
		
			System.out.print("Telefone: ");
			String telefone = scanner.next();
		
			System.out.println("Tipo de recurso: ");
			System.out.print("1 - Hospital\n2 - Abrigo\n3 - Cozinha\n4 - Centro de trocas\n5 - Banco de alimentos\nOpção: ");
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
			System.out.print("Nome: ");
			String nome = scanner.next();
			recurso.setNome(nome);
			System.out.print("Localização: ");
			String localizacao = scanner.next();
			recurso.setLocalizacao(localizacao);
			System.out.print("Telefone: ");
			String telefone = scanner.next();
			recurso.setTelefone(telefone);
			switch (tipoDeRecurso) {
				case hospital:
					System.out.print("Especialidades: ");
					String especialidades = scanner.next();
					((Hospital) recurso).setEspecialidades(especialidades);
					System.out.print("Vagas: ");
					int vagas = scanner.nextInt();
					((Hospital) recurso).setVagas(vagas);
					System.out.print("Custos acrescidos: ");
					String custosAcrescidos = scanner.next();
					((Hospital) recurso).setCustosAcrescidos(custosAcrescidos);
					System.out.print("Informação extra: ");
					String informacaoExtra = scanner.next();
					((Hospital) recurso).setInformacaoExtra(informacaoExtra);


					break;
				case abrigo:
					System.out.print("Vagas: ");
					vagas = scanner.nextInt();
					((Abrigo) recurso).setVagas(vagas);
					System.out.print("Custos acrescidos: ");
					custosAcrescidos = scanner.next();
					((Abrigo) recurso).setCustosAcrescidos(custosAcrescidos);
					System.out.print("Informação extra: ");
					informacaoExtra = scanner.next();
					((Abrigo) recurso).setInformacaoExtra(informacaoExtra);
					break;
				case cozinha:
					System.out.print("Tipo de comida: ");
					String tipoComida = scanner.next();
					((CozinhaComunitaria) recurso).setTipoComida(tipoComida);
					System.out.print("Capacidade: ");
					int capacidade = scanner.nextInt();
					((CozinhaComunitaria) recurso).setCapacidade(capacidade);
					System.out.print("Custos acrescidos: ");
					custosAcrescidos = scanner.next();
					((CozinhaComunitaria) recurso).setCustosAcrescidos(custosAcrescidos);
					System.out.print("Informação extra: ");
					informacaoExtra = scanner.next();
					((CozinhaComunitaria) recurso).setInformacaoExtra(informacaoExtra);
					break;
				case centro_trocas:
					System.out.print("Custos acrescidos: ");
					custosAcrescidos = scanner.next();
					((CentroTrocaRoupa) recurso).setCustosAcrescidos(custosAcrescidos);
					System.out.print("Informação extra: ");
					informacaoExtra = scanner.next();
					((CentroTrocaRoupa) recurso).setInformacaoExtra(informacaoExtra);
					
					break;
				case banco_alimento:
					System.out.print("Custos acrescidos: ");
					custosAcrescidos = scanner.next();
					((BancoAlimentos) recurso).setCustosAcrescidos(custosAcrescidos);
					System.out.print("Informação extra: ");
					informacaoExtra = scanner.next();
					((BancoAlimentos) recurso).setInformacaoExtra(informacaoExtra);
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
		System.out.print("Username: ");
		String username = scanner.next();
		System.out.print("Password: ");
		String password = scanner.next();
		Session session = sessionFactory.openSession();
		Query<Utilizador> query = session.createQuery("FROM Utilizador WHERE username = :username AND password = :password", Utilizador.class);
		query.setParameter("username", username);
		query.setParameter("password", encriptar(password));
		List<Utilizador> utilizadores = query.getResultList();
		if (utilizadores.size() == 1) {
			Utilizador utilizador = utilizadores.get(0);
			
			if (utilizador.getTipoUtilizador().equals("admin")) {
				admin();
			} else {
				menuUtilizador(utilizador.getIdUtilizador());
			}
		} else {
			System.out.println("Utilizador não encontrado.");
		}
		
	}
    private void menuUtilizador(int idUtilizador) {
		int opcao = -1;
		do{
			System.out.println("\n" + "--------------------");
		System.out.println("| Menu utilizador |");
		System.out.println(  "--------------------");
		System.out.println("1 - Ver recursos");
		System.out.println("2 - Adicionar recurso a favoritos");
		System.out.println("3 - Ver recursos favoritos");
		System.out.println("0 - Sair" +"\n" + "Opção: ");
		opcao = scanner.nextInt();

		switch (opcao) {
			case 1:
				System.out.print("Pesquisar por: \n 1 - Hospitais\n 2 - Abrigos\n 3 - Cozinhas Comunitárias\n 4 - Centros de trocas\n 5 - Bancos de alimentos\n 0 - Sair\nOpção: ");
				int tipoRecursoOp = scanner.nextInt();
				TipoRecurso tipoRecurso = null;
				switch (tipoRecursoOp) {
					case 1:
						tipoRecurso = TipoRecurso.hospital;
						break;
					case 2:
						tipoRecurso = TipoRecurso.abrigo;
						break;
					case 3:
						tipoRecurso = TipoRecurso.cozinha;
						break;
					case 4:
						tipoRecurso = TipoRecurso.centro_trocas;
						break;
					case 5:
						tipoRecurso = TipoRecurso.banco_alimento;
						break;
					case 0:
						break;
					default:
						System.out.println("Tipo de recurso inválido.");
						break;
				}
				System.out.print("Em que localização deseja procurar recursos? ");
				String localizacao = scanner.next();
				pesquisarRecurso(tipoRecurso, localizacao);
				break;
			case 2:
				adicionarFavorito(idUtilizador);
				break;
			case 3:
				mostrarFavoritos(idUtilizador);
				break;
			case 0:
				break;
			default:
				System.out.println("Opção inválida");
				break;
		}
		} while (opcao != 0);
		
		
	}
	private void mostrarFavoritos(int idUtilizador) {
		Session session = sessionFactory.openSession();
		Query<Favorito> query = session.createQuery("FROM Favorito WHERE idUtilizador = :idUtilizador", Favorito.class);
		query.setParameter("idUtilizador", idUtilizador);
		List<Favorito> favoritos = query.getResultList();
		System.out.println("\n" + "-------------------------");
		System.out.println("| Lista de favoritos |");
		System.out.println(  "-------------------------");
		//ciclo para mostrar os favoritos
		for (Favorito favorito : favoritos) {
			Recursos recurso = session.get(Recursos.class, favorito.getIdRecurso());
				System.out.println("Nome do Recurso: " + recurso.getNome());
				System.out.println("Localização: " + recurso.getLocalizacao());
				System.out.println("Telefone: " + recurso.getTelefone());
			System.out.println("Data do Favorito: " + favorito.getDataFavorito());
			
			System.out.println("-------------------------------");
		}

	

	}
	private void adicionarFavorito(int idUtilizador) {
		mostrarRecursos();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.print("Id do recurso a adicionar a favoritos: ");
		int idRecurso = scanner.nextInt();
		Favorito favorito = new Favorito();
		favorito.setIdUtilizador(idUtilizador);
		favorito.setIdRecurso(idRecurso);
		favorito.setDataFavorito(new java.util.Date());
		session.persist(favorito);
		System.out.println("Adicionando favorito para o utilizador " + idUtilizador + " e recurso " + idRecurso);


	}
	public void menuAnonimo(int i) {
		int opcao = -1;
		do{
        System.out.println("\n" + "--------------------");
			System.out.println("| Menu anónimo |");
			System.out.println(  "--------------------");
			System.out.print("Pesquisar por:"+"\n"+"1 - Hospitais"+"\n"+"2 - Abrigos"+"\n"+"3 - Cozinhas Comunitárias"+"\n"+"4 - Centros de trocas"+"\n"+"5 - Bancos de alimentos"+"\n"+"0 - Sair" +"\n" + "Opção: ");
			opcao = scanner.nextInt();
			System.out.print("Em que localização deseja procurar recursos?: ");
			String localizacao = scanner.next();
			switch (opcao) {
				case 1:
					pesquisarRecurso(TipoRecurso.hospital,localizacao);
					break;
				case 2:
					pesquisarRecurso(TipoRecurso.abrigo,localizacao);
					break;
				case 3:
					pesquisarRecurso(TipoRecurso.cozinha,localizacao);
					break;
				case 4:
					pesquisarRecurso(TipoRecurso.centro_trocas,localizacao);
					break;
				case 5:
					pesquisarRecurso(TipoRecurso.banco_alimento,localizacao);
					break;
				
				case 0:
				System.out.println("A sair...");
					break;
    }
		} while (opcao != 0);
}
	private void pesquisarRecurso(TipoRecurso tipo, String localizacao) {
		Session session = sessionFactory.openSession();
		Query<Recursos> query = session.createQuery("FROM Recursos WHERE tipoRecurso = :tipo AND localizacao = :localizacao", Recursos.class);
		query.setParameter("tipo", tipo);
		query.setParameter("localizacao", localizacao);
		List<Recursos> recursos = query.getResultList();
		switch (tipo) {
			case hospital:
				System.out.println("\n" + "----------------------");
				System.out.println("| Lista de hospitais |");
				System.out.println(  "----------------------");

				for (Recursos recurso : recursos) {
					//System.out.println("Id: " + recurso.getIdRecurso());
					System.out.println("Nome: " + recurso.getNome());
					System.out.println("Localização: " + recurso.getLocalizacao());
					System.out.println("Telefone: " + recurso.getTelefone());
					System.out.println("Especialidades: " + ((Hospital) recurso).getEspecialidades());
					System.out.println("Vagas: " + ((Hospital) recurso).getVagas());
					System.out.println("Custos acrescidos: " + ((Hospital) recurso).getCustosAcrescidos());
					System.out.println("Informação extra: " + ((Hospital) recurso).getInformacaoExtra());
					System.out.println("-------------------------------");
				}

				break;
			case abrigo:
				System.out.println("\n" + "-------------------------");
				System.out.println("| Lista de abrigos |");
				System.out.println(  "-------------------------");
				for (Recursos recurso : recursos) {
					//System.out.println("Id: " + recurso.getIdRecurso());
					System.out.println("Nome: " + recurso.getNome());
					System.out.println("Localização: " + recurso.getLocalizacao());
					System.out.println("Telefone: " + recurso.getTelefone());
					System.out.println("Vagas: " + ((Abrigo) recurso).getVagas());
					System.out.println("Custos acrescidos: " + ((Abrigo) recurso).getCustosAcrescidos());
					System.out.println("Informação extra: " + ((Abrigo) recurso).getInformacaoExtra());
					System.out.println("-------------------------------");
				}

				break;
			case cozinha:
				System.out.println("\n" + "-------------------------");
				System.out.println("| Lista de cozinhas comunitárias |");
				System.out.println(  "-------------------------");
				for (Recursos recurso : recursos) {
					//System.out.println("Id: " + recurso.getIdRecurso());
					System.out.println("Nome: " + recurso.getNome());
					System.out.println("Localização: " + recurso.getLocalizacao());
					System.out.println("Telefone: " + recurso.getTelefone());
					System.out.println("Tipo de comida: " + ((CozinhaComunitaria) recurso).getTipoComida());
					System.out.println("Capacidade: " + ((CozinhaComunitaria) recurso).getCapacidade());
					System.out.println("Custos acrescidos: " + ((CozinhaComunitaria) recurso).getCustosAcrescidos());
					System.out.println("Informação extra: " + ((CozinhaComunitaria) recurso).getInformacaoExtra());
					System.out.println("-------------------------------");
				}

				break;
			case centro_trocas:
				System.out.println("\n" + "-------------------------");
				System.out.println("| Lista de centros de trocas |");
				System.out.println(  "-------------------------");
				for (Recursos recurso : recursos) {
					//System.out.println("Id: " + recurso.getIdRecurso());
					System.out.println("Nome: " + recurso.getNome());
					System.out.println("Localização: " + recurso.getLocalizacao());
					System.out.println("Telefone: " + recurso.getTelefone());
					System.out.println("Custos acrescidos: " + ((CentroTrocaRoupa) recurso).getCustosAcrescidos());
					System.out.println("Informação extra: " + ((CentroTrocaRoupa) recurso).getInformacaoExtra());
					System.out.println("-------------------------------");
				}

				break;
			case banco_alimento:
				System.out.println("\n" + "-------------------------");
				System.out.println("| Lista de bancos de alimentos |");
				System.out.println(  "-------------------------");
				for (Recursos recurso : recursos) {
					//System.out.println("Id: " + recurso.getIdRecurso());
					System.out.println("Nome: " + recurso.getNome());
					System.out.println("Localização: " + recurso.getLocalizacao());
					System.out.println("Telefone: " + recurso.getTelefone());
					System.out.println("Custos acrescidos: " + ((BancoAlimentos) recurso).getCustosAcrescidos());
					System.out.println("Informação extra: " + ((BancoAlimentos) recurso).getInformacaoExtra());
					System.out.println("-------------------------------");
				}
				
				break;
			
			default:
				System.out.println("Tipo de recurso inválido.");
				session.getTransaction().rollback();
				session.close();
				return;
		
		
	}
}
}
