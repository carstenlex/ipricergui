Data {
#foreach( $komponente in $komponenten)
#set ($p = $komponente.Properties)
${p.Datatype.IpricerField}      ${p.InternalFieldName}      "${p.LabelText}"    ${p.RoleAccess}     ${p.RecordType}:${p.ExternalName}       ${p.MaxLength}      #if ($p.ProcedureNameForValues)-valproc  ${p.ProcedureNameForValues} -strict#end

#end

}
