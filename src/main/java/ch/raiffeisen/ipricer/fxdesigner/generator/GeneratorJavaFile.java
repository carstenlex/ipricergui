package ch.raiffeisen.ipricer.fxdesigner.generator;

import ch.raiffeisen.ipricer.definition.DefinitionDSL;
import ch.raiffeisen.ipricer.definition.GeneratorResponse;
import org.apache.commons.io.FileUtils;
import org.eclipse.xtext.validation.Issue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class GeneratorJavaFile {
    public static final String ENCODING = "ISO-8859-1";

    public void write(File definitionFileSave) {
        DefinitionDSL dsl = new DefinitionDSL();
        GeneratorResponse generatorResponse = dsl.generateJavaFromDefinition(definitionFileSave.toURI(),ENCODING);

        System.out.println("Issues");
        for (Issue issue : generatorResponse.getIssues()) {
            System.out.println(issue);
        }

        System.out.println("**************************************Files");
        for (Map.Entry<String, CharSequence> entry : generatorResponse.getGeneratedFiles().entrySet()) {
            System.out.println("**************************** " + entry.getKey() +"====>");
            System.out.println(entry.getValue());

            String default_output_path = entry.getKey().replace("DEFAULT_OUTPUT", "D:/temp/xtext");

            File file = new File(default_output_path);
            try {
                FileUtils.write(file, entry.getValue(), Charset.forName(ENCODING),false);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
