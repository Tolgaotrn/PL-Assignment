package rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Tipo;

public class TipoClient {
    private RestTemplate restTemplate = new RestTemplate();
	private static final String rootAPIURL = "http://localhost:8080/api/tipo";
    public List<Tipo> getAllTipo() {

		ResponseEntity<Tipo[]> response = restTemplate.getForEntity(rootAPIURL, Tipo[].class);

		List<Tipo> tipos = new ArrayList<>();

		if (response.getStatusCode().is2xxSuccessful()) {

			if (response.getBody() != null) {

				for (Tipo h : response.getBody()) {
					tipos.add(h);
				}
				return tipos;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return tipos;

	}
    public Tipo escolherTipo() {
        final Tipo[] tipoSelecionado = new Tipo[1];
    
        // Criar o Stage
        Stage stage = new Stage();
        stage.setTitle("Escolher Tipo");
    
        // Layout principal
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(15));
    
        // Lista para exibir os tipos
        ListView<Tipo> listView = new ListView<>();
        List<Tipo> tipos = getAllTipo(); // Obtém todos os tipos
        listView.getItems().addAll(tipos); // Adiciona os tipos à lista
    
        layout.setCenter(listView);
    
        // Botões para ações
        Button escolherBtn = new Button("Escolher Tipo");
        Button voltarBtn = new Button("Voltar");
    
        HBox buttonBox = new HBox(10, escolherBtn, voltarBtn);
        buttonBox.setAlignment(Pos.CENTER);
        layout.setBottom(buttonBox);
    
        // Ação do botão "Escolher Tipo"
        escolherBtn.setOnAction(event -> {
            tipoSelecionado[0] = listView.getSelectionModel().getSelectedItem();
            if (tipoSelecionado[0] != null) {
                System.out.println("Tipo escolhido: " + tipoSelecionado[0]);
                stage.close(); // Fecha a janela
            } else {
                System.out.println("Nenhum tipo selecionado!");
            }
        });
    
        // Ação do botão "Voltar"
        voltarBtn.setOnAction(event -> stage.close()); // Fecha a janela
    
        // Configurar a cena e exibir
        Scene scene = new Scene(layout, 500, 400);
        scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait(); // Espera até o fechamento do Stage
    
        return tipoSelecionado[0]; // Retorna o tipo selecionado ou null se nada for escolhido
    }
    public void mostrarTipoAdmin() {
        List<Tipo> tipos = getAllTipo();
        Stage stage = new Stage();
        stage.setTitle("Gestão de Tipos");
        Text text = new Text("Total de Tipos: " + tipos.size());
        text.getStyleClass().add("text");
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));
        layout.setTop(text);
        ListView<String> listView = new ListView<>();
        for (Tipo tipo : tipos) {
            String item = "Id: " + tipo.getIdTipo() + "\n" +
                          "Tipo: " + tipo.getTipo() + "\n";
            listView.getItems().add(item);
        }
        listView.setPrefSize(400, 300);
        layout.setCenter(listView);
        Button eliminarBtn = new Button("Eliminar");
        eliminarBtn.setDisable(true);
        Button atualizarBtn = new Button("Atualizar");
        atualizarBtn.setDisable(true);
        Button voltarBtn = new Button("Voltar");
        Button sairBtn = new Button("Sair");
        final Tipo[] selectedTipo = {null};
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                selectedTipo[0] = tipos.get(selectedIndex);
                eliminarBtn.setDisable(false);
                atualizarBtn.setDisable(false);
            } else {
                eliminarBtn.setDisable(true);
                atualizarBtn.setDisable(true);
            }
        });
    
        // Evento para o botão Eliminar
        eliminarBtn.setOnAction(e -> {
            if (selectedTipo[0] != null) {
                int idTipo = selectedTipo[0].getIdTipo();
    
                // Lógica de remoção do tipo
                TipoClient tipoClient = new TipoClient();
                tipoClient.deleteTipo(idTipo);
    
                // Remover o tipo da lista e atualizar a ListView
                tipos.remove(selectedTipo[0]);
                listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
                selectedTipo[0] = null;
                eliminarBtn.setDisable(true);
                atualizarBtn.setDisable(true);
            }
        });
    
        // Evento para o botão Atualizar
        atualizarBtn.setOnAction(e -> {
            if (selectedTipo[0] != null) {
                // Chamar método para atualizar o tipo
                // atualizarTipo(selectedTipo[0]);
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
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
    public void deleteTipo(int idTipo) {
        //apagar todos os recursos associados ao tipo
        restTemplate.delete(rootAPIURL + "/" + idTipo);
    }
}
