// Tthis script is the last included script! Good for creating the navigation

/* Add elements to the panel */
Antibodydb.Panel.add(Antibodydb.forms.Antibody);
Antibodydb.Panel.add(Antibodydb.forms.Targetprotein);
Antibodydb.Panel.add(Antibodydb.forms.Incubationprotocol);
Antibodydb.Panel.add(Antibodydb.tables.Targetprotein);

/* creating the navigation */

Antibodydb.addLinks([
    {
        title : 'Other',
        items : [
            {
                iconCls : 'home',
                html    : 'Welcome',
                href    : '#Antibodydb.Welcome'
            }
        ]
    },
    {
        title : 'Tables',
        items : [
            {
                iconCls : 'table',
                html    : 'Antibody',
                href    : '#Antibodydb.tables.Antibody'
            },
            {
                iconCls : 'table',
                html    : 'Target Protein',
                href    : '#Antibodydb.tables.Targetprotein'
            },
            {
                iconCls : 'table',
                html    : 'Incubationprotocol / Bufferset',
                href    : '#Antibodydb.tables.Targetprotein'
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
                iconCls  : 'form',
                html     : 'Antibody',
                href     : '#Antibodydb.forms.Antibody'
            },
            {
                iconCls : 'form',
                html    : 'Target Protein',
                href    : '#Antibodydb.forms.Targetprotein'
            },
            {
                iconCls : 'form',
                html    : 'Incubationprotocol / Bufferset',
                href    : '#Antibodydb.forms.Incubationprotocol'
            }
        ]
    },
    {
        title   : 'User',
        iconCls : 'user',
        items : [
            {
                iconCls  : 'keygo',
                html     : 'Change Password',
                handler : function() {
                    Antibodydb.user.Password.show();
                }
            },
            {
                iconCls  : 'userkey',
                html     : 'Logout',
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

Antibodydb.on('start', function() {
    Antibodydb.changePage = Antibodydb.Panel.layout.setActiveItem.createDelegate(Antibodydb.Panel.layout);
    Antibodydb.Navigation.doLayout(); // render all links
});
