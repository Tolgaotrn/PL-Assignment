package proj10;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "Cozinhas_Comunitarias")
public class CozinhaComunitaria extends Recursos {
    @Column(name = "tipo_comida")
    private String tipoComida;

    @Column(name = "capacidade")
    private int capacidade;

    @Column(name = "custos_acrescidos")
    private String custosAcrescidos;

    @Column(name = "informacao_extra")
    private String informacaoExtra;

    // ADICIONAR GETTERS E SETTERS
    public String getTipoComida() {
        return tipoComida;
    }
    public void setTipoComida(String tipoComida) {
        this.tipoComida = tipoComida;
    }
    public int getCapacidade() {
        return capacidade;
    }
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
    public String getCustosAcrescidos() {
        return custosAcrescidos;
    }
    public void setCustosAcrescidos(String custosAcrescidos) {
        this.custosAcrescidos = custosAcrescidos;
    }
    public String getInformacaoExtra() {
        return informacaoExtra;
    }
    public void setInformacaoExtra(String informacaoExtra) {
        this.informacaoExtra = informacaoExtra;
    }
}

