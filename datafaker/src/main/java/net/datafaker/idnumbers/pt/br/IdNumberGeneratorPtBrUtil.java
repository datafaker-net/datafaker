package net.datafaker.idnumbers.pt.br;

import net.datafaker.providers.base.BaseProviders;

public final class IdNumberGeneratorPtBrUtil {

    private IdNumberGeneratorPtBrUtil() {
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/CNPJ">https://en.wikipedia.org/wiki/CNPJ</a>
     *
     * @param formatted a cnpj (un)formatted
     * @param valid     a cnpj (in)valid
     */
    public static String cnpj(BaseProviders faker, boolean formatted, boolean valid, boolean multiBranch) {
        String cnpj;

        if (valid) {
            StringBuilder partial = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                partial.append(faker.random().nextInt(9));
            }

            if (multiBranch) {
                String string = String.valueOf(faker.random().nextInt(1, 9999));
                partial.append(String.valueOf('0').repeat(Math.max(0, 4 - string.length())));
                partial.append(string);
            } else {
                partial.append("0001");
            }

            cnpj = partial.toString();

            int d1 = digit(calculateWeight(cnpj, 9, 4, 12) + calculateWeight(cnpj, 5, 0, 4));
            int d2 = digit((d1 * 2) + calculateWeight(cnpj, 9, 5, 12) + calculateWeight(cnpj, 6, 0, 5));

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
    public static String cpf(BaseProviders faker, boolean formatted, boolean valid) {
        String cpf;
        if (valid) {
            char[] partial = new char[9];
            for (int i = 0; i < 9; i++) {
                partial[i] = (char)('0' + faker.random().nextInt(9));
            }
            cpf = String.valueOf(partial);

            int d1 = digit(calculateWeight(cpf, 10, 0, cpf.length()));
            int d2 = digit((d1 * 2) + calculateWeight(cpf, 11, 0, cpf.length()));

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
     * A valid CNPJ is unique and have an algorithm to validate it
     * <p>
     * CNPJ generator could generate a valid or invalid because, sometimes, we need to test a
     * registration with invalid number
     */
    public static boolean isCNPJValid(final String cnpj) {
        String cnpjUnmask = DocumentFormatterUtil.unmask(cnpj);
        final int cnpjPartialLength = 12;
        if (!cnpjUnmask.regionMatches(0, cnpjUnmask, 0, cnpjPartialLength)) {
            return false;
        }

        int d1 = digit(calculateWeight(cnpjUnmask, 9, 4, cnpjPartialLength) + calculateWeight(cnpjUnmask, 5, 0, 4));
        int d2 = digit((d1 * 2) + calculateWeight(cnpjUnmask, 9, 5, cnpjPartialLength) + calculateWeight(cnpjUnmask, 6, 0, 5));


        final String other = d1 + "" + d2;
        return cnpjUnmask.regionMatches(cnpjPartialLength, other, 0, other.length());
    }

    /**
     * Return true if the CPF is valid
     * A valid CPF is unique and have a algorithm to validate it
     * <p>
     * CPF generator could generate a valid or invalid because, sometimes, we need to test a
     * registration with invalid number
     */
    public static Boolean isCPFValid(final String cpf) {
        String cpfUnmask = DocumentFormatterUtil.unmask(cpf);

        String cpfPartial = cpfUnmask.substring(0, 9);

        int d1 = digit(calculateWeight(cpfUnmask, 10, 0, 9));
        int d2 = digit((d1 * 2) + calculateWeight(cpfUnmask, 11, 0, 9));

        return cpfUnmask.equals((cpfPartial + d1) + d2);
    }


    public static int calculateWeight(final String num, final int weight, int start, int end) {
        int sum = 0;
        int weightAux = weight;

        for (int index = start; index < end; index++) {
            sum += (num.charAt(index) - '0') * weightAux--;
        }
        return sum;
    }

    public static int digit(int verifyingDigit) {
        int remainder = verifyingDigit % 11;
        if (remainder == 0 || remainder == 1)
            return 0;
        else
            return 11 - remainder;
    }

}
