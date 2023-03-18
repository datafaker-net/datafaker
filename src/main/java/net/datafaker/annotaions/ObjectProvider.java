package net.datafaker.annotaions;

interface ObjectProvider {

    <K> K createObject(Class<K> clazz);
}
