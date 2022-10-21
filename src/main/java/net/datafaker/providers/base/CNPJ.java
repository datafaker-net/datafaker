package net.datafaker.providers.base;

import net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil;

/**
 * The Brazil National Registry of Legal Entities number (CNPJ) is a company identification number that must be obtained from the Department of Federal Revenue prior to the start of any business activities.
 *
 * @see <a href="https://en.wikipedia.org/wiki/CNPJ">CNPJ</a>
 * @since 1.1.0
 */
public class CNPJ extends AbstractProvider<BaseProviders> {

    protected CNPJ(BaseProviders faker) {
        super(faker);
    }

    /**
     * Return valid and formatted
     *
     * @return a valid CNPJ
     * @see IdNumberGeneratorPtBrUtil#cnpj(BaseProviders, boolean, boolean, boolean)
     */
    public String valid() {
        return IdNumberGeneratorPtBrUtil.cnpj(faker, true, true, false);
    }

    /**
     * Return valid and formatted
     *
     * @param formatted a CNPJ (un)formatted
     * @return a valid CNPJ
     * @see IdNumberGeneratorPtBrUtil#cnpj(BaseProviders, boolean, boolean, boolean)
     */
    public String valid(boolean formatted) {
        return IdNumberGeneratorPtBrUtil.cnpj(faker, formatted, true, false);
    }

    /**
     * Return valid and formatted. Also, it generates a multi-branch CNPJ.
     * <p>
     * CNPJ numbers follow this format:
     * {@code AA.AAA.AAA/BBBB-CC }
     * Where A is the number of the company, B is the branch number and C are verification digits.
     * If the parameter {@code multiBranch} is false, B will always be 0001.
     * Otherwise, this number will vary between 0001 and 9999 .
     *
     * @param formatted   a CNPJ (un)formatted
     * @param multiBranch CPNJ from a random company branch
     * @return a valid CNPJ
     * @see IdNumberGeneratorPtBrUtil#cnpj(BaseProviders, boolean, boolean, boolean)
     */
    public String valid(boolean formatted, boolean multiBranch) {
        return IdNumberGeneratorPtBrUtil.cnpj(faker, formatted, true, multiBranch);
    }

    /**
     * Return invalid and formatted
     *
     * @return an invalid CNPJ
     * @see IdNumberGeneratorPtBrUtil#cnpj(BaseProviders, boolean, boolean, boolean)
     */
    public String invalid() {
        return IdNumberGeneratorPtBrUtil.cnpj(faker, true, false, false);
    }

    /**
     * Return invalid and (un)formatted
     *
     * @return an invalid CNPJ
     * @see IdNumberGeneratorPtBrUtil#cnpj(BaseProviders, boolean, boolean, boolean)
     */
    public String invalid(boolean formatted) {
        return IdNumberGeneratorPtBrUtil.cnpj(faker, formatted, false, false);
    }

    /**
     * Return invalid and (un)formatted. Also, it generates a multi-branch CNPJ.
     * <p>
     * CNPJ numbers follow this format:
     * {@code AA.AAA.AAA/BBBB-CC }
     * Where A is the number of the company, B is the branch number and C are verification digits.
     * If the parameter {@code multiBranch} is false, B will always be 0001.
     * Otherwise, this number will vary between 0001 and 9999 .
     *
     * @return an invalid CNPJ
     * @see IdNumberGeneratorPtBrUtil#cnpj(BaseProviders, boolean, boolean, boolean)
     */
    public String invalid(boolean formatted, boolean multiBranch) {
        return IdNumberGeneratorPtBrUtil.cnpj(faker, formatted, false, multiBranch);
    }
}
