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
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import com.digital_indexing.questionary.QuestionaryMaker.auxiliary.WrapLayout;
import com.digital_indexing.questionary.QuestionaryMaker.model.Questionary.Answer;
import com.digital_indexing.questionary.QuestionaryMaker.model.Questionary.Question;

public class QuestionPanel extends JPanel {
	
//	------------------------------------------ ATTRIBUTES ---------------------------------
	
	private JLabel questionLabel;
	private JLabel[] answerLabels;
	private JToggleButton[][] toggleButtons;
	
	
//	------------------------------------------ CONSTRUCTOR ---------------------------------
	
	public QuestionPanel(Question question, int i) {
		super();
		buildComponents(question);
		buildLayout(i);
	}
	

	private void buildComponents(Question question) {
		//question Label
		questionLabel = new JLabel(question.text);
		questionLabel.setFont(MainPanel.FONT_BOLD);
		//answer groups
		Answer[] answers = question.answers;
		if (answers != null) {
			answerLabels = new JLabel[answers.length];
			toggleButtons = new JToggleButton[answers.length][];
			JLabel questionLabel;
			Answer answer;			
			String[] options;
			for (int i = 0; i < answers.length; i++) {
				answer = answers[i];
				questionLabel = answer.name != null ? new JLabel(answer.name) : null;
				questionLabel.setFont(MainPanel.FONT_BOLD);
				answerLabels[i] = questionLabel;
				options = answer.options;
				toggleButtons[i] = options != null ? new JToggleButton[options.length] : new JToggleButton[0];
				ButtonGroup buttonGroup = new ButtonGroup();
				for (int j = 0; j < toggleButtons[i].length; j++) {
					if (answer.multiselect) {
						toggleButtons[i][j] = new JCheckBox(answer.options[j]);
					}
					else {
						toggleButtons[i][j] = new JRadioButton(answer.options[j]);
						buttonGroup.add(toggleButtons[i][j]);
					}
					if (answer.selected[j])
						toggleButtons[i][j].setSelected(true);	
					toggleButtons[i][j].setFont(MainPanel.FONT_PLAIN);
				}
			}
		}
	}

	
	private void buildLayout(int questionNR) {
		//set scroll pane
		final JPanel questionaryPanel = new JPanel(new GridBagLayout());
		final JScrollPane scrollpane = new JScrollPane() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(Math.max(MainPanel.PANEL_WIDTH - 75, 200), Math.max(MainPanel.PANEL_HEIGHT - 100, 100));
			}
		};
		scrollpane.setViewportView(questionaryPanel);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.getVerticalScrollBar().setUnitIncrement(12);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(null, String.format("Question %d", questionNR + 1), TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, MainPanel.FONT_TITLE);
		scrollpane.setBorder(titledBorder);
		add(scrollpane);
		//set box layout
		GridBagConstraints gbc;
		//set question panel
		final JPanel questionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 30));
		questionPanel.add(questionLabel);
		gbc = makeGBC(0, 0, 1, 1);
		questionaryPanel.add(questionPanel, gbc);
		//add answers
		JPanel answerPanel;
		for (int i = 0; i < answerLabels.length; i++) {
			answerPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
			if (answerLabels[i] != null && answerLabels[i].getText().length() != 0)
				answerPanel.add(answerLabels[i]);
			for (JToggleButton button : toggleButtons[i]) 
				answerPanel.add(button);
			gbc = makeGBC(0, i + 1, 1, 1);
			questionaryPanel.add(answerPanel, gbc);
		}	
	}
	
	
//	------------------------------------------ METHODS ---------------------------------

	public boolean[][] getAnswers() {
		boolean[][] result;
		if (toggleButtons == null)
			result = new boolean[0][0];
		else {
			//initialize result
			result = new boolean[toggleButtons.length][];
			for (int i = 0; i < toggleButtons.length; i++) {
				result[i] = new boolean[toggleButtons[i].length];
			}
			//set values
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < result[i].length; j++) {
					result[i][j] = toggleButtons[i][j].isSelected();
				}
			}
		}
		return result;
	}
	
	
	private GridBagConstraints makeGBC(int i, int j, int k, int l) {
		GridBagConstraints res = new GridBagConstraints();
		res.gridx = i;
		res.gridy = j;
		res.gridwidth = k;
		res.gridheight = l;
		res.fill = GridBagConstraints.BOTH;
		return res;
	}

}
