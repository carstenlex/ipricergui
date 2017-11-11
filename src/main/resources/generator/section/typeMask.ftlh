TypeMask {
<#list allComponents>
    <#items as komponente>
        <#assign p=komponente.designComponentProperties>
    <#if p.recordType == "B" || p.recordType== "E">
    AddColumn ${p.internalFieldName}                       -row ${p.gridY} -col ${p.gridX} <#if p.separator>-sep<#else>-width ${p.width}</#if>
    </#if>
    </#items>
</#list>
}
