package net.datafaker;

import net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil;

/**
 * The CPF number (Cadastro de Pessoas Físicas, [sepeˈɛfi]; Portuguese for "Natural Persons Register")
 * is the Brazilian individual taxpayer registry identification, since its creation in 1965. This
 * number is attributed by the Brazilian Federal Revenue to Brazilians and resident aliens who,
 * directly or indirectly, pay taxes in Brazil. It's an 11-digit number in the format 000.000.000-00.
 *
 * @see <a href="https://en.wikipedia.org/wiki/CPF_number">CPF</a>
 * @since 0.8.0
 */
public class CPF {

    private final Faker faker;

    protected CPF(Faker faker) {
        this.faker = faker;
    }

    /**
     * Return valid and formatted
     *
     * @return a valid CPF
     * @see IdNumberGeneratorPtBrUtil#cpf(Faker, boolean, boolean)
     */
    public String valid() {
        return IdNumberGeneratorPtBrUtil.cpf(faker, true, true);
    }

    /**
     * Return valid and formatted
     *
     * @param formatted a (un)formatted CPF
     * @return a valid CPF
     * @see IdNumberGeneratorPtBrUtil#cpf(Faker, boolean, boolean)
     */
    public String valid(boolean formatted) {
        return IdNumberGeneratorPtBrUtil.cpf(faker, formatted, true);
    }

    /**
     * Return invalid and formatted
     *
     * @return an invalid CPF
     * @see IdNumberGeneratorPtBrUtil#cpf(Faker, boolean, boolean)
     */
    public String invalid() {
        return IdNumberGeneratorPtBrUtil.cpf(faker, true, false);
    }

    /**
     * Return invalid and formatted
     *
     * @param formatted a (un)formatted CPF
     * @return an invalid CPF
     * @see IdNumberGeneratorPtBrUtil#cpf(Faker, boolean, boolean)
     */
    public String invalid(boolean formatted) {
        return IdNumberGeneratorPtBrUtil.cpf(faker, formatted, false);
    }

}
