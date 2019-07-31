<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
<jsp:useBean id="assetManager" class="com.gen.cms.assets.beans.Assets_DBQueries"></jsp:useBean>


<TITLE>Generate Reference Number</TITLE>
<script type="text/javascript">
function reLoad()
{
	document.ReferenceCreation.action="";
	document.ReferenceCreation.submit();
}

function subForm()
{
	document.frm1.DocType.value=document.getElementById("docTy").value;
	alert(document.getElementById("docTy").value);
	alert(document.frm1.DocType.value);
	document.frm1.action="../../AssetsServlet";
	document.frm1.submit();  
}

function validate()
{
	if(document.getElementById("unit").value=="")
	{
		alert("Select Unit");
		return false;
	}
	if(document.getElementById("dep").value=="")
	{
		alert("Select Department");
		return false;
	}
	if(document.getElementById("usrId").value=="")
	{
		alert("Select Associate Name");
		return false;
	}
	if(document.getElementById("chk1").checked==true)
	{
		document.PersonalFile.status.value=document.getElementById("chk1").value;
	}
	if(document.getElementById("chk2").checked==true)
	{
		document.PersonalFile.status.value=document.getElementById("chk2").value;
	}
    /* document.PersonalFile.file.value=document.getElementById("file1").value; */
 
	 document.PersonalFile.action="../../AssetsServlet";
	document.PersonalFile.submit(); 
}
</script>
<style>
.align{
	background-image: url("..\..\images\Reference_No\background_image.jpg");
	height: inherit;
	width: auto;
	float: left;
	left: 20%;
	top: 30%;
	border-radius: 10%;
}
.align1
{
	float: left;
	left: 30%;
}
.align1 div
{
	text-align: left;
	font-size: 3ex;
	font-family: sans-serif;
	font-style: oblique;
	padding-bottom: 5px;
	padding-top: 10px;
	padding-left: 10px;
}
.lft label
{
	margin-top: 10px;
	vertical-align: middle;
	margin: 0;
	float: left;
}
.rht label
{
	display: table-cell;
	overflow: hidden;
}
</style>
</HEAD>
<%
	ReferenceNo_DBQuery db=new ReferenceNo_DBQuery();
	String reference=request.getParameter("ref");
	System.out.println("Reference Nnumber "+reference);
	ArrayList refDetails=db.selectDataRefTab(reference);
	System.out.println("Reference Data "+refDetails);
	String un=request.getParameter("unit");
	String dep12=request.getParameter("dep");
	String assoc=request.getParameter("userId");
	String chk=request.getParameter("chk1");
	System.out.println("Unit : "+un+" Department "+dep12+" Associate "+assoc);
	String department="";
	/* try
	{
		if(assoc!=null)
		{
			ArrayList depart=db.DepartmentUnit(assoc);
			System.out.println("Department Details "+depart);
			if(depart!=null)
			{
				for(int i=0;i<depart.size();i++)
				{
					ArrayList ar=(ArrayList)depart.get(i);
					department=""+ar.get(2);
				}
			}
		}
	}catch(ArrayIndexOutOfBoundsException e)
	{
		System.out.println("Exception "+e);
	} */
	System.out.println("Unit : "+un+" Department "+dep12+" Associate "+assoc);
 %>
<body class="align">
<form name="ReferenceCreation" method="post" action="">
<div>
	<div class="align1">
	<center><font style="font-size: 30; font-family: sans-serif;">Link Document</font></center>
	<table>
	<tr>
	<%for(int i=0;i<refDetails.size();i++)
	{ 
		ArrayList ar=(ArrayList)refDetails.get(i);%>
		<td>
			<input type="hidden" name="referenceNumber" value="<%=reference %>">
			<div class="lft"><label>Reference Number</label></div>  </td>
			<td><div class="rht"><label style="float: left;"><%=reference %></label></div></td>
		</tr>
		 <tr><td>
			<input type="hidden" name="DocT" id="docTy" value="<%=ar.get(3) %>">
			<div class="lft"><label>Document Type : </label></div></td>
			<td><div class="rht"> <label style="float: left;"><%=ar.get(3) %></label></div></td>
		</tr>	
		<tr>
			<td>
			<div class="lft"><label>File Name : </label></div></td>
			<td><div class="rht"><label style="float: left;"><%=ar.get(6) %></label></div></td>
		</tr>
		<tr>
			<td>
			<div class="lft"><label>Description</label></div></td>
			<td><div class="rht"><label style="float: left;"><%=ar.get(7) %></label></div></td>
		</tr>	
		<tr>
			<td>
			<div class="lft"><label>Submitted To</label></div></td>
			<td><div class="rht"><label style="float: left;"><%=ar.get(8).toString().toUpperCase() %></label></div></td>
		</tr>
		<tr>
			<td>
			<div class="lft"><label>Generated By</label></div></td>
			<%ArrayList useDetail=db.SelectFacFullName(ar.get(12).toString()); 
			System.out.println("Uder details "+useDetail);
			String FName=""+useDetail.get(1);
			String LName=""+useDetail.get(3);
			String FullName1=""+FName+" "+LName;%>
			<td><div class="rht"> <label style="float: left;"> <%=FullName1 %></label></div></td>
		</tr>	
		<tr>
			<td>
			<div class="lft"><label>Prepared By</label></div></td>
			<%ArrayList useDetail1=db.SelectFacFullName(ar.get(2).toString()); 
			System.out.println("Uder details "+useDetail);
			String FName1=""+useDetail1.get(1);
			String LName1=""+useDetail1.get(3);
			String FullName11=""+FName1+" "+LName1;%>
			<td><div class="rht"> <label style="float: left;"><%=FullName11 %></label></div></td>
		</tr>
		<tr>
			<td>
			<div class="lft"><label>Unit</label></div></td>
			<td><div class="rht"><%
    		ArrayList unit=db.SelectUnit();
    		System.out.println("Unit "+unit); %>
    			<select name="unit" id="unit" data-live-search="true" data-live-search-style="startsWith" class="selectpicker" data-style="btn-default" data-size="10" data-width="50%" data-height="10%" data-width="100%" data-height="10%" onchange="reLoad()">
    				<option value="">Select Unit</option>
    				<%for(int u=0;u<unit.size();u++){ 
    					ArrayList unar=(ArrayList)unit.get(u);
    					String unit1=""+unar.get(0);
    					%>
    					<option value="<%=unar.get(0)%>" <%if(unit1.equals(un)){ %>selected<%} %>><%=unar.get(0) %></option>
    				<%} %>
    			</select></div></td>
		</tr>	
		<tr>
			<td>
			<div class="lft"><label>Department</label></div></td>
			<td><div class="rht">
			<%
				System.out.println("Unit Short Form "+un);
				 ArrayList Depart=db.SelectDepartment(un);
	    		 System.out.println("Department List "+Depart+" Department Size "+Depart.size());%>
	    			<select name="dep" id="dep" data-live-search="true" data-live-search-style="startsWith" class="selectpicker" data-style="btn-default" data-size="10" data-width="50%" data-height="10%" data-width="100%" data-height="10%" onchange="reLoad()">
	    				<option value="">Select Department</option>
	    				<%
	    				String Did="";
	    				String Dname="";
	    				String DShName="";
	    				for(int d=0;d<Depart.size();d++)
	    				{
	    					System.out.println("Details Data "+Depart.get(d));
	    					ArrayList DepDet=(ArrayList)Depart.get(d);
	    					System.out.println("Details Data=== "+DepDet);
	    					System.out.println("Department "+DepDet.get(0)+" Unit "+DepDet.get(1));
	    					Did=""+DepDet.get(0);
	    					Dname=""+DepDet.get(1);
	    					DShName=""+DepDet.get(2);
	    					System.out.println("Department Sh_name "+DShName);
	    				%>
	    				<option value="<%=Did%>" <%if(Did.equals(dep12)){ %>selected<%} %>><%=Dname %></option>
	    			<%} %>
    			</select></div></td>
		</tr>
		<tr>
			<td>
			<div class="lft"><label>Associate Name</label></div></td>
			<td><div class="rht"><%
				System.out.println("Unit Short Form "+un);
				 ArrayList Associate=db.SelectAssociateNames(dep12);
	    		 System.out.println("Associate Name "+Associate);%>
	    			<select name="userId" id="usrId" data-live-search="true" data-live-search-style="startsWith" class="selectpicker" data-style="btn-default" data-size="10" data-width="50%" data-height="10%" data-width="100%" data-height="10%" onchange="reLoad()">
	    				<option value="">Select Associate</option>
	    				<%
	    				String fac_id="";
						String fName="";
						String mName="";
						String lName="";
						String FullName="";
					for(int f=0;f<Associate.size();f++){ 
						ArrayList Fac_data=(ArrayList)Associate.get(f);
						fac_id=""+Fac_data.get(0);
						fName=""+Fac_data.get(1);
						mName=""+Fac_data.get(2);
						FullName=""+fName+" "+mName+" "+lName;
	    				%>
	    				<option value="<%=fac_id%>" <%if(fac_id.equals(assoc)){ %>selected<%} %>><%=FullName %></option>
	    			<%} %> </div></td>
		</tr>	
		<tr>
			<td>
			<div class="lft"><label>Link To Personal Docs</label></div></td>
			<td><div class="rht"><input type="radio" value="1" name="chk1" id="chk1" onclick="reLoad()" <%if("1".equals(chk)){ %>checked<%} %>> Personal File
								<input type="radio" value="0" name="chk1" id="chk2" onclick="reLoad()" <%if("0".equals(chk)){ %>checked<%} %>> Not Personal File</div></td>
		</tr>	
		<tr>
			<td></td>
		</tr>
	<%} %>
	</table>
	</div>	
</div>
</form>
	<form name="frm1"  method="post" enctype="multipart/form-data">
	<input type="hidden" name="ActionId" value="PersonalFile">
	<input type="hidden" name="task" value="add">
	<input type="hidden" name="Units" value="<%=un%>">
	<input type="hidden" name="DocType" id="doc" value="<%%>">
	<input type="hidden" name="departShName" value="<%=dep12%>">
	<input type="hidden" name="status" value="<%=chk%>">
	<input type="hidden" name="referenceNumber" value="<%=reference %>">
	<input type="hidden" name="created_by" value="<%=email%>">
	<input type="hidden" name="Associate" id="Associate" value="<%=assoc%>">			
	<input type="file" name="tempload" class="form-control">
	<input type="submit" value="Upload" class="btn" onclick="subForm()">
</form>
</BODY>
<%}else{ %>
<%@include file="../cookieTracker/CookieTrackerBottom.jsp" %>
<%} %>
</HTML>