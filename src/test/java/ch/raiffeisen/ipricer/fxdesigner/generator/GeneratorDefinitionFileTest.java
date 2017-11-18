package ch.raiffeisen.ipricer.fxdesigner.generator;

import ch.raiffeisen.ipricer.fxdesigner.FXDesigner;
import org.junit.Test;

public class GeneratorDefinitionFileTest {

    @Test public void testFreemarkerTemplate() {
        GeneratorDefinitionFile generatorDefinitionFile = new GeneratorDefinitionFile();
        String s = generatorDefinitionFile.generateDefinitionFile(new FXDesigner());

    }

}