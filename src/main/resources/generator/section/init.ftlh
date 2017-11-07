Init {
<#list initComponents>
<#items as komponente>
<#assign p=komponente.designComponentProperties>
InitField   ${p.internalFieldName}      "${p.initValue}"
</#items>
</#list>
}
