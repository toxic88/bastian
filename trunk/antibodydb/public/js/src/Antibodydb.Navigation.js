Antibodydb.Navigation = new Ext.Panel({
	id           : 'navigation', // for stylesheets
    region       : 'west',
    width        : 200,
    minWidth     : 200,
    maxWidth     : 200,
    unstyled     : true,
    split        : true,
    collapseMode : 'mini',
    animCollapse : false,
    paddings     : '0 5 5 5',
    margins      : '2 0 5 5',
    layoutConfig : {
        align : 'stretch',
        pack  : 'start'
    },
    defaults     : {
        frame       : true,
        collapsible : true,
        margins     : '0 0 5 0'
    }
});

/**
 * Have to do this like this because Ext.XTemplate can't create Ext.Components
 */
Antibodydb.addLinks = function(o) {
    if(Ext.isArray(o)) {
        for(var i=0;i<o.length;i++) {
            Antibodydb.addLinks(o[i]);
        }
        return;
    }
    
    var panel = o, links = [].concat(panel.items);
    for(var i=0;i<links.length;i++) {
        o = links[i];
        links[i] = {
            tag : 'li',
            cn  : [
                {
                    tag : 'img',
                    src : Ext.BLANK_IMAGE_URL,
                    cls : Antibodydb.getIconCls(o.iconCls || o.cls)
                },
                {
                    tag       : 'a',
                    href      : o.href || 'javascript:;',
                    html      : o.html || o.text || '(no text)',
                    listeners : o.handler ? { click : o.handler } : (o.listeners ? o.listeners : null)
                }
            ]
        };
    }
    
    panel = {
        title     : panel.title || '(no text)',
        iconCls   : Antibodydb.getIconCls(panel.iconCls || panel.cls),
        contentEl : Ext.DomHelper.createDomX({
            tag : 'ul',
            cn  : links
        })
    };
    
    Antibodydb.Navigation.add(panel);
    if(Ext.isReady) {
        Antibodydb.Navigation.doLayout();
    }
};
