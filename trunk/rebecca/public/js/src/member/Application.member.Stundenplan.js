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
})();