package com.example.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

public class MyAnnotationTransformer implements IAnnotationTransformer {

	public void transform(ITestAnnotation testAnnotation, Class testClass, Constructor testConstructor,
			Method testMethod) {
		testAnnotation.setRetryAnalyzer(MyRetryAnalyzer.class);
	}
}
