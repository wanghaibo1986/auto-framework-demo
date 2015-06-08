package utils.testng_custom_listeners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import utils.custom_annotations.CaseId;

public class BJJListener implements IInvokedMethodListener {

	public void afterInvocation(IInvokedMethod invokedMethod, ITestResult result) {
		System.out.println("begin ======= =============================================================================from afterInvocation(): ");
		System.out.println("result.getInstanceName() --> " + result.getInstanceName());
		try {
            for (Method method : invokedMethod.getClass()
                    .getClassLoader()
                    .loadClass((result.getInstanceName()))
                    .getMethods()) {
                // checks if "CaseId" annotation is present for the method
                if (method.isAnnotationPresent(utils.custom_annotations.CaseId.class)) {
                    try {
                        // iterates all the annotations available in the method
                        for (Annotation anno : method.getDeclaredAnnotations()) {
                            System.out.println("Annotation in Method '" + method + "' : " + anno);
                        }
                        CaseId caseId = method.getAnnotation(CaseId.class);
                        for ( String id: caseId.value()) {
                        	System.out.println(id);
                        }
 
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		System.out.println("end ======= ============================================================================= from afterInvocation(): ");
	}

	public void beforeInvocation(IInvokedMethod method, ITestResult result) {
		// TODO Auto-generated method stub
		
	}

}
