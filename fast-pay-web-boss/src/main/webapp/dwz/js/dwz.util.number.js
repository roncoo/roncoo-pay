/**
 * @author 张慧华 z@j-ui.com
 * 
 */
(function(){

function formatCurrency(num) {
	num = num.toString().replace(/\$|\,/g,'');
	if(isNaN(num)) {num = "0";}
	
	var sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*100+0.50000000001);
	var cents = num%100;
	num = Math.floor(num/100).toString();
	
	if(cents<10) {cents = "0" + cents;}
	
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++){
		num = num.substring(0,num.length-(4*i+3))+','+num.substring(num.length-(4*i+3));
	}

	return (((sign)?'':'-') + num + '.' + cents);
}
function parseCurrency(str) {
	if (!str) return 0;
	str = str.replace(',', '');
	return $.isNumeric(str) ? parseFloat(str) : 0;
}

/**
 * 数字转中文大写
 */
function amountInWords(dValue, maxDec){
	// 验证输入金额数值或数值字符串：
	dValue = dValue.toString().replace(/,/g, ""); dValue = dValue.replace(/^0+/, "");	  // 金额数值转字符、移除逗号、移除前导零
	if (dValue == "") { return "零元整"; }	  // （错误：金额为空！）
	else if (isNaN(dValue)) { return "错误：金额不是合法的数值！"; }
   
	var minus = "";							 // 负数的符号“-”的大写：“负”字。可自定义字符，如“（负）”。
	var CN_SYMBOL = "";						 // 币种名称（如“人民币”，默认空）
	if (dValue.length > 1) {
		if (dValue.indexOf('-') == 0) { dValue = dValue.replace("-", ""); minus = "负"; }   // 处理负数符号“-”
		if (dValue.indexOf('+') == 0) { dValue = dValue.replace("+", ""); }				 // 处理前导正数符号“+”（无实际意义）
	}
   
	// 变量定义：
	var vInt = "", vDec = "";	// 字符串：金额的整数部分、小数部分
	var resAIW;	// 字符串：要输出的结果
	var parts;	// 数组（整数部分.小数部分），length=1时则仅为整数。
	var digits, radices, bigRadices, decimals;	// 数组：数字（0~9——零~玖）；基（十进制记数系统中每个数字位的基是10——拾,佰,仟）；大基（万,亿,兆,京,垓,杼,穰,沟,涧,正）；辅币（元以下，角/分/厘/毫/丝）。
	var zeroCount;	// 零计数
	var i, p, d;	// 循环因子；前一位数字；当前位数字。
	var quotient, modulus;	// 整数部分计算用：商数、模数。

	// 金额数值转换为字符，分割整数部分和小数部分：整数、小数分开来搞（小数部分有可能四舍五入后对整数部分有进位）。
	var NoneDecLen = (typeof(maxDec) == "undefined" || maxDec == null || Number(maxDec) < 0 || Number(maxDec) > 5);	 // 是否未指定有效小数位（true/false）
	parts = dValue.split('.');	// 数组赋值：（整数部分.小数部分），Array的length=1则仅为整数。
	if (parts.length > 1) {
		vInt = parts[0]; vDec = parts[1];	// 变量赋值：金额的整数部分、小数部分
	   
		if(NoneDecLen) { maxDec = vDec.length > 5 ? 5 : vDec.length; }	// 未指定有效小数位参数值时，自动取实际小数位长但不超5。
		var rDec = Number("0." + vDec);	
		rDec *= Math.pow(10, maxDec); rDec = Math.round(Math.abs(rDec)); rDec /= Math.pow(10, maxDec);	// 小数四舍五入
		var aIntDec = rDec.toString().split('.');
		if(Number(aIntDec[0]) == 1) { vInt = (Number(vInt) + 1).toString(); }	// 小数部分四舍五入后有可能向整数部分的个位进位（值1）
		if(aIntDec.length > 1) { vDec = aIntDec[1]; } else { vDec = ""; }
	}
	else { vInt = dValue; vDec = ""; if(NoneDecLen) { maxDec = 0; } }
	if(vInt.length > 44) { return "错误：金额值太大了！整数位长【" + vInt.length.toString() + "】超过了上限——44位/千正/10^43（注：1正=1万涧=1亿亿亿亿亿，10^40）！"; }
   
	// 准备各字符数组 Prepare the characters corresponding to the digits:
	digits = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖");
	radices = new Array("", "拾", "佰", "仟");	// 拾,佰,仟
	bigRadices = new Array("", "万", "亿", "兆", "京", "垓", "杼", "穰" ,"沟", "涧", "正");
	decimals = new Array("角", "分", "厘", "毫", "丝");	
   
	resAIW = ""; // 开始处理
   
	// 处理整数部分（如果有）
	if (Number(vInt) > 0) {
		zeroCount = 0;
		for (i = 0; i < vInt.length; i++) {
			p = vInt.length - i - 1; d = vInt.substr(i, 1); quotient = p / 4; modulus = p % 4;
			if (d == "0") { zeroCount++; }
			else {
				if (zeroCount > 0) { resAIW += digits[0]; }
				zeroCount = 0; resAIW += digits[Number(d)] + radices[modulus];
			}
			if (modulus == 0 && zeroCount < 4) { resAIW += bigRadices[quotient]; }
		}
		resAIW += "元";
	}
   
	// 处理小数部分（如果有）
	for (i = 0; i < vDec.length; i++) { d = vDec.substr(i, 1); if (d != "0") { resAIW += digits[Number(d)] + decimals[i]; } }
   
	// 处理结果
	if (resAIW == "") { resAIW = "零" + "元"; }	 // 零元
	if (vDec == "") { resAIW += "整"; }	// ...元整
	resAIW = CN_SYMBOL + minus + resAIW;	// 人民币/负......元角分/整
	return resAIW;
}

Number.prototype.formatCurrency = function(format) {
	return formatCurrency(this);
};
Number.prototype.amountInWords = function(maxDec) {
	return amountInWords(this, maxDec);
}

String.prototype.parseCurrency = function(format) {
	return parseCurrency(this);
};
String.prototype.amountInWords = function(maxDec) {
	var dValue = parseCurrency(this);
	return amountInWords(dValue, maxDec);
}
	
})();
