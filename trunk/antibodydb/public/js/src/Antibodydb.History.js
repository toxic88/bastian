Ext.History.on({
    'change' : function(token) {
        if (token) {
            var parts = token.split('.');
            var part = window;
            
            for(var i=0;i<parts.length;i++) {
                if (part[ parts[i] ]) {
                    part = part[ parts[i] ];
                } else {
                    return;
                }
            }
            Antibodydb.changePage(part);
        }
    }
});

Ext.History.init(function() {
    var token = Ext.History.getToken();
    if (token === null) {
        token = 'Antibodydb.Welcome';
    }
    Ext.History.add(token);
    Ext.History.fireEvent('change', token);
});
