function ViewUserDetail( userid )
    {
        if ( userid == null )
        {
            var selectedId = mygrid.getSelectedId();
            if ( selectedId == null )
            {
                alert( "��ѡ����Ҫ�鿴���û�!" );
                return false;
            }
            else
            {
                userid = mygrid.getUserData( selectedId, "userid" );
            }
        }
        PopupDetailWindow( userid );
 
    }