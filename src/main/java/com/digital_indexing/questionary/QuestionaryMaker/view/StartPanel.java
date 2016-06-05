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
package com.digital_indexing.questionary.QuestionaryMaker.view;

import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.digital_indexing.questionary.QuestionaryMaker.ComponentMap;

public class StartPanel extends JPanel {

	public StartPanel(Map<String, JComponent> components) {
		super();
		buildPanel(components);
	}

	private void buildPanel(Map<String, JComponent> components) {
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		centerPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		JLabel label = new JLabel("Load questionary data ");
		label.setFont(MainPanel.FONT_PLAIN);
		centerPanel.add(label);
		final JButton button = (JButton)components.get(ComponentMap.LOAD_BUTTON);
		centerPanel.add(button);
		add(centerPanel);
	}

}
