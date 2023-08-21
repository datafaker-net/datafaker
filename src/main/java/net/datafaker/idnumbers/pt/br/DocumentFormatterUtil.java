package net.datafaker.idnumbers.pt.br;

public final class DocumentFormatterUtil {

    public static final String D_REGEX = "\\D+";

    private DocumentFormatterUtil() {
    }

    private static final String PATTERN_CPF = "([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})";

    private static final String PATTERN_CNPJ = "([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})";

    public static String cnpj(String cnpj) {
        StringBuilder sb = new StringBuilder(20);
        sb.append(cnpj, 0, 2)
                .append('.').append(cnpj, 2, 5)
                .append('.').append(cnpj, 5, 8)
                .append('/').append(cnpj, 8, 12)
                .append('-').append(cnpj, 12, cnpj.length());
        return sb.toString();
    }

    public static String cpf(String cpf) {
        return cpf.replaceAll(PATTERN_CPF, "$1.$2.$3-$4");
    }

    public static String unmask(String doc) {
        StringBuilder res = new StringBuilder(doc.length());
        for (int i = 0; i < doc.length(); i++) {
            final char c = doc.charAt(i);
            if (Character.isDigit(c)) {
                res.append(c);
            }
        }
        return res.toString();
    }

}
