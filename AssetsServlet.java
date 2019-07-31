package com.gen.cms.assets.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadFile;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gen.cms.admin.dao.AdminManager;
import com.gen.cms.exceptions.CmsGeneralException;
import com.gen.cms.exceptions.CmsNamingException;
import com.gen.cms.exceptions.CmsSQLException;
import com.gen.cms.util.DateManager;
import com.oreilly.servlet.MultipartRequest;

import org.apache.commons.io.FileUtils;
import java.util.*;


/**
 * Servlet implementation class AssetsServlet
 */
public class AssetsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//String action="";
	private static String action = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	String str[]={"","category_master","sub_category_master","AddAssets","UpdateAssets","UpdateAssetsReg","ScrapSale",
			"AssetsTransfer","CreateNewFile","GenerateRefNo","AuditCreation","DoanloadFile"};
	String task[]={"","Add","Update","delete"};
	ArrayList<String> ar=new ArrayList<String>();
    public AssetsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<FileItem> multiparts=null;
		if(ServletFileUpload.isMultipartContent(request))
		{
			System.out.println("In Action Button");
			ar.add("GenerateRefNo");
			ar.add("PersonalFile");
			ServletFileUpload sf=new ServletFileUpload(new DiskFileItemFactory());
			try {
				 multiparts=sf.parseRequest(request);
				 Dictionary dict12=new Hashtable();
				 for(FileItem file : multiparts)
					{
					 	System.out.println("InSide Data");
					 	String desc = file.getString();
			             int f=ar.indexOf(desc);
			             switch(f)
			             {
			             case 0:
							 UploadRefFile(request, response, multiparts);
			            	 break;
			             case 1:
			            	 UploadPersonalFile(request, response, multiparts);
			            	 break;
			             default:
			            	System.out.println("Not a Valid Option");
			            	break;
			             }
					}
				 System.out.println("Dictionary val "+dict12); 
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			action = request.getParameter("Task");
			//Suraj code
			/*MultipartFormDataRequest mRequest = null;
			mRequest =(MultipartFormDataRequest) request.getSession().getAttribute("multipartrequest");
			if (mRequest != null) {
				//check that the requested action is uploading or other 
				action = mRequest.getParameter("task");
				System.out.println("Value of task 1="+action);
			} else {
				action = request.getParameter("task");
				System.out.println("Value of task 2="+action);
			}
			System.out.println("Value of task="+action);*/
		}
		int taskid=getTaskId(action);
		switch(taskid)
		{
		case 1:
			performAddOperation(request, response);
			break;
		case 2:
			performUpdateOperation(request, response);
			break;
		case 3: 
			performDeleteOperation(request, response);
			break;
		default:
			System.out.println("Invalid task");
		}
	}
	private int getActionId(String key)
	{
		int id=0;
		for(int i=1;i<str.length;i++)
		{
			if(key.equals(str[i]))
			{
				
				id=i;
				break;
			}
			
		}

		return id;
		
	}
	private int getTaskId(String key)
	{
		int id=0;
		for(int i=1;i<task.length;i++)
		{
			if(key.equals(task[i]))
			{
				System.out.println("inside Get action id if condition");
				id=i;
				break;
			}
			System.out.println("Out of is condition in get action");
		}
		return id;
	}
	private void performAddOperation(HttpServletRequest request, HttpServletResponse response)
	{
		String Action=request.getParameter("ActionId");
		/*MultipartFormDataRequest mRequest = null;
		mRequest =
			(MultipartFormDataRequest) request.getSession().getAttribute(
				"multipartrequest");	
		if (mRequest != null) {
			//check that the requested action is uploading or other 
			action = mRequest.getParameter("Task");
		} else {
			action = request.getParameter("Task");
		}*/
		
		System.out.println("In Action Button");
		int id=getActionId(Action);
		try{
			
		switch(id)
		{
		case 1:
			AddMainAssetCategory(request, response);
			break;
		case 2:
			AddAssetsSubCategory(request, response);
			break;
		case 3:
			AddAssetsDetails(request, response);
			break;
		case 4:
			UpdateAssetsDetails(request, response);
			break;
		case 5:
			UpdateAssetsRegister(request, response);
			break;
		case 6:
			ScrapSaleAsset(request, response);
			break;
		case 7:
			AssetsTransfer(request, response);
			break;
		case 8:
			CreateNewFile(request, response);
			break;
		case 9:
			GenerateRefNo(request, response);
			break;
		case 10:
			AuditCreation(request, response);
			break;
		case 11:
			DoanloadFile(request, response);
			break;
		default:
			System.out.println("Invalid option");
			break;
		}
		}
		catch(Exception e)
		{
			System.out.println("Error in add operations ");
			e.printStackTrace();
		}
	}
	
	private void DoanloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		System.out.println("In download");
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		String op=request.getParameter("op");
		String filepath="";
		if(op.equals("2"))
		{
			filepath=request.getParameter("PathDocs");
		}
		else if(op.equals("1"))
		{
			filepath = request.getParameter("path");   
		}
		response.setContentType("APPLICATION/OCTET-STREAM");
		PrintWriter out1 = response.getWriter();
		String filename = request.getParameter("FileName"); 
		String fileData=filepath +"\\"+ filename;
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
	    FileInputStream fl = new FileInputStream(filepath +"\\"+ filename);
	    int i;
	    while ((i = fl.read()) != -1) 
	    {
	        out1.write(i);
	    }
	    fl.close();
	    out.close();
	}
	
	private void UploadPersonalFile(HttpServletRequest request, HttpServletResponse response,List<FileItem> request1) throws IOException 
	{
		System.out.println("Upload Personal File");
		HttpSession session=request.getSession();
		Dictionary dict=new Hashtable();
		ReferenceNo_DBQuery ref=new ReferenceNo_DBQuery();
		for(FileItem file : request1)
		{
		 	String fieldName = file.getFieldName();
		 	String desc = file.getString();
            int f=ar.indexOf(desc);
            dict.put(fieldName, desc);
		}
	 	ArrayList depart=ref.DepartmentUnit(dict.get("Associate").toString());
		String deprmt="";
		for(int i=0;i<depart.size();i++)
		{
			ArrayList ar=(ArrayList)depart.get(i);
			deprmt=""+ar.get(2);
			dict.put("departShName", deprmt);
		}
		String destinationPath = null;
		try {
			String refer=dict.get("referenceNumber").toString().replace("/", "_");
			destinationPath=upLoadCourseOutlineForPedagogy(request, dict, refer, 2);
			UploadBean uploadBean = new UploadBean();
			uploadBean.setFolderstore(destinationPath);
			dict.put("ServerPath", destinationPath);
			for(FileItem item : request1){
				if(!item.isFormField())
				{
					String name = new File(item.getName()).getName();
					dict.put("FilName", name);
		            item.write( new File(destinationPath+ File.separator + name));
				}
	         }
		}catch(Exception e)
		{
			System.out.println("Exception "+e);
		}
		boolean ar=false;
		ar=ref.inserPersonalFileDetails(dict);
		if(ar)
		{
			session.setAttribute("msg", "Personal File Uploaded Sucessfully");
		}
		else
		{
			session.setAttribute("msg", "Unable to upload personal file, please try again");
		}
		response.sendRedirect("jsp/referencenumber/Reference_Register.jsp");
	}
	
	/*private void UploadPersonalFile(HttpServletRequest request, HttpServletResponse response, MultipartRequest mRequest) throws IOException 
	{
		System.out.println("In Upload File");
		String unit=mRequest.getParameter("Units");
		String depar=mRequest.getParameter("departShName");
		String Assoc=mRequest.getParameter("Associate");
		String createdBy=mRequest.getParameter("created_by");
		String fileName=mRequest.getParameter("tempload");
		String status=mRequest.getParameter("status");
		System.out.println("Unit "+unit+" Department "+depar+" Associate "+Assoc+" createdBy "+createdBy+" \n FileName "+fileName+" Status "+status);
	}*/
	
	private void UploadRefFile(HttpServletRequest request, HttpServletResponse response,List<FileItem> request1) throws IOException 
	{
		System.out.println("Generate Reference Number ");
		HttpSession session=request.getSession();
		Dictionary dict=new Hashtable();
		ReferenceNo_DBQuery ref=new ReferenceNo_DBQuery();
		for(FileItem file : request1)
		{
		 	String fieldName = file.getFieldName();
		 	String desc = file.getString();
            dict.put(fieldName, desc);
		}
		 System.out.println("Dictionary Value "+dict);
		 String fileName1="";
		String msg1 = "";
		boolean status1 = false;
		String destinationPath = null;
		try {
			String refer=dict.get("ref").toString().replace("/", "_");
			destinationPath=upLoadCourseOutlineForPedagogy(request, dict, refer, 1);
			UploadBean uploadBean = new UploadBean();
			uploadBean.setFolderstore(destinationPath);
			dict.put("ServerPath", destinationPath);
			for(FileItem item : request1){
				if(!item.isFormField())
				{
					String name = new File(item.getName()).getName();
					dict.put("FilName", name);
		            item.write( new File(destinationPath+ File.separator + name));
				}
	         }
		}catch(Exception e)
		{
			System.out.println("Exception "+e);
		}
		boolean ar=false;
		ar=ref.updateFilePath1(dict);
		if(ar)
		{
			session.setAttribute("msg", "File Uploaded Sucessfully");
		}
		else
		{
			session.setAttribute("msg", "Unable to upload file, please try again");
		}
		response.sendRedirect("jsp/referencenumber/Reference_Register.jsp");
	}
	
	//File Upload Option
	private String upLoadCourseOutlineForPedagogy(HttpServletRequest req, Dictionary dict, String refer, int a)throws CmsGeneralException 
	{
		System.out.println("Upload File Function");
		try {
			HttpSession session = req.getSession();
			AdminManager admManager = new AdminManager();
			//String fileName = null;
			String realPath = null;
			String destinationPath = null;
			//System.out.println("forFile>>>" + forFile);
			realPath = session.getServletContext().getRealPath("resources");
			if(a==1)
			{
				destinationPath =realPath + "\\Reference_Number\\"+ dict.get("unit") + "\\" + dict.get("depar") + "\\" + refer;
			}
			else if(a==2)
			{
				destinationPath =realPath + "\\ReferenceNumberImpLinkFile\\"+ dict.get("Units") + "\\" + dict.get("departShName") + "\\" + refer;
			}
			req.getSession().removeAttribute("multipartrequest");
			session = null;
			return destinationPath;
		} catch (Exception ex) {
			throw new CmsGeneralException(
				"Problem in setUploadFile function of Examination Servlet :"
					+ ex.getMessage());
		}
	}
	
	
	private void AuditCreation(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		System.out.println("-------Audit Creation-------");
		String option=request.getParameter("op");
		HttpSession session=request.getSession();
		AuditDBQuery aud=new AuditDBQuery();
		int optn=Integer.parseInt(option);
		boolean flag=false;
		switch(optn)
		{
		case 1:
			System.out.println("In Audit Type Creation");
			String auditName=request.getParameter("FileCre");
			String email=request.getParameter("email");
			System.out.println("Audit Type Name "+auditName+" Created By "+email);
			flag=aud.insertNewAuditType(email, auditName);
			if(flag)
			{
				session.setAttribute("error", "New Audit File Is Created");
			}
			else
			{
				session.setAttribute("error", "Sorry Unable To Create File Please Try Again");
			}
			response.sendRedirect("jsp/audit/AuditTypeCreation.jsp");
			break;
			
		}
	}
	
	private void GenerateRefNo(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		System.out.println("Generate Reference Number ");
		String option=request.getParameter("op");
		HttpSession session=request.getSession();
		ReferenceNo_DBQuery ref=new ReferenceNo_DBQuery();
		int num=Integer.parseInt(option);
		switch(num)
		{
		
			case 1:
				String refer12=request.getParameter("referencNum");
				System.out.println("Rerference Number length "+refer12.length()+" Reference number "+refer12);
				session=request.getSession();
				System.out.println("Null Value 2 "+refer12.equals("null"));
				if(refer12.equals("null"))
				{
					int count=1;
					String ref_gen[]=request.getParameterValues("ge_fName");
					String status=request.getParameter("stat");
					System.out.println("Status Found is "+status);
					System.out.println(" as = AMSC and SCET ");
					System.out.println(" sbs = SCOE, BIMD, SCES, SPUC, SCED ");
					String file_id=request.getParameter("f_type");
					String prep_by=request.getParameter("assoc");
					String comments=request.getParameter("cmnts");
					String name[]={"prog_id","unit","department","created_by","file_type","fileType_shName","prep_byName","prep_byShName","folderName","fileName","Description","submitted_to","gen_byShName"};
					Dictionary dict=new Hashtable();
					dict.put("prep_byId", prep_by);
					dict.put("File_TypeID", file_id);
					dict.put("cmnts", comments);
					for(int i=0;i<ref_gen.length;i++)
					{
						dict.put(name[i], ref_gen[i]);
					}
					System.out.println("Department fetched from JSP Page ==== "+dict.get("department"));
					System.out.println("JSP DATA "+dict);
					//
					if(status.isEmpty())
					{
						ArrayList depChange=ref.selectUpdateDepartment(dict.get("prog_id").toString());
						System.out.println("Department Updated "+depChange);
						System.out.println("Department Updated true empty "+depChange.isEmpty());
						if(depChange.isEmpty())
						{
							dict.put("department", dict.get("department").toString());
							System.out.println("Department before 999 == "+dict.get("department"));
						}
						else if(!depChange.isEmpty())
						{
							String empty=depChange.get(0).toString().replace("[", "").replace("]", "");
							System.out.println("Empty value data "+empty);
							dict.put("department", empty);
							System.out.println("Department ASFTER 999 == "+dict.get("department"));	
						}
					}
					
					//Fetch Month and year to identify weather its october 1 
					DateManager dt=new DateManager();
					int day=dt.readDay();
					int month=dt.readMonth();
					int year1=dt.readYear();
					String md=""+year1;
					String modate=""+day+"/"+month;
					String yr=md.substring(2, 4);
					String year="";
					int a=0;
					String dptmnt="";
					int b=0;
					if(modate.equals("1/10"))
					{
						int sum=Integer.parseInt(yr);
						b=sum+1;
						year=""+yr+"-"+b;
						dict.put("month_year", year);
						boolean monthyear=ref.updateYearMonth(year, dict.get("created_by").toString());
					}
					else
					{
						ArrayList moyr=ref.selectMonthYear();
						year=moyr.get(0).toString().replace("[", "").replace("]", "");
						dict.put("month_year", year);
					}
					
					//Fetch the max value from table to generate next reference number
					String ref_no="";
					int k=0;
					int n=1;
					if(status.equals("as"))
					{
						dptmnt=dict.get("department").toString();
						ArrayList prog=ref.MaxNumberDepartmentWise(dict.get("prog_id").toString(), dict.get("department").toString());
						String str=prog.get(0).toString().replace("[", "").replace("]", "");
						if(str.equals("null"))
						{
							ref_no=String.format("%03d", n);
						}
						else
						{
							k=Integer.parseInt(str);
							n=k+1;
							ref_no=String.format("%03d", n);
						}
						dict.put("numb", n);
						dict.put("ref_number", ref_no);
						System.out.println("Before Update "+dict.get("ref_number"));
					}
					else if(status.equals("sbs"))
					{
						dptmnt=dict.get("department").toString();
						ArrayList prog=ref.MaxNumberDepartmentWise(dict.get("prog_id").toString(), dict.get("department").toString());
						String str=prog.get(0).toString().replace("[", "").replace("]", "");
						if(str.equals("null"))
						{
							ref_no=String.format("%03d", n);
						}
						else
						{
							k=Integer.parseInt(str);
							n=k+1;
							ref_no=String.format("%03d", n);
						}
						dict.put("numb", n);
						dict.put("ref_number", ref_no);
						System.out.println("Before Update "+dict.get("ref_number"));
					}
					else
					{
						ArrayList prog=ref.MaxNumber(dict.get("prog_id").toString());
						String str=prog.get(0).toString().replace("[", "").replace("]", "");
						if(str.equals("null"))
						{
							ref_no=String.format("%03d", n);
						}
						else
						{
							k=Integer.parseInt(str);
							n=k+1;
							ref_no=String.format("%03d", n);
						}
						dict.put("numb", n);
						dict.put("ref_number", ref_no);
						System.out.println("Before Update "+dict.get("ref_number"));
					}
					//This code check weather particular department has reach to 999 reference number if it is than chang department eq. EEC to EEC2
					int total_ref=Integer.parseInt(ref_no);
					
					if(total_ref>=999)
					{
						System.out.println("--------REference number Greater than 999---------- ");
						String dep1=""+dict.get("department");
						dep1=dep1+"2";
						String ref_no1="";
						boolean updt=ref.updateRefNumb(dict.get("prog_id").toString());
						System.out.println("Update Result found of numb to 1  "+updt);
						if(updt==true)
						{
							ArrayList prog1=ref.MaxNumber(dict.get("prog_id").toString());
							System.out.println("MAx number after 999 "+prog1);
							String str1=prog1.get(0).toString().replace("[", "").replace("]", "");
							dict.put("numb", str1);
							k=Integer.parseInt(str1);
							ref_no=String.format("%03d", k);
						}
						boolean updep=ref.InsertDepartment(dict.get("prog_id").toString(),dep1);
						System.out.println("Insert Department "+updep);
						dict.put("department", dep1);
						dict.put("ref_number", ref_no);
						System.out.println("After Update "+dict.get("ref_number"));
						System.out.println("Department update "+dict.get("department"));
					}
					
					//Ex.: SGCS/ADM/MIN-015/09-10
					String ref_number_gen=""+dict.get("unit").toString()+"/"+dict.get("department")+"/"+dict.get("fileType_shName").toString()+"-"+dict.get("ref_number").toString()+"/"+dict.get("month_year").toString();
					System.out.println("Final Refernce number "+ref_number_gen);
					dict.put("ref_gene_no", ref_number_gen);
					count+=1;
					System.out.println("Newly Enter Department "+dptmnt);
					System.out.println(".......Before Working.......");
					boolean bol=ref.insertNewReference(dict, dptmnt);
					System.out.println(".......Working.......");
					System.out.println("Reference deatils inserted "+bol);
					if(bol)
					{
						System.out.println("Inserted Sucessfully ");
						session.setAttribute("error", "Reference Number Generated Sucessfully");
						session.setAttribute("name", dict.get("prep_byName"));
						session.setAttribute("ref_no", ref_number_gen);
						session.setAttribute("file_type", dict.get("file_type"));
						response.sendRedirect("jsp/referencenumber/Gen_reference_number.jsp");
					}
					else
					{
						System.out.println("failed To insert ");
						session.setAttribute("error", "Faild To Generate an Reference Number Plese Try Again");
						response.sendRedirect("jsp/referencenumber/Gen_reference_number.jsp");
					}
				}
				else if(!refer12.equals("null"))
				{
					System.out.println("Reference Number ");
					String prep_by=request.getParameter("assoc");
					String ref_gen[]=request.getParameterValues("ge_fName");
					String name[]={"prog_id","unit","department","created_by","file_type","fileType_shName","prep_byName","prep_byShName","folderName","fileName","Description","submitted_to","gen_byShName"};
					Dictionary dict=new Hashtable();
					for(int i=0;i<ref_gen.length;i++)
					{
						dict.put(name[i], ref_gen[i]);
					}
					dict.put("prepById", prep_by);
					System.out.println("JSP DATA "+dict);
					boolean bol=ref.updateReferenceNumber(dict, refer12);
					if(bol)
					{
						session.setAttribute("error", "Reference Number Details Updated");
						session.setAttribute("ref_no", refer12);
						session.setAttribute("name", dict.get("prep_byName"));
						session.setAttribute("file_type", dict.get("file_type"));
						response.sendRedirect("jsp/referencenumber/Gen_reference_number.jsp");
					}
					else
					{
						session.setAttribute("error", "Failed To Updated. Please Try Again");
						response.sendRedirect("jsp/referencenumber/Gen_reference_number.jsp");
					}
				}
			break;
		}
	}
	
	//File Upload
	private String upLoadCourseOutlineForPedagogy(
			HttpServletRequest req,
			MultipartFormDataRequest mrequest, String unit, String depart, String refer
			)
			throws CmsGeneralException {
			System.out.println("Upload File Function");
			try {
				System.out.println("In try function");
				HttpSession session = req.getSession();
				AdminManager admManager = new AdminManager();
				//String fileName = null;
				String realPath = null;
				String destinationPath = null;
				//System.out.println("forFile>>>" + forFile);
				realPath = session.getServletContext().getRealPath("resources");
				System.out.println("Real Path "+realPath);
				destinationPath =realPath + "\\Reference_Number\\"+ unit + "\\" + depart + "\\" + refer;
					
				System.out.println("Desitination File "+destinationPath);
				req.getSession().removeAttribute("multipartrequest");
				session = null;
				mrequest = null;
				return destinationPath;
			} catch (Exception ex) {
				throw new CmsGeneralException(
					"Problem in setUploadFile function of Examination Servlet :"
						+ ex.getMessage());
			}
		}

	//Reference Number Methods
	private void CreateNewFile(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		HttpSession session=request.getSession();
		System.out.println("In Document File Creation");
		ReferenceNo_DBQuery ref=new ReferenceNo_DBQuery();
		String name[]=request.getParameterValues("FileCre");
		String str[]={"created_by","document_name","document_sh_name"};
		Dictionary dict=new Hashtable();
		for(int i=0;i<name.length;i++)
		{
			System.out.println("Value of "+name[i]);
			dict.put(str[i], name[i]);
		}
		System.out.println("Dictionary stored value "+dict);
		int ar=ref.CheckDocuSHName(dict);
		System.out.println("Result "+ar);
		if(ar==1)
		{
			System.out.println("Main name already exists");
			session.setAttribute("error", "Document already exists");
		}
		else if(ar==2)
		{
			System.out.println("Short name already exists");
			session.setAttribute("error", "Document short name already exists");
		}
		else if(ar==0)
		{
			boolean bol=ref.insertNewDocument(dict);
			if(bol==true)
			{
				System.out.println("document file created ");
				session.setAttribute("error", "Document File Is Created Thank You");
			}
			else{
				System.out.println("document file created ");
				session.setAttribute("error", "Document File Is Created Thank You");
			}
		}
		response.sendRedirect("jsp/referencenumber/Add_New_File.jsp");
	}
	//Assets Methods
	private void AssetsTransfer(HttpServletRequest request, HttpServletResponse response) throws CmsSQLException, CmsNamingException, CmsGeneralException, IOException 
	{
		DateManager dm = new DateManager();	
		Assets_DBQueries db=new Assets_DBQueries();
		HttpSession session=request.getSession();
		String str[]={"1","2","3","4","5","6"};
		String str2="";
		String opt=request.getParameter("op");
		System.out.println("String value in fethed from jsp is: "+opt);
		for(String str1: str)
		{
			System.out.println("Value of exists data "+str1);
			System.out.println("Existing value is  treu or false "+str1.equals(opt));
			if(str1.equals(opt))
			{
				System.out.println("In If statement string="+str1+" And else part "+opt);
				switch(Integer.parseInt(opt))
				{
				case 1:
					Dictionary dict=new Hashtable();
					String cur_details[]={"depart","prog_mst_id","unit","created_by"};
					System.out.println("In case 1 Insert method");
					String sub_id[]=request.getParameterValues("catValue");
					String cur_dep[]=request.getParameterValues("Cur_dep");
					String tran_dep_id=request.getParameter("dep_name");
					String main_cat=request.getParameter("main_cat");
					String reason=request.getParameter("reason");
					dict.put("reason", reason);
					System.out.println("Transfer department "+tran_dep_id);
					for(int i=0;i<cur_dep.length;i++)
					{
						System.out.println("Cur Department Information "+cur_dep[i]);
						dict.put(cur_details[i], cur_dep[i]);
					}
					ArrayList tran_dep=db.Asset_transfer(1, dict, tran_dep_id, "");
					dict.put("trans_proc_mst_id", tran_dep_id);
					for(int i=0;i<tran_dep.size();i++)
					{
						ArrayList arr=(ArrayList)tran_dep.get(i);
						dict.put("tran_unit", arr.get(0));
						dict.put("tran_dep", arr.get(1));
					}
					System.out.println("Dictionary value "+dict);
					
					//fetch hod short name
					String cur_proc=dict.get("prog_mst_id").toString();
					ArrayList transfer_dep_hod=db.HODShortNames(tran_dep_id);
					String tran_hod_sh_name=transfer_dep_hod.get(0).toString().replace("[", "").replace("]", "").toUpperCase();
					transfer_dep_hod=db.HODShortNames(cur_proc);
					String cur_hod_sh_name=transfer_dep_hod.get(0).toString().replace("[", "").replace("]", "").toUpperCase();
					System.out.println("Short name "+tran_hod_sh_name+" cur "+cur_hod_sh_name );
					
					dict.put("cur_hod_sh_name", cur_hod_sh_name);
					dict.put("trans_hod_sh_name", tran_hod_sh_name);
					tran_dep=db.Asset_transfer(2, dict, "", "");
					System.out.println("True or false "+tran_dep.get(0).toString().replace("[", "").replace("]", ""));
					System.out.println("True false value "+tran_dep.get(1)+" real true/false "+tran_dep.get(1).equals("true"));
					String req_id=tran_dep.get(0).toString().replace("[", "").replace("]", "");
					dict.put("req_id", req_id);
					
					if(tran_dep.get(1).equals("true"))
					{
						System.out.println("Transfered sucessfully");
						for(int i=0;i<sub_id.length;i++)
						{
							System.out.println("Sub Category ID "+sub_id[i]);
							tran_dep=db.Asset_transfer(3, dict, sub_id[i], "");
							System.out.println("Inserted Sucessfully "+tran_dep.get(0));
							if(tran_dep.get(0).equals("true"))
							{
								System.out.println("Inserted Sucessfully ");
								session.setAttribute("error", "Request Is send HOD/HOI Approvel");
							}
							else
							{
								System.out.println("Failed Inserted  ");
								session.setAttribute("error", "Failed To Send HOD/HOI please try again");
							}	
						}	
					}
					else
					{
						System.out.println("Failed to transfer");
						session.setAttribute("error", "Failed To Send HOD/HOI please try again");
						
					}
					response.sendRedirect("jsp/assetmanagement/Assets_transfer_Form.jsp");
					break;
				
				case 2:
					System.out.println("in case 2");
					str2=request.getParameter("TrStatus");
					String id=request.getParameter("id");
					String msg=request.getParameter("txtMsg");
					String imp=request.getParameter("imp");
					String hod=request.getParameter("hod");
					String bol="";
					System.out.println("Option for jump page "+imp+" tru or false tr_hod "+imp.equals("2")+" cr_hod "+imp.equals("1"));
					System.out.println("Option for hod "+imp+" true or false tr_hod "+hod.equals("2")+" cr_hod "+hod.equals("1"));
					String status="";
					System.out.println("Transfer value "+str2);
					if(str2.equals("0"))
					{
						System.out.println("Approved ");
						status="1";
						System.out.println("Approved "+status);
					}
					else
					{
						status="2";
						System.out.println("cancel "+status);
					}
					if(hod.equals("1"))
					{
						ArrayList fl=db.selectAssetTransfer(4,status,id, msg);
						System.out.println("Array value is "+fl);
						bol=fl.toString().replace("[", "").replace("]", "");
						System.out.println("Updated value are "+bol+" utur/false "+bol.equals("true"));
					}
					else if(hod.equals("2"))
					{
						String fac_names[]=request.getParameterValues("Owner");
						String NewLoc[]=request.getParameterValues("newLoc");
						System.out.println("New Location "+NewLoc);
						String reg_nu[]=request.getParameterValues("reg_no");
						String name_id[]=new String[reg_nu.length];
						System.out.println("Names "+fac_names);
						for(int n=0;n<fac_names.length;n++)
						{
							System.out.println("FAculty names "+fac_names[n]+" register number "+reg_nu[n]+" New Location "+NewLoc[n]);
						}
						ArrayList fl=db.Asset_trans_hod_appr(fac_names,reg_nu,status,id, msg, NewLoc);
						System.out.println("Array value is "+fl);
						bol=fl.toString().replace("[", "").replace("]", "");
						System.out.println("Updated value are "+bol+" utur/false "+bol.equals("true"));
					}
					if(bol.equals("true"))
					{
						System.out.println("Updated Sucessfully");
						session.setAttribute("error", "Updated status ");
					}
					else
					{
						System.out.println("Failed to update ");
						session.setAttribute("error", "Failed To Update ");
					}
					if(imp.equals("1"))
					{
						response.sendRedirect("jsp/assetmanagement/Assets_Transfer_Appr.jsp");
					}
					else if(imp.equals("2"))
					{
						response.sendRedirect("jsp/assetmanagement/Assets_transfer_Hod_appr.jsp");
					}
					break;
					
				case 3:
						String depart1=request.getParameter("reg_id");
						String depart2=request.getParameter("stat");
						System.out.println("Length of array  reg_no "+depart1);
					
						System.out.println("String Value fetched is department "+depart2);

						break;
				case 4:
					System.out.println("Assets Transfer odfa");
					String swap=request.getParameter("swap");
					String tr_hod_sh_name=request.getParameter("tr_sh_name");
					String newR_id=request.getParameter("req_id");
					System.out.println("Request id "+newR_id);
					System.out.println("Swap number "+swap+" tru false "+swap.equals("1"));
					if(swap.equals("1"))
					{
						boolean tf=db.MT_status_update(newR_id);
						if(tf==true)
						{
							System.out.println("Send to Department");
							session.setAttribute("error", "Request has been send to MT");
						}
						else
						{
							System.out.println("Failed to send DEpartment");
							session.setAttribute("error", "Failed To resend");
						}
						response.sendRedirect("jsp/assetmanagement/Assets_Transfer_Status_HOD.jsp");
					}
					else
					{
					ArrayList reg_own=db.selectAssetTransfer(7, "", newR_id, "");
					System.out.println("New Location Data "+reg_own);
					ArrayList depart_det=db.selectAssetTransfer(17, "", newR_id, "");
					ArrayList sql1=new ArrayList();
					boolean bolVal=false;
					String prog_id="";
					String unit="";
					String depa="";
					for(int i=0;i<reg_own.size();i++)
					{
						ArrayList sql=(ArrayList)reg_own.get(i);
					}
					for(int i=0;i<depart_det.size();i++)
					{
						sql1=(ArrayList)depart_det.get(i);
						prog_id=""+sql1.get(2);
						unit=""+sql1.get(0);
						depa=""+sql1.get(1);
					}
					Dictionary dic=new Hashtable();
					String ref_no;
					String r_id;
					ArrayList reg=new ArrayList();
					ArrayList sub_sh_name=new ArrayList();
					ArrayList ref_numb=new ArrayList();
					int n=1;
					Dictionary dict1=new Hashtable();
					int ref_id=1;
					for(int j=0;j<reg_own.size();j++)
					{
						//Fetch sub category Id
						ArrayList sql=(ArrayList)reg_own.get(j);
						ArrayList new_id=db.selectAssetTransfer(9, "", sql.get(0).toString(), "");
						System.out.println("Category Id "+new_id);
						String asse_reg[]={"main_cat1","sub_cat","entry_date","Descrip","bill_no","bill_date","depart","quantityt","amount","main_name","sub_name"};
						for(int h=0;h<new_id.size();h++)
						{
							ArrayList sub_s=(ArrayList)new_id.get(h);
							for(int f=0;f<sub_s.size();f++)
							{
								dict1.put(asse_reg[f],sub_s.get(f));
							}
						}
						String catId=new_id.get(0).toString().replace("[", "").replace("]", "");
						
						//Fetch sub category short name
						ArrayList val=db.updateAssetsRegister(2, dic, dict1.get("sub_cat").toString(), "");
						String short_name=val.get(0).toString().replace("[", "").replace("]", "");	
						dict1.put("short_name", short_name);
						
						//Fetch Max reference number
						ArrayList ref=new ArrayList();
						ArrayList val1=db.updateAssetsRegister(1, dic,  dict1.get("sub_cat").toString(), prog_id);
						String st=val1.get(0).toString().replace("[", "").replace("]", "");
						System.out.println("Max value "+st);
						int k=0;
						if(st.equals("null"))
						{
							r_id=String.format("%03d", n);
						}
						else
						{
							k=Integer.parseInt(st);
							n=k+1;
							r_id=String.format("%03d", n);
						}
						System.out.println("Max value "+r_id);
						String dt=dm.getCurrentDate();
						String bDate1=dm.format(dt,"MM-dd-yyyy");
						String mm=bDate1.substring(0,2);
						String yy=bDate1.substring(8, 10);
						String new_ref_no=""+unit+"/"+depa+"/"+short_name.toLowerCase()+"-"+r_id+"/"+mm+"-"+yy;
						System.out.println("New refernce Number "+new_ref_no);
						bolVal=db.UpdateAssetsRegisterTable(n, new_ref_no, prog_id, depa, unit, sql.get(1).toString(), sql.get(0).toString(), newR_id, dict1, tr_hod_sh_name, sql.get(2).toString());
						System.out.println("Update assets register "+bolVal);
					}
					if(bolVal==true)
					{
						System.out.println("Send to Department");
						session.setAttribute("error", "Assets Transfered Process Sucessfully Completed");
					}
					else
					{
						System.out.println("Failed to send DEpartment");
						session.setAttribute("error", "Assets Transfered Failed Please Try Again");
					}
					response.sendRedirect("jsp/assetmanagement/Assets_Transfer_Status_HOD.jsp");
					}
					break;
				case 5:
					System.out.println("Case 5 Here");
					str2=request.getParameter("TrStatus");
					id=request.getParameter("id");
					msg=request.getParameter("txtMsg");
					System.out.println("Mt status "+str2);
					if(str2.equals("0"))
					{
						System.out.println("Approved ");
						status="1";
						System.out.println("Approved "+status);
					}
					else
					{
						status="3";
						System.out.println("rejected "+status);
					}
					ArrayList fl=db.selectAssetTransfer(15,status,id, msg);
					System.out.println("Array value is "+fl);
					bol=fl.toString().replace("[", "").replace("]", "");
					if(bol.equals("true"))
					{
						System.out.println("Insterted ");
						session.setAttribute("error", "Status Updated Sucessfully Thank You");					
					}
					else
					{
						System.out.println("Reje cted");
						session.setAttribute("error", "Rejected Report Send To Requested Department");
					}
					System.out.println("Updated value are "+bol+" utur/false "+bol.equals("true"));
					response.sendRedirect("jsp/assetmanagement/ManagementAppr.jsp");
					break;
				case 6:
					System.out.println("in case 6");
					boolean bol1=false;
					String fac_names[]=request.getParameterValues("Owner");
					String reg_nu[]=request.getParameterValues("reg_no");
					String name_id[]=new String[reg_nu.length];
					System.out.println("Names "+fac_names);
					for(int n=0;n<fac_names.length;n++)
					{
						System.out.println("FAculty names "+fac_names[n]+" register number "+reg_nu[n]);
					}
					System.out.println("Array value is ");
					boolean f2=db.AssetRegisterNewAssocAssign(fac_names,reg_nu);
					System.out.println("Array value is true of false "+f2);
					
					if(f2==true)
					{
						System.out.println("Updated Sucessfully");
						session.setAttribute("error", "Updated status ");
					}
					else
					{
						System.out.println("Failed to update ");
						session.setAttribute("error", "Failed To Update ");
					}
					response.sendRedirect("jsp/assetmanagement/Assets_HOD_Assign_new_Associate.jsp");
					break;
				default:
					System.out.println("Invalid option");
					break;
				}
			}
			else
			{
				System.out.println("Not exists else part");
			}
		}
		System.out.println("Out of for loop");
		
	}
	
/*	private void UpdateAssetsSS(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Assets_DBQueries db = new Assets_DBQueries();
		HttpSession session=request.getSession();
		String rev=request.getParameter("rev");
		String vendr=request.getParameter("vend");
		String reason1=request.getParameter("reason");
		String file=request.getParameter("fileName");
		String cancel=request.getParameter("cnl");
		String old_reg=request.getParameter("new_id");
		String new_reg=request.getParameter("as_id");
		System.out.println("Revenue "+rev+" Vendor "+vendr+" Reason "+reason1+" uploaded file is "+file+" CAncel "+cancel);
		if(cancel.equals("1"))
		{
			System.out.println("Status is canceld id");

			boolean flag=db.updateAssetsSSStatus(old_reg, new_reg);
			if(flag==true)
			{
				System.out.println("Assets has been inserted");
				session.setAttribute("error", "Assets has been canceled");
			}
			else{
				System.out.println("Assets not cancel");
				session.setAttribute("error", "Assets not canceled");
			}

		}
		else{
		boolean flag=db.updateAssetsSS(rev, vendr, reason1, file,new_reg, old_reg);
		if(flag==true)
		{
			System.out.println("UPdated on databse");
			session.setAttribute("error", "Updated and Send To Hod Hoi for approval");
		}
		else
		{
			System.out.println("faild to update");
			session.setAttribute("error", "Faild to update");
		}
		}
		response.sendRedirect("jsp/assetmanagement/Assets_Sale_Srap_reject_status.jsp");
	}
	*/

	private void ScrapSaleAsset(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String cas=request.getParameter("op");
		System.out.println("Assets Scrap Sale "+cas);
		Assets_DBQueries db=new Assets_DBQueries();
		int num=Integer.parseInt(cas);
		System.out.println("Scrap number "+num);
		switch(num)
		{
		
		case 1:
			System.out.println("in case 1");
/*			String UPLOAD_DIRECTORY="";
			Assets_DBQueries db=new Assets_DBQueries();
			HttpSession session=request.getSession();
			String email=request.getParameter("email");
			String Aname=request.getParameter("aname");
			String reg_id=request.getParameter("reg_id");
			String ARefNo=request.getParameter("ref_no");
			String bDate=request.getParameter("bDate");
			String BNumb=request.getParameter("bNumb");
			String SName=request.getParameter("bNumb");
			String PDate=request.getParameter("bdate");
			String amnt=request.getParameter("amnt");
			String Dep=request.getParameter("dep");
			String unit=request.getParameter("unit");
			String option=request.getParameter("ss");
			String buyer=request.getParameter("vend");
			String rev=request.getParameter("rev");
			String Reason=request.getParameter("reason");
			String proc_ms=request.getParameter("msc_id");
			String file=request.getParameter("fileName");
			String HodShName=request.getParameter("shName");
			System.out.println("BEfore upload file ");
			
			String option1="";
			if(option.equals("1"))
			{
				option1="sale";
			}
			else 
			{
				option1="scrap";
			}
			
			System.out.println("User id "+email+" Register Id "+reg_id+"a name "+Aname+" Ref No "+ARefNo+" date "+bDate+" BNumb "+BNumb+" SName "+SName+" PDate "+PDate+" amnt "+amnt+" Dep " +Dep+" unit "
					+unit+" option "+option1+" rev "+rev+" buyer "+buyer+" Reason "+Reason+" file path :"+file+" Prog msi id "+proc_ms+" Hod Short Name "+HodShName);
			File dir=new File("E:/PESTProject15-3-2018/workspace/pest/WebContent/resources/Assets/"+file);
			if(!dir.exists())
			{
				boolean ff=dir.mkdir();
				System.out.println("Path = "+dir.getPath());
				UPLOAD_DIRECTORY=dir.getPath();
				System.out.println("Created Path of the file"+UPLOAD_DIRECTORY);
				System.out.println("Created "+ff+" Directory created in name of "+dir);
				if(ff)
				{
					System.out.println("Directory created ");
				}
				else
				{
					System.out.println("Not created");
				}
			}
			else
			{
				System.out.println("File already exists created");
			}
			if(ServletFileUpload.isMultipartContent(request)){
		            try {
		                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		               
		                for(FileItem item : multiparts){
		                    if(!item.isFormField()){
		                        String name = new File(item.getName()).getName();
		                        item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
		                    }
		                }
		            
		               //File uploaded successfully
		                System.out.println("File uploaded");   
		            } catch (Exception ex) {
		            	System.out.println("File uploaded faild");             
		            }          
		          
		        }else{
		            System.out.println("Sorry this Servlet only handles file upload request");
		        }
			 DiskFileItemFactory factory = new DiskFileItemFactory();
			    String contextRoot = getServletContext().getRealPath("/");
			    factory.setRepository(new File(contextRoot));
			    ServletFileUpload upload = new ServletFileUpload(factory);
			    try {
			        List<FileItem> items = upload.parseRequest(request);
			        for (FileItem item : items) {
			            if (!item.isFormField()) {
			                // Process form file field (input type="file").
			                System.out.println("Field name: " + item.getFieldName());
			                System.out.println("File name: " + item.getName());
			                System.out.println("File size: " + item.getSize());
			                System.out.println("File type: " + item.getContentType());
	
			                String fileName = item.getName();
			                try {
			                    String uploadFolder = getServletContext().getRealPath("")+ File.separator + DATA_DIRECTORY;
			                    String filePath = uploadFolder + File.separator + fileName;
			                    File saveFile = new File(filePath);                        
			                    saveFile.createNewFile();
			                    item.write(saveFile);                        
	
			                } catch (Exception e) {
			                    // TODO Auto-generated catch block
			                    e.printStackTrace();
			                }
			            }
	
			        }
			    } catch (FileUploadException e) {
			        try {
			        	System.out.println("Cannot parse multiple request file " +e);
			            throw new ServletException("Cannot parse multipart request.", e);
			        } catch (ServletException e1) {
			            // TODO Auto-generated catch block
			        	System.out.println("Cannot  error "+e1);
			            e1.printStackTrace();
			        }
			    } 
	
			boolean flag=db.addSaleSrap(email, reg_id, Aname, ARefNo, bDate, BNumb, SName, PDate, amnt, Dep, unit, option1, rev, buyer, Reason, file, proc_ms, HodShName);
			if(flag==true)
			{
				System.out.println("Data inserted sucessfully of sale srap");
				session.setAttribute("suc", "Assets Scrap/Sale is updated and sent to hod approval");
			}
			else
			{
				System.out.println("Scrap not inserted");
				session.setAttribute("suc", "Sale/Scrap not updated please try again");
			}
			
			response.sendRedirect("jsp/assetmanagement/Assets_Scrap_Sale.jsp");*/
			break; 
		case 2:
			HttpSession session=request.getSession();
			Dictionary dict=new Hashtable();
			System.out.println("In sale scrap assets operation case 2");
			System.out.println("In case 1 inster method");
			String sub_id[]=request.getParameterValues("catValue");
			String main_cat=request.getParameter("main_cat");
			String reason=request.getParameter("reason");
			String prog_id=request.getParameter("prg_id");
			String depart=request.getParameter("depart");
			String unit=request.getParameter("unit");
			String email=request.getParameter("email");
			System.out.println("Main Category ");
			for(int i=0;i<sub_id.length;i++)
			{
				System.out.println("prog id "+sub_id[i]);
			}
			dict.put("main_cat", main_cat);
			dict.put("reason", reason);
			dict.put("prog_mst_id", prog_id);
			dict.put("depart", depart);
			dict.put("unit", unit);
			dict.put("created_by", email);
			System.out.println("prog id"+dict);
			ArrayList transfer_dep_hod=db.HODShortNames(dict.get("prog_mst_id").toString());
			System.out.println("In Hod status "+transfer_dep_hod );
			String cur_hod_sh_name=transfer_dep_hod.get(0).toString().replace("[", "").replace("]", "").toUpperCase();
			dict.put("hod_sh_name", cur_hod_sh_name);
			System.out.println("Short name  cur "+dict);
			ArrayList tran_dep=db.Assets_Sale_scrap_req_id(dict);
			System.out.println("True or false "+tran_dep.get(0).toString().replace("[", "").replace("]", ""));
			System.out.println("True false value "+tran_dep.get(1)+" real true/false "+tran_dep.get(1).equals("true"));
			String req_id=tran_dep.get(0).toString().replace("[", "").replace("]", "");
			dict.put("req_id", req_id);
				
			if(tran_dep.get(1).equals("true"))
			{
				System.out.println("Scrap sucessfully");
				for(int i=0;i<sub_id.length;i++)
				{
					System.out.println("Sub Category ID "+sub_id[i]);
					boolean bol=db.Assets_Sale_scrap_Data(dict, sub_id[i]);
					System.out.println("Inserted Sucessfully "+tran_dep.get(0));
					if(bol==true)
					{
						System.out.println("Inserted Sucessfully ");
						session.setAttribute("error", "Request is send HOD/HOI Approvel");
					}
					else
					{
						System.out.println("Failed Inserted  ");
						session.setAttribute("error", "Failed To Send HOD/HOI please try again");
					}	
				}	
			}
			else
			{
				System.out.println("Failed to transfer");
				session.setAttribute("error", "Failed To Send HOD/HOI please try again");
				
			}
			response.sendRedirect("jsp/assetmanagement/Assets_Scrap_Sale.jsp");
			break;
		case 3:
			System.out.println("Assets Scrap case 3");
			session=request.getSession();
		
				System.out.println("Else Condition");
				String comment=request.getParameter("txtMsg");
				String status=request.getParameter("status123");
				req_id=request.getParameter("reg_id");
				String user_id=request.getParameter("email");
				String stat="";
				System.out.println("Status value received is "+status);
				if(status.equals("1"))
				{
					stat="1";
					System.out.println("Status is approve "+stat);
				}
				else{
					stat="2";
					System.out.println("Rejected status  "+stat);
					
				}
				System.out.println("Comment value "+comment);
				boolean flag2=db.updateAssetsSSStatus(req_id, stat, comment);
				System.out.println("Status updated with sale scrap id "+flag2 );
				if(flag2==true)
				{
					System.out.println("Status updated sucessfully "+flag2);
					session.setAttribute("error", " Assets Sale/Scrap Status updated");
				}
				else
				{
					System.out.println("Faild status");
					session.setAttribute("error", "failed to update status");
				}
				response.sendRedirect("jsp/assetmanagement/Assets_HOD_Sale_Scrap.jsp");	
			break;
		case 4:
			System.out.println("In If Condition 4");
			session=request.getSession();
			String requ_id=request.getParameter("req_id");
			String optn=request.getParameter("option");
			System.out.println("Option "+optn);
			if(optn.equals("1"))
			{
				System.out.println("In if ");
				String reqst_id=request.getParameter("req_id");
				System.out.println("REquest id found "+reqst_id);
				ArrayList al=db.UpdateAssetRegSaleS(reqst_id);
				System.out.println("Register nummber to scrap is "+al);
				for(int i=0;i<al.size();i++)
				{
					int j=0;
					ArrayList arr=(ArrayList)al.get(i);
					while(j<arr.size())
					{
						String Asset_reg=arr.get(0).toString().replace("[", "").replace("]", "");
						System.out.println("Register number "+Asset_reg);
						boolean b=db.SaleScrapFinal(Asset_reg, reqst_id);
						System.out.println("True False "+b);
						j=j+1;
					}
				}
				response.sendRedirect("jsp/assetmanagement/Assets_Sale_Srap_reject_status.jsp");
			}
			else if(optn.equals("2"))
			{
				System.out.println("In if else ");
				String stata="0";
				boolean upd=db.updateAssetsSSStatusUp(requ_id, stata, "");
				if(upd==true)
				{
					System.out.println("Status updated sucessfully "+upd);
					session.setAttribute("error", " Assets Sale/Scrap Status updated Send HOD approval");
				}
				else
				{
					System.out.println("Faild status");
					session.setAttribute("error", "failed to update status Try again");
				}
				response.sendRedirect("jsp/assetmanagement/Assets_Sale_Srap_reject_status.jsp");
			}
			else if(optn.equals("3"))
			{
				System.out.println("Else f");
				String reqst_id=request.getParameter("req_id");
				System.out.println("REquest id found "+reqst_id);
				System.out.println("In if else ");
				String stata="0";
				boolean upd=db.updateAssetsSSStatusMTUPUp(requ_id, stata, "");
				if(upd==true)
				{
					System.out.println("Status updated sucessfully "+upd);
					session.setAttribute("error", " Assets Sale/Scrap Status updated Send Mt approval");
				}
				else
				{
					System.out.println("Faild status");
					session.setAttribute("error", "failed to update status Try again");
				}
				response.sendRedirect("jsp/assetmanagement/Assets_Sale_Srap_reject_status.jsp");
			}
			break;
			
		case 5:
			System.out.println("Assets Scrap case 5");
			session=request.getSession();
			comment=request.getParameter("txtMsg");
			status=request.getParameter("status123");
			req_id=request.getParameter("reg_id");
			user_id=request.getParameter("email");
			stat="";
			System.out.println("Status value received is "+status);
			if(status.equals("1"))
			{
				stat="1";
				System.out.println("Status is approve "+stat);
			}
			else
			{
				stat="2";
				System.out.println("Rejected status  "+stat);
			}
			System.out.println("Comment value "+comment);
			flag2=db.updateAssetsSSStatusMT(req_id, stat, comment);
			System.out.println("Status updated with sale scrap id "+flag2 );
			if(flag2==true)
			{
				System.out.println("Status updated sucessfully "+flag2);
				session.setAttribute("error", " Assets Sale/Scrap Status updated");
			}
			else
			{
				System.out.println("Faild status");
				session.setAttribute("error", "failed to Send Report To Associate");
			}
			response.sendRedirect("jsp/assetmanagement/MT_sale_Scrap.jsp");	
			break;
			
			default :
				System.out.println("Invalid Option");
				break;
		}
	}

	
	private void UpdateAssetsRegister(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Assets_DBQueries ad=new Assets_DBQueries();
		HttpSession session=request.getSession();
		System.out.println("In Update assets register");
		String descrp=request.getParameter("des");
		String Bill_no=request.getParameter("bill_no");
		String Supplier_name=request.getParameter("sup_name");
		String Quantity=request.getParameter("quant");
		String amount=request.getParameter("amnt");
		String as_id=request.getParameter("as_id");
		String cancel=request.getParameter("cnl");
		System.out.println("cancelation "+cancel.equals("1"));
		System.out.println("Description "+descrp);
		System.out.println("bill number " +Bill_no);
		System.out.println("Supplier " +Supplier_name);
		System.out.println("Quantity " +Quantity);
		System.out.println("amount "+amount);
		System.out.println("Assets_id "+as_id);
		
		
		if(cancel.equals("1"))
		{
			System.out.println("Status is canceld id");

			boolean flag=ad.updateAssetsStatus(as_id);
			if(flag==true)
			{
				System.out.println("Assets has been inserted");
				session.setAttribute("msg", "Assets has been canceled");
			}
			else{
				System.out.println("Assets not cancel");
				session.setAttribute("msg", "Assets not canceled");
			}

		}
		else{
		boolean flag=ad.updateAssetsReg(descrp, Bill_no, Supplier_name, Quantity, amount,as_id);
		System.out.println("update status "+flag);
		if(flag==true)
		{
			System.out.println("Updates Sucessfully");
			session.setAttribute("msg", "Update sucessfully please wait while HOD/HOI Response back");
		}
		else
		{
			session.setAttribute("msg", "Update faild please try again");
			System.out.println("Update faild");
		}
		}
		response.sendRedirect("jsp/assetmanagement/Assets_Rejected_Status_Report.jsp");
	}

	private void AddMainAssetCategory(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		System.out.println("In Servlet dopost AsssetsResgister Class");
		String user_id=request.getParameter("user_id");
		System.out.print("USer id is "+user_id+" \n");
		String cat=request.getParameter("txtCata");
		String CatShort=request.getParameter("txtShort");

		System.out.println("Enter Register Catagory : "+cat);
		System.out.println("Short form : "+CatShort);
		AssetsMasterVal am=new AssetsMasterVal();	
		HttpSession session=request.getSession();
		am.setUser_id(user_id);
		am.setCate(cat.toUpperCase());
		am.setShCat(CatShort.toUpperCase());
		ArrayList st=new ArrayList();
		st=Assets_DBQueries.selectMainCat1();
		System.out.println("Data is : "+st);
		ArrayList arr=new ArrayList();
		ArrayList resultSet = null;
		resultSet=Assets_DBQueries.selectMainCat1();
		arr=Assets_DBQueries.checkForExistingCategory(am);
		System.out.println("Array List value = "+arr);
		System.out.println("main Category "+am.getCate());
		if(!arr.isEmpty())
		{
	
				for(int i=0;i<st.size();i++)
				{
					ArrayList arr1=(ArrayList)st.get(i);
					System.out.println("main category "+arr1.get(1).equals(am.getCate()));
					if (arr1.get(1).equals(am.getCate()))
					{
						session.setAttribute("error", "Catagory Name Already Exists "+cat);
						System.out.println("main category 1  "+arr1.get(1).equals(am.getCate()));
					}
					else if(arr1.get(2).equals(am.getShCat()))
					{
						System.out.println("Exist.. = " +arr1.get(2).equals(am.getShCat()));
						session.setAttribute("error", "Catagory Short Name Already Exists "+CatShort);
					}
				}
		}
		else
		{
			System.out.println("Data Does not exists"+arr.equals(cat)+" and "+arr.equals(CatShort));
			boolean flag=Assets_DBQueries.addAssets(am);
			if (flag == false)   
			{
				session.setAttribute("error","Catagory not Inserted");
				System.out.println("Value not insterted");
			}
			else{
				session.setAttribute("error","Catagory inserted sucessefull");
				System.out.println("Inserted Sucessfully");
			}
		}
		response.sendRedirect("jsp/assetmanagement/MasterCreation.jsp");
	}
	
	
	private void AddAssetsSubCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, CmsSQLException, CmsGeneralException
	{
		String ar=request.getParameter("main_cat");
		String user_id=request.getParameter("user_id");
		String assets_sub1 = request.getParameter("txtSubCata");
		String assets_short1=request.getParameter("txtSubShort");
		String assets_sub=assets_sub1.toUpperCase();
		String assets_short=assets_short1.toUpperCase();
		Assets_DBQueries ad=new Assets_DBQueries();
		Sub_Cat_GS sc=new Sub_Cat_GS();
		HttpSession session=request.getSession();
		
		sc.setMain_cat(ar);
		sc.setSub_cat(assets_sub);
		sc.setSub_cat_short(assets_short);
		sc.setUser_id(user_id);
		System.out.println("Values of Category main_id = "+sc.getMain_cat()+" sub_name = "+sc.getSub_cat()+" sub_short = "+sc.getSub_cat_short());
		ArrayList arr=new ArrayList();
		ArrayList resultSet=new ArrayList();
		resultSet=Assets_DBQueries.select_sub_cat1();
		arr=Assets_DBQueries.checkForExistingSubCategory(sc);
		System.out.println("Array List value of "+arr);
		System.out.println("Array List value = "+arr);
		if(!arr.isEmpty())
		{
			for(int i=0;i<resultSet.size();i++)
				{
						ArrayList arr1=(ArrayList)resultSet.get(i);
						System.out.println("Fetched sub cat "+arr1.get(2));
						System.out.println("Fetched sub cat "+arr1.get(3));
						System.out.println("Fetched from get and set cat "+sc.getSub_cat());
						System.out.println("main category "+arr1.get(1).equals(sc.getSub_cat()));
						if (arr1.get(2).equals(sc.getSub_cat()))
						{
							session.setAttribute("error", " Sub Catagory Name Already Exists "+assets_sub);
							System.out.println("main category "+arr1.get(1).equals(sc.getSub_cat()));
						}
						else if(arr1.get(3).equals(sc.getSub_cat_short()))
						{
							System.out.println("Exist.. = " +arr1.get(2).equals(sc.getSub_cat_short()));
							session.setAttribute("error", " Sub Catagory Short Name Already Exists "+assets_short);
						}
					}
			}			
			else
			{
				boolean flag=Assets_DBQueries.addSubAssets(sc);
				if(flag == true)
				{
					session.setAttribute("error", "Value Is inserted sucessfully");
				}
				else
				{
					session.setAttribute("error", "Value Not Inserted");
				}
			}
			
		
		response.sendRedirect("jsp/assetmanagement/Assets_Sub_Category.jsp");
		
	}
	
	private void AddAssetsDetails(HttpServletRequest request, HttpServletResponse response) throws IOException, CmsGeneralException, CmsSQLException, SQLException
	{
		System.out.println("Add New Assets Details");
		String UPLOAD_DIRECTORY="";
		DateManager dm = new DateManager();	
		HttpSession session=request.getSession();
		String user_name=request.getParameter("user_id");
		String main_cat=request.getParameter("main_cat");
		String sub_cat=request.getParameter("sub_cat");
		String edate=request.getParameter("entry_date");
		String descr=request.getParameter("desc");
		String bil_no=request.getParameter("Bill_no");
		String bdate=request.getParameter("bill_date");
		String supl=request.getParameter("supply_name");
		String recp_amnt=request.getParameter("amount");
		String Sngl_amnt[]=request.getParameterValues("amnt");
		String location=request.getParameter("Loc");
		String unit=request.getParameter("unit");
		String depId=request.getParameter("dep");
		String DepName=request.getParameter("DepName");
		String UserId=request.getParameter("asso_name");
		String sup_number=request.getParameter("m_no");
		String warranty=request.getParameter("warranty");
		String Bimage=request.getParameter("bill_img");
		System.out.println("Image file "+Bimage);
		
		File image= new File(Bimage);
		System.out.println("Image Load "+image);
		
		String recp_quant=""+Sngl_amnt.length;
		System.out.println("email "+user_name+" Main category "+main_cat+" SubCategory "+sub_cat+" Entry Date "+edate+" description "+descr+
				" bill Number "+bil_no+" Bill date "+bdate+" supplier "+supl+" REcpt quant "+recp_quant+" recp_amnt "+recp_amnt+" Sngl_amnt "+Sngl_amnt+
				" location "+location+" Unit "+unit+" Department Id "+depId+" User ID "+UserId+" Warranty "+warranty+" Supplier Number "+sup_number);
		System.out.println("Sub category name "+sub_cat);
		
		AssetsMasterVal av=new AssetsMasterVal();
		av.setMain_cat(main_cat);
		av.setSub_cat(sub_cat);
		av.seteDate(edate);
		av.setDescr(descr);
		av.setBill_no(bil_no);
		av.setBill_dt(bdate);
		av.setSuppl(supl);
		av.setQuant(recp_quant);
		av.setAmnt(recp_amnt);
		av.setUser_id(UserId);
		Assets_DBQueries as=new Assets_DBQueries();
		ArrayList ar=as.getDepUserId(av);
		ArrayList hod_shName=as.HODShortNames(depId);
		String hod_sh_name=hod_shName.get(0).toString().replace("[", "").replace("]", "");
		System.out.println("Hod Short Name "+hod_shName);
		ArrayList list=as.selectMainName(main_cat);
		String ref_no="";
		String Main_cat_name="";
		String sub_cat_name="";
		for(int i=0;i<list.size();i++){
			ArrayList arr=(ArrayList)list.get(i);
			Main_cat_name=""+arr.get(1);
			System.out.println("main cat "+arr.get(1));
		}
		System.out.println("Main category id "+Main_cat_name);
		ArrayList list2=as.selectSubNameForAddAssets(main_cat, sub_cat);
		for(int i=0;i<list2.size();i++){
			ArrayList arr=(ArrayList)list2.get(i);
		   sub_cat_name=""+arr.get(2);
			System.out.println("Sub cat "+arr.get(2));
		}
		System.out.println("Sub cat "+sub_cat_name);
		
		
		for (int i=0;i<ar.size();i++)
		{
			ArrayList arr=(ArrayList)ar.get(i);
			System.out.println("Department "+arr.get(2));
			String dep1=""+arr.get(2);
			av.setDep(dep1);
		}
		
		/*File load option*/
		
		File dir=new File("E:/PESTProject15-3-2018/workspace/pest/WebContent/resources/Assets_Register_File/"+unit);
		if(!dir.exists())
		{
			boolean ff=dir.mkdir();
			File dir1=new File("E:/PESTProject15-3-2018/workspace/pest/WebContent/resources/Assets_Register_File/"+unit+"/"+av.getDep());
			boolean ff3=dir1.mkdir();
			System.out.println("Path = "+dir.getPath());
			System.out.println("Path 2 = "+dir1.getPath());
			UPLOAD_DIRECTORY=dir.getPath();
			System.out.println("Created Path of the file "+UPLOAD_DIRECTORY);
			System.out.println("Created "+ff+" Directory created in name of "+dir);
			if(ff==true && ff3==true)
			{
				System.out.println("Directory Unit/Department created ");
			}
			else
			{
				System.out.println("Not created");
			}
		}
		else if(dir.exists())
		{
			File dir1=new File("E:/PESTProject15-3-2018/workspace/pest/WebContent/resources/Assets_Register_File/"+unit+"/"+av.getDep());
			if(!dir.exists())
			{
				boolean ff2=dir.mkdir();
				System.out.println("Path = "+dir.getPath());
				UPLOAD_DIRECTORY=dir.getPath();
				System.out.println("Created Path of the file "+UPLOAD_DIRECTORY);
				System.out.println("Created "+ff2+" Directory created in name of "+dir);
				if(ff2)
				{
					System.out.println("Directory Department created ");
				}
				else
				{
					System.out.println("Not created");
				}
			}
			System.out.println("File already exists created");
		}
		
		// End Of file code

		av.setUnit(unit);
		av.setPgm_m_id(depId);
		boolean flag=false;
		String refNId="";
		for(int t=0;t<Sngl_amnt.length;t++)
		{
			String arr=Assets_DBQueries.getRef_id(av);
			if(arr==null||arr.equals("null"))
			{
				arr="0";
			}
			int rNo=Integer.parseInt(arr)+1;
			String ref_id = String.format("%03d", rNo); //Convert Integer number to 001, 002..... infinite
			av.setRef_id(ref_id);
		
			ArrayList sub_sh=Assets_DBQueries.fetchSubCatShortName(av);
			ArrayList arr3=(ArrayList)sub_sh.get(0);
			String sub_sh2=""+arr3.get(0);
			String sub_name=String.valueOf(arr3.get(0));
			String bDate1=dm.format(bdate,"MM-dd-yyyy");
			String mm=bDate1.substring(0,2);
			String yy=bDate1.substring(8, 10);
			String ref1_no=av.getUnit()+"/"+av.getDep()+"/"+sub_sh2.toLowerCase()+"-"+ref_id+"/"+mm+"-"+yy;
			String refDir=av.getUnit()+"_"+av.getDep()+"_"+sub_sh2.toLowerCase()+"_"+ref_id+"_"+mm+"_"+yy;
			String fileName=image.getName();
			
			//Rename File 
			File oldName=new File(fileName);
			System.out.println("--------File Name Found is---------- "+fileName);
			File newName=new File(refDir+".doc");
			boolean b=oldName.renameTo(newName);
			System.out.println("Renamed "+b+" Rename File --=-=--=-=-=-= "+newName);
			av.setRef_no(ref1_no);
			System.out.println("Refenrence number is "+ref1_no);
			flag=as.addRegister(av, sub_cat_name,Main_cat_name, location, Sngl_amnt[t], user_name, sup_number, warranty, hod_sh_name, image, refDir, Bimage);
			if(flag==true)
			{
				File dir4=new File("E:/PESTProject15-3-2018/workspace/pest/WebContent/resources/Assets_Register_File/"+unit+"/"+av.getDep()+"/"+refDir);
				String File_Path="E:/PESTProject15-3-2018/workspace/pest/WebContent/resources/Assets_Register_File/"+unit+"/"+av.getDep()+"/"+refDir;
				boolean flag2=as.updateAssetsRegisterFilePath(File_Path, ref1_no);
				System.out.println("Flag File Path updated "+flag2);
				System.out.println("Directory path for storing file (Destination) "+dir4);
				System.out.println("Directory path for storing file (sourse) "+image);
				boolean cr=dir4.mkdir();
				System.out.println("Fianl directory "+cr);
				FileUtils.copyFileToDirectory(image, dir4);
				refNId=refNId.concat(ref1_no+", ");
				System.out.println("Ref No Concatenation "+refNId);
			}
		}
		System.out.println("Refernce number "+refNId);
		if(flag==true)
		{
			session.setAttribute("error", "Value Is inserted sucessfully and data is send to HOD/HOI approval");
			session.setAttribute("ref_no", refNId);
		}
		else
		{
			session.setAttribute("error", "Value Not Inserted");
		}
		response.sendRedirect("jsp/assetmanagement/AddAssets.jsp");
	}
	
	private void UpdateAssetsDetails(HttpServletRequest request, HttpServletResponse response) throws IOException, CmsSQLException, CmsNamingException, CmsGeneralException
	{
		HttpSession session=request.getSession();
		System.out.println("In Update assets operation");
		String stat=request.getParameter("status123");
		String comment=request.getParameter("txtMsg");
		System.out.println("Comment = "+comment);
		String asset_reg_id=request.getParameter("Sub_id");
		String user_id=request.getParameter("email");
		String sub_name=request.getParameter("sub_name");
		String hod_shName="";
		String status="";
		System.out.println("Ststaus comment = "+stat+ " flag "+stat.equals("0"));
		if(stat.equals("0"))
		{
			status="1";
		}
		else
		{
			status="2";
			System.out.println("Status rejected = "+status);
			boolean flag1=Assets_DBQueries.insertAssetsStatus(asset_reg_id, sub_name, status, comment, user_id);
				if(flag1==true)
				{
					System.out.println("Insert into assets status ");
					session.setAttribute("status_msg", "Rejected report send to user, he/she get back to you soon,thank you");
				}
				else
				{
					System.out.println("Not inserted ");
					session.setAttribute("status_msg","Failed to send rejected report to user please contact user through call or phone");
				}
		}
		System.out.println("Status approved = "+status);
			boolean flag=Assets_DBQueries.updateAssets(status,comment,asset_reg_id, user_id);
			if(flag==true)
			{
				System.out.println("Inserted sucessfully "+flag);
				session.setAttribute("msg", "Status updated. Thank You");
			}
			else{
				System.out.println("Not inserted "+flag);
				session.setAttribute("msg", "Sorry, Status is not updated ");
			}
		
			System.out.println("Status "+status+" Comment "+comment+" assets_register id "+asset_reg_id);
			response.sendRedirect("jsp/assetmanagement/AssetsHodHoiApprovals.jsp");
			
	}
	
	private void performUpdateOperation(HttpServletRequest request, HttpServletResponse response)
	{
		
	}


	
	private void performDeleteOperation(HttpServletRequest request, HttpServletResponse response)
	{
		
	}
	 
}
