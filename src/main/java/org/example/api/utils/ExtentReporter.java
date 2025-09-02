package org.example.api.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ExtentReporter {
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static boolean shutdownHookRegistered = false;
    
    private static ExtentReports extent;
    private static String reportPath;
    private static Map<String, ExtentTest> testMap = new HashMap<>();
    private static ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();
    
    static {
        File reportsDir = new File(System.getProperty("user.dir") + "/reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }
        
        if (!shutdownHookRegistered) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (initialized.get() && extent != null) {
                    extent.flush();
                    
                    File reportFile = new File(reportPath);
                    String fileUrl = "file://" + reportFile.getAbsolutePath().replace("\\", "/");
                    
                    System.out.println("\n=========== TEST REPORT AVAILABLE ===========");
                    System.out.println("Report URL: " + fileUrl);
                }
            }));
            shutdownHookRegistered = true;
        }
    }

    public static boolean initReport() {
        if (initialized.getAndSet(true)) {
            return false;
        }
        
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));
        reportPath = System.getProperty("user.dir") + "/reports/TestReport_" + timeStamp + ".html";
        
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("InfoTrack Automation Results");
        reporter.config().setDocumentTitle("Test Results");
        reporter.config().setTheme(Theme.STANDARD);
        reporter.config().setEncoding("utf-8");
        reporter.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");
        
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        
        extent.setSystemInfo("Tester", "LongQuach");
        return true;
    }

    public static void startTest(String testName, String description) {
        if (extent == null) {
            initReport();
        }
        
        ExtentTest test;
        if (description != null && !description.isEmpty()) {
            test = extent.createTest(testName, description);
        } else {
            test = extent.createTest(testName);
        }
        
        testMap.put(testName, test);
        currentTest.set(test);
    }

    public static void error(String error) {
        if (currentTest.get() != null) {
            currentTest.get().log(Status.FAIL, error);
        }
    }

    public static void pass(String success) {
        if (currentTest.get() != null) {
            currentTest.get().log(Status.PASS, success);
        }
    }

    public static void json(String json) {
        if (currentTest.get() != null) {
            currentTest.get().info(json);
        }
    }

}
