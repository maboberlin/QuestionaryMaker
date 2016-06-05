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

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class ResultPanel extends JPanel {

	public ResultPanel(String resultText) {
		super();
		//set scroll pane
		final JPanel resultPanel = new JPanel(new GridBagLayout());
		final JScrollPane scrollpane = new JScrollPane() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(Math.max(MainPanel.PANEL_WIDTH - 75, 200), Math.max(MainPanel.PANEL_HEIGHT - 100, 100));
			}
		};
		scrollpane.setViewportView(resultPanel);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.getVerticalScrollBar().setUnitIncrement(12);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Result", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, MainPanel.FONT_TITLE);
		scrollpane.setBorder(titledBorder);
		add(scrollpane);
		//add textfield
		final JTextArea ta = new JTextArea(resultText);
		ta.setFont(MainPanel.FONT_PLAIN);
		ta.setEditable(false);
		ta.setLineWrap(false);
		resultPanel.add(ta);
	}
	
	

}
