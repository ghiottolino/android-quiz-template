INSTRUCTIONS FOR BUILDING

cd /home/tex/android-sdks/platform-tools

jarsigner -verbose -keystore android.keystore android-quiz-template.apk android

/home/tex/android-sdks/tools/zipalign -v 4 /home/tex/android-sdks/platform-tools/android-quiz-template.apk /home/tex/android-sdks/platform-tools/AndroidQuizTemplate.apk
 
rm android-quiz-template.apk

sudo rm /var/www/android/AndroidQuizTemplate.apk

sudo mv AndroidQuizTemplate.apk /var/www/android/AndroidQuizTemplate.apk

ls