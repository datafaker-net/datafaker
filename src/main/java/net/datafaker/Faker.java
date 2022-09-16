package net.datafaker;

import net.datafaker.fileformats.Json;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;

import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Provides utility methods for generating fake strings, such as names, phone
 * numbers, addresses. generate random strings with given patterns
 *
 * @author ren
 */
public class Faker {
    private final FakerContext context;
    private final FakeValuesService fakeValuesService;
    private static final Map<Class<? extends AbstractProvider>, Map<FakerContext, AbstractProvider>> PROVIDERS_MAP = new ConcurrentHashMap<>();

    public Faker() {
        this(Locale.ENGLISH);
    }

    public Faker(Locale locale) {
        this(locale, (Random) null);
    }

    public Faker(Random random) {
        this(Locale.ENGLISH, random);
    }

    public Faker(Locale locale, Random random) {
        this(locale, new RandomService(random));
    }

    public Faker(Locale locale, RandomService randomService) {
        this(new FakeValuesService(), new FakerContext(locale, randomService));
    }

    public Faker(FakeValuesService fakeValuesService, FakerContext context) {
        this.fakeValuesService = fakeValuesService;
        this.context = context;
        fakeValuesService.updateFakeValuesInterfaceMap(context.getLocaleChain());
    }

    /**
     * Constructs Faker instance with default argument.
     *
     * @deprecated Use the default constructor instead
     * @return {@link Faker#Faker()}
     */
    @Deprecated // Use the default constructor instead
    public static Faker instance() {
        return new Faker();
    }

    /**
     * Constructs Faker instance with provided {@link Locale}.
     *
     * @deprecated Use the contructor with locale instead
     * @param locale - {@link Locale}
     * @return {@link Faker#Faker(Locale)}
     */
    @Deprecated
    public static Faker instance(Locale locale) {
        return new Faker(locale);
    }

    /**
     * Constructs Faker instance with provided {@link Random}.
     *
     * @deprecated Use the contructor with random instead
     * @param random - {@link Random}
     * @return {@link Faker#Faker(Random)}
     */
    @Deprecated
    public static Faker instance(Random random) {
        return new Faker(random);
    }

    /**
     * Constructs Faker instance with provided {@link Locale} and {@link Random}.
     *
     * @deprecated Use the contructor with locale and random instead
     * @param locale - {@link Locale}
     * @param random - {@link Random}
     * @return {@link Faker#Faker(Locale, Random)}
     */
    @Deprecated
    public static Faker instance(Locale locale, Random random) {
        return new Faker(locale, random);
    }

    /**
     * Returns the internal locale being used, or the ROOT locale if no locale has been set.
     * @return Returns locale being used
     */
    public Locale getLocale() {
        return context.getLocale() == null
            ? Locale.ROOT : context.getLocale();
    }

    public FakerContext getContext() {
        return context;
    }

    public <T> T doWith(Callable<T> callable, Locale locale) {
        final Locale current = context.getLocale();
        T result;
        try {
            context.setCurrentLocale(locale);
            fakeValuesService.updateFakeValuesInterfaceMap(context.getLocaleChain());
            result = callable.call();
            return result;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            context.setCurrentLocale(current);
            fakeValuesService.updateFakeValuesInterfaceMap(context.getLocaleChain());
        }
    }

    public <T> T doWith(Callable<T> callable, long seed) {
        final RandomService current = context.getRandomService();
        T result;
        try {
            context.setRandomService(new RandomService(new Random(seed)));
            result = callable.call();
            return result;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            context.setRandomService(current);
        }
    }

    public <T> T doWith(Callable<T> callable, Locale locale, long seed) {
        final Locale currentLocale = context.getLocale();
        final RandomService currentRandomService = context.getRandomService();
        T result;
        try {
            context.setRandomService(new RandomService(new Random(seed)));
            context.setCurrentLocale(locale);
            result = callable.call();
            return result;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            context.setRandomService(currentRandomService);
            context.setCurrentLocale(currentLocale);
        }
    }


    /**
     * Returns a string with the '#' characters in the parameter replaced with random digits between 0-9 inclusive.
     * <p>
     * For example, the string "ABC##EFG" could be replaced with a string like "ABC99EFG".
     *
     * @param numberString Template for string generation
     * @return Generated string
     */
    public String numerify(String numberString) {
        return fakeValuesService.numerify(numberString, context);
    }

    /**
     * Returns a string with the '?' characters in the parameter replaced with random alphabetic
     * characters.
     * <p>
     * For example, the string "12??34" could be replaced with a string like "12AB34".
     *
     * @param letterString Template for string generation
     * @return Generated string.
     */
    public String letterify(String letterString) {
        return fakeValuesService.letterify(letterString, context);
    }

    /**
     * Returns a string with the '?' characters in the parameter replaced with random alphabetic
     * characters.
     * <p>
     * For example, the string "12??34" could be replaced with a string like "12AB34".
     */
    public String letterify(String letterString, boolean isUpper) {
        return fakeValuesService.letterify(letterString, context, isUpper);
    }

    /**
     * Applies both a {@link #numerify(String)} and a {@link #letterify(String)}
     * over the incoming string.
     */
    public String bothify(String string) {
        return fakeValuesService.bothify(string, context);
    }

    /**
     * Applies both a {@link #numerify(String)} and a {@link #letterify(String)}
     * over the incoming string.
     */
    public String bothify(String string, boolean isUpper) {
        return fakeValuesService.bothify(string, context, isUpper);
    }

    /**
     * Generates a String that matches the given regular expression.
     */
    public String regexify(String regex) {
        return fakeValuesService.regexify(regex, context);
    }

    /**
     * Generates a String by example. The output string will have the same pattern as the input string.
     * <p>
     * For example:
     * "AAA" becomes "KRA"
     * "abc" becomes "uio"
     * "948" becomes "345"
     * "A19c" becomes "Z20d"
     *
     * @param example The input string
     * @return The output string based on the input pattern
     */
    public String examplify(String example) {
        return fakeValuesService.examplify(example, context);
    }

    /**
     * Returns a string with the char2replace characters in the parameter replaced with random option from available options.
     * <p>
     * For example, the string "ABC??EFG" could be replaced with a string like "ABCtestтестEFG"
     * if passed options are new String[]{"test", "тест"}.
     *
     * @param string       Template for string generation
     * @param char2replace Char to replace
     * @param options      Options to use while filling the template
     * @return Generated string
     */
    public String templatify(String string, char char2replace, String... options) {
        return fakeValuesService().templatify(string, char2replace, context, options);
    }

    /**
     * Returns a string with the characters in the keys of optionsMap parameter replaced with random option from values.
     *
     * <p>
     * For example, the string "ABC$$EFG" could be replaced with a string like "ABCtestтестEFG"
     * if passed for key '$' there is value new String[]{"test", "тест"} in optionsMap
     *
     * @param string     Template for string generation
     * @param optionsMap Map with replacement rules
     * @return Generated string
     */
    public String templatify(String string, Map<Character, String[]> optionsMap) {
        return fakeValuesService().templatify(string, optionsMap, context);
    }

    /**
     * Returns a string with generated csv based number of lines and column expressions.
     * This method will use comma as default delimiter, always prints header and double quote as default quote.
     *
     * <p>
     * For example, it could generate
     * "name_column","last_name_column"
     * "Sabrina","Kihn"
     * <p>
     * for expression {@code faker.expression("#{csv '1','name_column','#{Name.first_name}','last_name_column','#{Name.last_name}'}");}
     *
     * @param limit             Number of lines
     * @param columnExpressions Even number of expressions.
     *                          The odd numbers args are used for columns names, and even for column values.
     * @return Generated string
     */
    public String csv(int limit, String ... columnExpressions) {
        return fakeValuesService().csv(limit, columnExpressions);
    }

    /**
     * Returns a string with generated csv based number of lines and column expressions.
     *
     * <p>
     * For example, it could generate
     * "Thad" ### "Crist"
     * "Kathryne" ### "Wuckert"
     * "Sybil" ### "Connelly"
     * <p>
     * for expression {@code faker.expression("#{csv ' ### ','"','false','3','name_column','#{Name.first_name}','last_name_column','#{Name.last_name}'}");}
     *
     * @param separator         Delimiter to use
     * @param quote             Quote to use
     * @param withHeader        Print header or not
     * @param limit             Number of lines
     * @param columnExpressions Even number of expressions.
     *                          The odd numbers args are used for columns names, and even for column values.
     * @return Generated string
     */
    public String csv(String separator, char quote, boolean withHeader, int limit, String ... columnExpressions) {
        return fakeValuesService().csv(separator, quote, withHeader, limit, columnExpressions);
    }

    public Json json(String... fieldExpressions) {
        return fakeValuesService().json(fieldExpressions);
    }

    public Json jsona(String... fieldExpressions) {
        return fakeValuesService().jsona(fieldExpressions);
    }

    public RandomService random() {
        return this.context.getRandomService();
    }

    FakeValuesService fakeValuesService() {
        return this.fakeValuesService;
    }

    /**
     * Allows to add paths to files with custom data. Data should be in YAML format.
     *
     * @param locale        the locale for which a path is going to be added.
     * @param path          path to a file with YAML structure
     * @throws IllegalArgumentException in case of invalid path
     */
    public void addPath(Locale locale, Path path) {
        fakeValuesService().addPath(locale, path);
    }

    @SuppressWarnings("unchecked")
    public static <T extends AbstractProvider> T getProvider(Class<T> clazz, Function<Faker, T> valueSupplier, Faker faker) {
        PROVIDERS_MAP.putIfAbsent(clazz, new ConcurrentHashMap<>());
        Map<FakerContext, AbstractProvider> map = PROVIDERS_MAP.get(clazz);
        T result = (T) map.get(faker.getContext());
        if (result == null) {
            result = valueSupplier.apply(faker);
            map.putIfAbsent(faker.getContext(), result);
        }
        return result;
    }

    /**
     *
     * @return builder to build {@code FakeCollection}
     */
    public <T> FakeCollection.Builder<T> collection() {
        return new FakeCollection.Builder<T>().faker(this);
    }

    @SafeVarargs
    public final <T> FakeCollection.Builder<T> collection(Supplier<T>... suppliers) {
        return new FakeCollection.Builder<>(suppliers).faker(this);
    }

    public final <T> FakeCollection.Builder<T> collection(List<Supplier<T>> suppliers) {
        return new FakeCollection.Builder<>(suppliers).faker(this);
    }

    public Address address() {
        return getProvider(Address.class, Address::new, this);
    }

    public Ancient ancient() {
        return getProvider(Ancient.class, Ancient::new, this);
    }

    public Animal animal() {
        return getProvider(Animal.class, Animal::new, this);
    }

    public App app() {
        return getProvider(App.class, App::new, this);
    }

    public Appliance appliance() {
        return getProvider(Appliance.class, Appliance::new, this);
    }

    public AquaTeenHungerForce aquaTeenHungerForce() {
        return getProvider(AquaTeenHungerForce.class, AquaTeenHungerForce::new, this);
    }

    public Artist artist() {
        return getProvider(Artist.class, Artist::new, this);
    }

    public Australia australia() {
        return getProvider(Australia.class, Australia::new, this);
    }

    public Avatar avatar() {
        return getProvider(Avatar.class, Avatar::new, this);
    }

    public Aviation aviation() {
        return getProvider(Aviation.class, Aviation::new, this);
    }

    public Aws aws() {
        return getProvider(Aws.class, Aws::new, this);
    }

    public BackToTheFuture backToTheFuture() {
        return getProvider(BackToTheFuture.class, BackToTheFuture::new, this);
    }

    public Babylon5 babylon5() {
        return getProvider(Babylon5.class, Babylon5::new, this);
    }

    public Barcode barcode() {
        return getProvider(Barcode.class, Barcode::new, this);
    }

    public Basketball basketball() {
        return getProvider(Basketball.class, Basketball::new, this);
    }

    public Battlefield1 battlefield1() {
        return getProvider(Battlefield1.class, Battlefield1::new, this);
    }

    public Beer beer() {
        return getProvider(Beer.class, Beer::new, this);
    }

    public BigBangTheory bigBangTheory() {
        return getProvider(BigBangTheory.class, BigBangTheory::new, this);
    }

    public BloodType bloodtype(){return getProvider(BloodType.class, BloodType::new, this);}

    public BojackHorseman bojackHorseman() {
        return getProvider(BojackHorseman.class, BojackHorseman::new, this);
    }

    public Book book() {
        return getProvider(Book.class, Book::new, this);
    }

    public Bool bool() {
        return getProvider(Bool.class, Bool::new, this);
    }

    public BossaNova bossaNova() {
        return getProvider(BossaNova.class, BossaNova::new, this);
    }

    public BreakingBad breakingBad() {
        return getProvider(BreakingBad.class, BreakingBad::new, this);
    }

    public BrooklynNineNine brooklynNineNine() {
        return getProvider(BrooklynNineNine.class, BrooklynNineNine::new, this);
    }

    public Buffy buffy() {
        return getProvider(Buffy.class, Buffy::new, this);
    }

    public Business business() {
        return getProvider(Business.class, Business::new, this);
    }

    public Camera camera() {
        return getProvider(Camera.class, Camera::new, this);
    }

    public Cannabis cannabis() {
        return getProvider(Cannabis.class, Cannabis::new, this);
    }

    public Cat cat() {
        return getProvider(Cat.class, Cat::new, this);
    }

    public ChuckNorris chuckNorris() {
        return getProvider(ChuckNorris.class, ChuckNorris::new, this);
    }

    public ClashOfClans clashOfClans() {
        return getProvider(ClashOfClans.class, ClashOfClans::new, this);
    }

    public Chiquito chiquito() {
        return getProvider(Chiquito.class, Chiquito::new, this);
    }

    public CNPJ cnpj() {
        return getProvider(CNPJ.class, CNPJ::new, this);
    }

    public Code code() {
        return getProvider(Code.class, Code::new, this);
    }

    public Coffee coffee() {
        return getProvider(Coffee.class, Coffee::new, this);
    }

    public Coin coin() {
        return getProvider(Coin.class, Coin::new, this);
    }

    public Color color() {
        return getProvider(Color.class, Color::new, this);
    }

    public Commerce commerce() {
        return getProvider(Commerce.class, Commerce::new, this);
    }

    public Community community() {
        return getProvider(Community.class, Community::new, this);
    }

    public Company company() {
        return getProvider(Company.class, Company::new, this);
    }

    public Computer computer() {
        return getProvider(Computer.class, Computer::new, this);
    }

    public Construction construction() {
        return getProvider(Construction.class, Construction::new, this);
    }

    public Money money() {
        return getProvider(Money.class, Money::new, this);
    }

    public Country country() {
        return getProvider(Country.class, Country::new, this);
    }

    public CPF cpf() {
        return getProvider(CPF.class, CPF::new, this);
    }

    public Hashing hashing() {
        return getProvider(Hashing.class, Hashing::new, this);
    }

    public CryptoCoin cryptoCoin() {
        return getProvider(CryptoCoin.class, CryptoCoin::new, this);
    }

    public Currency currency() {
        return getProvider(Currency.class, Currency::new, this);
    }

    public DarkSoul darkSoul(){
        return getProvider(DarkSoul.class, DarkSoul::new, this);
    }

    public DateAndTime date() {
        return getProvider(DateAndTime.class, DateAndTime::new, this);
    }

    public Disease disease() {
        return getProvider(Disease.class, Disease::new, this);
    }

    public Demographic demographic() {
        return getProvider(Demographic.class, Demographic::new, this);
    }

    public DcComics dcComics () {
        return getProvider(DcComics.class, DcComics::new, this);
    }

    public Departed departed() {
        return getProvider(Departed.class, Departed::new, this);
    }

    public Dessert dessert() {
        return getProvider(Dessert.class, Dessert::new, this);
    }

    public Device device() {
        return getProvider(Device.class, Device::new, this);
    }

    public Dog dog() {
        return getProvider(Dog.class, Dog::new, this);
    }

    public Domain domain() {
        return getProvider(Domain.class, Domain::new, this);
    }

    public DragonBall dragonBall() {
        return getProvider(DragonBall.class, DragonBall::new, this);
    }

    public DrivingLicense drivingLicense() {
        return getProvider(DrivingLicense.class, DrivingLicense::new, this);
    }

    public DumbAndDumber dumbAndDumber() {
        return getProvider(DumbAndDumber.class, DumbAndDumber::new, this);
    }

    public Dune dune() {
        return getProvider(Dune.class, Dune::new, this);
    }

    public Educator educator() {
        return getProvider(Educator.class, Educator::new, this);
    }

    public EldenRing eldenRing() {
        return getProvider(EldenRing.class, EldenRing::new, this);
    }

    public ElderScrolls elderScrolls() {
        return getProvider(ElderScrolls.class, ElderScrolls::new, this);
    }

    public EnglandFootBall englandfootball() {
        return getProvider(EnglandFootBall.class, EnglandFootBall::new, this);
    }

    public ElectricalComponents electricalComponents() {
        return getProvider(ElectricalComponents.class, ElectricalComponents::new, this);
    }

    public Esports esports() {
        return getProvider(Esports.class, Esports::new, this);
    }

    public FakeDuration duration() {
        return getProvider(FakeDuration.class, FakeDuration::new, this);
    }

    public Fallout fallout() {
        return getProvider(Fallout.class, Fallout::new, this);
    }

    public FamousLastWords famousLastWords() {
        return getProvider(FamousLastWords.class, FamousLastWords::new, this);
    }

    public File file() {
        return getProvider(File.class, File::new, this);
    }

    public FinalSpace finalSpace() {
        return getProvider(FinalSpace.class, FinalSpace::new, this);
    }

    public Finance finance() {
        return getProvider(Finance.class, Finance::new, this);
    }

    public Food food() {
        return getProvider(Food.class, Food::new, this);
    }

    public Football football() {
        return getProvider(Football.class, Football::new, this);
    }

    public Formula1 formula1() {
        return getProvider(Formula1.class, Formula1::new, this);
    }

    public Friends friends() {
        return getProvider(Friends.class, Friends::new, this);
    }

    public FunnyName funnyName() {
        return getProvider(FunnyName.class, FunnyName::new, this);
    }

    public GameOfThrones gameOfThrones() {
        return getProvider(GameOfThrones.class, GameOfThrones::new, this);
    }

    public GarmentSize garmentSize() {
      return getProvider(GarmentSize.class, GarmentSize::new, this);
    }

    public Gender gender() {
        return getProvider(Gender.class, Gender::new, this);
    }

    public Ghostbusters ghostbusters() {
        return getProvider(Ghostbusters.class, Ghostbusters::new, this);
    }

    public GratefulDead gratefulDead() {
        return getProvider(GratefulDead.class, GratefulDead::new, this);
    }

    public GreekPhilosopher greekPhilosopher() {
        return getProvider(GreekPhilosopher.class, GreekPhilosopher::new, this);
    }

    public Hacker hacker() {
        return getProvider(Hacker.class, Hacker::new, this);
    }

    public HarryPotter harryPotter() {
        return getProvider(HarryPotter.class, HarryPotter::new, this);
    }

    public Hearthstone hearthstone() {
        return getProvider(Hearthstone.class, Hearthstone::new, this);
    }

    public HeyArnold heyArnold() {
        return getProvider(HeyArnold.class, HeyArnold::new, this);
    }

    public Hipster hipster() {
        return getProvider(Hipster.class, Hipster::new, this);
    }

    public HitchhikersGuideToTheGalaxy hitchhikersGuideToTheGalaxy() {
        return getProvider(HitchhikersGuideToTheGalaxy.class, HitchhikersGuideToTheGalaxy::new, this);
    }

    public Hobbit hobbit() {
        return getProvider(Hobbit.class, Hobbit::new, this);
    }

    public Hobby hobby() {
        return getProvider(Hobby.class, Hobby::new, this);
    }

    public Hololive hololive() {
        return getProvider(Hololive.class, Hololive::new, this);
    }

    public Horse horse() {
        return getProvider(Horse.class, Horse::new, this);
    }

    public House house() {
        return getProvider(House.class, House::new, this);
    }

    public HowIMetYourMother howIMetYourMother() {
        return getProvider(HowIMetYourMother.class, HowIMetYourMother::new, this);
    }

    public IdNumber idNumber() {
        return getProvider(IdNumber.class, IdNumber::new, this);
    }

    public IndustrySegments industrySegments() {
        return getProvider(IndustrySegments.class, IndustrySegments::new, this);
    }

    public Internet internet() {
        return getProvider(Internet.class, Internet::new, this);
    }

    public Job job() {
        return getProvider(Job.class, Job::new, this);
    }

    public Kaamelott kaamelott() {
        return getProvider(Kaamelott.class, Kaamelott::new, this);
    }

    public Kpop kpop() {
        return getProvider(Kpop.class, Kpop::new, this);
    }

    public Lebowski lebowski() {
        return getProvider(Lebowski.class, Lebowski::new, this);
    }

    public LeagueOfLegends leagueOfLegends() {
        return getProvider(LeagueOfLegends.class, LeagueOfLegends::new, this);
    }

    public LordOfTheRings lordOfTheRings() {
        return getProvider(LordOfTheRings.class, LordOfTheRings::new, this);
    }

    public Lorem lorem() {
        return getProvider(Lorem.class, Lorem::new, this);
    }

    public Marketing marketing() {
        return getProvider(Marketing.class, Marketing::new, this);
    }

    public MassEffect massEffect() {
        return getProvider(MassEffect.class, MassEffect::new, this);
    }

    public Matz matz() {
        return getProvider(Matz.class, Matz::new, this);
    }

    public Mbti mbti() {return getProvider(Mbti.class, Mbti::new, this);}

    public Measurement measurement() {
        return getProvider(Measurement.class, Measurement::new, this);
    }

    public Medical medical() {
        return getProvider(Medical.class, Medical::new, this);
    }

    public Military military() {
        return getProvider(Military.class, Military::new, this);
    }

    public Minecraft minecraft() {
        return getProvider(Minecraft.class, Minecraft::new, this);
    }

    public Mood mood() {
        return getProvider(Mood.class, Mood::new, this);
    }

    public Mountain mountain() {
        return getProvider(Mountain.class, Mountain::new, this);
    }

    public Mountaineering mountaineering() {
        return getProvider(Mountaineering.class, Mountaineering::new, this);
    }

    public Movie movie() {
        return getProvider(Movie.class, Movie::new, this);
    }

    public Music music() {
        return getProvider(Music.class, Music::new, this);
    }

    public Name name() {
        return getProvider(Name.class, Name::new, this);
    }

    public Nation nation() {
        return getProvider(Nation.class, Nation::new, this);
    }

    public NatoPhoneticAlphabet natoPhoneticAlphabet() {
        return getProvider(NatoPhoneticAlphabet.class, NatoPhoneticAlphabet::new, this);
    }

    public Nigeria nigeria() {
        return getProvider(Nigeria.class, Nigeria::new, this);
    }

    public Number number() {
        return getProvider(Number.class, Number::new, this);
    }

    public Options options() {
        return getProvider(Options.class, Options::new, this);
    }

    public Overwatch overwatch() {
        return getProvider(Overwatch.class, Overwatch::new, this);
    }

    public OscarMovie oscarMovie(){return getProvider(OscarMovie.class, OscarMovie::new, this);}

    public Passport passport() {
        return getProvider(Passport.class, Passport::new, this);
    }

    public Password password() {
        return getProvider(Password.class, Password::new, this);
    }

    public PhoneNumber phoneNumber() {
        return getProvider(PhoneNumber.class, PhoneNumber::new, this);
    }

    public Photography photography() {
        return getProvider(Photography.class, Photography::new, this);
    }

    public Pokemon pokemon() {
        return getProvider(Pokemon.class, Pokemon::new, this);
    }

    public PrincessBride princessBride() {
        return getProvider(PrincessBride.class, PrincessBride::new, this);
    }

    public ProgrammingLanguage programmingLanguage() {
        return getProvider(ProgrammingLanguage.class, ProgrammingLanguage::new, this);
    }

    public Relationship relationships() {
        return getProvider(Relationship.class, Relationship::new, this);
    }

    public ResidentEvil residentEvil() {
        return getProvider(ResidentEvil.class, ResidentEvil::new, this);
    }

    public Restaurant restaurant() {
        return getProvider(Restaurant.class, Restaurant::new, this);
    }

    public RickAndMorty rickAndMorty() {
        return getProvider(RickAndMorty.class, RickAndMorty::new, this);
    }

    public Robin robin() {
        return getProvider(Robin.class, Robin::new, this);
    }

    public RockBand rockBand() {
        return getProvider(RockBand.class, RockBand::new, this);
    }

    public RuPaulDragRace ruPaulDragRace() {
        return getProvider(RuPaulDragRace.class, RuPaulDragRace::new, this);
    }

    public Science science() {
        return getProvider(Science.class, Science::new, this);
    }

    public Seinfeld seinfeld() {
        return getProvider(Seinfeld.class, Seinfeld::new, this);
    }

    public SlackEmoji slackEmoji() {
        return getProvider(SlackEmoji.class, SlackEmoji::new, this);
    }

    public Shakespeare shakespeare() {
        return getProvider(Shakespeare.class, Shakespeare::new, this);
    }

    public Sip sip() {
        return getProvider(Sip.class, Sip::new, this);
    }

    public Size size() {
        return getProvider(Size.class, Size::new, this);
    }

    public Simpsons simpsons() {
        return getProvider(Simpsons.class, Simpsons::new, this);
    }

    public SoulKnight soulKnight() {
        return getProvider(SoulKnight.class, SoulKnight::new, this);
    }

    public Space space() {
        return getProvider(Space.class, Space::new, this);
    }

    public StarCraft starCraft() {
        return getProvider(StarCraft.class, StarCraft::new, this);
    }

    public StarTrek starTrek() {
        return getProvider(StarTrek.class, StarTrek::new, this);
    }
    
    public StarWars starWars() { 
        return getProvider(StarWars.class, StarWars::new, this);
    }

    public Stock stock() {
        return getProvider(Stock.class, Stock::new, this);
    }

    public Subscription subscription() {
        return getProvider(Subscription.class, Subscription::new, this);
    }

    public Superhero superhero() {
        return getProvider(Superhero.class, Superhero::new, this);
    }

    public SuperMario superMario() {
        return getProvider(SuperMario.class, SuperMario::new, this);
    }

    public Tea tea() {
        return getProvider(Tea.class, Tea::new, this);
    }

    public Team team() {
        return getProvider(Team.class, Team::new, this);
    }

    public TheItCrowd theItCrowd() {
        return getProvider(TheItCrowd.class, TheItCrowd::new, this);
    }

    public Time time() {
        return getProvider(Time.class, Time::new, this);
    }

    public Touhou touhou() {
        return getProvider(Touhou.class, Touhou::new, this);
    }

    public Tron tron() {
        return getProvider(Tron.class, Tron::new, this);
    }

    public TwinPeaks twinPeaks() {
        return getProvider(TwinPeaks.class, TwinPeaks::new, this);
    }

    public Twitter twitter() {
        return getProvider(Twitter.class, Twitter::new, this);
    }

    public Unique unique() {
        return getProvider(Unique.class, Unique::new, this);
    }

    public University university() {
        return getProvider(University.class, University::new, this);
    }

    public Vehicle vehicle() {
        return getProvider(Vehicle.class, Vehicle::new, this);
    }

    public Verb verb() {
        return getProvider(Verb.class, Verb::new, this);
    }

    public Volleyball volleyball() {
        return getProvider(Volleyball.class, Volleyball::new, this);
    }

    public Weather weather() {
        return getProvider(Weather.class, Weather::new, this);
    }

    public Witcher witcher() {
        return getProvider(Witcher.class, Witcher::new, this);
    }

    public Yoda yoda() {
        return getProvider(Yoda.class, Yoda::new, this);
    }

    public Zelda zelda() {
        return getProvider(Zelda.class, Zelda::new, this);
    }


    public String resolve(String key) {
        return this.fakeValuesService.resolve(key, this, this, context);
    }

    public String resolve(String key, Supplier<String> message) {
        return this.fakeValuesService.resolve(key, this, this, message, context);
    }

    /**
     * Allows the evaluation of native YML expressions to allow you to build your
     * own.
     * <p>
     * The following are valid expressions:
     * <ul>
     * <li>#{regexify '(a|b){2,3}'}</li>
     * <li>#{regexify '\\.\\*\\?\\+'}</li>
     * <li>#{bothify '????','false'}</li>
     * <li>#{Name.first_name} #{Name.first_name} #{Name.last_name}</li>
     * <li>#{number.number_between '1','10'}</li>
     * </ul>
     *
     * @param expression (see examples above)
     * @return the evaluated string expression
     * @throws RuntimeException if unable to evaluate the expression
     */
    public String expression(String expression) {
        return this.fakeValuesService.expression(expression, this, getContext());
    }

}
