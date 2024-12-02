package com.framework.core.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.framework.core.utils.files.FileUtils;
import com.framework.core.utils.helper.MiscUtilities;
import com.framework.core.utils.properties.FrameworkProperties;


public class ExtentReport extends Report{

    private static ExtentReport report;

    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

    private ExtentReport() {}

    private static void setExtentReports(String reportDir, String reportFileName){
        String extentReportSubDir = FrameworkProperties.getFrameworkProperties().getProperty("extentReportSubDir");
        String extentReportDir = reportDir + extentReportSubDir;
        FileUtils.createDir(extentReportDir);
        
        reportFileName = "Run_";

        reportFileName = MiscUtilities.dateFormat("T+0", "MM_dd_yyyy") + "\\" + reportFileName
				+ MiscUtilities.dateFormat("T+0", "MM_dd_yyyy") + "_"
				+ MiscUtilities.getTimeStamp("local").replace("-", "").replace(":", "");
        
        String htmlReportFileName =  extentReportDir + reportFileName + ".html";
        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(htmlReportFileName);
        reporter.config().setReportName("API Test Results");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("System", "Windows");
        extentReports.setSystemInfo("Author", "Viswa");
        extentReports.setSystemInfo("Team", "QA Team");
    }

    public static ExtentReport getInstance(String reportDir, String reportFileName){
        if(extentReports == null){
            setExtentReports(reportDir,reportFileName);
            report = new ExtentReport();
        }
        return report;
    }

    public void testStart(String testMethodName){
        ExtentTest extentTest = extentReports.createTest(testMethodName);
        test.set(extentTest);
    }

    public void log(String testMethodName,ReportLevel level,String step,String detail){
        String message = testMethodName + " : " + step + " : " +detail;
        test.get().log(Status.valueOf(level.toString()),message);
    }
    public void log(String testMethodName,ReportLevel level,String message){
        test.get().log(Status.valueOf(level.toString()),testMethodName + " : " + message);
    }
    public void save(){
        extentReports.flush();
        test.remove();
    }
}
