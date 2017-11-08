package ch.raiffeisen.ipricer.fxdesigner.parser;

import ch.raiffeisen.ipricer.definition.DefinitionDSL;
import ch.raiffeisen.ipricer.definition.definitionDSL.*;
import ch.raiffeisen.ipricer.definition.definitionDSL.impl.DefinitionImpl;
import ch.raiffeisen.ipricer.fxdesigner.FXDesigner;
import ch.raiffeisen.ipricer.fxdesigner.component.DesignComponentDirectString;
import ch.raiffeisen.ipricer.fxdesigner.component.base.DesignComponent;
import ch.raiffeisen.ipricer.fxdesigner.domain.Page;
import ch.raiffeisen.ipricer.fxdesigner.domain.RecordType;
import ch.raiffeisen.ipricer.fxdesigner.domain.RoleAccess;
import ch.raiffeisen.ipricer.fxdesigner.generator.MethodProperties;
import org.eclipse.emf.common.util.EList;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {

    private FXDesigner fxDesigner;

    Map<String, DesignComponent> componentByInternalName = new HashMap<>();


    public Parser(FXDesigner fxDesigner, File file) {
        this.fxDesigner = fxDesigner;
        DefinitionDSL dsl = new DefinitionDSL();
        DefinitionImpl definition = dsl.parseDefinition(file.toURI());

        readLabels(definition);
        readDataSection(definition.getData());

        readTypeMaskSection(definition.getTypeMaskSection());
        readUnderlyingMaskSection(definition.getUnderlyingMaskSection());
        readOptionMaskSection(definition.getOptionMaskSection());

        readInitSection(definition.getInitSection());
        clearGrids();
        showComponentsOnGrids();
    }

    private void readInitSection(InitSection initSection) {
        if (initSection == null || initSection.getInitDefinitions()==null){
            return;
        }

        for(InitDefinition initDef: initSection.getInitDefinitions()){
            DesignComponent component = componentByInternalName.get(initDef.getId());
            component.properties.initValue = initDef.getInitValue();
        }
    }

    //TODO gridLines verschwinden
    private void clearGrids() {
        fxDesigner.methodGrid.getChildren().clear();
        fxDesigner.childGrid.getChildren().clear();
        fxDesigner.parentGrid.getChildren().clear();
    }

    private void readOptionMaskSection(OptionMaskSection optionMaskSection) {

        if (optionMaskSection == null || optionMaskSection.getOptionMaskDefinitions() == null){
            return;
        }
        for(TypeMaskDefinition maskDef: optionMaskSection.getOptionMaskDefinitions()) { //TODO TypeMaskDefinition umbenennen -> MaskDefintiion
            DesignComponent component = componentByInternalName.get(maskDef.getId()); //TODO wenn die hier nicht gefunden wird -> ERROR
            if (component != null) {
                component.properties.gridX = maskDef.getCol();
                component.properties.gridY = maskDef.getRow();
                component.setWidthProperty(maskDef.getWidth());
            }else{
                System.out.println("Komponente "+maskDef.getId()+" in OptionMask definiert, aber nicht in Data");
            }
            //TODO hier noch -sep bearbeiten
        }
    }

    private void readUnderlyingMaskSection(UnderlyingMaskSection underlyingMaskSection) {
        if (underlyingMaskSection == null || underlyingMaskSection.getUnderlyingMaskDefinitions() == null){
            return;
        }
        for(TypeMaskDefinition maskDef: underlyingMaskSection.getUnderlyingMaskDefinitions()) { //TODO TypeMaskDefinition umbenennen -> MaskDefintiion
            DesignComponent component = componentByInternalName.get(maskDef.getId()); //TODO wenn die hier nicht gefunden wird -> ERROR
            if (component != null) {
                component.properties.gridX = maskDef.getCol();
                component.properties.gridY = maskDef.getRow();
                component.setWidthProperty(maskDef.getWidth());
            } else {
                System.out.println("Komponente "+maskDef.getId()+" in UnderlyingMask definiert, aber nicht in Data");
            }
            //TODO hier noch -sep bearbeiten
        }
    }

    private void readTypeMaskSection(TypeMaskSection typeMaskSection) {
        if (typeMaskSection == null || typeMaskSection.getTypeMaskDefinitions() == null){
            return;
        }
        for(TypeMaskDefinition maskDef: typeMaskSection.getTypeMaskDefinitions()) { //TODO TypeMaskDefinition umbenennen -> MaskDefintiion
            DesignComponent component = componentByInternalName.get(maskDef.getId()); //TODO wenn die hier nicht gefunden wird -> ERROR
            if (component != null) {
                component.properties.gridX = maskDef.getCol();
                component.properties.gridY = maskDef.getRow();
                component.setWidthProperty(maskDef.getWidth());
            }else{
                System.out.println("Komponente "+maskDef.getId()+" in TypeMask definiert, aber nicht in Data");


            }
            //TODO hier noch -sep bearbeiten
        }
    }

    private void showComponentsOnGrids() {
        List<DesignComponent> methodComponents = componentByInternalName.values().stream().filter(methodComponent -> methodComponent.getPage() == Page.METHOD).collect(Collectors.toList());
        for(DesignComponent component: methodComponents) {
            fxDesigner.methodGrid.add(component,component.properties.gridX, component.properties.gridY);
        }

        List<DesignComponent> parentComponents = componentByInternalName.values().stream().filter(methodComponent -> methodComponent.getPage() == Page.PARENT).collect(Collectors.toList());
        for(DesignComponent component: parentComponents) {
            fxDesigner.parentGrid.add(component,component.properties.gridX, component.properties.gridY);
        }

        List<DesignComponent> childComponents = componentByInternalName.values().stream().filter(methodComponent -> methodComponent.getPage() == Page.CHILD).collect(Collectors.toList());
        for(DesignComponent component: childComponents) {
            fxDesigner.methodGrid.add(component,component.properties.gridX, component.properties.gridY);
        }
    }

    private void readDataSection(Data data) {
        EList<FieldDefinition> fieldDefinitions = data.getFieldDefinitions();


        for (FieldDefinition fd : fieldDefinitions) {
            FIELD_TYPE fieldType = fd.getFieldType();
            if (FIELD_TYPE.STRING_FIELD == fieldType) {
                DesignComponentDirectString componentDirectString = new DesignComponentDirectString();
                componentDirectString.setDesigner(fxDesigner);
                componentDirectString.properties.internalFieldName = fd.getId();
                componentDirectString.properties.externalName = fd.getFieldName();
                componentDirectString.setLabeltext(fd.getName());
                componentDirectString.setRoleAccess(mapRoleAccess(fd.getAccess()));
                componentDirectString.setPage(mapRecordClassToPage(fd.getRecordClass()));
                componentDirectString.properties.recordType=mapRecordClassToRecordType(fd.getRecordClass());
                componentDirectString.properties.maxLength = fd.getLength();
                for(Option option: fd.getOptions()){
                    if (option instanceof  OptionValproc){
                        OptionValproc optionValproc = (OptionValproc) option;
                        componentDirectString.properties.procedureNameForValues = optionValproc.getTclProc();
                    }
                    if (option instanceof OptionStrict){
                        OptionStrict optionStrict = (OptionStrict)option;
                        componentDirectString.properties.strict = optionStrict.isStrict();
                    }
                }
                componentByInternalName.put(fd.getId(), componentDirectString);
            }
        }

    }

    private RecordType mapRecordClassToRecordType(RECORDCLASS recordClass) {
        switch(recordClass){

            case METHOD_READ_ONLY: return RecordType.B;
            case PARENT_READ_ONLY: return RecordType.U;
            case PARENT_READ_WRITE: return RecordType.G;
            case PARENT_READ_REFERENCE: return RecordType.R;
            case CHILD_READ_ONLY: return RecordType.D;
            case CHILD_READ_WRITE: return RecordType.P;
            case CHILD_READ_REFERENCE: return RecordType.S;
            default: return RecordType.B;
        }
    }

    private Page mapRecordClassToPage(RECORDCLASS recordClass) {
        switch(recordClass) {

            case METHOD_READ_ONLY: return Page.METHOD;
            case PARENT_READ_ONLY:
            case PARENT_READ_WRITE:
            case PARENT_READ_REFERENCE: return Page.PARENT;
            case CHILD_READ_ONLY:
            case CHILD_READ_WRITE:
            case CHILD_READ_REFERENCE: return Page.CHILD;
            default: return Page.METHOD;
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
