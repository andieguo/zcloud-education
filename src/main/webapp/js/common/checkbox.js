// --列头全选框被单击---  
function ChkAllClick(keyIds, cbAllId){  
    var arrSon = document.getElementsByName(keyIds);  
 var cbAll = document.getElementById(cbAllId);  
 var tempState=cbAll.checked;  
 for(i=0;i<arrSon.length;i++) {  
  if(arrSon[i].checked!=tempState)  
           arrSon[i].click();  
 }  
}  
  
// --子项复选框被单击---  
function ChkSonClick(keyIds, cbAllId) {  
 var arrSon = document.getElementsByName(keyIds);  
 var cbAll = document.getElementById(cbAllId);  
 for(var i=0; i<arrSon.length; i++) {  
     if(!arrSon[i].checked) {  
     cbAll.checked = false;  
     return;  
     }  
 }  
 cbAll.checked = true;  
}  
  
// --反选被单击---  
function ChkOppClick(keyIds){  
 var arrSon = document.getElementsByName(keyIds);  
 for(i=0;i<arrSon.length;i++) {  
  arrSon[i].click();  
 }  
}  
