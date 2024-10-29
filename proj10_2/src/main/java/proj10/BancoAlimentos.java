package proj10;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Bancos_Alimentos")
public class BancoAlimentos extends Recursos {
    @Column(name = "tipos_comida_disponivel")
    private String tiposComidaDisponivel;

    @Column(name = "custos_acrescidos")
    private String custosAcrescidos;

    @Column(name = "informacao_extra")
    private String informacaoExtra;

    //ADICIONAR GETTERS E SETTERS
    public String getTiposComidaDisponivel() {
        return tiposComidaDisponivel;
    }
    public void setTiposComidaDisponivel(String tiposComidaDisponivel) {
        this.tiposComidaDisponivel = tiposComidaDisponivel;
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


