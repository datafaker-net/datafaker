package net.datafaker;

import net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil;

/**
 * CNPJ: The Federal Government interest in simplifying its registration procedures on companies appears in the mid-90's,
 * but it was only in 1998, through the SRF Normative Instruction No. 27, that the CNPJ (short for Cadastro Nacional da
 * Pessoa Jurídica in Portuguese, or 'National Registry of Legal Entities') was created, replacing the former system,
 * CGC (short for Cadastro Geral de Contribuintes in Portuguese, or 'General Taxpayers Registry').
 * At the end of 2003 it gained a new impulse by sharing and integrating registration data and fiscal information between States and the Union.
 * In 2009 the MEI (Microempreendedor Individual in Portuguese, or 'Individual Microentrepreneur') was created to supply market demand.
 *
 * @see <a href="https://en.wikipedia.org/wiki/CNPJ">CNPJ</a>
 * @since 1.1.0
 */
public class CNPJ {

    private final Faker faker;

    protected CNPJ(Faker faker) {
        this.faker = faker;
    }

    /**
     * Return valid and formatted
     *
     * @return a valid CNPJ
     * @see IdNumberGeneratorPtBrUtil#cnpj(Faker, boolean, boolean, boolean)
     */
    public String valid() {
        return IdNumberGeneratorPtBrUtil.cnpj(faker, true, true, false);
    }

    /**
     * Return valid and formatted
     *
     * @param formatted a CNPJ (un)formatted
     * @return a valid CNPJ
     * @see IdNumberGeneratorPtBrUtil#cnpj(Faker, boolean, boolean, boolean)
     */
    public String valid(boolean formatted) {
        return IdNumberGeneratorPtBrUtil.cnpj(faker, formatted, true, false);
    }

    /**
     * Return valid and formatted. Also, it generates a multi-branch CNPJ.
     *
     * CNPJ numbers follow this format:
     * {@code AA.AAA.AAA/BBBB-CC }
     * Where A is the number of the company, B is the branch number and C are verification digits.
     * If the parameter {@code multiBranch} is false, B will always be 0001.
     * Otherwise, this number will vary between 0001 and 9999 .
     *
     * @param formatted a CNPJ (un)formatted
     * @param multiBranch CPNJ from a random company branch
     * @return a valid CNPJ
     * @see IdNumberGeneratorPtBrUtil#cnpj(Faker, boolean, boolean, boolean)
     */
    public String valid(boolean formatted, boolean multiBranch) {
        return IdNumberGeneratorPtBrUtil.cnpj(faker, formatted, true, multiBranch);
    }

    /**
     * Return invalid and formatted
     *
     * @return an invalid CNPJ
     * @see IdNumberGeneratorPtBrUtil#cnpj(Faker, boolean, boolean, boolean)
     */
    public String invalid() {
        return IdNumberGeneratorPtBrUtil.cnpj(faker, true, false, false);
    }

    /**
     * Return invalid and (un)formatted
     *
     * @return an invalid CNPJ
     * @see IdNumberGeneratorPtBrUtil#cnpj(Faker, boolean, boolean, boolean)
     */
    public String invalid(boolean formatted) {
        return IdNumberGeneratorPtBrUtil.cnpj(faker, formatted, false, false);
    }

    /**
     * Return invalid and (un)formatted. Also, it generates a multi-branch CNPJ.
     *
     * CNPJ numbers follow this format:
     * {@code AA.AAA.AAA/BBBB-CC }
     * Where A is the number of the company, B is the branch number and C are verification digits.
     * If the parameter {@code multiBranch} is false, B will always be 0001.
     * Otherwise, this number will vary between 0001 and 9999 .
     *
     * @return an invalid CNPJ
     * @see IdNumberGeneratorPtBrUtil#cnpj(Faker, boolean, boolean, boolean)
     */
    public String invalid(boolean formatted, boolean multiBranch) {
        return IdNumberGeneratorPtBrUtil.cnpj(faker, formatted, false, multiBranch);
    }
}
