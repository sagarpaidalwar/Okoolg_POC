package com.atmecs.glucko.runner;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.atmecs.falcon.automation.appium.manager.Runner;

// edited by vibha
public class TestNGRunner {

    static Properties prop = new Properties();

    // private static ArrayList<String> getPackages(String rootDir) {
    // ArrayList<String> result = new ArrayList<String>();
    // File rootFolder = new File(rootDir);
    // if (!rootFolder.exists()) {
    // return result;
    // }
    // if (rootFolder.isDirectory()) {
    // result = getDirs(rootFolder);
    // }
    // return result;
    // }

    public static void main(String[] args) throws Exception {
        try {
            String packageName = "";
            String path =
                    System.getProperty("user.dir") + File.separator + "src" + File.separator
                    + "main" + File.separator + "java" + File.separator;

            File file = new File(path);
            TestScriptPackage obj = new TestScriptPackage();
            obj.getPackage(file);
            // System.out.println(obj.getPack().replaceFirst(",", ""));
            String x = obj.getPack().replaceFirst(",", "");
            Properties prop = new Properties();
            String[] pack = x.split(",");
            prop.load(new FileInputStream("config.properties"));

            String[] input = System.getProperty(prop.getProperty("MODULE")).split(",");
            int count = 0;
            for (String i : input) {
                for (String p : pack) {
                    if (p.contains(i)) {
                        packageName += "," + p;
                    } else {
                        count++;
                    }
                    if (count == pack.length) {
                        System.out.println("package is not available!!!!!!!!!!!!");
                        // add exception
                        throw new Exception("No Packages available.");
                    }
                }
                count = 0;
            }
            // getTestNGXml();
            Runner.testApp(packageName.replaceFirst(",", ""));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
    // private static ArrayList<String> getDirs(File folder) {
    // String[] files = folder.list();
    // ArrayList<String> dirsRes = new ArrayList<String>();
    //
    // String path = folder.getAbsolutePath();
    // for (String file : files) {
    // File tfile = new File(path + "\\" + file);
    // if (tfile.exists() && tfile.isDirectory()) {
    // ArrayList<String> dirs = getDirs(tfile);
    // if (dirs.size() > 0) {
    // for (String dir : dirs) {
    //
    // dirsRes.add(tfile.getName() + "." + dir);
    // }
    // }
    // }
    // }
    // if (dirsRes.size() == 0) {
    // dirsRes.add(folder.getName());
    // return dirsRes;
    // }
    // return dirsRes;
    // }

    // public static void main1(String[] args) {
    // runTests();
    // }
    //
    // public static void runTests() {
    // try {
    // prop.load(new FileInputStream("config.properties"));
    // } catch (IOException ex) {
    // ex.printStackTrace();
    // System.exit(1);
    // }
    // String modulesProp = prop.getProperty("MODULES");
    // if (modulesProp != null && modulesProp.contains(",")) {
    // modulesProp.split(",");
    // } else {
    // }
    // ArrayList<String> packagesList = new ArrayList<String>();
    // String rootPath =
    // System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
    // + File.separator + "java" + File.separator;
    // System.out.println("rootPath = " + rootPath);
    // packagesList = getPackages(rootPath);
    // System.out.println("packagesList = " + packagesList);
    // }

}
