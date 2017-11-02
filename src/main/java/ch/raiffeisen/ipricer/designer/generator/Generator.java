package ch.raiffeisen.ipricer.designer.generator;

import ch.raiffeisen.ipricer.designer.page.component.DesignComponent;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

public class Generator {


    public static final String METHOD_TEMPLATE = "/templates/methodDefinition.vm";
    private VelocityContext velocityContext;

    public Generator(){
        initTemplateEngine();
    }



    public String generateMethod(String method, String methodLabel, String parentLabel, String optionLabel, List<DesignComponent> komponenten){


    //TODO Validation for method file: sind alle Felder vorhanden und abgefüllt?

        velocityContext.put( "methodName", method );
        velocityContext.put("methodLabel", methodLabel);
        velocityContext.put("parentLabel", parentLabel);
        velocityContext.put("optionLabel", optionLabel);
        velocityContext.put("komponenten", komponenten);

        Template template = null;

        try
        {
            template = Velocity.getTemplate(METHOD_TEMPLATE);
        }
        catch( ResourceNotFoundException rnfe )
        {
            // couldn't find the template
        }
        catch( ParseErrorException pee )
        {
            // syntax error: problem parsing the template
        }
        catch( MethodInvocationException mie )
        {
            // something invoked in the template
            // threw an exception
        }
        catch( Exception e )
        {}

        StringWriter sw = new StringWriter();

        template.merge( velocityContext, sw );

        return sw.toString();

    }

    private void initTemplateEngine(){
        Properties p = new Properties();
        try(InputStream stream = this.getClass().getResourceAsStream("/velocity.properties")){
            p.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Velocity.init(p);
        velocityContext = new VelocityContext();
    }

    public String toDataSection(){
        return "DataSection";
    };
    public String toInitSection(){
        return "Init";
    };
    public String toTypeMaskSection(){
        return "typeMask";
    };
    public String toUnderlyingListSection(){
        return "underlyingList";
    };
    public String toUnderlyingMaskSection(){
        return "";
    };
    public String toOptionListSection(){
        return "";
    };
    public String toOptionMaskSection(){
        return "";
    };
}
