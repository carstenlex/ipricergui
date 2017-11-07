package ch.raiffeisen.ipricer.fxdesigner.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ProcedureNames {
    GetAllUsersAndGroups(RoleAccess.getNames()),
    GetJa(Arrays.asList("","Ja")),
    GetRundungsregeln(Rundungsregel.getNames()),
    GetYesNo(Arrays.asList("Ja","Nein"));

    List<String> values;

    private ProcedureNames(List<String> values){
        this.values = values;
    }


    public List<String> getValues() {
        return values;
    }
}
