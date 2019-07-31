<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="com.gen.cms.faculty.dao.FacultyDAO"%>
<jsp:useBean id="manager" class="com.gen.cms.admin.dao.AdminManager"
	scope="page"></jsp:useBean>
<jsp:useBean id="admsnMgr" class="com.gen.cms.admission.dao.AdmissionManager"
	scope="page"></jsp:useBean>
<jsp:useBean id="meetMgr"
	class="com.gen.cms.meeting.dao.MeetingManager" scope="session"></jsp:useBean>	
<jsp:useBean id="dateMgr" class="com.gen.cms.util.DateManager"
	scope="session"></jsp:useBean>
<jsp:useBean id="ExMgr" class="com.gen.cms.examination.dao.ExaminationManager"></jsp:useBean>	
<%@ page import="com.gen.cms.faculty.dao.FacultyDAO"%>	
<HTML>
<HEAD>
<%!
	/**
	 * @param str - The string to be tested for null value
	 * @param rStr - The String to be returned if the 1st arguement str is null
	 * @return String - A non null value or a value specified by user
	 */
	public String removeNull(String str,String rStr){
		if(str==null || str.equals("null") || str.equals("")) return rStr;
		return str;
	}
	
	/**
	 * @param str - The string to be tested for null value
	 * @return empty string in case the agruement str is null
	 * @see removeNull(String str, String rStr)
	 */
	public String removeNull(String str){
		return removeNull(str,"");
	}

%>

<meta http-equiv="Content-Language" content="en-us">
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%@ page import="java.util.*"%>
<META http-equiv="Content-Type" content="text/html; charset=windows-1252">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../../stylesheet/main.css" rel="stylesheet" type="text/css">
<title>TimetableUpload1</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR"
	content="Rational® Application Developer for WebSphere® Software">
<%@ include file="../cookieTracker/CookieTrackerTop.jsp" %>
<script type="text/javascript">

	function refreshPage(){
	/*	var b=document.timetableUploading.batch.value;
		document.timetableUploading.bbId.value=b;
		
		var p=document.timetableUploading.prog.value;
		//alert("p="+p);
		document.timetableUploading.ppId.value=p;
		
		var s=document.timetableUploading.sem.value;
		document.timetableUploading.ssId.value=s;*/
		
		document.timetableUploading.action="../../ExaminationLookUp";
		//document.timetableUploading.task.value="loadsem";
		document.timetableUploading.submit();
	
	}



</script>
</head>
<%
	ArrayList prg=new ArrayList();
	FacultyDAO fd=new FacultyDAO();
	String curbatch=manager.getCurrentBatchId();
	String sql4="select prog_mst_id from dir_asn_co where admsn_fac_id='"+email+"' and co_status=1 and batch_mst_id="+curbatch+" and co_acpt_status=2";
	try{
		prg=fd.select(sql4);
	}catch(Exception e){
	e.printStackTrace();
	}	
	//System.out.println("Value of prg array="+prg);
	
	ArrayList batchArr=manager.getBatchWithId();
	
	String bid=""+request.getParameter("bbId");
	String pid=""+request.getParameter("ppId");
	String sid=""+request.getParameter("ssId");

	//System.out.println("Value of prog="+pid);
	int noOfSem=manager.getNoOfSemFor(pid);
	String msg=""+request.getParameter("msg");
	
	
	System.out.println("Value of email="+email);
	
	//To get All uploaded timetable 
	ArrayList CourseOutlineArr=new ArrayList();
	//ExMgr.getAllUploadedCourseOutline(pid,sid,bid);
	
 %>


<body>
	<form name="timetableUploading" method="post" action="">
	<input type="hidden" name="task" value="loadsemForCourseOutline">
	
		<div>
		<table>
			<tr>
				<td><b><font size="2" color="red">Pedagogy Courseoutline Distribution Master</font></b></td>
			</tr>
		</table>
		<hr>
			<table width="377">
				<tr>
					<td width="77"><b>Dept/Unit</b></td>
					<td width="302">
						<select name="prog" tabindex="1" onchange="refreshPage()">
							<option value="0">Select Dept</option>
							<%
								for(int i=0;i<prg.size();i++){
									ArrayList prg1=(ArrayList)prg.get(i);
									String pId=""+prg1.get(0);
									String pName=manager.getProgramName(pId);
							 %>
							 <option value="<%=pId%>" <%if(pId.equals(pid)){%>selected<%}%>><%=pName%></option>
							<%}%>
						</select>
					</td>
				</tr>
				<tr>
		<td style="width: 77px" width="77"><b>Sem / Year</b></td>
		<td width="302">
						<select name="sem" tabindex="1" onchange="refreshPage()">
							<option value="0">Select Sem</option>
							<%
								for(int i=1;i<=noOfSem;i++){
								String si=""+i;
								String s=manager.getRomanSem(i);
									
							 %>
							 <option value="<%=i%>" <%if(si.equals(sid)){%>selected<%}%>><%=s%></option>
							<%}%>
						</select>
					</td>
				</tr>
				<tr>
					<td width="77"><b>Select Batch</b></td>
					<td width="302">
						<select name="batch" tabindex="1" onchange="refreshPage()">
						<option value="0">Select Batch</option>
						<%
							for(int i=0;i<batchArr.size();i++){
								ArrayList batchArr1=(ArrayList)batchArr.get(i);
								String batchId=""+batchArr1.get(0);
								String batch=""+batchArr1.get(1);
								int batch1=Integer.parseInt(batch);
						 %>
							<option value="<%=batchId%>" <%if(batchId.equals(bid)){%>selected<%}%>><%=batch%>-<%=batch1+1%></option>
						<%}%>
						</select>
					</td>
				
				</tr>
				
			</table>
		
		</div>



	</form>
	
	<form name="frm" method="post" enctype="multipart/form-data" action="../../CmsController">
	<input type=hidden name="moduleIndicator" value="Examination"> 
	<input type=hidden name="beanIndicator" value="pedagogyCourseOutline">
	<input type="hidden" name="task" value="add">
	<input type="hidden" name="bbId" value="<%=bid%>">
	<input type="hidden" name="ppId" value="<%=pid%>">
	<input type="hidden" name="ssId" value="<%=sid%>">
	<input type="hidden" name="staffId" value="<%=email%>">	
	<table width="520" height="103">
		<tr>
		<td style="width: 81px" valign="top" width="81"><b>Description</b></td>
		<td width="433"><textarea name="desc" cols="53" rows="3"></textarea></td>
		</tr>
				<tr>
				<td width="81"></td>
					<td width="433"><input type="file" name="fname"
			style="width: 373px"></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Submit"></td>
				</tr>
	
	</table>
	<br>
	<table>
		<tr>
			<td><b><font size="2"><%=removeNull(msg)%></font></b></td>
		</tr>
	</table>
	</form>
	<form action="">
	<div>
		<table>
			<tr>
				<td><b><font color="red" size="2"><u>List of Uploaded Courseoutline</u> : </font></b></td>
			</tr>
		</table>
		<br>
		<table border="1" style="border-collapse:collapse" width="604">
			<tr>
				<th width="28" style="width: 28px">Sl.No</th>
		<th style="width: 161px" width="161">File Name</th>
		<th width="149" style="width: 149px">Description</th>
				<th width="246">Uploaded By</th>
			</tr>
			<%
				for(int i=0;i<CourseOutlineArr.size();i++){
					ArrayList timeArr1=(ArrayList)CourseOutlineArr.get(i);
					String filename=""+timeArr1.get(3);
					String desc=""+timeArr1.get(4);
					String by=""+timeArr1.get(6);
					String byDate=""+timeArr1.get(7);
					String byTime=""+timeArr1.get(8);
					
			 %>
			 <tr>
			 	<td align="center" width="28"><b><%=i+1%></b></td>
			 	<td width="161"><a target="_blank" href="../../pedagogy/courseoutline/<%=bid%>/<%=pid%>/<%=sid%>/<%=filename%>"><%=filename%></a></td>
			 	<td align="center" width="149"><b><%=desc%></b></td>
			 	<td align="center" width="246"><b><%=admsnMgr.getFacultyShNm(by)%> on <%=dateMgr.format(byDate,"dd/MM/yyyy")%> @ <%=byTime%> Hrs.</b></td>
			 </tr>
			 
			<%}%>
		</table>
	
	
	</div>
	</form>
	
	
	

</body>
<%}else{%>
<%@ include file="../cookieTracker/CookieTrackerBottom.jsp"%>
<%}%>
</html>