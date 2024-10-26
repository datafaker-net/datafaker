package net.datafaker.sequence;

import net.datafaker.providers.base.BaseFaker;

interface Data {
    String name();

    String value();

    String range();

    String unit();
}

class BloodPressure implements Data {

    @Override
    public String name() {
        return "Mean Blood Pressure";
    }

    @Override
    public String value() {
        return new BaseFaker().random().nextInt(60, 180) + "";
    }

    @Override
    public String range() {
        return "";
    }

    @Override
    public String unit() {
        return "mm Hg";
    }
}

class Glucose implements Data {

    @Override
    public String name() {
        return "Glucose";
    }

    @Override
    public String value() {
        return "%.1f".formatted(new BaseFaker().random().nextDouble(3.2, 5.5));
    }

    @Override
    public String range() {
        return "3.2-5.5";
    }

    @Override
    public String unit() {
        return "mmol/L";
    }
}

class Temperature implements Data {

    @Override
    public String name() {
        return "Temperature";
    }

    @Override
    public String value() {
        return new BaseFaker().random().nextInt(30, 50) + "";
    }

    @Override
    public String range() {
        return "";
    }

    @Override
    public String unit() {
        return "degrees C";
    }
}
