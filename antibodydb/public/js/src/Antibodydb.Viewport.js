new Ext.Viewport({
    layout : 'border',
    items  : [
        {
            region    : 'north',
            xtype     : 'panel',
            contentEl : 'header',
            border    : false,
            height    : 63,
            bodyStyle : 'background-color:#DFE8F6;'
        },
        nav,
        panel
    ]
});

})(); /* See Antibodydb.Navigation.js */
