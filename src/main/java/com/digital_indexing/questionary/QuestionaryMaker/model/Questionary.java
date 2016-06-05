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

public class Questionary {
	
	public Question[] questions;
	
	public void setQuestions(Question[] questions) {
		this.questions = questions;
	}
	
	
	public static class Question {

		public String text;
		public Answer[] answers;
		public Condition[] conditions;
		
		public void setText(String text) {
			this.text = text;
		}
		public void setAnswers(Answer[] answers) {
			this.answers = answers;
		}
		public void setConditions(Condition[] conditions) {
			this.conditions = conditions;
		}
	}
	
	
	public static class Answer {

		public int id;
		public String name;
		public String[] options;
		public boolean[] selected;
		public boolean multiselect;
		
		public void setId(int id) {
			this.id = id;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setOptions(String[] options) {
			this.options = options;
		}
		public void setMultiselect(boolean multiselect) {
			this.multiselect = multiselect;
		}
	}
	
	
	public static class Condition {

		public int answer_id;
		public int[] values;
		
		public void setAnswer_id(int answer_id) {
			this.answer_id = answer_id;
		}
		public void setValues(int[] values) {
			this.values = values;
		}
	}

}
