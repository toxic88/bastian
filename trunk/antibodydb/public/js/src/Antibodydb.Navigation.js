;(function() { /* See Antibodydb.Viewport.js */
var nav = new Ext.Panel({
    id           : 'navigation', /* for stylesheets */
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
    defaults     : {
        frame       : true,
        collapsible : true,
        margins     : '0 0 5 0'
    }
});

/**
 * Have to do this like this because Ext.XTemplate can't create Ext.Components
 */
Antibodydb.addLinks = function() {
    var createLinks = function(links) {
        for(var i=0;i<links.length;i++) {
            var o = links[i];
            links[i] = {
                tag : 'li',
                cn  : [
                    {
                        tag : 'img',
                        src : Ext.BLANK_IMAGE_URL,
                        cls : Antibodydb.getIconCls(o)
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
        return links;
    };

    return function(o) {
        if (Ext.isArray(o)) {
            for(var i=0;i<o.length;i++) {
                Antibodydb.addLinks(o[i]);
            }
            return;
        }

        var panel = o, links = [].concat(panel.items);
        panel = {
            title     : panel.title || '(no text)',
            iconCls   : Antibodydb.getIconCls(panel),
            contentEl : Ext.DomHelper.createDomX({
                tag : 'ul',
                cn  : createLinks(links)
            })
        };

        nav.add(panel);
        if (Ext.isReady) {
            nav.doLayout();
        }
    };
}();


Antibodydb.on('start', function() {
    nav.doLayout();
    Ext.fly('navigation').on('click', function() {
        this.blur(); // blur the focus
    }, null, { delegate : 'a' });
});
