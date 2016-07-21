<script type="text/javascript">
var options = {
	axis: "0 0 1 1", // Where to put the labels (trbl)
	axisxstep: 11, // How many x interval labels to render (axisystep does the same for the y axis)
	axisxlables: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
	axisystep: 20,
	shade:false, // true, false
	smooth:true, //曲线
	symbol:"circle"
};

$(function () {
	
	// Make the raphael object
	var r = Raphael("chartHolder"); 
	
	var lines = r.linechart(
		20, // X start in pixels
		20, // Y start in pixels
		600, // Width of chart in pixels
		400, // Height of chart in pixels
		[0,1,2,3,4,5,6,7,8,9,10,11], // Array of x coordinates equal in length to ycoords
		[[584, 535, 406, 254, 171, 47, 24, 58, 104, 255, 396, 564],[100,20,300,400,300,582,70,50,90,100,110,120]], // Array of y coordinates equal in length to xcoords
		options // opts object
	).hoverColumn(function () {
        this.tags = r.set();

		var box_x = this.x, box_y = 30,
			box_w = 100, box_h = 80;
		if (box_x + box_w > r.width) box_x -= box_w;
		var box = r.rect(box_x,box_y,box_w,box_h).attr({stroke: "#f00", "stroke-width": 1, r:5});
		this.tags.push(box);

        for (var i = 0; i < this.y.length; i++) {
			//this.tags.push(r.blob(this.x, this.y[i], "$"+this.values[i]).insertBefore(this).attr([{ fill: "#ffa500", stroke: "#000"}, { fill: this.symbols[i].attr("fill") }]));
        	var t = r.text(box_x+20, box_y+10 + i*16,"$"+this.values[i]).attr({fill: this.symbols[i].attr("fill")})
        	this.tags.push(t);
        }
        
        
    }, function () {
        this.tags && this.tags.remove();
    });

    lines.symbols.attr({ r: 6 });
});
</script>

<div id="chartHolder" style="width: 650px;height: 450px"></div>
