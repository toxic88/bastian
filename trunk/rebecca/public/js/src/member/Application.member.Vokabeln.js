Ext.apply(Application.urls, {
    vokabeln : {
        read    : './vokabeln/select/format/json',
        create  : './vokabeln/create/format/json',
        update  : './vokabeln/update/format/json',
        destroy : './vokabeln/destroy/format/json'
    }
});

Application.modules.Vokabeln = {
    title    : 'Vokabeln',
    iconCls  : 'icon-table',
    xtype    : 'editorgrid',
    loadMask : true,
    layout   : 'fit', // for the afterlayout listener
    listeners : {
        afterlayout : {
            fn : function() {
                this.store.load();
            },
            single : true
        },
        rowcontextmenu : function(g, i, e) {
            var me = this;
            me.getSelectionModel().selectRow(i);
            if (!me.menu) {
                me.menu = new Ext.menu.Menu({
                    items : [
                        {
                            text    : 'Vokablen entfernen',
                            iconCls : 'icon-delete',
                            handler : function() {
                                me.onRemove();
                            }
                        },
                        {
                            text    : 'Vokabeln hinzuf&uuml;gen',
                            iconCls : 'icon-add',
                            handler : function() {
                                me.onAdd();
                            }
                        }
                    ]
                });
            }
            e.stopEvent();
            me.menu.showAt(e.getXY());
        }
    },
    keys : [
        {
            key : Ext.EventObject.ENTER,
            fn : function() {
                var me = Application.modules.Vokablen;
                if (me.activeEditor === null) {
                    var r = me.getSelectionModel().getSelected();
                    me.startEditing(me.store.indexOf(r), 1);
                }
            }
        },
        {
            key : Ext.EventObject.DELETE,
            fn : function() {
                Application.modules.Vokabeln.onRemove();
            }
        }
    ],
    getKeyMap : function(){
        if(!this.keyMap){
            this.keyMap = new Ext.KeyMap(this.body, this.keys);
        }
        return this.keyMap;
    },
    viewConfig : {
        forceFit : true
    },
    onRemove : function() {
    	var s = this.getSelectionModel();
    	if (s.hasSelection()) {
            s = s.getSelections();
            for (var i = 0, r; r = s[i]; i++){
                this.store.remove(r);
            }
        }
    },
    onAdd : function() {
        var r = new this.store.recordType({});
        this.stopEditing();
        this.store.insert(0, r);
        this.startEditing(0, 0);
    },
    tbar : {
        listeners : {
            afterlayout : {
                fn : function() {
                    this.search.store = this.ownerCt.store; // reference the grid.store to search.store
                },
                single : true
            }
        },
        items : [
            {
                text    : 'Vokabeln hinzuf&uuml;gen',
                iconCls : 'icon-add',
                handler : function() {
                    this.ownerCt.ownerCt.onAdd();
                }
            },
            {
                ref      : '../removeBtn', // reference to the grid
                text     : 'Vokabeln entfernen',
                iconCls  : 'icon-delete',
                disabled : true,
                handler  : function() {
                    this.ownerCt.ownerCt.onRemove();
                }
            },
            '-',
            'Suchen: ',
            {
                ref   : 'search', // refenrence to the tbar
                xtype : 'searchfield'
            }
        ]
    },
    store : {
        xtype           : 'jsonstore',
        autoSave        : true,
        root            : 'data',
        remoteSort      : true,
        proxy : new Ext.data.HttpProxy({
            api : {
                read    : Application.urls.vokabeln.read,
                create  : Application.urls.vokabeln.create,
                update  : Application.urls.vokabeln.update,
                destroy : Application.urls.vokabeln.destroy
            }
        }),
        fields : [
            {
                name : 'id',
                type : 'int'
            },
            {
                name : 'deutsch',
                allowBlank : false
            },
            {
                name : 'englisch'
            },
            {
                name : 'spanisch'
            }
        ],
        writer : new Ext.data.JsonWriter(),
        listeners : {
            exception : function(proxy, type, action, options, res, arg) {
                if (type == 'remote') {
                    // Ext.data.Api.actions.destroy is somewhere handled by ext...
                    if (action == Ext.data.Api.actions.update) {
                        arg.reject();
                    } else if (action == Ext.data.Api.actions.create) {
                        this.remove(arg);
                    }
                    Ext.Msg.error('Fehler!', '"' + arg.data.deutsch + '" konnte nicht ver&auml;ndert werden.<br />' + res.message);
                }
            }
        }
    },
    sm : new Ext.grid.RowSelectionModel({
        listeners : {
            selectionchange : function() {
                this.grid.removeBtn.setDisabled(!this.hasSelection());
            }
        }
    }),
    cm : new Ext.grid.ColumnModel({
        defaults : {
            sortable : true
        },
        columns : [
            {
                header    : 'Deutsch',
                dataIndex : 'deutsch',
                editor    : {
                    xtype      : 'textfield',
                    allowBlank : false
                }
            },
            {
                header    : 'Englisch',
                dataIndex : 'englisch',
                editor    : {
                    xtype         : 'textfield',
                    allowBlank    : false
                }
            },
            {
                header    : 'Spanisch',
                dataIndex : 'spanisch',
                editor    : {
                    xtype         : 'textfield',
                    allowBlank    : false
                }
            }
        ]
    })
};
