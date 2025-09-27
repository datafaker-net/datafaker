package net.datafaker.idnumbers;

import net.datafaker.annotations.InternalApi;
import net.datafaker.idnumbers.pt.br.IdNumberGeneratorPtBrUtil;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;

import static net.datafaker.idnumbers.Utils.birthday;
import static net.datafaker.idnumbers.Utils.gender;

/**
 * Brazilian individual taxpayer number
 */
@InternalApi
public class BrazilIdNumber implements IdNumberGenerator {
    @Override
    public String countryCode() {
        return "BR";
    }

    @Override
    public String generateInvalid(final BaseProviders faker) {
        return IdNumberGeneratorPtBrUtil.cpf(faker, true, false);
    }

    @Override
    public PersonIdNumber generateValid(BaseProviders faker, IdNumberRequest request) {
        String idNumber = IdNumberGeneratorPtBrUtil.cpf(faker, true, true);
        return new PersonIdNumber(idNumber,
            birthday(faker, request), gender(faker, request));
    }
}
