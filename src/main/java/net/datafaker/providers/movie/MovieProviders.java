package net.datafaker.providers.movie;

import net.datafaker.providers.base.ProviderRegistration;

public interface MovieProviders extends ProviderRegistration {
    default AquaTeenHungerForce aquaTeenHungerForce() {
        return getProvider(AquaTeenHungerForce.class, AquaTeenHungerForce::new);
    }

    default Avatar avatar() {
        return getProvider(Avatar.class, Avatar::new);
    }

    default BackToTheFuture backToTheFuture() {
        return getProvider(BackToTheFuture.class, BackToTheFuture::new);
    }

    default Babylon5 babylon5() {
        return getProvider(Babylon5.class, Babylon5::new);
    }

    default BigBangTheory bigBangTheory() {
        return getProvider(BigBangTheory.class, BigBangTheory::new);
    }

    default BojackHorseman bojackHorseman() {
        return getProvider(BojackHorseman.class, BojackHorseman::new);
    }

    default BossaNova bossaNova() {
        return getProvider(BossaNova.class, BossaNova::new);
    }

    default BreakingBad breakingBad() {
        return getProvider(BreakingBad.class, BreakingBad::new);
    }

    default BrooklynNineNine brooklynNineNine() {
        return getProvider(BrooklynNineNine.class, BrooklynNineNine::new);
    }

    default Buffy buffy() {
        return getProvider(Buffy.class, Buffy::new);
    }

    default ChuckNorris chuckNorris() {
        return getProvider(ChuckNorris.class, ChuckNorris::new);
    }

    default Departed departed() {
        return getProvider(Departed.class, Departed::new);
    }

    default DetectiveConan detectiveConan() {
        return getProvider(DetectiveConan.class, DetectiveConan::new);
    }

    default DoctorWho doctorWho() {
        return getProvider(DoctorWho.class, DoctorWho::new);
    }

    default Doraemon doraemon() {
        return getProvider(Doraemon.class, Doraemon::new);
    }

    default DragonBall dragonBall() {
        return getProvider(DragonBall.class, DragonBall::new);
    }

    default DumbAndDumber dumbAndDumber() {
        return getProvider(DumbAndDumber.class, DumbAndDumber::new);
    }

    default Dune dune() {
        return getProvider(Dune.class, Dune::new);
    }

    default FamilyGuy familyGuy() {
        return getProvider(FamilyGuy.class, FamilyGuy::new);
    }

    default FinalSpace finalSpace() {
        return getProvider(FinalSpace.class, FinalSpace::new);
    }

    default Friends friends() {
        return getProvider(Friends.class, Friends::new);
    }

    default FullmetalAlchemist fullMetalAlchemist() {
        return getProvider(FullmetalAlchemist.class, FullmetalAlchemist::new);
    }

    default GameOfThrones gameOfThrones() {
        return getProvider(GameOfThrones.class, GameOfThrones::new);
    }

    default Ghostbusters ghostbusters() {
        return getProvider(Ghostbusters.class, Ghostbusters::new);
    }

    default HarryPotter harryPotter() {
        return getProvider(HarryPotter.class, HarryPotter::new);
    }

    default HeyArnold heyArnold() {
        return getProvider(HeyArnold.class, HeyArnold::new);
    }

    default HitchhikersGuideToTheGalaxy hitchhikersGuideToTheGalaxy() {
        return getProvider(
            HitchhikersGuideToTheGalaxy.class, HitchhikersGuideToTheGalaxy::new);
    }

    default Hobbit hobbit() {
        return getProvider(Hobbit.class, Hobbit::new);
    }

    default HowIMetYourMother howIMetYourMother() {
        return getProvider(HowIMetYourMother.class, HowIMetYourMother::new);
    }

    default Kaamelott kaamelott() {
        return getProvider(Kaamelott.class, Kaamelott::new);
    }

    default LordOfTheRings lordOfTheRings() {
        return getProvider(LordOfTheRings.class, LordOfTheRings::new);
    }

    default MoneyHeist moneyHeist() {
        return getProvider(MoneyHeist.class, MoneyHeist::new);
    }

    default Movie movie() {
        return getProvider(Movie.class, Movie::new);
    }

    default OnePiece onePiece() {
        return getProvider(OnePiece.class, OnePiece::new);
    }

    default OscarMovie oscarMovie() {
        return getProvider(OscarMovie.class, OscarMovie::new);
    }

    default Pokemon pokemon() {
        return getProvider(Pokemon.class, Pokemon::new);
    }

    default PrincessBride princessBride() {
        return getProvider(PrincessBride.class, PrincessBride::new);
    }

    default ResidentEvil residentEvil() {
        return getProvider(ResidentEvil.class, ResidentEvil::new);
    }

    default RickAndMorty rickAndMorty() {
        return getProvider(RickAndMorty.class, RickAndMorty::new);
    }

    default RuPaulDragRace ruPaulDragRace() {
        return getProvider(RuPaulDragRace.class, RuPaulDragRace::new);
    }

    default Seinfeld seinfeld() {
        return getProvider(Seinfeld.class, Seinfeld::new);
    }

    default StarTrek starTrek() {
        return getProvider(StarTrek.class, StarTrek::new);
    }

    default StarWars starWars() {
        return getProvider(StarWars.class, StarWars::new);
    }

    default StudioGhibli studioGhibli() {
        return getProvider(StudioGhibli.class, StudioGhibli::new);
    }

    default TheItCrowd theItCrowd() {
        return getProvider(TheItCrowd.class, TheItCrowd::new);
    }

    default TwinPeaks twinPeaks() {
        return getProvider(TwinPeaks.class, TwinPeaks::new);
    }

    default Witcher witcher() {
        return getProvider(Witcher.class, Witcher::new);
    }
}
