package ch.raiffeisen.ipricer.fxdesigner.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum RoleAccess {
    radmin, rtrader, none, supervisor;

    public static List<String> getNames(){
        return Arrays.stream(values()).map(v -> v.name()).collect(Collectors.toList());
    }
}
