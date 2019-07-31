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

</script>
<style>
table{
	padding: 10px;
}
/* .data
{
	background-image: url("..\..\images\Reference_No\background_image.jpg");
	border: 2px dashed black;
	width: 50%;
	height: 50%;
	left: 30%;
	top: 100%;
} */

.list
{
	font-size: 15px;
	font-family: sans-serif;
	font-weight: bolder;
	color: black;
}
.dt{
	color: green;
	font-size: 15px;
	font-family: sans-serif;
	font-weight: bolder;
	left: 20%;
}

a{
	font-size: 15px;
}
</style>
</HEAD>
<%
	ReferenceNo_DBQuery db=new ReferenceNo_DBQuery();
	String assocName=request.getParameter("name");
	String ref=request.getParameter("ref");
	ArrayList linkData=db.selectPersonalFile(ref);
	System.out.println("Data Fetched is "+linkData);
	System.out.println("Data Fetched is size "+linkData.size());
 %>
<body>
<form name="ReferenceCreation" method="post" action="../../AssetsServlet">
<input type="hidden" name="ActionId" value="DoanloadFile">
<input type="hidden" name="op" value="2">
<input type="hidden" name="Task" value="Add">
<div class="container-fluid">
<div class="row">
<%for(int i=0;i<linkData.size();i++)
{
	ArrayList detail=(ArrayList)linkData.get(i); 
if(detail!=null){%>
	<div class="col-sm-4">
		<center ><font style="padding-left: 10px; font-size: 15px; color: black; font-weight: bolder;">Associate Related Document</font></center>
	<%if(detail.get(9).equals("1")){ %>
		<font style="padding-left: 10px; font-size: 15px; color: red; font-weight: bolder;">Important File Link To Your Profile</font>
	<%} 
	else{
	%>
		<font style="padding-left: 10px; font-size: 15px; color: red; font-weight: bolder;">Not An Important File</font>
	<%} %>
	<table class="table">
		<tr>
			<td class="list">Reference Number : </td>
			<td class="dt"><%=detail.get(1) %></td>
		</tr>
		<tr>
			<td class="list">Document Type : </td>
			<td class="dt"><%=detail.get(2) %></td>
		</tr>
		<tr>
			<td class="list">Unit : </td>
			<td class="dt"><%=detail.get(5) %></td>
		</tr>
		<tr>
			<td class="list">Department : </td>
			<td class="dt"><%=detail.get(6) %></td>
		</tr>
		<tr>
			<td class="list">View File : 
			</td>
			<%String filep=""+detail.get(8)+"\\"+detail.get(13); 
			System.out.println("File Path "+filep);%>
			<td class="dt">
			<input type="hidden" name="FileName" value="<%=detail.get(13)%>">
			<input type="hidden" name="PathDocs" value="<%=detail.get(8)%>">
			<input type="submit" value="Download" ></td>
		</tr>
	</table>
	</div>
	<%} %>
<%} %>
</div>
</form>
<form name="ReferenceCreation" method="post" action="../../AssetsServlet">
<input type="hidden" name="ActionId" value="DoanloadFile">
<input type="hidden" name="op" value="1">
<input type="hidden" name="Task" value="Add">
<hr/>
<div class="div">
<div class="col-sm-4">
<center ><font style="padding-left: 10px; font-size: 15px; color: black; font-weight: bolder;">Other Related Document</font></center>
<table class="table">
	<%ArrayList ref_det=db.selectReferenceUploadDoc(ref);
	System.out.println("Reference Numebr Data "+ref_det);
	for(int k=0;k<ref_det.size();k++)
	{
		int cnt=1;
		ArrayList refdata=(ArrayList)ref_det.get(k); 
		if(refdata.get(19)!=null)
		{%>
			<tr>
				<td class="list">Document Type : </td>
				<td class="dt"><%=refdata.get(3) %></td>
			</tr>
			<tr>
				<td class="list">Click Here To View : </td>
				<%String filPath=""+refdata.get(19)+"\\"+refdata.get(22); 
				System.out.println("Path "+filPath);%>
				<input type="hidden" name="path" value="<%=refdata.get(19)%>">
				<input type="hidden" name="FileName" value="<%=refdata.get(22)%>">
				<td class="dt"><input type="submit" value="Download"></td>
			</tr>
			<%
		}else{%>
			<font style="padding-left: 10px; font-size: 15px; color: red; font-weight: bolder;">No Related Document Found</font>
		<%}
	} %>
</table>
</div>
</div>
</form>
</BODY>
<%}else{ %>
<%@include file="../cookieTracker/CookieTrackerBottom.jsp" %>
<%} %>
</HTML>