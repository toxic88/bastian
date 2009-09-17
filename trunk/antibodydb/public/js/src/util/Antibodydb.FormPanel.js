Antibodydb.FormPanel = Ext.extend(Ext.form.FormPanel, {
    iconCls     : Antibodydb.getIconCls('form'),
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
