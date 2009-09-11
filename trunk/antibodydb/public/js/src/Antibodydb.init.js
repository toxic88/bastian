Antibodydb.addLinks([
    {
        title : 'Other',
        items : [
            {
                iconCls : 'home',
                html    : 'Welcome',
                href    : '#Welcome'
            }
        ]
    },
    {
        title : 'Tables',
        items : [
            {
                iconCls : 'table',
                html    : 'Antibody',
                href    : '#tables.Antibody'
            },
            {
                iconCls : 'table',
                html    : 'Target Protein',
                href    : '#tables.Targetprotein'
            },
            {
                iconCls : 'table',
                html    : 'Incubationprotocol / Bufferset',
                href    : '#tables.Targetprotein'
            },
            {
                iconCls : 'table',
                html    : 'Images'
            }
        ]
    },
    {
        title : 'Forms',
        items : [
            {
                iconCls : 'form',
                html    : 'Antibody',
                href    : '#forms.Antibody'
            },
            {
                iconCls : 'form',
                html    : 'Target Protein',
                href    : '#forms.Targetprotein'
            },
            {
                iconCls : 'form',
                html    : 'Incubationprotocol / Bufferset',
                href    : '#forms.Incubationprotocol'
            }
        ]
    },
    {
        title   : 'User',
        iconCls : 'user',
        items : [
            {
                iconCls : 'keygo',
                html    : 'Change Password',
                handler : function() {
                    Antibodydb.user.Password.show();
                }
            },
            {
                iconCls : 'userkey',
                html    : 'Logout',
                handler : function() {
                    Ext.fly('loading-mask').fadeIn({
                        callback : function() {
                            window.location.href = Antibodydb.urls.Logout;
                        }
                    });
                }
            }
        ]
    }
]);
