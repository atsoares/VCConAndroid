package br.com.alexandersoares.vccon.model;

/**
 * Created by alexl on 09/05/2017.
 */

public class Animal {

    private int id;
    private Usuario user_id;
    //private String foto;
    private String name;
    private String tipo;

    public Animal() {
        super();
    }

    public Animal(String name, String tipo, Usuario user_id) {
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


}
