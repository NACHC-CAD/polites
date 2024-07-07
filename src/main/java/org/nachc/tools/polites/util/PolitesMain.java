package org.nachc.tools.polites.util;

import org.nachc.tools.fhirtoomop.util.params.AppParams;
import org.nachc.tools.polites.util.gui.PolitesGui;

public class PolitesMain {

	public static void main(String[] args) {
		AppParams.touch();
		new PolitesGui();
	}

}
