package com.cuong.utils;

public class C {

	public static class View {

		public static final String LISTS_MANAGER = "ListsManager.fxml";
		public static final String ADD_NEW_LIST = "AddNewList.fxml";
		public static final String LIST_MANAGER = "ListManager.fxml";
		public static final String LIST_TABLE_VIEW = "ListTableView.fxml";
		public static final String WORD_TABLE_VIEW = "WordTableView.fxml";

	}

	public static class Ref {

		public static final String WORDS = "words";
		public static final String LISTS = "lists";
		public static final String APP_NAME = "appName";

		public static class Lists {
			public static final String WORD_IDs = "wordIDs";
		}

	}

	public static class Title {

		public static final String INIT = "Loading";
		public static final String CHOOSE_IMPORT_FILE = "Choose import file";
		public static final String TEXT_FILE = "Text file";
		public static final String ADD_NEW_LIST = "Add new list";
		public static final String LIST_DETAIL = "List Detail";
		public static final String SHOW_WORDS = "Show words";
		public static final String DELETE = "Delete";

	}

	public static class FileExtension {

		public static final String TEXT_FILE = "*.txt";

	}

	public static class ModelProperty {

		public static class List {
			public static final String ID = "id";
			public static final String NAME = "name";
			public static final String CREATED_AT = "createdAt";
			public static final String UPDATED_AT = "updatedAt";
			public static final String WORD_IDS = "wordIDs";
		}

		public static class Word {
			public static final String ID = "id";
			public static final String KANJI = "kanji";
			public static final String HIRAGANA = "hiragana";
			public static final String MEANING = "meaning";
			public static final String AM_HAN_VIET = "amHanViet";
			public static final String CREATED_AT = "createdAt";
			public static final String UPDATED_AT = "updatedAt";
		}

	}

	public static class ViewModelProperty {

		public static class List {
			public static final String ID = "id";
			public static final String NAME = "name";
			public static final String CREATED_AT = "createdAt";
			public static final String UPDATED_AT = "updatedAt";
			public static final String NUMBER_OF_WORDS = "numberOfWords";
		}

		public static class Word {
			public static final String ID = "id";
			public static final String KANJI = "kanji";
			public static final String HIRAGANA = "hiragana";
			public static final String MEANING = "meaning";
			public static final String AM_HAN_VIET = "amHanViet";
			public static final String CREATED_AT = "createdAt";
			public static final String UPDATED_AT = "updatedAt";
		}

	}

	public static class Mark {

		public static final String BEGIN = "#";

	}

}
