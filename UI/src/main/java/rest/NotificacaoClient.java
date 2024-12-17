package rest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import model.Favoritos;
import model.Hospital;
import model.Notificacoes;
import model.Recursos;
public class NotificacaoClient {
    private RestTemplate restTemplate = new RestTemplate();

	private static final String rootAPIURL = "http://localhost:8080/api/notificacoes";

	public Notificacoes getNotificacoesById(Long id) {

		ResponseEntity<Notificacoes> response = restTemplate.getForEntity(rootAPIURL + "/" + id.toString(), Notificacoes.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Notificacoes body = response.getBody();
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
    ///user/{idUtilizador}
    public List<Notificacoes> getNotificacoesByUser(int idUtilizador) {
        ResponseEntity<Notificacoes[]> response = restTemplate.getForEntity(rootAPIURL + "/user/" + idUtilizador, Notificacoes[].class);

		List<Notificacoes> notificacoes = new ArrayList<>();

		if (response.getStatusCode().is2xxSuccessful()) {

			if (response.getBody() != null) {

				for (Notificacoes n : response.getBody()) {
					notificacoes.add(n);
				}
				return notificacoes;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return notificacoes;

    }
	public boolean updateNotificacoes(Notificacoes notificacoes) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Notificacoes> requestEntity = new HttpEntity<Notificacoes>(notificacoes, headers);
		ResponseEntity<Notificacoes> response = restTemplate.exchange(rootAPIURL, HttpMethod.PUT, requestEntity, Notificacoes.class);
		
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Updated");
		} else {
			System.out.println("Nothing found");
		}

		return response.getStatusCode().is2xxSuccessful();
	}

	public List<Notificacoes> getAllNotificacoes() {

		ResponseEntity<Notificacoes[]> response = restTemplate.getForEntity(rootAPIURL, Notificacoes[].class);

		List<Notificacoes> notificacoes = new ArrayList<>();

		if (response.getStatusCode().is2xxSuccessful()) {

			if (response.getBody() != null) {

				for (Notificacoes n : response.getBody()) {
					notificacoes.add(n);
				}
				return notificacoes;
			} else {
				System.out.println("No body");
			}
		} else {
			System.out.println("Nothing found");
		}
		return notificacoes;

	}

	public boolean saveNotificacoes(Notificacoes notificacoes) {

		if (!Objects.isNull(notificacoes.getId()) && notificacoes.getId() != 0) {
			return this.updateNotificacoes(notificacoes);
		}

		ResponseEntity<Notificacoes> response = restTemplate.postForEntity(rootAPIURL, notificacoes, Notificacoes.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			Notificacoes body = response.getBody();
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
    public boolean deleteNotificacoes(int id) {
        ResponseEntity<Void> response = restTemplate.exchange(rootAPIURL + "/user/" + id, HttpMethod.DELETE, null, Void.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Deleted");
        } else {
            System.out.println("Nothing found");
        }
        return response.getStatusCode().is2xxSuccessful();
    }
	public void notificarFavoritos(Recursos recursoSelecionado) {
		FavoritosClient favoritosClient = new FavoritosClient();
		List<Favoritos> favoritos = favoritosClient.getFavoritosByRecurso(recursoSelecionado.getIdRecurso());
		for (Favoritos f : favoritos) {
			Notificacoes notificacoes = new Notificacoes();
			notificacoes.setId_utilizador(f.getUtilizador().getIdUtilizador());
			notificacoes.setData(new Date(System.currentTimeMillis()));
			notificacoes.setId_recurso(recursoSelecionado.getIdRecurso());
			notificacoes.setDescricao("As vagas em " + recursoSelecionado.getNome() + " estão a acabar!");
			this.saveNotificacoes(notificacoes);
		}
	}
}
