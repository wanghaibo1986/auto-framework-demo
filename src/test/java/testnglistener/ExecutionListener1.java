package testnglistener;

import org.testng.IExecutionListener;

public class ExecutionListener1 implements IExecutionListener {

	public void onExecutionFinish() {
		System.out.println("from ExecutionLister1.onExecutionFinish() ");
	}

	public void onExecutionStart() {
		System.out.println("from ExecutionLister1.onExecutionStart() ");
	}

}
