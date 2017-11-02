package ch.raiffeisen.ipricer.designer.generator;

import ch.raiffeisen.ipricer.designer.PropertiesPanel;
import ch.raiffeisen.ipricer.designer.domain.RecordType;
import ch.raiffeisen.ipricer.designer.domain.RoleAccess;
import ch.raiffeisen.ipricer.designer.page.Page;
import ch.raiffeisen.ipricer.designer.page.PageMethod;
import ch.raiffeisen.ipricer.designer.page.component.Datatype;
import ch.raiffeisen.ipricer.designer.page.component.DesignComponent;
import ch.raiffeisen.ipricer.designer.page.component.ipricer.IPricerProperties;
import ch.raiffeisen.ipricer.designer.page.component.types.DesignComponentParam;

import java.util.ArrayList;
import java.util.List;

public class GeneratorTest {
    @org.junit.Test
    public void generateMethod() throws Exception {
        Generator generator = new Generator();
        List<DesignComponent> komponenten = createKomponenten();
        String s = generator.generateMethod("AP", "sectionLabelV", "methodlAbelV", "optionlAbelV", komponenten);

        System.out.println(s);
    }

    private List<DesignComponent> createKomponenten() {
        List<DesignComponent> komponenten = new ArrayList<>();
        PropertiesPanel propertiesPanel = new PropertiesPanel();

        int zellex = 0;
        int zelley =0;

        Page pageMethod = new PageMethod(propertiesPanel);
        DesignComponentParam paramString = new DesignComponentParam(pageMethod, "ask source", zellex++, zelley++);
        IPricerProperties p = paramString.getProperties();
        p.datatype = Datatype.Zahl;
        p.internalFieldName = "myInternalFieldname";
        p.labelText = "daLabelText";
        p.roleAccess = RoleAccess.radmin;
        p.recordType = RecordType.C;
        p.externalName = "DaExternalName";
        p.procedureNameForValues = "GetJaNein";
        p.maxLength = 30;
        p.gridY = 0;
        p.gridX = 1;
        p.width = 80;
        komponenten.add(paramString);


        paramString = new DesignComponentParam(pageMethod, "ask source", zellex++, zelley++);
        p = paramString.getProperties();
        p.datatype = Datatype.Zahl;
        p.internalFieldName = "typeMaskFieldname";
        p.labelText = "daLabelText";
        p.roleAccess = RoleAccess.radmin;
        p.recordType = RecordType.B;
        p.externalName = "DaExternalName";
        p.procedureNameForValues = null;
        p.maxLength = 30;
        p.initValue = "InitializeIT";
        p.gridY = 0;
        p.gridX = 2;
        p.width = 150;
        komponenten.add(paramString);

        for (RecordType recordType : RecordType.values()) {
            paramString = new DesignComponentParam(pageMethod, recordType.name()+ " ask source", zellex, zelley++);
            p = paramString.getProperties();
            p.datatype = Datatype.Zahl;
            p.internalFieldName = recordType.name()+"-Fieldname";
            p.labelText = "daLabelText for "+recordType.name();
            p.roleAccess = RoleAccess.radmin;
            p.recordType = recordType;
            p.externalName = "DaExternalName";
            p.procedureNameForValues = null;
            p.maxLength = 30;
            p.initValue = "InitializeIT";
            p.gridY = 0;
            p.gridX = 2;
            p.width = 150;

            komponenten.add(paramString);
        }
        return komponenten;
    }

}