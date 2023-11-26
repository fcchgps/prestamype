package com.prestamype.factura.infraestructure.adapter.util;

import java.util.ArrayList;

public class NodoXml
{
    private String nombre;
    private String valor;
    private String tags;
    private int indice;
    private int total;
    public ArrayList lstnode=new ArrayList();
    public ArrayList lstattr=new ArrayList();

    public NodoXml()
    {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
