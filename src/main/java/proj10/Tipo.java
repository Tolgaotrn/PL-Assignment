package proj10;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//type of resource
//e.g. hospital, shalter, etc.
//this class is used to store the type of resource
//"dad class"
//the other class will inherit from this one
//when the admin creates a new type of resource it will be stored as default type with the default atributes (are in the discord)
@Entity
@Table(name = "Tipo") //table name "type"
public class Tipo { //type class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tipo; //type id

    @Column(nullable = false)
    private String tipo; //type name

    public Tipo() {}

    public Tipo(String tipo) {
        this.tipo = tipo; //set the type name
    }

    public int getIdTipo() {
        return id_tipo; //get the type id
    }

    public void setIdTipo(int id_tipo) {
        this.id_tipo = id_tipo; //set the type id
    }

    public String getTipo() {
        return tipo; //get the type name
    }

    public void setTipo(String tipo) {
        this.tipo = tipo; //set the type name
    }

}

