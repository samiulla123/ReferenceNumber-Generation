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
function display()
{
	alert("hi");
	if(document.getElementById("disp").style.display=="none")
	{
		document.getElementById("disp").style.display="inline";
	}
	else if(document.getElementById("disp").style.display=="inline")
	{
		document.getElementById("disp").style.display="none";
	}
}
function Load()
{
	var p=document.getElementById("bd").value;
	var f=document.getElementById("bd1").value;
	if(p=="")
	{
		alert("Select Prepared By Option");
		document.ReferenceCreation.assoc.focus();
		return false;
	}
	if(f=="")
	{
		alert("Select File Type Option");
		document.ReferenceCreation.f_type.focus();
		return false;
	}
	if(document.getElementById("bd3").value=="")
	{
		alert("Please provide file name");
		document.getElementById("bd3").focus();
		return false;
	}
	if(document.getElementById("bd4").value=="")
	{
		alert("Enter description ");
		document.getElementById("bd4").focus();
		return false;
	}
	if(document.getElementById("bd5").value=="")
	{
		alert("Enter short-name in submitted column");
		document.getElementById("bd5").focus();
		return false;
	}
	if(document.getElementById("cmnts").value=="")
	{
		alert("Enter Comment");
		document.getElementById("cmnts").focus();
		return false;
	}
	
		document.ReferenceCreation.action="../../AssetsServlet";
		document.ReferenceCreation.submit();
	
}
</script>
<style>
table{
	width: 100%;
	padding: 10px;
	float: left;
}
td{
	border-bottom: 1px soild white;
	padding: 10px;
}
caption{
	text-align:center;
	font-family: bold;
	font-size: 20px;	
	color: black;
}
.bd{
	background-color: #FFECEC;
}

#Layer5{
	/* width: 100%;
	height: 100%; */
	/* margin: 50px auto; */
	border-radius: 10px;
	background: linear-gradient(
      53deg,
      #FFECEC
    );
    margin-top: 50px;
    float:left;
}

#Layer4{
	width: 60%;
    background-image: url("..\..\images\Reference_No\background_image.jpg");
	border-radius: 10px;
	margin-top: 50px;
    float:left;
}

#button:hover{
	color: #BD0102;
}
#fnt{
	font-size: 15px;
}
.ref_No{
	font-size:20px;
	font-style:oblique;
	font-family: sans-serif;
	border-radius: 3px;
	margin-top: 30%;
	margin-left:10%;
}
.ref_No1{
	font-size:20px;
	font-style:oblique;
	font-family: sans-serif;
	border-radius: 3px;
	margin-top: 10%;
	margin-left:10%;
}
</style>
</HEAD>
<%
	boolean flag=false;
	int stats=0;
	System.out.println("________JSP__________");
	ReferenceNo_DBQuery rdb=new ReferenceNo_DBQuery();
	String ref_No=request.getParameter("f_type");
	System.out.println("File type= "+ref_No);
	String departmentName=(String)request.getParameter("depart");
	System.out.println("-------deparment Staff is------- "+departmentName);
	String subm=request.getParameter("sub");
	String refNubr=request.getParameter("refNumber");
	ArrayList ar=new ArrayList();
	ar=rdb.selectFileType();
	String f_name="";
	String f_sh_name="";
	for(int l=0;l<ar.size();l++)
	{
		ArrayList ar1=(ArrayList)ar.get(l);
		if(ar1.get(0).equals(ref_No))
		{
			f_name=""+ar1.get(1);
			f_sh_name=""+ar1.get(2);
		}
	}
	System.out.println("Reference Number "+ref_No);
	String pre_by=request.getParameter("assoc");
	System.out.println("PrePared by "+pre_by);
	String full_name="";
	String sh_name="";
	String dep="";
	ArrayList fac_det=rdb.SlectDepAssociate(email);
	ArrayList dep_det=rdb.DepartmentUnit(email);
	for(int j=0;j<dep_det.size();j++)
	{
		ArrayList ddd=(ArrayList)dep_det.get(j);
		dep=""+ddd.get(2);
	}
	ArrayList dep_AMSC_SCET=rdb.DepartmentUnitAMSC_SCET(email);
	System.out.println("Department Detail in Jsp "+dep_det);
	System.out.println("DEpartment of AMSC and SCET "+dep_AMSC_SCET);
	String prog_id="";
	String unit="";
	String depart="";
	String val="";
	
	//1 - only forSCOE, BIMD,SCES, SPUC,SCED adim 
	//2 - AMSC, SCET Admin
	//3 - AMSC, SCET faculty
	if(flag==true)
	{
		for(int u=0;u<dep_AMSC_SCET.size();u++)
		{
			ArrayList depun=(ArrayList)dep_AMSC_SCET.get(u);
			prog_id=""+depun.get(0);
			unit=""+depun.get(1);
			try
			{
				if(departmentName!=null)
				{
					depart=departmentName;
					val="as";
				}
			else
				{
					depart=""+depun.get(2);
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("Null Pointer Exception "+e);
			}
		}
	}
	else if(flag==false)
	{
		for(int u=0;u<dep_det.size();u++)
		{
			ArrayList depun=(ArrayList)dep_det.get(u);
			prog_id=""+depun.get(0);
			unit=""+depun.get(1);
			try
			{
				if(departmentName!=null)
				{
					depart=departmentName;
					val="sbs";
				}
				else
				{
					depart=""+depun.get(2);
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("Null Pointer Exception "+e);
			}
		}
	}
	System.out.println("Department  name "+depart);
	System.out.println("Unit name "+unit);
	for(int f=0;f<fac_det.size();f++)
	{
		ArrayList fac=(ArrayList)fac_det.get(f);
		if(fac.get(0).equals(pre_by))
		{
			String name=""+fac.get(1);
			String mName=""+fac.get(2);
			String lName=""+fac.get(3);
			sh_name=""+fac.get(4);
			full_name=""+name+" "+mName+" "+lName;
		}
	}
	System.out.println("faculty full name "+full_name);
	System.out.println("faculty short name "+sh_name);
	System.out.println("file name "+f_name);
	System.out.println("file short name "+f_sh_name);
	System.out.println("Faculty information "+fac_det);
	String bgclr="";
 if(num==1)
 {
 	bgclr="Layer4";
 }
 else{
 	bgclr="Layer5";
 }
 
 String error=(String)session.getAttribute("error");
 session.removeAttribute("error");
 String refer_numb=(String)session.getAttribute("ref_no");
 session.removeAttribute("ref_no");
 String file_type=(String)session.getAttribute("file_type");
 session.removeAttribute("file_type");
 String Fullname=(String)session.getAttribute("name");
 session.removeAttribute("name");
 String fileDesc="";
 %>
<body>
<form name="ReferenceCreation" method="post">
<input type="hidden" name="ActionId" value="GenerateRefNo">
<input type="hidden" name="op" value="1">
<input type="hidden" name="Task" value="Add">
<input type="hidden" name="email" value="<%=email%>">
<input type="hidden" name="stat" value="<%=val%>">
<input type="hidden" name="ge_fName" value="<%=prog_id%>">
<input type="hidden" name="ge_fName" value="<%=unit%>">
<input type="hidden" name="ge_fName" value="<%=depart%>">
<input type="hidden" name="ge_fName" value="<%=email%>">
<input type="hidden" name="referencNum" value="<%=refNubr%>">
<input type="hidden" value="<%=f_name%>" name="ge_fName">
<input type="hidden" value="<%=f_sh_name%>" name="ge_fName">
<input type="hidden" value="<%=full_name%>" name="ge_fName">
<input type="hidden" value="<%=sh_name%>" name="ge_fName">
<div class="container">
</div>
<div class="container">
	<div id="<%=bgclr%>">
		<div style="float:left; top: 20%;">
		<br/>
		<br/>
			<%-- <img src="<%=request.getContextPath()%>/images/Reference_No/QueTion.png" style="width:30%; height: 30%;" alt="Image Not Displayed"><br/> --%>
			
			<%if(error!=null) {%>
		 &nbsp;&nbsp;&nbsp;&nbsp;	<h5 style="left: 20%">Thank you <font color="green"><%=Fullname%></font> Your <br/> <%=error %></h5><br/>
			<%} %>
			<%if(file_type!=null){ %>
		 &nbsp;&nbsp;&nbsp;&nbsp;	<h5>File Type: <font color="green"><%=file_type %></font><h5><br/>
			<%} %>
			<%if(refer_numb!=null){ %>
		 &nbsp;&nbsp;&nbsp;&nbsp;	<h5>Reference Number : <font color="red"><%=refer_numb %></font></h5><br/>
			<%} %>
			</div>
		<div style="float:right;">
		<table>
			<caption>Reference Number Generation</caption>
			<tr>
				<%
				 System.out.println("-----------------Department Details -------------");
				if(unit.equals("SCOE") || unit.equals("BIMD") || unit.equals("SCES") || unit.equals("SPUC") || unit.equals("SCED"))
				{
					 ArrayList Associate_status=rdb.AssociateStatus(email);
					 String status=Associate_status.get(0).toString().replace("[","").replace("]","");
					  System.out.println("Status ="+status);
					 if(status.equals("2"))
					 {
					 	stats=1;
						 System.out.println("-----------------Department Details -------------"+Associate_status); %>
						<td id="fnt">Department <label style="color: red;"><b> * </b></label></td>
						 <td>
						 <select name="depart"  data-live-search="true" data-live-search-style="startsWith" class="selectpicker bd"  data-size="10" onchange="reLoad()">
						 	<option value="">Select Department</option>
						 	<option value="ADM" <%if("ADM".equals(departmentName)){ %>selected<%} %>>ADM</option>
						 	<option value="ACA" <%if("ACA".equals(departmentName)){ %>selected<%} %>>ACA</option>
						 	<option value="<%=dep %>" <%if(dep.equals(departmentName)){ %>selected<%} %>><%=dep %></option>
						 </select></td>
					 <%} %>
				<%}%> 
				<%if(unit.equals("AMSC") || unit.equals("SCET"))
				{
					 ArrayList Associate_status=rdb.AssociateStatus(email);
					 String status=Associate_status.get(0).toString().replace("[","").replace("]","");
					 System.out.println("Status ="+status);
					 if(status.equals("2"))
					 {
					 	stats=2;
					 	flag=true;
						 System.out.println("-----------------Department Details -------------"+Associate_status); %>
						<td id="fnt">Department <label style="color: red;"><b> * </b></label></td>
						 <td>
						 <select name="depart"  data-live-search="true" data-live-search-style="startsWith" class="selectpicker bd"  data-size="10" onchange="reLoad()">
						 	<option value="">Select Department</option>
						 	<option value="ADM" <%if("ADM".equals(departmentName)){ %>selected<%} %>>ADM</option>
						 	<option value="ACA" <%if("ACA".equals(departmentName)){ %>selected<%} %>>ACA</option>
						 </select></td>
					 <%} 
					 else if(status.equals("1"))
					 {
					 	flag=true;
					 	stats=3;
					 } %>
				<%} %>
			</tr>
			<tr>
				<td id="fnt">Prepare By <label style="color: red;"><b> * </b></label></td>
				<td><select class="form-control bd" id="bd" name="assoc" data-live-search="true" data-live-search-style="startsWith" class="selectpicker bd"  data-size="10">
				<option value="">Select Associate</option>
				<%
					String fac_id="";
					String fName="";
					String mName="";
					String lName="";
					String FullName="";
				for(int f=0;f<fac_det.size();f++){ 
					ArrayList Fac_data=(ArrayList)fac_det.get(f);
					fac_id=""+Fac_data.get(0);
					fName=""+Fac_data.get(1);
					mName=""+Fac_data.get(2);
					lName=""+Fac_data.get(3);
					sh_name=""+Fac_data.get(4);
					FullName=""+fName+" "+mName+" "+lName;
					System.out.println("Faculty name "+FullName);%>
					<option value="<%=fac_id%>" <%if(fac_id.equals(pre_by)){%>selected<%} %>><%=FullName.toString().toUpperCase()%></option>
				<%} %>
				</select>
				</td>
			</tr>
			<tr>
				<td id="fnt">Document Type <label style="color: red;"><b> * </b></label></td>
				<td><select id="bd1" name="f_type" data-live-search="true" data-live-search-style="startsWith" class="selectpicker bd"  data-size="10" onchange="reLoad()">
				<option value="">Select File Type</option>
				<%
				System.out.println("In jsp page "+ar);
				String ref_id="";
				for(int i=0;i<ar.size();i++)
				{
					ArrayList ar2=(ArrayList)ar.get(i);
					ref_id=""+ar2.get(0);
					String f_name1=""+ar2.get(1);
					String f_sh_name1=""+ar2.get(2); 	
			 %>
				<option value="<%=ref_id%>" <%if(ref_id.equals(ref_No)) {%>selected<%} %>><%=f_name1%></option>
				 <%if(ref_id.equals(ref_No)){
					fileDesc=f_name1;
					}%> 
					<%}%></select>
				</td>
			</tr>
			<tr>
				<td id="fnt">Folder Name</td>
				<td><input type="text" class="form-control bd" id="bd2" name="ge_fName" placeholder="Enter Folder Name" onkeypress="return (event.charCode!=39)" required></td>
			</tr>
			<tr>
				<td id="fnt">File Name <label style="color: red;"><b> * </b></label></td>
				<td><input type="text" class="form-control bd" id="bd3" name="ge_fName" placeholder="Enter File Name" onkeypress="return (event.charCode!=39)" required></td>
			</tr>
			<tr>
				<td id="fnt">Description <label style="color: red;"><b> * </b></label></td>
				<td><select name="ge_fName" id="bd4" data-live-search="true" data-live-search-style="startsWith" class="selectpicker bd"  data-size="10">
					<option value="">Select Description</option>
					<option value="<%=fileDesc%>"><%=fileDesc %></option></select></td>
			</tr>
			<tr>
				<td id="fnt">Comments <label style="color: red;"><b> * </b></label></td>
				<td><textarea name="cmnts" id="cmnts" placeholder="Enter Text" class="form-control bd"></textarea></td>
			</tr>
			<tr>
				<%if(subm!=null){ %>
					<td id="fnt">Submitted To <label style="color: red;"><b> * </b></label></td>
				<td><input type="text" class="form-control bd" id="bd5" value="<%=subm%>" name="ge_fName" placeholder="Enter HOD/HOI Short Name" onkeypress="return (event.charCode!=39)" required></td>	
				<%}else { %>
				<td id="fnt">Submitted To <label style="color: red;"><b> * </b></label></td>
				<td><input type="text" class="form-control bd" id="bd5" name="ge_fName" placeholder="Enter HOD/HOI Short Name" onkeypress="return (event.charCode!=39)" required></td>
				<%} %>
			</tr>
			<tr>
				<td id="fnt">Generated By</td>
				<td>
				<%
				String Cr_shName="";
				for(int c=0;c<fac_det.size();c++){ 
					ArrayList cr_by=(ArrayList)fac_det.get(c);
					System.out.println("Created Data by "+cr_by.get(0));
					System.out.println("Equals  "+cr_by.get(0).equals(email));
					if(cr_by.get(0).equals(email))
					{
						Cr_shName=""+cr_by.get(4).toString().toUpperCase();
					}
					
				}%>
				<h5><%=Cr_shName%></h5></td>
				<input type="hidden" value="<%=Cr_shName%>" id="gen_shName" name="ge_fName">	
			</tr>
			<tr>
				<td style="text-align:center; vertical-align:middle;"><input type="button" class="btn" id="button" value="Generate" onclick="Load()"></td>
				<td style="text-align:center; vertical-align:middle;"><input type="reset" class="btn" id="button" value="cancel"></td>
			</tr>
			<tr>
				<td><font size="2"><b> <label style="color: red;"> * </label>Indicates Mandatory</b></font></td>
			</tr>
		</table>
		</div> 
	</div>
</div>
</form>
</BODY>
<%}else{ %>
<%@include file="../cookieTracker/CookieTrackerBottom.jsp" %>
<%} %>
</HTML>