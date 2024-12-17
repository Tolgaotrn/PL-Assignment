package model;

public class Hospital extends Recursos {
    private String especialidades;
    

    private int vagas;

    private String custosAcrescidos;

    private String informacaoExtra;

    private int idHospital;


    public Hospital() {
    }
    public Hospital(String nome, String telefone, Localizacao localizacao, Tipo tipo, String especialidades, int vagas, String custosAcrescidos, String informacaoExtra) {
        super(nome, telefone, localizacao, tipo);
        this.especialidades = especialidades;
        this.vagas = vagas;
        this.custosAcrescidos = custosAcrescidos;
        this.informacaoExtra = informacaoExtra;
    }
    // IMPLEMENTAR GETTERS E SETTERS
    public void setHospitalId (int idHospital) {
        this.idHospital = idHospital;
    }
    public int getHospitalId () {
        return idHospital;
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

    public String getInformacaoExtra() {
        return informacaoExtra;
    }

    public void setInformacaoExtra(String informacaoExtra) {
        this.informacaoExtra = informacaoExtra;
    }

    public void setIdRecurso(int idRecurso) {
        super.setIdRecurso(idRecurso);
    }
    public int getIdRecurso() {
        return super.getIdRecurso();
    }
}


