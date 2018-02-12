/*!
 * MultiUpload for xheditor
 * @requires xhEditor
 * 
 * @author Yanis.Wang<yanis.wang@gmail.com>
 * @site http://xheditor.com/
 * @licence LGPL(http://www.opensource.org/licenses/lgpl-license.php)
 * 
 * @Version: 0.9.2 (build 100505)
 */
var swfu,selQueue=[],selectID,arrMsg=[],allSize=0,uploadSize=0;
function removeFile()
{
	var file;
	if(!selectID)return;
	for(var i in selQueue)
	{
		file=selQueue[i];
		if(file.id==selectID)
		{
			selQueue.splice(i,1);
			allSize-=file.size;
			swfu.cancelUpload(file.id);
			$('#'+file.id).remove();
			selectID=null;
			break;
		}
	}
	$('#btnClear').hide();
	if(selQueue.length==0)$('#controlBtns').hide();
}
function startUploadFiles()
{
	if(swfu.getStats().files_queued>0)
	{
		$('#controlBtns').hide();
		swfu.startUpload();
	}
	else alert('上传前请先添加文件');
}
function setFileState(fileid,txt)
{
	$('#'+fileid+'_state').text(txt);
}
function fileQueued(file)//队列添加成功
{
	for(var i in selQueue)if(selQueue[i].name==file.name){swfu.cancelUpload(file.id);return false;}//防止同名文件重复添加
	if(selQueue.length==0)$('#controlBtns').show();
	selQueue.push(file);
	allSize+=file.size;
	$('#listBody').append('<tr id="'+file.id+'"><td>'+file.name+'</td><td>'+formatBytes(file.size)+'</td><td id="'+file.id+'_state">就绪</td></tr>');
	$('#'+file.id).hover(function(){$(this).addClass('hover');},function(){$(this).removeClass('hover');})
	.click(function(){selectID=file.id;$('#listBody tr').removeClass('select');$(this).removeClass('hover').addClass('select');$('#btnClear').show();})
}
function fileQueueError(file, errorCode, message)//队列添加失败
{
	var errorName='';
	switch (errorCode)
	{
		case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
			errorName = "只能同时上传 "+this.settings.file_upload_limit+" 个文件";
			break;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			errorName = "选择的文件超过了当前大小限制："+this.settings.file_size_limit;
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			errorName = "零大小文件";
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			errorName = "文件扩展名必需为："+this.settings.file_types_description+" ("+this.settings.file_types+")";
			break;
		default:
			errorName = "未知错误";
			break;
	}
	alert(errorName);
}
function uploadStart(file)//单文件上传开始
{
	setFileState(file.id,'上传中…');
}
function uploadProgress(file, bytesLoaded, bytesTotal)//单文件上传进度
{
	var percent=Math.ceil((uploadSize+bytesLoaded)/allSize*100);
	$('#progressBar span').text(percent+'% ('+formatBytes(uploadSize+bytesLoaded)+' / '+formatBytes(allSize)+')');
	$('#progressBar div').css('width',percent+'%');
}
function uploadSuccess(file, serverData)//单文件上传成功
{
	var data=Object;
	try{eval("data=" + serverData);}catch(ex){};
	if(data.err!=undefined&&data.msg!=undefined)
	{
		if(!data.err)
		{
			uploadSize+=file.size;
			arrMsg.push(data.msg);
			setFileState(file.id,'上传成功');
		}
		else
		{
			setFileState(file.id,'上传失败');
			alert(data.err);
		}
	}
	else setFileState(file.id,'上传失败！');
}
function uploadError(file, errorCode, message)//单文件上传错误
{
	setFileState(file.id,'上传失败！');
}
function uploadComplete(file)//文件上传周期结束
{
	if(swfu.getStats().files_queued>0)swfu.startUpload();
	else uploadAllComplete();
}
function uploadAllComplete()//全部文件上传成功
{
	callback(arrMsg);
}
function formatBytes(bytes) {
	var s = ['Byte', 'KB', 'MB', 'GB', 'TB', 'PB'];
	var e = Math.floor(Math.log(bytes)/Math.log(1024));
	return (bytes/Math.pow(1024, Math.floor(e))).toFixed(2)+" "+s[e];
}