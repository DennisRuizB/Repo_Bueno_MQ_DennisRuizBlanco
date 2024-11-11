package edu.upc.dsa.models;

public class RegistroPuntoDeInteres {
    private String userId;
    private int cordHoritzontal;
    private int cordVertical;


    public RegistroPuntoDeInteres() {
    }
    public RegistroPuntoDeInteres(String userId, int cordHoritzontal, int cordVertical) {
        this.userId = userId;
        this.cordHoritzontal = cordHoritzontal;
        this.cordVertical = cordVertical;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCordHoritzontal() {
        return cordHoritzontal;
    }

    public void setCordHoritzontal(int cordHoritzontal) {
        this.cordHoritzontal = cordHoritzontal;
    }

    public int getCordVertical() {
        return cordVertical;
    }

    public void setCordVertical(int cordVertical) {
        this.cordVertical = cordVertical;
    }
}