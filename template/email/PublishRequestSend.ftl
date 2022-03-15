<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Dev CP publish template</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<style>
    .detail {
        margin: 20px 30px;
    }
    .publish{
        margin: 20px 0px 10px 30px;
    }
    .tool{
        margin: 0px 0px 10px 50px;
    }


</style>
<#--<body style="margin: 10px">-->

<p>Visit template publish request was submitted, system is now processing data synchronization. You will get an email notification when the process is done.</p>

<p class="detail">Template Name: <b>${confPerform.performTypeDesc}</b></p>
<p class="detail">Template ID: <b>${confPerform.templateId}</b></p>
<p class="detail">Request ID: <b>${requestData.pubRequestId}</b></p>
<p class="detail">Requested By: <b>${requestData.requestedBy}</b></p>
<#--<p class="detail">Request Date: <b>${requestData.requestDate}</b></p>-->
<p class="detail">Request Date: <b>${requestData.status}</b></p>
<p class="detail">Markets: <b>${requestData.bmus}</b></p>
<p class="detail">Languages: <b>${requestData.languageNames}</b></p>
<div>
    <div class="publish">Published To:</div>
    <#list tools as tool>
        <div class="tool">- ${tool}</div>
    </#list>
</div>



<p>Central Portal ${emailMode}</p>


</body>
</html>