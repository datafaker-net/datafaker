package net.datafaker;

/**
 * This class is used to generate garments sizes randomly.
 *
 */

public class GarmentSize extends AbstractProvider {

  protected GarmentSize(Faker faker) {
    super(faker);
  }

  /**
   * This method returns a garment size
   *
   * @return a string of garment size
   */
  public String size() {
    return faker.fakeValuesService().fetchString("garments.sizes");
  }

  /**
   * This method returns a garment XS size
   *
   * @return a XS string of garment size
   */
  public String anXS() { return faker.fakeValuesService().fetchString("garments.xs"); }

  /**
   * This method returns a garment S size
   *
   * @return a S string of garment size
   */
  public String anS() { return faker.fakeValuesService().fetchString("garments.s"); }


  /**
   * This method returns a garment M size
   *
   * @return a M string of garment size
   */
  public String anM() { return faker.fakeValuesService().fetchString("garments.m"); }


  /**
   * This method returns a garment L size
   *
   * @return a L string of garment size
   */
  public String anL() { return faker.fakeValuesService().fetchString("garments.l"); }

  /**
   * This method returns a garment XL size
   *
   * @return a XL string of garment size
   */
  public String anXL() { return faker.fakeValuesService().fetchString("garments.xl"); }

  /**
   * This method returns a garment XXL size
   *
   * @return a XXL string of garment size
   */
  public String anXXL() { return faker.fakeValuesService().fetchString("garments.xxl"); }


  /**
   * This method returns a garment XXXL size
   *
   * @return a XXXL string of garment size
   */
  public String anXXXL() { return faker.fakeValuesService().fetchString("garments.xxxl"); }


}
