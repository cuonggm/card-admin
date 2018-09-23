package com.cuong.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.apache.commons.io.FilenameUtils;
import com.cuong.daos.BaseDAO;
import com.cuong.daos.ListDAO;
import com.cuong.daos.OnComplete;
import com.cuong.daos.WordDAO;
import com.cuong.daos.impl.ListDAOImpl;
import com.cuong.daos.impl.WordDAOImpl;
import com.cuong.models.List;
import com.cuong.models.Word;
import com.cuong.services.ListService;
import com.cuong.utils.C;

public class ListServiceImpl extends GenericService<String, List> implements ListService {

	private ListDAO listDAO = new ListDAOImpl();
	private WordDAO wordDAO = new WordDAOImpl();

	@Override
	public BaseDAO<String, List> getMainDAO() {
		return listDAO;
	}

	@Override
	public void deleteCascade(String listId, OnComplete<List> onComplete) {
		listDAO.load(listId, new OnComplete<List>() {

			@Override
			public void onSuccess(List object) {
				for (String wordId : object.getWordIDs()) {
					wordDAO.delete(wordId, null);
				}
				listDAO.delete(listId, onComplete);
			}

			@Override
			public void onError(String error) {
				onComplete.onError(error);
			}
		});
	}

	@Override
	public void importTextFile(File file) {
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
					wordDAO.save(word, null);
					list.getWordIDs().add(word.getId());
				}
			}
			listDAO.save(list, null);
			scanner.close();
		} catch (FileNotFoundException e) {

		}
	}

	@Override
	public void updateName(String id, String name, OnComplete<List> onComplete) {
		listDAO.updateName(id, name, onComplete);
	}

}
