package ch.raiffeisen.ipricer.fxdesigner.generator;

import ch.raiffeisen.ipricer.fxdesigner.FXDesigner;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import javafx.scene.control.TextField;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Generator {

    public static final String KEIN_WERT = "kein Wert";
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



        try (Writer out = new OutputStreamWriter(System.out)) {
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
        placeholders.put("name", "Carsten");
    }

    private void validateData(FXDesigner fxDesigner) throws GeneratorException{
        ErrorReport errorReport = new ErrorReport();
        validateMethodDefinition(errorReport,fxDesigner.methodName.getText(), fxDesigner.methodLabel.getText(), fxDesigner.parentLabel.getText(), fxDesigner.childLabel.getText());
        if (errorReport.hasErrors()) {
            throw new GeneratorException(errorReport);
        }
    }

    private void validateMethodDefinition(ErrorReport errorReport, String methodName, String methodLabel, String parentLabel, String childLabel) {
        if (StringUtils.isEmpty(methodName)){
            errorReport.addError("methodName", KEIN_WERT);
        }
        if (StringUtils.isEmpty(methodLabel)){
            errorReport.addError("methodLabel",KEIN_WERT);
        }
        if (StringUtils.isEmpty(parentLabel)){
            errorReport.addError("parentLabel",KEIN_WERT);
        }
        if (StringUtils.isEmpty(childLabel)){
            errorReport.addError("childLabel",KEIN_WERT);
        }

    }

}
