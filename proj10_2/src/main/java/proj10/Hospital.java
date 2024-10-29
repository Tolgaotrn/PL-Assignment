package proj10;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hospitais")
public class Hospital implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hospital")
    private int idHospital;

    @ManyToOne
    @JoinColumn(name = "id_recurso", nullable = false)
    private Recursos recursos;

    @Column(name = "especialidades", columnDefinition = "TEXT")
    private String especialidades;

    @Column(name = "vagas")
    private int vagas;

    @Column(name = "custos_acrescidos", length = 255)
    private String custosAcrescidos;



    public int getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(int idHospital) {
        this.idHospital = idHospital;
    }

    public Recursos getRecurso() {
        return recursos;
    }

    public void setRecurso(Recursos recurso) {
        this.recursos = recurso;
    }

    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
        this.especialidades = especialidades;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public String getCustosAcrescidos() {
        return custosAcrescidos;
    }

    public void setCustosAcrescidos(String custosAcrescidos) {
        this.custosAcrescidos = custosAcrescidos;
    }
}

