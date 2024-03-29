/**
 * Same as Ext.DomHelper.createDom but with listeners!
 */
Ext.DomHelper.createDomX = function(o, parentNode) {
    var el,
        doc = document,
        useSet,
        attr,
        val,
        cn;

    if (Ext.isArray(o)) {                   /* Allow Arrays of siblings to be inserted */
        el = doc.createDocumentFragment();  /* in one shot using a DocumentFragment */
        Ext.each(o, function(v) {    
            Ext.DomHelper.createDomX(v, el);
        });
    } else if (typeof o == "string") {      /* Allow a string as a child spec. */
        el = doc.createTextNode(o);
    } else {
        el = doc.createElement( o.tag || 'div' );
        useSet = !!el.setAttribute;         /* In IE some elements don't have setAttribute */
        for(attr in o){
            val = o[attr];
            if(["tag", "children", "cn", "html", "style", "listeners"].indexOf(attr) == -1 && !Ext.isFunction(val)) {
                if (attr == "cls") {
                    el.className = val;
                } else {
                    useSet ? el.setAttribute(attr, val) : el[attr] = val;
                }
            }
        }
        Ext.DomHelper.applyStyles(el, o.style);

        if (cn = o.children || o.cn) {
            Ext.DomHelper.createDomX(cn, el);
        } else if (o.html) {
            el.innerHTML = o.html;
        }
        if (o.listeners) {                  /* added support for eventlisteners */
            Ext.EventManager.on(el, o.listeners);
        }
    }
    if (parentNode){
       parentNode.appendChild(el);
    }
    return el;
};

/**
 * Error MessageBox
 */
Ext.MessageBox.error = Ext.Msg.error = function(title, msg, fn, scope) {
    this.show({
        title   : title,
        msg     : msg,
        buttons : this.OK,
        icon    : this.ERROR,
        fn      : fn,
        scope   : scope
    });
    return this;
}.createDelegate(Ext.Msg);

/**
 * Ext.History addition
 */
Ext.History.init = Ext.History.init.createInterceptor(function() {
    var html = '';
    if (!Ext.getDom(this.fieldId)) {
        html += '<input type="hidden" id="' + this.fieldId + '" />';
    }
    if (!Ext.getDom(this.iframeId)) {
        html += '<iframe id="' + this.iframeId + '"></iframe>';
    }
    if (html !== '') {
        Ext.DomHelper.insertHtml('afterEnd', document.body, '<form class="x-hidden">' + html + '</form>');
    }
}, Ext.History);

/**
 * Every GridPanel / EditorGridPanel should use the Ext.ux.grid.BufferView by default
 */
Ext.grid.GridPanel.override({
    getView : function(){
        if (!this.view){
            this.view = new Ext.ux.grid.BufferView(this.viewConfig);
        }
        return this.view;
    }
});
