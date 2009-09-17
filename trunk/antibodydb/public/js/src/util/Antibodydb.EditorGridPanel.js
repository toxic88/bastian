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
