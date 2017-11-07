OptionList {
#foreach( $komponente in $komponenten)
#set ($p = $komponente.Properties)
#if ($p.ShowInOptionList)
AddColumn ${p.InternalFieldName}               -width ${p.OptionListWidth}
#end
#end

}
