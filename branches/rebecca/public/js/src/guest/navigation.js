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
        title : 'User',
        iconCls : 'icon-user',
        items : [
            {
                iconCls : 'icon-userkey',
                html    : 'Login',
                handler : function() {
                    Application.Login.show();
                }
            }
        ]
    }
]);