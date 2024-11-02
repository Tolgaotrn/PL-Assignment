package proj10;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import org.hibernate.query.Query;
import com.mysql.cj.xdevapi.Schema;
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
 * tabela de recursos ligar à tabela de favoritos através de uma relação 1:m, pois um recurso pode ser favorito de vários utilizadores (FEITO)
 * tabela de utilizadores ligar à tabela de favoritos através de uma relação 1:m, pois um utilizador pode ter vários favoritos (FEITO)
 * recursos 1:m----> favoritos <----1:m utilizadores (FEITO)
 * ADICIONAR FAVORITOS (FEITO)
 * ALTERAR NEXT PARA NEXTLINE, POIS OS INPUTS SE TIVEREM ESPAÇOS NÃO FUNCIONAM (FEITO)
 * susbtituir por * (FEITO)
 * lista de localiações numa tabela diferente e outra (FEITO)
 * tipo passar para tabela diferente e adicionar os CRUD para o tipo (FEITO)
 * ADICIONAR OUTRO TIPO (BASE DE DADOS E JAVA) COM ATRIBUTOS DEFAULT PARA CASO OUTRO TIPO DE RECURSO SEJA ADICIONADO
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
	private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode estar vazio");
        }
        if (nome.length() < 2) {
            throw new IllegalArgumentException("O nome deve ter pelo menos 2 caracteres");
        }
        if (!nome.matches("^[a-zA-ZÀ-ú\\s]+$")) {
            throw new IllegalArgumentException("O nome deve conter apenas letras");
        }
    }

    private void validarUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("O username não pode estar vazio");
        }
        if (username.length() < 3) {
            throw new IllegalArgumentException("O username deve ter pelo menos 3 caracteres");
        }
        if (!username.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("O username deve conter apenas letras e números");
        }
    }

    private void validarPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("A password não pode estar vazia");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("A password deve ter pelo menos 6 caracteres");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("A password deve conter pelo menos uma letra maiúscula");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("A password deve conter pelo menos uma letra minúscula");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("A password deve conter pelo menos um número");
        }
    }

    private void validarTelemovel(String telemovel) {
        if (telemovel == null || telemovel.trim().isEmpty()) {
            throw new IllegalArgumentException("O número de telemóvel não pode estar vazio");
        }
        if (!telemovel.matches("^[0-9]{9}$")) {
            throw new IllegalArgumentException("O número de telemóvel deve ter 9 dígitos numéricos");
        }
    }

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
        System.out.println("--------------------" + "\n");
        
        Utilizador u = new Utilizador();
        
        System.out.print("Nome:");
        String nome = scanner.next();
        validarNome(nome);
        System.out.print("\n");
    
        System.out.print("Username:");
        String username = scanner.next();
        validarUsername(username);
        System.out.print("\n");
        
        System.out.print("Password:");
        String password = scanner.next();
        validarPassword(password);
        System.out.print("\n");
        
        System.out.print("Telemovel:");
        String telemovel = scanner.next();
        validarTelemovel(telemovel);
        System.out.print("\n");

        u.setNome(nome);
        u.setUsername(username);
        u.setPassword(encriptar(password));
        u.setTelemovel(telemovel);
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
			System.out.println("Cidade: " + recurso.getLocalizacao().getCidade() + "\nDistrito: " + recurso.getLocalizacao().getDistrito());
			System.out.println("Telefone: " + recurso.getTelefone());
			System.out.println("-------------------------------");
			
			switch (recurso.getTipo().getTipo()) {
				case "hospital":
					//select * from hospital where id_recurso = recurso.getIdRecurso();
					Query<Hospital> queryHospital = session.createQuery("FROM Hospital WHERE idRecurso = :idRecurso", Hospital.class);
					queryHospital.setParameter("idRecurso", recurso.getIdRecurso());
					List<Hospital> hospitais = queryHospital.getResultList();
					for (Hospital hospital : hospitais) {
						System.out.println("Tipo: " + recurso.getTipo().getTipo());
						System.out.println("Especialidade: " + hospital.getEspecialidades());
						System.out.println("Vagas: " + hospital.getVagas());
						System.out.println("Custos Acrescidos: " + hospital.getCustosAcrescidos());
						System.out.println("Informação Extra: " + hospital.getInformacaoExtra());
						System.out.println("-------------------------------");
					}
					break;
				case "abrigo":
					Query<Abrigo> queryAbrigo = session.createQuery("FROM Abrigo WHERE idRecurso = :idRecurso", Abrigo.class);
					queryAbrigo.setParameter("idRecurso", recurso.getIdRecurso());
					List<Abrigo> abrigos = queryAbrigo.getResultList();
					for (Abrigo abrigo : abrigos) {
						System.out.println("Tipo: " + recurso.getTipo().getTipo());
						System.out.println("Vagas: " + abrigo.getVagas());
						System.out.println("Custos Acrescidos: " + abrigo.getCustosAcrescidos());
						System.out.println("Informação Extra: " + abrigo.getInformacaoExtra());
						System.out.println("-------------------------------");
					}
				case "banco":
					Query<BancoAlimentos> queryBanco = session.createQuery("FROM Banco WHERE idRecurso = :idRecurso", BancoAlimentos.class);
					queryBanco.setParameter("idRecurso", recurso.getIdRecurso());
					List<BancoAlimentos> bancos = queryBanco.getResultList();
					for (BancoAlimentos banco : bancos) {
						System.out.println("Tipo: " + recurso.getTipo().getTipo());
						System.out.println("Tipo de Comida: " + banco.getTiposComidaDisponivel());
						System.out.println("Custos Acrescidos: " + banco.getCustosAcrescidos());
						System.out.println("Informação Extra: " + banco.getInformacaoExtra());
						System.out.println("-------------------------------");
					}
					break;
				case "centro":
					Query<CentroTrocaRoupa> queryCentro = session.createQuery("FROM Centro WHERE idRecurso = :idRecurso", CentroTrocaRoupa.class);
					queryCentro.setParameter("idRecurso", recurso.getIdRecurso());
					List<CentroTrocaRoupa> centros = queryCentro.getResultList();
					for (CentroTrocaRoupa centro : centros) {
						System.out.println("Tipo: " + recurso.getTipo().getTipo());
						System.out.println("Custos Acrescidos: " + centro.getCustosAcrescidos());
						System.out.println("Informação Extra: " + centro.getInformacaoExtra());
						System.out.println("-------------------------------");
					}
					break;
				case "cozinha":
					Query<CozinhaComunitaria> queryCozinha = session.createQuery("FROM Cozinha WHERE idRecurso = :idRecurso", CozinhaComunitaria.class);
					queryCozinha.setParameter("idRecurso", recurso.getIdRecurso());
					List<CozinhaComunitaria> cozinhas = queryCozinha.getResultList();
					for (CozinhaComunitaria cozinha : cozinhas) {
						System.out.println("Tipo: " + recurso.getTipo().getTipo());
						System.out.println("Tipo de Comida: " + cozinha.getTipoComida());
						System.out.println("Capacidade: " + cozinha.getCapacidade());
						System.out.println("Custos Acrescidos: " + cozinha.getCustosAcrescidos());
						System.out.println("Informação Extra: " + cozinha.getInformacaoExtra());
						System.out.println("-------------------------------");
					}
					break;
				default:
					break;
			}
			}	
		}
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
			System.out.println("7 - Ver tipos");
			System.out.println("8 - Adicionar tipo");
			System.out.println("9 - Eliminar tipo");
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
				case 7:
					mostrarTipo();
					break;
				case 8:
					adicionarTipo();
					break;
				case 9:
					eliminarTipo();
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
				System.out.println("\n" + "Tipo de recurso:");
				mostrarTipo();
				System.out.print("Id do tipo de recurso: ");
				int idTipo = scanner.nextInt();
				Tipo tipo = session.get(Tipo.class, idTipo);
		
				System.out.print("Nome: ");
				String nome = scanner.next();
				System.out.print("Telefone: ");
				String telefone = scanner.next();
				System.out.print("Cidade: ");
				String cidade = scanner.next();
		
				// Verificar se a cidade existe na base de dados
				Query<Localizacao> query = session.createQuery("FROM Localizacao WHERE cidade = :cidade", Localizacao.class);
				query.setParameter("cidade", cidade);
				List<Localizacao> localizacoes = query.getResultList();
				Localizacao localizacao;
		
				if (localizacoes.size() == 1) {
					localizacao = localizacoes.get(0);
				} else {
					localizacao = new Localizacao();
					localizacao.setCidade(cidade);
					System.out.print("Distrito: ");
					String distrito = scanner.next();
					localizacao.setDistrito(distrito);
					session.persist(localizacao);
				}
		
				// Criando e persistindo o recurso
				Recursos recurso = new Recursos();
				recurso.setNome(nome);
				recurso.setTelefone(telefone);
				recurso.setLocalizacao(localizacao);
				recurso.setTipo(tipo);
				session.persist(recurso); // Persistindo o recurso primeiro
		
				// Criando um hospital se o tipo for hospital
				if (recurso.getTipo().getTipo().equals("hospital")) {
					Hospital hospital = new Hospital();
					hospital.setNome(recurso.getNome()); // Copiando o nome
					hospital.setTelefone(recurso.getTelefone()); // Copiando o telefone
					hospital.setLocalizacao(localizacao); // Associe a mesma localização
					hospital.setTipo(tipo); // Defina o tipo aqui
		
					System.out.print("Especialidades: ");
					String especialidades = scanner.next();
					hospital.setEspecialidades(especialidades);
					System.out.print("Vagas: ");
					int vagas = scanner.nextInt();
					hospital.setVagas(vagas);
					System.out.print("Custos Acrescidos: ");
					String custosAcrescidos = scanner.next();
					hospital.setCustosAcrescidos(custosAcrescidos);
					System.out.print("Informação Extra: ");
					String informacaoExtra = scanner.next();
					hospital.setInformacaoExtra(informacaoExtra);
					session.persist(hospital); // Persistindo o hospital
				} 
				// Continue da mesma forma para abrigo, banco de alimentos etc.
		
				session.getTransaction().commit();
			
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
			System.out.print("Nome: ");
			String nome = scanner.next();
			recurso.setNome(nome);
			System.out.print("Telefone: ");
			String telefone = scanner.next();
			recurso.setTelefone(telefone);
			System.out.print("Cidade: ");
			String cidade = scanner.next();
			//verificar se cidade existe na base de dados
			Query<Localizacao> query = session.createQuery("FROM Localizacao WHERE cidade = :cidade", Localizacao.class);
			query.setParameter("cidade", cidade);
			List<Localizacao> localizacoes = query.getResultList();
			Localizacao localizacao;
			if (localizacoes.size() == 1) {
				localizacao = localizacoes.get(0);
			} else {
				localizacao = new Localizacao();
				localizacao.setCidade(cidade);
				System.out.print("Distrito: ");
				String distrito = scanner.next();
				localizacao.setDistrito(distrito);
				session.persist(localizacao);
				
			}
			recurso.setLocalizacao(localizacao);
			if (recurso.getTipo().getTipo().equals("hospital")) {
				Hospital hospital = session.get(Hospital.class, id);
				System.out.print("Especialidades: ");
				String especialidades = scanner.next();
				hospital.setEspecialidades(especialidades);
				System.out.print("Vagas: ");
				int vagas = scanner.nextInt();
				hospital.setVagas(vagas);
				System.out.print("Custos Acrescidos: ");
				String custosAcrescidos = scanner.next();
				hospital.setCustosAcrescidos(custosAcrescidos);
				System.out.print("Informação Extra: ");
				String informacaoExtra = scanner.next();
				hospital.setInformacaoExtra(informacaoExtra);
			} else if (recurso.getTipo().getTipo().equals("abrigo")) {
				Abrigo abrigo = session.get(Abrigo.class, id);
				System.out.print("Vagas: ");
				int vagas = scanner.nextInt();
				abrigo.setVagas(vagas);
				System.out.print("Custos Acrescidos: ");
				String custosAcrescidos = scanner.next();
				abrigo.setCustosAcrescidos(custosAcrescidos);
				System.out.print("Informação Extra: ");
				String informacaoExtra = scanner.next();
				abrigo.setInformacaoExtra(informacaoExtra);
			} else if (recurso.getTipo().getTipo().equals("banco")) {
				BancoAlimentos banco = session.get(BancoAlimentos.class, id);
				System.out.print("Tipo de Comida: ");
				String tiposComidaDisponivel = scanner.next();
				banco.setTiposComidaDisponivel(tiposComidaDisponivel);
				System.out.print("Custos Acrescidos: ");
				String custosAcrescidos = scanner.next();
				banco.setCustosAcrescidos(custosAcrescidos);
				System.out.print("Informação Extra: ");
				String informacaoExtra = scanner.next();
				banco.setInformacaoExtra(informacaoExtra);
			} else if (recurso.getTipo().getTipo().equals("centro")) {
				CentroTrocaRoupa centro = session.get(CentroTrocaRoupa.class, id);
				System.out.print("Custos Acrescidos: ");
				String custosAcrescidos = scanner.next();
				centro.setCustosAcrescidos(custosAcrescidos);
				System.out.print("Informação Extra: ");
				String informacaoExtra = scanner.next();
				centro.setInformacaoExtra(informacaoExtra);
			} else if (recurso.getTipo().getTipo().equals("cozinha")) {
				CozinhaComunitaria cozinha = session.get(CozinhaComunitaria.class, id);
				System.out.print("Tipo de Comida: ");
				String tipoComida = scanner.next();
				cozinha.setTipoComida(tipoComida);
				System.out.print("Capacidade: ");
				int capacidade = scanner.nextInt();
				cozinha.setCapacidade(capacidade);
				System.out.print("Custos Acrescidos: ");
				String custosAcrescidos = scanner.next();
				cozinha.setCustosAcrescidos(custosAcrescidos);
				System.out.print("Informação Extra: ");
				String informacaoExtra = scanner.next();
				cozinha.setInformacaoExtra(informacaoExtra);
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
			
			if (utilizador.getTipoUtilizador().equals("administrador")) {
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
				pesquisarRecurso();
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
		mostrarRecursos(); // Método que mostra todos os recursos
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.print("Id do recurso a adicionar a favoritos: ");
		int idRecurso = scanner.nextInt();
	
		Favorito favorito = new Favorito();
		favorito.setIdUtilizador(idUtilizador);
		favorito.setIdRecurso(idRecurso);
		favorito.setDataFavorito(new java.util.Date());
	
		session.persist(favorito);
		session.getTransaction().commit(); // Comita a transação
		session.close(); // Fecha a sessão
	
		System.out.println("Recurso adicionado aos favoritos com sucesso!");
	}
	
	public void menuAnonimo() {
        System.out.println("\n" + "--------------------");
			System.out.println("| Menu anónimo |");
			System.out.println(  "--------------------");
			pesquisarRecurso();
    
}
private void pesquisarRecurso() {
    System.out.print("Tipo de recurso: ");
    mostrarTipo();
    System.out.print("Id do tipo de recurso: ");
    int idTipo = scanner.nextInt();
    System.out.print("Cidade: ");
    String cidade = scanner.next();
	Session session = sessionFactory.openSession();
	Query<Recursos> query = session.createQuery("FROM Recursos WHERE tipo.id_tipo = :idTipo AND localizacao.cidade = :cidade", Recursos.class);
		query.setParameter("idTipo", idTipo);
		query.setParameter("cidade", cidade);
    List<Recursos> recursos = query.getResultList();

    if (recursos.size() == 0) {
        System.out.println("Nenhum recurso encontrado.");
    } else {
        System.out.println("\n" + "-------------------------");
        System.out.println("| Lista de recursos |");
        System.out.println("-------------------------");
        for (Recursos recurso : recursos) {
            System.out.println("Nome: " + recurso.getNome());
            System.out.println("Telefone: " + recurso.getTelefone());
			System.out.println("Cidade: " + recurso.getLocalizacao().getCidade());
			System.out.println("Distrito: " + recurso.getLocalizacao().getDistrito());
			if (recurso instanceof Hospital) {
				Hospital hospital = (Hospital) recurso; 
				System.out.println("Especialidades: " + hospital.getEspecialidades());
				System.out.println("Vagas: " + hospital.getVagas());
				System.out.println("Custos Acrescidos: " + hospital.getCustosAcrescidos());
				System.out.println("Informação Extra: " + hospital.getInformacaoExtra());
				System.out.println("-------------------------------");
			}
        }
    }
    
    System.out.println("\n" + "-------------------------");
}

public void mostrarTipo() {
	System.out.println("\n" + "-------------------------");
	System.out.println("| Lista de tipos |");
	System.out.println(  "-------------------------");
	Session session = sessionFactory.openSession();
	Query<Tipo> query = session.createQuery("FROM Tipo", Tipo.class);
	List<Tipo> tipos = query.getResultList();
	for (Tipo tipo : tipos) {
		System.out.println("Id: " + tipo.getIdTipo());
		System.out.println("Tipo: " + tipo.getTipo());
		System.out.println("-------------------------------");
	}
	session.close();
			
}
public void adicionarTipo() {
	Session session = sessionFactory.openSession();
	session.beginTransaction();
	System.out.print("Tipo: ");
	String tipo = scanner.next();
	Tipo t = new Tipo();
	t.setTipo(tipo);
	session.persist(t);	
	session.getTransaction().commit();
	session.close();
}
public void eliminarTipo() {
	mostrarTipo();
	Session session = sessionFactory.openSession();
	session.beginTransaction();
	System.out.print("Id do tipo a eliminar: ");
	int id = scanner.nextInt();
	Tipo tipo = session.get(Tipo.class, id);
	session.delete(tipo);
	session.getTransaction().commit();
	System.out.println("Tipo eliminado com sucesso!");
	session.close();
}
}
