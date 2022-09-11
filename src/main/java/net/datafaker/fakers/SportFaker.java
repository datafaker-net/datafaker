package net.datafaker.fakers;

import net.datafaker.Basketball;
import net.datafaker.EnglandFootBall;
import net.datafaker.Football;
import net.datafaker.Formula1;
import net.datafaker.Volleyball;

public interface SportFaker extends BaseFaker {
  Basketball basketball();

  EnglandFootBall englandfootball();

  Football football();

  Formula1 formula1();

  Volleyball volleyball();
}
