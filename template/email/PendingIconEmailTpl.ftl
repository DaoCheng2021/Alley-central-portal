<!DOCTYPE html>
<html lang="en">
<head>
    <title>Table_Simple CSS for HTML tables</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <style type="text/css">
        html {
            font-family: sans-serif;
            -ms-text-size-adjust: 100%;
            -webkit-text-size-adjust: 100%;
        }

        body {
            margin: 10px;
        }

        table {
            border-collapse: collapse;
            border-spacing: 0;
        }

        td, th {
            padding: 0;
        }

        .pure-table {
            border-collapse: collapse;
            border-spacing: 0;
            empty-cells: show;
            border: 1px solid #cbcbcb;
        }

        .pure-table caption {
            color: #000;
            font: italic 85%/1 arial, sans-serif;
            padding: 1em 0;
            text-align: center;
        }

        .pure-table td, .pure-table th {
            border-left: 1px solid #cbcbcb;
            border-width: 0 0 0 1px;
            font-size: inherit;
            margin: 0;
            overflow: visible;
            padding: .5em 1em;
        }

        .pure-table thead {
            background-color: #e0e0e0;
            color: #000;
            text-align: left;
            vertical-align: bottom;
        }

        .pure-table td {
            background-color: transparent;
        }
    </style>
</head>
<body>
<table class="pure-table">
    <thead>
    <tr>
        <th>iconId</th>
        <th>brand</th>
        <th>tags</th>
        <th>name</th>
    </tr>
    </thead>

    <tbody>
    <#list AssetIconList as list>
    <tr>
        <td><a href=${list.serverPath}>${list.iconId}</a></td>
        <td><a href="https://www.baidu.com">${list.brand}</a></td>
        <td>${list.tags}</td>
        <td>${list.name}</td>
    </tr>
    </#list>

    <#--
    html文件简单模板：http://www.webkaka.com/tutorial/html/2020/021567/
    点击字体的超链接： <a href="https://www.baidu.com">点击就是百度</a>
    -->
    </tbody>
</table>
</body>
</html>
