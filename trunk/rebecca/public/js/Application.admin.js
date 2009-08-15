;(function() {
var data = [
    [ '7:50 - 8:35',    ' - ',      'Physik',   'Englisch', 'G/GK',     'Sport',    1 ],
    [ '8:35 - 9:20',    ' - ',      'Physik',   'Englisch', 'G/GK',     'Sport',    2 ],
    [ '9:25 - 10:10',   'Mathe',    'Religion', 'Chemie',   'Biologie', 'VBRW',     3 ],
    [ '10:30 - 11:15',  'Deutsch',  'Religion', 'Chemie',   'Deutsch',  'VBRW',     4 ],
    [ '11:20 - 12:05',  'Spanisch', 'VBRW',     'Mathe',    'Mathe',    'Spanisch', 5 ],
    [ '12:10 - 12:55',  'Spanisch', 'VBRW',     'Mathe',    'Englisch', 'Spanisch', 6 ],
    [ '13:00 - 13:45',  ' - ',      'Deutsch',  ' - ',      'Biologie', '',         7 ],
    [ '13:45 - 14:30',  'Info',     '',         'VBRW',     '',         '',         8 ],
    [ '14:35 - 15:20',  'Info',     '',         'VBRW',     '',         '',         9 ]
];

Application.modules.Stundenplan = {
    title            : 'Stundenplan',
    xtype            : 'grid',
    iconCls          : 'icon-table',
    columnLines      : true,
    enableColumnMove : false,
    viewConfig : {
        forceFit : true
    },
    store  : {
        xtype : 'arraystore',
        data  : data,
        fields: [
            'zeit',
            'montag',
            'dienstag',
            'mittwoch',
            'donnerstag',
            'freitag',
            'stunde'
        ]
    },
    cm : new Ext.grid.ColumnModel({
        defaults : {
            align        : 'center',
            resizable    : false,
            sortable     : false,
            menuDisabled : true
        },
        columns : [
            { header: 'Zeit' },
            { header: 'Montag' },
            { header: 'Dienstag' },
            { header: 'Mittwoch' },
            { header: 'Donnerstag' },
            { header: 'Freitag' },
            { header: 'Stunde', width : 50 }
        ]
    })
};
})();Ext.apply(Application.urls, {
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
    autoEncode : true,
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
Ext.apply(Application.urls, {
    user : {
        read    : './user/select/format/json',
        create  : './user/create/format/json',
        update  : './user/update/format/json',
        destroy : './user/destroy/format/json'
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
Application.addLinks([
    {
        title : 'Rebecca',
        items : [
            {
                iconCls : 'icon-home',
                html    : 'Willkommen',
                href    : '#Welcome'
            }
        ]
    },
    {
        title : 'Schule',
        items : [
            {
                iconCls : 'icon-table',
                html    : 'Stundenplan',
                href    : '#Stundenplan'
            },
            {
                iconCls : 'icon-table',
                html    : 'Vokabeln',
                href    : '#Vokabeln'
            }
        ]
    },
    {
        title : 'Admin',
        items : [
            {
                iconCls : 'icon-table',
                html    : 'Benutzer Manager',
                href    : '#UserManager'
            }
        ]
    },
    {
        title : 'User',
        iconCls : 'icon-user',
        items : [
            {
                iconCls : 'icon-userkey',
                html    : 'Logout',
                handler : function() {
                    var callback = function() {
                        Ext.Msg.hide();
                        Ext.fly('loading-mask').fadeIn({
                            callback : function() {
                                window.location.reload();
                            }
                        });
                    }
                    Ext.Msg.wait('Ausloggen...', 'Bitte warten...');
                    Ext.Ajax.request({
                        url     : Application.urls.logout,
                        success : callback,
                        failure : callback
                    });
                }
            }
        ]
    }
]);