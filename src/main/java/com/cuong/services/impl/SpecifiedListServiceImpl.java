package com.cuong.services.impl;

import java.util.List;
import java.util.logging.Logger;

import com.cuong.daos.ListDAO;
import com.cuong.daos.OnComplete;
import com.cuong.daos.WordDAO;
import com.cuong.daos.impl.ListDAOImpl;
import com.cuong.daos.impl.WordDAOImpl;
import com.cuong.eventhandlers.EntityChangeEventHandler;
import com.cuong.eventhandlers.EntityEventHandler;
import com.cuong.models.Word;
import com.cuong.services.SpecifiedListService;

public class SpecifiedListServiceImpl implements SpecifiedListService {

	private static final Logger LOGGER = Logger.getLogger(SpecifiedListServiceImpl.class.getName());

	private EntityEventHandler<Word> wordEventHandler;

	private String listId;

	private WordDAO wordDAO = new WordDAOImpl();
	private ListDAO listDAO = new ListDAOImpl();

	public SpecifiedListServiceImpl(String listId) {
		this.listId = listId;
	}

	@Override
	public void setWordEventHandler(EntityEventHandler<Word> wordEventHandler) {
		LOGGER.info("SET WORD CHANGE EVENT HANDLER");
		this.wordEventHandler = wordEventHandler;
		if (wordEventHandler != null) {
			LOGGER.info("CALLING ACTIVATE");
			activateListening();
		} else {
			LOGGER.info("CALLING DEACTIVATE");
			deactivateListening();
		}
	}

	@Override
	public void deleteWordCascade(String wordId) {
		LOGGER.info("START: " + wordId);
		wordDAO.delete(wordId, new OnComplete<Word>() {

			@Override
			public void onSuccess(Word object) {
				listDAO.deleteWordId(listId, wordId, null);
			}

			@Override
			public void onError(String error) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void deactivateListening() {
		LOGGER.info("START DEACTIVATE");
		listDAO.removeListenWordIDsOf(listId);
		listDAO.loadWordIDsOf(listId, new OnComplete<List<String>>() {

			@Override
			public void onSuccess(List<String> object) {
				LOGGER.info("REMOVING LISTEN ID: " + object);
				for (String wordID : object) {
					wordDAO.removeListen(wordID);
				}

			}

			@Override
			public void onError(String error) {

			}
		});
	}

	private void activateListening() {
		LOGGER.info("ACTIVATE LISTEN WORDIDS");
		listDAO.listenWordIDsOf(listId, new EntityEventHandler<String>() {

			@Override
			public void onEntityAdded(String entity) {
				LOGGER.info("Listen wordId ref: " + entity);
				wordDAO.listen(entity, new EntityChangeEventHandler<Word>() {

					@Override
					public void onEntityChange(String id, Word entity) {
						LOGGER.info("Ref word: " + id + " changed");
						if (entity == null) {
							LOGGER.info("Deleted");
							wordEventHandler.onEntityRemoved(entity);
						} else {
							LOGGER.info("Added or Updated");
							wordEventHandler.onEntityAdded(entity);
						}
					}

					@Override
					public void onCancelled(String error) {
						// TODO Auto-generated method stub

					}
				});
			}

			@Override
			public void onEntityChanged(String entity) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onEntityRemoved(String entity) {
				LOGGER.info("entity: " + entity);
				wordDAO.removeListen(entity);
			}

			@Override
			public void onEntityMoved(String entity) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onEntityCancelled(String error) {
				// TODO Auto-generated method stub

			}
		});
	}

}
