/**
 * ApplicationWindow.java Created by jhoppmann on Mar 10, 2013
 */
package com.dhbw_db.view;

import com.dhbw_db.control.MainController;
import com.dhbw_db.control.MainUI;
import com.dhbw_db.model.beans.User;
import com.dhbw_db.view.admin.AdminStartPage;
import com.dhbw_db.view.lecturer.LecturerStartPage;
import com.dhbw_db.view.login.LoginWindow;
import com.dhbw_db.view.student.StudentStartPage;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * @author jhoppmann
 * @version 0.1
 * @since
 */
public class ApplicationWindow extends Panel {

	private static final long serialVersionUID = -1232697565973119448L;

	Panel topPanel;

	NavigationBar menu;

	Panel applicationSpace;

	public ApplicationWindow() {

		if (MainController.get()
							.getUser() == null) {
			LoginWindow login = new LoginWindow();
			MainUI.getCurrent()
					.addWindow(login);
			login.focus();

		} else {
			// setSizeFull();
			setWidth("1200px");
			setHeight("880px");

			VerticalLayout vlo = new VerticalLayout();
			// vlo.setSizeFull();
			setContent(vlo);

			// build the top panel
			topPanel = new Panel();
			// topPanel.setHeight(200, Unit.PIXELS);
			HorizontalLayout topPanelContent = new HorizontalLayout();
			Image topImage = new Image(	null,
										new ClassResource("../../../img/dhbwtop.jpg"));
			topPanelContent.addComponent(topImage);
			topPanelContent.setComponentAlignment(	topImage,
													Alignment.MIDDLE_CENTER);
			topPanelContent.setSizeFull();

			topPanel.setContent(topPanelContent);

			// create the menu bar
			menu = new NavigationBar();

			// create the main application space
			applicationSpace = new Panel();
			applicationSpace.setWidth("100%");
			applicationSpace.setHeight("700px");

			AbsoluteLayout applicationSpaceLayout = new AbsoluteLayout();
			User u = MainController.get()
									.getUser();
			if (u.isAdmin())
				applicationSpaceLayout.addComponent(new AdminStartPage());
			else if (u.isLecturer())
				applicationSpaceLayout.addComponent(new LecturerStartPage());
			else
				applicationSpaceLayout.addComponent(new StudentStartPage());

			applicationSpace.setContent(applicationSpaceLayout);

			// add all components to the layout
			vlo.addComponent(topPanel);
			vlo.addComponent(menu);
			vlo.addComponent(applicationSpace);

		}
	}

	public void replaceView(Component c) {
		AbsoluteLayout applicationSpaceLayout = new AbsoluteLayout();
		applicationSpaceLayout.addComponent(c);
		applicationSpace.setContent(applicationSpaceLayout);
	}

}
