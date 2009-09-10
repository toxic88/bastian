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
