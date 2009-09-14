Ext.ns('Ext.ux.grid');
/**
 * Ext.ux.grid.MetaGrid Extension Class
 * Extension of Ext.grid.EditorGridPanel to handle metaData.
 *
 * @author Michael LeComte
 * @version 0.2
 * @date Feb 2, 2009
 *
 * @class Ext.ux.grid.MetaGrid
 * @extends Ext.grid.EditorGridPanel
 */
Ext.ux.grid.MetaGrid = Ext.extend(Ext.grid.EditorGridPanel, {

    initPreview: true,

    loadMask: {
        msg: 'Loading ...'
    },

    /**
     * @cfg {Boolean} true to mask the grid if there's no data to make
     * it even more obvious that the grid is empty.  This will apply a
     * mask to the grid's body with a message in the middle if there
     * are zero rows - quite hard for the user to miss.
     */
    maskEmpty: true,

    /**
     * key number for new records (will be adjusted by new records)
     */
    newKey: -1,

    paging: {
        perPage: 25
    },

    /**
     * @cfg {String} primaryKey The database table primary key.
     */
    primaryKey: 'id',

    stripeRows: true,

    trackMouseOver: true,

    initComponent: function(){

        Ext.applyIf(this, {
            plugins: [],
            pagingPlugins: [],
            // customize view config
            viewConfig: {
                emptyText: 'No Data',
                forceFit: true
            }
        });

        if (this.filters) {
            this.filters = new Ext.grid.GridFilters({
                filters:[]
            });
            this.plugins.push(this.filters);
            this.pagingPlugins.push(this.filters);
        }

        this.store = new Ext.data.Store({
            url: this.url,
            // create reader (reader will be further configured through metaData sent by server)
            reader: new Ext.data.JsonReader(),
            baseParams: this.baseParams,
            listeners: {
                // register to the store's metachange event
                metachange: {
                    fn: this.onMetaChange,
                    scope: this
                },
                loadexception: {
                    fn: function(proxy, options, response, e){
                        if (Ext.isFirebug) {
                            console.warn('store loadexception: ', arguments);
                        }
                        else {
                            Ext.Msg.alert('store loadexception: ', arguments);
                        }
                    }
                },
                scope: this
            }
        });

        // mask the grid if there is no data if so configured
        if (this.maskEmpty) {
            this.store.on(
                'load', function() {
                    var el = this.getGridEl();
                    if (this.store.getTotalCount() == 0 && typeof el == 'object') {
                        el.mask('No Data', 'x-mask');
                    }
                }, this
            );
        }

        /*
         * JSONReader provides metachange functionality which allows you to create
         * dynamic records natively
         * It does not allow you to create the grid's column model dynamically.
         */
        if (this.columns && (this.columns instanceof Array)) {
            this.colModel = new Ext.grid.ColumnModel(this.columns);
            delete this.columns;
        }

        // Create a empty colModel if none given
        if (!this.colModel) {
            this.colModel = new Ext.grid.ColumnModel([]);
        }

        /**
         * defaultSortable : Boolean
         * Default sortable of columns which have no sortable specified
         * (defaults to false)
         * Instead of specifying sorting permission by individual columns
         * can just specify for entire grid
         */
        this.colModel.defaultSortable = true;

        Ext.ux.grid.MetaGrid.superclass.initComponent.call(this);

        var params = { //this is only parameters for the FIRST page load,
            reconfigure: true,
            start: 0, //pass start/limit parameters for paging
            limit: this.paging.perPage
        };

        this.store.load({params: params});

    },

    /**
     * Configure the reader using the server supplied meta data.
     * This grid is observing the store's metachange event (which will be triggered
     * when the metaData property is detected in the returned json data object).
     * This method is specified as the handler for the that metachange event.
     * This method interrogates the metaData property of the json packet (passed
     * to this method as the 2nd argument ).  The local meta property also contains
     * other user-defined properties needed:
     *     fields
     *     defaultSortable
     *     id
     *     root
     *     start
     *     limit
     *     sortinfo.field
     *     sortinfo.direction
     *     successProperty
     *     totalProperty
     * @param {Object} store
     * @param {Object} meta The reader's meta property that exposes the JSON metadata
     */
    onMetaChange: function(store, meta){

        // avoid loading meta on store reload
        delete (store.lastOptions.params.meta);

        var columns = [], editor, plugins, storeCfg, l, convert;

        // set primary Key
        this.primaryKey = meta.id;

        // the metaData.fields property in the returned data packet will be used to:
        // 1. internally create a Record constructor using the array of field definitions:
        // this.recordType = Ext.data.Record.create(o.metaData.fields);
        // both the reader and the store will have a recordType property
        // 2. reconfigure the column model:
        Ext.each(meta.fields, function(col){

            // if plugin specified
            if (col.plugin !== undefined) {
                columns.push(eval(col.plugin));
                return;
            }

            // if header property is not specified do not add to column model
            if (col.header == undefined) {
                return;
            }

            // if not specified assign dataIndex = name
            if (typeof col.dataIndex == "undefined") {
                col.dataIndex = col.name;
            }

            //if using gridFilters extension
            if (this.filters) {
                if (col.filter !== undefined) {
                    if ((col.filter.type !== undefined)) {
                       col.filter.dataIndex = col.dataIndex;
                        this.filters.addFilter(col.filter);
                    }
                }
                delete col.filter;
            }

            // if renderer specified in meta data
            if (typeof col.renderer == "string") {

                // if specified Ext.util or a function will eval to get that function
                if (col.renderer.indexOf("Ext") < 0 && col.renderer.indexOf("function") < 0) {
                    //                    col.renderer = this.setRenderer(col.renderer);
                    col.renderer = this[col.renderer].createDelegate(this);
                }
                else {
                    col.renderer = eval(col.renderer);
                }
            }

            /*
             // if want to modify default column id
             if(typeof col.id == "undefined"){
             col.id = 'c' + i;
             }
             */
            // if listeners specified in meta data
            l = col.listeners;
            if (typeof l == "object") {
                for (var e in l) {
                    if (typeof e == "string") {
                        for (var c in l[e]) {
                            if (typeof c == "string") {
                                l[e][c] = eval(l[e][c]);
                            }
                        }
                    }
                }
            }

            // if convert specified assume it's a function and eval it
            if (col.convert) {
                col.convert = eval(col.convert);
            }

            editor = col.editor;

            if (editor) {

                switch (editor.xtype) {
                    case 'checkbox':
                        delete (col.editor);
                        delete (col.renderer);
                        col = new Ext.grid.CheckColumn(col);
                        col.editor = Ext.ComponentMgr.create(editor, 'textfield');
                        col.init(this);
                        break;
                    case 'combo':
                        if (col.editor.store) {
                            storeCfg = col.editor.store;
                            col.editor.store = new Ext.data[storeCfg.storeType](storeCfg.config);
                        }
                        col.editor = Ext.ComponentMgr.create(editor, 'textfield');
                        break;
                    case 'datefield':
                        col.editor = Ext.ComponentMgr.create(editor, 'textfield');
                        break;
                    default:
                        col.editor = Ext.ComponentMgr.create(editor, 'textfield');
                        break;
                }

                plugins = editor.plugins;
                delete (editor.plugins);

                //configure any listeners specified for this column's editor
                l = editor.listeners;
                if (typeof l == "object") {
                    for (var e in l) {
                        if (typeof e == "string") {
                            for (var c in l[e]) {
                                if (typeof c == "string") {
                                    l[e][c] = eval(l[e][c]);
                                }
                            }
                        }
                    }
                }
            }

            if (plugins instanceof Array) {
                editor.plugins = [];
                Ext.each(plugins, function(plugin){
                    plugin.name = plugin.name || col.dataIndex;
                    editor.plugins.push(Ext.ComponentMgr.create(plugin));
                });
            }

            // add column to colModel config array
            columns.push(col);

        }, this); // end of columns loop
        var cm = new Ext.grid.ColumnModel(columns);

        if (meta.defaultSortable != undefined) {
            cm.defaultSortable = meta.defaultSortable;
        }

        // can change the store if we need to also, perhaps if we detect a groupField
        // config for example
        // meta.groupField or meta.storeCfg.groupField;
        var store = this.store;

        // Reconfigure the grid to use a different Store and Column Model. The View
        // will be bound to the new objects and refreshed.
        this.reconfigure(store, cm);

        // to add a record, just get a reference to the recordType:
        // this.store.recordType
        // and then use it to create a new record:
        // var r = new s.recordType({
        //     value:4,
        //     displayField:"Last Week",
        //     total: 23
        // });
        // and then insert it into the store (updates the grid visually also):
        // this.store.insert(0, r);

        //update the store for the pagingtoolbar also
        var oldStore = this.pagingToolbar.store;
        this.pagingToolbar.unbind(oldStore);
        this.pagingToolbar.bind(store);

        if (this.stateful) {
            this.initState();
        }
    }
});
Ext.reg('metagrid', Ext.ux.grid.MetaGrid);