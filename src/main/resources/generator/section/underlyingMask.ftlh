UnderlyingMask {
<#list allComponents>
    <#items as komponente>
        <#assign p=komponente.designComponentProperties>
        <#if p.recordType == "U" || p.recordType == "G" || p.recordType == "R">
        AddColumn   ${p.internalFieldName}  -row ${p.gridY} -col ${p.gridX} <#if p.separator>-sep<#else>-width ${p.width}</#if>
        </#if>
    </#items>
</#list>
}
