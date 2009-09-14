Ext.ns('Ext.ux.form');
/**
 * NOTE: The changes will be in Ext 3.1
 * with changes from http://extjs.com/forum/showthread.php?p=356809#post356809, http://extjs.com/forum/showthread.php?p=262550#post262550
 */
Ext.ux.form.FileUploadField = Ext.extend(Ext.form.TextField,  {
    /**
     * @cfg {String} buttonText The button text to display on the upload button (defaults to
     * 'Browse...').  Note that if you supply a value for {@link #buttonCfg}, the buttonCfg.text
     * value will be used instead if available.
     */
    buttonText : 'Browse...',
    /**
     * @cfg {Boolean} buttonOnly True to display the file upload field as a button with no visible
     * text field (defaults to false).  If true, all inherited TextField members will still be available.
     */
    buttonOnly : false,
    /**
     * @cfg {Number} buttonOffset The number of pixels of space reserved between the button and the text field
     * (defaults to 3).  Note that this only applies if {@link #buttonOnly} = false.
     */
    buttonOffset : 3,
    /**
     * @cfg {Object} buttonCfg A standard {@link Ext.Button} config object.
     */

    /* private */
    readOnly : true,

    /**
     * @hide
     * @method autoSize
     */
    autoSize : Ext.emptyFn,

    /* private */
    initComponent: function() {
        this.supr().initComponent.call(this);

        this.addEvents(
            /**
             * @event fileselected
             * Fires when the underlying file input field's value has changed from the user
             * selecting a new file from the system file selection dialog.
             * @param {Ext.ux.FileUploadField} this
             * @param {String} value The file value returned by the underlying file input field
             */
            'fileselected'
        );
    },

    /* private */
    onRender : function(ct, position) {
        this.supr().onRender.call(this, ct, position);

        this.wrap = this.el.wrap({cls:'x-form-field-wrap x-form-file-wrap'});
        this.el.addClass('x-form-file-text');
        this.el.dom.removeAttribute('name');

        this.createFileInput();

        if( this.disabled ) {
            this.fileInput.dom.disabled = true;
        }

        var btnCfg = Ext.applyIf(this.buttonCfg || {}, {
            text: this.buttonText
        });
        this.button = new Ext.Button(Ext.apply(btnCfg, {
            renderTo : this.wrap,
            cls      : 'x-form-file-btn' + (btnCfg.iconCls ? ' x-btn-icon' : '')
        }));

        this.addFileListeners();

        if ( this.buttonOnly ){
            this.el.hide();
            this.wrap.setWidth(this.button.getEl().getWidth());
        }
    },

    /* private */
    addFileListeners : function() {
        this.fileInput.on({
            'change'    : function() {
                var v = this.fileInput.dom.value;
                this.setValue(v);
                this.fireEvent('fileselected', this, v);
            },
            'mouseover' : function() {
                this.button.addClass(['x-btn-over', 'x-btn-focus']);
            },
            'mouseout'  : function() {
                this.button.removeClass(['x-btn-over', 'x-btn-focus', 'x-btn-click']);
            },
            'mousedown' : function() {
                this.button.addClass('x-btn-click');
            },
            'mouseup'   : function() {
                this.button.removeClass(['x-btn-over', 'x-btn-focus', 'x-btn-click']);
            },
            scope       : this
        });
    },

    /* private */
    createFileInput : function() {
        this.fileInput = this.wrap.createChild({
            id   : this.getFileInputId(),
            name : this.name || this.getId(),
            cls  : 'x-form-file',
            tag  : 'input',
            type : 'file',
            size : 1
        });
    },

    reset : function() {
        this.fileInput.removeAllListeners();
        this.fileInput.remove();
        this.createFileInput();
        this.addFileListeners();
        this.supr().reset.call(this);
    },

    /* private */
    onDestroy : function() {
        if ( this.fileInput ) {
            Ext.destroy(this.fileInput);
            this.fileInput = null;
        }

        if ( this.button ) {
            this.button.destroy();
            this.button = null;
        }

        this.supr().onDestroy.call(this);
    },

    /* private */
    onEnable : function() {
        this.supr().onEnable.call(this);
        this.fileInput.dom.disabled = false;
        this.button.enable();
    },

    /* private */
    onDisable : function() {
        this.supr().onDisable.call(this);
        this.fileInput.dom.disabled = true;
        this.button.disable();
    },

    /* private */
    getFileInputId : function() {
        return this.id+'-file';
    },

    /* private */
    onResize : function(w, h) {
        this.supr().onResize.call(this, w, h);

        this.wrap.setWidth(w);

        if ( !this.buttonOnly ){
            var w = this.wrap.getWidth() - this.button.getEl().getWidth() - this.buttonOffset;
            this.el.setWidth(w);
        }
    },

    /* private */
    preFocus : Ext.emptyFn,

    /* private */
    getResizeEl : function(){
        return this.wrap;
    },

    /* private */
    getPositionEl : function(){
        return this.wrap;
    },

    /* private */
    alignErrorIcon : function(){
        this.errorIcon.alignTo(this.wrap, 'tl-tr', [2, 0]);
    }

});
Ext.reg('fileuploadfield', Ext.ux.form.FileUploadField);
Ext.form.FileUploadField = Ext.ux.form.FileUploadField;
Ext.ns('Ext.ux.form');
Ext.ux.form.SearchField = Ext.extend(Ext.form.TwinTriggerField, {
    initComponent : function(){
        this.supr().initComponent.call(this);
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },

    validationEvent : false,
    validateOnBlur  : false,
    trigger1Class   : 'x-form-clear-trigger',
    trigger2Class   : 'x-form-search-trigger',
    hideTrigger1    : true,
    width           : 180,
    hasSearch       : false,
    paramName       : 'query',

    onTrigger1Click : function(){
        if(this.hasSearch){
            this.el.dom.value = '';
            var o = {start: 0};
            this.store.baseParams = this.store.baseParams || {};
            this.store.baseParams[this.paramName] = '';
            this.store.reload({params:o});
            this.triggers[0].hide();
            this.hasSearch = false;
        }
    },

    onTrigger2Click : function(){
        var v = this.getRawValue();
        if(v.length < 1){
            this.onTrigger1Click();
            return;
        }
        var o = {start: 0};
        this.store.baseParams = this.store.baseParams || {};
        this.store.baseParams[this.paramName] = v;
        this.store.reload({params:o});
        this.hasSearch = true;
        this.triggers[0].show();
    }
});
Ext.reg('searchfield', Ext.ux.form.SearchField);
Ext.form.SearchField = Ext.ux.form.SearchField;
Ext.ns('Ext.ux.form');
Ext.ux.form.SelectBox = Ext.extend(Ext.form.ComboBox, {
    searchResetDelay : 100,
    editable         : false,
    forceSelection   : true,
    rowHeight        : false,
    lastSearchTerm   : false,
    triggerAction    : 'all',
    mode             : 'local',
    lazyInit         : false,

    constructor : function(config) {
        this.supr().constructor.call(this, config);
        this.lastSelectedIndex = this.selectedIndex || 0;
    },

    initComponent : function(){
        if (! this.store && this.data ){
            this.store = new Ext.data.ArrayStore({
                fields     : ['text'],
                data       : this.data,
                expandData : true
            });
            this.displayField = 'text';
        }
        this.supr().initComponent.apply(this, arguments);
    },

    initEvents : function(){
        this.supr().initEvents.apply(this, arguments);
        /* you need to use keypress to capture upper/lower case and shift+key, but it doesn't work in IE */
        this.el.on('keydown', this.keySearch, this, true);
        this.cshTask = new Ext.util.DelayedTask(this.clearSearchHistory, this);
    },

    keySearch : function(e, target, options) {
        var raw = e.getKey();
        var key = String.fromCharCode(raw);
        var startIndex = 0;

        if ( !this.store.getCount() ) {
            return;
        }

        switch(raw) {
            case Ext.EventObject.HOME:
                e.stopEvent();
                this.selectFirst();
                return;

            case Ext.EventObject.END:
                e.stopEvent();
                this.selectLast();
                return;

            case Ext.EventObject.PAGEDOWN:
                this.selectNextPage();
                e.stopEvent();
                return;

            case Ext.EventObject.PAGEUP:
                this.selectPrevPage();
                e.stopEvent();
                return;
        }

        /* skip special keys other than the shift key */
        if ( (e.hasModifier() && !e.shiftKey) || e.isNavKeyPress() || e.isSpecialKey() ) {
            return;
        }
        if ( this.lastSearchTerm == key ) {
            startIndex = this.lastSelectedIndex;
        }
        this.search(this.displayField, key, startIndex);
        this.cshTask.delay(this.searchResetDelay);
    },

    onRender : function(ct, position) {
        this.store.on('load', this.calcRowsPerPage, this);
        this.supr().onRender.apply(this, arguments);
        if ( this.mode == 'local' ) {
            this.calcRowsPerPage();
        }
    },

    onSelect : function(record, index, skipCollapse){
        if ( this.fireEvent('beforeselect', this, record, index) !== false ){
            this.setValue(record.data[this.valueField || this.displayField]);
            if( !skipCollapse ) {
                this.collapse();
            }
            this.lastSelectedIndex = index + 1;
            this.fireEvent('select', this, record, index);
        }
    },

    render : function(ct) {
        this.supr().render.apply(this, arguments);
        if ( Ext.isSafari ) {
            this.el.swallowEvent('mousedown', true);
        }
        this.el.unselectable();
        this.innerList.unselectable();
        this.trigger.unselectable();
        this.innerList.on('mouseup', function(e, target, options) {
            if ( target.id && target.id == this.innerList.id ) {
                return;
            }
            this.onViewClick();
        }, this);

        this.innerList.on('mouseover', function(e, target, options) {
            if ( target.id && target.id == this.innerList.id ) {
                return;
            }
            this.lastSelectedIndex = this.view.getSelectedIndexes()[0] + 1;
            this.cshTask.delay(this.searchResetDelay);
        }, this);

        this.trigger.un('click', this.onTriggerClick, this);
        this.trigger.on('mousedown', function(e, target, options) {
            e.preventDefault();
            this.onTriggerClick();
        }, this);

        this.on('collapse', function(e, target, options) {
            Ext.getDoc().un('mouseup', this.collapseIf, this);
        }, this, true);

        this.on('expand', function(e, target, options) {
            Ext.getDoc().on('mouseup', this.collapseIf, this);
        }, this, true);
    },

    clearSearchHistory : function() {
        this.lastSelectedIndex = 0;
        this.lastSearchTerm = false;
    },

    selectFirst : function() {
        this.focusAndSelect(this.store.data.first());
    },

    selectLast : function() {
        this.focusAndSelect(this.store.data.last());
    },

    selectPrevPage : function() {
        if ( !this.rowHeight ) {
            return;
        }
        var index = Math.max(this.selectedIndex-this.rowsPerPage, 0);
        this.focusAndSelect(this.store.getAt(index));
    },

    selectNextPage : function() {
        if ( !this.rowHeight ) {
            return;
        }
        var index = Math.min(this.selectedIndex+this.rowsPerPage, this.store.getCount() - 1);
        this.focusAndSelect(this.store.getAt(index));
    },

    search : function(field, value, startIndex) {
        field = field || this.displayField;
        this.lastSearchTerm = value;
        var index = this.store.find.apply(this.store, arguments);
        if ( index !== -1 ) {
            this.focusAndSelect(index);
        }
    },

    focusAndSelect : function(record) {
        var index = typeof record === 'number' ? record : this.store.indexOf(record);
        this.select(index, this.isExpanded());
        this.onSelect(this.store.getAt(record), index, this.isExpanded());
    },

    calcRowsPerPage : function() {
        if ( this.store.getCount() ) {
            this.rowHeight = Ext.fly(this.view.getNode(0)).getHeight();
            this.rowsPerPage = this.maxHeight / this.rowHeight;
        } else {
            this.rowHeight = false;
        }
    }

});
Ext.reg('selectbox', Ext.ux.form.SelectBox);
Ext.form.SelectBox = Ext.ux.form.SelectBox;
/*!
 * Ext JS Library 3.0.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.ns('Ext.ux.grid');

/**
 * @class Ext.ux.grid.BufferView
 * @extends Ext.grid.GridView
 * A custom GridView which renders rows on an as-needed basis.
 */
Ext.ux.grid.BufferView = Ext.extend(Ext.grid.GridView, {
    /**
     * @cfg {Number} rowHeight
     * The height of a row in the grid.
     */
    rowHeight: 19,

    /**
     * @cfg {Number} borderHeight
     * The combined height of border-top and border-bottom of a row.
     */
    borderHeight: 2,

    /**
     * @cfg {Boolean/Number} scrollDelay
     * The number of milliseconds before rendering rows out of the visible
     * viewing area. Defaults to 100. Rows will render immediately with a config
     * of false.
     */
    scrollDelay: 100,

    /**
     * @cfg {Number} cacheSize
     * The number of rows to look forward and backwards from the currently viewable
     * area.  The cache applies only to rows that have been rendered already.
     */
    cacheSize: 20,

    /**
     * @cfg {Number} cleanDelay
     * The number of milliseconds to buffer cleaning of extra rows not in the
     * cache.
     */
    cleanDelay: 500,

    initTemplates : function(){
        this.supr().initTemplates.call(this);
        var ts = this.templates;
        // empty div to act as a place holder for a row
        ts.rowHolder = new Ext.Template(
            '<div class="x-grid3-row {alt}" style="{tstyle}"></div>'
        );
        ts.rowHolder.disableFormats = true;
        ts.rowHolder.compile();

        ts.rowBody = new Ext.Template(
            '<table class="x-grid3-row-table" border="0" cellspacing="0" cellpadding="0" style="{tstyle}">',
            '<tbody><tr>{cells}</tr>',
            (this.enableRowBody ? '<tr class="x-grid3-row-body-tr" style="{bodyStyle}"><td colspan="{cols}" class="x-grid3-body-cell" tabIndex="0" hidefocus="on"><div class="x-grid3-row-body">{body}</div></td></tr>' : ''),
            '</tbody></table>'
        );
        ts.rowBody.disableFormats = true;
        ts.rowBody.compile();
    },

    getStyleRowHeight : function(){
        return Ext.isBorderBox ? (this.rowHeight + this.borderHeight) : this.rowHeight;
    },

    getCalculatedRowHeight : function(){
        return this.rowHeight + this.borderHeight;
    },

    getVisibleRowCount : function(){
        var rh = this.getCalculatedRowHeight();
        var visibleHeight = this.scroller.dom.clientHeight;
        return (visibleHeight < 1) ? 0 : Math.ceil(visibleHeight / rh);
    },

    getVisibleRows: function(){
        var count = this.getVisibleRowCount();
        var sc = this.scroller.dom.scrollTop;
        var start = (sc == 0 ? 0 : Math.floor(sc/this.getCalculatedRowHeight())-1);
        return {
            first: Math.max(start, 0),
            last: Math.min(start + count + 2, this.ds.getCount()-1)
        };
    },

    doRender : function(cs, rs, ds, startRow, colCount, stripe, onlyBody){
        var ts = this.templates, ct = ts.cell, rt = ts.row, rb = ts.rowBody, last = colCount-1;
        var rh = this.getStyleRowHeight();
        var vr = this.getVisibleRows();
        var tstyle = 'width:'+this.getTotalWidth()+';height:'+rh+'px;';
        // buffers
        var buf = [], cb, c, p = {}, rp = {tstyle: tstyle}, r;
        for (var j = 0, len = rs.length; j < len; j++) {
            r = rs[j]; cb = [];
            var rowIndex = (j+startRow);
            var visible = rowIndex >= vr.first && rowIndex <= vr.last;
            if (visible) {
                for (var i = 0; i < colCount; i++) {
                    c = cs[i];
                    p.id = c.id;
                    p.css = i == 0 ? 'x-grid3-cell-first ' : (i == last ? 'x-grid3-cell-last ' : '');
                    p.attr = p.cellAttr = "";
                    p.value = c.renderer(r.data[c.name], p, r, rowIndex, i, ds);
                    p.style = c.style;
                    if (p.value == undefined || p.value === "") {
                        p.value = "&#160;";
                    }
                    if (r.dirty && typeof r.modified[c.name] !== 'undefined') {
                        p.css += ' x-grid3-dirty-cell';
                    }
                    cb[cb.length] = ct.apply(p);
                }
            }
            var alt = [];
            if(stripe && ((rowIndex+1) % 2 == 0)){
                alt[0] = "x-grid3-row-alt";
            }
            if(r.dirty){
                alt[1] = " x-grid3-dirty-row";
            }
            rp.cols = colCount;
            if(this.getRowClass){
                alt[2] = this.getRowClass(r, rowIndex, rp, ds);
            }
            rp.alt = alt.join(" ");
            rp.cells = cb.join("");
            buf[buf.length] =  !visible ? ts.rowHolder.apply(rp) : (onlyBody ? rb.apply(rp) : rt.apply(rp));
        }
        return buf.join("");
    },

    isRowRendered: function(index){
        var row = this.getRow(index);
        return row && row.childNodes.length > 0;
    },

    syncScroll: function(){
        this.supr().syncScroll.apply(this, arguments);
        this.update();
    },

    // a (optionally) buffered method to update contents of gridview
    update: function(){
        if (this.scrollDelay) {
            if (!this.renderTask) {
                this.renderTask = new Ext.util.DelayedTask(this.doUpdate, this);
            }
            this.renderTask.delay(this.scrollDelay);
        }else{
            this.doUpdate();
        }
    },

    doUpdate: function(){
        if (this.getVisibleRowCount() > 0) {
            var g = this.grid, cm = g.colModel, ds = g.store;
            var cs = this.getColumnData();

            var vr = this.getVisibleRows();
            for (var i = vr.first; i <= vr.last; i++) {
                // if row is NOT rendered and is visible, render it
                if(!this.isRowRendered(i)){
                    var html = this.doRender(cs, [ds.getAt(i)], ds, i, cm.getColumnCount(), g.stripeRows, true);
                    this.getRow(i).innerHTML = html;
                }
            }
            this.clean();
        }
    },

    // a buffered method to clean rows
    clean : function(){
        if(!this.cleanTask){
            this.cleanTask = new Ext.util.DelayedTask(this.doClean, this);
        }
        this.cleanTask.delay(this.cleanDelay);
    },

    doClean: function(){
        if (this.getVisibleRowCount() > 0) {
            var vr = this.getVisibleRows();
            vr.first -= this.cacheSize;
            vr.last += this.cacheSize;

            var i = 0, rows = this.getRows();
            // if first is less than 0, all rows have been rendered
            // so lets clean the end...
            if(vr.first <= 0){
                i = vr.last + 1;
            }
            for(var len = this.ds.getCount(); i < len; i++){
                // if current row is outside of first and last and
                // has content, update the innerHTML to nothing
                if ((i < vr.first || i > vr.last) && rows[i].innerHTML) {
                        rows[i].innerHTML = '';
                }
            }
        }
    },

    layout: function(){
        this.supr().layout.call(this);
        this.update();
    }
});
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
(Ext.isIE6 || Ext.isIE7) && (Ext.BLANK_IMAGE_URL = 'images/default/s.gif'); /* only need this for IE6 / IE7 */

Ext.state.Manager.setProvider(
    new Ext.state.CookieProvider(/*{
        expires : new Date( new Date().getTime() + (1000 * 60 * 60 * 24 * 7) ) // 7 Days is default
    }*/)
);

Ext.History.on({
    'change' : function(token) {
        var modules = 'Application.modules.',
            str = modules + (new Function(['return ', modules, token, ';'].join(''))() ? token : Application.pageNotFoundModule);
        /*
        if ( !str.render ) {
            str = Application.addModule( str );
        }
        Application.changePage( str );
        */
        new Function(['if(!', str, '.render){', str, '=Application.addModule(', str, ');}Application.changePage(', str, ');'].join(''))();
    }
});

Application = Ext.apply(new Ext.util.Observable, (function(){
    var _initHistory = function() {
        var token = this.getToken() || Application.defaultModule;
        this.add(token);
        this.fireEvent('change', token);
    }

    return {
        init : function() {
            var me = this;
            me.addEvents('shutdown', 'start');

            Ext.EventManager.on(window, 'beforeunload', function(e){
                if(me.fireEvent('shutdown') === false) {
                    /* e.browserEvent.returnValue = 'geh nicht weg!'; */
                    e.stopEvent();
                    return false;
                }
            });

            me.fireEvent('start');

            Ext.History.init(_initHistory, Ext.History);
            delete me.init;
        },

        cookie  : Ext.copyTo({}, Ext.state.Manager, 'get,set,clear'),
        history : Ext.copyTo({
            get : function() {
                return this.getToken();
            }
        }, Ext.History, 'getToken,add,back,forward'),

        modules : {}, /* Namespace for all modules */
        defaultModule : 'Welcome',
        pageNotFoundModule : 'PageNotFound',

        urls    : {
            logout : './auth/logout/format/json',
            login  : './auth/login/format/json'
        }
    };
}()));

/**
 * Base Modules
 */
Application.modules.Welcome = {
    title   : 'Willkommen',
    iconCls : 'icon-home',
    html    : '<p>Was soll hier nur stehen?</p>'
};
Application.modules.PageNotFound = {
    title   : '404 - Seite nicht gefunden',
    iconCls : 'icon-error',
    html    : '<h2>Die Seite konnte nicht gefunden werden...</h2>'
};


Ext.onReady(function() {
    Ext.QuickTips.init();
	
    Ext.apply(Ext.QuickTips.getQuickTip(), {
        maxWidth : 500
    });

    Application.init();

    Ext.fly('loading-mask').fadeOut({
         useDisplay : true
    });
});
;(function() { /* See Application.Viewport.js */
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
Application.addLinks = function() {
    var createLinks = function(links) {
        for(var i=0;i<links.length;i++) {
            var o = links[i];
            links[i] = {
                tag : 'li',
                cn  : [
                    {
                        tag : 'img',
                        src : Ext.BLANK_IMAGE_URL,
                        cls : o.iconCls || null
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
                Application.addLinks(o[i]);
            }
            return;
        }

        var panel = o, links = [].concat(panel.items);
        panel = {
            title     : panel.title || '(no text)',
            iconCls   : panel.iconCls || null,
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


Application.on('start', function() {
    nav.doLayout();
    Ext.fly('navigation').on('click', function() {
        this.blur(); // blur the focus
    }, null, { delegate : 'a' });
});
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
Application.addModule = panel.add.createDelegate(panel);
Application.on('start', function() {
    Application.changePage = panel.layout.setActiveItem.createDelegate(panel.layout);
});
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

})(); /* See Application.Navigation.js */
