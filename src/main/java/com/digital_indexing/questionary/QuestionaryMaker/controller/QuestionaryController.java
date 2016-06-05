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
package com.digital_indexing.questionary.QuestionaryMaker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.digital_indexing.questionary.QuestionaryMaker.ComponentMap;
import com.digital_indexing.questionary.QuestionaryMaker.auxiliary.Logger4Questionary;
import com.digital_indexing.questionary.QuestionaryMaker.model.QuestionaryModel;
import com.digital_indexing.questionary.QuestionaryMaker.model.Questionary.Question;
import com.digital_indexing.questionary.QuestionaryMaker.view.MainPanel;
import com.digital_indexing.questionary.QuestionaryMaker.view.QuestionPanel;
import com.digital_indexing.questionary.QuestionaryMaker.view.ResultPanel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class QuestionaryController {
	
//	------------------------------------------ ATTRIBUTES ---------------------------------
	
	private int questionNr;
	private QuestionaryModel model;
	private MainPanel mainPanel;
	private QuestionPanel questionPanel;
	private Map<String, JComponent> components;
	
	
//	------------------------------------------ CONSTRUCTOR ---------------------------------

	public QuestionaryController(Map<String, JComponent> components, MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		this.components = components;
		setLoadAction();
		setSaveAction();
		setPageChangeAction(); 
		
	}
	
	
//	------------------------------------- BUTTON ACTIONS -----------------------------------

	private void setLoadAction() {
		final JButton load = (JButton)components.get(ComponentMap.LOAD_BUTTON);
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
				final JFileChooser fileChooser = new JFileChooser(currentDir);
				fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
				int res = fileChooser.showOpenDialog(mainPanel);
				if (res == JFileChooser.APPROVE_OPTION) {
					String filename = fileChooser.getSelectedFile().toString();
					try {
						model = new QuestionaryModel(filename);
					} catch (JsonSyntaxException | IOException  e1) {
						JOptionPane.showMessageDialog(mainPanel, "Error when loading .json-questionary-file", "Loading Error", JOptionPane.ERROR_MESSAGE);
						Logger4Questionary.logger4Questionary.warn(e1.getMessage());
						return;
					}
					mainPanel.setChangePageButtons();
					setNewQuestion(0);
				} 
			}

		});	
	}
	
	
	private void setSaveAction() {
		final JButton save_text = (JButton)components.get(ComponentMap.SAVETEXT_BUTTON);
		final JButton save_json = (JButton)components.get(ComponentMap.SAVEJSON_BUTTON);
		save_text.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = model.getResultText();
				saveFile(text);
			}
		});
		save_json.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Gson gson = new Gson();
				String json = gson.toJson(model.getQuestionary());
				saveFile(json);
			}
		});	
	}
	
	
	private void setPageChangeAction() {
		final JButton left = (JButton)components.get(ComponentMap.LEFT_BUTTON);
		final JButton right = (JButton)components.get(ComponentMap.RIGHT_BUTTON);
		left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				storeValues();
				for (int i = questionNr - 1; i >= 0; i--) {
					if (model.isValidPage(i)) {
						setNewQuestion(i);
						break;
					}
				}
			}
		});
		right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				storeValues();
				for (int i = questionNr + 1; i < model.getNrOfQuestions(); i++) {
					if (model.isValidPage(i)) {
						setNewQuestion(i);
						return;
					}
				}
				setResultPage();
			}
		});
	}
	
	
//	---------------------------------- OTHER METHODS ------------------------------------
	
	private void setNewQuestion(int i) {
		//en/disable button
		final JButton left = (JButton)components.get(ComponentMap.LEFT_BUTTON);
		left.setEnabled( i == 0 ? false : true);
		//build question panel 	
		Question question = model.getQuestion(i);
		if (question == null) {
			Logger4Questionary.logger4Questionary.warn(String.format("Error when initializing question Nr. %d!", questionNr));
			return;
		}
		questionPanel = new QuestionPanel(question, i);
		//set question panel
		mainPanel.setQuestionPanel(questionPanel);
		questionNr = i;
	}
	
	
	private void setResultPage() {
		String resultText = model.getResultText();
		final ResultPanel panel = new ResultPanel(resultText);
		mainPanel.setResultPanel(panel);
	}
	
	
	private void storeValues() {
		boolean[][] answers = questionPanel.getAnswers();
		if (!model.storeAnswers(questionNr, answers)) {
			Logger4Questionary.logger4Questionary.info(String.format("Invalid number of answers / options in model / panel for question Nr. %d!", questionNr));
		}
		
	}
	
	
	private void saveFile(String text) {
		String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
		final JFileChooser fileChooser = new JFileChooser(currentDir);
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		int res = fileChooser.showSaveDialog(mainPanel);
		if (res == JFileChooser.APPROVE_OPTION) {
			String filename = fileChooser.getSelectedFile().toString();
			Path p = Paths.get(filename);
			BufferedWriter bw;
			try {
				bw = Files.newBufferedWriter(p, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
				bw.write(text);
				bw.flush();
				bw.close();
			} catch (FileAlreadyExistsException exc) {
				int res2 = JOptionPane.showConfirmDialog(mainPanel, "File already exists! Really want to save it?", "File aready exists", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (res2 == JOptionPane.OK_OPTION) {
					try {
						bw = Files.newBufferedWriter(p, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
						bw.write(text);
						bw.flush();
						bw.close();
					} catch (IOException e) {
						Logger4Questionary.logger4Questionary.warn(exc.getMessage());
					}
				}
			}
			catch (IOException | SecurityException exc) {
				Logger4Questionary.logger4Questionary.warn(exc.getMessage());
			} 
		}
	}

}
