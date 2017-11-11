package ch.raiffeisen.ipricer.fxdesigner.parser;

import ch.raiffeisen.ipricer.definition.DefinitionDSL;
import ch.raiffeisen.ipricer.definition.definitionDSL.*;
import ch.raiffeisen.ipricer.definition.definitionDSL.impl.DefinitionImpl;
import ch.raiffeisen.ipricer.fxdesigner.FXDesigner;
import ch.raiffeisen.ipricer.fxdesigner.component.DesignComponentDirectDate;
import ch.raiffeisen.ipricer.fxdesigner.component.DesignComponentDirectString;
import ch.raiffeisen.ipricer.fxdesigner.component.DesignComponentDirectZahl;
import ch.raiffeisen.ipricer.fxdesigner.component.DesignComponentSeparator;
import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.domain.GridGroesse;
import ch.raiffeisen.ipricer.fxdesigner.domain.Page;
import ch.raiffeisen.ipricer.fxdesigner.domain.RecordType;
import ch.raiffeisen.ipricer.fxdesigner.domain.RoleAccess;
import ch.raiffeisen.ipricer.fxdesigner.generator.MethodProperties;
import javafx.scene.layout.GridPane;
import org.eclipse.emf.common.util.EList;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {


    private FXDesigner fxDesigner;

    Map<String, DesignComponent> componentByInternalName = new HashMap<>();


    public Parser(FXDesigner fxDesigner, File file) {
        this.fxDesigner = fxDesigner;
        DefinitionDSL dsl = new DefinitionDSL();
        DefinitionImpl definition = dsl.parseDefinition(file.toURI());

        readLabels(definition);
        Set<String> separators = ermittleSeparators(definition);
        readDataSection(definition.getData(), separators);

        TypeMaskSection typeMaskSection = definition.getTypeMaskSection();
        UnderlyingMaskSection underlyingMaskSection = definition.getUnderlyingMaskSection();
        OptionMaskSection optionMaskSection = definition.getOptionMaskSection();
        readTypeMaskSection(typeMaskSection);
        readUnderlyingMaskSection(underlyingMaskSection);
        readOptionMaskSection(optionMaskSection);

        readInitSection(definition.getInitSection());

    }

    private void readInitSection(InitSection initSection) {
        if (initSection == null || initSection.getInitDefinitions() == null) {
            return;
        }

        for (InitDefinition initDef : initSection.getInitDefinitions()) {
            DesignComponent component = componentByInternalName.get(initDef.getId());
            component.properties.initValue = initDef.getInitValue();
        }
    }



    private void readOptionMaskSection(OptionMaskSection optionMaskSection) {

        if (optionMaskSection == null || optionMaskSection.getOptionMaskDefinitions() == null) {
            return;
        }
        GridGroesse gridGroesse = ermittleGridGroesse(optionMaskSection.getOptionMaskDefinitions());
        fxDesigner.reInitGrid(fxDesigner.childGrid, gridGroesse);
        handleMaskSection(optionMaskSection.getOptionMaskDefinitions(), Page.CHILD);
    }

    private void readUnderlyingMaskSection(UnderlyingMaskSection underlyingMaskSection) {
        if (underlyingMaskSection == null || underlyingMaskSection.getUnderlyingMaskDefinitions() == null) {
            return;
        }

        GridGroesse gridGroesse = ermittleGridGroesse(underlyingMaskSection.getUnderlyingMaskDefinitions());
        fxDesigner.reInitGrid(fxDesigner.parentGrid, gridGroesse);
        handleMaskSection(underlyingMaskSection.getUnderlyingMaskDefinitions(), Page.PARENT);

    }

    private void readTypeMaskSection(TypeMaskSection typeMaskSection) {
        if (typeMaskSection == null || typeMaskSection.getTypeMaskDefinitions() == null) {
            return;
        }
        GridGroesse gridGroesse = ermittleGridGroesse(typeMaskSection.getTypeMaskDefinitions());
        fxDesigner.reInitGrid(fxDesigner.methodGrid, gridGroesse);
        handleMaskSection(typeMaskSection.getTypeMaskDefinitions(), Page.METHOD);
    }

    private GridGroesse ermittleGridGroesse(EList<TypeMaskDefinition> maskDefinitions) {
        GridGroesse gg = new GridGroesse();
        gg.cols = maskDefinitions.stream().map(def -> def.getCol()).reduce(Integer::max).orElse(GridGroesse.DEFAULT_COLS);
        gg.rows = maskDefinitions.stream().map(def -> def.getRow()).reduce(Integer::max).orElse(GridGroesse.DEFAULT_ROWS);
        return gg;

    }

    private void handleMaskSection(List<TypeMaskDefinition> maskDefinitions, Page page) {
        for (TypeMaskDefinition maskDef : maskDefinitions) { //TODO TypeMaskDefinition umbenennen -> MaskDefintiion

            DesignComponent component = componentByInternalName.get(maskDef.getId()); //TODO wenn die hier nicht gefunden wird -> ERROR
            if (component != null) {
                if (maskDef.getSeparator() != null) { //TODO fix it right
                    component = component.copy(); //Separator ist nur einmal definiert im Data-Bereich, wird aber mehrfach benutzt -> für GUI neue Component
                    component.properties.isSeparator = true;
                }
                component.properties.gridX = maskDef.getCol();
                component.properties.gridY = maskDef.getRow();
                component.setWidthProperty(maskDef.getWidth());
                try {
                    GridPane gridPane = fxDesigner.gridFromPage.get(page);
                    System.out.println("GridPane: "+gridPane.getUserData());
                    gridPane.add(component, component.properties.gridX, component.properties.gridY);
                } catch (IllegalArgumentException e) {
                    System.out.println("Doppelte Komponente auf Page: " + page + "; Komponente: " + component.toString());
                }
            } else {
                System.out.println("Komponente " + maskDef.getId() + " in TypeMask definiert, aber nicht in Data");
            }

        }
    }

    /**
     * In der DataSection werden alle Felder definiert. Allerdings wird ein Seprator einfach als String definiert und nur
     * in den einzelnen Mask-Sections mit der Option "-sep" markiert. Daher werden hier noch die Mask-Sections durchlaufen
     * und die Namen der Felder ermittelt, die ein Separator sind
     *
     * @param data
     * @param separators
     */
    private void readDataSection(Data data, Set<String> separators) {
        EList<FieldDefinition> fieldDefinitions = data.getFieldDefinitions();

        for (FieldDefinition fd : fieldDefinitions) {
            FIELD_TYPE fieldType = fd.getFieldType();
            DesignComponent component = null;
            if (separators.contains(fd.getId())) {
                component = new DesignComponentSeparator();
            }else {
                switch(fieldType){

                    case STRING_FIELD:
                        component = new DesignComponentDirectString();
                        break;
                    case INTEGER_FIELD:
                    case PRICE_FIELD:
                    case DOUBLE_FIELD:
                    case ZAHL_FIELD:
                        component = new DesignComponentDirectZahl();
                        break;
                    case TIME_FIELD:
                    case DATE_FIELD:
                        component = new DesignComponentDirectDate();
                        break;
                }
            }
            component.setDesigner(fxDesigner);
            component.properties.internalFieldName = fd.getId();
            component.properties.externalName = fd.getFieldName();
            component.setLabeltext(fd.getName());
            component.setRoleAccess(mapRoleAccess(fd.getAccess()));
            component.setPage(mapRecordClassToPage(fd.getRecordClass()));
            component.properties.recordType = mapRecordClassToRecordType(fd.getRecordClass());
            component.properties.maxLength = fd.getLength();
            for (Option option : fd.getOptions()) {
                if (option instanceof OptionValproc) {
                    OptionValproc optionValproc = (OptionValproc) option;
                    component.properties.procedureNameForValues = optionValproc.getTclProc();
                }
                if (option instanceof OptionStrict) {
                    OptionStrict optionStrict = (OptionStrict) option;
                    component.properties.strict = optionStrict.isStrict();
                }
            }
            componentByInternalName.put(fd.getId(), component);

        }

    }

    private Set<String> ermittleSeparators(DefinitionImpl definition) {

        EList<TypeMaskDefinition> tMaskDefinitions = definition.getTypeMaskSection().getTypeMaskDefinitions();
        EList<TypeMaskDefinition> uMaskDefinitions=definition.getUnderlyingMaskSection().getUnderlyingMaskDefinitions();
        EList<TypeMaskDefinition> oMaskDefinitions=definition.getOptionMaskSection().getOptionMaskDefinitions();

        List<TypeMaskDefinition> maskDefinitions = new ArrayList<>();
        maskDefinitions.addAll(tMaskDefinitions);
        maskDefinitions.addAll(uMaskDefinitions);
        maskDefinitions.addAll(oMaskDefinitions);

        Set<String> separatorNames = maskDefinitions.stream()
                .filter(def -> def.getSeparator() != null)
                .map(def -> def.getId())
                .collect(Collectors.toSet());

        if (separatorNames == null) {
            separatorNames = new HashSet<>();
        }

        return separatorNames;
    }

    private RecordType mapRecordClassToRecordType(RECORDCLASS recordClass) {
        switch (recordClass) {

            case METHOD_READ_ONLY:
                return RecordType.B;
            case PARENT_READ_ONLY:
                return RecordType.U;
            case PARENT_READ_WRITE:
                return RecordType.G;
            case PARENT_READ_REFERENCE:
                return RecordType.R;
            case CHILD_READ_ONLY:
                return RecordType.D;
            case CHILD_READ_WRITE:
                return RecordType.P;
            case CHILD_READ_REFERENCE:
                return RecordType.S;
            default:
                return RecordType.B;
        }
    }

    private Page mapRecordClassToPage(RECORDCLASS recordClass) {
        switch (recordClass) {

            case METHOD_READ_ONLY:
                return Page.METHOD;
            case PARENT_READ_ONLY:
            case PARENT_READ_WRITE:
            case PARENT_READ_REFERENCE:
                return Page.PARENT;
            case CHILD_READ_ONLY:
            case CHILD_READ_WRITE:
            case CHILD_READ_REFERENCE:
                return Page.CHILD;
            default:
                return Page.METHOD;
        }
    }

    private RoleAccess mapRoleAccess(ROLE access) {
        switch (access) {
            case RADMIN:
                return RoleAccess.radmin;
            case NONE:
                return RoleAccess.none;
            case RTRADER:
                return RoleAccess.rtrader;
            case SUPERVISOR:
                return RoleAccess.supervisor;
            default:
                return RoleAccess.none;
        }


    }

    private void readLabels(DefinitionImpl definition) {
        MethodProperties mp = new MethodProperties();

        mp.childLabel = definition.getGuiLabelChild();
        mp.methodLabel = definition.getGuiLabelMethod();
        mp.parentLabel = definition.getGuiLabelParent();
        mp.methodName = definition.getMethodName();

        fxDesigner.setMethodProperties(mp);
    }
}
