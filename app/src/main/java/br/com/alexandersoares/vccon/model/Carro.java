package br.com.alexandersoares.vccon.model;

/**
 * Created by alexl on 09/05/2017.
 */

public class Carro {

    private int id;
    private String user_id;
    private String placa;
    private String marca;
    private String modelo;
    private String cor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Carro() {
        super();
    }

    public Carro(String placa, String marca, String modelo, String cor, String user_id) {
        super();
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.user_id = user_id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        return placa + " " +  modelo + " " + marca;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
