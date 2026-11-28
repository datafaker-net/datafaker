/*
 * Homepage interactions for datafaker.net.
 *
 * The sample rows below were produced by Datafaker itself
 * (name().fullName(), internet().emailAddress(), address().city(),
 * phoneNumber().phoneNumber()) for each locale, so what visitors see is
 * real library output.
 */
(function () {
  "use strict";

  var SAMPLES = {
    "en-US": [
      ["Garnet VonRueden", "clemencia.mcdermott@hotmail.com", "East Benito", "(878) 363-3108"],
      ["Melda Cartwright", "luther.murazik@yahoo.com", "South Sachikostad", "(983) 530-8600"],
      ["Shane Hermiston", "mellissa.schmidt@hotmail.com", "New Vicentaburgh", "(983) 253-0432"],
      ["David Sipes", "brande.steuber@gmail.com", "Natashiaborough", "(743) 252-6471"],
      ["Adeline Donnelly", "steve.kovacek@yahoo.com", "Bashirianview", "(983) 828-5273"],
      ["Del Mann Jr.", "lester.lang@yahoo.com", "Nienowville", "(520) 450-0991"],
      ["Yolanda Aufderhar", "zita.toy@yahoo.com", "Sebastianhaven", "(424) 299-3349"],
      ["Jamar Miller", "john.mcdermott@gmail.com", "Lake Alphonse", "(983) 933-3634"],
      ["Bryce Howe Sr.", "lauretta.waters@gmail.com", "Huelsburgh", "(341) 553-5995"],
      ["Rodrick Rippin", "phil.rowe@gmail.com", "Shanaeberg", "(641) 870-6985"],
      ["Dr. Marylyn Hayes", "ardith.bahringer@yahoo.com", "Port Jacelyn", "(983) 444-3453"],
      ["Princess Quigley", "elease.pagac@hotmail.com", "Morganton", "(952) 861-8239"],
      ["Olga Orn V", "blair.veum@yahoo.com", "Ortizfurt", "(501) 912-6945"],
      ["Whitney Reilly", "yong.kuhn@hotmail.com", "South Agustin", "(571) 783-8102"],
      ["Jackie Ebert II", "louisa.heller@yahoo.com", "West Marylou", "(351) 499-1404"],
      ["Celina Gorczany", "scott.mraz@gmail.com", "West Delsie", "(303) 334-9647"]
    ],
    "nl-NL": [
      ["Mevr. Dr. Ying Okker", "miquele.welzenis@hotmail.nl", "West Tamsmaburg", "0172 583 550"],
      ["Prof. Ize Lugtigheid", "vijay.fung@hotmail.nl", "Marcelisdijk", "0174 740 467"],
      ["Helder Stomphorst", "chayenna.heger@hotmail.nl", "Sebregtsdijk", "0487 499 190"],
      ["Yorben Krekels V", "nickey.erades@kpnmail.nl", "Meiegeest", "0294 877 399"],
      ["Dorus van Pel", "skip.akkoc@kpnmail.nl", "Nusharatdorp", "0529 850 209"],
      ["Jaey Snelder", "preshwand.schiepers@hotmail.nl", "Oud Cathleensluis", "0294 551 623"],
      ["Harlinde van den Cleijne", "zayra.langejan@yahoo.com", "Boekhoffmeer", "0228 873 795"],
      ["Orhan van de Wijtenburg", "vaso.wolsheimer@live.nl", "Noord Compierendrecht", "0343 108 274"],
      ["Mevr. Dr. Bart Sprangers", "bergina.pelger@hotmail.nl", "Gaarenstroomdijk", "0252 227 261"],
      ["Prof. Vogelina Winthagen", "kimberly.wortel@kpnmail.nl", "Beekelaarberg", "0181 172 884"],
      ["Mario Poelmann", "bas.verbeek@outlook.com", "Bijberg", "075 551 1577"],
      ["Huibertje den Meuleners", "jonella.buurlage@live.nl", "Aanenkerk", "0180 423 218"],
      ["Zayenne Zweverink", "esmay.dortmans@live.nl", "Broekmeulenmeer", "0294 276 067"],
      ["Herlinde Folkeringa", "ardjuna.samuels@gmail.com", "Nieuw Keurentjessluis", "0486 786 451"],
      ["Kylianna Jakobs", "daniela.klasens@outlook.com", "Haddemansluus", "0346 089 562"],
      ["Msc Daborah Muskee", "thijn.kuhne@outlook.com", "Nieuw Schreijenbergmeer", "0251 983 523"]
    ],
    "de-DE": [
      ["Dennis Caspari", "andrew.kozakiewicz@mail.de", "Mikitenkoheim", "032 695695746"],
      ["Ludwig Morhelfer", "betty.fleischer@mail.de", "Kastenscheid", "037601 344943"],
      ["Björn Blassneck", "jasmine.mathes@yahoo.com", "Geyerdorf", "0661 842087644"],
      ["Hr. Ferdinand Hohl", "julienne.falter@gmx.de", "Angelinahagen", "04956 08"],
      ["Kiana Eich", "jasmin.bender@hotmail.com", "Bad Laura", "0571 7833295"],
      ["Gian Kummle", "joscha.moll@yahoo.com", "Neu Cedric", "032 2243280563"],
      ["Arda Kofferschlager", "carl.klemme@web.de", "Josuaberg", "069 83426802507"],
      ["Freiherrin Nikolas Ghirmai", "rene.maier@mail.de", "Buchruckerscheid", "032 9535956406"],
      ["Jenna Leiter", "frieda.schultz@gmx.de", "Müllergrün", "040 04301386047"],
      ["Baron Melvin Lorenz", "pepe.lott@freenet.de", "Klein Laurencehagen", "04962 74287"],
      ["Yves Seigel", "lana.schultz@gmx.de", "Pohlehagen", "032 76122820127"],
      ["Aylin Pippig", "joost.raabe@hotmail.com", "Jungbluthheim", "04909 05"],
      ["Cosima Paschke", "saskia.grossheim@yahoo.com", "Neumairdorf", "035651 241958"],
      ["Dr. Tomke Vieweg", "arwen.keiner@yahoo.com", "Fridadorf", "032 18118994083"],
      ["Freiherrin Ahmet Hohnheiser", "luiz.kappe@yahoo.com", "Kohnlefeld", "04972 071089"],
      ["Dr. Diana Bittner", "lenn.suffa@gmx.de", "Ost Adriano", "02155 83472910348"]
    ],
    "fr-FR": [
      ["Mme Adam Laine", "elisa.pierre@hotmail.fr", "Nancy", "02 66 64 50 72"],
      ["Fabre Elisa", "julien.morel@yahoo.fr", "Besançon", "04 10 70 11 24"],
      ["Rémy Noémie", "lea.moreau@yahoo.fr", "Antony", "04 21 03 40 13"],
      ["Mlle Juliette Dupont", "nicolas.gauthier@yahoo.fr", "Valence", "02 49 00 34 74"],
      ["Théo Clément", "pauline.colin@gmail.com", "Le Mans", "02 61 94 46 66"],
      ["Maxime Marie", "justine.dubois@hotmail.fr", "Grenoble", "05 33 76 55 53"],
      ["Paul Benoit", "ines.petit@gmail.com", "Amiens", "02 61 78 86 37"],
      ["Sarah Francois", "yanis.fontaine@hotmail.fr", "Caen", "05 97 68 95 78"],
      ["Paul Maillard", "matteo.rey@gmail.com", "Calais", "05 59 80 99 03"],
      ["Mlle Valentin Maillard", "clara.leroux@yahoo.fr", "Saint-Paul", "05 97 47 78 56"],
      ["M Raphaël Bertrand", "baptiste.maillard@yahoo.fr", "Perpignan", "04 85 54 35 15"],
      ["Dr Lucie Renault", "theo.huet@yahoo.fr", "Lyon", "05 95 62 82 22"],
      ["Océane Marchand", "hugo.david@hotmail.fr", "Valence", "05 99 90 76 71"],
      ["M Paul Barre", "lola.lacroix@gmail.com", "Troyes", "05 97 17 71 47"],
      ["Renault Noa", "lola.paris@gmail.com", "Villejuif", "04 93 25 35 17"],
      ["Aubert Mathilde", "alice.benoit@yahoo.fr", "Courbevoie", "05 95 07 96 47"]
    ],
    "es-ES": [
      ["Teresa Patiño Murillo", "manuel.granados@yahoo.com", "Pamplona", "969 06 07 02"],
      ["Débora Sosa Vanegas", "andrea.bernal@hotmail.com", "Orense", "969 06 68 16"],
      ["Andrés Sanches Galindo", "norma.aponte@hotmail.com", "Almería", "955 49 66 84"],
      ["Alicia Ulloa Soria", "rebeca.estrada@yahoo.com", "Baracaldo", "814 84 37 96"],
      ["Pablo Alejandro Escobar", "gustavo.urias@gmail.com", "Gerona", "969 06 59 74"],
      ["Felipe Lovato Burgos", "barbara.mireles@gmail.com", "El Prat de LLobregat", "973 91 35 09"],
      ["Lucía Colunga Mondragón", "natalia.bustamante@yahoo.com", "Sanlúcar de Barrameda", "946 90 67 30"],
      ["Martín Barrientos Elizondo", "miguelangel.nevarez@hotmail.com", "Burgos", "973 41 50 01"],
      ["Ana María Viera Rendón", "susana.galindo@yahoo.com", "Alcalá de Guadaira", "972 91 17 13"],
      ["Mario Colón Robledo", "federico.corral@hotmail.com", "Jaén", "979 57 43 40"],
      ["Sr. Elisa Vanegas Briones", "magdalena.carrion@hotmail.com", "Albacete", "400 80 68 21"],
      ["Sra. Javier Ávalos Terrazas", "rebeca.saavedra@gmail.com", "Bilbao", "853 05 54 99"],
      ["Patricia Briseño Salcedo", "esteban.jaquez@yahoo.com", "Hospitalet de LLobregat", "973 66 78 27"],
      ["Sra. Jacobo Palomino Cervántez", "esteban.gamez@yahoo.com", "Málaga", "930 80 43 26"],
      ["Alberto Gil Arellano", "adan.armas@yahoo.com", "Gecho", "871 98 08 95"],
      ["Santiago Arenas Cervantes", "leticia.estrada@hotmail.com", "Avilés", "969 06 54 76"]
    ],
    "pt-BR": [
      ["Gabrielly Paiva", "anacecilia.souza@gmail.com", "Ipiranga do Sul", "(55) 2086-0124"],
      ["Roberta Rolim Neto", "enzomiguel.chaves@yahoo.com", "Imbé", "(37) 3526-6784"],
      ["Camila Almeida", "kaique.pires@hotmail.com", "Cocos", "(71) 3492-9883"],
      ["Lucas Gabriel Pinheira", "pedromiguel.reis@live.com", "Santo Antônio do Paraíso", "(75) 5798-3604"],
      ["Aline Amoreira", "aline.barroso@live.com", "Palmeirante", "(44) 2249-1249"],
      ["Marli Banheira", "levi.lima@live.com", "Tufilândia", "(51) 2051-6519"],
      ["Danilo Antena", "joaopedro.bernardes@yahoo.com", "Caculé", "(88) 5044-7413"],
      ["Milena Marins", "isaac.castelo@live.com", "Pedranópolis", "(21) 2824-4015"],
      ["Davi Miguel Camacho", "joaquim.hernandes@gmail.com", "Pirangi", "(55) 4665-2977"],
      ["Sra. Davi Lucca Dias", "clara.esteves@bol.com.br", "Palmeirina", "(75) 2615-4066"],
      ["Enrico Conceição", "deneval.assumpcao@yahoo.com", "Jardim do Seridó", "(75) 4303-6216"],
      ["Vicente Alves", "catarina.veles@hotmail.com", "Capivari de Baixo", "(61) 2527-6932"],
      ["Bryan Vieira", "ayla.goulart@yahoo.com", "Divisópolis", "(86) 2257-9964"],
      ["Alice Lessa", "anavitoria.nogueira@bol.com.br", "Teolândia", "(75) 5610-3542"],
      ["Alice Veloso", "thomas.regueira@live.com", "Cantá", "(22) 2454-8728"],
      ["Vinicius da Rocha", "gabriela.gomide@gmail.com", "Governador Newton Bello", "(32) 4272-6144"]
    ]
  };

  var LOCALE_CODE = {
    "en-US": "new Faker()",
    "nl-NL": "new Faker(Locale.forLanguageTag(\"nl-NL\"))",
    "de-DE": "new Faker(Locale.forLanguageTag(\"de-DE\"))",
    "fr-FR": "new Faker(Locale.forLanguageTag(\"fr-FR\"))",
    "es-ES": "new Faker(Locale.forLanguageTag(\"es-ES\"))",
    "pt-BR": "new Faker(Locale.forLanguageTag(\"pt-BR\"))"
  };

  var ROWS = 5;

  function shuffled(list) {
    var copy = list.slice();
    for (var i = copy.length - 1; i > 0; i--) {
      var j = Math.floor(Math.random() * (i + 1));
      var tmp = copy[i];
      copy[i] = copy[j];
      copy[j] = tmp;
    }
    return copy;
  }

  function initDemo(root) {
    var body = root.querySelector("[data-demo-rows]");
    var ctor = root.querySelector("[data-demo-ctor]");
    var buttons = root.querySelectorAll("[data-locale]");
    var generate = root.querySelector("[data-demo-generate]");
    var locale = "en-US";

    function render() {
      var rows = shuffled(SAMPLES[locale]).slice(0, ROWS);
      body.textContent = "";
      rows.forEach(function (row) {
        var tr = document.createElement("tr");
        row.forEach(function (value) {
          var td = document.createElement("td");
          td.textContent = value;
          tr.appendChild(td);
        });
        body.appendChild(tr);
      });
      ctor.textContent = LOCALE_CODE[locale];
      body.classList.remove("is-fresh");
      void body.offsetWidth;
      body.classList.add("is-fresh");
    }

    buttons.forEach(function (button) {
      button.addEventListener("click", function () {
        locale = button.getAttribute("data-locale");
        buttons.forEach(function (b) {
          b.setAttribute("aria-pressed", String(b === button));
        });
        render();
      });
    });

    generate.addEventListener("click", render);
    render();
  }

  function initTabs(root) {
    var tabs = Array.prototype.slice.call(root.querySelectorAll("[role=tab]"));
    var panels = root.querySelectorAll("[role=tabpanel]");

    function select(tab, focus) {
      tabs.forEach(function (t) {
        var selected = t === tab;
        t.setAttribute("aria-selected", String(selected));
        t.tabIndex = selected ? 0 : -1;
      });
      panels.forEach(function (panel) {
        panel.hidden = panel.id !== tab.getAttribute("aria-controls");
      });
      if (focus) tab.focus();
    }

    tabs.forEach(function (tab, index) {
      tab.addEventListener("click", function () { select(tab, false); });
      tab.addEventListener("keydown", function (event) {
        var next = null;
        if (event.key === "ArrowDown" || event.key === "ArrowRight") next = tabs[(index + 1) % tabs.length];
        if (event.key === "ArrowUp" || event.key === "ArrowLeft") next = tabs[(index - 1 + tabs.length) % tabs.length];
        if (event.key === "Home") next = tabs[0];
        if (event.key === "End") next = tabs[tabs.length - 1];
        if (next) {
          event.preventDefault();
          select(next, true);
        }
      });
    });
  }

  function initInstall(root) {
    var options = root.querySelectorAll("[data-install]");
    var snippets = root.querySelectorAll("[data-install-snippet]");
    var copy = root.querySelector("[data-install-copy]");

    function current() {
      var shown = root.querySelector("[data-install-snippet]:not([hidden])");
      return shown ? shown.textContent.trim() : "";
    }

    options.forEach(function (option) {
      option.addEventListener("click", function () {
        var name = option.getAttribute("data-install");
        options.forEach(function (o) { o.setAttribute("aria-pressed", String(o === option)); });
        snippets.forEach(function (s) { s.hidden = s.getAttribute("data-install-snippet") !== name; });
      });
    });

    copy.addEventListener("click", function () {
      if (!navigator.clipboard) return;
      navigator.clipboard.writeText(current()).then(function () {
        copy.textContent = "Copied";
        setTimeout(function () { copy.textContent = "Copy"; }, 1600);
      });
    });
  }

  function init() {
    var demo = document.querySelector("[data-demo]");
    var tabs = document.querySelector("[data-feature-tabs]");
    var install = document.querySelector("[data-install-root]");
    if (demo) initDemo(demo);
    if (tabs) initTabs(tabs);
    if (install) initInstall(install);
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", init);
  } else {
    init();
  }
})();
