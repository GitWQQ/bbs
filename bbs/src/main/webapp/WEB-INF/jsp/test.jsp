<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib  uri="http://www.tag.com/mytag" prefix="mytag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path=request.getContextPath();
	String basepath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<base href="<%=basepath%>">
	<title>如意论坛</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<script type="text/javascript">
        var hkey_root, hkey_path, hkey_key;
        hkey_root = "HKEY_CURRENT_USER";
        hkey_path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\"; //网页打印时设置清空页眉页脚     
        function setup_null() {
            try {
                var RegWsh = new ActiveXObject("WScript.Shell")
                hkey_key = "header"
                RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "")
                hkey_key = "footer"
                RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "")
            } catch (e) {}
        }

        function setup_default() { //网页打印时设置页眉页脚默认值        
            try {
                var RegWsh = new ActiveXObject("WScript.Shell")
                hkey_key = "header"
                RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "&w&b页码，&p/&P")
                hkey_key = "footer"
                RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "&u&b&d")
            } catch (e) {}
        }

        function doPrint(printDiv) {
            try {
            	setup_null();
                newwin = window.open("", "newwin", "height=" + window.screen.height + ",width=" + window.screen.width +
                    ",toolbar=no,scrollbars=auto,menubar=no");
                newwin.document.body.innerHTML = document.getElementById(printDiv).innerHTML;
                newwin.window.print();
                newwin.window.close();
                setup_default();
            } catch (e) {}
        }

        function printPage() { //获取当前页的html代码 
            setup_null();
            bdhtml = window.document.body.innerHTML;
            sprnstr = "<!--start-->";
            eprnstr = "<!--end-->";
            printhtml = bdhtml.substr(bdhtml.indexOf(sprnstr) + 17);
            printhtml = printhtml.substring(0, printhtml.indexOf(eprnstr));
            f = document.getElementById('printf');
            f.contentDocument.write(printhtml); //写入到新的iframe窗口
            f.contentDocument.close();
            f.contentWindow.print(); //在新的iframe窗口调用浏览器打印机
        }
    </script>
</head>

<body>
<%@include file="../common/script_style.jsp" %> 
	  <!--start-->
    <h3>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</h3>
    <h3>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</h3>
    <h3>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</h3>
    <h3>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</h3>
    <h3>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</h3>
    <h3>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</h3>
    <h3>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</h3>
    <h3>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</h3>
    <h3>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</h3>
    <h3>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</h3>
    <!--end-->
    <input type="button" value="打印" onClick="printPage()" />
    <input type="button" value="打印2" onClick="doPrint()" />
    <iframe id="printf" src="" width="0" height="0" frameborder="0"></iframe>
    </div>
</body>
</html>

