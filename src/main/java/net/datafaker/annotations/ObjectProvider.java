package net.datafaker.annotations;

interface ObjectProvider {

    <K> K createObject(Class<K> clazz);
}
