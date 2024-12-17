package model;
import jakarta.persistence.*;


public class Donation {
    private int id;
    private int donorId;
    private double amount;
    private java.util.Date date;
    private Recursos recurso;
    private String donorName;
    public Donation() {
    }
    public Donation(int donorId, double amount, java.util.Date date, Recursos recurso, String donorName) {
        this.donorId = donorId;
        this.amount = amount;
        this.date = date;
        this.recurso = recurso;
        this.donorName = donorName;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public Recursos getRecurso() {
        return recurso;
    }

    public void setRecurso(Recursos recurso) {
        this.recurso = recurso;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
    @Override
    public String toString() {
        return "---------\n" +
                "Id da doação: " + id + "\n" +
                "Id do doador: " + donorId + "\n" +
                "Quantidade: " + amount +
                "Data: " + date +
                "Recurso: " + recurso +
                "Nome do doador: " + donorName ;
    }
}
