/**
 * TimeWorker.java Created by jhoppmann on Mar 27, 2013
 */
package com.dhbw_db.control;

import java.util.Date;
import java.util.List;

import com.dhbw_db.model.io.database.DataAccess;
import com.dhbw_db.model.io.database.MySQLAccess;
import com.dhbw_db.model.io.logging.LoggingService;
import com.dhbw_db.model.io.logging.LoggingService.LogLevel;
import com.dhbw_db.model.request.Request;

/**
 * The TimeWorker class checks for any overdue or cancelled requests once every
 * 24 hours.
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class TimeWorker implements Runnable {

	private static TimeWorker tw;

	private TimeWorker() {
		// nothing to see here, move along
	}

	/**
	 * This is a singleton. Initialize and return instance here.
	 * 
	 * @return The instance of this class
	 */
	public static TimeWorker getInstance() {
		if (tw == null) {
			tw = new TimeWorker();
		}
		return tw;
	}

	@Override
	public void run() {
		while (true) {
			// run once every 24 hours
			try {

				Thread.sleep(86400000L);
				DataAccess dao = new MySQLAccess();
				List<Request> requests = dao.getRequests();
				for (Request rq : requests) {
					rq.checkTime();
					dao.updateRequest(rq);
				}
				// tell the loggers that the worker ran successfully
				LoggingService.getInstance()
								.log(	"Timeworker: run at "
												+ (new Date()).toString(),
										LogLevel.DEBUG);
			} catch (InterruptedException e) {
				LoggingService.getInstance()
								.log(e.getMessage(), LogLevel.ERROR);
				e.printStackTrace();
			}
		}
	}

}
