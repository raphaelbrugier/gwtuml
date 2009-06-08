if(!dojo._hasResource["dojox.charting.themes.PurpleRain"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.charting.themes.PurpleRain"] = true;
dojo.provide("dojox.charting.themes.PurpleRain");
dojo.require("dojox.charting.Theme");

(function(){
	//	notes: colors generated by moving in 30 degree increments around the hue circle,
	//		at 90% saturation, using a B value of 75 (HSB model).
	var dxc=dojox.charting;
	dxc.themes.PurpleRain=new dxc.Theme({
		colors: [
			"#4879bc",
			"#ef446f",
			"#3f58a7",
			"#8254a2",
			"#4956a6"
		]
	});
})();

}
