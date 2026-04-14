package com.example.base;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class MyRetryAnalyzer implements IRetryAnalyzer {
	private int retryCount = 0;
	private static final int maxRetryCount = 2; // Max attempts

	@Override
	public boolean retry(ITestResult result) {
		if (!result.isSuccess()) {
			if (retryCount < maxRetryCount) {
				retryCount++;
				return true; // Retry
			}
		}
		return false; // Stop
	}
}
