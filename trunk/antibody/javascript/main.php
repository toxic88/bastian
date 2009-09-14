<?php
include "../include/settings.php";
// .php daher weil ich dem Javascript noch ein paar parameter und
// andere sachen übergeben will
?>

$(document).ready(function() {
	// alle Links zu AJAX-Links machen
	$("#dkfz_bottom_navi a").click(function() {
		// Einfach die Seite laden auf die der Link verweist
		load($(this).attr("href"));
		// GANZ wichtig!!! sonst wird die Seite neu geladen
		return false;
	});
	// Falls das Cookie für das automatische einloggen noch existiert
	// dann gleich mal einloggen lassen
	if($.cookie("<?=$antibody->cookieName?>"))
		load("Login.php");
	// alle Links überprüfen
	login.check(true);
});

// Globales Setup für $.ajax() Funktionen
$.ajaxSetup({
	// siehe include/settings.php
	data:{ "<?=$antibody->postName[0]?>":"<?=$antibody->postName[1]?>" },
	// immer POST-Request!
	type:"post"
});

function load(url) {
	// Falls ein Request mal länger dauern sollte, weiß der Benutzer das was
	// gemacht wird
	$("#loading").html("Loading...").fadeIn("fast");
	
	// Falls die URL ein Leerzeichen enthält z.B.: "Default.php #content"
	// wird der hintere Teil ("#content") in die Variable selector gespeichert
	// und die url natürlich auch um " #content" gekürzt
	// was dann gemacht wird siehe weiter unten
	var off = url.indexOf(" ");
	if ( off >= 0 ) {
		var selector = url.slice(off, url.length);
		url = url.slice(0, off);
	}
	// AJAX-Request senden
	$.ajax({
		url:url,
		success:function(r) {
			// Falls es einen Selector gibt wird nach diesem in dem Response
			// gesucht und es wird dann nur dieser Teil zurück gegeben (geht nur bei HTML)
			// z.B.:
			/*
<div>
	ich werde nicht angezeigt
	<div id="content">
		nur ich soll angezeigt werden!
	</div>
	ich auch nicht...
</div>
			*/
			// hoffe das es jetzt klarer ist was ich damit meine
			if(selector)
				$("#content")[0].innerHTML = $(selector, r)[0].innerHTML;
			else
				$("#content").html(r);
			// So alles fertig also das "Loading..." auch wegmachen
			// sonst irritiert das den Benutzer vielleicht
			$("#loading").fadeOut("slow");
		}
	});

}

// Kleine helfer Funktionen damit man die Links unsichtbar und wieder sichtbar
// machen kann
var login = {
	// Zeigt die Links an die ein normaler Benutzer sehen darf
	// den Login-Link braucht man ja auch nicht mehr also sieht man
	// den dann nicht mehr.
	show:function() {
		$(".logout").hide();
		$(".login").show();
	},
	// das gegenteil von show
	hide:function() {
		$(".logout").show();
		$(".login").hide();
	},
	// genauso wie hide nur sieht es viel schöner aus :)
	fadeOut:function() {
		$(".logout").animate({ opacity:"show", height:"show" }, "slow");
		$(".login").animate({ opacity:"hide", height:"hide" },"slow");
	},
	// genauso wie show nur sieht es viel schöner aus :)
	fadeIn:function() {
		$(".login").animate({ opacity:"show", height:"show" }, "slow");
		$(".logout").animate({ opacity:"hide", height:"hide" },"slow");
	},
	// diese Funktion managed alles
	// wenn der parameter "fast" auf "true" steht gibts keine schönen animationen...
	check:function(fast) {
		// falls das Login-Cookie existiert und auf "true" steht
		// werden die Links angezeigt
		if( $.cookie("Login") == true )
			if(fast) // schnell
				this.show();
			else // oder langsam
				this.fadeIn();
		// falls das Login-Cookie nicht existiert werden die Links ...
		else if(fast) // ... schnell ...
			this.hide();
		else // ... oder langsam ...
			this.fadeOut();
		// ... versteckt
		window.setTimeout(function() { $(".login:visible, .logout:visible").css({ display:"block" }); }, 700);
	}
};

// So hier ist nun das $numbers Array
// welches natürlich erst noch encodiert werden muss
// siehe include/settings.php
var numbers = <?=json_encode($numbers)?>;

// Diese Object wird für die AJAX-Tooltips verwendet
// OLresponseAJAX => der Response vom AJAX-Request
// Verwendet wird das zum Beispiel so: <td><a href="javascript:;" onclick="return OLgetAJAX('meine/Datei.php', tooltip.defaultCommand);" onmouseout="return nd();">Hallo!</a></td>
// hier sind 2 verschiedenen Arten von Tooltips
var tooltip = {
	stickyCommand:function() {
		// Sticky: der Tooltip bleibt solange bis man mit der Maus
		// rein und wieder raus gefahren ist
		return overlib(OLresponseAJAX, CENTER, STICKY, NOCLOSE);
	},
	defaultCommand:function() {
		// Zentriert und folgt nicht der Maus
		return overlib(OLresponseAJAX, CENTER, NOFOLLOW);
	}
};
