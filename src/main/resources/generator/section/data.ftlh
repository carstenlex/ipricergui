Data {
<#list dataComponents>
<#items as komponente>
<#assign p=komponente.designComponentProperties>
${p.fieldDefinition}      ${p.internalFieldName}      "${p.labelText}"    ${p.roleAccess}     ${p.recordType}:${p.externalName}       ${p.maxLength} <#if p.procedureNameForValues != "">-valproc  ${p.procedureNameForValues} -strict</#if>
</#items>
</#list>
}
