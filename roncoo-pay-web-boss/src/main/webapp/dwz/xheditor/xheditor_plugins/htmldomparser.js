/*
 * HTMLParser - This implementation of parser assumes we are parsing HTML in browser
 * and user DOM methods available in browser for parsing HTML.
 * 
 * @author Himanshu Gilani
 * 
 */

var HTMLParser = function(node, handler, opts) {
	opts = opts || {};
	var nodesToIgnore = opts['nodesToIgnore'] || [];
	var parseHiddenNodes = opts['parseHiddenNodes'] || 'false';
	
	var c = node.childNodes;
	for ( var i = 0; i < c.length; i++) {
		try {
			var ignore = false;
			for(var k=0; k< nodesToIgnore.length; k++) {
				if(c[i].nodeName.toLowerCase() == nodesToIgnore[k]) {
					ignore= true;
					break;
				}
			}
			
			//NOTE hidden node testing is expensive in FF.
			if (ignore || (!parseHiddenNodes && isHiddenNode(c[i]))  ){
				continue;
			} 
			
			if (c[i].nodeName.toLowerCase() != "#text" && c[i].nodeName.toLowerCase() != "#comment") {
				var attrs = [];

				if (c[i].hasAttributes()) {
					var attributes = c[i].attributes;
					for ( var a = 0; a < attributes.length; a++) {
						var attribute = attributes.item(a);

						attrs.push({
							name : attribute.nodeName,
							value : attribute.nodeValue,
						});
					}
				}

				if (handler.start) {
					if (c[i].hasChildNodes()) {
						handler.start(c[i].nodeName, attrs, false);

						if (c[i].nodeName.toLowerCase() == "pre" || c[i].nodeName.toLowerCase() == "code") {
							handler.chars(c[i].innerHTML);
						} else if (c[i].nodeName.toLowerCase() == "iframe" || c[i].nodeName.toLowerCase() == "frame") {
							if (c[i].contentDocument && c[i].contentDocument.documentElement) {
								return HTMLParser(c[i].contentDocument.documentElement, handler, opts);
							}
						} else if (c[i].hasChildNodes()) {
							HTMLParser(c[i], handler, opts);
						}

						if (handler.end) {
							handler.end(c[i].nodeName);
						}
					} else {
						handler.start(c[i].nodeName, attrs, true);
					}
				}
			} else if (c[i].nodeName.toLowerCase() == "#text") {
				if (handler.chars) {
					handler.chars(c[i].nodeValue);
				}
			} else if (c[i].nodeName.toLowerCase() == "#comment") {
				if (handler.comment) {
					handler.comment(c[i].nodeValue);
				}
			}
		} catch (e) {
			//properly log error	
			console.log("error while parsing node: " + c[i].nodeName.toLowerCase());
		}
	}
};

function isHiddenNode(node) {
	if(node.nodeName.toLowerCase() == "title"){
		return false;
	}
	
	if (window.getComputedStyle) {
		try {
			var style = window.getComputedStyle(node, null);
			if (style.getPropertyValue && style.getPropertyValue('display') == 'none') {
				return true;
			}
		} catch (e) {
			// consume and ignore. node styles are not accessible
		}
		return false;
	}
} 