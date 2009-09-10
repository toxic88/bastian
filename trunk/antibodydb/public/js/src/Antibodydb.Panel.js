Antibodydb.Panel = new Ext.Panel({
    region       : 'center',
    margins      : '2 5 5 0',
    unstyled     : true,
    layout       : 'card',
    activeItem   : 0,
    layoutConfig : {
        deferredRender : true
    },
    defaults     : {
        autoScroll : true,
        frame      : true
    },
    items : 
        (Antibodydb.Welcome = new Ext.Panel({
            xtype  : 'panel',
            title  : 'Welcome',
            layout : 'fit',
            html   : '<p>Ich habe die komplette Seite neu gemacht! Falls Fehler auftreten oder etwas nicht geht bitte mir eine E-Mail schreiben!</p><p>Danke <a href="mailto:b.buchholz@dkfz-heidelberg.de">Bastian Buchholz</a></p>'
        }))
});
