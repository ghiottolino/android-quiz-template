INSTRUCTIONS FOR BUILDING

cd /home/tex/android-sdks/platform-tools

jarsigner -verbose -keystore android.keystore android-quiz-template.apk android

/home/tex/android-sdks/tools/zipalign -v 4 /home/tex/android-sdks/platform-tools/android-quiz-template.apk /home/tex/android-sdks/platform-tools/AndroidQuizTemplate.apk
 
rm android-quiz-template.apk

sudo rm /var/www/android/AndroidQuizTemplate.apk

sudo mv AndroidQuizTemplate.apk /var/www/android/AndroidQuizTemplate.apk

ls

TODOs:

GAME:

* add explanations to answers: they could be automatically generated (most feminine words in german finish with e)
* add generic attribute to questions/answers: they could be used for insert additional information (translation)
* add statistics

* save and load session
* implement reset


APP:
*make it a library...somehow...

* add other apps button

LAYOUT:
* look for icons
