android-quiz-template
=====================

# Introduction

An android library project to use for different types of multiple-answer quiz applications. 
In particular the templates offers 2 kind of quizes, or game modes:
* Educational Game: for learning while playing through a clever repetition system of questions which has been answered wrongly.
* Trivia Game: for a more classic and fast hit or miss kind of game.

Moreover the library provides a quiz domain model and logic and a lot of extensible UI elements.

##Features
* Educational Game Template
* Trivia Game Template
* Multiple correct answer mode
* Pretty and Extensible UI
* Random order answers (the answers are always shown in a random position)
* Pretty user feedback and record managent 
* Question categorization
* Settings Activity for selecting which categories should be active for the current quiz
* Compatibility with AndroidAnnotations (http://androidannotations.org/)
* DefaultUncaughtExceptionHandler with bug report functionality

##Educational Game

![alt tag](https://db.tt/qGAYoOcf)

In an educational game the user really has to focus when giving an answer and try to memorize the correct answers. If the wrong answer is given, the user should try answering again until she founds the correct answer. 
A simple but clever repetition algorithm will make the user learn from her mistakes: the wrongly answered question will be asked again soon, so the user have the possibility to answer correctly the next time (and learn!).

##Trivia Game

![alt tag](https://db.tt/Q23omwUI)

The Trivia game is the more classic and fast kind of game: the user sees a question and a bunch of answers, and try to guess the correct one. Both if the answer is correct or not the next question will be shown and the answered question will not be asked again in the game session.

### How to create a Quiz in 4 easy steps

1. Create your own Questions (i.e. with you own QuestionGenerator)
The most important element in a quiz is a question, which is made of a question text, a list of answers (at least one shoul be correct) and a list of categories.
You should learn how to create a question, here is an example:

```java	
String questionText = "Question Text?";
List<Answer> answers = new LinkedList<Answer>();
answers.add(new Answer("Correct Answer", true));
answers.add(new Answer("Wrong Answer", false));
answers.add(new Answer("Wrong Answer", false));
answers.add(new Answer("Wrong Answer", false));
List<String> categories = Arrays.asList("category1", "category2");
Question question = new Question(questionText, answers, categories );

```
It's a good practice to create a class (i.e. QuestionGenerator) that "generates" a List of questions (List<Question> questions) in order to decouple the components of the tests.
The Question generation is in many cases where the android-quiz-templates applications differentiate from each others:
* some generators could just generate algebra questions using Random numbers  [Algebra Quiz] (https://play.google.com/store/apps/details?id=com.nicolatesser.algebraquiz)
* some generators could load a database a words with genders from a text file [German Gender Quiz] (https://play.google.com/store/apps/details?id=com.nicolatesser.germanverbpreposition)
* some generators could load a question catalogue from a Internet resource ...
* and so on...

2. Load you questions database:

Before starting your game, wherever in your application you should initialize the QuestionDatabase. The QuestionDatabase will be initialized just once for each execution of you app, if it already holds some questions, nothing happen.
For doing so, get an instance of the singleton QuestionDatabase object, and initialized it with a list of questions.

```java	
QuestionDatabase qd = QuestionDatabase.getInstance();
List<Question> questions = new MyQuestionGenerator().getQuestions();	
qd.prepare(questions);
```



3. Create you quiz activity:

Create a MyQuizActivity which extends either QuizActivity or EduQuizActivity

```java	
public class MyQuizActivity extends QuizActivity {
...
}

```

4. In your Activity (MyQuizActivity) initialize a game and start playing:


```java	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.quiz);
	SessionUtils.setSession(this, new Session());	//create an empty session	
	TriviaGame game = new TriviaGame(QuestionDatabase.getInstance(),new ArrayList<String>()); //starting a TriviaGame after having
	GameHolder.setInstance(game);
	loadRecord();	
	displayNextQuestion();
}	

```
		
### How to create a Quiz application using Android Annotations

The android-quiz-template library does not depend from [AndroidAnnotations](androidannotations.org), but as I am big fan of it, I wanted to build my applications using it. So I had to tweek a bit the library code in order to make it more easily accessible also from classes annotated with @EActivity.

In order to use the annotations for creating a quiz, you can still follow the points 1 to 3 changing you code in a more android annotations fashion, but I reccomend you to pay attention to step 4, and using this snippet as referiment:


```java	
@EActivity(R.layout.quiz)
public class MyQuizActivity extends QuizActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@AfterViews
	protected void afterViews() {
		initViews();
		SessionUtils.setSession(this, new Session());		
		TriviaGame game = new TriviaGame(QuestionDatabase.getInstance(),new ArrayList<String>());
		GameHolder.setInstance(game);
		loadRecord();
		displayNextQuestion();
	}
```



## Applications implemented using this framework

### German Gender Quiz

For testing your knowledge of german words gender.
This game consists of guessing the gender of a German word. At every stage you will see a random German noun, and you can guess its gender by clicking on the 3 buttons ('der' for masculine, 'die' for feminine and 'das' for neutral words).

[Play Store] (https://play.google.com/store/apps/details?id=com.nicolatesser.germangenderquiz)

![alt tag](https://db.tt/qGAYoOcf)


### What's Playing

What's playing? allows you to test your musical culture by listening and guessing tracks' titles, artists' names and release year of popular songs!

Soon in the play store!

![alt tag](https://db.tt/mR96q0wP)

![alt tag](https://db.tt/b9Ioy5bD)


### German Verb Prepositions Quiz

The quiz consists of guessing the prepositions (and the case) fitting to a German verb. At every stage you will see a random German verb, and you can guess the prepositions (1 ore more possible correct answers are possible) by clicking on the correct button. If the verb could be combined with more than one preposition the number of correct answers is shown in brackets at the end of the question. 

[Play Store] (https://play.google.com/store/apps/details?id=com.nicolatesser.germanverbpreposition)

### German Adjective Declension

The purpose of the game is to decline the adjective of the given german sentences, according to gender, number and case of the noun and the type of declination (definite articles, undefinite articles, null articles). It also provides an handy cheatsheet for learning more about adjective declension.

[Play Store] (https://play.google.com/store/apps/details?id=com.nicolatesser.germanadjectivedeclension)

### Algebra Quiz

For testing your algebra skills, and train the four basic mathematical operations.
You can also choose if you want to separately train the addition, the division, the multiplication or the division.
This app is useful for both kids learning the basic operations and adults willing to do some mental gymnastics.

[Play Store] (https://play.google.com/store/apps/details?id=com.nicolatesser.algebraquiz)

### English irregular verbs quiz

The quiz consists of guessing the simple past and the past participle of an English irregular verb shown in simple present. At every stage you will see a random English verb, and you can guess the related simple past and past participle by clicking on the correct answer

[Play Store]  (https://play.google.com/store/apps/details?id=com.nicolatesser.englishirregularverb)

### Spanish Verb Prepositions Quiz

The quiz consists of guessing the correct prepositions for a Spanish verb. At every stage you will see a random Spanish verb, and you can guess the correct preposition (or select "no preposition" if the verb does not want a preposition) by clicking on the correct button. If the verb could be combined with more than one preposition the number of correct answers is shown in brackets at the end of the question

[Play Store]  (https://play.google.com/store/apps/details?id=com.nicolatesser.spanishverbpreposition)

### Italian articles quiz (free)

The quiz consists of guessing the article of an Italian word. At every stage you will see a random Italian noun, and you can guess its article by clicking on the 4 buttons corresponding to the 4 singular determinative articles (il,lo,la,l').

[Play Store]  (https://play.google.com/store/apps/details?id=com.nicolatesser.italiangenderquiz)


