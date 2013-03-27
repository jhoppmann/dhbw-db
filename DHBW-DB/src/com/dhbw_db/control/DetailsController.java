/**
 * DetailsController.java Created by jhoppmann on Mar 27, 2013
 */
package com.dhbw_db.control;

import java.util.Calendar;
import java.util.Date;

import com.dhbw_db.model.mail.EmailSessionBean;
import com.dhbw_db.model.request.Request;
import com.dhbw_db.model.request.Request.Status;
import com.dhbw_db.view.DetailsPage;
import com.dhbw_db.view.PostButtonPage;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * The DetailsController controls the actions on the details page, namely the
 * request workflow steps
 * 
 * @author jhoppmann
 * @version 0.1
 * @since 0.1
 */
public class DetailsController implements ClickListener, ValueChangeListener {

	private static final long serialVersionUID = -6383781317470427217L;

	private Request request;

	private DetailsPage controlledView;

	/**
	 * The standard constructor takes a target view and a request.
	 * 
	 * @param view The view to control
	 * @param request The displayed request
	 */
	public DetailsController(DetailsPage view, Request request) {
		this.controlledView = view;
		this.request = request;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		if (controlledView.getDate() != null && request != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(request.getUntil());
			cal.add(Calendar.DATE, 1);
			Date afterUntil = cal.getTime();

			if (controlledView.getDate()
								.before(afterUntil)) {
				controlledView.setDate(afterUntil);
			}
		}

	}

	@Override
	public void buttonClick(ClickEvent event) {
		String caption = event.getButton()
								.getCaption();

		if (caption.equals("Zurückziehen")) {
			request.retract();
		} else if (caption.equals("Genehmigen")) {
			request.approve();
		} else if (caption.equals("Ablehnen")) {
			request.reject();
		} else if (caption.equals("Verlängerung")) {
			request.setUntil(controlledView.getDate());
			request.moveToStatus(Status.OPEN);
			(new EmailSessionBean()).sendMailRequestLecturer(request);
		} else if (caption.equals("Beenden")) {
			request.complete();
		} else if (caption.equals("Abbrechen")) {
			request.cancel();
		}

		MainController.get()
						.getDataAccess()
						.updateRequest(request);
		MainController.get()
						.changeView(new PostButtonPage(	"Ihre Entscheidung wurde übermittelt",
														"Der Antrag wurde gemäß ihrer Entscheidung modifiziert."));

	}

}
