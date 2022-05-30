package org.jsfml.audio;

import com.rubynaxela.kyanite.core.audio.SoundBuffer;
import com.rubynaxela.kyanite.core.audio.SoundSource;
import org.jsfml.internal.Intercom;

import java.nio.Buffer;

@Deprecated
@Intercom
public abstract class Sound extends SoundSource {

    public Sound() {
    }

    protected Sound(long wrap) {
        super(wrap);
    }

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native long nativeCreate();

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native void nativeDelete();

    protected native long nativeCopy();

    protected native void nativeGetData(Buffer buffer);

    protected native void play();

    protected native void pause();

    protected native void stop();

    protected native void nativeSetBuffer(SoundBuffer soundBuffer);

    protected native void nativeSetLoop(boolean loop);

    protected native void nativeSetPlayingOffset(long offset);

    @Override
    protected native int nativeGetStatus();

    @Override
    public Status getStatus() {
        return super.getStatus();
    }
}