package rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import model.Favoritos;
import model.Hospital;
import model.Localizacao;
import model.Notificacoes;
import model.Tipo;
import model.Utilizador;

public class UtilizadorService {
	private RestTemplate restTemplate = new RestTemplate();
	private static final String rootAPIURL = "http://localhost:8080/api/utilizadores";
	public List<Utilizador> getAllUtilizador() {

		ResponseEntity<Utilizador[]> response = restTemplate.getForEntity(rootAPIURL, Utilizador[].class);

		List<Utilizador> users = new ArrayList<>();

		if (response.getStatusCode().is2xxSuccessful()) {

			if (response.getBody() != null) {

				for (Utilizador u : response.getBody()) {
					users.add(u);
				}
				return users;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return users;

	}
    public Utilizador selecionarUtilizador() {
        List<Utilizador> users = getAllUtilizador();
    
        Stage stage = new Stage();
        stage.setTitle("Selecionar Utilizador");
    
        ListView<String> listView = new ListView<>();
        for (Utilizador user : users) {
            String item = "Id: " + user.getIdUtilizador() + "\n" +
                          "Nome: " + user.getNome() + "\n" +
                          "Telemovel: " + user.getTelemovel() + "\n";
            listView.getItems().add(item);
        }
    
        listView.setPrefSize(400, 300);
    
        final Utilizador[] selectedUser = {null};
    
        listView.setOnMouseClicked(event -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                selectedUser[0] = users.get(selectedIndex);
                stage.close();
            }
        });
    
        Scene scene = new Scene(listView);
        scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    
        return selectedUser[0];
    }
    public void mostrarUtilizadores() {
        List<Utilizador> users = getAllUtilizador();
    
        Stage stage = new Stage();
        stage.setTitle("Lista de Utilizadores");
    
        // Criar um layout para adicionar os componentes
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));
    
        // Mostrar numa label quantos utilizadores existem
        Text text = new Text("Existem " + users.size() + " utilizadores.");
        text.getStyleClass().add("text");
    
        // Adicionar a label ao layout
        layout.getChildren().add(text);
    
        // Adicionar a ListView ao layout
        ListView<String> listView = new ListView<>();
        for (Utilizador user : users) {
            String item = "Id: " + user.getIdUtilizador() + "\n" +
                          "Nome: " + user.getNome() + "\n" +
                          "Telemovel: " + user.getTelemovel() + "\n";
            listView.getItems().add(item);
        }
        listView.setPrefSize(400, 300);
        layout.getChildren().add(listView);
    
        // Criar a cena com o layout
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }
    public Utilizador getUtilizadorById(int id) {
        ResponseEntity<Utilizador> response = restTemplate.getForEntity(rootAPIURL + "/" + id, Utilizador.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            System.out.println("Utilizador not found");
            return null;
        }
    }
    public void eliminarUtilizador(int idAdmin) {
        List<Utilizador> users = getAllUtilizador();
    
        Stage stage = new Stage();
        stage.setTitle("Eliminar Utilizador");
    
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));
    
        // ListView para exibir os utilizadores
        ListView<String> listView = new ListView<>();
        for (Utilizador user : users) {
            String item = "Id: " + user.getIdUtilizador() + "\n" +
                          "Nome: " + user.getNome() + "\n" +
                          "Telemovel: " + user.getTelemovel() + "\n";
            listView.getItems().add(item);
        }
        listView.setPrefSize(400, 300);
    
        layout.setCenter(listView);
    
        // Botões
        Button eliminarBtn = new Button("Eliminar");
        eliminarBtn.setDisable(true);
    
        Button voltarBtn = new Button("Voltar");
    
        // Eventos de seleção na ListView
        final Utilizador[] selectedUser = {null};
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                selectedUser[0] = users.get(selectedIndex);
                eliminarBtn.setDisable(false);
            } else {
                eliminarBtn.setDisable(true);
            }
        });
    
        // Evento para o botão Eliminar
        eliminarBtn.setOnAction(e -> {
            if (selectedUser[0] != null) {
                int idUtilizador = selectedUser[0].getIdUtilizador();
                NotificacaoClient notificacaoClient = new NotificacaoClient();
                notificacaoClient.deleteNotificacoes(idUtilizador);
                FavoritosClient favoritosClient = new FavoritosClient();
                favoritosClient.deleteFavoritos(idUtilizador);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Void> response = restTemplate.exchange(
                rootAPIURL + "/" + idUtilizador + "?adminId=" + idAdmin,
                HttpMethod.DELETE,
                null,
                Void.class
            );

            // Remover o utilizador da lista e atualizar a ListView
            users.remove(selectedUser[0]);
            listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
            selectedUser[0] = null;
            eliminarBtn.setDisable(true);
        }
    });

    // Evento para o botão Voltar
    voltarBtn.setOnAction(e -> stage.close());

    // Layout para os botões
    HBox buttonBox = new HBox(10, eliminarBtn, voltarBtn);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setPadding(new Insets(10));
    layout.setBottom(buttonBox);

    Scene scene = new Scene(layout, 500, 400);
    scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(scene);
    stage.showAndWait();
}




    public boolean saveUtilizador(Utilizador utilizador) {

        if (!Objects.isNull(utilizador.getIdUtilizador()) && utilizador.getIdUtilizador() != 0) {
            return this.updateUtilizador(utilizador);
        }

        ResponseEntity<Utilizador> response = restTemplate.postForEntity(rootAPIURL, utilizador, Utilizador.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Utilizador body = response.getBody();
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
    public boolean updateUtilizador(Utilizador utilizador) {

	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	
	HttpEntity<Utilizador> requestEntity = new HttpEntity<Utilizador>(utilizador, headers);
	ResponseEntity<Utilizador> response = restTemplate.exchange(rootAPIURL, HttpMethod.PUT, requestEntity, Utilizador.class);
	
	if (response.getStatusCode().is2xxSuccessful()) {
		System.out.println("Updated");
	} else {
		System.out.println("Nothing found");
	}

	return response.getStatusCode().is2xxSuccessful();
}

public void menuUtilizador(Stage stage, int idUtilizador) {
    VBox root = new VBox();
    root.setSpacing(20);
    root.setPadding(new Insets(20));
    root.setAlignment(Pos.CENTER);
    Utilizador utilizador = getUtilizadorById(idUtilizador);
    javafx.scene.text.Text titulo = new javafx.scene.text.Text("Bem-vindo, " + utilizador.getNome() + "!");
    titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    titulo.getStyleClass().add("text");
    root.getChildren().add(titulo);
    BorderPane notificacoesLayout = new BorderPane();
    notificacoesLayout.setPadding(new Insets(15));
    ListView<String> listView = new ListView<>();
    listView.setPrefHeight(200);
    listView.setMinHeight(100); 
    NotificacaoClient notificacaoClient = new NotificacaoClient();
    List<Notificacoes> notificacoesList = notificacaoClient.getNotificacoesByUser(idUtilizador);
    
    if (notificacoesList.isEmpty()) {
        // Não mostrar ListView se não houver notificações
        listView.setVisible(false);
        listView.setManaged(false);
    } else {
        RecursosClient recursosClient = new RecursosClient();
        for (Notificacoes n : notificacoesList) {
            String nomeRecurso = recursosClient.getRecursosById( idUtilizador, n.getId_recurso()).getNome();
            String item = String.format(
                "%s\nData: %s\n%s",
                nomeRecurso,
                n.getData(),
                formatDescricao(n.getDescricao())
            );
            listView.getItems().add(item);
        }
    }
    listView.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    // Adicionar ListView ao centro do layout
    notificacoesLayout.setCenter(listView);

    // Layout de botões
    HBox botoes = new HBox();
    botoes.setSpacing(10);
    botoes.setAlignment(Pos.CENTER);

    Button procurarRecursosBtn = new Button("Procurar recursos");
    Button verFavoritosBtn = new Button("Ver favoritos");
    Button adicionarFavoritosBtn = new Button("Adicionar favoritos");
    Button removerNotificacoesBtn = new Button("Remover notificações");
    Button removerFavoritosBtn = new Button("Remover favoritos");
    Button doarButton = new Button("Doar");
    Button verDoacoes = new Button("Ver doações");
    Button sairBtn = new Button("Sair");
    botoes.getChildren().addAll(procurarRecursosBtn, verFavoritosBtn, adicionarFavoritosBtn, removerFavoritosBtn, removerNotificacoesBtn,doarButton,verDoacoes, sairBtn);
    FavoritosClient favoritosClient = new FavoritosClient();
    verFavoritosBtn.setOnAction(e -> mostrarFavoritos(idUtilizador)); // Mostrar favoritos
    adicionarFavoritosBtn.setOnAction(e -> favoritosClient.menuAdicionarFavoritos(idUtilizador));
    removerFavoritosBtn.setOnAction(e -> favoritosClient.menuRemoverFavoritos(idUtilizador));
    removerNotificacoesBtn.setOnAction(e -> {
        notificacaoClient.deleteNotificacoes(idUtilizador); // Remover notificações
        listView.getItems().clear(); // Limpar lista após remoção
        listView.getItems().add("Sem notificações disponíveis.");
    });
    sairBtn.setOnAction(e -> stage.close());
    doarButton.setOnAction(e -> {
        DonationClient DonationClient = new DonationClient();
        DonationClient.menuDoacao(idUtilizador);
    });

    verDoacoes.setOnAction(e -> {
        DonationClient donationClient = new DonationClient();
        donationClient.verDoacoes(idUtilizador);
    });
    // Ação do botão Procurar recursos
    procurarRecursosBtn.setOnAction(e -> {
        // Novo Stage para procurar recursos
        Stage procurarStage = new Stage();
        
        procurarStage.setTitle("Procurar Recursos");
        
        // Layout para os botões de filtro
        VBox filtrosLayout = new VBox(10);
        filtrosLayout.setAlignment(Pos.CENTER);
        filtrosLayout.setPadding(new Insets(20));

        // Criar os botões de filtro
        Button verTodosBtn = new Button("Ver todos");
        Button verPorLocalizacaoBtn = new Button("Procurar por localização");
        Button verPorTipoBtn = new Button("Procurar por tipo");
        Button verPorTipoELocalizacaoBtn = new Button("Procurar por tipo e localização");

        // Adicionar botões ao layout
        filtrosLayout.getChildren().addAll(verTodosBtn, verPorLocalizacaoBtn, verPorTipoBtn, verPorTipoELocalizacaoBtn);
        RecursosClient recursosClient = new RecursosClient();
        verTodosBtn.setOnAction(evt -> recursosClient.mostrarRecursos(idUtilizador)); 
        verPorLocalizacaoBtn.setOnAction(evt -> {recursosClient.mostrarRecursosByLocalizacao(idUtilizador);});
        verPorTipoBtn.setOnAction(evt -> {recursosClient.mostrarRecursosByTipo(idUtilizador);});
        verPorTipoELocalizacaoBtn.setOnAction(evt -> {
            //escolher tipo e localizacao
            TipoClient tipoClient = new TipoClient();
            Tipo tipo = tipoClient.escolherTipo();
            LocalizacaoService localizacaoClient = new LocalizacaoService();
            Localizacao localizacao = localizacaoClient.escolherLocalizacao();    
            recursosClient.mostrarRecursosByTipoELocalizacao(idUtilizador, tipo, localizacao);
        });
        Scene filtrosScene = new Scene(filtrosLayout, 300, 200);
        procurarStage.setScene(filtrosScene);
        //bloquear a procurarStage
        procurarStage.initModality(Modality.APPLICATION_MODAL);
        filtrosScene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
        procurarStage.initModality(Modality.APPLICATION_MODAL);
        procurarStage.showAndWait();
    });

    root.getChildren().addAll(notificacoesLayout, botoes);
    Scene scene = new Scene(root, 700, 500);
    stage.setTitle("Menu Utilizador");
    scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
    stage.setMinWidth(1150);
    stage.setMinHeight(400);
    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.showAndWait();
}
private String formatDescricao(String descricao) {
    StringBuilder descricaoFormatada = new StringBuilder();

    String[] partes = descricao.split("\\[|\\]");
    descricaoFormatada.append(partes[0].trim()).append("\n\n");

    if (partes.length > 1) {
        String recursosTexto = partes[1].trim();
        String[] recursos = recursosTexto.split("-----------------,");

        for (String recurso : recursos) {
            descricaoFormatada.append("Recurso:\n")
                    .append(recurso.trim().replaceAll(", \\n", "\n"))
                    .append("\n-----------------\n");
        }
    }

    return descricaoFormatada.toString();
}

public void mostrarFavoritos(int idUtilizador) {
    FavoritosClient favoritosClient = new FavoritosClient();
    List<Favoritos> favoritosList = favoritosClient.getFavoritosByUtilizadorId(idUtilizador);
    //mostrar quantos favoritos existem deste utilizador numa label
    Text text = new Text("Tem " + favoritosList.size() + " favoritos.");
    text.getStyleClass().add("text");
    
    // Criação da janela
    Stage stage = new Stage();
    stage.setTitle("Favoritos do Utilizador");

    // Layout principal
    BorderPane layout = new BorderPane();
    layout.setPadding(new Insets(15));
    layout.setTop(text);
    // ListView para exibir os favoritos
    ListView<String> listView = new ListView<>();

    // Adiciona os favoritos formatados ao ListView
    for (Favoritos favorito : favoritosList) {
        String item = String.format(
            "Tipo: %s\nNome: %s\nTelefone: %s\nDistrito: %s\nCidade: %s\nData: %s",
            favorito.getRecurso().getTipo().getTipo(),
            favorito.getRecurso().getNome(),
            favorito.getRecurso().getTelefone(),
            favorito.getRecurso().getLocalizacao().getDistrito(),
            favorito.getRecurso().getLocalizacao().getCidade(),
            favorito.getDataFavorito()
        );
        listView.getItems().add(item);
    }

    layout.setCenter(listView);

    // Botão para fechar a janela
    Button voltarBtn = new Button("Voltar");
    voltarBtn.setOnAction(event -> stage.close());

    // Box para o botão
    HBox buttonBox = new HBox(10, voltarBtn);
    buttonBox.setAlignment(Pos.CENTER);
    layout.setBottom(buttonBox);

    // Configurar e mostrar a cena
    Scene scene = new Scene(layout, 500, 400);
    scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.showAndWait();
}
public void mostrarUtilizadoresAdmin(int idAdmin) {
    List<Utilizador> users = getAllUtilizador();

    Stage stage = new Stage();
    stage.setTitle("Gerir Utilizadores");

    BorderPane layout = new BorderPane();
    layout.setPadding(new Insets(10));
    // Label com o número de utilizadores
    Text text = new Text("Existem " + users.size() + " utilizadores.");
    text.getStyleClass().add("text");
    layout.setTop(text);

    // ListView para exibir os utilizadores
    ListView<String> listView = new ListView<>();
    for (Utilizador user : users) {
        String item = "Id: " + user.getIdUtilizador() + "\n" +
                      "Nome: " + user.getNome() + "\n" +
                      "Telemovel: " + user.getTelemovel() + "\n";
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

    // Eventos de seleção na ListView
    final Utilizador[] selectedUser = {null};
    listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            selectedUser[0] = users.get(selectedIndex);
            eliminarBtn.setDisable(false);
            atualizarBtn.setDisable(false);
        } else {
            eliminarBtn.setDisable(true);
            atualizarBtn.setDisable(true);
        }
    });

    // Evento para o botão Eliminar
    eliminarBtn.setOnAction(e -> {
        if (selectedUser[0] != null) {
            int idUtilizador = selectedUser[0].getIdUtilizador();
            NotificacaoClient notificacaoClient = new NotificacaoClient();
            notificacaoClient.deleteNotificacoes(idUtilizador);
            FavoritosClient favoritosClient = new FavoritosClient();
            favoritosClient.deleteFavoritos(idUtilizador);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Void> response = restTemplate.exchange(
                rootAPIURL + "/" + idUtilizador + "?adminId=" + idAdmin,
                HttpMethod.DELETE,
                null,
                Void.class
            );

            // Remover o utilizador da lista e atualizar a ListView
            users.remove(selectedUser[0]);
            listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
            selectedUser[0] = null;
            eliminarBtn.setDisable(true);
            atualizarBtn.setDisable(true);
        }
    });

    atualizarBtn.setOnAction(e -> {
        if (selectedUser[0] != null) {
            Utilizador user = selectedUser[0];
            System.out.println("Atualizar utilizador: " + user.getNome());
        }
    });
    voltarBtn.setOnAction(e -> stage.close());

    HBox buttonBox = new HBox(10, eliminarBtn, atualizarBtn, voltarBtn);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setPadding(new Insets(10));
    layout.setBottom(buttonBox);

    Scene scene = new Scene(layout, 500, 400);
    scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.showAndWait();
}
}

