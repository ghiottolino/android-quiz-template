package com.nicolatesser.androidquiztemplate.quiz;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

public class Game {

	private int record;

	private Session session;
	
	private Settings settings;

	private List<Question> questions;

	private List<Question> recentlyWronglyAnsweredQuestions;

	private Map<String, List<Question>> questionsByCategory;

	private static final Integer RECENTLY_WRONGLY_ANSWERED_QUESTIONS_SIZE = 10;

	private Random rg = new Random();

	public static Game instance = null;

	public static Game getInstance() {
		if (instance == null) {
			// init with dummy data.
			instance = new Game();
		}
		return instance;
	}
	
	public static void setInstance(Game game)
	{
		instance=game;
	}

	/**
	 * Private contructor with test data
	 */
	private Game() {
		this(new Vector<Question>(), 0);
		List<Question> questions = new Vector<Question>();
		List<Answer> answers1 = new Vector<Answer>();
		List<Answer> answers2 = new Vector<Answer>();
		List<Answer> answers3 = new Vector<Answer>();
		List<Answer> answers4 = new Vector<Answer>();

		Answer answer1false = new Answer("answer 1", false);
		Answer answer2false = new Answer("answer 2", false);
		Answer answer3false = new Answer("answer 3", false);
		Answer answer4false = new Answer("answer 4", false);
		Answer answer1true = new Answer("answer 1", true);
		Answer answer2true = new Answer("answer 2", true);
		Answer answer3true = new Answer("answer 3", true);
		Answer answer4true = new Answer("answer 4", true);

		answers1.add(answer1true);
		answers1.add(answer2false);
		answers1.add(answer3false);

		answers2.add(answer1false);
		answers2.add(answer2true);
		answers2.add(answer3false);
		answers2.add(answer4false);

		answers3.add(answer1false);
		answers3.add(answer2true);
		answers3.add(answer3true);

		answers4.add(answer1false);
		answers4.add(answer2true);
		answers4.add(answer3false);
		answers4.add(answer4true);

		Question question1 = new Question("question 1", answers1,
				Arrays.asList("category1"));
		Question question2 = new Question("question 2", answers2,
				Arrays.asList("category1", "category2"));
		Question question3 = new Question("question 3", answers3,
				Arrays.asList("category3", "category4"));
		Question question4 = new Question("question 4", answers4,
				Arrays.asList("category4", "category5"));

		questions.add(question1);
		questions.add(question2);
		questions.add(question3);
		questions.add(question4);
		setQuestions(questions);
	}

	public Game(List<Question> questions, Integer record) {
		this.questions = questions;
		this.questionsByCategory = new HashMap<String, List<Question>>();
		initQuestionsByCategory();
		this.recentlyWronglyAnsweredQuestions = new LinkedList<Question>();
		this.session = new Session();
		this.settings = new Settings(null);
		if (record == null) {
			this.record = 0;
		} else {
			this.record = record;
		}
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
		initQuestionsByCategory();
	}

	public void initQuestionsByCategory() {
		for (Question question : this.questions) {
			List<String> categories = question.getCategories();
			for (String category : categories) {
				List<Question> questionByCategoryList = this.questionsByCategory
						.get(category);
				if (questionByCategoryList == null) {
					this.questionsByCategory.put(category,
							new Vector<Question>());
					questionByCategoryList = this.questionsByCategory
							.get(category);
				}
				questionByCategoryList.add(question);
				this.questionsByCategory.put(category, questionByCategoryList);
			}
		}
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Question getQuestion() {
		Question question;
		// if there are more than 10 questions in the
		// recentlyWronglyAnsweredQuestions list
		// then retrieves one of that question
		if (shouldChooseFromWrongAnsweredQuestions()) {
			question = getRandomQuestionFromQuestionList(this.recentlyWronglyAnsweredQuestions);
			recentlyWronglyAnsweredQuestions.remove(question);
		} else {
			//check if categories are selected
			List<String> selectedSettings = this.settings.getSelectedSettings();
			if (selectedSettings.size() == 0) {
				question = getRandomQuestionFromQuestionList(this.questions);
			} else {
				List<Question> pool = new Vector<Question>();
				for (String selectedCategory : selectedSettings) {
					List<Question> categoryQuestions = questionsByCategory.get(selectedCategory);
					if (categoryQuestions!=null)
					{
						pool.addAll(categoryQuestions);
					}
				}
				if (pool.size()>0)
				{
					question = getRandomQuestionFromQuestionList(pool);
				}
				else
				{
					question = getRandomQuestionFromQuestionList(this.questions);
				}
			}			
		}
		return question;
	}

	/**
	 * Return a random question from the recentlyWronglyAnsweredQuestions list
	 * 
	 * @return
	 */
	private Question getRandomQuestionFromQuestionList(List<Question> questions) {
		Collections.shuffle(questions);
		// TODO : make more robust, check for null....
		Question question = questions.get(0);
		return question;
	}

	/**
	 * Decide if return a question of the wrongly answered questions according
	 * to the size of the recentlyWronglyAnsweredQuestions list
	 * 
	 * @return
	 */
	private boolean shouldChooseFromWrongAnsweredQuestions() {
		if ((recentlyWronglyAnsweredQuestions == null)
				|| (recentlyWronglyAnsweredQuestions.size() == 0)) {
			return false;
		}

		if (recentlyWronglyAnsweredQuestions.size() > RECENTLY_WRONGLY_ANSWERED_QUESTIONS_SIZE) {
			return true;
		}

		int random = rg.nextInt(RECENTLY_WRONGLY_ANSWERED_QUESTIONS_SIZE);

		if (random < recentlyWronglyAnsweredQuestions.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check the answer for a given question
	 * 
	 * @param question
	 * @param answer
	 * @return
	 */
	public boolean checkAnswer(Question question, Answer answer) {

		boolean correct = question.getAnswers().contains(answer);
		this.session.setTotalAttempts(this.session.getTotalAttempts() + 1);

		if (correct) {
			this.session
					.setCorrectAttempts(this.session.getCorrectAttempts() + 1);
			this.session.setConsecutiveAttempts(this.session
					.getConsecutiveAttempts() + 1);
		} else {
			this.session.setConsecutiveAttempts(0);
			if (!recentlyWronglyAnsweredQuestions.contains(question)) {
				recentlyWronglyAnsweredQuestions.add(question);
			}
		}
		return correct;
	}
	
	public boolean checkAnswers(Question question, List<Answer> answers) {

		boolean correct = question.getAnswers().containsAll(answers);
		this.session.setTotalAttempts(this.session.getTotalAttempts() + 1);

		if (correct) {
			this.session
					.setCorrectAttempts(this.session.getCorrectAttempts() + 1);
			this.session.setConsecutiveAttempts(this.session
					.getConsecutiveAttempts() + 1);
		} else {
			this.session.setConsecutiveAttempts(0);
			if (!recentlyWronglyAnsweredQuestions.contains(question)) {
				recentlyWronglyAnsweredQuestions.add(question);
			}
		}
		return correct;
	}
	
	
	

	public boolean isNewRecord() {
		if (session.getConsecutiveAttempts() > this.record) {
			this.record = session.getConsecutiveAttempts();
			return true;
		} else {
			return false;
		}
	}

	public int getRecord() {
		return this.record;
	}

	public void setRecord(int record) {
		this.record = record;
	}

	public List<String> getCategories() {
		Set<String> categories = this.questionsByCategory.keySet();
		String[] categoriesArrays = categories.toArray(new String[0]);
		List<String> categoriesList = Arrays.asList(categoriesArrays);
		Collections.sort(categoriesList);
		return categoriesList;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}
	
	

}
