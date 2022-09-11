package net.datafaker;

import net.datafaker.service.FakeValuesService;
import net.datafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;

/**
 * Provides utility methods for generating fake strings, such as names, phone
 * numbers, addresses. generate random strings with given patterns
 *
 * @author ren
 */
public class MovieFaker extends Faker {

    public MovieFaker() {
        super();
    }

    public MovieFaker(Locale locale) {
        super(locale);
    }

    public MovieFaker(Random random) {
        super(random);
    }

    public MovieFaker(Locale locale, Random random) {
        super(locale, random);
    }

    public MovieFaker(Locale locale, RandomService randomService) {
        super(locale, randomService);
    }

    public MovieFaker(FakeValuesService fakeValuesService, RandomService random) {
        super(fakeValuesService, random);
    }

    public AquaTeenHungerForce aquaTeenHungerForce() {
        return getProvider(AquaTeenHungerForce.class, () -> new AquaTeenHungerForce(this));
    }

    public Avatar avatar() {
        return getProvider(Avatar.class, () -> new Avatar(this));
    }

    public BackToTheFuture backToTheFuture() {
        return getProvider(BackToTheFuture.class, () -> new BackToTheFuture(this));
    }

    public Babylon5 babylon5() {
        return getProvider(Babylon5.class, () -> new Babylon5(this));
    }

    public BigBangTheory bigBangTheory() {
        return getProvider(BigBangTheory.class, () -> new BigBangTheory(this));
    }

    public BojackHorseman bojackHorseman() {
        return getProvider(BojackHorseman.class, () -> new BojackHorseman(this));
    }

    public BossaNova bossaNova() {
        return getProvider(BossaNova.class, () -> new BossaNova(this));
    }

    public BreakingBad breakingBad() {
        return getProvider(BreakingBad.class, () -> new BreakingBad(this));
    }

    public BrooklynNineNine brooklynNineNine() {
        return getProvider(BrooklynNineNine.class, () -> new BrooklynNineNine(this));
    }

    public Buffy buffy() {
        return getProvider(Buffy.class, () -> new Buffy(this));
    }

    public ChuckNorris chuckNorris() {
        return getProvider(ChuckNorris.class, () -> new ChuckNorris(this));
    }

    public DarkSoul darkSoul(){
        return getProvider(DarkSoul.class, () -> new DarkSoul(this));
    }

    public Departed departed() {
        return getProvider(Departed.class, () -> new Departed(this));
    }

    public DragonBall dragonBall() {
        return getProvider(DragonBall.class, () -> new DragonBall(this));
    }

    public DumbAndDumber dumbAndDumber() {
        return getProvider(DumbAndDumber.class, () -> new DumbAndDumber(this));
    }

    public Dune dune() {
        return getProvider(Dune.class, () -> new Dune(this));
    }

    public FinalSpace finalSpace() {
        return getProvider(FinalSpace.class, () -> new FinalSpace(this));
    }

    public Friends friends() {
        return getProvider(Friends.class, () -> new Friends(this));
    }

    public GameOfThrones gameOfThrones() {
        return getProvider(GameOfThrones.class, () -> new GameOfThrones(this));
    }

    public Ghostbusters ghostbusters() {
        return getProvider(Ghostbusters.class, () -> new Ghostbusters(this));
    }

    public HarryPotter harryPotter() {
        return getProvider(HarryPotter.class, () -> new HarryPotter(this));
    }

    public HeyArnold heyArnold() {
        return getProvider(HeyArnold.class, () -> new HeyArnold(this));
    }

    public HitchhikersGuideToTheGalaxy hitchhikersGuideToTheGalaxy() {
        return getProvider(HitchhikersGuideToTheGalaxy.class, () -> new HitchhikersGuideToTheGalaxy(this));
    }

    public Hobbit hobbit() {
        return getProvider(Hobbit.class, () -> new Hobbit(this));
    }


    public HowIMetYourMother howIMetYourMother() {
        return getProvider(HowIMetYourMother.class, () -> new HowIMetYourMother(this));
    }

    public Kaamelott kaamelott() {
        return getProvider(Kaamelott.class, () -> new Kaamelott(this));
    }

    public Lebowski lebowski() {
        return getProvider(Lebowski.class, () -> new Lebowski(this));
    }

    public LordOfTheRings lordOfTheRings() {
        return getProvider(LordOfTheRings.class, () -> new LordOfTheRings(this));
    }

    public MassEffect massEffect() {
        return getProvider(MassEffect.class, () -> new MassEffect(this));
    }

    public Movie movie() {
        return getProvider(Movie.class, () -> new Movie(this));
    }

    public OscarMovie oscarMovie(){return getProvider(OscarMovie.class, () -> new OscarMovie(this));}

    public Pokemon pokemon() {
        return getProvider(Pokemon.class, () -> new Pokemon(this));
    }

    public PrincessBride princessBride() {
        return getProvider(PrincessBride.class, () -> new PrincessBride(this));
    }

    public ResidentEvil residentEvil() {
        return getProvider(ResidentEvil.class, () -> new ResidentEvil(this));
    }

    public RickAndMorty rickAndMorty() {
        return getProvider(RickAndMorty.class, () -> new RickAndMorty(this));
    }

    public RuPaulDragRace ruPaulDragRace() {
        return getProvider(RuPaulDragRace.class, () -> new RuPaulDragRace(this));
    }

    public Seinfeld seinfeld() {
        return getProvider(Seinfeld.class, () -> new Seinfeld(this));
    }

    public Simpsons simpsons() {
        return getProvider(Simpsons.class, () -> new Simpsons(this));
    }

    public StarTrek starTrek() {
        return getProvider(StarTrek.class, () -> new StarTrek(this));
    }
    
    public StarWars starWars() { 
        return getProvider(StarWars.class, () -> new StarWars(this)); 
    }

    public TheItCrowd theItCrowd() {
        return getProvider(TheItCrowd.class, () -> new TheItCrowd(this));
    }

    public TwinPeaks twinPeaks() {
        return getProvider(TwinPeaks.class, () -> new TwinPeaks(this));
    }

    public Witcher witcher() {
        return getProvider(Witcher.class, () -> new Witcher(this));
    }

}
