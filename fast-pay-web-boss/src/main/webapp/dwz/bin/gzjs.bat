cd D:\workspace\UI\dev\dwz_jui\bin

REM -------------- start package javascript --------------

type ..\js\dwz.core.js > dwzESC.js
type ..\js\dwz.util.date.js >> dwzESC.js
type ..\js\dwz.validate.method.js >> dwzESC.js
type ..\js\dwz.barDrag.js >> dwzESC.js
type ..\js\dwz.drag.js >> dwzESC.js
type ..\js\dwz.tree.js >> dwzESC.js
type ..\js\dwz.accordion.js >> dwzESC.js
type ..\js\dwz.ui.js >> dwzESC.js
type ..\js\dwz.theme.js >> dwzESC.js
type ..\js\dwz.switchEnv.js >> dwzESC.js

type ..\js\dwz.alertMsg.js >> dwzESC.js
type ..\js\dwz.contextmenu.js >> dwzESC.js
type ..\js\dwz.navTab.js >> dwzESC.js
type ..\js\dwz.tab.js >> dwzESC.js
type ..\js\dwz.resize.js >> dwzESC.js
type ..\js\dwz.dialog.js >> dwzESC.js
type ..\js\dwz.dialogDrag.js >> dwzESC.js
type ..\js\dwz.sortDrag.js >> dwzESC.js
type ..\js\dwz.cssTable.js >> dwzESC.js
type ..\js\dwz.stable.js >> dwzESC.js
type ..\js\dwz.taskBar.js >> dwzESC.js
type ..\js\dwz.ajax.js >> dwzESC.js
type ..\js\dwz.pagination.js >> dwzESC.js
type ..\js\dwz.database.js >> dwzESC.js
type ..\js\dwz.datepicker.js >> dwzESC.js
type ..\js\dwz.effects.js >> dwzESC.js
type ..\js\dwz.panel.js >> dwzESC.js
type ..\js\dwz.checkbox.js >> dwzESC.js
type ..\js\dwz.combox.js >> dwzESC.js
type ..\js\dwz.history.js >> dwzESC.js
type ..\js\dwz.print.js >> dwzESC.js

cscript ESC.wsf -l 1 -ow dwzESC1.js dwzESC.js
cscript ESC.wsf -l 2 -ow dwzESC2.js dwzESC1.js
cscript ESC.wsf -l 3 -ow dwzESC3.js dwzESC2.js

type dwzESC2.js > dwz.min.js
#gzip -f dwz.min.js
#copy dwz.min.js.gz dwz.min.gzjs /y

del dwzESC*.js
del dwz.min.js.gz