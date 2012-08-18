INSTRUCTIONS FOR BUILDING

cd /home/tex/android-sdks/platform-tools

jarsigner -verbose -keystore android.keystore AndroidTemplate.apk android

/home/tex/android-sdks/tools/zipalign -v 4 /home/tex/android-sdks/platform-tools/AndroidTemplate.apk /home/tex/android-sdks/platform-tools/AndroidAppTemplate.apk
 
rm AndroidTemplate.apk

sudo rm /var/www/android/AndroidAppTemplate.apk

sudo mv AndroidAppTemplate.apk /var/www/android/AndroidAppTemplate.apk

