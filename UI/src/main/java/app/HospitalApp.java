package app;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rest.HospitalClient;
import model.Hospital;

import java.util.List;

public class HospitalApp extends Application {

    private HospitalClient hospitalClient = new HospitalClient();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hospitais");

        // Tabela
        TableView<Hospital> tableView = new TableView<>();

        // Colunas da Tabela
        TableColumn<Hospital, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Hospital, String> colEspecialidades = new TableColumn<>("Especialidades");
        colEspecialidades.setCellValueFactory(new PropertyValueFactory<>("especialidades"));

        TableColumn<Hospital, Integer> colVagas = new TableColumn<>("Vagas");
        colVagas.setCellValueFactory(new PropertyValueFactory<>("vagas"));

        TableColumn<Hospital, String> colCustos = new TableColumn<>("Custos Acrescidos");
        colCustos.setCellValueFactory(new PropertyValueFactory<>("custosAcrescidos"));

        TableColumn<Hospital, String> colInfoExtra = new TableColumn<>("Informação Extra");
        colInfoExtra.setCellValueFactory(new PropertyValueFactory<>("informacaoExtra"));

        // Adicionar Colunas à Tabela
        tableView.getColumns().addAll(colNome, colEspecialidades, colVagas, colCustos, colInfoExtra);

        // Botão para carregar hospitais
        Button btnCarregar = new Button("Listar Hospitais");
        btnCarregar.setOnAction(e -> {
            List<Hospital> hospitais = hospitalClient.getAllHospital();
            ObservableList<Hospital> data = FXCollections.observableArrayList(hospitais);
            tableView.setItems(data);
        });

        // Layout
        VBox vbox = new VBox(10, btnCarregar, tableView);
        vbox.setAlignment(Pos.CENTER);

        // Cena
        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
