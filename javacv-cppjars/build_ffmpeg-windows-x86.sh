VER=1.2
LIBS=ffmpeg-$VER-win32-shared/bin/*.dll
7z x ffmpeg-$VER-win32-shared.7z $LIBS
mkdir -p com/googlecode/javacv/cpp/windows-x86/
cp $LIBS com/googlecode/javacv/cpp/windows-x86/
jar cvf ffmpeg-$VER-windows-x86.jar com/
rm -Rf com/
