# Toteutusdokumentti

## Ohjelman yleisrakenne

Kansiosta Labyrinttiratkoja pääsee kansioihin src/labyrintti ja test. Ensimmäinen näistä, eli src/labyrintti, sisältää periaatteessa koko ohjelman. test sisältää vain testit. <br />
<br />
src/labyrintti kansiossa on tiedosto Main.java sekä kolme kansiota: algoritmit, malli, palvelu. Näistä palvelussa tapahtuu kaikki käyttäjän toimiin liittyvä, mallissa luodaan käyttäjälle näytettävä info ja algoritmeissa tapahtuu varsinainen algoritmeihin liittyvä toiminta. <br />

Kansiossa algoritmit on erikseen kansiot labyrintin generoimiselle ja ratkaisemiselle. Generointi kansion tiedostoista kolme liittyvät Kruskalin algoritmiin. Raja.java tiedostossa on kaarien luokka Raja, jossa voi luoda eri solmuja yhdistävän kaaren, tässä nimellä Raja. ErotetutOsat.java tiedostossa on luokka joka yhdistää labyrintin eri osia mikäli ne eivät ole jo yhdistettyjä toisiinsa. PolkuPuu.java luokassa tapahtuu muu Kruskalin algoritmiin liittyvä toiminta. Siellä labyrintin kulkuväylät varsinaisesti luodaan. PriminAlgoritmi.java kansiossa taas tapahtuu kaikki Primin algoritmiin liittyvä toiminta. 

## Suorituskyky- ja O-analyysivertailu (mikäli työ vertailupainotteinen)

Kruskalin algoritmi suoriutuu labyrintin luomisesta nopeammin kuin Prim. Tämä johtunee siitä, että Kruskalin algoritmi toimii hyvin harvoissa verkoissa eli sellaisissa verkoissa, joissa solmuja yhdistää suhteellisen vähän kaaria. Näissä labyrinteissa kaaria yhdestä solmusta on vain neljä: ylös, alas, vasemmalle ja oikealle. Niinpä tämä sopii paremmin Kruskalille, jonka aikavaativuus on O(e log e), jossa e on kaarien eli edgejen määrä. Prim toimisi Kruskalia paremmin, mikäli kaaria solmuista toiseen olisi merkittävästi enemmän. Sen aikavaativuus on O(e log v) missä v on solmujen määrä. 

## Työn mahdolliset puutteet ja parannusehdotukset

Ohjelmakoodi voisi olla selkeämpää, metodit lyhyempiä, luokat, muuttujat ja metodit paremmin nimettyjä ja ohjelman rakennekin selkeämpi.

Koodin voisi olla kirjoitettu selkeämmin ja ymmärrettävämmin. Kommentointi voisi olla kattavampaa.

JavaDoc-kommentointia ei tässä käytetty, vaikka ohjelma toteutettiin Javalla. 

## Lähteet

[Kruskalin ja Primin algoritmien aikavaativuudet, slide 45](https://slideplayer.com/slide/5954251/)
