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

import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.digital_indexing.questionary.QuestionaryMaker.controller.QuestionaryController;
import com.digital_indexing.questionary.QuestionaryMaker.view.MainPanel;

public class QuestionaryMainFrame extends JFrame {
	
	private MainPanel mainPanel;
		
	public QuestionaryMainFrame() throws HeadlessException {
		super("Questionary Maker");
		//set look and feel
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        	ex.printStackTrace();
        }
		//set close menu
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(QuestionaryMainFrame.this, "Really Exit?", "Quit Application", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == 0) {
					QuestionaryMainFrame.this.setVisible(false);
					QuestionaryMainFrame.this.dispose();
					System.exit(0);	
				}
			}
		});
	}

	
	public void init() {
		//build components and panels
		Map<String, JComponent> components = new ComponentMap();
		mainPanel = new MainPanel(components);
		//set Controller
		new QuestionaryController(components, mainPanel);
		//set introduction panel
		mainPanel.setStart();
	}

	
	public void showAndDisplayGUI() {
		setContentPane(mainPanel);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Locale.setDefault(Locale.ENGLISH);
		JOptionPane.setDefaultLocale(Locale.ENGLISH);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
