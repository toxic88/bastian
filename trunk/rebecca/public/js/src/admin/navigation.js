Application.addLinks([
    {
        title : 'Rebecca',
        items : [
            {
                iconCls : 'icon-home',
                html    : 'Willkommen',
                href    : '#Welcome'
            }
        ]
    },
    {
        title : 'Schule',
        items : [
            {
                iconCls : 'icon-table',
                html    : 'Stundenplan',
                href    : '#Stundenplan'
            }
        ]
    },
    {
        title : 'Admin',
        items : [
            {
                iconCls : 'icon-table',
                html    : 'Benutzer Manager',
                href    : '#UserManager'
            }
        ]
    },
    {
        title : 'User',
        iconCls : 'icon-user',
        items : [
            {
                iconCls : 'icon-userkey',
                html    : 'Logout',
                handler : function() {
                    var callback = function() {
                        Ext.Msg.hide();
                        Ext.fly('loading-mask').fadeIn({
                            callback : function() {
                                window.location.reload();
                            }
                        });
                    }
                    Ext.Msg.wait('Ausloggen...', 'Bitte warten...');
                    Ext.Ajax.request({
                        url     : Application.urls.logout,
                        success : callback,
                        failure : callback
                    });
                }
            }
        ]
    }
]);