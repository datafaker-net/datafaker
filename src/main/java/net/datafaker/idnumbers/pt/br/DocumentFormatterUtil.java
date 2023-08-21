package net.datafaker.idnumbers.pt.br;

public final class DocumentFormatterUtil {

    private DocumentFormatterUtil() {
    }

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
        StringBuilder sb = new StringBuilder(20);
        sb.append(cpf, 0, 3)
                .append('.').append(cpf, 3, 6)
                .append('.').append(cpf, 6, 9)
                .append('-').append(cpf, 9, cpf.length());
        return sb.toString();
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
