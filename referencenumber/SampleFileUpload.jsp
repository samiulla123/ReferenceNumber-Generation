<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.gen.cms.util.DateManager"%>
<%@page import="com.gen.cms.assets.beans.*"%> 
<%@page import="java.util.ArrayList"%> 
<%@page import="java.sql.ResultSet"%>
<HTML>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>


<%@ page import="com.gen.cms.assets.beans.*"%>
<%@ page import="java.util.*"%>

<%@ page errorPage="../../error.jsp" %>

<%@ include file="../cookieTracker/CookieTrackerTop.jsp" %> 
<%
	int num=0;
	String browserType=(String)request.getHeader("User-Agent");
	System.out.println("Browser Type "+browserType); 
	Assets_DBQueries dbVer=new Assets_DBQueries();
	String version=dbVer.BrowserVersion(browserType);
	float ver=11;
	if(!version.isEmpty())
	{
	ver=Float.parseFloat(version);
	}
	System.out.println("Version of IE "+ver);
	if(ver<11.0){
		num=1;%>
	<!-- Code Comes here -->
	<%}else if(version.isEmpty()){%>
		 <meta http-equiv="X-UA-Compatible" content="IE=edge">
		 <meta name="viewport" content="width=device-width, initial-scale=1">
		 <meta name="robots" content="noindex, nofollow">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.css" />
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.js"></script>
	<%} %>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<META name="GENERATOR" content="IBM WebSphere Studio" />
<META http-equiv="Content-Style-Type" content="text/css" />
<LINK href="../../stylesheet/main.css" rel="stylesheet" type="text/css" />
<SCRIPT language="javascript" src="../../javascript/Validate.js"></SCRIPT>


<TITLE>Generate Reference Number</TITLE>
<script type="text/javascript">
function reLoad()
{
	document.ReferenceCreation.action="";
	document.ReferenceCreation.submit();
}

function printMsg()
{
	document.getElementById("msvisible").style.display="inline";
}

function diplayDiv()
{
	document.getElementById("dispHide").style.display="inline";
	/* var chk=document.getElementById("chek").checked;
	if(chk==true)
	{
		document.getElementById("dispHide").style.display="inline";
	}
	else
	{
		document.getElementById("chek").checked=false;
		document.getElementById("dispHide").style.display="none";
	} */
}

function upload(data)
{
	document.getElementById("refNumb").value=data;
 	document.ReferenceCreation.action="../../Onclick_Assets_Sub";
	document.ReferenceCreation.submit(); 
}

function printText()
	{
		var sTable = document.getElementById("d1").innerHTML;
		var unit= document.getElementById("u").value;
		var dep= document.getElementById("d").value;
        // CREATE A WINDOW OBJECT.
        var msg="Reference Report";
        var win = window.open('', '', 'height=700,width=700,location=yes,scrollbars=yes,resizable=yes');
		var today = new Date();
		var date=today.getDay()+'/'+today.getMonth()+'/'+today.getFullYear();
       win.document.write('<html><head>');
       win.document.write('<title>Asset Print</title>');// <title> FOR PDF HEADER.
       win.document.write('<LINK href="../../stylesheet/Assets_CSS/ReferenceNumberPrint.css" rel="stylesheet" type="text/css" />');  
       win.document.write('</head>');
       win.document.write('<body>');
       win.document.write('<center><h1 style="margin:0px;">PES&T\'s, SHAIKH GROUP OF INSTITUTIONS </h1></center>');
       win.document.write('<center><h2 style="margin:0px;">Shaikh Campus Nehru Nagar Belgavi-590010</h2></center>');
       win.document.write('<center><h4 style="margin: 0px">Reference Number Report</h4></center>');
       win.document.write('<font size="3"> Unit: '+unit+'</font> &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; <font size="3">Department: '+dep);
       win.document.write(sTable);         // THE TABLE CONTENTS INSIDE THE BODY TAG.
        win.document.write('</body></html>'); 
 		win.print();
        win.document.close(); 	// CLOSE THE CURRENT WINDOW. 

          // PRINT THE CONTENTS.
	}

</script>
<style>
#tbl {
	border-collapse: collapse;
	border: 1px solid black;
	width: 100%;
}

td,th {
	border-collapse: collapse;
	border: 1px solid black;
	text-align: center;
}

#disp {
	display: none;
}

#mouseHover:hover  #disp {
	width: 20%;
	height: 15%;
	text-align: left;
	border-radius: 10%;
	background-color: #DB9796;
	border: 2px solid black;
	color: black;
	font-size: 15px;
	position: absolute;
	font-family: sans-serif;
	font-style: oblique;
	display: inline;
}

#dispHide {
	width: 30%;
	height: 20%;
	background-color: #DB9796;
	border: 3px solid black;
	color: black;
	position: absolute;
	display: none;
	float: left;
	z-index: 1; /* Sit on top */
	padding-top: 100px; /* Location of the box */
	left: 80%;
	top: 50%;
}

.msg {
	width: 20%;
	background-color: #DB9796;
	color: green;
	position: absolute;
	z-index: 1;
	padding-top: 10px;
	left: 10%;
	top: 20%;
	text-align: center;
	font-family: sans-serif;
	font-size: 20px;
	border: 2px solid black;
}
/* #chek:hover #dispHide
{
	display: inline;
} */
/* 
table tr td div.msvisible {
	background-color: yellow;
	display: block;
} */
</style>
</HEAD>
<%
	ReferenceNo_DBQuery db=new ReferenceNo_DBQuery();
	String ref_no=request.getParameter("");
	ArrayList hod=db.fetchHODHOI(email);
	String hoi_hod=hod.get(0).toString().replace("[","").replace("]","");
	ArrayList depart=db.DepartmentUnit(email);
	String Unit="";
	String department="";
	for(int i=0;i<depart.size();i++)
	{
		ArrayList ar=(ArrayList)depart.get(i);
		Unit=""+ar.get(1);
		department=""+ar.get(2);
	}
	String msg=(String)session.getAttribute("msg");
	session.removeAttribute("msg");
 %>
<body>
<form name="ReferenceCreation" method="post" enctype="multipart/form-data">
<input type="hidden" name="ActionId" value="GenerateRefNo">
<input type="hidden" name="op" value="2">
<input type="hidden" name="Task" value="Add">
<input type="hidden" name="email" value="<%=email%>">
<input type="hidden" id="refNumb" name="ref" value="">
<input type="hidden" name="depar" value="<%=department%>">
<input type="hidden" name="unit" value="<%=Unit%>">
	<input type="">
</form>
</BODY>
<%}else{ %>
<%@include file="../cookieTracker/CookieTrackerBottom.jsp" %>
<%} %>
</HTML>