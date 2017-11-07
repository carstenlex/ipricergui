package ch.raiffeisen.ipricer.fxdesigner.generator;

import ch.raiffeisen.ipricer.fxdesigner.FXDesigner;
import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Generator {

    public static final String KEIN_WERT = "kein Wert";
    public static final String KOMPONENTE = "komponente";
    Configuration cfg;


    public Generator(){
        cfg = new Configuration(Configuration.VERSION_2_3_27);

// Specify the source where the template files come from. Here I set a
// plain directory for it, but non-file-system sources are possible too:
        cfg.setClassForTemplateLoading(this.getClass(),"/generator");

// Set the preferred charset template files are stored in. UTF-8 is
// a good choice in most applications:
        cfg.setDefaultEncoding("UTF-8");

// Sets how errors will appear.
// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        cfg.setLogTemplateExceptions(false);

// Wrap unchecked exceptions thrown during template processing into TemplateException-s.
        cfg.setWrapUncheckedExceptions(true);
    }

    public String generateDefinitionFile(FXDesigner fxDesigner) throws  GeneratorException {
        Map<String, Object> placeholders = new HashMap<>();

        validateData(fxDesigner);
        fillPlaceholders(placeholders, fxDesigner);



        try  {
            Writer out = new OutputStreamWriter(System.out); //TODO . try Resource wenn es nicht System.out ist
            Template template = cfg.getTemplate("methodDefinition.ftlh");
            template.process(placeholders,out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void fillPlaceholders(Map<String, Object> placeholders, FXDesigner fxDesigner) {
        placeholders.put("mp",MethodProperties.from(fxDesigner));
        placeholders.put("dataComponents",getDataComponents(fxDesigner));
        placeholders.put("initComponents",getInitComponents(fxDesigner));
        placeholders.put("allComponents",fxDesigner.getAllDesignComponents());
    }

    private List<DesignComponent> getInitComponents(FXDesigner fxDesigner) {
        List<DesignComponent> collect = fxDesigner.getAllDesignComponents().stream().filter(c -> !c.properties.isSeparator && StringUtils.isNotBlank(c.properties.initValue)).collect(Collectors.toList());
        return collect;
    }

    private List<DesignComponent> getDataComponents(FXDesigner fxDesigner) {
        List<DesignComponent> alleOhneSeparators = fxDesigner.getAllDesignComponents().stream().filter(c -> !c.properties.isSeparator).collect(Collectors.toList());
        Optional<DesignComponent> separator = fxDesigner.getAllDesignComponents().stream().filter(c -> c.properties.isSeparator).findFirst();

        if (separator.isPresent()){
            List<DesignComponent> dataComponents = new ArrayList<>();
            dataComponents.addAll(alleOhneSeparators);
            dataComponents.add(separator.get());
            return dataComponents;
        }else{
            return alleOhneSeparators;
        }

    }

    private void validateData(FXDesigner fxDesigner) throws GeneratorException{
        ErrorReport errorReport = new ErrorReport();
        validateMethodDefinition(errorReport,MethodProperties.from(fxDesigner));
        validateKomponenten(errorReport,fxDesigner.getAllDesignComponents(), fxDesigner);
        if (errorReport.hasErrors()) {
            throw new GeneratorException(errorReport);
        }
    }

    private void validateKomponenten(ErrorReport errorReport, List<DesignComponent> allDesignComponents, FXDesigner fxDesigner) {
        if (allDesignComponents == null || allDesignComponents.size() ==0){
            errorReport.addError("komponenten","Keine DesignComponents definiert");
        }
        validateInternalFieldnames(errorReport,getDataComponents(fxDesigner)); //in dieser Liste ist nur einmal ein Separator enthalten
        validateExternalFieldnames(errorReport,allDesignComponents);
    }

    private void validateExternalFieldnames(ErrorReport errorReport, List<DesignComponent> allDesignComponents) {
        allDesignComponents.stream().filter(c -> StringUtils.isBlank(c.properties.externalName)).forEach(c -> errorReport.addError(c.properties.internalFieldName,"ExternalName nicht gesetzt"));
    }

    private void validateInternalFieldnames(ErrorReport errorReport, List<DesignComponent> dataComponents) {
        dataComponents.stream().filter(c -> StringUtils.isBlank(c.properties.internalFieldName)).forEach(c -> errorReport.addError(KOMPONENTE,"InternalFieldName nicht gesetzt:"+c.properties.toString()));

        Map<String, Long> doppelteInternalfieldnamesMitAnzahl = dataComponents.stream().collect(Collectors.groupingBy(c -> c.properties.internalFieldName, Collectors.counting()))
                .entrySet().stream().filter(doppelte -> doppelte.getValue() > 1).collect(Collectors.toMap(doppelte -> doppelte.getKey(), doppelte -> doppelte.getValue()));

        if (doppelteInternalfieldnamesMitAnzahl.isEmpty()) {
            return;
        }


        for(Map.Entry<String, Long> entry: doppelteInternalfieldnamesMitAnzahl.entrySet()) {
            errorReport.addError(KOMPONENTE,"InternalFieldname <"+entry.getKey()+"> ist mehrfach vergeben: "+entry.getValue());
        }

    }

    private void validateMethodDefinition(ErrorReport errorReport, MethodProperties methodProperties) {
        if (StringUtils.isEmpty(methodProperties.methodName)){
            errorReport.addError("methodName", KEIN_WERT);
        }
        if (StringUtils.isEmpty(methodProperties.methodLabel)){
            errorReport.addError("methodLabel",KEIN_WERT);
        }
        if (StringUtils.isEmpty(methodProperties.parentLabel)){
            errorReport.addError("parentLabel",KEIN_WERT);
        }
        if (StringUtils.isEmpty(methodProperties.childLabel)){
            errorReport.addError("childLabel",KEIN_WERT);
        }

    }

}
