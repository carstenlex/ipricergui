TypeMask {
#foreach( $komponente in $komponenten)
#set ($p = $komponente.Properties)
#if ($p.RecordType == "B" || $p.RecordType == "E")
AddColumn ${p.InternalFieldName}                       -row ${p.GridY} -col ${p.GridX} -width ${p.Width}
#end
#end

}
