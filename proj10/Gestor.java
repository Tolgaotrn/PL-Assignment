package proj10;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import org.hibernate.query.Query;
import com.mysql.cj.xdevapi.Schema;

import jakarta.persistence.Column;

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
 private boolean validarNome(String nome) {
     boolean valid = false;
     if (nome == null || nome.trim().isEmpty()) {
        System.out.println("O nome não pode estar vazio");
        return valid = false;
     }else if (nome.length() < 2) {
         System.out.println("O nome deve ter pelo menos 2 caracteres");
         return valid = false;
     }else if (!nome.matches("^[a-zA-ZÀ-ú\s]+$")) {
         System.out.println("O nome deve conter apenas letras");
         return valid = false;
     }else
     {
         return valid=true;
     }


 }

 private boolean validarUsername(String username) {
     boolean valid = false;
     if (username == null || username.trim().isEmpty()) {
         System.out.println("O username não pode estar vazio");
         return valid = false;
     }else if (username.length() < 3) {
         System.out.println("O username deve ter pelo menos 3 caracteres");
         return valid = false;
     }else if (!username.matches("^[a-zA-Z0-9]+$")) {
         System.out.println("O username deve conter apenas letras e números");
         return valid = false;
     }else
     {
         return valid= true;
     }
 }

    private boolean validarPassword(String password) 
    {
        boolean valid = false;
        if (password == null || password.trim().isEmpty()) {
            System.out.println("A password não pode estar vazia");
            return valid=false;
        }else if (password.length() < 6) {
            System.out.println("A password deve ter pelo menos 6 caracteres");
            return valid=false;
        }else if (!password.matches(".[A-Z].")) {
            System.out.println("A password deve conter pelo menos uma letra maiúscula");
            return valid=false;
        }else if (!password.matches(".[a-z].")) {
            System.out.println("A password deve conter pelo menos uma letra minúscula");
            return valid=false;
        }else if (!password.matches(".[0-9].")) {
            System.out.println("A password deve conter pelo menos um número");
            return valid=false;
        }else 
        {
            return valid=true;
        }

    }

    private boolean validarTelemovel(String telemovel) {
        boolean valid= false;
        if (telemovel == null || telemovel.trim().isEmpty()) {
            System.out.println("O número de telemóvel não pode estar vazio");
            return valid= false;
        }else if (!telemovel.matches("^[0-9]{9}$")) {
            System.out.println("O número de telemóvel deve ter 9 dígitos numéricos");
            return valid= false;
        }else
        {
            return valid = true;
        }
    }

    public void exit() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.getTransaction().commit();
        session.close();
    }

    public void criarUtilizador() {
        String password;
    	String nome;
        String username;
    	String telemovel;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        System.out.println("\n" + "--------------------");
        System.out.println("| Criar utilizador |");
        System.out.println("--------------------" + "\n");
        
        Utilizador u = new Utilizador();
        
        do {
        System.out.print("Nome:");
        nome = scanner.next();
        System.out.print("\n");
        }while(validarNome(nome)==false);
        
        do {
        System.out.print("Username:");
        username = scanner.next();
        System.out.print("\n");
        }while(validarUsername(username)==false);
        do {
            System.out.print("Password:");
            password = scanner.next();
            System.out.print("\n");
         }while(validarPassword(password) == false);
        
        
        do {
        System.out.print("Telemovel:");
        telemovel = scanner.next();
        System.out.print("\n");
        }while(validarTelemovel(telemovel)==false);
        
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
		if (recursos.size() == 0) {
			System.out.println("Nenhum recurso encontrado.");
		} else {
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
					//mostrar recurso default -> ACABAR

					break;
			}
			}
		}
		}
    public void admin() {
		int opcao = -1;
		while (opcao != 0) {
			try {
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
					addDefaultType();
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
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		}
		
    }	
	private void atualizaUser() {
		mostrarUtilizadores();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Utilizador utilizador = new Utilizador();
		try {	//validação de erro ao atualizar utilizador
		System.out.print("Id do utilizador a atualizar: ");
		int id = scanner.nextInt();
		utilizador = session.get(Utilizador.class, id);
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage()); //validação de erro ao inserir id do utilizador
		}
		try {
		System.out.print("Nome: ");
		String nome = scanner.next();
		utilizador.setNome(nome);
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage()); //validação de erro ao atualizar nome do utilizador
		}
		try {
		System.out.print("Telemóvel: ");
		String telemovel = scanner.next();
		utilizador.setTelemovel(telemovel);
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage()); //validação de erro ao atualizar telemovel
		}


		
		session.update(utilizador);
		session.getTransaction().commit();
		System.out.println("Utilizador atualizado com sucesso!");
		session.close();
		}
	private void mostrarUtilizadores() {
		Session session = sessionFactory.openSession();
		Query<Utilizador> query = session.createQuery("FROM Utilizador", Utilizador.class);
		List<Utilizador> utilizadores = query.getResultList();
		if (utilizadores.size() == 0) { //validação se existirem utilizadores na base de dados
			System.out.println("Nenhum utilizador encontrado.");
			
		} else {
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
		

				Recursos recurso = new Recursos();
				recurso.setNome(nome);
				recurso.setTelefone(telefone);
				recurso.setLocalizacao(localizacao);
				recurso.setTipo(tipo);
				session.persist(recurso);
		
				if (recurso.getTipo().getTipo().equals("hospital")) {
					Hospital hospital = new Hospital();
					hospital.setNome(recurso.getNome()); 
					hospital.setTelefone(recurso.getTelefone());
					hospital.setLocalizacao(localizacao);
					hospital.setTipo(tipo);
		
					String especialidades;
                    int vagas;
                    String custosAcrescidos;
                    String informacaoExtra;
                    do {
                        System.out.print("Especialidades: ");
                        especialidades = scanner.next();
                        hospital.setEspecialidades(especialidades);
                        System.out.print("Vagas: ");
                        vagas = scanner.nextInt();
                        hospital.setVagas(vagas);
                        System.out.print("Custos Acrescidos: ");
                        custosAcrescidos = scanner.next();
                        hospital.setCustosAcrescidos(custosAcrescidos);
                        System.out.print("Informação Extra: ");
                        informacaoExtra = scanner.next();
                        hospital.setInformacaoExtra(informacaoExtra);
                    } while (especialidades.length() < 1 || vagas < 0 || custosAcrescidos.length() < 1 || informacaoExtra.length() < 1);
				} else if(recurso.getTipo().getTipo().equals("abrigo")){
					
					int vagas;
					String custosAcrescidos;
					String informacaoExtra;
					do {
						//vagas custos acrescidos informação extra
						Abrigo abrigo = new Abrigo();
						System.out.print("Vagas: ");
						vagas = scanner.nextInt();
						abrigo.setVagas(vagas);
						System.out.print("Custos Acrescidos: ");
						custosAcrescidos = scanner.next();
						abrigo.setCustosAcrescidos(custosAcrescidos);
						System.out.print("Informação Extra: ");
						informacaoExtra = scanner.next();
						abrigo.setInformacaoExtra(informacaoExtra);
						session.persist(abrigo);
					}while (vagas < 0 || custosAcrescidos.length() < 1 || informacaoExtra.length() < 1);
				} else if(recurso.getTipo().getTipo().equals("cozinha")){
					
					String tipoComida;
					int capacidade;
					String custosAcrescidos;
					String informacaoExtra;
					do {
						//tipo de comida capacidade custos acrescidos informação extra
						CozinhaComunitaria cozinha = new CozinhaComunitaria();
						System.out.print("Tipo de Comida: ");
						tipoComida = scanner.next();
						cozinha.setTipoComida(tipoComida);
						System.out.print("Capacidade: ");
						capacidade = scanner.nextInt();
						cozinha.setCapacidade(capacidade);
						System.out.print("Custos Acrescidos: ");
						custosAcrescidos = scanner.next();
						cozinha.setCustosAcrescidos(custosAcrescidos);
						System.out.print("Informação Extra: ");
						informacaoExtra = scanner.next();
						cozinha.setInformacaoExtra(informacaoExtra);
						session.persist(cozinha);
					}while (tipoComida.length() <1 || capacidade < 0 || custosAcrescidos.length() < 1 || informacaoExtra.length() < 1);
				} else if(recurso.getTipo().getTipo().equals("centro")){
					
					String custosAcrescidos;
					String informacaoExtra;
					do {
						//custos acrescidos informação extra
							CentroTrocaRoupa centro = new CentroTrocaRoupa();
							System.out.print("Custos Acrescidos: ");
							custosAcrescidos = scanner.next();
							centro.setCustosAcrescidos(custosAcrescidos);
							System.out.print("Informação Extra: ");
							informacaoExtra = scanner.next();
							centro.setInformacaoExtra(informacaoExtra);
							session.persist(centro);
					 }while (custosAcrescidos.length() < 1 || informacaoExtra.length() < 1);
						 
				} else if (recurso.getTipo().getTipo().equals("default")) {
					
					String name;
					do {
						DefaultType defaultType = new DefaultType();
						System.out.print("Nome: ");
						name = scanner.next();
						defaultType.setName(name);
						defaultType.setTipo(tipo);
						session.persist(defaultType);
					}while (nome.length() < 1);
				}
		
				session.getTransaction().commit();
			
		}			
	private void eliminarRecurso() {
			int id;
			do {
				mostrarRecursos();
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				System.out.print("Id do recurso a eliminar: ");
				id = scanner.nextInt();
				Recursos recurso = session.get(Recursos.class, id);
				session.delete(recurso);
				session.getTransaction().commit();
				System.out.println("Recurso eliminado com sucesso!");
				session.close();
			}while(id < 0);
		}	
    private void editarRecursos() {
    		int id;
    		String nome;
    		String telefone;
    		String cidade;
    		Recursos recurso;
    		do {
    			mostrarRecursos();
    			Session session = sessionFactory.openSession();
    			session.beginTransaction();
    			System.out.print("Id do recurso a editar: ");
    			id = scanner.nextInt();
    			recurso = session.get(Recursos.class, id);
    			System.out.print("Nome: ");
    			nome = scanner.next();
    			recurso.setNome(nome);
    			System.out.print("Telefone: ");
    			telefone = scanner.next();
    			recurso.setTelefone(telefone);
    			System.out.print("Cidade: ");
    			cidade = scanner.next();
    		}while(id < 0 || nome.length() < 1 || telefone.length() < 1 || cidade.length() <1);
    		
			
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
    System.out.println("\n-------------------------");
    System.out.println("| Lista de tipos |");
    System.out.println("-------------------------");
    Session session = sessionFactory.openSession();
    Query<Tipo> queryTipos = session.createQuery("FROM Tipo", Tipo.class);
    List<Tipo> tipos = queryTipos.getResultList();
    for (Tipo tipo : tipos) {
        System.out.println("Id: " + tipo.getIdTipo());
        System.out.println("Tipo: " + tipo.getTipo());
        Query<DefaultType> query5 = session.createQuery("FROM DefaultType", DefaultType.class);
        List<DefaultType> defaultTypes = query5.getResultList();
        if (defaultTypes.size() > 0) {
            System.out.println("Default Types:");
            for (DefaultType defaultType : defaultTypes) {
				System.out.println("Id: " + defaultType.getId());
                System.out.println("Tipo: " + defaultType.getName());
            }
        }
        System.out.println("-------------------------------");
    }
    session.close();
}

public void addDefaultType() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    System.out.print("Tipo: ");
    String type = scanner.next();
    DefaultType defaultType = new DefaultType();
    defaultType.setName(type);
    session.persist(defaultType);	
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
