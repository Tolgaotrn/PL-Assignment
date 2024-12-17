package rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import model.Abrigo;
import model.Hospital;

public class AbrigoClient {
    private RestTemplate restTemplate = new RestTemplate();

	private static final String rootAPIURL = "http://localhost:8080/api/abrigos";

	public Abrigo getAbrigoById(Long id) {

		ResponseEntity<Abrigo> response = restTemplate.getForEntity(rootAPIURL + "/" + id.toString(), Abrigo.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Abrigo body = response.getBody();
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
    public List<Abrigo> getAllAbrigo() {

		ResponseEntity<Abrigo[]> response = restTemplate.getForEntity(rootAPIURL, Abrigo[].class);

		List<Abrigo> abrigos = new ArrayList<>();

		if (response.getStatusCode().is2xxSuccessful()) {

			if (response.getBody() != null) {

				for (Abrigo h : response.getBody()) {
					abrigos.add(h);
				}
				return abrigos;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return abrigos;

	}







}
