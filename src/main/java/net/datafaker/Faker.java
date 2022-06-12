package net.datafaker;

import net.datafaker.fileformats.Json;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.RandomService;

import java.nio.file.Path;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * Provides utility methods for generating fake strings, such as names, phone
 * numbers, addresses. generate random strings with given patterns
 *
 * @author ren
 */
public class Faker {
    private final RandomService randomService;
    private final FakeValuesService fakeValuesService;
    private final Map<Class<?>, Object> providersMap = new IdentityHashMap<>();

    public Faker() {
        this(Locale.ENGLISH);
    }

    public Faker(Locale locale) {
        this(locale, new RandomService());
    }

    public Faker(long seed) {
        this(Locale.ENGLISH, seed);
    }

    public Faker(Locale locale, long seed) {
        this(locale, new RandomService(seed));
    }

    public Faker(Locale locale, RandomService randomService) {
        this(new FakeValuesService(locale, randomService), randomService);
    }

    public Faker(FakeValuesService fakeValuesService, RandomService random) {
        this.randomService = random;
        this.fakeValuesService = fakeValuesService;
    }

    /**
     * Constructs Faker instance with default argument.
     *
     * @return {@link Faker#Faker()}
     */
    public static Faker instance() {
        return new Faker();
    }

    /**
     * Constructs Faker instance with provided {@link Locale}.
     *
     * @param locale - {@link Locale}
     * @return {@link Faker#Faker(Locale)}
     */
    public static Faker instance(Locale locale) {
        return new Faker(locale);
    }

    /**
     * Constructs Faker instance with provided {@link long}.
     *
     * @param seed - {@link long}
     * @return {@link Faker#Faker(long)}
     */
    public static Faker instance(long seed) {
        return new Faker(seed);
    }

    /**
     * Constructs Faker instance with provided {@link Locale} and {@link long}.
     *
     * @param locale - {@link Locale}
     * @param seed - {@link long}
     * @return {@link Faker#Faker(Locale, long)}
     */
    public static Faker instance(Locale locale, long seed) {
        return new Faker(locale, seed);
    }

    /**
     * Returns the internal locale being used, or the ROOT locale if no locale has been set.
     * @return Returns locale being used
     */
    public Locale getLocale() {
        return fakeValuesService.getLocalesChain().isEmpty() || fakeValuesService.getLocalesChain().get(0) == null
            ? Locale.ROOT : fakeValuesService.getLocalesChain().get(0);
    }

    public <T> T doWith(Callable<T> callable, Locale locale) {
        final Locale current = fakeValuesService.getCurrentLocale();
        T result;
        try {
            fakeValuesService.setCurrentLocale(locale);
            result = callable.call();
            return result;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            fakeValuesService.setCurrentLocale(current);
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
        return fakeValuesService.numerify(numberString);
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
        return fakeValuesService.letterify(letterString);
    }

    /**
     * Returns a string with the '?' characters in the parameter replaced with random alphabetic
     * characters.
     * <p>
     * For example, the string "12??34" could be replaced with a string like "12AB34".
     */
    public String letterify(String letterString, boolean isUpper) {
        return fakeValuesService.letterify(letterString, isUpper);
    }

    /**
     * Applies both a {@link #numerify(String)} and a {@link #letterify(String)}
     * over the incoming string.
     */
    public String bothify(String string) {
        return fakeValuesService.bothify(string);
    }

    /**
     * Applies both a {@link #numerify(String)} and a {@link #letterify(String)}
     * over the incoming string.
     */
    public String bothify(String string, boolean isUpper) {
        return fakeValuesService.bothify(string, isUpper);
    }

    /**
     * Generates a String that matches the given regular expression.
     */
    public String regexify(String regex) {
        return fakeValuesService.regexify(regex);
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
        return fakeValuesService.examplify(example);
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
        return fakeValuesService().templatify(string, char2replace, options);
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
        return fakeValuesService().templatify(string, optionsMap);
    }

    /**
     * Returns a string with generated csv based number of lines and column expressions.
     * This method will use comma as default delimiter, always prints header and double quote as default quote.
     *
     * <p>
     * For example, it could generate
     * "name_column","last_name_column"
     * "Sabrina","Kihn"
     *
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
     *
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
        return this.randomService;
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
    protected <T> T getProvider(Class<T> clazz, Supplier<T> valueSupplier) {
        T result = (T) providersMap.get(clazz);
        if (result == null) {
            providersMap.putIfAbsent(clazz, valueSupplier.get());
            result = (T) providersMap.get(clazz);
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
        return getProvider(Address.class, () -> new Address(this));
    }

    public Ancient ancient() {
        return getProvider(Ancient.class, () -> new Ancient(this));
    }

    public Animal animal() {
        return getProvider(Animal.class, () -> new Animal(this));
    }

    public App app() {
        return getProvider(App.class, () -> new App(this));
    }

    public Appliance appliance() {
        return getProvider(Appliance.class, () -> new Appliance(this));
    }

    public AquaTeenHungerForce aquaTeenHungerForce() {
        return getProvider(AquaTeenHungerForce.class, () -> new AquaTeenHungerForce(this));
    }

    public Artist artist() {
        return getProvider(Artist.class, () -> new Artist(this));
    }

    public Australia australia() {
        return getProvider(Australia.class, () -> new Australia(this));
    }

    public Avatar avatar() {
        return getProvider(Avatar.class, () -> new Avatar(this));
    }

    public Aviation aviation() {
        return getProvider(Aviation.class, () -> new Aviation(this));
    }

    public Aws aws() {
        return getProvider(Aws.class, () -> new Aws(this));
    }

    public BackToTheFuture backToTheFuture() {
        return getProvider(BackToTheFuture.class, () -> new BackToTheFuture(this));
    }

    public Babylon5 babylon5() {
        return getProvider(Babylon5.class, () -> new Babylon5(this));
    }

    public Barcode barcode() {
        return getProvider(Barcode.class, () -> new Barcode(this));
    }

    public Basketball basketball() {
        return getProvider(Basketball.class, () -> new Basketball(this));
    }

    public Battlefield1 battlefield1() {
        return getProvider(Battlefield1.class, () -> new Battlefield1(this));
    }

    public Beer beer() {
        return getProvider(Beer.class, () -> new Beer(this));
    }

    public BigBangTheory bigBangTheory() {
        return getProvider(BigBangTheory.class, () -> new BigBangTheory(this));
    }

    public BloodType bloodtype(){return getProvider(BloodType.class, () -> new BloodType(this));}

    public BojackHorseman bojackHorseman() {
        return getProvider(BojackHorseman.class, () -> new BojackHorseman(this));
    }

    public Book book() {
        return getProvider(Book.class, () -> new Book(this));
    }

    public Bool bool() {
        return getProvider(Bool.class, () -> new Bool(this));
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

    public Business business() {
        return getProvider(Business.class, () -> new Business(this));
    }

    public Camera camera() {
        return getProvider(Camera.class, () -> new Camera(this));
    }

    public Cannabis cannabis() {
        return getProvider(Cannabis.class, () -> new Cannabis(this));
    }

    public Cat cat() {
        return getProvider(Cat.class, () -> new Cat(this));
    }

    public ChuckNorris chuckNorris() {
        return getProvider(ChuckNorris.class, () -> new ChuckNorris(this));
    }

    public CNPJ cnpj() {
        return getProvider(CNPJ.class, () -> new CNPJ(this));
    }

    public Code code() {
        return getProvider(Code.class, () -> new Code(this));
    }

    public Coffee coffee() {
        return getProvider(Coffee.class, () -> new Coffee(this));
    }

    public Coin coin() {
        return getProvider(Coin.class, () -> new Coin(this));
    }

    public Color color() {
        return getProvider(Color.class, () -> new Color(this));
    }

    public Commerce commerce() {
        return getProvider(Commerce.class, () -> new Commerce(this));
    }

    public Company company() {
        return getProvider(Company.class, () -> new Company(this));
    }

    public Construction construction() {
        return getProvider(Construction.class, () -> new Construction(this));
    }

    public Country country() {
        return getProvider(Country.class, () -> new Country(this));
    }

    public CPF cpf() {
        return getProvider(CPF.class, () -> new CPF(this));
    }

    public Hashing hashing() {
        return getProvider(Hashing.class, () -> new Hashing(this));
    }

    public CryptoCoin cryptoCoin() {
        return getProvider(CryptoCoin.class, () -> new CryptoCoin(this));
    }

    public Currency currency() {
        return getProvider(Currency.class, () -> new Currency(this));
    }

    public DarkSoul darkSoul(){
        return getProvider(DarkSoul.class, () -> new DarkSoul(this));
    }

    public DateAndTime date() {
        return getProvider(DateAndTime.class, () -> new DateAndTime(this));
    }

    public Disease disease() {
        return getProvider(Disease.class, () -> new Disease(this));
    }

    public Demographic demographic() {
        return getProvider(Demographic.class, () -> new Demographic(this));
    }

    public Departed departed() {
        return getProvider(Departed.class, () -> new Departed(this));
    }

    public Dessert dessert() {
        return getProvider(Dessert.class, () -> new Dessert(this));
    }

    public Device device() {
        return getProvider(Device.class, () -> new Device(this));
    }

    public Dog dog() {
        return getProvider(Dog.class, () -> new Dog(this));
    }

    public Domain domain() {
        return getProvider(Domain.class, () -> new Domain(this));
    }

    public DragonBall dragonBall() {
        return getProvider(DragonBall.class, () -> new DragonBall(this));
    }

    public Dune dune() {
        return getProvider(Dune.class, () -> new Dune(this));
    }

    public Educator educator() {
        return getProvider(Educator.class, () -> new Educator(this));
    }

    public EldenRing eldenRing() {
        return getProvider(EldenRing.class, () -> new EldenRing(this));
    }

    public ElderScrolls elderScrolls() {
        return getProvider(ElderScrolls.class, () -> new ElderScrolls(this));
    }

    public EnglandFootBall englandfootball() {
        return getProvider(EnglandFootBall.class, () -> new EnglandFootBall(this));
    }

    public ElectricalComponents electricalComponents() {
        return getProvider(ElectricalComponents.class, () -> new ElectricalComponents(this));
    }

    public Esports esports() {
        return getProvider(Esports.class, () -> new Esports(this));
    }

    public FakeDuration duration() {
        return getProvider(FakeDuration.class, () -> new FakeDuration(this));
    }

    public FamousLastWords famousLastWords() {
        return getProvider(FamousLastWords.class, () -> new FamousLastWords(this));
    }

    public File file() {
        return getProvider(File.class, () -> new File(this));
    }

    public Finance finance() {
        return getProvider(Finance.class, () -> new Finance(this));
    }

    public Food food() {
        return getProvider(Food.class, () -> new Food(this));
    }

    public Football football() {
        return getProvider(Football.class, () -> new Football(this));
    }

    public Formula1 formula1() {
        return getProvider(Formula1.class, () -> new Formula1(this));
    }

    public Friends friends() {
        return getProvider(Friends.class, () -> new Friends(this));
    }

    public FunnyName funnyName() {
        return getProvider(FunnyName.class, () -> new FunnyName(this));
    }

    public GameOfThrones gameOfThrones() {
        return getProvider(GameOfThrones.class, () -> new GameOfThrones(this));
    }

    public Gender gender() {
        return getProvider(Gender.class, () -> new Gender(this));
    }

    public GratefulDead gratefulDead() {
        return getProvider(GratefulDead.class, () -> new GratefulDead(this));
    }

    public GreekPhilosopher greekPhilosopher() {
        return getProvider(GreekPhilosopher.class, () -> new GreekPhilosopher(this));
    }

    public Hacker hacker() {
        return getProvider(Hacker.class, () -> new Hacker(this));
    }

    public HarryPotter harryPotter() {
        return getProvider(HarryPotter.class, () -> new HarryPotter(this));
    }

    public Hearthstone hearthstone() {
        return getProvider(Hearthstone.class, () -> new Hearthstone(this));
    }

    public HeyArnold heyArnold() {
        return getProvider(HeyArnold.class, () -> new HeyArnold(this));
    }

    public Hipster hipster() {
        return getProvider(Hipster.class, () -> new Hipster(this));
    }

    public HitchhikersGuideToTheGalaxy hitchhikersGuideToTheGalaxy() {
        return getProvider(HitchhikersGuideToTheGalaxy.class, () -> new HitchhikersGuideToTheGalaxy(this));
    }

    public Hobbit hobbit() {
        return getProvider(Hobbit.class, () -> new Hobbit(this));
    }

    public Hobby hobby() {
        return getProvider(Hobby.class, () -> new Hobby(this));
    }

    public Horse horse() {
        return getProvider(Horse.class, () -> new Horse(this));
    }

    public House house() {
        return getProvider(House.class, () -> new House(this));
    }

    public HowIMetYourMother howIMetYourMother() {
        return getProvider(HowIMetYourMother.class, () -> new HowIMetYourMother(this));
    }

    public IdNumber idNumber() {
        return getProvider(IdNumber.class, () -> new IdNumber(this));
    }

    public Internet internet() {
        return getProvider(Internet.class, () -> new Internet(this));
    }

    public Job job() {
        return getProvider(Job.class, () -> new Job(this));
    }

    public Kaamelott kaamelott() {
        return getProvider(Kaamelott.class, () -> new Kaamelott(this));
    }

    public Kpop kpop() {
        return getProvider(Kpop.class, () -> new Kpop(this));
    }

    public Lebowski lebowski() {
        return getProvider(Lebowski.class, () -> new Lebowski(this));
    }

    public LeagueOfLegends leagueOfLegends() {
        return getProvider(LeagueOfLegends.class, () -> new LeagueOfLegends(this));
    }

    public LordOfTheRings lordOfTheRings() {
        return getProvider(LordOfTheRings.class, () -> new LordOfTheRings(this));
    }

    public Lorem lorem() {
        return getProvider(Lorem.class, () -> new Lorem(this));
    }

    public Marketing marketing() {
        return getProvider(Marketing.class, () -> new Marketing(this));
    }

    public Matz matz() {
        return getProvider(Matz.class, () -> new Matz(this));
    }

    public Mbti mbti() {return getProvider(Mbti.class, () -> new Mbti(this));}

    public Measurement measurement() {
        return getProvider(Measurement.class, () -> new Measurement(this));
    }

    public Medical medical() {
        return getProvider(Medical.class, () -> new Medical(this));
    }

    public Military military() {
        return getProvider(Military.class, () -> new Military(this));
    }

    public Minecraft minecraft() {
        return getProvider(Minecraft.class, () -> new Minecraft(this));
    }

    public Mood mood() {
        return getProvider(Mood.class, () -> new Mood(this));
    }

    public Mountain mountain() {
        return getProvider(Mountain.class, () -> new Mountain(this));
    }

    public Mountaineering mountaineering() {
        return getProvider(Mountaineering.class, () -> new Mountaineering(this));
    }

    public Movie movie() {
        return getProvider(Movie.class, () -> new Movie(this));
    }

    public Music music() {
        return getProvider(Music.class, () -> new Music(this));
    }

    public Name name() {
        return getProvider(Name.class, () -> new Name(this));
    }

    public Nation nation() {
        return getProvider(Nation.class, () -> new Nation(this));
    }

    public NatoPhoneticAlphabet natoPhoneticAlphabet() {
        return getProvider(NatoPhoneticAlphabet.class, () -> new NatoPhoneticAlphabet(this));
    }

    public Nigeria nigeria() {
        return getProvider(Nigeria.class, () -> new Nigeria(this));
    }

    public Number number() {
        return getProvider(Number.class, () -> new Number(this));
    }

    public Options options() {
        return getProvider(Options.class, () -> new Options(this));
    }

    public Overwatch overwatch() {
        return getProvider(Overwatch.class, () -> new Overwatch(this));
    }

    public OscarMovie oscarMovie(){return getProvider(OscarMovie.class, () -> new OscarMovie(this));}

    public Passport passport() {
        return getProvider(Passport.class, () -> new Passport(this));
    }

    public PhoneNumber phoneNumber() {
        return getProvider(PhoneNumber.class, () -> new PhoneNumber(this));
    }

    public Photography photography() {
        return getProvider(Photography.class, () -> new Photography(this));
    }

    public Pokemon pokemon() {
        return getProvider(Pokemon.class, () -> new Pokemon(this));
    }

    public PrincessBride princessBride() {
        return getProvider(PrincessBride.class, () -> new PrincessBride(this));
    }

    public ProgrammingLanguage programmingLanguage() {
        return getProvider(ProgrammingLanguage.class, () -> new ProgrammingLanguage(this));
    }

    public Relationship relationships() {
        return getProvider(Relationship.class, () -> new Relationship(this));
    }

    public ResidentEvil residentEvil() {
        return getProvider(ResidentEvil.class, () -> new ResidentEvil(this));
    }

    public Restaurant restaurant() {
        return getProvider(Restaurant.class, () -> new Restaurant(this));
    }

    public RickAndMorty rickAndMorty() {
        return getProvider(RickAndMorty.class, () -> new RickAndMorty(this));
    }

    public Robin robin() {
        return getProvider(Robin.class, () -> new Robin(this));
    }

    public RockBand rockBand() {
        return getProvider(RockBand.class, () -> new RockBand(this));
    }

    public RuPaulDragRace ruPaulDragRace() {
        return getProvider(RuPaulDragRace.class, () -> new RuPaulDragRace(this));
    }

    public Science science() {
        return getProvider(Science.class, () -> new Science(this));
    }

    public Seinfeld seinfeld() {
        return getProvider(Seinfeld.class, () -> new Seinfeld(this));
    }

    public SlackEmoji slackEmoji() {
        return getProvider(SlackEmoji.class, () -> new SlackEmoji(this));
    }

    public Shakespeare shakespeare() {
        return getProvider(Shakespeare.class, () -> new Shakespeare(this));
    }

    public Sip sip() {
        return getProvider(Sip.class, () -> new Sip(this));
    }

    public Size size() {
        return getProvider(Size.class, () -> new Size(this));
    }

    public Simpsons simpsons() {
        return getProvider(Simpsons.class, () -> new Simpsons(this));
    }

    public SoulKnight soulKnight() {
        return getProvider(SoulKnight.class, () -> new SoulKnight(this));
    }

    public Space space() {
        return getProvider(Space.class, () -> new Space(this));
    }

    public StarCraft starCraft() {
        return getProvider(StarCraft.class, () -> new StarCraft(this));
    }

    public StarTrek starTrek() {
        return getProvider(StarTrek.class, () -> new StarTrek(this));
    }

    public Stock stock() {
        return getProvider(Stock.class, () -> new Stock(this));
    }

    public Subscription subscription() {
        return getProvider(Subscription.class, () -> new Subscription(this));
    }

    public Superhero superhero() {
        return getProvider(Superhero.class, () -> new Superhero(this));
    }

    public SuperMario superMario() {
        return getProvider(SuperMario.class, () -> new SuperMario(this));
    }

    public Tea tea() {
        return getProvider(Tea.class, () -> new Tea(this));
    }

    public Team team() {
        return getProvider(Team.class, () -> new Team(this));
    }

    public TheItCrowd theItCrowd() {
        return getProvider(TheItCrowd.class, () -> new TheItCrowd(this));
    }

    public Time time() {
        return getProvider(Time.class, () -> new Time(this));
    }

    public Touhou touhou() {
        return getProvider(Touhou.class, () -> new Touhou(this));
    }

    public Tron tron() {
        return getProvider(Tron.class, () -> new Tron(this));
    }

    public TwinPeaks twinPeaks() {
        return getProvider(TwinPeaks.class, () -> new TwinPeaks(this));
    }

    public Twitter twitter() {
        return getProvider(Twitter.class, () -> new Twitter(this));
    }

    public University university() {
        return getProvider(University.class, () -> new University(this));
    }

    public Vehicle vehicle() {
        return getProvider(Vehicle.class, () -> new Vehicle(this));
    }

    public Verb verb() {
        return getProvider(Verb.class, () -> new Verb(this));
    }

    public Volleyball volleyball() {
        return getProvider(Volleyball.class, () -> new Volleyball(this));
    }

    public Weather weather() {
        return getProvider(Weather.class, () -> new Weather(this));
    }

    public Witcher witcher() {
        return getProvider(Witcher.class, () -> new Witcher(this));
    }

    public Yoda yoda() {
        return getProvider(Yoda.class, () -> new Yoda(this));
    }

    public Zelda zelda() {
        return getProvider(Zelda.class, () -> new Zelda(this));
    }

    public String resolve(String key) {
        return this.fakeValuesService.resolve(key, this, this);
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
        return this.fakeValuesService.expression(expression, this);
    }

}
