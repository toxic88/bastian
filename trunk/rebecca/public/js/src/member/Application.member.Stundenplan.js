;(function() {
var data = [
    [ '7:50 - 8:35',   'Mathe', 'Spanisch', 'BWL',    'Deutsch',  'Deutsch',  1 ],
    [ '8:35 - 9:20',   'Mathe', 'Spanisch', 'BWL',    'Deutsch',  'Deutsch',  2 ],
    [ '9:25 - 10:10',  'Info',  'Religion', 'Chemie', 'VWL',      'BWL',      3 ],
    [ '10:30 - 11:15', 'Info',  'Religion', 'Chemie', 'VWL',      'BWL',      4 ],
    [ '11:20 - 12:05', 'GGK',   'Englisch', 'Mathe',  'Spanisch', 'Englisch', 5 ],
    [ '12:10 - 12:55', ' - ',   'Englisch', 'Mathe',  'Spanisch', 'Englisch', 6 ],
    [ '13:00 - 13:45', 'Sport', '',         ' - ',    '',         'GGK',      7 ],
    [ '13:45 - 14:30', 'Sport', '',         'WGEO',   '',         '',         8 ],
    [ '14:35 - 15:20', '',      '',         'WGEO',   '',         '',         9 ]
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
})();