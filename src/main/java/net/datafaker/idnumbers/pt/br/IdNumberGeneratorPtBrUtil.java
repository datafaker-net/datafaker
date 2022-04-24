package net.datafaker.idnumbers.pt.br;

import net.datafaker.Faker;

public final class IdNumberGeneratorPtBrUtil {

    private IdNumberGeneratorPtBrUtil() {
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/CNPJ">https://en.wikipedia.org/wiki/CNPJ</a>
     *
     * @param formatted a cnpj (un)formatted
     * @param valid     a cnpj (in)valid
     */
    public static String cnpj(Faker faker, boolean formatted, boolean valid, boolean multiBranch) {
        String cnpj;

        if (valid) {
            StringBuilder partial = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                partial.append(faker.random().nextInt(9));
            }

            if (multiBranch) {
                partial.append(leftPad('0', 4, String.valueOf(faker.random().nextInt(1, 9999))));
            }
            else {
                partial.append("0001");
            }

            cnpj = partial.toString();

            int d1 = digit(calculateWeight(cnpj.substring(4, 12), 9) + calculateWeight(cnpj.substring(0, 4), 5));
            int d2 = digit((d1 * 2) + calculateWeight(cnpj.substring(5, 12), 9) + calculateWeight(cnpj.substring(0, 5), 6));

            cnpj = (cnpj + d1) + d2;
        } else {
            cnpj = String.valueOf(faker.random().nextInt(1000000000) + (faker.random().nextInt(90) + 10) * 1000000000000L);
        }

        String result = (formatted) ? DocumentFormatterUtil.cnpj(cnpj) : cnpj;

        // Sometimes the generated number is not what you expected, for example, you expected an invalid number,
        // but the generated number is valid. This fixes the issue by generating a new number until it matches the expectation.
        if (isCNPJValid(result) != valid) {
            result = cnpj(faker, formatted, valid, multiBranch);
        }

        return result;
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/CPF_number">https://en.wikipedia.org/wiki/CPF_number</a>
     *
     * @param formatted a CPF (un)formatted
     * @param valid     a CPF (in)valid
     */
    public static String cpf(Faker faker, boolean formatted, boolean valid) {
        String cpf;
        if (valid) {
            StringBuilder partial = new StringBuilder();
            for (int i = 0; i < 9; i++) {
                partial.append(faker.random().nextInt(9));
            }
            cpf = partial.toString();

            int d1 = digit(calculateWeight(cpf, 10));
            int d2 = digit((d1 * 2) + calculateWeight(cpf, 11));

            cpf = (cpf + d1) + d2;
        } else {
            cpf = String.valueOf(faker.random().nextInt(1000000000) + (faker.random().nextInt(90) + 10) * 1000000000L);
        }

        String result = formatted ? DocumentFormatterUtil.cpf(cpf) : cpf;

        if (isCPFValid(result) != valid) {
            // Sometimes the generated number is not what you expected, for example, you expected an invalid number,
            // but the generated number is valid. This fixes the issue by generating a new number until it matches the expectation.
            result = cpf(faker, formatted, valid);
        }

        return result;
    }

    /**
     * Return true if the CNPJ is valid
     * A valid CNPJ is unique and have a algorithm to validate it
     * <p>
     * CNPJ generator could generate a valid or invalid because, somentimes, we need to test a
     * registration with invalid number
     */
    public static Boolean isCNPJValid(final String cnpj) {
        String cnpjUnmask = DocumentFormatterUtil.unmask(cnpj);
        String cnpjPartial = cnpjUnmask.substring(0, 12);

        int d1 = digit(calculateWeight(cnpjPartial.substring(4, 12), 9) + calculateWeight(cnpjPartial.substring(0, 4), 5));
        int d2 = digit((d1 * 2) + calculateWeight(cnpjPartial.substring(5, 12), 9) + calculateWeight(cnpjPartial.substring(0, 5), 6));

        String anObject = (cnpjPartial + d1) + d2;

        return cnpjUnmask.equals(anObject);
    }

    /**
     * Return true if the CPF is valid
     * A valid CPF is unique and have a algorithm to validate it
     * <p>
     * CPF generator could generate a valid or invalid because, somentimes, we need to test a
     * registration with invalid number
     */
    public static Boolean isCPFValid(final String cpf) {
        String cpfUnmask = DocumentFormatterUtil.unmask(cpf);

        String cpfPartial = cpfUnmask.substring(0, 9);

        int d1 = digit(calculateWeight(cpfPartial, 10));
        int d2 = digit((d1 * 2) + calculateWeight(cpfPartial, 11));

        return cpfUnmask.equals((cpfPartial + d1) + d2);
    }


    public static int calculateWeight(final String num, final int weight) {
        int sum = 0;
        int weightAux = weight;

        for (int index = 0; index < num.length(); index++) {
            sum += Integer.parseInt(num.substring(index, index + 1)) * weightAux--;
        }
        return sum;
    }

    public static int digit(int verifyingDigit) {
        if (verifyingDigit % 11 == 0 || verifyingDigit % 11 == 1)
            return 0;
        else
            return 11 - verifyingDigit % 11;
    }

    private static String leftPad(char pad, int length, String string) {
        StringBuilder appender = new StringBuilder();
        for (int i = 0; i < length - string.length(); i++ ) {
            appender.append(pad);
        }
        return appender.append(string).toString();
    }

}
