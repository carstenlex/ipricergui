
OptionMask {
<#list allComponents>
    <#items as komponente>
        <#assign p=komponente.designComponentProperties>
        <#if p.recordType == "D" || p.recordType== "P" || p.recordType== "S">
        AddColumn ${p.internalFieldName}                       -row ${p.gridY} -col ${p.gridX} -width ${p.width}
        </#if>
    </#items>
</#list>
}