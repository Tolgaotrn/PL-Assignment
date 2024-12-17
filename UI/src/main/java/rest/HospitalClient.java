package rest;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ch.qos.logback.classic.pattern.Util;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Favoritos;
import model.Hospital;
import model.Recursos;
import model.Utilizador;

public class HospitalClient {
	private RestTemplate restTemplate = new RestTemplate();

	private static final String rootAPIURL = "http://localhost:8080/api/hospitais";

	public Hospital getHospitalById(Long id) {

		ResponseEntity<Hospital> response = restTemplate.getForEntity(rootAPIURL + "/" + id.toString(), Hospital.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Hospital body = response.getBody();
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

	public Hospital getHospitalByIdRecurso(int id) {

		ResponseEntity<Hospital> response = restTemplate.getForEntity(rootAPIURL + "/recurso/" + id, Hospital.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Hospital body = response.getBody();
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

	public boolean updateHospital(Hospital hospital) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Hospital> requestEntity = new HttpEntity<Hospital>(hospital, headers);
		ResponseEntity<Hospital> response = restTemplate.exchange(rootAPIURL + "/" + hospital.getIdRecurso(), HttpMethod.PUT, requestEntity, Hospital.class);
		
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Updated");
		} else {
			System.out.println("Nothing found");
		}

		return response.getStatusCode().is2xxSuccessful();
	}

	public List<Hospital> getAllHospital() {

		ResponseEntity<Hospital[]> response = restTemplate.getForEntity(rootAPIURL, Hospital[].class);

		List<Hospital> hospitais = new ArrayList<>();

		if (response.getStatusCode().is2xxSuccessful()) {

			if (response.getBody() != null) {

				for (Hospital h : response.getBody()) {
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

	public boolean saveHospital(Hospital hospital) {

		if (!Objects.isNull(hospital.getHospitalId()) && hospital.getHospitalId() != 0) {
			return this.updateHospital(hospital);
		}

		ResponseEntity<Hospital> response = restTemplate.postForEntity(rootAPIURL, hospital, Hospital.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Hospital body = response.getBody();
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

	public boolean deleteHospital(int idHospital) {


		ResponseEntity<Void> response = restTemplate.exchange(rootAPIURL + "/" + idHospital, HttpMethod.DELETE, null, Void.class);
		
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Deleted");
		} else {
			System.out.println("Nothing found");
		}

		return response.getStatusCode().is2xxSuccessful();

	}

public void mostrarHospital(int idRecurso) {
    // Recupera o hospital com base no idRecurso
    Hospital hospital = getHospitalByIdRecurso(idRecurso);
    
    if (hospital != null) {
        Stage hospitalStage = new Stage();
        hospitalStage.setTitle("Informações do Hospital");
        
        VBox vbox = new VBox(10); 
        vbox.setPadding(new javafx.geometry.Insets(20));
        
        // Labels com as informações do hospital
        Label nomeLabel = new Label("Nome do Hospital: " + hospital.getNome());
        nomeLabel.getStyleClass().add("text");
        
        Label telefoneLabel = new Label("Telefone: " + hospital.getTelefone());
        Label enderecoLabel = new Label("Distrito: " + hospital.getLocalizacao().getDistrito());
        Label enderecoLabel2 = new Label("Cidade: " + hospital.getLocalizacao().getCidade());
        
        Label especialidadesLabel = new Label("Especialidades: " + hospital.getEspecialidades());
        Label vagasLabel = new Label("Vagas: " + hospital.getVagas());
        Label custosAcrescidosLabel = new Label("Custos Acrescidos: " + hospital.getCustosAcrescidos());
        Label informacaoExtraLabel = new Label("Informação Extra: " + hospital.getInformacaoExtra());
        vbox.getChildren().addAll(nomeLabel, enderecoLabel, telefoneLabel, enderecoLabel2, especialidadesLabel, vagasLabel, custosAcrescidosLabel, informacaoExtraLabel);
        Button voltarButton = new Button("Voltar");
        voltarButton.setOnAction(e -> {
            hospitalStage.close();  // Fecha a janela atual (informações do hospital)
        });
        vbox.getChildren().addAll(voltarButton);
        Scene hospitalScene = new Scene(vbox, 400, 300);
        hospitalScene.getStylesheets().add(getClass().getResource("/rest/style.css").toExternalForm());
		hospitalStage.setMinWidth(500);
		hospitalStage.setMinHeight(400);
        hospitalStage.setScene(hospitalScene);
		hospitalStage.initModality(Modality.APPLICATION_MODAL);
        hospitalStage.showAndWait();
    } else {
        // Caso o hospital não seja encontrado
        System.out.println("Hospital não encontrado");
    }
}
}

