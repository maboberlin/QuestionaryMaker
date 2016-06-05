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
package com.digital_indexing.questionary.QuestionaryMaker.auxiliary;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Logger4Questionary {
	
// ----------------------------- LOGGER INITIALIZATION ------------------------

	public static Logger logger4Questionary = Logger.getLogger("QuestionaryLogger");
	static {
		Path log4jFile = Paths.get(".", "config", "log4j.properties").toAbsolutePath().normalize(); 
		PropertyConfigurator.configure(log4jFile.toString());
		logger4Questionary.setLevel((Level)Level.WARN);
	}

}
