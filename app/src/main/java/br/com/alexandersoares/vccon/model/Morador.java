package br.com.alexandersoares.vccon.model;

/**
 * Created by alexl on 09/05/2017.
 */

public class Morador {

    private int id;
    private Usuario user_id;
    private String name;
    private String parentesco;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Morador() {
        super();
    }

    public Morador(String name, String parentesco, Usuario user_id) {
        super();
        this.name = name;
        this.parentesco = parentesco;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

}
