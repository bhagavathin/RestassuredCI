package Restassured.Restassured;
// reads the xls files and generates corresponding html reports
// Calls sendmail - mail

import java.io.*;
import java.util.Date;
import java.util.Properties;



public class ReportUtil {
public static String result_FolderName=null;
	public static void main(String[] arg) throws Exception {
		// read suite.xls
		ReportUtil Rep=new ReportUtil();
				Rep.GeneratehtmlReport();
		//SendMail.execute(CONFIG.getProperty("report_file_name"));
		
	}

	
	public void GeneratehtmlReport() throws Exception 
	{
		// read suite.xls
		
		System.out.println("executing");
		Date d = new Date();
		String date=d.toString().replaceAll(" ", "_");
		date=date.replaceAll(":", "_");
		date=date.replaceAll("\\+", "_");
		System.out.println(date);
		 result_FolderName="Reports"+"_"+date;
		//String reportsDirPath=System.getProperty("user.dir")+"\\Reports";
		new File(result_FolderName).mkdirs();
		
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//config//config.properties");
		Properties CONFIG= new Properties();
		CONFIG.load(fs);
		String environment=CONFIG.getProperty("environment");
		String release=CONFIG.getProperty("release");
		Xls_Reader suiteXLS = new Xls_Reader(System.getProperty("user.dir")+"//src//res//Automation_Run_Report.xlsx");
		// create index.html
		String indexHtmlPath=result_FolderName+"\\index.html";
		new File(indexHtmlPath).createNewFile();
		try{
			  
			  FileWriter fstream = new FileWriter(indexHtmlPath);
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write("<html><HEAD> <TITLE>Automation Test Results</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> Automation Test Results</u></b></h4><table  border=1 cellspacing=1 cellpadding=1 ><tr><h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Test Details :</u></h4><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Date</b></td><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
			  out.write(d.toString());
			  out.write("</b></td></tr><tr><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Environment</b></td><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
			  out.write(environment);
			  out.write("</b></td></tr><tr><td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Release</b></td><td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>");
			  out.write(release);
			  out.write("</b></td></tr></table><h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>Report :</u></h4><table  border=1 cellspacing=1 cellpadding=1 width=100%><tr><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>SUITE NAME</b></td><td width=40% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>DESCRIPTION</b></td></tr>");

			 // out.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>TC04</b></td><td width=40% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>TC04</b></td><td width=10% align=center  bgcolor=yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>Skip</b></td></tr>");
			  
			  int totalTestSuites=suiteXLS.getRowCount(Constants.TEST_SUITE_SHEET);
			  String currentTestSuite=null;
			  Xls_Reader current_suite_xls=null;
			  String suite_result="";
			  //System.out.println("Debug:0 "+totalTestSuites);
			  //Harcoded suite count
			  currentTestSuite=null;
			  current_suite_xls=null;
				 // System.out.println("Debug:0 "+Constants.TEST_SUITE_SHEET+Constants.SUITE_ID+currentSuiteID);
				  
				  current_suite_xls=new Xls_Reader(System.getProperty("user.dir")+"//src//res//Automation_Run_Report.xlsx");
				  String currentTestName=null;
				  String currentTestRunmode=null;
				  String currentTestDescription=null;
				  
			     for(int currentTestCaseID=2;currentTestCaseID<=current_suite_xls.getRowCount(Constants.TEST_CASES_SHEET);currentTestCaseID++)
			     {
			    	    currentTestSuite = current_suite_xls.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID,currentTestCaseID);
				    	// System.out.println("Debug:1 "+currentTestCaseID);
				    	 currentTestName=null;
				    	 currentTestDescription=null;
				    	 currentTestRunmode=null;
				    	 
				    	// System.out.println("Debug:1.2 "+Constants.TEST_CASES_SHEET+"TCID:"+Constants.TCID+currentTestCaseID+"RunMode:"+Constants.RUNMODE);
				    	 currentTestName = current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID);
				    	 currentTestDescription = current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.DESCRIPTION, currentTestCaseID);
				    	 currentTestRunmode = current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID);
				    	 System.out.println(currentTestSuite + " -- "+currentTestName );
				    	 
				    	// System.out.println("Debug:2 TestName: "+currentTestName+"  Des: "+currentTestDescription+"  RunMode: "+currentTestRunmode);
				    	 // make the file corresponding to test Steps
				    	   String testSteps_file=result_FolderName+"\\"+currentTestSuite+"_steps.html";
						   new File(testSteps_file).createNewFile();						  
						  //System.out.println("Debug:2.1 "+""+"  Test Step Sheet: "+Constants.TEST_STEPS_SHEET);
						   Constants.TEST_STEPS_SHEET=current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, "SuiteName", currentTestCaseID);
						   System.out.println("Test case name:"+Constants.TEST_STEPS_SHEET);
							  int rows= current_suite_xls.getRowCount(Constants.TEST_STEPS_SHEET);
							  int cols = current_suite_xls.getColumnCount(Constants.TEST_STEPS_SHEET);
							 // System.out.println("Debug:2.2 "+""+"  Rows: "+rows+"  Coles: "+cols);
							  FileWriter fstream_test_steps= new FileWriter(testSteps_file);
							  BufferedWriter out_test_steps= new BufferedWriter(fstream_test_steps);
							  out_test_steps.write("<html><HEAD> <TITLE>"+currentTestSuite+" Test Results</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> "+currentTestSuite+" Detailed Test Results</u></b></h4><table width=100% border=1 cellspacing=1 cellpadding=1 >");
							  out_test_steps.write("<tr>");
							  // System.out.println("Debug:3 "+Constants.TEST_STEPS_SHEET+"Cols:"+cols);
							  for(int colNum=0;colNum<cols;colNum++){
								 // System.out.println("Debug:3.1 "+Constants.TEST_STEPS_SHEET+colNum+1);
								  out_test_steps.write("<td align= center bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
								  out_test_steps.write(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1));								
							  }
							  out_test_steps.write("</b></tr>");
			

						  // fill the whole sheet
						  boolean result_col=false;
						 // System.out.println("Debug:4 Rows: "+rows);
						 
						  for(int rowNum=2;rowNum<=rows;rowNum++){
							  out_test_steps.write("<tr>");  
							  for(int colNum=0;colNum<cols-1;colNum++)
							  {						
								   String data=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, rowNum);
								   result_col=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1).startsWith(Constants.RESULT);
								
//								  if(data.isEmpty()){
//									  if(result_col)
//										  data="SKIP";  
//									  else
//									  data=" ";
//								  }
								  if((data.startsWith("Pass") || data.startsWith("PASS")) && result_col)
									  out_test_steps.write("<td align=center bgcolor=green><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
								  else if((data.startsWith("Fail") || data.startsWith("FAIL")) && result_col){
									  out_test_steps.write("<td align=center bgcolor=red><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
									  if(suite_result.equals(""))
									  suite_result="FAIL";
								  }
								  else if((data.startsWith("Skip") || data.startsWith("SKIP")) && result_col)
									  out_test_steps.write("<td align=center bgcolor=yellow><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
								  else 
									  out_test_steps.write("<td align= center bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=2>");
								   out_test_steps.write(data );
//								    String TestcaseID1 = current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"TCID", rowNum);
//								   if (current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"RunMode", rowNum).equalsIgnoreCase("YES"))
//									 {out_test_steps.write("<a href="+current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"Response", rowNum)+">"+"TestcaseID1" +"</a>");}
//									 
							  }
							  out_test_steps.write("<td>");
							   String TestcaseID1 = current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"TCID", rowNum);							  
							   if (current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"RunMode", rowNum).equalsIgnoreCase("YES") && current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"WebServices", rowNum).equalsIgnoreCase("API")) 
								 {
								   String fp = current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"Response", rowNum);
								   System.out.println("actual link"+"->"+"<a href="+current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"Response", rowNum)+">"+TestcaseID1 +"</a>");
								   System.out.println(TestcaseID1 );
								   out_test_steps.write("<a href="+fp+">"+TestcaseID1 +"</a>");}
							      //out_test_steps.write("<a href="+TestcaseID1 +"onclick=location.href='"+current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,"Response", rowNum)+">"+TestcaseID1+"</a>");}
							   out_test_steps.write("</td>"); 
							  out_test_steps.write("</tr>");
						  }					  
						  out_test_steps.write("</tr>");
						  out_test_steps.write("</table>");  
						  out_test_steps.close();

//						 out_suite_index.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
//						 out_suite_index.write(currentTestName);
//						 out_suite_index.write("</b></td><td width=40% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
//						 out_suite_index.write(currentTestDescription);
//						 out_suite_index.write("</b></td><td width=10% align=center  bgcolor=");
//						 
//						 if(!currentTestRunmode.equalsIgnoreCase(Constants.RUNMODE_YES))
//							  out_suite_index.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>Skip</b></td></tr>");
//						  else
//							  out_suite_index.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>Pass</b></td></tr>");
//						 
						
				     
//				    out_suite_index.write("</table>");  
//				     out_suite_index.close();	
				  
				  out.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				  out.write("<a href="+currentTestSuite.replace(" ", "%20")+"_steps.html>"+currentTestSuite+"</a>");
				  out.write("</b></td><td width=40% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				  out.write(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.DESCRIPTION,currentTestCaseID));
				 // out.write("</b></td><td width=10% align=center  bgcolor=");
//				  if(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE,currentTestCaseID).equalsIgnoreCase(Constants.RUNMODE_YES))
//					  if(suite_result.equals("FAIL"))
//						  out.write("red><FONT COLOR=153E7E FACE=Arial SIZE=2><b>FAIL</b></td></tr>");
//						  else
//							//  out.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>PASS</b></td></tr>");
				  //else
					 // out.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>SKIP</b></td></tr>");
			     }
			    //Close the output stream
				  out.write("</table>");
				  out.close();
			  
		}
			  catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  e.printStackTrace();
		  }
		

		
		//SendMail.execute(CONFIG.getProperty("report_file_name"));
		
		
	}

}
