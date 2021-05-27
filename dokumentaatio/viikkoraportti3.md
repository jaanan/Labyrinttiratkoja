Muistiinpanoja ennen palautusta.

Lauantai. Tajusin taas alkaa koodata Visual Studio Codella. Lisäilin gitignoreen tiedostoja kun hämäsi, kun Visua Studio Code herjasi .class tiedostoja. Mietin, voinko poistaa ko. tiedostot ihan kokonaan vai tapahtuisiko jotain hirveää, jos niin tekisin...

poistin vscode directoryn ja /bin kansion. Pelottaa.. Vaikuttaa kuitenkin toimivan. 

Sunnuntai

Pohdin ErotetutOsat.javan riviä range(0, koko).forEach(this::teeOsa); ja mietin, että miten sen voisi kirjoittaa esim. for loopiksi. En oikein ymmärrä, mihin tuo this tuossa viittaa. Osasin ehkä muuttaa sen. Vaikuttaa siltä, että ohjelma toimii edelleen. 
RANGESTA MIETIN: meneekö se tosiaan 0:sta eteenpäin? pitääkö i:n aloittaa nollasta, vai pitäisikö sen aloittaa 1:sestä? Toisaalta näyttää ajavan sitä ihan nätisti näin, joten ilmeisesti 0:sta voi aloittaa. 

Tiistai

Luojan kiitos nyt on vasta tiistai. Pelkäsin jo, että on keskiviikko. Noh. Tänään olen miettinyt mm. PolkuPuu.java -tiedoston funktioita. Siellä on Collections.shuffle(reunat); joka pitäisi saada pois tuosta Collectionsista. luoReunat() funktio palauttaa Listin. Olen selvittänyt, että taulukoihin pystyy kyllä tallentamaan myös itse keksittyjä olioita, ei vain Stringejä, Charreja ja Intejä. Joten Rajoja pystyy tallentamaan myös taulukkoon. Tuo funktio luoReunat tallentaa tällä hetkellä niitä ArrayListeihin. Pitäisi saada muutettua niin, että se tallentaa niitä taulukoihin. Mietin vielä, teenkö kokonaan uuden tiedoston, johon kokeilen näitä uusia vai alkanko kommentoida noita vanhoja metodeja pois ja kokeilen tuossa samassa tiedostossa rakentaa hommaa eteenpäin taulukoilla. Ekana mun pitää varmastikin kerrata taulukot. Miten niihin lisättiin jne. Mitä taulukoilla voi tehdä, jotta voin käyttää niitä hyödyllisesti tässä. Olen jo unohtanut nämä, sillä kävin ohjelmoinnin perusteet vuonna 2016. 

Edellä mainittujen lisäksi tuolla samassa PolkuPuu-tiedostossa on metodissa luoViritettyPuu return rajat.stream().filter(raja -> connects(raja, erotteleOsat)).collect(toList()); näköinen hirviö. Tuo pitää jossain vaiheessa myös saada auki, mutta aloitan ehkä noista ArrayListit taulukoiksi asioista. Metodissa luoPolut on myös hämmennystä aiheuttava
 
//            viritettyPuu.stream().map(raja -> {
//                var eka = indeksista(raja.getEkaPala());
//                var toka = indeksista(raja.getTokaPala());
//                return getPolku(eka, toka);
//            }).collect(toList());
//    }

Tässäkin viritetty puu on List muotoinen, joten se pitää muuttaa taulukoksi.

Keskiviikko

Muuttelin Polkupuu-tiedoston Arraylistejä taulukoiksi. Tein tämän omassa branchissään Visual Studio Codella. En tiedä uskallanko enää pushailla ja pullailla menemään, etten menetä mitään. Pitäisi kerrata, miten nämä branchit toimii. 

Torstai

Tänään on avannut Polkupuu tiedoston funktioita collection.shuffle sellaiseksi, että se toimisi taulukoilla.
Lisäksi olen työstänyt sen luoViritettyPuu metodia sellaiseksi, että se toimisi taulukoilla ja ilman stream.filter --> collect(toList()) funktioita.
