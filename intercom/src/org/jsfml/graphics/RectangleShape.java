package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.graphics.Shape;

@Deprecated
@Intercom
public abstract class RectangleShape extends Shape {

    @Override
    @Deprecated
    protected native long nativeCreate();

    @Override
    @Deprecated
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
    protected native void nativeDelete();

    protected native void nativeSetSize(float w, float h);
}
