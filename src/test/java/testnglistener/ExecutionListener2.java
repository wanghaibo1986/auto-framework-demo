package testnglistener;

import org.testng.IExecutionListener;

public class ExecutionListener2 implements IExecutionListener {
	public void onExecutionFinish() {
		System.out.println("from ExecutionListener2.onExecutionFinish() ");
	}

	public void onExecutionStart() {
		System.out.println("from ExecutionListener2.onExecutionStart() ");
	}
}
