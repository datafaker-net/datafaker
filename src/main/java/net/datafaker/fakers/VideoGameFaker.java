package net.datafaker.fakers;

import net.datafaker.Battlefield1;
import net.datafaker.ClashOfClans;
import net.datafaker.EldenRing;
import net.datafaker.ElderScrolls;
import net.datafaker.Esports;
import net.datafaker.Fallout;
import net.datafaker.Hearthstone;
import net.datafaker.LeagueOfLegends;
import net.datafaker.Minecraft;
import net.datafaker.SoulKnight;
import net.datafaker.StarCraft;
import net.datafaker.SuperMario;
import net.datafaker.Touhou;
import net.datafaker.Zelda;

public interface VideoGameFaker extends BaseFaker {
  Battlefield1 battlefield1();

  ClashOfClans clashOfClans();

  EldenRing eldenRing();

  ElderScrolls elderScrolls();

  Esports esports();

  Fallout fallout();

  Hearthstone hearthstone();

  LeagueOfLegends leagueOfLegends();

  Minecraft minecraft();

  SoulKnight soulKnight();

  StarCraft starCraft();

  SuperMario superMario();

  Touhou touhou();

  Zelda zelda();
}
