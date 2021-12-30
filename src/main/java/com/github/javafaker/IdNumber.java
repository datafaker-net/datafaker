package com.github.javafaker;

import com.github.javafaker.idnumbers.EnIdNumber;
import com.github.javafaker.idnumbers.NricNumber;
import com.github.javafaker.idnumbers.NricNumber.Type;
import com.github.javafaker.idnumbers.PtNifIdNumber;
import com.github.javafaker.idnumbers.EnZAIdNumber;
import com.github.javafaker.idnumbers.EsMXIdNumber;
import com.github.javafaker.idnumbers.SvSEIdNumber;
import com.github.javafaker.idnumbers.ZhCnIdNumber;

public class IdNumber {
    private final Faker faker;

    protected IdNumber(Faker faker) {
        this.faker = faker;
    }

    public String valid() {
        return faker.fakeValuesService().resolve("id_number.valid", this, faker);
    }

    public String invalid() {
        return faker.numerify(faker.fakeValuesService().resolve("id_number.invalid", this, faker));
    }

    public String ssnValid() {
        EnIdNumber enIdNumber = new EnIdNumber();
        return enIdNumber.getValidSsn(faker);
    }

    /**
     * Specified as #{IDNumber.valid_sv_se_ssn} in sv-SE.yml
     */
    public String validSvSeSsn() {
        SvSEIdNumber svSEIdNumber = new SvSEIdNumber();

        return svSEIdNumber.getValidSsn(faker);
    }

    /**
     * Specified as #{IDNumber.valid_en_za_ssn} in en-ZA.yml
     */
    public String validEnZaSsn() {
        EnZAIdNumber enZAIdNumber = new EnZAIdNumber();
        return enZAIdNumber.getValidSsn(faker);
    }

    /**
     * Specified as #{IDNumber.invalid_en_za_ssn} in en-ZA.yml
     */
    public String inValidEnZaSsn() {
        EnZAIdNumber enZAIdNumber = new EnZAIdNumber();
        return enZAIdNumber.getInValidSsn(faker);
    }

    /**
     * Specified as #{IDNumber.invalid_sv_se_ssn} in sv-SE.yml
     */
    public String invalidSvSeSsn() {
        SvSEIdNumber svSEIdNumber = new SvSEIdNumber();
        return svSEIdNumber.getInvalidSsn(faker);
    }

    public String singaporeanFin(){
        return NricNumber.getValidFIN(faker, Type.FOREIGNER_TWENTY_FIRST_CENTURY);
    }

    public String singaporeanFinBefore2000(){
        return NricNumber.getValidFIN(faker, Type.FOREIGNER_TWENTIETH_CENTURY);
    }

    public String singaporeanUin(){
        return NricNumber.getValidFIN(faker, Type.SINGAPOREAN_TWENTY_FIRST_CENTURY);
    }

    public String singaporeanUinBefore2000(){
        return NricNumber.getValidFIN(faker, Type.SINGAPOREAN_TWENTIETH_CENTURY);
    }

    /**
     * Generate a valid Zh-CN id number.
     * @return A Zh-CN id number
     */
    public String validZhCNSsn ()  {
        ZhCnIdNumber zhCnIdNumber = new ZhCnIdNumber();
        return zhCnIdNumber.getValidSsn(faker);
    }

    public String validPtNif() {
        PtNifIdNumber idNumber = new PtNifIdNumber();
        return idNumber.getValid(faker);
    }

    public String invalidPtNif() {
        PtNifIdNumber idNumber = new PtNifIdNumber();
        return idNumber.getInvalid(faker);
    }


    /**
     * Specified as #{IDNumber.valid_es_mx_ssn} in es-MX.yml
     * @return A valid MEX CURP.
     */
    public String validEsMXSsn() {
        EsMXIdNumber esMXIdNumber = new EsMXIdNumber();
        return esMXIdNumber.get(faker);
    }

    /**
     * Specified as #{IDNumber.invalid_es_mx_ssn} in es-MX.yml
     * @return A valid MEX CURP.
     */
    public String invalidEsMXSsn() {
        EsMXIdNumber esMXIdNumber = new EsMXIdNumber();
        return esMXIdNumber.getWrong(faker);
    }

}
