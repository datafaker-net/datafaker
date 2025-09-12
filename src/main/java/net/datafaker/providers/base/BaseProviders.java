package net.datafaker.providers.base;

import net.datafaker.providers.healthcare.HealthcareFaker;

/**
 * The interface to register providers for {@link BaseFaker}.
 */
public interface BaseProviders extends ProviderRegistration {

    default Address address() {
        return getProvider(Address.class, Address::new);
    }

    default Ancient ancient() {
        return getProvider(Ancient.class, Ancient::new);
    }

    default Animal animal() {
        return getProvider(Animal.class, Animal::new);
    }

    default App app() {
        return getProvider(App.class, App::new);
    }

    default Appliance appliance() {
        return getProvider(Appliance.class, Appliance::new);
    }

    default Artist artist() {
        return getProvider(Artist.class, Artist::new);
    }

    default Australia australia() {
        return getProvider(Australia.class, Australia::new);
    }

    default Aviation aviation() {
        return getProvider(Aviation.class, Aviation::new);
    }

    default Aws aws() {
        return getProvider(Aws.class, Aws::new);
    }

    default Azure azure() {
        return getProvider(Azure.class, Azure::new);
    }

    default Barcode barcode() {
        return getProvider(Barcode.class, Barcode::new);
    }

    default BloodType bloodtype() {
        return getProvider(BloodType.class, BloodType::new);
    }

    default Book book() {
        return getProvider(Book.class, Book::new);
    }

    default Bool bool() {
        return getProvider(Bool.class, Bool::new);
    }

    default Brand brand() {
        return getProvider(Brand.class, Brand::new);
    }

    default Business business() {
        return getProvider(Business.class, Business::new);
    }

    default Camera camera() {
        return getProvider(Camera.class, Camera::new);
    }

    default Cannabis cannabis() {
        return getProvider(Cannabis.class, Cannabis::new);
    }

    default Cat cat() {
        return getProvider(Cat.class, Cat::new);
    }

    default Chiquito chiquito() {
        return getProvider(Chiquito.class, Chiquito::new);
    }

    default CNPJ cnpj() {
        return getProvider(CNPJ.class, CNPJ::new);
    }

    default Code code() {
        return getProvider(Code.class, Code::new);
    }

    default Coin coin() {
        return getProvider(Coin.class, Coin::new);
    }

    default Color color() {
        return getProvider(Color.class, Color::new);
    }

    default Commerce commerce() {
        return getProvider(Commerce.class, Commerce::new);
    }

    default Community community() {
        return getProvider(Community.class, Community::new);
    }

    default Company company() {
        return getProvider(Company.class, Company::new);
    }

    default Compass compass() {
        return getProvider(Compass.class, Compass::new);
    }

    default Computer computer() {
        return getProvider(Computer.class, Computer::new);
    }

    default Construction construction() {
        return getProvider(Construction.class, Construction::new);
    }

    default Cosmere cosmere() {
        return getProvider(Cosmere.class, Cosmere::new);
    }

    default Country country() {
        return getProvider(Country.class, Country::new);
    }

    default CPF cpf() {
        return getProvider(CPF.class, CPF::new);
    }

    default Credentials credentials() {
        return getProvider(Credentials.class, Credentials::new);
    }

    default CryptoCoin cryptoCoin() {
        return getProvider(CryptoCoin.class, CryptoCoin::new);
    }

    default CultureSeries cultureSeries() {
        return getProvider(CultureSeries.class, CultureSeries::new);
    }

    /**
     * @deprecated Use {@link #money()} instead.
     */
    @Deprecated(since = "2.5.0", forRemoval = true)
    @SuppressWarnings("removal")
    default Currency currency() {
        return getProvider(Currency.class, Currency::new);
    }

    /**
     * @deprecated Use {@link #timeAndDate()} instead.
     */
    @Deprecated(since = "2.5.0", forRemoval = true)
    @SuppressWarnings("removal")
    default DateAndTime date() {
        return getProvider(DateAndTime.class, DateAndTime::new);
    }

    default DcComics dcComics() {
        return getProvider(DcComics.class, DcComics::new);
    }

    default Demographic demographic() {
        return getProvider(Demographic.class, Demographic::new);
    }

    default Device device() {
        return getProvider(Device.class, Device::new);
    }

    default Dog dog() {
        return getProvider(Dog.class, Dog::new);
    }

    default Domain domain() {
        return getProvider(Domain.class, Domain::new);
    }

    default DrivingLicense drivingLicense() {
        return getProvider(DrivingLicense.class, DrivingLicense::new);
    }

    default Drone drone() {
        return getProvider(Drone.class, Drone::new);
    }

    default DungeonsAndDragons dungeonsAndDragons() {
        return getProvider(DungeonsAndDragons.class, DungeonsAndDragons::new);
    }

    default Educator educator() {
        return getProvider(Educator.class, Educator::new);
    }

    default ElectricalComponents electricalComponents() {
        return getProvider(ElectricalComponents.class, ElectricalComponents::new);
    }

    default Emoji emoji() {
        return getProvider(Emoji.class, Emoji::new);
    }

    default FakeDuration duration() {
        return getProvider(FakeDuration.class, FakeDuration::new);
    }

    default FamousLastWords famousLastWords() {
        return getProvider(FamousLastWords.class, FamousLastWords::new);
    }

    default File file() {
        return getProvider(File.class, File::new);
    }

    default Finance finance() {
        return getProvider(Finance.class, Finance::new);
    }

    default FinancialTerms financialTerms() {
        return getProvider(FinancialTerms.class, FinancialTerms::new);
    }

    default FunnyName funnyName() {
        return getProvider(FunnyName.class, FunnyName::new);
    }

    default GarmentSize garmentSize() {
        return getProvider(GarmentSize.class, GarmentSize::new);
    }

    default Gender gender() {
        return getProvider(Gender.class, Gender::new);
    }

    default GreekPhilosopher greekPhilosopher() {
        return getProvider(GreekPhilosopher.class, GreekPhilosopher::new);
    }

    default Hacker hacker() {
        return getProvider(Hacker.class, Hacker::new);
    }

    default Hashing hashing() {
        return getProvider(Hashing.class, Hashing::new);
    }

    default Hipster hipster() {
        return getProvider(Hipster.class, Hipster::new);
    }

    default Hobby hobby() {
        return getProvider(Hobby.class, Hobby::new);
    }

    default Hololive hololive() {
        return getProvider(Hololive.class, Hololive::new);
    }

    default Horse horse() {
        return getProvider(Horse.class, Horse::new);
    }

    default House house() {
        return getProvider(House.class, House::new);
    }

    default IdNumber idNumber() {
        return getProvider(IdNumber.class, IdNumber::new);
    }

    default Image image() {
        return getProvider(Image.class, Image::new);
    }

    default IndustrySegments industrySegments() {
        return getProvider(IndustrySegments.class, IndustrySegments::new);
    }

    default Internet internet() {
        return getProvider(Internet.class, Internet::new);
    }

    default Job job() {
        return getProvider(Job.class, Job::new);
    }

    default Kpop kpop() {
        return getProvider(Kpop.class, Kpop::new);
    }

    default LanguageCode languageCode() {
        return getProvider(LanguageCode.class, LanguageCode::new);
    }

    default LargeLanguageModel largeLanguageModel() {
        return getProvider(LargeLanguageModel.class, LargeLanguageModel::new);
    }

    default Locality locality() {
        return getProvider(Locality.class, Locality::new);
    }

    default Location location() {
        return getProvider(Location.class, Location::new);
    }

    default Lorem lorem() {
        return getProvider(Lorem.class, Lorem::new);
    }

    default Marketing marketing() {
        return getProvider(Marketing.class, Marketing::new);
    }

    default Matz matz() {
        return getProvider(Matz.class, Matz::new);
    }

    default Mbti mbti() {
        return getProvider(Mbti.class, Mbti::new);
    }

    default Measurement measurement() {
        return getProvider(Measurement.class, Measurement::new);
    }

    /**
     * Use {@link HealthcareFaker} instead
     */
    @Deprecated(since = "2.5.0", forRemoval = true)
    @SuppressWarnings("removal")
    default Medical medical() {
        return getProvider(Medical.class, Medical::new);
    }

    default Military military() {
        return getProvider(Military.class, Military::new);
    }

    default Money money() {
        return getProvider(Money.class, Money::new);
    }

    default Mood mood() {
        return getProvider(Mood.class, Mood::new);
    }

    default Mountain mountain() {
        return getProvider(Mountain.class, Mountain::new);
    }

    default Mountaineering mountaineering() {
        return getProvider(Mountaineering.class, Mountaineering::new);
    }

    default Music music() {
        return getProvider(Music.class, Music::new);
    }

    default Name name() {
        return getProvider(Name.class, Name::new);
    }

    default Nation nation() {
        return getProvider(Nation.class, Nation::new);
    }

    default NatoPhoneticAlphabet natoPhoneticAlphabet() {
        return getProvider(NatoPhoneticAlphabet.class, NatoPhoneticAlphabet::new);
    }

    default Nigeria nigeria() {
        return getProvider(Nigeria.class, Nigeria::new);
    }

    default Number number() {
        return getProvider(Number.class, Number::new);
    }

    default OlympicSport olympicSport() {
        return getProvider(OlympicSport.class, OlympicSport::new);
    }

    default Passport passport() {
        return getProvider(Passport.class, Passport::new);
    }

    default PhoneNumber phoneNumber() {
        return getProvider(PhoneNumber.class, PhoneNumber::new);
    }

    default Photography photography() {
        return getProvider(Photography.class, Photography::new);
    }

    default Planet planet() {
        return getProvider(Planet.class, Planet::new);
    }

    default ProgrammingLanguage programmingLanguage() {
        return getProvider(ProgrammingLanguage.class, ProgrammingLanguage::new);
    }

    default Relationship relationships() {
        return getProvider(Relationship.class, Relationship::new);
    }

    default Restaurant restaurant() {
        return getProvider(Restaurant.class, Restaurant::new);
    }

    default Robin robin() {
        return getProvider(Robin.class, Robin::new);
    }

    default RockBand rockBand() {
        return getProvider(RockBand.class, RockBand::new);
    }

    default Science science() {
        return getProvider(Science.class, Science::new);
    }

    default SlackEmoji slackEmoji() {
        return getProvider(SlackEmoji.class, SlackEmoji::new);
    }

    default Shakespeare shakespeare() {
        return getProvider(Shakespeare.class, Shakespeare::new);
    }

    default Sip sip() {
        return getProvider(Sip.class, Sip::new);
    }

    default Size size() {
        return getProvider(Size.class, Size::new);
    }

    default Space space() {
        return getProvider(Space.class, Space::new);
    }

    default Stock stock() {
        return getProvider(Stock.class, Stock::new);
    }

    default Subscription subscription() {
        return getProvider(Subscription.class, Subscription::new);
    }

    default Superhero superhero() {
        return getProvider(Superhero.class, Superhero::new);
    }

    default Team team() {
        return getProvider(Team.class, Team::new);
    }

    default Text text() {
        return getProvider(Text.class, Text::new);
    }

    default Time time() {
        return getProvider(Time.class, Time::new);
    }

    default TimeAndDate timeAndDate() {
        return getProvider(TimeAndDate.class, TimeAndDate::new);
    }

    default Tire tire() {
        return getProvider(Tire.class, Tire::new);
    }

    default Transport transport() {
        return getProvider(Transport.class, Transport::new);
    }

    default Twitter twitter() {
        return getProvider(Twitter.class, Twitter::new);
    }

    default Unique unique() {
        return getProvider(Unique.class, Unique::new);
    }

    default University university() {
        return getProvider(University.class, University::new);
    }

    default Vehicle vehicle() {
        return getProvider(Vehicle.class, Vehicle::new);
    }

    default Verb verb() {
        return getProvider(Verb.class, Verb::new);
    }

    default Weather weather() {
        return getProvider(Weather.class, Weather::new);
    }

    default Word word() {
        return getProvider(Word.class, Word::new);
    }

    default Yoda yoda() {
        return getProvider(Yoda.class, Yoda::new);
    }

    default Zodiac zodiac() {
        return getProvider(Zodiac.class, Zodiac::new);
    }

    default Pronouns pronouns() {
        return getProvider(Pronouns.class, Pronouns::new);
    }

}
