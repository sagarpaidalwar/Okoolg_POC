package com.atmecs.glucko.testscripts;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.appium.manager.UserBaseTest;

public class TestScript2 extends UserBaseTest {
    @AfterTest
    public void afterTest() {
        System.out.println("inside testScript2 in after test");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("inside testScript2 in before test");
    }

    @Test
    public void test() {
        System.out.println("inside testScript2 in test");
    }
}
