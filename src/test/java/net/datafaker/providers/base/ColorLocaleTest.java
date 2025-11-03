package net.datafaker.providers.base;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ColorLocaleTest extends BaseFakerLocaleTest {

    private final Color colorUA = fakerUA.color();
    private final Color colorUZ = fakerUZ.color();
    private final Color colorCH = fakerCH.color();
    private final Color colorBR = fakerBR.color();
    private final Color colorDK = fakerDK.color();
    private final Color colorJP = fakerJP.color();
    private final Color colorBY = fakerBY.color();
    private final Color colorArabic = fakerArabic.color();
    private final Color colorCA = fakerCA.color();
    private final Color colorCZ = fakerCZ.color();
    private final Color colorDE = fakerDE.color();
    private final Color colorFR = fakerFR.color();
    private final Color colorHU = fakerHU.color();
    private final Color colorAM = fakerAM.color();
    private final Color colorKR = fakerKR.color();
    private final Color colorPT = fakerPT.color();
    private final Color colorRU = fakerRU.color();
    private final Color colorSE = fakerSE.color();
    private final Color colorTH = fakerTH.color();
    private final Color colorTR = fakerTR.color();

    @Override
    protected Stream<Arguments> localeProviderListTest() {
        return Stream.of(
            arguments(TestSpec.of(colorUA::name, "color.name"), fakerUA),
            arguments(TestSpec.of(colorJP::name, "color.name"), fakerJP),
            arguments(TestSpec.of(colorBY::name, "color.name"), fakerBY),
            arguments(TestSpec.of(colorUZ::name, "color.name"), fakerUZ),
            arguments(TestSpec.of(colorArabic::name, "color.name"), fakerArabic),
            arguments(TestSpec.of(colorCA::name, "color.name"), fakerCA),
            arguments(TestSpec.of(colorCZ::name, "color.name"), fakerCZ),
            arguments(TestSpec.of(colorDE::name, "color.name"), fakerDE),
            arguments(TestSpec.of(colorFR::name, "color.name"), fakerFR),
            arguments(TestSpec.of(colorHU::name, "color.name"), fakerHU),
            arguments(TestSpec.of(colorAM::name, "color.name"), fakerAM),
            arguments(TestSpec.of(colorKR::name, "color.name"), fakerKR),
            arguments(TestSpec.of(colorPT::name, "color.name"), fakerPT),
            arguments(TestSpec.of(colorRU::name, "color.name"), fakerRU),
            arguments(TestSpec.of(colorSE::name, "color.name"), fakerSE),
            arguments(TestSpec.of(colorTH::name, "color.name"), fakerTH),
            arguments(TestSpec.of(colorTR::name, "color.name"), fakerTR),
            arguments(TestSpec.of(colorCH::name, "color.name"), fakerCH),
            arguments(TestSpec.of(colorBR::name, "color.name"), fakerBR),
            arguments(TestSpec.of(colorBY::name, "color.name"), fakerBY),
            arguments(TestSpec.of(colorDK::name, "color.name"), fakerDK));
    }
}
