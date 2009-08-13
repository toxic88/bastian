/**
 * NOTE: The changes will be in Ext 3.1
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
Ext.reg('fileuploadfield', Ext.ux.FileUploadField);
Ext.form.FileUploadField = Ext.ux.FileUploadField;
