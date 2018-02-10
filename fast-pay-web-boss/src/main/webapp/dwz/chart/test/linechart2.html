<script type="text/javascript">
var options = {
	axis: "0 0 1 1", // Where to put the labels (trbl)
	axisxstep: 10, // How many x interval labels to render (axisystep does the same for the y axis)
	shade:false, // true, false
	smooth:true, //曲线
	symbol:"circle"
};

$(function () {
	
	// Make the raphael object
	var r = Raphael("chartHolder"); 
	
	var lines = r.linechart(
		20, // X start in pixels
		10, // Y start in pixels
		600, // Width of chart in pixels
		400, // Height of chart in pixels
		[[.5,1.5,2,2.5,3,3.5,4,4.5,5],[.5,1.5,2,2.5,3,3.5,4,4.5,5]], // Array of x coordinates equal in length to ycoords
		[[7,11,9,16,3,19,12,12,15],[1,2,3,4,3,6,7,5,9]], // Array of y coordinates equal in length to xcoords
		options // opts object
	).hoverColumn(function () {
        this.tags = r.set();

        for (var i = 0, ii = this.y.length; i < ii; i++) {
            this.tags.push(r.tag(this.x, this.y[i], this.values[i], 160, 10).insertBefore(this).attr([{ fill: "#fff" }, { fill: this.symbols[i].attr("fill") }]));
        }
    }, function () {
        this.tags && this.tags.remove();
    });

    lines.symbols.attr({ r: 6 });
});
</script>

<div id="chartHolder" style="width: 650px;height: 450px"></div>
