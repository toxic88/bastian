(Ext.isIE6 || Ext.isIE7) && (Ext.BLANK_IMAGE_URL = 'images/default/s.gif');

Antibodydb = Ext.apply(new Ext.util.Observable, (function(){
    var preIconCls = 'icon-';

    return {
        
        init: function() {
            var me = this;
            
            me.addEvents('shutdown', 'start');
            
            Ext.EventManager.on(window, 'beforeunload', function(e){
                if(me.fireEvent('shutdown') === false) {
                    //e.browserEvent.returnValue = 'geh nicht weg!';
                    e.stopEvent();
                    return false;
                }
            });
            
            me.fireEvent('start');
        },
        
        getIconCls :function(cls) {
            if(typeof cls == 'object') {
                cls = cls.iconCls ? cls.iconCls : false;
            }
            return cls ? (preIconCls + cls) : null;
        },
        
        urls : {
            Logout                   : './auth/logout',

            ChangePassword           : './user/change.password/format/json',

            TargetproteinSave        : './targetprotein/save/format/json',
            TargetproteinDelete      : './targetprotein/delete/format/json',
            TargetproteinLoad        : './targetprotein/load/format/json',
            TargetproteinSelect      : './targetprotein/select/format/json',

            IncubationprotocolSave   : './incbuationprotocol/save/format/json',
            IncubationprotocolDelete : './incubationprotocol/delete/format/json',
            IncubationprotocolLoad   : './incbuationprotocol/load/format/json',
            IncubationprotocolSelect : './incubationprotocol/select/format/json',

            AntibodySave             : './antibody/save/format/json',
            AntibodyDelete           : './antibody/delete/format/json',
            AntibodyLoad             : './antibody/load/format/json',
            AntibodySelect           : './antibody/select/format/json'
        }
        
    };
}()));

Ext.ns('Antibodydb.forms', 'Antibodydb.tables', 'Antibodydb.user');

Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.apply(Ext.QuickTips.getQuickTip(), {
        maxWidth : 500
    });
            
    Antibodydb.init(); // start the application
    
    Ext.fly('loading-mask').fadeOut({
         useDisplay : true
    });
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
        
    if (Ext.isArray(o)) {                       // Allow Arrays of siblings to be inserted
        el = doc.createDocumentFragment(); // in one shot using a DocumentFragment
        Ext.each(o, function(v) {    
            Ext.DomHelper.createDomX(v, el);
        });
    } else if (typeof o == "string") {         // Allow a string as a child spec.
        el = doc.createTextNode(o);
    } else {
        el = doc.createElement( o.tag || 'div' );
        useSet = !!el.setAttribute; // In IE some elements don't have setAttribute
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
        if (o.listeners) { // added support for eventlisteners
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
Ext.ux.SelectBox = Ext.extend(Ext.form.ComboBox, {
    searchResetDelay : 100,
    editable         : false,
    forceSelection   : true,
    rowHeight        : false,
    lastSearchTerm   : false,
    triggerAction    : 'all',
    mode             : 'local',
    lazyInit         : false,
    
	constructor : function(config) {
	    Ext.ux.SelectBox.superclass.constructor.call(this, config);
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
        Ext.ux.SelectBox.superclass.initComponent.apply(this, arguments);
    },
    
    initEvents : function(){
        Ext.ux.SelectBox.superclass.initEvents.apply(this, arguments);
        // you need to use keypress to capture upper/lower case and shift+key, but it doesn't work in IE
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

        // skip special keys other than the shift key
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
        Ext.ux.SelectBox.superclass.onRender.apply(this, arguments);
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
        Ext.ux.SelectBox.superclass.render.apply(this, arguments);
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
Ext.reg('selectbox', Ext.ux.SelectBox);
Ext.form.SelectBox = Ext.ux.SelectBox;
/**
 * with changes from http://extjs.com/forum/showthread.php?p=356809#post356809, http://extjs.com/forum/showthread.php?p=262550#post262550
 */
Ext.ux.FileUploadField = Ext.extend(Ext.form.TextField,  {
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

    // private
    readOnly : true,
    
    /**
     * @hide 
     * @method autoSize
     */
    autoSize : Ext.emptyFn,
    
    // private
    initComponent: function() {
        Ext.ux.FileUploadField.superclass.initComponent.call(this);
        
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
    
    // private
    onRender : function(ct, position) {
        Ext.ux.FileUploadField.superclass.onRender.call(this, ct, position);
        
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
        Ext.ux.FileUploadField.superclass.reset.call(this);
    },
    
    onDestroy : function() {
        if ( this.fileInput ) {
            Ext.destroy(this.fileInput);
            this.fileInput = null;
        }

        if ( this.button ) {
            this.button.destroy();
            this.button = null;
        }

        Ext.ux.FileUploadField.superclass.onDestroy.call(this);
    },
    
    onEnable : function() {
        Ext.ux.FileUploadField.superclass.onEnable.call(this);
        this.fileInput.dom.disabled = false;
        this.button.enable();
    },

    onDisable : function() {
        Ext.ux.FileUploadField.superclass.onDisable.call(this);
        this.fileInput.dom.disabled = true;
        this.button.disable();
    },
    
    // private
    getFileInputId : function() {
        return this.id+'-file';
    },
    
    // private
    onResize : function(w, h) {
        Ext.ux.FileUploadField.superclass.onResize.call(this, w, h);
        
        this.wrap.setWidth(w);
        
        if ( !this.buttonOnly ){
            var w = this.wrap.getWidth() - this.button.getEl().getWidth() - this.buttonOffset;
            this.el.setWidth(w);
        }
    },
    
    // private
    preFocus : Ext.emptyFn,
    
    // private
    getResizeEl : function(){
        return this.wrap;
    },

    // private
    getPositionEl : function(){
        return this.wrap;
    },

    // private
    alignErrorIcon : function(){
        this.errorIcon.alignTo(this.wrap, 'tl-tr', [2, 0]);
    }
    
});
Ext.reg('fileuploadfield', Ext.ux.FileUploadField);
Ext.form.FileUploadField = Ext.ux.FileUploadField;
Ext.ux.SearchField = Ext.extend(Ext.form.TwinTriggerField, {
    initComponent : function(){
        Ext.ux.SearchField.superclass.initComponent.call(this);
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
Ext.reg('searchfield', Ext.ux.SearchField);
Ext.form.SearchField = Ext.ux.SearchField;
/*Ext.override(Ext.form.FormPanel, {
    createForm : function() {
        var config = Ext.copyTo({}, this.initialConfig, 'api,baseParams,errorReader,fileUpload,method,paramOrder,paramsAsHash,reader,standardSubmit,timeout,trackResetOnLoad,url');
        return new Ext.form.BasicForm(null, config);
    },
    send : function() {
        return this.getForm().submit({ // submit the form
            waitMsg   : 'Sending data...',
            success   : this.handleSuccess,
            failure   : this.handleFailure,
            scope     : this // scope of the handler functions
        });
    },
    handleFailure : function(bform, action) { // default handler
        Ext.Msg.error('Action Failed!', action.result ? action.result.error : 'Server Error', function() {
            bform.items.first().focus();
        });
    },
    handleSuccess : function(bform, action) { // default handler
        bform.reset();
    },
    load : function(id) {
        this.getForm().load({
            url     : this.loadUrl,
            params : {
                id : id
            },
            waitMsg   : 'Sending data...',
            success   : this.handleLoadSuccess,
            failure   : this.handleLoadFailure,
            scope     : this // scope of the handler functions
        });
    },
    handleLoadSuccess : function(bform, action) {

    },
    handleLoadFailure : function(bform, action) {
        Ext.Msg.error('Action Failed!', action.result ? action.result.error : 'Server Error', function() {
            bform.items.first().focus();
        });
    }
});*/

Antibodydb.FormPanel = Ext.extend(Ext.form.FormPanel, {
	iconCls : Antibodydb.getIconCls('form'),
	defaultType : 'textfield',
	
    createForm : function() {
        var config = Ext.copyTo({}, this.initialConfig, 'api,baseParams,errorReader,fileUpload,method,paramOrder,paramsAsHash,reader,standardSubmit,timeout,trackResetOnLoad,url');
        return new Ext.form.BasicForm(null, config);
    },
	
	send : function() {
        return this.getForm().submit({ // submit the form
            waitMsg   : 'Sending data...',
            success   : this.handleSuccess,
            failure   : this.handleFailure,
            scope     : this // scope of the handler functions
        });
	},
    handleFailure : function(bform, action) { // default handler
        Ext.Msg.error('Action Failed!', action.result ? action.result.error : 'Server Error', function() {
            bform.items.first().focus();
        });
    },
    handleSuccess : function(bform, action) { // default handler
        bform.reset();
    }
	
});
Ext.reg('antibodydb.form', Antibodydb.FormPanel);
Antibodydb.EditorGridPanel = Ext.extend(Ext.grid.EditorGridPanel, {
    layout   : 'fit',
    iconCls  : Antibodydb.getIconCls('table'),
    loadMask : true,
    viewConfig : {
        forceFit       : true,
        emptyText      : 'No records found.'
    },
    sm : new Ext.grid.RowSelectionModel({
        singleSelect : true
    }),
    
    constructor : function(config) {
        this.supr().constructor.call(this, config);
        
        this.on('rowcontextmenu', this.onContextClick, this);
        this.on({
            afterlayout : {
                fn : function() {
                    this.store.load({
                        params : {
                            start : 0,
                            limit : 25
                        }
                    });
                },
                scope  : this,
                single : true
            }
        });
    },
    
    onContextClick : function(grid, index, e) {
        this.getSelectionModel().selectRow(index);
        if (!this.menu) {
            this.menu = new Ext.menu.Menu({
                items : [
                    {
                        text    : 'Edit',
                        iconCls : Antibodydb.getIconCls('edit'),
                        handler : this.onEdit,
                        scope   : this
                    },
                    {
                        text    : 'Remove',
                        iconCls : Antibodydb.getIconCls('remove'),
                        handler : this.onRemove,
                        scope   : this
                    },
                    '-',
                    {
                        text    : 'Add',
                        iconCls : Antibodydb.getIconCls('add'),
                        handler : this.onAdd,
                        scope   : this
                    }
                ]
            });
        }            
        e.stopEvent()
        this.menu.showAt(e.getXY());
    },
    
    initComponent : function() {
        this.tbar = this.buildTopToolbar();
        this.bbar = this.buildBottomToolbar();
        this.keys = this.buildKeys();
        
        this.supr().initComponent.call(this);
    },
    
    buildTopToolbar : function() {
        return [
            {
                text    : 'Add',
                iconCls : Antibodydb.getIconCls('add'),
                handler : this.onAdd,
                scope   : this
            },
            {
                text    : 'Edit',
                iconCls : Antibodydb.getIconCls('edit'),
                handler : this.onEdit,
                scope   : this
            },
            {
                text    : 'Remove',
                iconCls : Antibodydb.getIconCls('remove'),
                handler : this.onRemove,
                scope   : this
            },
            'Search:',
            {
                xtype : 'searchfield',
                store : this.store
            }
        ];
    },
    
    buildBottomToolbar : function() {
        return {
            xtype       : 'paging',
            store       : this.store,
            pageSize    : 25,
            displayInfo : true
        };
    },
    
    buildKeys : function() {
        return [
            {
                key   : Ext.EventObject.DELETE,
                fn    : this.onRemove,
                scope : this
            },
            {
                key   : Ext.EventObject.ENTER,
                fn    : this.onEdit,
                scope : this
            }
        ];
    },

    /**
     * @override
     * changed the target of the Ext.KeyMap to this.body
     */
    getKeyMap : function(){
        if(!this.keyMap){
            this.keyMap = new Ext.KeyMap(this.body, this.keys);
        }
        return this.keyMap;
    },
    
    /**
     * Abstract methods which have to be implemented
     */
    onRemove : Ext.emptyFn,
    onEdit : Ext.emptyFn,
    onAdd : Ext.emptyFn
    
});
Ext.reg('antibodydb.editorgridpanel', Antibodydb.EditorGridPanel);
(function() {
var assertStore = new Ext.data.ArrayStore({
    fields : ['value', 'text'],
    data   : [
        [0, ' - '],
        [1, 'Very good'],
        [2, 'Good'],
        [3, 'Bad'],
        [4, 'Very bad']
    ]
});

Antibodydb.forms.Antibody = new Antibodydb.FormPanel({
    title        : 'Antibody',
    iconCls      : Antibodydb.getIconCls('form'),
    fileUpload   : true,
    defaultType  : 'textfield',
    labelWidth   : 120,
    monitorValid : true,
    url          : Antibodydb.urls.AntibodySave,
    loadUrl      : Antibodydb.urls.AntibodyLoad,
    defaults     : {
        anchor : '-20'
    },
    items : [
        {
            name       : 'Antibody:Antibody',
            fieldLabel : 'Antibody',
            allowBlank : false
        },
        {
            name       : 'Antibody:Lot',
            fieldLabel : 'Lot#'
        },
        {
            xtype    : 'container',
            layout   : 'column',
            defaults : {
                layout : 'form'
            },
            items  : [
                {
                    columnWidth : 1,
                    items : {
                        ref            : '../../Targetprotein',
                        xtype          : 'combo',
                        allowBlank     : false,
                        anchor         : '-5',
                        forceSelection : true,
                        fieldLabel     : 'Target Protein',
                        displayField   : 'Targetprotein:Targetprotein',
                        valueField     : 'Targetprotein:id',
                        hiddenName     : 'Antibody:fs_T_Targetprotein_id',
                        triggerAction  : 'all',
                        store : {
                            xtype      : 'jsonstore',
                            url        : Antibodydb.urls.TargetproteinSelect,
                            root       : 'data',
                            idProperty : 'Targetprotein:id',
                            fields     : [ 'Targetprotein:id', 'Targetprotein:Targetprotein' ]
                        }
                    }
                },
                {
                    width : 70,
                    items : {
                        xtype   : 'button',
                        text    : 'Create New',
                        handler : function() {
                            Antibodydb.forms.TargetproteinWindow.show();
                        }
                    }
                }
            ]
        },
        {
            name       : 'Antibody:Source',
            fieldLabel : 'Source'
        },
        {
            name       : 'Antibody:Dilution_Western',
            fieldLabel : 'Dilution Western'
        },
        {
            xtype    : 'container',
            layout   : 'column',
            defaults : {
                layout : 'form'
            },
            items  : [
                {
                    columnWidth : 1,
                    items : {
                        ref            : '../../Incubationprotocol',
                        xtype          : 'combo',
                        anchor         : '-5',
                        hiddenName     : 'Antibody:fs_T_Incubationprotocol_id',
                        fieldLabel     : 'Incubation Protocol',
                        triggerAction  : 'all',
                        forceSelection : true,
                        displayField   : 'Incubationprotocol',
                        valueField     : 'id',
                        store : {
                            xtype      : 'jsonstore',
                            url        : Antibodydb.urls.TargetproteinSelect,
                            root       : 'data',
                            idProperty : 'id',
                            fields     : [ 'id', 'Incubationprotocol' ]
                        }
                    }
                },
                {
                    width : 70,
                    items : {
                        xtype   : 'button',
                        text    : 'Create New',
                        handler : function() {
                            Antibodydb.forms.IncubationprotocolWindow.show();
                        }
                    }
                }
            ]
        },
        {
            name       : 'Stock',
            fieldLabel : 'Stock'
        },
        {
            xtype       : 'fieldset',
            title       : 'Comments',
            collapsible : true,
            autoHeight  : true,
            anchor      : '-20',
            items : [
                {
                    xtype    : 'container',
                    layout   : 'column',
                    defaults : { 
                        layout : 'form'
                    },
                    items : [
                        {
                            columnWidth : 1,
                            defaultType : 'textarea',
                            labelWidth  : 150,
                            defaults    : {
                                anchor : '-5',
                                height : 70
                            },
                            items : [
                                {
                                    name       : 'Western',
                                    fieldLabel : 'Evaluation Western'
                                },
                                {
                                    name       : 'Capture_Array',
                                    fieldLabel : 'Evaluation Capture Array'
                                },
                                {
                                    name       : 'RPPA',
                                    fieldLabel : 'Evaluation RPPA'
                                },
                                {
                                    name       : 'IP',
                                    fieldLabel : 'IP'
                                },
                                {
                                    name       : '[IF]',
                                    fieldLabel : 'IF'
                                },
                                {
                                    name       : 'IHC',
                                    fieldLabel : 'IHC'
                                },
                                {
                                    name       : 'FACS',
                                    fieldLabel : 'FACS'
                                }
                            ]
                        },
                        {
                            width       : 150,
                            defaultType : 'selectbox',
                            defaults : {
                                anchor       : '100%',
                                hideLabel    : true,
                                lazyRender   : true,
                                ctCls        : 'form-comments-selectbox',
                                store        : assertStore,
                                displayField : 'text',
                                valueField   : 'value',
                                value        : 0
                            },
                            items : [
                                {
                                    hiddenName : 'Evaluation_Western'
                                },
                                {
                                    hiddenName : 'Evaluation_Capture_Array'
                                },
                                {
                                    hiddenName : 'Evaluation_RPPA'
                                },
                                {
                                    hiddenName : 'Evaluation_IP'
                                },
                                {
                                    hiddenName : 'Evaluation_IF'
                                },
                                {
                                    hiddenName : 'Evaluation_IHC'
                                },
                                {
                                    hiddenName : 'Evaluation_FACS'
                                }
                            ]
                        }
                    ]
                }
            ]
        },
        {
            xtype       : 'fieldset',
            title       : 'Others',
            collapsible : true,
            autoHeight  : true,
            anchor      : '-20',
            defaultType : 'textfield',
            defaults    : {
                anchor:'100%'
            },
            items : [
                {
                    name       : 'Datasheet',
                    xtype      : 'fileuploadfield',
                    fieldLabel : 'Datasheet'
                },
                {
                    name       : 'Embedding',
                    fieldLabel : 'Embedding'
                },
                {
                    name       : 'Fixation',
                    fieldLabel : 'Fixation'
                }
            ]  
        }
    ],
    buttons : [
        {
            formBind : true,
            text     : 'Save',
            handler  : function() {
                this.ownerCt.ownerCt.send();
            }
        },
        {
            text : 'Reset',
            handler : function() {
                this.ownerCt.ownerCt.getForm().reset();
                this.ownerCt.ownerCt.items.first().focus(false, 400);
            }
        }
    ]
});
})();
Antibodydb.forms.Targetprotein = new Antibodydb.FormPanel({
    title        : 'Target Protein',
    iconCls      : Antibodydb.getIconCls('form'),
    url          : Antibodydb.urls.TargetproteinSave,
    loadUrl      : Antibodydb.urls.TargetproteinLoad,
    defaultType  : 'textfield',
    monitorValid : true,
    labelWidth   : 130,
    defaults     : {
        anchor : '-20'
    },
    items : [
        {
            xtype : 'hidden',
            name  : 'Targetprotein:id'
        },
        {
            name       : 'Targetprotein:Targetprotein',
            fieldLabel : 'Target Protein',
            allowBlank : false
        },
        {
            name       : 'Targetprotein:MW_kD',
            fieldLabel : 'MW [kD]'
        },
        {
            name       : 'Targetprotein:SwissProt_Accsession',
            fieldLabel : 'SwissProt Accsession#'
        },
        {
            name       : 'Targetprotein:Supplier',
            fieldLabel : 'Supplier'
        },
        {
            name       : 'Targetprotein:Stock',
            fieldLabel : 'Stock'
        },
        {
            name       : 'Targetprotein:References',
            fieldLabel : 'References'
        }
    ],
    buttons : [
        {
            formBind : true,
            text     : 'Save',
            handler  : function() {
                this.ownerCt.ownerCt.send();
            }
        },
        {
            text : 'Reset',
            handler : function() {
                this.ownerCt.ownerCt.getForm().reset();
                this.ownerCt.ownerCt.items.first().focus(false, 400);
            }
        }
    ]
});

Antibodydb.forms.TargetproteinWindow = new Ext.Window({
    title       : 'Target Protein',
    layout      : 'fit',
    iconCls     : Antibodydb.getIconCls('form'),
    modal       : true,
    closeAction : 'hide',
    resizable   : false,
    border      : false,
    width       : 445,
    height      : 245,
    listeners   : {
        show : function() {
            this.form.items.first().focus(false, 400);
        },
        hide : function() {
            this.form.getForm().reset();
        }
    },
    items : [
        {
            xtype        : 'antibodydb.form',
            ref          : 'form',
            url          : Antibodydb.urls.TargetproteinSave,
            frame        : true,
            monitorValid : true,
            labelWidth   : 130,
            defaultType  : 'textfield',
            defaults     : {
                anchor     : '100%'
            },
            items : [
                {
                    name       : 'Targetprotein:Targetprotein',
                    fieldLabel : 'Target Protein',
                    allowBlank : false
                },
                {
                    name       : 'Targetprotein:MW_kD',
                    fieldLabel : 'MW [kD]'
                },
                {
                    name       : 'Targetprotein:SwissProt_Accsession',
                    fieldLabel : 'SwissProt Accsession#'
                },
                {
                    name       : 'Targetprotein:Supplier',
                    fieldLabel : 'Supplier'
                },
                {
                    name       : 'Targetprotein:Stock',
                    fieldLabel : 'Stock'
                },
                {
                    name       : 'Targetprotein:References',
                    fieldLabel : 'References'
                }
            ],
            buttons : [
                {
                    formBind : true,
                    text     : 'Save',
                    handler  : function() {
                        this.ownerCt.ownerCt.send();
                    }
                },
                {
                    text     : 'Close',
                    handler  : function() {
                        this.ownerCt.ownerCt.ownerCt.hide();
                    }
                }
            ],
            handleSuccess : function(bform, action) {
                try {
                    var id = action.result.data.id;
                    var t  = action.result.data.Targetprotein;
                    
                    Antibodydb.forms.Antibody.Targetprotein.setValue(t);
                    Antibodydb.forms.Antibody.Targetprotein.hiddenField.value = id;
                    Antibodydb.tables.Targetprotein.getStore().reload();
                } catch(e) {}
                bform.reset();
                this.ownerCt.hide();
            }
        }
    ]
});
Antibodydb.forms.Incubationprotocol = new Antibodydb.FormPanel({
    title        : 'Incubationprotocol',
    iconCls      : Antibodydb.getIconCls('form'),
    defaultType  : 'textfield',
    monitorValid : true,
    labelWidth   : 110,
    url          : Antibodydb.urls.IncubationprotocolSave,
    loadUrl      : Antibodydb.urls.IncubationprotocolLoad,
    defaults     : {
        anchor : '-20'
    },
    items : [
        {
            name       : 'Incubationprotocol:Incubationprotocol',
            fieldLabel : 'Incubationprotocol',
            allowBlank : false
        },
        {
            name       : 'Incubationprotocol:Blocking',
            fieldLabel : 'Blocking'
        },
        {
            name       : 'Incubationprotocol:Incubation_1',
            fieldLabel : '1°AB Incubation'
        },
        {
            name       : 'Incubationprotocol:Washing_1',
            fieldLabel : 'Washing I'
        },
        {
            name       : 'Incubationprotocol:Incubation_2',
            fieldLabel : '2°AB Incubation'
        },
        {
            name       : 'Incubationprotocol:Washing_2',
            fieldLabel : 'Washing II'
        },
        {
            xtype       : 'fieldset',
            title       : 'Bufferset',
            autoHeight  : true,
            anchor      : '-20',
            defaultType : 'textfield',
            labelWidth  : 100,
            defaults    : {
                anchor : '100%'
            },
            items : [
                {
                    name       : 'Bufferset:Bufferset',
                    fieldLabel : 'Bufferset',
                    allowBlank : false
                },
                {
                    name       : 'Bufferset:Washbuffer',
                    fieldLabel : 'Washbuffer'
                },
                {
                    name       : 'Bufferset:Incubation_1',
                    fieldLabel : '1°AB Incubation'
                },
                {
                    name       : 'Bufferset:Incubation_2',
                    fieldLabel : '2°AB Incubation'
                },
                {
                    name       : 'Bufferset:Blocking',
                    fieldLabel : 'Blocking'
                }
            ]
        }
    ],
    buttons : [
        {
            formBind : true,
            text     : 'Save',
            handler  : function() {
                this.ownerCt.ownerCt.send();
            }
        },
        {
            text : 'Reset',
            handler : function() {
                this.ownerCt.ownerCt.getForm().reset();
                this.ownerCt.ownerCt.items.first().focus(false, 400);
            }
        }
    ]
});

Antibodydb.forms.IncubationprotocolWindow = new Ext.Window({
    title       : 'Incubationprotocol',
    layout      : 'fit',
    iconCls     : Antibodydb.getIconCls('form'),
    modal       : true,
    closeAction : 'hide',
    resizable   : false,
    border      : false,
    width       : 500,
    height      : 410,
    listeners   : {
        show : function() {
            this.form.items.first().focus(false, 400);
        },
        hide : function() {
            this.form.getForm().reset();
        }
    },
    items : [
        {
            xtype        : 'antibodydb.form',
            ref          : 'form',
            url          : Antibodydb.urls.IncubationprotocolSave,
            frame        : true,
            monitorValid : true,
            labelWidth   : 110,
            defaultType  : 'textfield',
            defaults     : {
                anchor     : '100%'
            },
            items : [
                {
                    name       : 'Incubationprotocol',
                    fieldLabel : 'Incubationprotocol',
                    allowBlank : false
                },
                {
                    name       : 'Blocking',
                    fieldLabel : 'Blocking'
                },
                {
                    name       : 'Incubation_1',
                    fieldLabel : '1°AB Incubation'
                },
                {
                    name       : 'Washing_1',
                    fieldLabel : 'Washing I'
                },
                {
                    name       : 'Incubation_2',
                    fieldLabel : '2°AB Incubation'
                },
                {
                    name       : 'Washing_2',
                    fieldLabel : 'Washing II'
                },
                {
                    xtype       : 'fieldset',
                    title       : 'Bufferset',
                    autoHeight  : true,
                    defaultType : 'textfield',
                    labelWidth  : 100,
                    defaults    : {
                        anchor : '100%'
                    },
                    items : [
                        {
                            name       : 'Bufferset',
                            fieldLabel : 'Bufferset',
                            allowBlank : false
                        },
                        {
                            name       : 'Washbuffer',
                            fieldLabel : 'Washbuffer'
                        },
                        {
                            name       : 'Incubation_1',
                            fieldLabel : '1°AB Incubation'
                        },
                        {
                            name       : 'Incubation_2',
                            fieldLabel : '2°AB Incubation'
                        },
                        {
                            name       : 'Blocking',
                            fieldLabel : 'Blocking'
                        }
                    ]
                }
            ],
            buttons : [
                {
                    formBind : true,
                    text     : 'Save',
                    handler  : function() {
                        this.ownerCt.ownerCt.send();
                    }
                },
                {
                    text     : 'Close',
                    handler  : function() {
                        this.ownerCt.ownerCt.ownerCt.hide();
                    }
                }
            ],
            handleSuccess : function(bform, action) {
                try {
                    var id = action.result.data.id;
                    var i  = action.result.data.Incubationprotocol;

                    Antibodydb.forms.Antibody.Incubationprotocol.setValue(i);
                    Antibodydb.forms.Antibody.Incubationprotocol.hiddenField.value = id;
                } catch(e) {}
                bform.reset();
                this.ownerCt.hide();
            }
        }
    ]
});
Antibodydb.tables.Targetprotein = new Antibodydb.EditorGridPanel({
    title    : 'Targetprotein',
    store : new Ext.data.JsonStore({
        root       : 'data',
        remoteSort : true,
        idProperty : 'Targetprotein:id',
        proxy : new Ext.data.HttpProxy({
            api : {
                read    : Antibodydb.urls.TargetproteinSelect,
                create  : Antibodydb.urls.TargetproteinSave,
                update  : Antibodydb.urls.TargetproteinSave,
                destroy : Antibodydb.urls.TargetproteinDelete
            }
        }),
        fields : [
            { name : 'Targetprotein:id', type : 'int' },
            'Targetprotein:Targetprotein',
            'Targetprotein:MW_kD',
            'Targetprotein:SwissProt_Accsession',
            'Targetprotein:Supplier',
            'Targetprotein:Stock',
            'Targetprotein:References'
        ],
        writer : new Ext.data.JsonWriter({
            encode         : true,
            writeAllFields : true
        }),
        listeners : {
            exception : function(eins, zwei, drei, res, result, rec) {
                try {
                    rec.reject(); // reject faild updates
                } catch(e) {}
            }
        }
    }),
    cm : new Ext.grid.ColumnModel({
        defaults : {
            sortable : true,
            editor   : new Ext.form.TextField()
        },
        columns : [
            {
                header    : 'Targetprotein',
                dataIndex : 'Targetprotein:Targetprotein'
            },
            {
                header    : 'MW [kD]',
                dataIndex : 'Targetprotein:MW_kD'
            },
            {
                header    : 'SwissProt Accsession#',
                dataIndex : 'Targetprotein:SwissProt_Accsession'
            },
            {
                header    : 'Supplier',
                dataIndex : 'Targetprotein:Supplier'
            },
            {
                header    : 'Stock',
                dataIndex : 'Targetprotein:Stock'
            },
            {
                header    : 'References',
                dataIndex : 'Targetprotein:References'
            }
        ]
    }),
    onRemove : function() {
        var rec = this.getSelectionModel().getSelected();
        if (!rec) {
            return;
        }
        Ext.Msg.confirm('Remove Targetprotein', 'Do you really want to remove "' + rec.data.Targetprotein + '"?', function(btn) {
            if (btn === 'yes') {
                this.store.remove(rec);
            }
        }, this);
    },
    onEdit : function() {
        var rec = this.getSelectionModel().getSelected();
        if (!rec) {
            return;
        }
        try {
            Ext.History.add('Antibodydb.forms.Targetprotein');
			var tf = Antibodydb.forms.Targetprotein;
            Antibodydb.changePage(tf); // force render!
            tf.form.reset();
            tf.form.loadRecord(rec);
        } catch(e) {};
    },
    onAdd : function() {
        Antibodydb.forms.TargetproteinWindow.show();
    }
});


Antibodydb.user.Password = new Ext.Window({
    title       : 'Change Password',
    layout      : 'fit',
    modal       : true,
    closeAction : 'hide',
    resizable   : false,
    border      : false,
    iconCls     : Antibodydb.getIconCls('keygo'),
    width       : 370,
    height      : 165,
    listeners   : {
        show : function() {
            this.form.items.first().focus(false, 400);
        },
        hide : function() {
            this.form.getForm().reset();
        }
    },
    items : [
        {
            xtype        : 'form',
            ref          : 'form',
            url          : Antibodydb.urls.ChangePassword,
            frame        : true,
            monitorValid : true,
            labelWidth   : 135,
            defaultType  : 'textfield',
            defaults     : {
                allowBlank : false,
                anchor     : '100%',
                inputType  : 'password'
            },
            items : [
                {
                    fieldLabel : 'Current Password',
                    name       : 'oldpassword'
                },
                {
                    fieldLabel : 'New Password',
                    name       : 'newpassword1',
                    minLength  : 4
                },
                {
                    fieldLabel : 'Confirm New Password',
                    name       : 'newpassword2',
                    minLength  : 4
                }
            ],
            buttons : [
                {
                    formBind : true,
                    text     : 'Save',
                    handler  : function() {
                        this.ownerCt.ownerCt.send();
                    }
                },
                {
                    text     : 'Close',
                    handler  : function() {
                        this.ownerCt.ownerCt.ownerCt.hide();
                    }
                }
            ],
            handleSuccess : function(bform, action) {
            	this.ownerCt.hide();
            }
        }
    ]
});
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
Antibodydb.Viewport = new Ext.Viewport({
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
        Antibodydb.Navigation,
        Antibodydb.Panel
    ]
});
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
