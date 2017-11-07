package ch.raiffeisen.ipricer.fxdesigner.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Rundungsregel {
    KEINE("lblRundungsRegelKeineRundung", "Keine Rundung"),
    FUENF_BASISPUNKTE("lblRundungsRegel5Basispunkte", "kaufm. auf 5-er"),
    EIN_ACHTEL_PROZENT("lblRundungsRegel18Prozent", "1/8 Prozent"),
    DREI_NACHKOMMASTELLEN("lblRundungsRegel3Nachkommastellen", "Drei Nachkommastellen");



    private String messageKey;
    private String codeUndAnzeigeIPricer;

    Rundungsregel(){

    }

    public static List<String> getNames(){
        return Arrays.stream(values()).map(v -> v.name()).collect(Collectors.toList());
    }

    Rundungsregel(String messageKey, String codeIPricer){
        this.messageKey = messageKey;
        this.codeUndAnzeigeIPricer = codeIPricer;
    }

    public static Rundungsregel fromCodeUndAnzeigeIPricer(String iPricerCode) {
        for (Rundungsregel regel : Rundungsregel.values()) {
            if (regel.getCodeUndAnzeigeIPricer().equals(iPricerCode)) {
                return regel;
            }
        }
        return KEINE;
    }

    public String getCodeUndAnzeigeIPricer() {
        return codeUndAnzeigeIPricer;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public BigDecimal round(BigDecimal value){
        switch (this) {
            case KEINE:
                return value;
            case FUENF_BASISPUNKTE:
                return round5Basispunkte(value);
            case EIN_ACHTEL_PROZENT:
                return roundEinAchtelProzent(value);
            case DREI_NACHKOMMASTELLEN:
                return roundDreiNachkommastellen(value);

            default:
                return value;
        }

    }

    private BigDecimal roundDreiNachkommastellen(BigDecimal value) {
        if (value==null)
            return null;

        return precisionRound(value,3);
    }

    /**
     * kaufmännische 5er-Rundung auf der 2. Nachkommastelle
     * @param value
     * @return
     */
    private BigDecimal round5Basispunkte(BigDecimal value) {
        if (value==null)
            return null;

        BigDecimal result = roundByIncrement(value, new BigDecimal("0.05"));
        result.setScale(2);
        return result;
    }

    private BigDecimal roundEinAchtelProzent(BigDecimal value) {
        if (value==null)
            return null;

        BigDecimal result = roundByIncrement(value, new BigDecimal("0.125"));
        result.setScale(3);
        return result;
    }

    private BigDecimal roundByIncrement(BigDecimal value, BigDecimal increment) {
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        BigDecimal divided = value.divide(increment,0,roundingMode);
        return divided.multiply(increment);
    }


    private static BigDecimal precisionRound(BigDecimal value, int precision) {
        if (value != null) {
            return value.setScale(precision, RoundingMode.HALF_UP);
        }
        return value;
    }
}
