package ch.raiffeisen.ipricer.fxdesigner.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ch.raiffeisen.ipricer.fxdesigner.domain.RecordType.*;

public enum Page {
    METHOD(B, E), PARENT(U,L,G,R), CHILD(D,C,P,S);

    private List<RecordType> recordTypes = new ArrayList<>();

    private Page(RecordType ... types){
            recordTypes.addAll(Arrays.asList(types));
    }
    public List<RecordType> getRecordTypes(){
        return recordTypes;
    }

    public RecordType getDefault(){
        return recordTypes.get(0);
    }
}
