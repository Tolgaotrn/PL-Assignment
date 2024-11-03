package proj10;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "Abrigos")
public class Abrigo extends Recursos {
    @Column(name = "vagas")
    private int vagas;

    @Column(name = "custos_acrescidos")
    private String custosAcrescidos;

    @Column(name = "informacao_extra")
    private String informacaoExtra;

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
    public String getInformacaoExtra() {
        return informacaoExtra;
    }
    public void setInformacaoExtra(String informacaoExtra) {
        this.informacaoExtra = informacaoExtra;
    }

    
}



