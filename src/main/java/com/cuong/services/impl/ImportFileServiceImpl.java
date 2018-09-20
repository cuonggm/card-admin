package com.cuong.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;

import com.cuong.daos.ListDAO;
import com.cuong.daos.WordDAO;
import com.cuong.daos.impl.ListDAOImpl;
import com.cuong.daos.impl.WordDAOImpl;
import com.cuong.models.List;
import com.cuong.models.Word;
import com.cuong.services.ImportFileService;
import com.cuong.utils.C;

public class ImportFileServiceImpl implements ImportFileService {

	private static final Logger LOGGER = Logger.getLogger(ImportFileServiceImpl.class.getName());

	private WordDAO wordDAO = new WordDAOImpl();
	private ListDAO listDAO = new ListDAOImpl();

	@Override
	public void importFromFile(File file) {
		LOGGER.info("import file: " + file.getName());
		try {
			List list = new List();
			list.setName(FilenameUtils.removeExtension(file.getName()));
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.equals(C.Mark.BEGIN)) {
					Word word = new Word();
					line = scanner.nextLine().trim();
					word.setKanji(line);
					line = scanner.nextLine().trim();
					word.setHiragara(line);
					line = scanner.nextLine().trim();
					word.setMeaning(line);
					line = scanner.nextLine().trim();
					word.setAmHanViet(line);
					LOGGER.info("word: " + word);
					wordDAO.save(word, null);
					list.getWordIDs().add(word.getId());
				}
			}
			listDAO.save(list, null);
			scanner.close();
		} catch (FileNotFoundException e) {
			LOGGER.info(e.getMessage());
		}

	}

}
