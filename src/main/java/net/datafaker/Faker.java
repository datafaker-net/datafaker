package net.datafaker;

import net.datafaker.service.FakeValuesService;
import net.datafaker.service.RandomService;

import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
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
        this(locale, (Random) null);
    }

    public Faker(Random random) {
        this(Locale.ENGLISH, random);
    }

    public Faker(Locale locale, Random random) {
        this(locale, new RandomService(random));
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
     * Constructs Faker instance with provided {@link Random}.
     *
     * @param random - {@link Random}
     * @return {@link Faker#Faker(Random)}
     */
    public static Faker instance(Random random) {
        return new Faker(random);
    }

    /**
     * Constructs Faker instance with provided {@link Locale} and {@link Random}.
     *
     * @param locale - {@link Locale}
     * @param random - {@link Random}
     * @return {@link Faker#Faker(Locale, Random)}
     */
    public static Faker instance(Locale locale, Random random) {
        return new Faker(locale, random);
    }

    public Locale getLocale() {
        return fakeValuesService.getLocalesChain().get(0);
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

    public RandomService random() {
        return this.randomService;
    }

    FakeValuesService fakeValuesService() {
        return this.fakeValuesService;
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

    public Beer beer() {
        return getProvider(Beer.class, () -> new Beer(this));
    }

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

    public Buffy buffy() {
        return getProvider(Buffy.class, () -> new Buffy(this));
    }

    public Business business() {
        return getProvider(Business.class, () -> new Business(this));
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

    public Country country() {
        return getProvider(Country.class, () -> new Country(this));
    }

    public CPF cpf() {
        return getProvider(CPF.class, () -> new CPF(this));
    }

    public Crypto crypto() {
        return getProvider(Crypto.class, () -> new Crypto(this));
    }

    public Currency currency() {
        return getProvider(Currency.class, () -> new Currency(this));
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

    public Dessert dessert() {
        return getProvider(Dessert.class, () -> new Dessert(this));
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

    public ElderScrolls elderScrolls() {
        return getProvider(ElderScrolls.class, () -> new ElderScrolls(this));
    }

    public EnglandFootBall englandfootball() {
        return getProvider(EnglandFootBall.class, () -> new EnglandFootBall(this));
    }

    public Esports esports() {
        return getProvider(Esports.class, () -> new Esports(this));
    }

    public FakeDuration duration() {
        return getProvider(FakeDuration.class, () -> new FakeDuration(this));
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

    public Hacker hacker() {
        return getProvider(Hacker.class, () -> new Hacker(this));
    }

    public HarryPotter harryPotter() {
        return getProvider(HarryPotter.class, () -> new HarryPotter(this));
    }

    public Hearthstone hearthstone() {
        return getProvider(Hearthstone.class, () -> new Hearthstone(this));
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

    public Number number() {
        return getProvider(Number.class, () -> new Number(this));
    }

    public Options options() {
        return getProvider(Options.class, () -> new Options(this));
    }

    public Overwatch overwatch() {
        return getProvider(Overwatch.class, () -> new Overwatch(this));
    }

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

    public Superhero superhero() {
        return getProvider(Superhero.class, () -> new Superhero(this));
    }

    public Team team() {
        return getProvider(Team.class, () -> new Team(this));
    }

    public TheItCrowd theItCrowd() {
        return getProvider(TheItCrowd.class, () -> new TheItCrowd(this));
    }

    public Touhou touhou() {
        return getProvider(Touhou.class, () -> new Touhou(this));
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
