Ext.apply(Application.urls, {
    user : {
        read    : './admin/user/select/format/json',
        create  : './admin/user/create/format/json',
        update  : './admin/user/update/format/json',
        destroy : './admin/user/destroy/format/json'
    }
});

Application.modules.UserManager = {
    title    : 'Benutzer Manager',
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
                            text    : 'Benutzer entfernen',
                            iconCls : 'icon-user-delete',
                            handler : function() {
                                me.onRemove();
                            }
                        },
                        {
                            text    : 'Benutzer hinzuf&uuml;gen',
                            iconCls : 'icon-user-add',
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
                var me = Application.modules.UserManager;
                if (me.activeEditor === null) {
                    var r = me.getSelectionModel().getSelected();
                    me.startEditing(me.store.indexOf(r), 1);
                }
            }
        },
        {
            key : Ext.EventObject.DELETE,
            fn : function() {
                Application.modules.UserManager.onRemove();
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
        this.startEditing(0, 1);
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
                text    : 'Benutzer hinzuf&uuml;gen',
                iconCls : 'icon-user-add',
                handler : function() {
                    this.ownerCt.ownerCt.onAdd();
                }
            },
            {
                ref      : '../removeBtn', // reference to the grid
                text     : 'Benutzer entfernen',
                iconCls  : 'icon-user-delete',
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
        totalProperty   : 'total',
        successProperty : 'success',
        idProperty      : 'id',
        root            : 'data',
        remoteSort      : true,
        proxy : new Ext.data.HttpProxy({
            api : {
                read    : Application.urls.user.read,
                create  : Application.urls.user.create,
                update  : Application.urls.user.update,
                destroy : Application.urls.user.destroy
            }
        }),
        fields : [
            {
                name : 'id',
                type : 'int'
            },
            {
                name : 'username',
                allowBlank : false
            },
            {
                name : 'password',
                allowBlank : false
            },
            {
                name : 'rights',
                type : 'int',
                allowBlank : false
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
                    Ext.Msg.error('Fehler!', '"' + arg.data.username + '" konnte nicht ver&auml;ndert werden.<br />' + res.message);
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
                header    : 'id',
                dataIndex : 'id',
                width     : 20
            },
            {
                header    : 'Benutzername',
                dataIndex : 'username',
                editor    : {
                    xtype      : 'textfield',
                    allowBlank : false
                }
            },
            {
                header    : 'Passwort',
                dataIndex : 'password',
                editor    : {
                    xtype         : 'textfield',
                    allowBlank    : false,
                    selectOnFocus : true
                }
            },
            {
                header    : 'Rechte',
                dataIndex : 'rights',
                renderer  : function(v) {
                    switch(v) {
                        case 1:
                            return 'Admin';
                            break;
                        case 2:
                            return 'Benutzer';
                            break;
                        default:
                            return v;
                            break;
                    }
                },
                editor : {
                    xtype        : 'selectbox',
                    displayField : 'text',
                    valueField   : 'value',
                    store  : {
                        xtype : 'arraystore',
                        data  : [
                            [ 1, 'Admin' ],
                            [ 2, 'Benutzer' ]
                        ],
                        fields : [ 'value', 'text' ]
                    }
                }
            }
        ]
    })
};
