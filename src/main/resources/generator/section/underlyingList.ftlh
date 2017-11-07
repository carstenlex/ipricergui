UnderlyingList {
<#list allComponents>
    <#items as komponente>
        <#assign p=komponente.designComponentProperties>
        <#if p.showInUnderlyingList>
        AddColumn ${p.internalFieldName}               -width ${p.underlyingListWidth}
        </#if>
    </#items>
</#list>
}
