var panel = new Ext.Panel({
    id           : 'content',
    region       : 'center',
    margins      : '2 5 5 0',
    unstyled     : true,
    activeItem   : 0,
    layout : {
        type           : 'card',
        deferredRender : true
    },
    defaults     : {
        autoScroll : true,
        frame      : true
    }
});
/* Only used internal */
Antibodydb.addModule = panel.add.createDelegate(panel);
Antibodydb.on('start', function() {
    Antibodydb.changePage = panel.layout.setActiveItem.createDelegate(panel.layout);
});
