package com.iee.webbase.modelConvertDemo;

public interface Converter<S,T> {
    T doForward(S s);

    S doBackward(T t);
}
