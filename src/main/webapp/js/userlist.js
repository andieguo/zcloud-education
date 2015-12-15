function ViewUserDetail( userid )
    {
        if ( userid == null )
        {
            var selectedId = mygrid.getSelectedId();
            if ( selectedId == null )
            {
                alert( "请选择你要查看的用户!" );
                return false;
            }
            else
            {
                userid = mygrid.getUserData( selectedId, "userid" );
            }
        }
        PopupDetailWindow( userid );
 
    }