package rest;

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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Localizacao;
import model.Tipo;
public class LocalizacaoService {
	private RestTemplate restTemplate = new RestTemplate();

	private static final String rootAPIURL = "http://localhost:8080/api/localizacao";

	public Localizacao getLocalizacaoById(Long id) {

		ResponseEntity<Localizacao> response = restTemplate.getForEntity(rootAPIURL + "/" + id.toString(), Localizacao.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Localizacao body = response.getBody();
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

	public boolean updateLocalizacao(Localizacao localizacao) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Localizacao> requestEntity = new HttpEntity<Localizacao>(localizacao, headers);
		ResponseEntity<Localizacao> response = restTemplate.exchange(rootAPIURL, HttpMethod.PUT, requestEntity, Localizacao.class);
		
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Updated");
		} else {
			System.out.println("Nothing found");
		}

		return response.getStatusCode().is2xxSuccessful();
	}

	public List<Localizacao> getAllLocalizacao() {

		ResponseEntity<Localizacao[]> response = restTemplate.getForEntity(rootAPIURL, Localizacao[].class);

		List<Localizacao> localizacoes = new ArrayList<>();

		if (response.getStatusCode().is2xxSuccessful()) {

			if (response.getBody() != null) {

				for (Localizacao l : response.getBody()) {
					localizacoes.add(l);
				}
				return localizacoes;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return localizacoes;

	}

	public boolean saveLocalizacao(Localizacao localizacao) {

		if (!Objects.isNull(localizacao.getIdLocalizacao()) && localizacao.getIdLocalizacao() != 0) {
			return this.updateLocalizacao(localizacao);
		}

		ResponseEntity<Localizacao> response = restTemplate.postForEntity(rootAPIURL, localizacao, Localizacao.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Localizacao body = response.getBody();
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

	public boolean deleteLocalizacao(int idLocalizacao) {


		ResponseEntity<Void> response = restTemplate.exchange(rootAPIURL + "/" + idLocalizacao, HttpMethod.DELETE, null, Void.class);
		
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Deleted");
		} else {
			System.out.println("Nothing found");
		}

		return response.getStatusCode().is2xxSuccessful();

	}
	public Localizacao mostrarLocalizacao() {
		// Criar o Stage dentro do método
		Stage stage = new Stage();
	
		// Layout principal
		BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(15));
	
		// Lista para exibir as localizações
		ListView<Localizacao> listView = new ListView<>();
		List<Localizacao> localizacoes = this.getAllLocalizacao(); // Obtém todas as localizações
		listView.getItems().addAll(localizacoes); // Adiciona as localizações à lista
	
		layout.setCenter(listView);
	
		// Botões para ações
		Button voltarBtn = new Button("Voltar");
		HBox buttonBox = new HBox(10, voltarBtn);
		buttonBox.setAlignment(Pos.CENTER);
		layout.setBottom(buttonBox);
	
		// Variável para armazenar a localização selecionada
		final Localizacao[] localizacaoSelecionada = new Localizacao[1];
	
		// Ação do botão Voltar
		voltarBtn.setOnAction(event -> stage.close()); // Fecha a janela
	
		// Ação de seleção da ListView
		listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			localizacaoSelecionada[0] = newValue; // Atribui a localização selecionada
		});
	
		// Ação para retornar a localização selecionada
		Button confirmarBtn = new Button("Confirmar Seleção");
		confirmarBtn.setOnAction(event -> {
			if (localizacaoSelecionada[0] != null) {
				System.out.println("Localização selecionada: " + localizacaoSelecionada[0]);
				stage.close(); // Fecha o Stage
			} else {
				System.out.println("Nenhuma localização selecionada.");
			}
		});
	
		// Adicionando o botão de confirmação ao layout
		HBox confirmarBox = new HBox(10, confirmarBtn);
		confirmarBox.setAlignment(Pos.CENTER);
		layout.setBottom(confirmarBox);
	
		// Configurar a cena e exibir
		Scene scene = new Scene(layout, 500, 400);
		scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Localizações");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	
		return localizacaoSelecionada[0]; // Retorna a localização selecionada
	}

    public Localizacao escolherLocalizacao() {
		// Criar o Stage dentro do método
		Stage stage = new Stage();
	
		// Layout principal
		BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(15));
	
		// Lista para exibir as localizações
		ListView<Localizacao> listView = new ListView<>();
		List<Localizacao> localizacoes = getAllLocalizacao(); // Um método para buscar todas as localizações
		listView.getItems().addAll(localizacoes);
		layout.setCenter(listView);
	
		// Botões para ações
		Button voltarBtn = new Button("Voltar");
		Button confirmarBtn = new Button("Confirmar Seleção");
	
		HBox buttonBox = new HBox(10, confirmarBtn, voltarBtn);
		buttonBox.setAlignment(Pos.CENTER);
		layout.setBottom(buttonBox);
	
		// Variável para armazenar a localização selecionada
		final Localizacao[] localizacaoSelecionada = new Localizacao[1];
	
		// Ação do botão Voltar
		voltarBtn.setOnAction(event -> stage.close()); // Fecha a janela
	
		// Ação de seleção da ListView
		listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			localizacaoSelecionada[0] = newValue; // Atribui a localização selecionada
		});
	
		// Ação do botão Confirmar Seleção
		confirmarBtn.setOnAction(event -> {
			if (localizacaoSelecionada[0] != null) {
				System.out.println("Localização selecionada: " + localizacaoSelecionada[0]);
				stage.close(); // Fecha o Stage
			} else {
				System.out.println("Nenhuma localização selecionada.");
			}
		});
	
		// Configurar a cena e exibir
		Scene scene = new Scene(layout, 500, 400);
		scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Escolher Localização");
		stage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a tela principal
		stage.showAndWait();
	
		return localizacaoSelecionada[0]; // Retorna a localização selecionada
	}
	
	

}
