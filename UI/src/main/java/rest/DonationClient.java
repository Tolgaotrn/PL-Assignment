package rest;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import model.Hospital;
import model.Recursos;
public class DonationClient {
    private RestTemplate restTemplate = new RestTemplate();
    private static final String rootAPIURL = "http://localhost:8080/api/donations";
    public void menuDoacao(int idUtilizador) {
        RecursosClient recursosClient = new RecursosClient();
        Recursos recursoSelecionado = recursosClient.selecionarRecurso(idUtilizador);

        Stage stage = new Stage();
        stage.setTitle("Menu de Doação");
        stage.initModality(Modality.APPLICATION_MODAL);
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(15));

        if (recursoSelecionado == null) {
            Label mensagem = new Label("Nenhum recurso selecionado para doação.");
            layout.setCenter(mensagem);
        } else {
            VBox formLayout = new VBox(10);
            formLayout.setPadding(new Insets(15));

            Label titulo = new Label("Detalhes da Doação");
            titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Alteração para setar o estilo com setFont

            Label nomeRecurso = new Label("Recurso: " + recursoSelecionado.getNome());
            Label label1 = new Label("Valor da doação");
            TextField valorDoacao = new TextField();
            Label label2 = new Label("Nome do doador (opcional)");
            TextField nomeField = new TextField();
            Button confirmarBtn = new Button("Confirmar");
            confirmarBtn.setOnAction(e -> {
                String valor = valorDoacao.getText();
                String nome = nomeField.getText();
                if (nome.isEmpty()) {
                    nome = "Anónimo";
                }
                Donation donation = new Donation(idUtilizador, Double.parseDouble(valor), new Date(System.currentTimeMillis()), recursoSelecionado, nome);
                saveDone(donation);
                System.out.println("Doação de " + valor + " registrada para o recurso: " + recursoSelecionado.getNome());
                stage.close();
            });
            Button cancelarBtn = new Button("Cancelar");
            cancelarBtn.setOnAction(e -> stage.close());
            HBox botoes = new HBox(10, confirmarBtn, cancelarBtn);
            botoes.setAlignment(Pos.CENTER);
            formLayout.getChildren().add(titulo);
            formLayout.getChildren().add(nomeRecurso);
            formLayout.getChildren().add(label1);
            formLayout.getChildren().add(valorDoacao);
            formLayout.getChildren().add(label2);
            formLayout.getChildren().add(nomeField);
            formLayout.getChildren().add(botoes);
            layout.setCenter(formLayout);
        }
        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }
    public List<Donation> getAllDonationByUserId(int idUtilizador) {
        ResponseEntity<Donation[]> response = restTemplate.getForEntity(rootAPIURL + "/user/" + idUtilizador, Donation[].class);
        List<Donation> doacoes = new ArrayList<>();
        if (response.getStatusCode().is2xxSuccessful()) {
            if (response.getBody() != null) {
                for (Donation h : response.getBody()) {
                    doacoes.add(h);
                }
                return doacoes;
            } else {
                System.out.println("No body");
            }
        } else {
            System.out.println("Nothing found");
        }
        return doacoes;
    }
    public List<Donation> getAllDonation() {
        ResponseEntity<Donation[]> response = restTemplate.getForEntity(rootAPIURL, Donation[].class);

        List<Donation> doacoes = new ArrayList<>();

        if (response.getStatusCode().is2xxSuccessful()) {
            if (response.getBody() != null) {
                for (Donation h : response.getBody()) {
                    doacoes.add(h);
                }
                return doacoes;
            } else {
                System.out.println("No body");
            }
        } else {
            System.out.println("Nothing found");
        }
        return doacoes;
    }
    private void saveDone(Donation donation) {
        // Envia a doação para o servidor
        RestTemplate restTemplate = new RestTemplate();
        String rootAPIURL = "http://localhost:8080/api/donations";
        restTemplate.postForObject(rootAPIURL, donation, Donation.class);
    }
    public void listarDoacoes() {
        List<Donation> doacoes = getAllDonation();
        Stage stage = new Stage();
        stage.setTitle("Lista de Doações");
        Text text = new Text("Existem " + doacoes.size() + " doações registradas.");
        //adicionar o style.css
        text.getStyleClass().add("text");
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().add(text);

        if (doacoes.isEmpty()) {
            Label mensagem = new Label("Nenhuma doação encontrada.");
            layout.getChildren().add(mensagem);
        } else {
            ListView<String> listView = new ListView<>();
            for (Donation donation : doacoes) {
                listView.getItems().add("Doação de " + donation.getAmount() + " para o recurso: " + donation.getRecurso().getNome() + " por " + donation.getDonorName());
            }
            layout.getChildren().add(listView);
        }

        Button voltarBtn = new Button("Voltar");
        voltarBtn.setOnAction(e -> stage.close());
        layout.getChildren().add(voltarBtn);

        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }
    public void verDoacoes(int idUtilizador) {
        List<Donation> doacoes = getAllDonationByUserId(idUtilizador);
        Stage stage = new Stage();
        stage.setTitle("Lista de Doações");
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        //label para mosrar quantas doações o utilizador fez
        Label label = new Label("O utilizador fez " + doacoes.size() + " doações");
        layout.getChildren().add(label);


        if (doacoes.isEmpty()) {
            Label mensagem = new Label("Nenhuma doação encontrada.");
            layout.getChildren().add(mensagem);
        } else {
            ListView<String> listView = new ListView<>();
            for (Donation donation : doacoes) {
                listView.getItems().add("Doação de " + donation.getAmount() + "€ para o recurso: " + donation.getRecurso().getNome());
            }
            layout.getChildren().add(listView);
        }

        Button voltarBtn = new Button("Voltar");
        voltarBtn.setOnAction(e -> stage.close());
        layout.getChildren().add(voltarBtn);

        Scene scene = new Scene(layout, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }
}
