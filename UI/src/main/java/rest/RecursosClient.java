package rest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Donation;
import model.Favoritos;
import model.Hospital;
import model.Localizacao;
import model.Recursos;
import model.Tipo;
import rest.TipoClient;

public class RecursosClient {
    private RestTemplate restTemplate = new RestTemplate();
	private static final String rootAPIURL = "http://localhost:8080/api/recursos";
	public boolean saveRecurso(Recursos recurso) {

		if (!Objects.isNull(recurso.getIdRecurso()) && recurso.getIdRecurso() != 0) {
			return this.updateRecursos(recurso);
		}

		ResponseEntity<Recursos> response = restTemplate.postForEntity(rootAPIURL, recurso, Recursos.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Recursos body = response.getBody();
			if (body != null) {
				System.out.println(body.toString());
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}

		return response.getStatusCode().is2xxSuccessful();
	}
	public Recursos getRecursosById(int iduser, int id) {

		ResponseEntity<Recursos> response = restTemplate.getForEntity(rootAPIURL + "/" + id + "/user/"+iduser, Recursos.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Recursos body = response.getBody();
			if (body != null) {
				System.out.println(body.toString());
				return body;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}

		return null;
	}
	public boolean updateRecursos(Recursos recursos) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Recursos> requestEntity = new HttpEntity<Recursos>(recursos, headers);
		System.out.println("Recursos: " + recursos);
		ResponseEntity<Recursos> response = restTemplate.exchange(rootAPIURL + "/"+recursos.getIdRecurso(), HttpMethod.PUT, requestEntity, Recursos.class);
		
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Updated");
		} else {
			System.out.println("Nothing found");
		}

		return response.getStatusCode().is2xxSuccessful();
	}
	public List<Recursos> getAllRecursos(int id) {

		ResponseEntity<Recursos[]> response = restTemplate.getForEntity(rootAPIURL + "/user/" + id, Recursos[].class);

		List<Recursos> hospitais = new ArrayList<>();

		if (response.getStatusCode().is2xxSuccessful()) {

			if (response.getBody() != null) {

				for (Recursos h : response.getBody()) {
					hospitais.add(h);
				}
				return hospitais;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return hospitais;

	}
	public List<Recursos> getAllRecursosByLocalizacao(int idUser, int idLocalizacao) {
		//endpoint: /localizacao/{idLocalizacao}
		ResponseEntity<Recursos[]> response = restTemplate.getForEntity(rootAPIURL + "/localizacao/"+ idLocalizacao, Recursos[].class);

		List<Recursos> recursos = new ArrayList<>();

		if (response.getStatusCode().is2xxSuccessful()) {

			if (response.getBody() != null) {

				for (Recursos h : response.getBody()) {
					recursos.add(h);
				}
				return recursos;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return recursos;

	}
	public List<Recursos> getAllRecursosByTipo(int idUser, int idTipo) {
		//endpoint: /tipo/{idTipo}
		ResponseEntity<Recursos[]> response = restTemplate.getForEntity(rootAPIURL +"/tipo/"+ idTipo, Recursos[].class);

		List<Recursos> recursos = new ArrayList<>();

		if (response.getStatusCode().is2xxSuccessful()) {

			if (response.getBody() != null) {

				for (Recursos h : response.getBody()) {
					recursos.add(h);
				}
				return recursos;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return recursos;

	}
	public boolean saveRecursos(Recursos recursos) {

		if (!Objects.isNull(recursos.getIdRecurso()) && recursos.getIdRecurso() != 0) {
			return this.updateRecursos(recursos);
		}

		ResponseEntity<Recursos> response = restTemplate.postForEntity(rootAPIURL, recursos, Recursos.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Recursos body = response.getBody();
			if (body != null) {
				System.out.println(body.toString());
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}

		return response.getStatusCode().is2xxSuccessful();
	}
	public boolean deleteRecursos(int idRecursos, int idUser) {
		FavoritosClient favoritosClient = new FavoritosClient();
		List<Favoritos> favoritos = favoritosClient.getFavoritosByRecurso(idRecursos);
	    if (!favoritos.isEmpty()) {
        //for (Favoritos favorito : favoritos) {
            boolean favoritoDeletado = favoritosClient.deleteFavoritosRecurso(idRecursos);
        //}
   	}
		ResponseEntity<Void> response = restTemplate.exchange(rootAPIURL + "/" + idRecursos + "/user/"+ idUser, HttpMethod.DELETE, null, Void.class);
		
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Deleted");
		} else {
			System.out.println("Nothing found");
		}

		return response.getStatusCode().is2xxSuccessful();

	}
	public void mostrarRecursosByTipo(int iduser) {
		TipoClient tipoService = new TipoClient();
		Tipo tipo = tipoService.escolherTipo();
		List<Recursos> recursos = getAllRecursosByTipo(iduser, tipo.getIdTipo());
		Stage stage = new Stage();
		stage.setTitle("Recursos do Tipo Específico");
		BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(15));
		//mostrar quantos recursos existem do tipo
		Label label = new Label("Existem " + recursos.size()+ " do Tipo: " + tipo.getTipo());
		label.getStyleClass().add("text");
		layout.setTop(label);
		ListView<String> listView = new ListView<>();
		
		if (recursos == null || recursos.isEmpty()) {
			listView.getItems().add("Nenhum recurso disponível para este tipo.");
		} else {
			// Crie uma lista para armazenar os recursos
			for (Recursos r : recursos) {
				String item = "Nome: " + (r.getNome() != null ? r.getNome() : "Nome não disponível") + "\n" +
						"Telefone: " + (r.getTelefone() != null ? r.getTelefone() : "Telefone não disponível") + "\n" +
						"Localização: " + 
						(r.getLocalizacao() != null && r.getLocalizacao().getCidade() != null ? r.getLocalizacao().getCidade() : "Cidade não disponível") + ", " +
						(r.getLocalizacao() != null && r.getLocalizacao().getDistrito() != null ? r.getLocalizacao().getDistrito() : "Distrito não disponível") + "\n";
				listView.getItems().add(item);
			}
	
			// Configurar o evento de clique no item específico da ListView
			listView.setOnMouseClicked(e -> {
				System.out.println("Clicou em um item da lista");
				int selectedIndex = listView.getSelectionModel().getSelectedIndex();
				if (selectedIndex != -1) {
					Recursos recursoSelecionado = recursos.get(selectedIndex);  // Pega o recurso correspondente
					mostrarRecursoEspecifico(recursoSelecionado);
				}
			});
		}
		layout.setCenter(listView);
		Scene scene = new Scene(layout, 400, 300);
		scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	public void mostrarRecursosByLocalizacao(int idUser) {
	LocalizacaoService localizacaoService = new LocalizacaoService();
	Localizacao localizacao = localizacaoService.mostrarLocalizacao();
	List<Recursos> recursos = getAllRecursosByLocalizacao(idUser, localizacao.getIdLocalizacao());
    Stage stage = new Stage();
	
    stage.setTitle("Existem "+ recursos.size()+" recursos na Localização: " + localizacao.getCidade() + ", " + localizacao.getDistrito());
    BorderPane layout = new BorderPane();
    layout.setPadding(new Insets(15));
    ListView<String> listView = new ListView<>();
    if (recursos == null || recursos.isEmpty()) {
        listView.getItems().add("Nenhum recurso disponível nesta localização.");
    } else {
        for (Recursos r : recursos) {
            String item = "Nome: " + (r.getNome() != null ? r.getNome() : "Nome não disponível") + "\n" +
			"Telefone: " + (r.getTelefone() != null ? r.getTelefone() : "Telefone não disponível") + "\n" +
			"Localização: " + 
			(r.getLocalizacao() != null && r.getLocalizacao().getCidade() != null ? r.getLocalizacao().getCidade() : "Cidade não disponível") + ", " +
			(r.getLocalizacao() != null && r.getLocalizacao().getDistrito() != null ? r.getLocalizacao().getDistrito() : "Distrito não disponível") + "\n";
            listView.getItems().add(item);
        }
		// Configurar o evento de clique no item específico da ListView
		listView.setOnMouseClicked(e -> {
			System.out.println("Clicou em um item da lista");
			int selectedIndex = listView.getSelectionModel().getSelectedIndex();
			if (selectedIndex != -1) {
				Recursos recursoSelecionado = recursos.get(selectedIndex);  // Pega o recurso correspondente
				mostrarRecursoEspecifico(recursoSelecionado);
			}
		});
    }
    layout.setCenter(listView);
    Button voltarBtn = new Button("Voltar");
    voltarBtn.setOnAction(event -> stage.close());
    HBox buttonBox = new HBox(10, voltarBtn);
    buttonBox.setAlignment(Pos.CENTER);
    layout.setBottom(buttonBox);
    Scene scene = new Scene(layout, 500, 400);
	scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
    stage.setScene(scene);
    stage.show();
}
	public void mostrarRecursos(int idUser) {
	List<Recursos> recursos = getAllRecursos(idUser);
    Stage stage = new Stage();
    stage.setTitle("Todos os Recursos");
    Text text = new Text("Existem " + recursos.size() + ".");
    text.getStyleClass().add("text");
    BorderPane layout = new BorderPane();
    layout.setPadding(new Insets(15));
	layout.setTop(text);
    ListView<String> listView = new ListView<>();
    if (recursos == null || recursos.isEmpty()) {
        listView.getItems().add("Nenhum recurso disponível.");
    } else {
        for (Recursos r : recursos) {
            String item = "Nome: " + (r.getNome() != null ? r.getNome() : "Nome não disponível") + "\n" +
			"Telefone: " + (r.getTelefone() != null ? r.getTelefone() : "Telefone não disponível") + "\n" +
			"Localização: " + 
			(r.getLocalizacao() != null && r.getLocalizacao().getCidade() != null ? r.getLocalizacao().getCidade() : "Cidade não disponível") + ", " +
			(r.getLocalizacao() != null && r.getLocalizacao().getDistrito() != null ? r.getLocalizacao().getDistrito() : "Distrito não disponível") + "\n";
            listView.getItems().add(item);
        }
		listView.setOnMouseClicked(e -> {
			System.out.println("Clicou em um item da lista");
			int selectedIndex = listView.getSelectionModel().getSelectedIndex();
			if (selectedIndex != -1) {
				Recursos recursoSelecionado = recursos.get(selectedIndex);  // Pega o recurso correspondente
				mostrarRecursoEspecifico(recursoSelecionado);
			}
		});
    }
    layout.setCenter(listView);
    Button voltarBtn = new Button("Voltar");
    voltarBtn.setOnAction(e -> stage.close());
    HBox buttonBox = new HBox(voltarBtn);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setPadding(new Insets(10));
    layout.setBottom(buttonBox);
    Scene scene = new Scene(layout, 500, 400);
	scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
    stage.setScene(scene);
    stage.show();
}
	public void mostrarRecursoEspecifico(Recursos recurso) {
		ListView<String> listView = new ListView<>();
		
		if (recurso == null) {
			listView.getItems().add("Recurso não disponível.");
		} else {
			switch (recurso.getTipo().getTipo()) {
				case "hospital":
					HospitalClient hospitalClient = new HospitalClient();
					int idHospital = recurso.getIdRecurso();
					hospitalClient.mostrarHospital(idHospital);
					break;
				default:
					Stage recursoStage = new Stage();
					recursoStage.setTitle("Informações do recurso");
					VBox vbox = new VBox(10); 
					vbox.setPadding(new javafx.geometry.Insets(20));
					Label nomeLabel = new Label("Nome do recurso: " + recurso.getNome());
					nomeLabel.getStyleClass().add("text");
					Label telefoneLabel = new Label("Telefone: " + recurso.getTelefone());
					Label enderecoLabel = new Label("Distrito: " + recurso.getLocalizacao().getDistrito());
					Label enderecoLabel2 = new Label("Cidade: " + recurso.getLocalizacao().getCidade());
					vbox.getChildren().addAll(nomeLabel, enderecoLabel, telefoneLabel, enderecoLabel2);
					Button voltarButton = new Button("Voltar");
					voltarButton.setOnAction(e -> {
						recursoStage.close();
					});
					vbox.getChildren().addAll(voltarButton);
					Scene recursoScene = new Scene(vbox, 400, 300);
					recursoScene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
					recursoStage.setMinWidth(500);
					recursoStage.setMinHeight(400);
					recursoStage.setScene(recursoScene);
					recursoStage.show();
					break;
			}
		}
	}
	public Recursos selecionarRecurso(int idUtilizador) {
		// Criar um novo Stage
		Stage stage = new Stage();
		stage.setTitle("Selecionar Recurso");
		List<Recursos> recursos = getAllRecursos(idUtilizador);
		//mostrar quantos recursos existem
		Text text = new Text("Existem " + recursos.size() + " recursos.");
		text.getStyleClass().add("text");
		// Layout principal
		BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(15));
		layout.setTop(text);
		// ListView para exibir os recursos
		ListView<String> listView = new ListView<>();
		if (recursos == null || recursos.isEmpty()) {
			listView.getItems().add("Nenhum recurso disponível.");
		} else {
			for (Recursos r : recursos) {
				String item = "Nome: " + (r.getNome() != null ? r.getNome() : "Nome não disponível") + "\n" +
							  "Telefone: " + (r.getTelefone() != null ? r.getTelefone() : "Telefone não disponível") + "\n" +
							  "Localização: " + 
							  (r.getLocalizacao() != null && r.getLocalizacao().getCidade() != null ? r.getLocalizacao().getCidade() : "Cidade não disponível") + ", " +
							  (r.getLocalizacao() != null && r.getLocalizacao().getDistrito() != null ? r.getLocalizacao().getDistrito() : "Distrito não disponível") + "\n";
				listView.getItems().add(item);
			}
		}
	
		// Armazena o recurso selecionado
		final Recursos[] recursoSelecionado = {null};
	
		// Adicionar evento de clique
		listView.setOnMouseClicked(e -> {
			int selectedIndex = listView.getSelectionModel().getSelectedIndex();
			if (selectedIndex != -1 && recursos != null && !recursos.isEmpty()) {
				recursoSelecionado[0] = recursos.get(selectedIndex);
				stage.close(); // Fecha o Stage ao selecionar
			}
		});
	
		layout.setCenter(listView);
		Button voltarBtn = new Button("Voltar");
		voltarBtn.setOnAction(e -> stage.close());
		HBox buttonBox = new HBox(voltarBtn);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPadding(new Insets(10));
		layout.setBottom(buttonBox);
		Scene scene = new Scene(layout, 500, 400);
		scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait(); 
	
		return recursoSelecionado[0];
	}
	public void mostrarRecursoAdmin(int idAdmin) {
		// Obter recursos
		List<Recursos> recursos = getAllRecursos(idAdmin);
	
		Stage stage = new Stage();
		stage.setTitle("Gestão de Recursos");
		//mostrar qiuantos recursos existem
		Text text = new Text("Existem " + recursos.size() + " recursos.");
		text.getStyleClass().add("text");
		BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(10));
		layout.setTop(text);
		// ListView para exibir os recursos
		ListView<String> listView = new ListView<>();
		for (Recursos recurso : recursos) {
			String item = "Id: " + recurso.getIdRecurso() + "\n" +
						  "Nome: " + recurso.getNome() + "\n" +
						  "Telefone: " + recurso.getTelefone() + "\n" +
						  "Localização: " + (recurso.getLocalizacao() != null ? recurso.getLocalizacao().getCidade() : "N/A") + "\n";
			listView.getItems().add(item);
		}
		listView.setPrefSize(400, 300);
		layout.setCenter(listView);
	
		// Botões
		Button eliminarBtn = new Button("Eliminar");
		eliminarBtn.setDisable(true);
	
		Button atualizarBtn = new Button("Atualizar");
		atualizarBtn.setDisable(true);
	
		Button voltarBtn = new Button("Voltar");
	
		Button sairBtn = new Button("Sair");
	
		// Eventos de seleção na ListView
		final Recursos[] selectedRecurso = {null};
		listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				int selectedIndex = listView.getSelectionModel().getSelectedIndex();
				selectedRecurso[0] = recursos.get(selectedIndex);
				eliminarBtn.setDisable(false);
				atualizarBtn.setDisable(false);
			} else {
				eliminarBtn.setDisable(true);
				atualizarBtn.setDisable(true);
			}
		});
	
		// Evento para o botão Eliminar
		eliminarBtn.setOnAction(e -> {
			if (selectedRecurso[0] != null) {
				int idRecurso = selectedRecurso[0].getIdRecurso();
	
				// Lógica de remoção do recurso
				RecursosClient recursosClient = new RecursosClient();
				recursosClient.deleteRecursos(idRecurso, idAdmin);
	
				// Remover o recurso da lista e atualizar a ListView
				recursos.remove(selectedRecurso[0]);
				listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
				selectedRecurso[0] = null;
				eliminarBtn.setDisable(true);
				atualizarBtn.setDisable(true);
			}
		});
	
		// Evento para o botão Atualizar
		atualizarBtn.setOnAction(e -> {
			if (selectedRecurso[0] != null) {
				// Chamar método para atualizar o recurso
				//atualizarRecurso(selectedRecurso[0]);
			}
		});
	
		// Evento para o botão Voltar
		voltarBtn.setOnAction(e -> stage.close());
	
		// Evento para o botão Sair
		sairBtn.setOnAction(e -> System.exit(0));
	
		// Layout para os botões
		HBox buttonBox = new HBox(10, eliminarBtn, atualizarBtn, voltarBtn, sairBtn);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPadding(new Insets(10));
		layout.setBottom(buttonBox);
	
		Scene scene = new Scene(layout, 500, 400);
		scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
    public void mostrarTelaAtualizarVagas(Hospital recursoSelecionado) {
    // Criar uma nova janela
    Stage stage = new Stage();
    stage.setTitle("Atualizar Vagas");

    // Layout principal
    BorderPane layout = new BorderPane();
    layout.setPadding(new Insets(15));

    if (recursoSelecionado == null) {
        // Mensagem caso nenhum recurso seja selecionado
        Label mensagem = new Label("Nenhum recurso selecionado para atualizar vagas.");
        layout.setCenter(mensagem);
    } else {
        // Layout do formulário
        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(15));

        // Título
        Label titulo = new Label("Atualizar Vagas");
        titulo.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 16));

        // Nome do recurso
        Label nomeRecurso = new Label("Recurso: " + recursoSelecionado.getNome());

        // Campo para inserir o número de vagas
        Label labelVagas = new Label("Vagas:");
        TextField vagasField = new TextField();

        // Botão para guardar
        Button guardarBtn = new Button("Guardar");
        guardarBtn.setDisable(true); // Desabilitado inicialmente

        // Habilitar o botão apenas quando o campo de vagas não estiver vazio
        vagasField.textProperty().addListener((observable, oldValue, newValue) -> {
            guardarBtn.setDisable(newValue.trim().isEmpty());
        });

        // Ação do botão "Guardar"
        guardarBtn.setOnAction(e -> {
                int vagas = Integer.parseInt(vagasField.getText().trim());
                recursoSelecionado.setVagas(vagas);
				HospitalClient hospitalClient = new HospitalClient();
				hospitalClient.updateHospital(recursoSelecionado);
                System.out.println("Número de vagas atualizado para: " + vagas);
				//notificar utilizadores com favoritos
				if (vagas<=10){
					NotificacaoClient notificacaoClient = new NotificacaoClient();
					notificacaoClient.notificarFavoritos(recursoSelecionado);
				}
                stage.close();
            
        });

        // Botão para cancelar
        Button cancelarBtn = new Button("Cancelar");
        cancelarBtn.setOnAction(e -> stage.close());

        // Layout para os botões
        HBox botoes = new HBox(10, guardarBtn, cancelarBtn);
        botoes.setAlignment(Pos.CENTER);

        // Adicionar os elementos ao layout do formulário
        formLayout.getChildren().addAll(titulo, nomeRecurso, labelVagas, vagasField, botoes);
        layout.setCenter(formLayout);
    }
    Scene scene = new Scene(layout, 400, 300);
    scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
    stage.setScene(scene);
    stage.show();
	}
	public void mostrarTelaAtualizarRecurso(Recursos recurso) {
		Stage stage = new Stage();
		stage.setTitle("Atualizar Recurso");
	
		// Layout principal
		VBox root = new VBox(10);
		root.setStyle("-fx-padding: 20; -fx-spacing: 10;");
	
		// Campos de entrada de texto
		TextField txtNome = new TextField(recurso.getNome());
		txtNome.setPromptText("Nome");
	
		TextField txtTelefone = new TextField(recurso.getTelefone());
		txtTelefone.setPromptText("Telefone");
	
		TextField txtCidade = new TextField(recurso.getLocalizacao().getCidade());
		txtCidade.setPromptText("Cidade");
	
		TextField txtDistrito = new TextField(recurso.getLocalizacao().getDistrito());
		txtDistrito.setPromptText("Distrito");
	
		TextField txtTipo = new TextField(recurso.getTipo().getTipo());
		txtTipo.setPromptText("Tipo");
		Button btnGuardar = new Button("Guardar");
		btnGuardar.setDisable(false);
	
		Button btnSair = new Button("Sair");
	
		btnGuardar.setOnAction(event -> {
			if (!txtNome.getText().isEmpty()) {
				recurso.setNome(txtNome.getText());
			}
			if (!txtTelefone.getText().isEmpty()) {
				recurso.setTelefone(txtTelefone.getText());
			}
			if (!txtCidade.getText().isEmpty()) {
				recurso.getLocalizacao().setCidade(txtCidade.getText());
			}
			if (!txtDistrito.getText().isEmpty()) {
				recurso.getLocalizacao().setDistrito(txtDistrito.getText());
			}
			if (!txtTipo.getText().isEmpty()) {
				recurso.getTipo().setTipo(txtTipo.getText());
			}
			RecursosClient recursosClient = new RecursosClient();
			recursosClient.updateRecursos(recurso);
			System.out.println("Recurso atualizado: " + recurso);
			stage.close();
		});
		btnSair.setOnAction(event -> stage.close());
		HBox buttonBox = new HBox(10, btnGuardar, btnSair);
		buttonBox.setStyle("-fx-alignment: center;");
		root.getChildren().addAll(new Label("Atualizar Recurso"), txtNome, txtTelefone, txtCidade, txtDistrito, txtTipo, buttonBox);
		Scene scene = new Scene(root, 400, 300);
		scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	public Recursos criaRecursos() {
		final Recursos[] novoRecurso = {null};
		Stage stage = new Stage();
		stage.setTitle("Criar Novo Recurso");

		// Layout principal
		VBox root = new VBox(10);
		root.setStyle("-fx-padding: 20; -fx-spacing: 10;");

		// Campos de entrada de texto
		TextField txtNome = new TextField();
		txtNome.setPromptText("Nome");

		TextField txtTelefone = new TextField();
		txtTelefone.setPromptText("Telefone");

		// ListView para selecionar Tipo
		TipoClient tipoClient = new TipoClient();
		List<Tipo> tipos = tipoClient.getAllTipo();
		ListView<String> tipoListView = new ListView<>();
		for (Tipo tipo : tipos) {
			tipoListView.getItems().add(tipo.getTipo());
		}

		// ListView para selecionar Localizacao
		LocalizacaoService localizacaoService = new LocalizacaoService();
		List<Localizacao> localizacoes = localizacaoService.getAllLocalizacao();
		ListView<String> localizacaoListView = new ListView<>();
		for (Localizacao localizacao : localizacoes) {
			localizacaoListView.getItems().add(localizacao.getCidade() + ", " + localizacao.getDistrito());
		}

		Button btnGuardar = new Button("Guardar");
		Button btnVoltar = new Button("Voltar");


		btnGuardar.setOnAction(event -> {
			String nome = txtNome.getText();
			String telefone = txtTelefone.getText();
			Tipo tipoSelecionado = tipos.get(tipoListView.getSelectionModel().getSelectedIndex());
			Localizacao localizacaoSelecionada = localizacoes.get(localizacaoListView.getSelectionModel().getSelectedIndex());
			novoRecurso[0] = new Recursos(nome, telefone, localizacaoSelecionada, tipoSelecionado);
			stage.close();
		});
		btnVoltar.setOnAction(event -> stage.close());
		HBox buttonBox = new HBox(10, btnGuardar, btnVoltar);
		buttonBox.setStyle("-fx-alignment: center;");
		root.getChildren().addAll(
			new Label("Criar Novo Recurso"),
			txtNome,
			txtTelefone,
			new Label("Selecionar Tipo"),
			tipoListView,
			new Label("Selecionar Localização"),
			localizacaoListView,
			buttonBox
		);
		Scene scene = new Scene(root, 400, 500);
		scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		stage.showAndWait();
		return novoRecurso[0];
	}
	public void adicionarRecurso(Recursos novoRecurso) {
		System.out.println("Tipo de recurso: " + novoRecurso.getTipo().getTipo());
		switch (novoRecurso.getTipo().getTipo()) {
			case "hospital":
				Hospital novoHospital = new Hospital();
				novoHospital.setNome(novoRecurso.getNome());
				novoHospital.setTelefone(novoRecurso.getTelefone());
				novoHospital.setLocalizacao(novoRecurso.getLocalizacao());

				Stage hospitalStage = new Stage();
				hospitalStage.setTitle("Criar Novo Hospital");

				VBox hospitalRoot = new VBox(10);
				hospitalRoot.setStyle("-fx-padding: 20; -fx-spacing: 10;");

				TextField txtEspecialidades = new TextField();
				txtEspecialidades.setPromptText("Especialidades");

				TextField txtVagas = new TextField();
				txtVagas.setPromptText("Número de Vagas");

				TextField txtCustosAcrescidos = new TextField();
				txtCustosAcrescidos.setPromptText("Custos Acrescidos");

				TextField txtInformacaoExtra = new TextField();
				txtInformacaoExtra.setPromptText("Informação Extra");

				Button btnGuardarHospital = new Button("Guardar Hospital");
				btnGuardarHospital.setDisable(false);

				Button btnVoltarHospital = new Button("Voltar");

				btnGuardarHospital.setOnAction(event -> {
						String especialidades = txtEspecialidades.getText().trim();
						int vagas = Integer.parseInt(txtVagas.getText().trim());
						String custosAcrescidos = txtCustosAcrescidos.getText().trim();
						String informacaoExtra = txtInformacaoExtra.getText().trim();
						novoHospital.setTipo(novoRecurso.getTipo());
						novoHospital.setEspecialidades(especialidades);
						novoHospital.setVagas(vagas);
						novoHospital.setCustosAcrescidos(custosAcrescidos);
						novoHospital.setInformacaoExtra(informacaoExtra);

						
						if (novoHospital.getIdRecurso()!=novoRecurso.getIdRecurso()) {
						} else{
							HospitalClient hospitalClient = new HospitalClient();
							hospitalClient.saveHospital(novoHospital);
						}
						hospitalStage.close();
					
				});
				btnVoltarHospital.setOnAction(event -> hospitalStage.close());
				HBox hospitalButtonBox = new HBox(10, btnGuardarHospital, btnVoltarHospital);
				hospitalButtonBox.setStyle("-fx-alignment: center;");
				hospitalRoot.getChildren().addAll(
					new Label("Criar Novo Hospital"),
					txtEspecialidades,
					txtVagas,
					txtCustosAcrescidos,
					txtInformacaoExtra,
					hospitalButtonBox);
				Scene hospitalScene = new Scene(hospitalRoot, 400, 400);
				hospitalScene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
				hospitalStage.setScene(hospitalScene);
				hospitalStage.showAndWait();
			break;
			default:
				System.out.println("Tipo de recurso não suportado.");}
		if (novoRecurso != null) {
			System.out.println("Recurso criado com sucesso!");
		}
	}
	public List <Recursos> getAllRecursosByTipoLocalizacao(int idUser, Tipo tipo, Localizacao localizacao) {
		//BASE_URL + "/search?tipoId=" + tipo.getIdTipo() + "&localizacaoId=" + localizacao.getIdLocalizacao();
		ResponseEntity<Recursos[]> response = restTemplate.getForEntity(rootAPIURL + "/search?tipoId=" + tipo.getIdTipo() + "&localizacaoId=" + localizacao.getIdLocalizacao(), Recursos[].class);
		List<Recursos> hospitais = new ArrayList<>();

		if (response.getStatusCode().is2xxSuccessful()) {

			if (response.getBody() != null) {

				for (Recursos h : response.getBody()) {
					hospitais.add(h);
				}
				return hospitais;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return hospitais;

	}
	public void mostrarRecursosByTipoELocalizacao(int idUtilizador, Tipo tipo, Localizacao localizacao) {
		//mostar recursos por tipo e localizacao
		List<Recursos> recursos = getAllRecursosByTipoLocalizacao(idUtilizador, tipo, localizacao);
		Stage stage = new Stage();
		stage.setTitle("Recursos do Tipo e Localização Específicos");
		BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(15));
		//mostrar quantos recursos existem do tipo e localizacao
		Label label = new Label("Existem " + recursos.size()+ " do Tipo: " + tipo.getTipo() + " na Localização: " + localizacao.getCidade() + ", " + localizacao.getDistrito());
		label.getStyleClass().add("text");
		layout.setTop(label);
		ListView<String> listView = new ListView<>();
		listView.setPrefSize(400, 300);
		
		if (recursos == null || recursos.isEmpty()) {
			listView.getItems().add("Nenhum recurso disponível para este tipo e localização.");
		} else {
			for (Recursos r : recursos) {
				String item = "Nome: " + (r.getNome() != null ? r.getNome() : "Nome não disponível") + "\n" +
						"Telefone: " + (r.getTelefone() != null ? r.getTelefone() : "Telefone não disponível") + "\n" +
						"Localização: " + 
						(r.getLocalizacao() != null && r.getLocalizacao().getCidade() != null ? r.getLocalizacao().getCidade() : "Cidade não disponível") + ", " +
						(r.getLocalizacao() != null && r.getLocalizacao().getDistrito() != null ? r.getLocalizacao().getDistrito() : "Distrito não disponível") + "\n";
				listView.getItems().add(item);
			}
			listView.setOnMouseClicked(e -> {
				System.out.println("Clicou em um item da lista");
				int selectedIndex = listView.getSelectionModel().getSelectedIndex();
				if (selectedIndex != -1) {
					Recursos recursoSelecionado = recursos.get(selectedIndex);
					mostrarRecursoEspecifico(recursoSelecionado);
				}
			});
		}
		layout.setCenter(listView);
		Scene scene = new Scene(layout, 400, 300);
		scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		}
}

	
