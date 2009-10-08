Antibodydb.modules.forms.Incubationprotocol = {
    xtype        : 'antibodydb.form',
    title        : 'Incubationprotocol',
    iconCls      : Antibodydb.getIconCls('form'),
    defaultType  : 'textfield',
    monitorValid : true,
    labelWidth   : 110,
    url          : Antibodydb.urls.incubationprotocol.save,
    loadUrl      : Antibodydb.urls.incubationprotocol.load,
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
};

Antibodydb.modules.forms.IncubationprotocolWindow = new Ext.Window({
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
            url          : Antibodydb.urls.incubationprotocol.save,
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

                    Antibodydb.modules.forms.Antibody.Incubationprotocol.setValue(i);
                    Antibodydb.modules.forms.Antibody.Incubationprotocol.hiddenField.value = id;
                } catch(e) {}
                bform.reset();
                this.ownerCt.hide();
            }
        }
    ]
});
