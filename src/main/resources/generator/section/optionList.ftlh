OptionList {
<#list allComponents>
    <#items as komponente>
        <#assign p=komponente.designComponentProperties>
        <#if p.showInOptionList>
        AddColumn ${p.internalFieldName}               -width ${p.optionListWidth}
        </#if>
    </#items>
</#list>
}
