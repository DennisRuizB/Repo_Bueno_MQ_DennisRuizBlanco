package edu.upc.dsa.models;

public class PuntoDeInteres {
    int CordHoritzontal;
    int CordVertical;
    ElementType tipo;

    public enum ElementType {
        DOOR, WALL, BRIDGE, POTION, SWORD, COIN, GRASS, TREE
    }

    public PuntoDeInteres() {
    }

    public PuntoDeInteres(int CordHoritzontal, int CordVertical, ElementType tipo) {
        this.setCordHoritzontal(CordHoritzontal);
        this.setCordVertical(CordVertical);
        this.setTipo(tipo);
    }

    public int getCordHoritzontal() {
        return CordHoritzontal;
    }

    public void setCordHoritzontal(int cordHoritzontal) {
        CordHoritzontal = cordHoritzontal;
    }

    public int getCordVertical() {
        return CordVertical;
    }

    public void setCordVertical(int cordVertical) {
        CordVertical = cordVertical;
    }

    public ElementType getTipo() {
        return tipo;
    }

    public void setTipo(ElementType tipo) {
        this.tipo = tipo;
    }
}
