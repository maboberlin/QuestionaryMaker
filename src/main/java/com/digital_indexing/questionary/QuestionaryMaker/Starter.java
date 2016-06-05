/**
 * "Questionary Maker 2000"
 *
 * Copyright (C) 2016 Matthias Boesinger (boesingermatthias@gmail.com).
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 *
 * @license GPL-3.0+ <http://spdx.org/licenses/GPL-3.0+>
 */
package com.digital_indexing.questionary.QuestionaryMaker;

import javax.swing.SwingUtilities;

public class Starter {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				QuestionaryMainFrame x = new QuestionaryMainFrame();
				x.init();
				x.showAndDisplayGUI();
			}
		});

	}

}
