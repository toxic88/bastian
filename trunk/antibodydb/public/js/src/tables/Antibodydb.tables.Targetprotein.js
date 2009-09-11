Antibodydb.modules.tables.Targetprotein = {
    xtype : 'antibodydb.editorgridpanel',
    title : 'Targetprotein',
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
            Ext.History.add('forms.Targetprotein');
			var tf = Antibodydb.modules.forms.Targetprotein;
            Antibodydb.changePage(tf); // force render!
            tf.form.reset();
            tf.form.loadRecord(rec);
        } catch(e) {};
    },
    onAdd : function() {
        Antibodydb.modules.forms.TargetproteinWindow.show();
    }
};
