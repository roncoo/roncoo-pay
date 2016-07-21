/*!
 * WYSIWYG UBB Editor support for xhEditor
 * @requires xhEditor
 * 
 * @author Yanis.Wang<yanis.wang@gmail.com>
 * @site http://xheditor.com/
 * @licence LGPL(http://www.opensource.org/licenses/lgpl-license.php)
 * 
 * @Version: 0.9.12 (build 120228)
 */
function ubb2html(sUBB)
{
	var i,sHtml=String(sUBB),arrcode=new Array(),cnum=0;
	var arrFontsize=['10px','13px','16px','18px','24px','32px','48px'];

	sHtml=sHtml.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];});
	sHtml=sHtml.replace(/\r?\n/g,"<br />");
	
	sHtml=sHtml.replace(/\[code\s*(?:=\s*([^\]]+?))?\]([\s\S]*?)\[\/code\]/ig,function(all,t,c){//code特殊处理
		cnum++;arrcode[cnum]=all;
		return "[\tubbcodeplace_"+cnum+"\t]";
	});

	sHtml=sHtml.replace(/\[(\/?)(b|u|i|s|sup|sub)\]/ig,'<$1$2>');
	sHtml=sHtml.replace(/\[color\s*=\s*([^\]"]+?)(?:"[^\]]*?)?\s*\]/ig,'<font color="$1">');
	sHtml=sHtml.replace(/\[font\s*=\s*([^\]"]+?)(?:"[^\]]*?)?\s*\]/ig,'<font face="$1">');
	sHtml=sHtml.replace(/\[\/(color|font)\]/ig,'</font>');
	sHtml=sHtml.replace(/\[size\s*=\s*([^\]"]+?)(?:"[^\]]*?)?\s*\]/ig,function(all,size){
		if(size.match(/^\d+$/))size=arrFontsize[size-1];
		return '<span style="font-size:'+size+';">';
	});
	sHtml=sHtml.replace(/\[back\s*=\s*([^\]"]+?)(?:"[^\]]*?)?\s*\]/ig,'<span style="background-color:$1;">');
	sHtml=sHtml.replace(/\[\/(size|back)\]/ig,'</span>');
	for(i=0;i<3;i++)sHtml=sHtml.replace(/\[align\s*=\s*([^\]"]+?)(?:"[^\]]*?)?\s*\](((?!\[align(?:\s+[^\]]+)?\])[\s\S])*?)\[\/align\]/ig,'<p align="$1">$2</p>');
	sHtml=sHtml.replace(/\[img\]\s*(((?!")[\s\S])+?)(?:"[\s\S]*?)?\s*\[\/img\]/ig,'<img src="$1" alt="" />');
	sHtml=sHtml.replace(/\[img\s*=([^,\]]*)(?:\s*,\s*(\d*%?)\s*,\s*(\d*%?)\s*)?(?:,?\s*(\w+))?\s*\]\s*(((?!")[\s\S])+?)(?:"[\s\S]*)?\s*\[\/img\]/ig,function(all,alt,p1,p2,p3,src){
		var str='<img src="'+src+'" alt="'+alt+'"',a=p3?p3:(!isNum(p1)?p1:'');
		if(isNum(p1))str+=' width="'+p1+'"';
		if(isNum(p2))str+=' height="'+p2+'"'
		if(a)str+=' align="'+a+'"';
		str+=' />';
		return str;
	});
	sHtml=sHtml.replace(/\[emot\s*=\s*([^\]"]+?)(?:"[^\]]*?)?\s*\/\]/ig,'<img emot="$1" />');
	sHtml=sHtml.replace(/\[url\]\s*(((?!")[\s\S])*?)(?:"[\s\S]*?)?\s*\[\/url\]/ig,'<a href="$1">$1</a>');
	sHtml=sHtml.replace(/\[url\s*=\s*([^\]"]+?)(?:"[^\]]*?)?\s*\]\s*([\s\S]*?)\s*\[\/url\]/ig,'<a href="$1">$2</a>');
	sHtml=sHtml.replace(/\[email\]\s*(((?!")[\s\S])+?)(?:"[\s\S]*?)?\s*\[\/email\]/ig,'<a href="mailto:$1">$1</a>');
	sHtml=sHtml.replace(/\[email\s*=\s*([^\]"]+?)(?:"[^\]]*?)?\s*\]\s*([\s\S]+?)\s*\[\/email\]/ig,'<a href="mailto:$1">$2</a>');
	sHtml=sHtml.replace(/\[quote\]/ig,'<blockquote>');
	sHtml=sHtml.replace(/\[\/quote\]/ig,'</blockquote>');
	sHtml=sHtml.replace(/\[flash\s*(?:=\s*(\d+)\s*,\s*(\d+)\s*)?\]\s*(((?!")[\s\S])+?)(?:"[\s\S]*?)?\s*\[\/flash\]/ig,function(all,w,h,url){
		if(!w)w=480;if(!h)h=400;
		return '<embed type="application/x-shockwave-flash" src="'+url+'" wmode="opaque" quality="high" bgcolor="#ffffff" menu="false" play="true" loop="true" width="'+w+'" height="'+h+'"/>';
	});
	sHtml=sHtml.replace(/\[media\s*(?:=\s*(\d+)\s*,\s*(\d+)\s*(?:,\s*(\d+)\s*)?)?\]\s*(((?!")[\s\S])+?)(?:"[\s\S]*?)?\s*\[\/media\]/ig,function(all,w,h,play,url){
		if(!w)w=480;if(!h)h=400;
		return '<embed type="application/x-mplayer2" src="'+url+'" enablecontextmenu="false" autostart="'+(play=='1'?'true':'false')+'" width="'+w+'" height="'+h+'"/>';
	});
	sHtml=sHtml.replace(/\[table\s*(?:=\s*(\d{1,4}%?)\s*(?:,\s*([^\]"]+)(?:"[^\]]*?)?)?)?\s*\]/ig,function(all,w,b){
		var str='<table';
		if(w)str+=' width="'+w+'"';
		if(b)str+=' bgcolor="'+b+'"';
		return str+'>';
	});
	sHtml=sHtml.replace(/\[tr\s*(?:=\s*([^\]"]+?)(?:"[^\]]*?)?)?\s*\]/ig,function(all,bg){
		return '<tr'+(bg?' bgcolor="'+bg+'"':'')+'>';
	});
	sHtml=sHtml.replace(/\[td\s*(?:=\s*(\d{1,2})\s*,\s*(\d{1,2})\s*(?:,\s*(\d{1,4}%?))?)?\s*\]/ig,function(all,col,row,w){
		return '<td'+(col>1?' colspan="'+col+'"':'')+(row>1?' rowspan="'+row+'"':'')+(w?' width="'+w+'"':'')+'>';
	});
	sHtml=sHtml.replace(/\[\/(table|tr|td)\]/ig,'</$1>');
	
	sHtml=sHtml.replace(/\[\*\]((?:(?!\[\*\]|\[\/list\]|\[list\s*(?:=[^\]]+)?\])[\s\S])+)/ig,'<li>$1</li>');
	sHtml=sHtml.replace(/\[list\s*(?:=\s*([^\]"]+?)(?:"[^\]]*?)?)?\s*\]/ig,function(all,type){
		var str='<ul';
		if(type)str+=' type="'+type+'"';
		return str+'>';
	});
	sHtml=sHtml.replace(/\[\/list\]/ig,'</ul>');
	sHtml=sHtml.replace(/\[hr\/\]/ig,'<hr />');
	
	for(i=1;i<=cnum;i++)sHtml=sHtml.replace("[\tubbcodeplace_"+i+"\t]", arrcode[i]);

	sHtml=sHtml.replace(/(^|<\/?\w+(?:\s+[^>]*?)?>)([^<$]+)/ig, function(all,tag,text){
		return tag+text.replace(/[\t ]/g,function(c){return {'\t':'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',' ':'&nbsp;'}[c];});
	});
	function isNum(s){if(s!=null&&s!='')return !isNaN(s);else return false;}
	
	return sHtml;
}

function html2ubb(sHtml)
{

	var regSrc=/\s+src\s*=\s*(["']?)\s*(.+?)\s*\1(\s|$)/i,regWidth=/\s+width\s*=\s*(["']?)\s*(\d+(?:\.\d+)?%?)\s*\1(\s|$)/i,regHeight=/\s+height\s*=\s*(["']?)\s*(\d+(?:\.\d+)?%?)\s*\1(\s|$)/i,regBg=/(?:background|background-color|bgcolor)\s*[:=]\s*(["']?)\s*((rgb\s*\(\s*\d{1,3}%?,\s*\d{1,3}%?\s*,\s*\d{1,3}%?\s*\))|(#[0-9a-f]{3,6})|([a-z]{1,20}))\s*\1/i
	var i,sUBB=String(sHtml),arrcode=new Array(),cnum=0;

	sUBB=sUBB.replace(/[ \t]*\r?\n[ \t]*/g,'');
	
	sUBB = sUBB.replace(/<(script|style)(\s+[^>]*?)?>[\s\S]*?<\/\1>/ig, '');
	sUBB = sUBB.replace(/<!--[\s\S]*?-->/ig,'');

	sUBB=sUBB.replace(/<br(\s+[^>]*)?\/?>/ig,"\r\n");
	
	sUBB=sUBB.replace(/\[code\s*(=\s*([^\]]+?))?\]([\s\S]*?)\[\/code\]/ig,function(all,t,c){//code特殊处理
		cnum++;arrcode[cnum]=all;
		return "[\tubbcodeplace_"+cnum+"\t]";
	});
	
	sUBB=sUBB.replace(/<(\/?)(b|u|i|s)(\s+[^>]*?)?>/ig,'[$1$2]');
	sUBB=sUBB.replace(/<(\/?)strong(\s+[^>]*?)?>/ig,'[$1b]');
	sUBB=sUBB.replace(/<(\/?)em(\s+[^>]*?)?>/ig,'[$1i]');
	sUBB=sUBB.replace(/<(\/?)(strike|del)(\s+[^>]*?)?>/ig,'[$1s]');
	sUBB=sUBB.replace(/<(\/?)(sup|sub)(\s+[^>]*?)?>/ig,'[$1$2]');

	//font转ubb
	function font2ubb(all,tag,attrs,content)
	{
		if(!attrs)return content;
		var arrStart=[],arrEnd=[];
		var match;
		match=attrs.match(/ face\s*=\s*"\s*([^"]+)\s*"/i);
		if(match){
			arrStart.push('[font='+match[1]+']');
			arrEnd.push('[/font]');
		}
		match=attrs.match(/ size\s*=\s*"\s*(\d+)\s*"/i);
		if(match){
			arrStart.push('[size='+match[1]+']');
			arrEnd.push('[/size]');
		}
		match=attrs.match(/ color\s*=\s*"\s*([^"]+)\s*"/i);
		if(match){
			arrStart.push('[color='+formatColor(match[1])+']');
			arrEnd.push('[/color]');
		}
		return arrStart.join('')+content+arrEnd.join('');
	}
	sUBB = sUBB.replace(/<(font)(\s+[^>]*?)?>(((?!<\1(\s+[^>]*?)?>)[\s\S]|<\1(\s+[^>]*?)?>((?!<\1(\s+[^>]*?)?>)[\s\S]|<\1(\s+[^>]*?)?>((?!<\1(\s+[^>]*?)?>)[\s\S])*?<\/\1>)*?<\/\1>)*?)<\/\1>/ig,font2ubb);//第3层
	sUBB = sUBB.replace(/<(font)(\s+[^>]*?)?>(((?!<\1(\s+[^>]*?)?>)[\s\S]|<\1(\s+[^>]*?)?>((?!<\1(\s+[^>]*?)?>)[\s\S])*?<\/\1>)*?)<\/\1>/ig,font2ubb);//第2层
	sUBB = sUBB.replace(/<(font)(\s+[^>]*?)?>(((?!<\1(\s+[^>]*?)?>)[\s\S])*?)<\/\1>/ig,font2ubb);//最里层

	for(i=0;i<3;i++)sUBB=sUBB.replace(/<(span)(?:\s+[^>]*?)?\s+style\s*=\s*"((?:[^"]*?;)*\s*(?:font-family|font-size|color|background|background-color)\s*:[^"]*)"(?: [^>]+)?>(((?!<\1(\s+[^>]*?)?>)[\s\S]|<\1(\s+[^>]*?)?>((?!<\1(\s+[^>]*?)?>)[\s\S]|<\1(\s+[^>]*?)?>((?!<\1(\s+[^>]*?)?>)[\s\S])*?<\/\1>)*?<\/\1>)*?)<\/\1>/ig,function(all,tag,style,content){
		var face=style.match(/(?:^|;)\s*font-family\s*:\s*([^;]+)/i),size=style.match(/(?:^|;)\s*font-size\s*:\s*([^;]+)/i),color=style.match(/(?:^|;)\s*color\s*:\s*([^;]+)/i),back=style.match(/(?:^|;)\s*(?:background|background-color)\s*:\s*([^;]+)/i),str=content;
		var arrStart=[],arrEnd=[];
		if(face){
			arrStart.push('[font='+face[1]+']');
			arrEnd.push('[/font]');
		}
		if(size){
			arrStart.push('[size='+size[1]+']');
			arrEnd.push('[/size]');
		}
		if(color){
			arrStart.push('[color='+formatColor(color[1])+']');
			arrEnd.push('[/color]');
		}
		if(back){
			arrStart.push('[back='+formatColor(back[1])+']');
			arrEnd.push('[/back]');
		}
		return arrStart.join('')+str+arrEnd.join('');
	});
	function formatColor(c)
	{
		var matchs;
		if(matchs=c.match(/\s*rgb\s*\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*\)/i)){c=(matchs[1]*65536+matchs[2]*256+matchs[3]*1).toString(16);while(c.length<6)c='0'+c;c='#'+c;}
		c=c.replace(/^#([0-9a-f])([0-9a-f])([0-9a-f])$/i,'#$1$1$2$2$3$3');
		return c;
	}
	for(i=0;i<3;i++)sUBB=sUBB.replace(/<(div|p)(?:\s+[^>]*?)?[\s"';]\s*(?:text-)?align\s*[=:]\s*(["']?)\s*(left|center|right)\s*\2[^>]*>(((?!<\1(\s+[^>]*?)?>)[\s\S])+?)<\/\1>/ig,'[align=$3]$4[/align]');
	for(i=0;i<3;i++)sUBB=sUBB.replace(/<(center)(?:\s+[^>]*?)?>(((?!<\1(\s+[^>]*?)?>)[\s\S])*?)<\/\1>/ig,'[align=center]$2[/align]');
	for(i=0;i<3;i++)sUBB=sUBB.replace(/<(p|div)(?:\s+[^>]*?)?\s+style\s*=\s*"(?:[^;"]*;)*\s*text-align\s*:([^;"]*)[^"]*"(?: [^>]+)?>(((?!<\1(\s+[^>]*?)?>)[\s\S]|<\1(\s+[^>]*?)?>((?!<\1(\s+[^>]*?)?>)[\s\S]|<\1(\s+[^>]*?)?>((?!<\1(\s+[^>]*?)?>)[\s\S])*?<\/\1>)*?<\/\1>)*?)<\/\1>/ig,function(all,tag,align,content){
		return '[align='+align+']'+content+'[/align]';
	});
	sUBB=sUBB.replace(/<a(?:\s+[^>]*?)?\s+href=(["'])\s*(.+?)\s*\1[^>]*>\s*([\s\S]*?)\s*<\/a>/ig,function(all,q,url,text){
		if(!(url&&text))return '';
		var tag='url',str;
		if(url.match(/^mailto:/i))
		{
			tag='email';
			url=url.replace(/mailto:(.+?)/i,'$1');
		}
		str='['+tag;
		if(url!=text)str+='='+url;
		return str+']'+text+'[/'+tag+']';
	});
	sUBB=sUBB.replace(/<img(\s+[^>]*?)\/?>/ig,function(all,attr){
		var emot=attr.match(/\s+emot\s*=\s*(["']?)\s*(.+?)\s*\1(\s|$)/i);
		if(emot)return '[emot='+emot[2]+'/]';
		var url=attr.match(regSrc),alt=attr.match(/\s+alt\s*=\s*(["']?)\s*(.*?)\s*\1(\s|$)/i),w=attr.match(regWidth),h=attr.match(regHeight),align=attr.match(/\s+align\s*=\s*(["']?)\s*(\w+)\s*\1(\s|$)/i),str='[img',p='';
		if(!url)return '';
		p+=alt[2];
		if(w||h)p+=','+(w?w[2]:'')+','+(h?h[2]:'');
		if(align)p+=','+align[2];
		if(p)str+='='+p;
		str+=']'+url[2]+'[/img]';
		return str;
	});
	sUBB=sUBB.replace(/<blockquote(?:\s+[^>]*?)?>/ig,'[quote]');
	sUBB=sUBB.replace(/<\/blockquote>/ig,'[/quote]');
	sUBB=sUBB.replace(/<embed((?:\s+[^>]*?)?(?:\s+type\s*=\s*"\s*application\/x-shockwave-flash\s*"|\s+classid\s*=\s*"\s*clsid:d27cdb6e-ae6d-11cf-96b8-4445535400000\s*")[^>]*?)\/?>/ig,function(all,attr){
		var url=attr.match(regSrc),w=attr.match(regWidth),h=attr.match(regHeight),str='[flash';
		if(!url)return '';
		if(w&&h)str+='='+w[2]+','+h[2];
		str+=']'+url[2];
		return str+'[/flash]';
	});
	sUBB=sUBB.replace(/<embed((?:\s+[^>]*?)?(?:\s+type\s*=\s*"\s*application\/x-mplayer2\s*"|\s+classid\s*=\s*"\s*clsid:6bf52a52-394a-11d3-b153-00c04f79faa6\s*")[^>]*?)\/?>/ig,function(all,attr){
		var url=attr.match(regSrc),w=attr.match(regWidth),h=attr.match(regHeight),p=attr.match(/\s+autostart\s*=\s*(["']?)\s*(.+?)\s*\1(\s|$)/i),str='[media',auto='0';
		if(!url)return '';
		if(p)if(p[2]=='true')auto='1';
		if(w&&h)str+='='+w[2]+','+h[2]+','+auto;
		str+=']'+url[2];
		return str+'[/media]';
	});
	sUBB=sUBB.replace(/<table(\s+[^>]*?)?>/ig,function(all,attr){
		var str='[table';
		if(attr)
		{
			var w=attr.match(regWidth),b=attr.match(regBg);
			if(w)
			{
				str+='='+w[2];
				if(b)str+=','+b[2];
			}
		}
		return str+']';
	});
	sUBB=sUBB.replace(/<tr(\s+[^>]*?)?>/ig,function(all,attr){
		var str='[tr';
		if(attr)
		{
			var bg=attr.match(regBg)
			if(bg)str+='='+bg[2];
		}
		return str+']';
	});
	sUBB=sUBB.replace(/<(?:th|td)(\s+[^>]*?)?>/ig,function(all,attr){
		var str='[td';
		if(attr)
		{
			var col=attr.match(/\s+colspan\s*=\s*(["']?)\s*(\d+)\s*\1(\s|$)/i),row=attr.match(/\s+rowspan\s*=\s*(["']?)\s*(\d+)\s*\1(\s|$)/i),w=attr.match(regWidth);
			col=col?col[2]:1;
			row=row?row[2]:1;
			if(col>1||row>1||w)str+='='+col+','+row;
			if(w)str+=','+w[2];
		}
		return str+']';
	});
	sUBB=sUBB.replace(/<\/(table|tr)>/ig,'[/$1]');
	sUBB=sUBB.replace(/<\/(th|td)>/ig,'[/td]');
	
	sUBB=sUBB.replace(/<ul(\s+[^>]*?)?>/ig,function(all,attr){
		var t;
		if(attr)t=attr.match(/\s+type\s*=\s*(["']?)\s*(.+?)\s*\1(\s|$)/i);
		return '[list'+(t?'='+t[2]:'')+']';
	});
	sUBB=sUBB.replace(/<ol(\s+[^>]*?)?>/ig,'[list=1]');
	sUBB=sUBB.replace(/<li(\s+[^>]*?)?>/ig,'[*]');
	sUBB=sUBB.replace(/<\/li>/ig,'');
	sUBB=sUBB.replace(/<\/(ul|ol)>/ig,'[/list]');
	sUBB=sUBB.replace(/<h([1-6])(\s+[^>]*?)?>/ig,function(all,n){return '\r\n\r\n[size='+(7-n)+'][b]'});
	sUBB=sUBB.replace(/<\/h[1-6]>/ig,'[/b][/size]\r\n\r\n');
	sUBB=sUBB.replace(/<address(\s+[^>]*?)?>/ig,'\r\n[i]');
	sUBB=sUBB.replace(/<\/address>/ig,'[i]\r\n');
	sUBB=sUBB.replace(/<hr(\s+[^>]*?)?\/>/ig,'[hr/]');

	for(i=0;i<3;i++)sUBB=sUBB.replace(/<(p)(?:\s+[^>]*?)?>(((?!<\1(\s+[^>]*?)?>)[\s\S]|<\1(\s+[^>]*?)?>((?!<\1(\s+[^>]*?)?>)[\s\S]|<\1(\s+[^>]*?)?>((?!<\1(\s+[^>]*?)?>)[\s\S])*?<\/\1>)*?<\/\1>)*?)<\/\1>/ig,"\r\n\r\n$2\r\n\r\n");
	for(i=0;i<3;i++)sUBB=sUBB.replace(/<(div)(?:\s+[^>]*?)?>(((?!<\1(\s+[^>]*?)?>)[\s\S]|<\1(\s+[^>]*?)?>((?!<\1(\s+[^>]*?)?>)[\s\S]|<\1(\s+[^>]*?)?>((?!<\1(\s+[^>]*?)?>)[\s\S])*?<\/\1>)*?<\/\1>)*?)<\/\1>/ig,"\r\n$2\r\n");
	
	sUBB=sUBB.replace(/((\s|&nbsp;)*\r?\n){3,}/g,"\r\n\r\n");//限制最多2次换行
	sUBB=sUBB.replace(/^((\s|&nbsp;)*\r?\n)+/g,'');//清除开头换行
	sUBB=sUBB.replace(/((\s|&nbsp;)*\r?\n)+$/g,'');//清除结尾换行
	
	for(i=1;i<=cnum;i++)sUBB=sUBB.replace("[\tubbcodeplace_"+i+"\t]", arrcode[i]);

	sUBB=sUBB.replace(/<[^<>]+?>/g,'');//删除所有HTML标签
	var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
	sUBB=sUBB.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
	
	//清除空内容的UBB标签
	sUBB=sUBB.replace(/\[([a-z]+)(?:=[^\[\]]+)?\]\s*\[\/\1\]/ig,'');
	
	return sUBB;
}