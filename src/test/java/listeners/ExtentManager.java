package listeners;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports createInstance(String fileName){

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);

        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        extent.setSystemInfo("Organization","QualityStream");

        return extent;

    }

    private static String detectEnvironment(){
        if(System.getenv("JENKINS_HOME")!=null){
            return "JenkinsCI";
        }else {
            return "Local";
        }
    }

    public static String getDefaultReportPath(){
        String baseDir = System.getProperty("user.dir");
        String reportDir = baseDir + "/reports";

        File directory = new File(reportDir);
        if(!directory.exists()){
            directory.mkdir();
        }

        return reportDir;
    }

    public static String getDefaultReport_Date_Path(){
        String baseDir = System.getProperty("user.dir");
        String reportDir = baseDir + "/reports";

        File directory = new File(reportDir);
        if(!directory.exists()){
            directory.mkdir();
        }

        String fileName = "/API_Report_"+new Date().getTime()+".html";

        return reportDir+fileName;
    }



}
