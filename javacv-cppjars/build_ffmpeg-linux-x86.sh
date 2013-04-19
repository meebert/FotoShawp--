tar -xjvf ffmpeg-$FFMPEG_VERSION.tar.bz2
mv ffmpeg-$FFMPEG_VERSION ffmpeg-$FFMPEG_VERSION-linux-x86
cd ffmpeg-$FFMPEG_VERSION-linux-x86
tar -xjvf ../last_stable_x264.tar.bz2
X264=`echo x264-snapshot-*`
cd $X264
./configure --enable-static --enable-pic --host=i686-linux
make -j4
cd ../
./configure --enable-shared --enable-gpl --enable-version3 --enable-runtime-cpudetect --disable-outdev=sdl --enable-libx264 --extra-cflags="-I$X264" --extra-ldflags="-L$X264" --cc="gcc -m32"
make -j4
LIBS="libavcodec/libavcodec.so.54 libavdevice/libavdevice.so.54 libavfilter/libavfilter.so.3 libavformat/libavformat.so.54 libavutil/libavutil.so.52 libpostproc/libpostproc.so.52 libswresample/libswresample.so.0 libswscale/libswscale.so.2"
strip $LIBS
mkdir -p com/googlecode/javacv/cpp/linux-x86/
cp $LIBS com/googlecode/javacv/cpp/linux-x86/
jar cvf ../ffmpeg-$FFMPEG_VERSION-linux-x86.jar com/
rm -Rf com/
cd ../
