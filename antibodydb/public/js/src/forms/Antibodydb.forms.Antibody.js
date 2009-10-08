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

Antibodydb.modules.forms.Antibody = {
    xtype        : 'antibodydb.form',
    title        : 'Antibody',
    iconCls      : Antibodydb.getIconCls('form'),
    fileUpload   : true,
    defaultType  : 'textfield',
    labelWidth   : 120,
    monitorValid : true,
    url          : Antibodydb.urls.antibody.save,
    loadUrl      : Antibodydb.urls.antibody.load,
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
                            url        : Antibodydb.urls.targetprotein.select,
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
                            Antibodydb.modules.forms.TargetproteinWindow.show();
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
                            url        : Antibodydb.urls.targetprotein.select,
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
                            Antibodydb.modules.forms.IncubationprotocolWindow.show();
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
};
})();
