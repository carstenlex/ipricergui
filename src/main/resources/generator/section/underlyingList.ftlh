UnderlyingList {
#foreach( $komponente in $komponenten)
#set ($p = $komponente.Properties)
#if ($p.ShowInUnderlyingList)
AddColumn ${p.InternalFieldName}               -width ${p.UnderlyingListWidth}
#end
#end

}
