package proj10;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Favoritos")
public class Favorito {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_favorito")  // Corrigir o nome da coluna
    private int idFavorito;

    @Column(name = "id_utilizador", nullable = false)
    private int idUtilizador;

    @Column(name = "id_recurso", nullable = false)
    private int idRecurso;

    @Column(name = "data_favorito", nullable = false)
    private Date dataFavorito;

    public int getIdFavorito() {
        return idFavorito;
    }

    public int getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(int idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public Date getDataFavorito() {
        return dataFavorito;
    }

    public void setDataFavorito(Date dataFavorito) {
        this.dataFavorito = dataFavorito;
    }

    public void setIdRecurso(int idRecurso) {
       this.idRecurso = idRecurso;
    }
}
