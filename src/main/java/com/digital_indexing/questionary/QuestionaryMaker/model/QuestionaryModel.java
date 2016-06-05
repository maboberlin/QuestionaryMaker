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
package com.digital_indexing.questionary.QuestionaryMaker.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.digital_indexing.questionary.QuestionaryMaker.auxiliary.Logger4Questionary;
import com.digital_indexing.questionary.QuestionaryMaker.model.Questionary.Answer;
import com.digital_indexing.questionary.QuestionaryMaker.model.Questionary.Condition;
import com.digital_indexing.questionary.QuestionaryMaker.model.Questionary.Question;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class QuestionaryModel {
	
//	------------------------------------- QUESTIONARY ---------------------------------
	
	private Questionary questionary;
	
	
//	------------------------------------- CONSTRUCTOR ----------------------------------
	
	public QuestionaryModel(String filename) throws JsonSyntaxException, IOException {
		Path p = Paths.get(filename);
		byte[] bytes =  Files.readAllBytes(p);
		String text = new String(bytes, StandardCharsets.UTF_8);
		Gson gson = new Gson();
		questionary = gson.fromJson(text, Questionary.class);
		initSelected();
	}
	
	
	private void initSelected() {
		if (questionary != null) {
			for (Question question : questionary.questions) {
				for (Answer answer : question.answers) {
					answer.selected = answer.options != null ? new boolean[answer.options.length] : new boolean[answer.options.length];
					for (int i = 0; i < answer.selected.length; i++) {
						answer.selected[i] = false;
					}
				}	
			}
		}	
	}
	
	
//	-------------------------------------- METHODS --------------------------------------
	
	public Questionary getQuestionary() {
		return questionary;
	}
	
	public int getNrOfQuestions() {
		return questionary.questions.length;
	}


	public Question getQuestion(int questionNr) {
		if (questionary == null)
			return null;
		if (questionNr < 0 || questionNr >= questionary.questions.length)
			return null;
		return questionary.questions[questionNr];
	}
	
	
	private Answer getAnswerById(int answerId) {
		for (Question question : questionary.questions) {
			for (Answer answer : question.answers) {
				if (answer.id == answerId)
					return answer;
			}	
		}
		return null;
	}


	public boolean isValidPage(int questionNr) {
		if (questionary == null)
			return false;
		if (questionNr < 0 || questionNr >= questionary.questions.length)
			return false;
		Condition[] conditions = questionary.questions[questionNr].conditions;
		if (conditions == null)
			return true;
		//check each condition (in case of multiple values, values will be OR-combined!)	
		Answer toCheck;
		int answerId;
		int[] selectedValues;
		boolean conditionOK;
		for (Condition condition : conditions) {
			answerId = condition.answer_id;
			selectedValues = condition.values;
			toCheck = getAnswerById(answerId);
			//check selected values of the answer referenced by this condition
			if (toCheck != null) {
				conditionOK = false;
				for (int j : selectedValues) {
					if (toCheck.selected[j]) {
						conditionOK = true;
						break;
					}
				}
				if (!conditionOK)
					return false;
			}
			else {
				Logger4Questionary.logger4Questionary.info(String.format("Condition of answer nr %d is invalid!", answerId));
			}
		}
		return true;
	}


	public boolean storeAnswers(int questionNr, boolean[][] answers) {
		if (questionary == null)
			return false;
		if (questionNr < 0 || questionNr >= questionary.questions.length)
			return false;
		Question question = questionary.questions[questionNr];
		if (answers.length != question.answers.length)
			return false;
		for (int i = 0; i < question.answers.length; i++) {
			if (answers[i].length != question.answers[i].selected.length)
				return false;
			for (int j = 0; j < question.answers[i].selected.length; j++) {
				question.answers[i].selected[j] = answers[i][j];
			}
		}
		return true;
	}


	public String getResultText() {
		if (questionary == null)
			return null;
		StringBuilder sb = new StringBuilder();
		Question question;
		String text;
		for (int i = 0; i < questionary.questions.length; i++) {
			if (isValidPage(i)) {
				question = questionary.questions[i];
				text = question != null ? String.format("Question %d: %s%n", i + 1, question.text) : "";
				sb.append(text);
				for (Answer answer : question.answers) {
					text = answer.name != null && answer.name.length() > 0 ? String.format("%s: ", answer.name) : "Answer(s): ";
					sb.append(text);
					boolean firstset = false;
					for (int j = 0; j < answer.options.length; j++) {
						if (answer.selected[j]) {
							sb.append(firstset ? String.format(", %s", answer.options[j]) : answer.options[j]);
							firstset = true;
						}
					}
					sb.append(System.lineSeparator());
				}
				sb.append(System.lineSeparator());
			}
		}
		return sb.toString();
	}

}
