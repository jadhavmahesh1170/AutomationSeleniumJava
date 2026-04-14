package com.example.base;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyTestListener implements ITestListener{
	
	@Override
	public void onTestFailure(ITestResult result) {
		System.err.println("Test failed: "+result.getName());
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test successful:"+result.getName());
	}
}
