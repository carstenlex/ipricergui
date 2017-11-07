
OptionMask {
#foreach( $komponente in $komponenten)
#set ($p = $komponente.Properties)
#if ($p.RecordType == "D" || $p.RecordType == "P" || $p.RecordType == "S")
AddColumn ${p.InternalFieldName}                       -row ${p.GridY} -col ${p.GridX} -width ${p.Width}
#end
#end

}