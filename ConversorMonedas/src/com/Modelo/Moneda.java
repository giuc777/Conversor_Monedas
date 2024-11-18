package com.Modelo;

public class Moneda {
    String pais;
    double valorEnUSD;


    public Moneda(String pais, double valorEnUSD) {
        this.pais = pais;
        this.valorEnUSD = valorEnUSD;
    }

    public String getPais() {
        return pais;
    }

    public double getValorEnUSD() {
        return valorEnUSD;
    }

    @Override
    public String toString() {
       return  "Moneda: "+this.pais+" | "+"Precion USD: "+this.valorEnUSD;
    }

    public String convertido(){
        return  "Moneda: "+this.pais+" | "+"Precion convertido: "+this.valorEnUSD;
    }

    public Moneda conversion (Moneda AConvertir, double valorAC){
        double moneda2=AConvertir.getValorEnUSD();
        double rEnUSD = (1/this.valorEnUSD) * valorAC;
        double rFinal =  Double.parseDouble(String.format("%.2f", rEnUSD/(1/moneda2)));
        String nombreMoneda = AConvertir.getPais();


        Moneda m = new Moneda(nombreMoneda,rFinal);

        return m;


    }
}
