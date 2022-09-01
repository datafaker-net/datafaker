package net.datafaker;

/**
 * This class is used to generate garments sizes randomly.
 *
 * @since 1.6.0
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
    return faker.resolve("garments_sizes.sizes");
  }
}
