if(!dojo._hasResource["dojox.charting.plot2d.ClusteredBars"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.charting.plot2d.ClusteredBars"] = true;
dojo.provide("dojox.charting.plot2d.ClusteredBars");

dojo.require("dojox.charting.plot2d.common");
dojo.require("dojox.charting.plot2d.Bars");

dojo.require("dojox.lang.functional");
dojo.require("dojox.lang.functional.reversed");

(function(){
	var df = dojox.lang.functional, dc = dojox.charting.plot2d.common,
		purgeGroup = df.lambda("item.purgeGroup()");

	dojo.declare("dojox.charting.plot2d.ClusteredBars", dojox.charting.plot2d.Bars, {
		render: function(dim, offsets){
			this.dirty = this.isDirty();
			if(this.dirty){
				dojo.forEach(this.series, purgeGroup);
				this.cleanGroup();
				var s = this.group;
				df.forEachRev(this.series, function(item){ item.cleanGroup(s); });
			}
			var t = this.chart.theme, color, stroke, fill, f, gap, height, thickness,
				ht = this._hScaler.scaler.getTransformerFromModel(this._hScaler),
				vt = this._vScaler.scaler.getTransformerFromModel(this._vScaler);
				baseline = Math.max(0, this._hScaler.bounds.lower),
				baselineWidth = ht(baseline),
				events = this.events();
			f = dc.calculateBarSize(this._vScaler.bounds.scale, this.opt, this.series.length);
			gap = f.gap;
			height = thickness = f.size;
			this.resetEvents();
			for(var i = this.series.length - 1; i >= 0; --i){
				var run = this.series[i], shift = thickness * (this.series.length - i - 1);
				if(!this.dirty && !run.dirty){ continue; }
				run.cleanGroup();
				var s = run.group;
				if(!run.fill || !run.stroke){
					// need autogenerated color
					color = run.dyn.color = new dojo.Color(t.next("color"));
				}
				stroke = run.stroke ? run.stroke : dc.augmentStroke(t.series.stroke, color);
				fill = run.fill ? run.fill : dc.augmentFill(t.series.fill, color);
				for(var j = 0; j < run.data.length; ++j){
					var v = run.data[j],
						hv = ht(v),
						width = hv - baselineWidth,
						w = Math.abs(width);
					if(w >= 1 && height >= 1){
						var shape = s.createRect({
							x: offsets.l + (v < baseline ? hv : baselineWidth),
							y: dim.height - offsets.b - vt(j + 1.5) + gap + shift,
							width: w, height: height
						}).setFill(fill).setStroke(stroke);
						run.dyn.fill   = shape.getFill();
						run.dyn.stroke = shape.getStroke();
						if(events){
							var o = {
								element: "bar",
								index:   j,
								run:     run,
								plot:    this,
								hAxis:   this.hAxis || null,
								vAxis:   this.vAxis || null,
								shape:   shape,
								x:       v,
								y:       j + 1.5
							};
							this._connectEvents(shape, o);
						}
					}
				}
				run.dirty = false;
			}
			this.dirty = false;
			return this;
		}
	});
})();

}
