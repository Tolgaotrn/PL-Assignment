package proj10;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "Centros_Trocas_Roupa")
public class CentroTrocaRoupa extends Recursos {
    @Column(name = "custos_acrescidos")
    private String custosAcrescidos;

    @Column(name = "informacao_extra")
    private String informacaoExtra;

    //ADICIONAR GETTERS E SETTERS
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

