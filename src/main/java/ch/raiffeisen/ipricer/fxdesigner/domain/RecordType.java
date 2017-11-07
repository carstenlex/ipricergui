package ch.raiffeisen.ipricer.fxdesigner.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum RecordType {

    B("Global Data Admin record"),
    E("Global Data User record"),

    L("parent/underlying chain record"),
    U("parent/underlying static"),
    G("parent/underlying dynamic"),
    R("parent/underlying result"),

    C("child/option chain record"),
    D("child/option static"),
    P("child/option dynamic"),
    S("child/option result");

    private String description;

    RecordType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static List<String> getNames(){
        return Arrays.stream(values()).map(v -> v.name()).collect(Collectors.toList());
    }
}