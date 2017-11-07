package ch.raiffeisen.ipricer.fxdesigner.generator;

import ch.raiffeisen.ipricer.fxdesigner.FXDesigner;
import org.junit.Test;

import java.io.IOException;

public class GeneratorTest {

    @Test public void testFreemarkerTemplate() {
        Generator generator = new Generator();
        String s = generator.generateDefinitionFile(new FXDesigner());

    }

}