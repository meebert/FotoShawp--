VER=1.2
LIBS=ffmpeg-$VER-win64-shared/bin/*.dll
7z x ffmpeg-$VER-win64-shared.7z $LIBS
mkdir -p com/googlecode/javacv/cpp/windows-x86_64/
cp $LIBS com/googlecode/javacv/cpp/windows-x86_64/
jar cvf ffmpeg-$VER-windows-x86_64.jar com/
rm -Rf com/
