package br.com.alexandersoares.vccon.model;

import java.io.Serializable;

/**
 * Created by alexl on 09/05/2017.
 */

public class Animal implements Serializable {

    private int id;
    private String user_id;
    //private String foto;
    private String name;
    private String tipo;

    public Animal() {
        super();
    }

    public Animal(String name, String tipo, String user_id) {
        super();
        this.name = name;
        this.tipo = tipo;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
