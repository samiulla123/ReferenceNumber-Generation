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

function upload(refNo)
{
	var file=document.getElementById("data"+refNo).value;
	alert(document.getElementById("data"+refNo).value);
	if(file=="")
	{
		alert("Browse File");
		return false;
	}
	/* document.getElementById("filePath").value=file; */
	document.getElementById("refNumb").value=refNo;
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
<form name="Not_Multi" method="post">
	
</form>
<form name="ReferenceCreation" method="post" enctype="multipart/form-data">
<input type="hidden" name="ActionId" value="GenerateRefNo">
<input type="hidden" name="op" value="2">
<input type="hidden" name="Task" value="Add">
<input type="hidden" name="email" value="<%=email%>">
<input type="hidden" id="refNumb" name="ref" value="">
<input type="hidden" name="depar" value="<%=department%>">
<input type="hidden" name="unit" value="<%=Unit%>">
<div class="container-fluid" style="margin-top: 20px;">
<h5>Reference Number Register</h5>
	<div class="center1">
		<div class="container-fluid">
			<div class="form-group">
				<div class="col-sm-5">
					<div class="input-group">
						<center><h5 style="margin: 0; color: green;">Enter Month in character, Year, Description, associate Short Name to search Reference Number </h5></center> <br/>
						<center><input id="hvr" type="text" name="search" class="form-control" placeholder="Type here " autocomplete="on" ></center><br/>
						<br/>
							<center><button type="submit" id="btn" value="Search" class="btn btn-primary"><span class="glyphicon glyphicon-search">Search</span></button></center>
						</div>
					</div>
				</div>
			</div>
	</div>
	<%if(msg!=null) {%>
		<div style="border-radius: 30%; float:right;">
			<label class="msg"><%=msg %></label> 
		</div>
	<%} %>
		<br/>
		<br/>
		<input type="hidden" value="<%=Unit%>" id="u">
		<input type="hidden" value="<%=department%>" id="d">
	<div id="d1">
	<table id="tbl" class="table">
	<%
		String str=(String)request.getParameter("search");
		ArrayList report=new ArrayList();
		int total_ref=0;
		try{
			if(str!=null)
			{
				report=db.SearchUserRef(str, email);
				total_ref=report.size();
			}
			else
			{
				report=db.selectReferenceReport(email);
			}
		}
		catch(NullPointerException e)
		{
			System.out.println("Null Pointer Exception "+e);
		}
		DateManager dm=new DateManager();
		String today=dm.getCurrentDate();
	 %>
	 <%if(total_ref!=0) {%>
	 	<h5>Total Reference Number Register Under <%=str.toUpperCase() %> : <%=total_ref %></h5>
	 <%} %>
		<tr>
			<th width="1%">Sl.No</th>
			<th width="15%">Reference Number</th>
			<th width="8%">Entry Date</th>
			<th width="2%">Prep<br/> By</th>
			<th style="width: 5%;">Folder<br/> Name</th>
			<th width="2%">File<br/> Name</th>
			<th style="width:20%;">Description</th>
			<th width="2%">Submitted<br/> To</th>
			<th width="2%">Generated<br/> By</th>
			<th width="2%">Checked<br/> By</th>
			<th width="2%">File Upload</th>
			<%if(email.equals(hoi_hod)){ %>
			<th width="2%">Upload Associate<br/> Document</th>
			<%}%>
			<% %>
		</tr>
		<%
			int count=1;
			DateManager dt=new DateManager();
			for(int i=0;i<report.size();i++)
			{ %>
		<tr>
				<%ArrayList ar=(ArrayList)report.get(i);
				String user_Id=""+ar.get(2);
				ArrayList userDetail=db.SelectFacFullName(user_Id);
				 String fullName="";
				String depa="";
					String name=""+userDetail.get(1);
					String mName=""+userDetail.get(2);
					String lName=""+userDetail.get(3);
					depa=""+userDetail.get(4);
					fullName=""+name+" "+mName+" "+lName;
				%>
				<td><%=count %></td>
				<% ArrayList refe=db.selectFilePath(ar.get(11).toString());
				ArrayList pdata=db.checkDataExists(ar.get(11).toString(), email);
			/* 	String pDt=pdata.get(0).toString().replace("[","").replace("]",""); */
				String nu=refe.get(0).toString().replace("[","").replace("]","");
				if(nu.equals("null")){%>
				<%if(pdata.size()==1)
					{%>
						<td><a href="ref_ListOfPersnal_Docs.jsp?name=<%=email%>&ref=<%=ar.get(11)%>"><%=ar.get(11) %></a></td>	
					<%}
				  else
					{%>
						<td> <%=ar.get(11) %></td>
					<%}
				}else { %>
				<td><a href="ref_ListOfPersnal_Docs.jsp?name=<%=email%>&ref=<%=ar.get(11)%>"><%=ar.get(11) %></a></td>	
				<%} %>
				<%String date1=""+ar.get(13);
				Date dat=new SimpleDateFormat("yyyy-mm-dd").parse(date1);
				SimpleDateFormat f=new SimpleDateFormat("dd-mm-yyyy"); %>
				<td><%=f.format(dat) %></td>
				<td><div id="mouseHover"><%=ar.get(1).toString().toUpperCase()%>
				<div id="disp">
					<b>Name:</b> <%=fullName %> <br/>
					<b>Department:</b> <%=depa %></div></div></td>
				<td><%=ar.get(5)%></td>
				<td><%=ar.get(6)%></td>
				<td><%=ar.get(7) %></td>
				<td><%=ar.get(8).toString().toUpperCase()%></td>
				<td><%=ar.get(9).toString().toUpperCase()%></td>
				<td><%=ar.get(9).toString().toUpperCase()%></td>
				<td><!-- <input type="checkbox" id="chek"> -->
				<div id=""><!-- <input type="button" value="Upload" onclick="diplayDiv()"><label>Upload</label> -->
				<%if(nu.equals("null")){%>
				<div id="">
					<%-- <input type="file" name="FileUpload" id="data<%=ar.get(11)%>"> --%>
				<a href="UploadFiles.jsp?ref=<%=ar.get(11)%>">Upload Document</a>
				</div>
				<%} %></div></td>
				<%
				ArrayList slct=new ArrayList();
				String assoc="";
				if(email.equals(hoi_hod))
				{ 
					slct=db.selectPersonalFile(ar.get(11).toString());
					for(int r=0;r<slct.size();r++)
					{
						ArrayList arl=(ArrayList)slct.get(r);
						assoc=""+arl.get(4);
					}%>
					<td>
					<%if(slct.size()==0)
					{%>
					<a href="Ref_Personal_Docs.jsp?ref=<%=ar.get(11) %>">Upload</a>
				  <%}%></td>
				<%}%>
				<%-- <td>
				<%if(slct.size()>0){ 
					if(email.equals(assoc)){%>
						<a href="ref_ListOfPersnal_Docs.jsp?name=<%=email%>&ref=<%=ar.get(11)%>">View Document</a>
				<%} 
				}%></td> --%>
		</tr>
		<%count+=1;} %>
	</table>
	</div>
</div>
	<div style="">
		<center><input type="button" class="btn" value="Print" onclick="printText()"></center>
	</div>

</form>
</BODY>
<%}else{ %>
<%@include file="../cookieTracker/CookieTrackerBottom.jsp" %>
<%} %>
</HTML>