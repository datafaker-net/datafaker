package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.ProviderRegistration;

public interface EntertainmentProviders extends ProviderRegistration {
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

    default Boardgame boardgame() {
        return getProvider(Boardgame.class, Boardgame::new);
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

    default CowboyBebop cowboyBebop() {
        return getProvider(CowboyBebop.class, CowboyBebop::new);
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

    default FreshPrinceOfBelAir freshPrinceOfBelAir() {
        return getProvider(FreshPrinceOfBelAir.class, FreshPrinceOfBelAir::new);
    }

    default Friends friends() {
        return getProvider(Friends.class, Friends::new);
    }

    default FullmetalAlchemist fullMetalAlchemist() {
        return getProvider(FullmetalAlchemist.class, FullmetalAlchemist::new);
    }

    default Futurama futurama() {
        return getProvider(Futurama.class, Futurama::new);
    }

    default GameOfThrones gameOfThrones() {
        return getProvider(GameOfThrones.class, GameOfThrones::new);
    }

    default Ghostbusters ghostbusters() {
        return getProvider(Ghostbusters.class, Ghostbusters::new);
    }

    default GratefulDead gratefulDead() {
        return getProvider(GratefulDead.class, GratefulDead::new);
    }

    default GravityFalls gravityFalls() {
        return getProvider(GravityFalls.class, GravityFalls::new);
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

    default HowToTrainYourDragon howToTrainYourDragon() {
        return getProvider(HowToTrainYourDragon.class, HowToTrainYourDragon::new);
    }

    default Joke joke() {
        return getProvider(Joke.class, Joke::new);
    }

    default Kaamelott kaamelott() {
        return getProvider(Kaamelott.class, Kaamelott::new);
    }

    default Lebowski lebowski() {
        return getProvider(Lebowski.class, Lebowski::new);
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

    default Naruto naruto() {
        return getProvider(Naruto.class, Naruto::new);
    }

    default NewGirl newGirl() {
        return getProvider(NewGirl.class, NewGirl::new);
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

    default Severance severance() {
        return getProvider(Severance.class, Severance::new);
    }

    default Show show() {
        return getProvider(Show.class, Show::new);
    }

    default SiliconValley siliconValley() {
        return getProvider(SiliconValley.class, SiliconValley::new);
    }

    default Simpsons simpsons() {
        return getProvider(Simpsons.class, Simpsons::new);
    }

    default SouthPark southPark() {
        return getProvider(SouthPark.class, SouthPark::new);
    }

    default Spongebob spongebob() {
        return getProvider(Spongebob.class, Spongebob::new);
    }

    default Stargate stargate() {
        return getProvider(Stargate.class, Stargate::new);
    }

    default StarTrek starTrek() {
        return getProvider(StarTrek.class, StarTrek::new);
    }

    default StarWars starWars() {
        return getProvider(StarWars.class, StarWars::new);
    }

    default StrangerThings strangerThings() {
        return getProvider(StrangerThings.class, StrangerThings::new);
    }

    default StudioGhibli studioGhibli() {
        return getProvider(StudioGhibli.class, StudioGhibli::new);
    }

    default Suits suits() {
        return getProvider(Suits.class, Suits::new);
    }

    default Supernatural supernatural() {
        return getProvider(Supernatural.class, Supernatural::new);
    }

    default SwordArtOnline swordArtOnline() {
        return getProvider(SwordArtOnline.class, SwordArtOnline::new);
    }

    default TheExpanse theExpanse() {
        return getProvider(TheExpanse.class, TheExpanse::new);
    }

    default TheItCrowd theItCrowd() {
        return getProvider(TheItCrowd.class, TheItCrowd::new);
    }

    default TheKingkillerChronicle theKingkillerChronicle() {
        return getProvider(TheKingkillerChronicle.class, TheKingkillerChronicle::new);
    }

    default TheRoom theRoom() {
        return getProvider(TheRoom.class, TheRoom::new);
    }

    default TheThickOfIt theThickOfIt() {
        return getProvider(TheThickOfIt.class, TheThickOfIt::new);
    }

    default TheVentureBros theVentureBros() {
        return getProvider(TheVentureBros.class, TheVentureBros::new);
    }

    default Tron tron() {
        return getProvider(Tron.class, Tron::new);
    }

    default TwinPeaks twinPeaks() {
        return getProvider(TwinPeaks.class, TwinPeaks::new);
    }

    default VForVendetta vForVendetta() {
        return getProvider(VForVendetta.class, VForVendetta::new);
    }

    default Witcher witcher() {
        return getProvider(Witcher.class, Witcher::new);
    }
}
