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

import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;

import com.digital_indexing.questionary.QuestionaryMaker.view.MainPanel;

public class ComponentMap extends HashMap<String, JComponent> {

	public static final String LOAD_BUTTON = "loadbutton";
	public static final String LEFT_BUTTON = "leftbutton";
	public static final String RIGHT_BUTTON = "rightbutton";
	public static final String SAVETEXT_BUTTON = "savetxtbutton";
	public static final String SAVEJSON_BUTTON = "savejsonbutton";
	
	public ComponentMap() {
		super();
		init();
	}

	private void init() {
		final JButton load = new JButton("Load");
		final JButton left = new JButton("<");
		final JButton right = new JButton(">");
		final JButton saveText = new JButton("Save Text");
		final JButton saveJson = new JButton("Save JSON");
		load.setFont(MainPanel.FONT_PLAIN);
		left.setFont(MainPanel.FONT_PLAIN);
		right.setFont(MainPanel.FONT_PLAIN);
		saveText.setFont(MainPanel.FONT_PLAIN);
		saveJson.setFont(MainPanel.FONT_PLAIN);
		put(LOAD_BUTTON, load);
		put(LEFT_BUTTON, left);
		put(RIGHT_BUTTON, right);
		put(SAVETEXT_BUTTON, saveText);
		put(SAVEJSON_BUTTON, saveJson);
	}

}
