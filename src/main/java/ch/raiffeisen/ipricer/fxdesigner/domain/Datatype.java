package ch.raiffeisen.ipricer.fxdesigner.domain;

public enum Datatype {
    Zahl("PriceField"), Date("DateField"), String("StringField");


    private String ipricerField;

    Datatype(String ipricerField) {
        this.ipricerField = ipricerField;
    }

    public java.lang.String getIpricerField() {
        return ipricerField;
    }


}
