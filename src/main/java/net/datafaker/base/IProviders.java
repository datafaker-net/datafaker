package net.datafaker.base;


/**
 * The interface to register providers for {@link BaseFaker}.
 */
public interface IProviders extends ProviderRegistration {

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

  default AquaTeenHungerForce aquaTeenHungerForce() {
    return getProvider(AquaTeenHungerForce.class, AquaTeenHungerForce::new);
  }

  default Artist artist() {
    return getProvider(Artist.class, Artist::new);
  }

  default Australia australia() {
    return getProvider(Australia.class, Australia::new);
  }

  default Avatar avatar() {
    return getProvider(Avatar.class, Avatar::new);
  }

  default Aviation aviation() {
    return getProvider(Aviation.class, Aviation::new);
  }

  default Aws aws() {
    return getProvider(Aws.class, Aws::new);
  }

  default BackToTheFuture backToTheFuture() {
    return getProvider(BackToTheFuture.class, BackToTheFuture::new);
  }

  default Babylon5 babylon5() {
    return getProvider(Babylon5.class, Babylon5::new);
  }

  default Barcode barcode() {
    return getProvider(Barcode.class, Barcode::new);
  }

  default Battlefield1 battlefield1() {
    return getProvider(Battlefield1.class, Battlefield1::new);
  }

  default Beer beer() {
    return getProvider(Beer.class, Beer::new);
  }

  default BigBangTheory bigBangTheory() {
    return getProvider(BigBangTheory.class, BigBangTheory::new);
  }

  default BloodType bloodtype() {
    return getProvider(BloodType.class, BloodType::new);
  }

  default BojackHorseman bojackHorseman() {
    return getProvider(BojackHorseman.class, BojackHorseman::new);
  }

  default Book book() {
    return getProvider(Book.class, Book::new);
  }

  default Bool bool() {
    return getProvider(Bool.class, Bool::new);
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

  default ChuckNorris chuckNorris() {
    return getProvider(ChuckNorris.class, ChuckNorris::new);
  }

  default ClashOfClans clashOfClans() {
    return getProvider(ClashOfClans.class, ClashOfClans::new);
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

  default Coffee coffee() {
    return getProvider(Coffee.class, Coffee::new);
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

  default Computer computer() {
    return getProvider(Computer.class, Computer::new);
  }

  default Construction construction() {
    return getProvider(Construction.class, Construction::new);
  }

  default Money money() {
    return getProvider(Money.class, Money::new);
  }

  default Country country() {
    return getProvider(Country.class, Country::new);
  }

  default CPF cpf() {
    return getProvider(CPF.class, CPF::new);
  }

  default Hashing hashing() {
    return getProvider(Hashing.class, Hashing::new);
  }

  default CryptoCoin cryptoCoin() {
    return getProvider(CryptoCoin.class, CryptoCoin::new);
  }

  default Currency currency() {
    return getProvider(Currency.class, Currency::new);
  }

  default DarkSoul darkSoul() {
    return getProvider(DarkSoul.class, DarkSoul::new);
  }

  default DateAndTime date() {
    return getProvider(DateAndTime.class, DateAndTime::new);
  }

  default Disease disease() {
    return getProvider(Disease.class, Disease::new);
  }

  default Demographic demographic() {
    return getProvider(Demographic.class, Demographic::new);
  }

  default DcComics dcComics() {
    return getProvider(DcComics.class, DcComics::new);
  }

  default Departed departed() {
    return getProvider(Departed.class, Departed::new);
  }

  default Dessert dessert() {
    return getProvider(Dessert.class, Dessert::new);
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

  default DragonBall dragonBall() {
    return getProvider(DragonBall.class, DragonBall::new);
  }

  default DrivingLicense drivingLicense() {
    return getProvider(DrivingLicense.class, DrivingLicense::new);
  }

  default DumbAndDumber dumbAndDumber() {
    return getProvider(DumbAndDumber.class, DumbAndDumber::new);
  }

  default Dune dune() {
    return getProvider(Dune.class, Dune::new);
  }

  default Educator educator() {
    return getProvider(Educator.class, Educator::new);
  }

  default EldenRing eldenRing() {
    return getProvider(EldenRing.class, EldenRing::new);
  }

  default ElderScrolls elderScrolls() {
    return getProvider(ElderScrolls.class, ElderScrolls::new);
  }

  default ElectricalComponents electricalComponents() {
    return getProvider(ElectricalComponents.class, ElectricalComponents::new);
  }

  default Esports esports() {
    return getProvider(Esports.class, Esports::new);
  }

  default FakeDuration duration() {
    return getProvider(FakeDuration.class, FakeDuration::new);
  }

  default Fallout fallout() {
    return getProvider(Fallout.class, Fallout::new);
  }

  default FamousLastWords famousLastWords() {
    return getProvider(FamousLastWords.class, FamousLastWords::new);
  }

  default File file() {
    return getProvider(File.class, File::new);
  }

  default FinalSpace finalSpace() {
    return getProvider(FinalSpace.class, FinalSpace::new);
  }

  default Finance finance() {
    return getProvider(Finance.class, Finance::new);
  }

  default Food food() {
    return getProvider(Food.class, Food::new);
  }

  default Friends friends() {
    return getProvider(Friends.class, Friends::new);
  }

  default FunnyName funnyName() {
    return getProvider(FunnyName.class, FunnyName::new);
  }

  default GameOfThrones gameOfThrones() {
    return getProvider(GameOfThrones.class, GameOfThrones::new);
  }

  default GarmentSize garmentSize() {
    return getProvider(GarmentSize.class, GarmentSize::new);
  }

  default Gender gender() {
    return getProvider(Gender.class, Gender::new);
  }

  default Ghostbusters ghostbusters() {
    return getProvider(Ghostbusters.class, Ghostbusters::new);
  }

  default GratefulDead gratefulDead() {
    return getProvider(GratefulDead.class, GratefulDead::new);
  }

  default GreekPhilosopher greekPhilosopher() {
    return getProvider(GreekPhilosopher.class, GreekPhilosopher::new);
  }

  default Hacker hacker() {
    return getProvider(Hacker.class, Hacker::new);
  }

  default HarryPotter harryPotter() {
    return getProvider(HarryPotter.class, HarryPotter::new);
  }

  default Hearthstone hearthstone() {
    return getProvider(Hearthstone.class, Hearthstone::new);
  }

  default HeyArnold heyArnold() {
    return getProvider(HeyArnold.class, HeyArnold::new);
  }

  default Hipster hipster() {
    return getProvider(Hipster.class, Hipster::new);
  }

  default HitchhikersGuideToTheGalaxy hitchhikersGuideToTheGalaxy() {
    return getProvider(
        HitchhikersGuideToTheGalaxy.class, HitchhikersGuideToTheGalaxy::new);
  }

  default Hobbit hobbit() {
    return getProvider(Hobbit.class, Hobbit::new);
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

  default HowIMetYourMother howIMetYourMother() {
    return getProvider(HowIMetYourMother.class, HowIMetYourMother::new);
  }

  default IdNumber idNumber() {
    return getProvider(IdNumber.class, IdNumber::new);
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

  default Kaamelott kaamelott() {
    return getProvider(Kaamelott.class, Kaamelott::new);
  }

  default Kpop kpop() {
    return getProvider(Kpop.class, Kpop::new);
  }

  default Lebowski lebowski() {
    return getProvider(Lebowski.class, Lebowski::new);
  }

  default LeagueOfLegends leagueOfLegends() {
    return getProvider(LeagueOfLegends.class, LeagueOfLegends::new);
  }

  default LordOfTheRings lordOfTheRings() {
    return getProvider(LordOfTheRings.class, LordOfTheRings::new);
  }

  default Lorem lorem() {
    return getProvider(Lorem.class, Lorem::new);
  }

  default Marketing marketing() {
    return getProvider(Marketing.class, Marketing::new);
  }

  default MassEffect massEffect() {
    return getProvider(MassEffect.class, MassEffect::new);
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

  default Medical medical() {
    return getProvider(Medical.class, Medical::new);
  }

  default Military military() {
    return getProvider(Military.class, Military::new);
  }

  default Minecraft minecraft() {
    return getProvider(Minecraft.class, Minecraft::new);
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

  default Movie movie() {
    return getProvider(Movie.class, Movie::new);
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

  default Options options() {
    return getProvider(Options.class, Options::new);
  }

  default Overwatch overwatch() {
    return getProvider(Overwatch.class, Overwatch::new);
  }

  default OscarMovie oscarMovie() {
    return getProvider(OscarMovie.class, OscarMovie::new);
  }

  default Passport passport() {
    return getProvider(Passport.class, Passport::new);
  }

  default Password password() {
    return getProvider(Password.class, Password::new);
  }

  default PhoneNumber phoneNumber() {
    return getProvider(PhoneNumber.class, PhoneNumber::new);
  }

  default Photography photography() {
    return getProvider(Photography.class, Photography::new);
  }

  default Pokemon pokemon() {
    return getProvider(Pokemon.class, Pokemon::new);
  }

  default PrincessBride princessBride() {
    return getProvider(PrincessBride.class, PrincessBride::new);
  }

  default ProgrammingLanguage programmingLanguage() {
    return getProvider(ProgrammingLanguage.class, ProgrammingLanguage::new);
  }

  default Relationship relationships() {
    return getProvider(Relationship.class, Relationship::new);
  }

  default ResidentEvil residentEvil() {
    return getProvider(ResidentEvil.class, ResidentEvil::new);
  }

  default Restaurant restaurant() {
    return getProvider(Restaurant.class, Restaurant::new);
  }

  default RickAndMorty rickAndMorty() {
    return getProvider(RickAndMorty.class, RickAndMorty::new);
  }

  default Robin robin() {
    return getProvider(Robin.class, Robin::new);
  }

  default RockBand rockBand() {
    return getProvider(RockBand.class, RockBand::new);
  }

  default RuPaulDragRace ruPaulDragRace() {
    return getProvider(RuPaulDragRace.class, RuPaulDragRace::new);
  }

  default Science science() {
    return getProvider(Science.class, Science::new);
  }

  default Seinfeld seinfeld() {
    return getProvider(Seinfeld.class, Seinfeld::new);
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

  default Simpsons simpsons() {
    return getProvider(Simpsons.class, Simpsons::new);
  }

  default SoulKnight soulKnight() {
    return getProvider(SoulKnight.class, SoulKnight::new);
  }

  default Space space() {
    return getProvider(Space.class, Space::new);
  }

  default StarCraft starCraft() {
    return getProvider(StarCraft.class, StarCraft::new);
  }

  default StarTrek starTrek() {
    return getProvider(StarTrek.class, StarTrek::new);
  }

  default StarWars starWars() {
    return getProvider(StarWars.class, StarWars::new);
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

  default SuperMario superMario() {
    return getProvider(SuperMario.class, SuperMario::new);
  }

  default Tea tea() {
    return getProvider(Tea.class, Tea::new);
  }

  default Team team() {
    return getProvider(Team.class, Team::new);
  }

  default TheItCrowd theItCrowd() {
    return getProvider(TheItCrowd.class, TheItCrowd::new);
  }

  default Time time() {
    return getProvider(Time.class, Time::new);
  }

  default Touhou touhou() {
    return getProvider(Touhou.class, Touhou::new);
  }

  default Tron tron() {
    return getProvider(Tron.class, Tron::new);
  }

  default TwinPeaks twinPeaks() {
    return getProvider(TwinPeaks.class, TwinPeaks::new);
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

  default Witcher witcher() {
    return getProvider(Witcher.class, Witcher::new);
  }

  default Yoda yoda() {
    return getProvider(Yoda.class, Yoda::new);
  }

  default Zelda zelda() {
    return getProvider(Zelda.class, Zelda::new);
  }
}
