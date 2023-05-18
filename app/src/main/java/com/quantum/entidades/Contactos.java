package com.quantum.entidades;

public class Contactos {
    private int id;
    private String nombre;
    private String Item;
    private String pallet;
    private String resultado;
    private boolean isSelected = false;

    public int getId() {
        return id;
    }

    public int setId(int id) {
        this.id = id;
        return id;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getItem() {
        return Item;
    }

    public String setItem(String item) {
        Item = item;
        return item;
    }

    public String getPallet() {
        return pallet;
    }

    public String  setPallet(String pallet) {
        this.pallet = pallet;
        return pallet;
    }

    public String getResultado() {
        return resultado;
    }

    public String setResultado(String resultado) {
        this.resultado = resultado;
        return resultado;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
