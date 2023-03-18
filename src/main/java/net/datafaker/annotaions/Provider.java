package net.datafaker.annotaions;

import java.lang.reflect.AnnotatedElement;

interface Provider<R extends AnnotatedElement> {

    Object getValue(R field);
}
