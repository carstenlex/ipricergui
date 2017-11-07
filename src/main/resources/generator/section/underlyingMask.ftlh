UnderlyingMask {
#foreach( $komponente in $komponenten)
#set ($p = $komponente.Properties)
#if ($p.RecordType == "U" || $p.RecordType == "G" || $p.RecordType == "R")
AddColumn   ${p.InternalFieldName}                       -row ${p.GridY} -col ${p.GridX} -width ${p.Width}
#end
#end

}
