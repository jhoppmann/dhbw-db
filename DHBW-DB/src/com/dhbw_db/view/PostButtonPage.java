/**
 * PostButtonPage.java Created by Moritz Walliser on 24.03.2013
 */
package com.dhbw_db.view;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

/**
 * @author Moritz Walliser
 * @version 0.1
 * @since
 */
public class PostButtonPage extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private static final long serialVersionUID = 1L;

	@AutoGenerated
	private AbsoluteLayout mainLayout;

	@AutoGenerated
	private Label headLineLabel;

	@AutoGenerated
	private Label infoLabel;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public PostButtonPage(String headline, String subline) {
		buildMainLayout(headline, subline);
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout(String headline, String subline) {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		// infoLabel
		infoLabel = new Label();
		infoLabel.setImmediate(false);
		infoLabel.setWidth("-1px");
		infoLabel.setHeight("-1px");
		infoLabel.setValue(subline);
		mainLayout.addComponent(infoLabel, "top:60.0px;left:20.0px;");

		// headLineLabel
		headLineLabel = new Label();
		headLineLabel.setStyleName("headline");
		headLineLabel.setImmediate(false);
		headLineLabel.setWidth("-1px");
		headLineLabel.setHeight("-1px");
		headLineLabel.setValue(headline);
		mainLayout.addComponent(headLineLabel, "top:20.0px;left:20.0px;");

		return mainLayout;
	}

}
