package app;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Administrador;
import model.Utilizador;
import rest.UtilizadorService;
import rest.administradorService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

//ADMIN
//ADMIN DE RECURSO
//o utilizador tem de conseguir marcar as notificações como lidas
//tem de haver um botao para confirmar a ação de eliminar um utilizador
//listar recursos, doações e favoritos (+) tem de ter filtros como por exemplo tipo e localização

public class AplicacaoPrincipal extends Application {

    public Stage stagePrincipal;
    administradorService adminservice = new administradorService();
    UtilizadorService userservice = new UtilizadorService();

    // Cena principal
    private Scene cenaPrincipal;
    public Stage cenarioPrincipal;
    

    @Override
    public void start(Stage primaryStage) {
        this.cenarioPrincipal = primaryStage;
        System.out.println("Iniciar AplicacaoPrincipal...");

        mostrarLogin();

        cenarioPrincipal.setTitle("À Mão");
        cenarioPrincipal.setMinWidth(400);
        cenarioPrincipal.setMinHeight(400);
        cenarioPrincipal.show();
    }

    // Tela de Login
    private void mostrarLogin() {
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setAlignment(Pos.CENTER);
        Text titulo = new Text("À Mão");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
        Text subtitulo = new Text("Recursos à distância de um clique");
        subtitulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: #ffffff;");
        VBox topContainer = new VBox(10);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.getChildren().addAll(titulo, subtitulo);
        topContainer.setPadding(new Insets(20));
        Text text1 = new Text("Username");
        text1.getStyleClass().add("text");
        Text text2 = new Text("Password");
        text2.getStyleClass().add("text");

        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("text-field");

        PasswordField passwordField = new PasswordField();

        Button entrarBtn = new Button("Entrar");
        entrarBtn.getStyleClass().add("button");
        entrarBtn.disableProperty().bind(
                usernameField.textProperty().isEmpty()
                        .or(passwordField.textProperty().length().lessThan(5))
        );

        Button criarContaBtn = new Button("Criar conta");
        criarContaBtn.getStyleClass().add("button");
        // Ações dos botões
        entrarBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (validaLoginAdmin(username, password) != -1) {
                administradorService adminservice = new administradorService();
                Administrador admin = adminservice.getAdministradorById(validaLoginAdmin(username, password));
                if (admin.getRecurso() != null) {
                    adminservice.menuAdminRecurso(cenarioPrincipal, admin.getIdAdmin());
                } else {
                    mostrarMenuAdministrador(admin.getIdAdmin());
                }
            } else if (validaLoginUser(username, password) != -1) {
                mostrarMenuUtilizador(validaLoginUser(username, password));
            } else {
                System.out.println("Credenciais inválidas. Tente novamente.");
            }
        });
        criarContaBtn.setOnAction(e -> mostrarMenuRegistro());
        gp.add(titulo, 0, 0, 2, 1); 
        gp.add(subtitulo, 0, 1, 2, 1); 
        gp.add(text1, 0, 2);
        gp.add(usernameField, 1, 2);
        gp.add(text2, 0, 3);
        gp.add(passwordField, 1, 3);
        gp.add(entrarBtn, 1, 4);
        gp.add(criarContaBtn, 0, 4);
        cenaPrincipal = new Scene(gp, 400, 200);
        cenaPrincipal.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
        cenarioPrincipal.setScene(cenaPrincipal);
    }
    private void mostrarMenuRegistro() {
        GridPane gpRegistro = new GridPane();
        gpRegistro.setPadding(new Insets(10, 10, 10, 10));
        gpRegistro.setVgap(5);
        gpRegistro.setHgap(5);
        gpRegistro.setAlignment(Pos.CENTER);

        Text nomeLabel = new Text("Nome:");
        nomeLabel.getStyleClass().add("text");

        Text usernameLabel = new Text("Username:");
        usernameLabel.getStyleClass().add("text");

        Text passwordLabel = new Text("Password:");
        passwordLabel.getStyleClass().add("text");

        Text confirmarPasswordLabel = new Text("Confirmar Password:");
        confirmarPasswordLabel.getStyleClass().add("text");

        Text telemovelLabel = new Text("Telemóvel:");
        telemovelLabel.getStyleClass().add("text");

        TextField nomeField = new TextField();
        nomeField.getStyleClass().add("text-field");

        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("text-field");

        PasswordField passwordField = new PasswordField();
        PasswordField confirmarPasswordField = new PasswordField();
        TextField telemovelField = new TextField();
        telemovelField.getStyleClass().add("text-field");


        Button salvarBtn = new Button("Salvar");
        salvarBtn.getStyleClass().add("button");
        Button voltarBtn = new Button("Voltar");
        voltarBtn.getStyleClass().add("button");
        salvarBtn.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
        voltarBtn.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");

        salvarBtn.disableProperty().bind(
                nomeField.textProperty().isEmpty()
                        .or(usernameField.textProperty().isEmpty())
                        .or(passwordField.textProperty().isEmpty())
                        .or(confirmarPasswordField.textProperty().isEmpty())
                        .or(telemovelField.textProperty().isEmpty())
                        .or(passwordField.textProperty().isNotEqualTo(confirmarPasswordField.textProperty()))
        );

        salvarBtn.setOnAction(e -> {
            Utilizador novoUser = new Utilizador(nomeField.getText(), usernameField.getText(),
                    encriptar(passwordField.getText()), telemovelField.getText());
            userservice.saveUtilizador(novoUser);
            System.out.println("Utilizador registado com sucesso.");
            mostrarLogin();
        });

        voltarBtn.setOnAction(e -> mostrarLogin());

        gpRegistro.add(nomeLabel, 0, 0);
        gpRegistro.add(nomeField, 1, 0);
        gpRegistro.add(usernameLabel, 0, 1);
        gpRegistro.add(usernameField, 1, 1);
        gpRegistro.add(passwordLabel, 0, 2);
        gpRegistro.add(passwordField, 1, 2);
        gpRegistro.add(confirmarPasswordLabel, 0, 3);
        gpRegistro.add(confirmarPasswordField, 1, 3);
        gpRegistro.add(telemovelLabel, 0, 4);
        gpRegistro.add(telemovelField, 1, 4);
        gpRegistro.add(salvarBtn, 0, 5);
        gpRegistro.add(voltarBtn, 1, 5);
        cenaPrincipal.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
        cenaPrincipal.setRoot(gpRegistro);
    }

    private void mostrarMenuAdministrador(int idAdmin) {
        adminservice.menuAdministrador(cenarioPrincipal, idAdmin);
    }

    private void mostrarMenuUtilizador(int idUtilizador) {
        userservice.menuUtilizador(cenarioPrincipal, idUtilizador);
    }

    private int validaLoginAdmin(String username, String password) {
        List<model.Administrador> admins = adminservice.getAllAdministrador();
        for (model.Administrador a : admins) {
            if (a.getUsername().equals(username) && a.getPassword().equals(encriptar(password))) {
                return a.getIdAdmin();
            }
        }
        return -1;
    }

    private int validaLoginUser(String username, String password) {
        List<model.Utilizador> users = userservice.getAllUtilizador();
        for (model.Utilizador u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(encriptar(password))) {
                return u.getIdUtilizador();
            }
        }
        return -1;
    }

    private String encriptar(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro de encriptação", e);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
