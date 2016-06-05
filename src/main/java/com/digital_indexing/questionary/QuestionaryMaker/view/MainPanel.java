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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.digital_indexing.questionary.QuestionaryMaker.ComponentMap;

public class MainPanel extends JPanel {
	
//	------------------------------------------ ATTRIBUTES ---------------------------------

	public static final int PANEL_WIDTH = 800;
	public static final int PANEL_HEIGHT = 400;
	
	public static final Font FONT_BOLD = new Font("Sans", Font.BOLD, 20);
	public static final Font FONT_PLAIN = new Font("Sans", Font.PLAIN, 20);
	public static final Font FONT_TITLE = new Font("Sans", Font.PLAIN, 20);
	
	private JPanel mainPanel;
	private JPanel southPanel;
	
	Map<String, JComponent> components;

	
//	----------------------------------------- CONSTRUCTOR ---------------------------------

	public MainPanel(Map<String, JComponent> components) {
		super();
		this.components = components;
		setLayout(new BorderLayout(10, 10));
		southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 200, 20));
		mainPanel = new JPanel(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
	}

	
//	------------------------------------------ METHODS ---------------------------------
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
	}
	

	public void setStart() {
		StartPanel startPanel = new StartPanel(components);
		startPanel.setBorder(BorderFactory.createEmptyBorder(Math.max(PANEL_HEIGHT - 250, 50), 10, 100, 10));
		mainPanel.add(startPanel);
		
	}
	
	
	public void setChangePageButtons() {
		//set bottom layout
		final JButton leftButton = (JButton)components.get(ComponentMap.LEFT_BUTTON);
		final JButton rightButton = (JButton)components.get(ComponentMap.RIGHT_BUTTON);
		southPanel.add(leftButton);
		southPanel.add(rightButton);
	}


	public void setQuestionPanel(QuestionPanel questionPanel) {
		mainPanel.removeAll();
		mainPanel.add(questionPanel, BorderLayout.CENTER);
		validate();
		repaint();
	}


	public void setResultPanel(ResultPanel panel) {
		final JButton leftButton = (JButton)components.get(ComponentMap.SAVETEXT_BUTTON);
		final JButton rightButton = (JButton)components.get(ComponentMap.SAVEJSON_BUTTON);
		southPanel.removeAll();
		mainPanel.removeAll();
		southPanel.add(leftButton);
		southPanel.add(rightButton);
		mainPanel.add(panel, BorderLayout.CENTER);
		validate();
		repaint();
		
	}

}
