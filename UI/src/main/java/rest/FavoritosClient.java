package rest;

import java.time.LocalDate;
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
import model.Favoritos;
import model.Recursos;
import model.Utilizador;

public class FavoritosClient {
    private RestTemplate restTemplate = new RestTemplate();

	private static final String rootAPIURL = "http://localhost:8080/api/favoritos";

	public Favoritos getFavoritosById(Long id) {

		ResponseEntity<Favoritos> response = restTemplate.getForEntity(rootAPIURL + "/" + id.toString(), Favoritos.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Favoritos body = response.getBody();
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

	public boolean updateFavoritos(Favoritos favoritos) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Favoritos> requestEntity = new HttpEntity<Favoritos>(favoritos, headers);
		ResponseEntity<Favoritos> response = restTemplate.exchange(rootAPIURL, HttpMethod.PUT, requestEntity, Favoritos.class);
		
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Updated");
		} else {
			System.out.println("Nothing found");
		}

		return response.getStatusCode().is2xxSuccessful();
	}

	public List<Favoritos> getAllFavoritos() {

		ResponseEntity<Favoritos[]> response = restTemplate.getForEntity(rootAPIURL, Favoritos[].class);

		List<Favoritos> favoritos = new ArrayList<>();

		if (response.getStatusCode().is2xxSuccessful()) {

			if (response.getBody() != null) {

				for (Favoritos f : response.getBody()) {
					favoritos.add(f);
				}
				return favoritos;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return favoritos;

	}
    public List<Favoritos> getFavoritosByUtilizadorId(int id) {
        ResponseEntity<Favoritos[]> response = restTemplate.getForEntity(rootAPIURL + "/utilizador/"+ id, Favoritos[].class);
		List<Favoritos> favoritos = new ArrayList<>();
		if (response.getStatusCode().is2xxSuccessful()) {
			if (response.getBody() != null) {
				for (Favoritos f : response.getBody()) {
					favoritos.add(f);
				}
				return favoritos;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return favoritos;

    }
	
	public boolean deleteFavoritosById(int idFavoritos) {
		ResponseEntity<Void> response = restTemplate.exchange(rootAPIURL + "/fav_id/" + idFavoritos, HttpMethod.DELETE, null, Void.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Deleted");
		} else {
			System.out.println("Nothing found");
		}
		return response.getStatusCode().is2xxSuccessful();
	}


	public boolean savefavoritos(Favoritos favoritos) {

		if (!Objects.isNull(favoritos.getIdFavorito()) && favoritos.getIdFavorito() != 0) {
			return this.updateFavoritos(favoritos);
		}
        ///{idUtilizador}/{idRecurso}
		ResponseEntity<Favoritos> response = restTemplate.postForEntity(rootAPIURL + "/"+favoritos.getUtilizador().getIdUtilizador() + "/" + favoritos.getRecurso().getIdRecurso(), favoritos, Favoritos.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Favoritos body = response.getBody();
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
	public boolean deleteFavoritos(int idFavoritos) {
		ResponseEntity<Void> response = restTemplate.exchange(rootAPIURL + "/" + idFavoritos, HttpMethod.DELETE, null, Void.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Deleted");
		} else {
			System.out.println("Nothing found");
		}
		return response.getStatusCode().is2xxSuccessful();
	}
	public boolean deleteFavoritosRecurso(int idRecurso) {
		ResponseEntity<Void> response = restTemplate.exchange(rootAPIURL + "/recurso/" + idRecurso, HttpMethod.DELETE, null, Void.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Deleted");
		} else {
			System.out.println("Nothing found");
		}
		return response.getStatusCode().is2xxSuccessful();
	}
    public void menuAdicionarFavoritos(int idUtilizador) {
         Stage stage = new Stage();
        stage.setTitle("Adicionar Favoritos");

        // Layout principal
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(15));

        // Lista para exibir os recursos
        ListView<Recursos> listView = new ListView<>();
        RecursosClient recursosClient = new RecursosClient();
        List<Recursos> recursosDisponiveis = recursosClient.getAllRecursos(idUtilizador); // Busca todos os recursos disponíveis
        listView.getItems().addAll(recursosDisponiveis); // Adiciona todos os recursos à lista

        layout.setCenter(listView);

        // Botões para ações
        Button adicionarBtn = new Button("Adicionar Favorito");
        Button voltarBtn = new Button("Voltar");

        HBox buttonBox = new HBox(10, adicionarBtn, voltarBtn);
        buttonBox.setAlignment(Pos.CENTER);
        layout.setBottom(buttonBox);

        // Ações dos botões
        adicionarBtn.setOnAction(event -> {
            Recursos recursoSelecionado = listView.getSelectionModel().getSelectedItem();
            UtilizadorService utilizadorService = new UtilizadorService();
            Utilizador utilizador = utilizadorService.getUtilizadorById(idUtilizador);
            if (recursoSelecionado != null) {
                Favoritos novoFavorito = new Favoritos(
                        utilizador,
                        recursoSelecionado,
                        LocalDate.now().toString() // Data atual como string
                );

                savefavoritos(novoFavorito); // Salva no serviço de favoritos

                System.out.println("Favorito adicionado com sucesso!");
                System.out.println(novoFavorito); // Exibe informações do favorito

                listView.getItems().remove(recursoSelecionado); // Remove o recurso da lista exibida
            } else {
                System.out.println("Nenhum recurso selecionado!");
            }
        });

        voltarBtn.setOnAction(event -> stage.close()); // Fecha a janela
        Scene scene = new Scene(layout, 500, 400);
		stage.initModality(Modality.APPLICATION_MODAL);
		scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }

    public Object menuRemoverFavoritos(int idUtilizador) {
        Stage stage = new Stage();
        stage.setTitle("Remover Favoritos");

        // Layout principal
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(15));

        // Lista para exibir os favoritos
        ListView<Favoritos> listView = new ListView<>();
        List<Favoritos> favoritos = getFavoritosByUtilizadorId(idUtilizador); // Busca todos os favoritos do usuário
        listView.getItems().addAll(favoritos); // Adiciona todos os favoritos à lista
        layout.setCenter(listView);

        // Botões para ações
        Button removerBtn = new Button("Remover Favorito");
        Button voltarBtn = new Button("Voltar");

        HBox buttonBox = new HBox(10, removerBtn, voltarBtn);
        buttonBox.setAlignment(Pos.CENTER);
        layout.setBottom(buttonBox);

        // Ações dos botões
        removerBtn.setOnAction(event -> {
            Favoritos favoritoSelecionado = listView.getSelectionModel().getSelectedItem();
            if (favoritoSelecionado != null) {
                deleteFavoritos(favoritoSelecionado.getIdFavorito()); // Remove o favorito
                System.out.println("Favorito removido com sucesso!");
                System.out.println(favoritoSelecionado); // Exibe informações do favorito
                listView.getItems().remove(favoritoSelecionado); // Remove o favorito da lista exibida
            } else {
                System.out.println("Nenhum favorito selecionado!");
            }
        });

        voltarBtn.setOnAction(event -> stage.close()); // Fecha a janela

        // Configurar a cena e exibir
        Scene scene = new Scene(layout, 500, 400);
		scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
		stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        return null;
    }

    public List<Favoritos> getFavoritosByRecurso(int idRecurso) {
        ResponseEntity<Favoritos[]> response = restTemplate.getForEntity(rootAPIURL + "/recurso/"+ idRecurso, Favoritos[].class);
		List<Favoritos> favoritos = new ArrayList<>();
		if (response.getStatusCode().is2xxSuccessful()) {
			if (response.getBody() != null) {
				for (Favoritos f : response.getBody()) {
					favoritos.add(f);
				}
				return favoritos;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return favoritos;
    }

}
