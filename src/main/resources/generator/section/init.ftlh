Init {
#foreach( $komponente in $komponenten)
#set ($p = $komponente.Properties)
#if ($p.InitValue)
InitField   ${p.InternalFieldName}      "$p.InitValue"
#end
#end

}
