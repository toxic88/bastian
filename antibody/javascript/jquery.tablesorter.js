/*
 * 
 * TableSorter 2.0 - Client-side table sorting with ease!
 * Version 2.0.3
 * @requires jQuery v1.2.3
 * 
 * Copyright (c) 2007 Christian Bach
 * Examples and docs at: http://tablesorter.com
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 * 
 */
/**
 *
 * @description Create a sortable table with multi-column sorting capabilitys
 * 
 * @example $('table').tablesorter();
 * @desc Create a simple tablesorter interface.
 *
 * @example $('table').tablesorter({ sortList:[[0,0],[1,0]] });
 * @desc Create a tablesorter interface and sort on the first and secound column in ascending order.
 * 
 * @example $('table').tablesorter({ headers: { 0: { sorter: false}, 1: {sorter: false} } });
 * @desc Create a tablesorter interface and disableing the first and secound column headers.
 * 
 * @example $('table').tablesorter({ 0: {sorter:"integer"}, 1: {sorter:"currency"} });
 * @desc Create a tablesorter interface and set a column parser for the first and secound column.
 * 
 * 
 * @param Object settings An object literal containing key/value pairs to provide optional settings.
 * 
 * @option String cssHeader (optional)             A string of the class name to be appended to sortable tr elements in the thead of the table.
 *                                                 Default value: "header"
 * 
 * @option String cssAsc (optional)             A string of the class name to be appended to sortable tr elements in the thead on a ascending sort.
 *                                                 Default value: "headerSortUp"
 * 
 * @option String cssDesc (optional)             A string of the class name to be appended to sortable tr elements in the thead on a descending sort.
 *                                                 Default value: "headerSortDown"
 * 
 * @option String sortInitialOrder (optional)     A string of the inital sorting order can be asc or desc.
 *                                                 Default value: "asc"
 * 
 * @option String sortMultisortKey (optional)     A string of the multi-column sort key.
 *                                                 Default value: "shiftKey"
 * 
 * @option String textExtraction (optional)     A string of the text-extraction method to use.
 *                                                 For complex html structures inside td cell set this option to "complex",
 *                                                 on large tables the complex option can be slow.
 *                                                 Default value: "simple"
 * 
 * @option Object headers (optional)             An array containing the forces sorting rules.
 *                                                 This option let's you specify a default sorting rule.
 *                                                 Default value: null
 * 
 * @option Array sortList (optional)             An array containing the forces sorting rules.
 *                                                 This option let's you specify a default sorting rule.
 *                                                 Default value: null
 * 
 * @option Array sortForce (optional)             An array containing forced sorting rules.
 *                                                 This option let's you specify a default sorting rule, which is prepended to user-selected rules.
 *                                                 Default value: null
 *  
  * @option Array sortAppend (optional)             An array containing forced sorting rules.
 *                                                 This option let's you specify a default sorting rule, which is appended to user-selected rules.
 *                                                 Default value: null
 * 
 * @option Boolean widthFixed (optional)         Boolean flag indicating if tablesorter should apply fixed widths to the table columns.
 *                                                 This is usefull when using the pager companion plugin.
 *                                                 This options requires the dimension jquery plugin.
 *                                                 Default value: false
 *
 * @option Boolean cancelSelection (optional)     Boolean flag indicating if tablesorter should cancel selection of the table headers text.
 *                                                 Default value: true
 *
 * @option Boolean debug (optional)             Boolean flag indicating if tablesorter should display debuging information usefull for development.
 *
 * @type jQuery
 *
 * @name tablesorter
 * 
 * @cat Plugins/Tablesorter
 * 
 * @author Christian Bach/christian.bach@polyester.se
 */

(function($) {
    $.extend({
        tablesorter: new function() {

            var parsers = [], widgets = [];

            this.defaults = {
                cssHeader: "header",
                cssAsc: "headerSortUp",
                cssDesc: "headerSortDown",
                sortInitialOrder: "asc",
                sortMultiSortKey: "shiftKey",
                sortForce: null,
                sortAppend: null,
                textExtraction: "simple",
                parsers: {},
                widgets: ["zebra"],
                widgetZebra: {css: ["even","odd"]},
                headers: {},
                widthFixed: true,
                cancelSelection: true,
                sortList: [],
                headerList: [],
                dateFormat: "us",
                decimal: '.',
                debug: false
            };

            /* debuging utils */
            function benchmark(s,d) {
                log(s + " " + (new Date().getTime() - d.getTime()) + "ms");
            }

            this.benchmark = benchmark;

            function log(s) {
                typeof console != "undefined" && typeof console.debug != "undefined" ? console.log(s) : alert(s);
            }

            /* parsers utils */
            function buildParserCache(table,$headers) {

                if(table.config.debug) { var parsersDebug = ""; }

                var rows = table.tBodies[0].rows;

                if(table.tBodies[0].rows[0]) {

                    var list = [], cells = rows[0].cells, l = cells.length;

                    for (var i=0;i < l; i++) {
                        var p = false;

                        if($.metadata && ($($headers[i]).metadata() && $($headers[i]).metadata().sorter)  ) {

                            p = getParserById($($headers[i]).metadata().sorter);

                        } else if((table.config.headers[i] && table.config.headers[i].sorter)) {

                            p = getParserById(table.config.headers[i].sorter);
                        }
                        if(!p) {
                            p = detectParserForColumn(table,cells[i]);
                        }

                        if(table.config.debug) { parsersDebug += "column:" + i + " parser:" +p.id + "\n"; }

                        list.push(p);
                    }
                }

                if(table.config.debug) { log(parsersDebug); }

                return list;
            };

            function detectParserForColumn(table,node) {
                var l = parsers.length;
                for(var i=1; i < l; i++) {
                    if(parsers[i].is($.trim(getElementText(table.config,node)),table,node)) {
                        return parsers[i];
                    }
                }
                // 0 is always the generic parser (text)
                return parsers[0];
            }

            function getParserById(name) {
                var l = parsers.length;
                for(var i=0; i < l; i++) {
                    if(parsers[i].id.toLowerCase() == name.toLowerCase()) {
                        return parsers[i];
                    }
                }
                return false;
            }

            /* utils */
            function buildCache(table) {

                if(table.config.debug) { var cacheTime = new Date(); }


                var totalRows = (table.tBodies[0] && table.tBodies[0].rows.length) || 0,
                    totalCells = (table.tBodies[0].rows[0] && table.tBodies[0].rows[0].cells.length) || 0,
                    parsers = table.config.parsers,
                    cache = {row: [], normalized: []};

                    for (var i=0;i < totalRows; ++i) {

                        /** Add the table data to main data array */
                        var c = table.tBodies[0].rows[i], cols = [];

                        cache.row.push($(c));

                        for(var j=0; j < totalCells; ++j) {
                            cols.push(parsers[j].format(getElementText(table.config,c.cells[j]),table,c.cells[j]));
                        }

                        cols.push(i); // add position for rowCache
                        cache.normalized.push(cols);
                        cols = null;
                    };

                if(table.config.debug) { benchmark("Building cache for " + totalRows + " rows:", cacheTime); }

                return cache;
            };

            function getElementText(config,node) {

                var t = "";

                if(!node) return t;

                if(config.textExtraction == "simple") {
                    if(node.childNodes[0] && node.childNodes[0].hasChildNodes()) {
                        t = node.childNodes[0].innerHTML;
                    } else {
                        t = node.innerHTML;
                    }
                } else {
                    if(typeof(config.textExtraction) == "function") {
                        t = config.textExtraction(node);
                    } else {
                        t = $(node).text();
                    }
                }
                return t;
            }

            function appendToTable(table,cache) {
                // New
                if(cache.row.length==0) return;
                // new
                if(table.config.debug) {var appendTime = new Date()}

                var c = cache,
                    r = c.row,
                    n= c.normalized,
                    totalRows = n.length,
                    checkCell = (n[0].length-1),
                    tableBody = $(table.tBodies[0]),
                    rows = [];

                for (var i=0;i < totalRows; i++) {
                    rows.push(r[n[i][checkCell]]);
                    if(!table.config.appender) {

                        var o = r[n[i][checkCell]];
                        var l = o.length;
                        for(var j=0; j < l; j++) {

                            tableBody[0].appendChild(o[j]);

                        }

                        //tableBody.append(r[n[i][checkCell]]);
                    }
                }

                if(table.config.appender) {

                    table.config.appender(table,rows);
                }

                rows = null;

                if(table.config.debug) { benchmark("Rebuilt table:", appendTime); }

                //apply table widgets
                applyWidget(table);

                // trigger sortend
                setTimeout(function() {
                    $(table).trigger("sortEnd");
                },0);

            };

            function buildHeaders(table) {

                if(table.config.debug) { var time = new Date(); }

                // New
                if(!table.tHead) {
                    var thead = $("<thead/>");

                    $(thead).append(table.tBodies[0].rows[0]);

                    $.each($(thead).find("td, th"), function() {
                        $(thead).find("tr").append("<th>" + $(this).html() + "</th>");
                        $(this).remove();
                    });

                    $(table).prepend(thead);
                }
                // new
                var meta = ($.metadata) ? true : false, tableHeadersRows = [];

                for(var i = 0; i < table.tHead.rows.length; i++) { tableHeadersRows[i]=0; };

                $tableHeaders = $("thead th",table).addClass("cursor");

                $tableHeaders.each(function(index) {

                    this.count = 0;
                    this.column = index;
                    this.order = formatSortingOrder(table.config.sortInitialOrder);

                    if(checkHeaderMetadata(this) || checkHeaderOptions(table,index)) this.sortDisabled = true;

                    if(!this.sortDisabled) {
                        $(this).addClass(table.config.cssHeader);
                    }

                    // add cell to headerList
                    table.config.headerList[index]= this;
                });

                if(table.config.debug) { benchmark("Built headers:", time); log($tableHeaders); }

                return $tableHeaders;

            };

               function checkCellColSpan(table, rows, row) {
                var arr = [], r = table.tHead.rows, c = r[row].cells;

                for(var i=0; i < c.length; i++) {
                    var cell = c[i];

                    if ( cell.colSpan > 1) {
                        arr = arr.concat(checkCellColSpan(table, headerArr,row++));
                    } else  {
                        if(table.tHead.length == 1 || (cell.rowSpan > 1 || !r[row+1])) {
                            arr.push(cell);
                        }
                        //headerArr[row] = (i+row);
                    }
                }
                return arr;
            };

            function checkHeaderMetadata(cell) {
                if(($.metadata) && ($(cell).metadata().sorter === false)) { return true; };
                return false;
            }

            function checkHeaderOptions(table,i) {
                if((table.config.headers[i]) && (table.config.headers[i].sorter === false)) { return true; };
                return false;
            }

            function applyWidget(table) {
                var c = table.config.widgets;
                var l = c.length;
                for(var i=0; i < l; i++) {
                    getWidgetById(c[i]).format(table);
                }
            }

            function getWidgetById(name) {
                var l = widgets.length;
                for(var i=0; i < l; i++) {
                    if(widgets[i].id.toLowerCase() == name.toLowerCase() ) {
                        return widgets[i];
                    }
                }
            };

            function formatSortingOrder(v) {

                if(typeof(v) != "Number") {
                    i = (v.toLowerCase() == "desc") ? 1 : 0;
                } else {
                    i = (v == (0 || 1)) ? v : 0;
                }
                return i;
            }

            function isValueInArray(v, a) {
                var l = a.length;
                for(var i=0; i < l; i++) {
                    if(a[i][0] == v) {
                        return true;
                    }
                }
                return false;
            }

            function setHeadersCss(table,$headers, list, css) {
                // remove all header information
                $headers.removeClass(css[0]).removeClass(css[1]);

                var h = [];
                $headers.each(function(offset) {
                        if(!this.sortDisabled) {
                            h[this.column] = $(this);
                        }
                });

                var l = list.length;
                for(var i=0; i < l; i++) {
                    h[list[i][0]].addClass(css[list[i][1]]);
                }
            }

            function fixColumnWidth(table,$headers) {
                var c = table.config;
                if(c.widthFixed) {
                    var colgroup = $('<colgroup>');
                    $("tr:first td",table.tBodies[0]).each(function() {
                        colgroup.append($('<col>').css('width',$(this).width()));
                    });
                    $(table).prepend(colgroup);
                };
            }

            function updateHeaderSortCount(table,sortList) {
                var c = table.config, l = sortList.length;
                for(var i=0; i < l; i++) {
                    var s = sortList[i], o = c.headerList[s[0]];
                    o.count = s[1];
                    o.count++;
                }
            }

            /* sorting methods */
            function multisort(table,sortList,cache) {

                if(table.config.debug) { var sortTime = new Date(); }

                var dynamicExp = "var sortWrapper = function(a,b) {", l = sortList.length;

                for(var i=0; i < l; i++) {

                    var c = sortList[i][0];
                    var order = sortList[i][1];
                    var s = (getCachedSortType(table.config.parsers,c) == "text") ? ((order == 0) ? "sortText" : "sortTextDesc") : ((order == 0) ? "sortNumeric" : "sortNumericDesc");

                    var e = "e" + i;

                    dynamicExp += "var " + e + " = " + s + "(a[" + c + "],b[" + c + "]); ";
                    dynamicExp += "if(" + e + ") { return " + e + "; } ";
                    dynamicExp += "else { ";
                }

                // if value is the same keep orignal order
                var orgOrderCol = cache.normalized[0].length - 1;
                dynamicExp += "return a[" + orgOrderCol + "]-b[" + orgOrderCol + "];";

                for(var i=0; i < l; i++) {
                    dynamicExp += "}; ";
                }

                dynamicExp += "return 0; ";
                dynamicExp += "}; ";

                eval(dynamicExp);

                cache.normalized.sort(sortWrapper);

                if(table.config.debug) { benchmark("Sorting on " + sortList.toString() + " and dir " + order + " time:", sortTime); }

                return cache;
            };

            function sortText(a,b) {
                return ((a < b) ? -1 : ((a > b) ? 1 : 0));
            };

            function sortTextDesc(a,b) {
                return ((b < a) ? -1 : ((b > a) ? 1 : 0));
            };

             function sortNumeric(a,b) {
                return a-b;
            };

            function sortNumericDesc(a,b) {
                return b-a;
            };

            function getCachedSortType(parsers,i) {
                return parsers[i].type;
            };

            /* public methods */
            this.construct = function(settings) {

                return this.each(function() {
                    // Changed
                    if(!this.tBodies) return;
                    // changed
                    var $this, $document,$headers, cache, config, shiftDown = 0, sortOrder;

                    this.config = {};

                    config = $.extend(this.config, $.tablesorter.defaults, settings);

                    // store common expression for speed
                    $this = $(this);

                    // build headers
                    $headers = buildHeaders(this);

                    // try to auto detect column type, and store in tables config
                    this.config.parsers = buildParserCache(this,$headers);


                    // build the cache for the tbody cells
                    this.cache = cache = buildCache(this);

                    // get the css class names, could be done else where.
                    var sortCSS = [config.cssDesc,config.cssAsc];

                    // fixate columns if the users supplies the fixedWidth option
                    fixColumnWidth(this);

                    // apply event handling to headers
                    // this is to big, perhaps break it out?
                    $headers.click(function(e) {

                        $this.trigger("sortStart");

                        var totalRows = ($this[0].tBodies[0] && $this[0].tBodies[0].rows.length) || 0;

                        if(!this.sortDisabled && totalRows > 0) {


                            // store exp, for speed
                            var $cell = $(this);

                            // get current column index
                            var i = this.column;

                            // get current column sort order
                            this.order = this.count++ % 2;

                            // user only whants to sort on one column
                            if(!e[config.sortMultiSortKey]) {

                                // flush the sort list
                                config.sortList = [];

                                if(config.sortForce != null) {
                                    var a = config.sortForce;
                                    for(var j=0; j < a.length; j++) {
                                        if(a[j][0] != i) {
                                            config.sortList.push(a[j]);
                                        }
                                    }
                                }

                                // add column to sort list
                                config.sortList.push([i,this.order]);

                            // multi column sorting
                            } else {
                                // the user has clicked on an all ready sortet column.
                                if(isValueInArray(i,config.sortList)) {

                                    // revers the sorting direction for all tables.
                                    for(var j=0; j < config.sortList.length; j++) {
                                        var s = config.sortList[j], o = config.headerList[s[0]];
                                        if(s[0] == i) {
                                            o.count = s[1];
                                            o.count++;
                                            s[1] = o.count % 2;
                                        }
                                    }
                                } else {
                                    // add column to sort list array
                                    config.sortList.push([i,this.order]);
                                }
                            };
                            setTimeout(function() {
                                //set css for headers
                                setHeadersCss($this[0],$headers,config.sortList,sortCSS);
                                appendToTable($this[0],multisort($this[0],config.sortList,cache));
                            },1);
                            // stop normal event by returning false
                            return false;
                        }
                    // cancel selection
                    }).mousedown(function() {
                        if(config.cancelSelection) {
                            this.onselectstart = function() {return false};
                            return false;
                        }
                    });

                    // apply easy methods that trigger binded events
                    $this.bind("update",function(e,c) {

                        if(c) { cache=c; return; }

                        // rebuild parsers.
                        this.config.parsers = buildParserCache(this,$headers);

                        // rebuild the cache map
                        cache = buildCache(this);

                    }).bind("sorton",function(e,list) {

                        $(this).trigger("sortStart");

                        config.sortList = list;

                        // update and store the sortlist
                        var sortList = config.sortList;

                        // update header count index
                        updateHeaderSortCount(this,sortList);

                        //set css for headers
                        setHeadersCss(this,$headers,sortList,sortCSS);


                        // sort the table and append it to the dom
                        appendToTable(this,multisort(this,sortList,cache));

                    }).bind("appendCache",function() {

                        appendToTable(this,cache);

                    }).bind("applyWidgetId",function(e,id) {

                        getWidgetById(id).format(this);

                    }).bind("applyWidgets",function() {
                        // apply widgets
                        applyWidget(this);
                    });

                    if($.metadata && ($(this).metadata() && $(this).metadata().sortlist)) {
                        config.sortList = $(this).metadata().sortlist;
                    }
                    // if user has supplied a sort list to constructor.
                    if(config.sortList.length > 0) {
                        $this.trigger("sorton",[config.sortList]);
                    }

                    // apply widgets
                    applyWidget(this);
                });
            };

            this.addParser = function(parser) {
                var l = parsers.length, a = true;
                for(var i=0; i < l; i++) {
                    if(parsers[i].id.toLowerCase() == parser.id.toLowerCase()) {
                        a = false;
                    }
                }
                if(a) { parsers.push(parser); };
            };

            this.addWidget = function(widget) {
                widgets.push(widget);
            };

            this.formatFloat = function(s) {
                var i = parseFloat(s);
                return (isNaN(i)) ? 0 : i;
            };
            this.formatInt = function(s) {
                var i = parseInt(s);
                return (isNaN(i)) ? 0 : i;
            };

            this.isDigit = function(s,config) {
                var DECIMAL = '\\' + config.decimal;
                var exp = '/(^[+]?0(' + DECIMAL +'0+)?$)|(^([-+]?[1-9][0-9]*)$)|(^([-+]?((0?|[1-9][0-9]*)' + DECIMAL +'(0*[1-9][0-9]*)))$)|(^[-+]?[1-9]+[0-9]*' + DECIMAL +'0+$)/';
                return RegExp(exp).test($.trim(s));
            };

            this.clearTableBody = function(table) {
                if($.browser.msie) {
                    function empty() {
                        while ( this.firstChild ) this.removeChild( this.firstChild );
                    }
                    empty.apply(table.tBodies[0]);
                } else {
                    table.tBodies[0].innerHTML = "";
                }
            };
        }
    });

    // extend plugin scope
    $.fn.extend({
        tablesorter: $.tablesorter.construct
    });

    var ts = $.tablesorter;

    // add default parsers
    ts.addParser({
        id: "text",
        is: function(s) {
            return true;
        },
        format: function(s) {
            return $.trim(s.toLowerCase());
        },
        type: "text"
    });
/*
    ts.addParser({
        id: "digit",
        is: function(s,table) {
            var c = table.config;
            return $.tablesorter.isDigit(s,c);
        },
        format: function(s) {
            return $.tablesorter.formatFloat(s);
        },
        type: "numeric"
    });

    ts.addParser({
        id: "currency",
        is: function(s) {
            return /^[Â£$â�¬?.]/.test(s);
        },
        format: function(s) {
            return $.tablesorter.formatFloat(s.replace(new RegExp(/[^0-9.]/g),""));
        },
        type: "numeric"
    });

    ts.addParser({
        id: "ipAddress",
        is: function(s) {
            return /^\d{2,3}[\.]\d{2,3}[\.]\d{2,3}[\.]\d{2,3}$/.test(s);
        },
        format: function(s) {
            var a = s.split("."), r = "", l = a.length;
            for(var i = 0; i < l; i++) {
                var item = a[i];
                   if(item.length == 2) {
                    r += "0" + item;
                   } else {
                    r += item;
                   }
            }
            return $.tablesorter.formatFloat(r);
        },
        type: "numeric"
    });

    ts.addParser({
        id: "url",
        is: function(s) {
            return /^(https?|ftp|file):\/\/$/.test(s);
        },
        format: function(s) {
            return jQuery.trim(s.replace(new RegExp(/(https?|ftp|file):\/\//),''));
        },
        type: "text"
    });

    ts.addParser({
        id: "isoDate",
        is: function(s) {
            return /^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test(s);
        },
        format: function(s) {
            return $.tablesorter.formatFloat((s != "") ? new Date(s.replace(new RegExp(/-/g),"/")).getTime() : "0");
        },
        type: "numeric"
    });

    ts.addParser({
        id: "percent",
        is: function(s) {
            return /\%$/.test($.trim(s));
        },
        format: function(s) {
            return $.tablesorter.formatFloat(s.replace(new RegExp(/%/g),""));
        },
        type: "numeric"
    });

    ts.addParser({
        id: "usLongDate",
        is: function(s) {
            return s.match(new RegExp(/^[A-Za-z]{3,10}\.? [0-9]{1,2}, ([0-9]{4}|'?[0-9]{2}) (([0-2]?[0-9]:[0-5][0-9])|([0-1]?[0-9]:[0-5][0-9]\s(AM|PM)))$/));
        },
        format: function(s) {
            return $.tablesorter.formatFloat(new Date(s).getTime());
        },
        type: "numeric"
    });

    ts.addParser({
        id: "shortDate",
        is: function(s) {
            return /\d{1,2}[\/\-]\d{1,2}[\/\-]\d{2,4}/.test(s);
        },
        format: function(s,table) {
            var c = table.config;
            s = s.replace(/\-/g,"/");
            if(c.dateFormat == "us") {
                // reformat the string in ISO format
                s = s.replace(/(\d{1,2})[\/\-](\d{1,2})[\/\-](\d{4})/, "$3/$1/$2");
            } else if(c.dateFormat == "uk") {
                //reformat the string in ISO format
                s = s.replace(/(\d{1,2})[\/\-](\d{1,2})[\/\-](\d{4})/, "$3/$2/$1");
            } else if(c.dateFormat == "dd/mm/yy" || c.dateFormat == "dd-mm-yy") {
                s = s.replace(/(\d{1,2})[\/\-](\d{1,2})[\/\-](\d{2})/, "$1/$2/$3");
            }
            return $.tablesorter.formatFloat(new Date(s).getTime());
        },
        type: "numeric"
    });

    ts.addParser({
        id: "time",
        is: function(s) {
            return /^(([0-2]?[0-9]:[0-5][0-9])|([0-1]?[0-9]:[0-5][0-9]\s(am|pm)))$/.test(s);
        },
        format: function(s) {
            return $.tablesorter.formatFloat(new Date("2000/01/01 " + s).getTime());
        },
      type: "numeric"
    });


    ts.addParser({
        id: "metadata",
        is: function(s) {
            return false;
        },
        format: function(s,table,cell) {
            var c = table.config, p = (!c.parserMetadataName) ? 'sortValue' : c.parserMetadataName;
            return $(cell).metadata()[p];
        },
      type: "numeric"
    });
*/
    // add default widgets
    ts.addWidget({
        id: "zebra",
        format: function(table) {
            if(table.config.debug) { var time = new Date(); }

            for(var i=0, l=table.tBodies[0].rows.length;i<l;i++) {
                var classnames = table.tBodies[0].rows[i].className.split(" ");
                var odd = $.inArray(table.config.widgetZebra.css[0], classnames);
                var even = $.inArray(table.config.widgetZebra.css[1], classnames);
                if(odd!=-1)
                    classnames.splice(odd, 1);
                if(even!=-1)
                    classnames.splice(even, 1);

                if(i % 2 == 0)
                    classnames.push(table.config.widgetZebra.css[0]);
                else
                    classnames.push(table.config.widgetZebra.css[1]);

                table.tBodies[0].rows[i].className = classnames.join(" ");
            }

/*            $("tr:visible",table.tBodies[0])
            .filter(':even')
            .removeClass(table.config.widgetZebra.css[1]).addClass(table.config.widgetZebra.css[0])
            .end().filter(':odd')
            .removeClass(table.config.widgetZebra.css[0]).addClass(table.config.widgetZebra.css[1]);
*/            if(table.config.debug) { $.tablesorter.benchmark("Applying Zebra widget", time); }
        }
    });
})(jQuery);

(function($) {
    $.extend({
        tablesorterPager: new function() {

            function updatePageDisplay(c) {
                var container = $(c.cssPageDisplay,c.container);
                if(container.is(":input"))
                    var s = container.val((c.page+1) + c.seperator + c.totalPages);
                else
                    var s = container.html((c.page+1) + c.seperator + c.totalPages);
            }

            function setPageSize(table,size) {
                var c = table.config;
                c.size = size;
                c.totalPages = Math.ceil(c.totalRows / c.size);
                c.pagerPositionSet = false;
                moveToPage(table);
                fixPosition(table);
            }

            function fixPosition(table) {
                var c = table.config;
                if(!c.pagerPositionSet && c.positionFixed) {
                    var c = table.config, o = $(table);
                    if(o.offset) {
                        c.container.css({
                            top: o.offset().top + o.height() + 'px',
                            position: 'absolute'
                        });
                    }
                    c.pagerPositionSet = true;
                }
            }

            function moveToFirstPage(table) {
                var c = table.config;
                c.page = 0;
                moveToPage(table);
            }

            function moveToLastPage(table) {
                var c = table.config;
                c.page = (c.totalPages-1);
                moveToPage(table);
            }

            function moveToNextPage(table) {
                var c = table.config;
                c.page++;
                if(c.page >= (c.totalPages-1)) {
                    c.page = (c.totalPages-1);
                }
                moveToPage(table);
            }

            function moveToPrevPage(table) {
                var c = table.config;
                c.page--;
                if(c.page <= 0) {
                    c.page = 0;
                }
                moveToPage(table);
            }


            function moveToPage(table) {
                var c = table.config;
                if(c.page < 0 || c.page > (c.totalPages-1)) {
                    c.page = 0;
                }

                renderTable(table,c.rowsCopy);
            }

            function renderTable(table,rows) {

                if(table.config.debug) { var renderTable = new Date(); }

                var c = table.config;
                var l = rows.length;
                var s = (c.page * c.size);
                var e = (s + c.size);
                // Changed
                if(e > l ) {
                    e = l;
                }
                // changed
                var tableBody = $(table.tBodies[0]);

                // clear the table body

                $.tablesorter.clearTableBody(table);

                for(var i = s; i < e; i++) {

                    //tableBody.append(rows[i]);

                    var o = rows[i];
                    var l = o.length;
                    for(var j=0; j < l; j++) {

                        tableBody[0].appendChild(o[j]);

                    }
                }
                fixPosition(table,tableBody);

                $(table).trigger("applyWidgets");

                if( c.page >= c.totalPages ) {
                    moveToLastPage(table);
                }

                updatePageDisplay(c);

                if(table.config.debug) { $.tablesorter.benchmark("Rebuilt table:", renderTable); }
            }

            this.appender = function(table,rows) {

                var c = table.config;

                c.rowsCopy = rows;
                c.totalRows = rows.length;
                c.totalPages = Math.ceil(c.totalRows / c.size);

                renderTable(table,rows);
            };

            this.defaults = {
                size: 10,
                offset: 0,
                page: 0,
                totalRows: 0,
                totalPages: 0,
                container: null,
                cssNext: '.next',
                cssPrev: '.prev',
                cssFirst: '.first',
                cssLast: '.last',
                cssPageDisplay: '.pagedisplay',
                cssPageSize: '.pagesize',
                seperator: "/",
                positionFixed: false,
                appender: this.appender
            };

            this.construct = function(settings) {

                return this.each(function() {

                    config = $.extend(this.config, $.tablesorterPager.defaults, settings);

                    var table = this, pager = config.container;

                    $(this).trigger("appendCache");

                    config.size = parseInt($(".pagesize",pager).val());

                    $(config.cssFirst,pager).click(function() {
                        moveToFirstPage(table);
                        return false;
                    });
                    $(config.cssNext,pager).click(function() {
                        moveToNextPage(table);
                        return false;
                    });
                    $(config.cssPrev,pager).click(function() {
                        moveToPrevPage(table);
                        return false;
                    });
                    $(config.cssLast,pager).click(function() {
                        moveToLastPage(table);
                        return false;
                    });
                    $(config.cssPageSize,pager).change(function() {
                        setPageSize(table,parseInt($(this).val()));
                        return false;
                    });
                    $(config.cssPageDisplay,pager)/*.change(function() {
                        config.page = parseInt($(this).val().split(config.seperator)[0])-1;
                        moveToPage(table);
                        return false;
                    })*/.click(function() {
                        if(config.totalPages==1) return;
                        var input = $(this).clone(true);
                        var dd = $("<select size='1' class='" + config.cssPageDisplay + "'></select>");
                        for(var i=0;i<=config.totalPages-1;)
                            $(dd).append("<option value='" + i + "'" + ((i==config.page) ? " selected='selected'" : "") + ">" + ++i + "</option>");
                        $(dd).change(function() {
                            $(this).replaceWith(input);
                            config.page = parseInt($(this).val());
                            moveToPage(table);
                            return false;
                        }).blur(function() {
                            $(this).replaceWith(input);
                            return false;
                        });
                        $(this).replaceWith(dd);
                        return false;
                    });
                });
            };

        }
    });
    // extend plugin scope
    $.fn.extend({
        tablesorterPager: $.tablesorterPager.construct
    });

})(jQuery);

(function($) {
    $.extend({
        tablesorterSearch: new function() {

            this.defaults = {
                searchInput:null,
                searchBtn:null,
                colums:null,
                noMatch:null,
                Match:null,
                searchDelay:0
            };

            function Match(searchString,cols,number,table) {
                if($.isFunction(table.config.Match))
                    table.config.Match(searchString,cols,number,table);
            }

            function noMatch(searchString,cols,table) {
                if($.isFunction(table.config.noMatch))
                    table.config.noMatch(searchString,cols,table);
            }

            function renderTable(table,rows) {
                var l = rows.length;

                var tableBody = $(table.tBodies[0]);

                $.tablesorter.clearTableBody(table);

                for(var i=0;i<l;i++) {
                    var o = rows[i];
                    for(var j=0; j < o.length; j++) {

                        tableBody[0].appendChild(o[j]);

                    }
                }
                $(table).trigger("applyWidgets");
            }

            function search(table,cache,searchString,cols) {
                if(searchString!="") {
                    if(table.config.debug) { var searchTime = new Date(); }

                    var words = searchString.toLowerCase().split(" ");
                    var n = cache.normalized;
                    var c = { normalized:[], row:[] }
                    for(var rows=0;rows<n.length;rows++) {
                        var found = false;
                        for(var colums=0;colums<n[rows].length-1;colums++) {
                            if(typeof cols == "object" && cols.length && !isNaN(cols[0])) {
                                if($.inArray(colums, cols)==-1)
                                    continue;
                            }
                            for(var word=0;word<words.length;word++) {
                                if(n[rows][colums].toString().indexOf($.trim(words[word]))>-1)
                                    found = true;
                            }
                        }
                        if(found) {
                            n[rows][ n[rows].length-1 ] = c.normalized.length;
                            c.normalized.push(n[rows]);
                            c.row.push(cache.row[rows]);
                            continue;
                        }
                    }
                    cache = c;
                    if(table.config.debug) { $.tablesorter.benchmark("Searching for '" + searchString + "' in colum " + cols.join(", ") + " results " + cache.row.length + ":", searchTime); }
                }
                if(cache.row.length==0)
                    return noMatch(searchString,cols,table);

                if($.tablesorterPager)
                    $.tablesorterPager.appender(table,cache.row);
                else
                    renderTable(table,cache.row);

                $(table).trigger("update", [cache]);
                Match(searchString,cols,cache.row.length,table);
            }

            this.construct = function(settings) {

                return this.each(function() {
                    config = $.extend(this.config, $.tablesorterSearch.defaults, settings);
                    var table = this;
                    var cache = this.cache;
                    var lastString = "";
                    var timeout;
                    $(config.colums).append("<option value=''> - </option>");
                    $.each(config.headerList, function(i, v) {
                        if($.trim($(v).text()) != "")
                            $(config.colums).append("<option value=" + i + ">" + $(v).text() + "</option>");
                    });
                    $(config.searchBtn).click(function() {
                        var string = $.trim($(config.searchInput).val().toString());
                        if(lastString==string.toLowerCase()) return; else lastString = string;
                        var cols = [NaN];
                        if(!isNaN(config.colums) && typeof config.colums == "number")
                            cols = [config.colums];
                        else if(typeof config.colums == "object" && config.colums.length && !config.colums.jquery)
                            cols = config.colums;
                        else if((typeof config.colums == "object" && config.colums.jquery) || typeof config.colums == "string") {
                            cols = [parseInt($(config.colums).val())];
                            $(config.colums).unbind().change(function() {
                                cols = [parseInt($(config.colums).val())];
                                search(table,cache,string,cols);
                            });
                        }
                        clearTimeout(timeout);
                        timeout = setTimeout(function() {
                            search(table,cache,string,cols);
                        }, config.searchDelay);
                    });

                });
            };

        }
    });
    // extend plugin scope
    $.fn.extend({
        tablesorterSearch: $.tablesorterSearch.construct
    });

})(jQuery);
