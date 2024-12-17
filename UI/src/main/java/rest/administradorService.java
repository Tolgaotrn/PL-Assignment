package rest;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import app.AplicacaoPrincipal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Administrador;
import model.Hospital;
import model.Recursos;
import model.Tipo;
public class administradorService {
	UtilizadorService userservice= new UtilizadorService();
	Scanner scanner = new Scanner(System.in);
	private RestTemplate restTemplate = new RestTemplate();
	private static final String rootAPIURL = "http://localhost:8080/api/administrador";
	public List<Administrador> getAllAdministrador() {
		ResponseEntity<Administrador[]> response = restTemplate.getForEntity(rootAPIURL, Administrador[].class);
		List<Administrador> admins = new ArrayList<>();
		if (response.getStatusCode().is2xxSuccessful()) {
			if (response.getBody() != null) {
				for (Administrador a : response.getBody()) {
					admins.add(a);
				}
				return admins;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return admins;
	}
	public void menuAdministrador(Stage stage, int idAdmin) {
	    Stage menuAdminStage = new Stage();
	    menuAdminStage.setTitle("Menu Administrador Geral");
	    GridPane gpAdmin = new GridPane();
	    gpAdmin.setPadding(new Insets(10, 10, 10, 10));
	    gpAdmin.setVgap(10);
	    gpAdmin.setHgap(10);
	    gpAdmin.setAlignment(Pos.CENTER);
	    Button listarUtilizadoresBtn = new Button("Listar Utilizadores");
	    Button listarRecursosBtn = new Button("Listar Recursos");
	    Button adicionarRecursoBtn = new Button("Adicionar Recurso");
	    Button listarDoacoesBtn = new Button("Listar Doações");
	    Button listarTiposBtn = new Button("Listar Tipos");
	    Button sairBtn = new Button("Sair");

	    listarUtilizadoresBtn.setOnAction(e -> userservice. mostrarUtilizadoresAdmin(idAdmin));
		RecursosClient recursosClient = new RecursosClient();
		listarRecursosBtn.setOnAction(e -> recursosClient.mostrarRecursoAdmin(idAdmin));
		adicionarRecursoBtn.setOnAction(e -> {
			Recursos novoRecurso = recursosClient.criaRecursos();
			recursosClient.adicionarRecurso(novoRecurso);
		});
		listarDoacoesBtn.setOnAction(e -> {
		DonationClient donationClient = new DonationClient();
		donationClient.listarDoacoes();
		});
		TipoClient tipoClient = new TipoClient();
	    listarTiposBtn.setOnAction(e -> tipoClient.mostrarTipoAdmin());
	    sairBtn.setOnAction(e -> menuAdminStage.close());
	    gpAdmin.add(listarUtilizadoresBtn, 0, 0);
	    gpAdmin.add(listarRecursosBtn, 1, 0);
	    gpAdmin.add(adicionarRecursoBtn, 0, 1);
	    gpAdmin.add(listarDoacoesBtn, 1, 1);
	    gpAdmin.add(listarTiposBtn, 2, 0);
	    gpAdmin.add(sairBtn, 2, 1);
	    Scene sceneAdmin = new Scene(gpAdmin, 600, 400);
		sceneAdmin.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
	    menuAdminStage.setScene(sceneAdmin);
	    menuAdminStage.showAndWait();
	}
	public Administrador getAdministradorById(int idAdmin) {
		ResponseEntity<Administrador> response = restTemplate.getForEntity(rootAPIURL + "/" + idAdmin, Administrador.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		} else {
			System.out.println("Nothing found");
		}
		return null;
	}
    public void menuAdminRecurso(Stage cenarioPrincipal, int idAdmin) {
		Stage menuAdminStage = new Stage();
		RecursosClient recursosClient = new RecursosClient();
		Administrador admin = getAdministradorById(idAdmin);
		Recursos recurso = recursosClient.getRecursosById(admin.getIdAdmin(),admin.getRecurso().getIdRecurso());
	    menuAdminStage.setTitle("Menu Administrador Geral");
	    GridPane gpAdmin = new GridPane();
	    gpAdmin.setPadding(new Insets(10, 10, 10, 10));
	    gpAdmin.setVgap(10);
	    gpAdmin.setHgap(10);
	    gpAdmin.setAlignment(Pos.CENTER);
	    Button verrecurso = new Button("Ver Recurso");
	    Button atualizarRecurso = new Button("Atualizar Recurso");
		Button atualizarVagas = new Button("Atualizar Vagas");
		atualizarVagas.setOnAction(e -> {
			HospitalClient hospitalClient = new HospitalClient();
			Hospital hospital = hospitalClient.getHospitalByIdRecurso(recurso.getIdRecurso());
			recursosClient.mostrarTelaAtualizarVagas(hospital);
		});
		Button sairBtn = new Button("Sair");
		sairBtn.setOnAction(e -> menuAdminStage.close());
		gpAdmin.add(sairBtn, 4, 0);
	    gpAdmin.add(verrecurso, 0, 0);
	    gpAdmin.add(atualizarRecurso, 1, 0);
		gpAdmin.add(atualizarVagas, 2, 0);
		verrecurso.setOnAction(e -> {
			recursosClient.mostrarRecursoEspecifico(recurso);
		});
		atualizarRecurso.setOnAction(e -> {
			recursosClient.mostrarTelaAtualizarRecurso(recurso);
		});
	    Scene sceneAdmin = new Scene(gpAdmin, 600, 400);
		sceneAdmin.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
		menuAdminStage.initModality(Modality.APPLICATION_MODAL);
	    menuAdminStage.setScene(sceneAdmin);
	    menuAdminStage.showAndWait();
	}
}
