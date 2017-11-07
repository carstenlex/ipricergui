package ch.raiffeisen.ipricer.fxdesigner.generator;

import java.util.*;

public class ErrorReport {
    LinkedHashMap<String,List<String>> errors = new LinkedHashMap<>();

    public ErrorReport addError(String key, String error) {
        List<String> listOfErrors = errors.get(key);
        if (listOfErrors == null) {
            listOfErrors = new ArrayList<>();
            errors.put(key,listOfErrors);
        }
        listOfErrors.add(error);

        return this;
    }

    public boolean hasErrors(){
        return !errors.keySet().isEmpty();
    }

    @Override
    public String toString() {

        String result = "Fehler beim Generieren:\n";
        result += "******************************\n";
        for(Map.Entry<String, List<String>> entry: errors.entrySet()){
            String key = entry.getKey();
            List<String> value = entry.getValue();

            result += "Ursache: " + key+"\n";
            for(String fehler: value){
                result += fehler +"\n";
            }
        }

        return result;
    }
}
