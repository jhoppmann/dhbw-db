/**
 * Executor.java Created by jhoppmann on Mar 4, 2013
 */
package com.dhbw_db.model.execution;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author jhoppmann
 * @version 0.1
 * @since
 */
public class Executor {
	ExecutorService executor;

	private static Executor instance;

	private Executor(int poolSize) {
		executor = Executors.newFixedThreadPool(poolSize);
	}

	/**
	 * The non-argument constructor constructs a new Executor object with a
	 * default pool size of one.
	 */
	public Executor() {
		this(1);
	}

	public static Executor getInstance() {
		if (instance == null) {
			instance = new Executor(1);
		}
		return instance;
	}

	/**
	 * This adds a task to the executor's queue
	 * 
	 * @param ca The callable that represents the task
	 */
	public synchronized FutureTask<String> addTask(Callable<String> ca) {
		FutureTask<String> ft = new FutureTask<String>(ca);

		executor.execute(ft);

		return ft;
	}

	/**
	 * This method checks if a <tt>FutureTask</tt> is done, and gets its return
	 * value if there were no problems. This method should be preferred to
	 * calling the get() method directly on a future task, as it provides error
	 * and status checking.
	 * 
	 * @param ft The <tt>FutureTask</tt> for which the status should be checked
	 * @return A message describing the status of the FutureTask, or its return
	 *         value
	 */

	public String checkStatus(FutureTask<String> ft) {
		String message = "";
		if (ft.isDone()) {
			try {
				if (ft.isCancelled())
					return "Task has been canceled.";
				else
					return ft.get();
			} catch (InterruptedException e) {
				message = e.getMessage();
			} catch (ExecutionException e) {
				message = e.getMessage();
			}
		} else
			return "Task not done yet";
		// This case will only happen in case of exception
		return message;
	}
}
