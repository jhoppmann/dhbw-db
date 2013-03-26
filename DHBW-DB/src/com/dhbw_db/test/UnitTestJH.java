/**
 * UnitTestJH.java Created by jhoppmann on Mar 10, 2013
 */
package com.dhbw_db.test;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.junit.Test;

import com.dhbw_db.model.execution.Executor;
import com.dhbw_db.model.io.FileAccess;
import com.dhbw_db.model.io.logging.ConsoleLogger;
import com.dhbw_db.model.io.logging.FileLogger;
import com.dhbw_db.model.io.logging.LoggingService;
import com.dhbw_db.model.io.logging.LoggingService.LogLevel;
import com.dhbw_db.model.request.Request;

/**
 * @author jhoppmann
 * @version 0.1
 * @since
 */
public class UnitTestJH {

	private LoggingService log;

	public UnitTestJH() {

	}

	@Test
	public void test() {
		System.out.println("Start time is " + now() + "("
				+ new Date().toString() + ")");
		try {
			testExecution();
			testFileIO();
			testLoggers();
			testRequestWorkflow();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("End time is " + now() + "(" + new Date().toString()
				+ ")");
	}

	/**
	 * @throws IOException
	 * 
	 */
	private void testFileIO() throws IOException {
		System.out.println("Entering file io test at " + now());
		File testFile = new File("./log/test.log");
		FileAccess.appendLine(testFile, "file1 append1", true);
		FileAccess.appendLine(testFile, "file1 append2", true);
		FileAccess.appendLine(testFile, "file1 append3", true);
		FileAccess.appendLine(testFile, "file1 append4", true);
		FileAccess.appendLine(testFile, "file1 append final", true);

		FileAccess fa = new FileAccess();

		System.out.println(fa.loadMailText("AdminInfo"));

		System.out.println("Exiting file io test at " + now());

	}

	private boolean testLoggers() {
		log = LoggingService.getInstance();

		log.registerLogger(new ConsoleLogger());
		log.registerLogger(new FileLogger(	new File("./log/test.log"),
											false));

		log.log("Test1", LogLevel.INFO);
		log.log("Test2", LogLevel.INFO);
		log.log("Test3", LogLevel.INFO);

		return true;
	}

	private boolean testExecution() throws InterruptedException {
		System.out.println("\nEntering execution test at " + now());
		Executor exec = new Executor();

		FutureTask<String> ft = exec.addTask(new Command());
		// wait a moment
		Thread.sleep(1000L);
		System.out.println(exec.checkStatus(ft));

		exec.addTask(new WaitCommand());
		exec.addTask(new Command());

		// wait a moment to prevent premature end
		Thread.sleep(3000L);

		System.out.println("Exiting execution test at " + now() + "\n\n");
		return true;
	}

	private class Command implements Callable<String> {

		@Override
		public String call() throws Exception {
			System.out.println("Command run at " + now());
			return "Done at " + now();
		}

	}

	private class WaitCommand implements Callable<String> {

		@Override
		public String call() throws Exception {
			System.out.println("Wait command entered at " + now());
			Thread.sleep(2000L);
			System.out.println("Wait command run at " + now());
			return "Done at " + now();
		}

	}

	private boolean testRequestWorkflow() {
		System.out.println("Entering request test at " + now());

		// calculate a day two days in the future from now
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 2);
		Date inTwoDays = cal.getTime();

		Request rq = new Request(	1,
									1);
		rq.setStart(new Date());
		rq.setEnd(inTwoDays);

		System.out.println(rq.getStatus()
								.toString());

		System.out.println("Exiting request test at " + now() + "\n\n");
		return true;
	}

	private String now() {
		return "" + new Date().getTime();
	}
}
