# Määrittelydokumentti
Kuulun tietojenkäsittelytieteen kandidaatti (TKT) ohjelmaan.
Dokumentaation kieli: suomi

## Mitä algoritmeja ja tietorakenteita toteutat työssäsi?
- Labyrintin generoimiseen käytän Kruskalin ja Primin algoritmejä 
- Labyrintin ratkaisemiseen käytän A* algoritmiä

## Mitä ongelmaa ratkaiset ja miksi valitsit kyseiset algoritmit?
Ratkaisen labyrintinluomisongelman.

Wikipedian [Maze geration algorithm](https://en.wikipedia.org/wiki/Maze_generation_algorithm) sivulla on kuvattu erilaisia algoritmeja, joiden avulla voi generoida eli luoda labyrintin. Siellä otsikon Graph theory based methods alla oli tietorakenteet ja algoritmit kurssilta tuttu nimi: Kruskal. Ajattelin, että tuosta saattaisin jotain ymmärtää, koska hänen algoritmiään oli käsitelty kurssilla. 

Jätin tämän harjoitustyön kuitenkin kesken viime kesänä ja tein syksyllä kanditutkielmani pienimmän virittävän puun algoritmeistä ymmärtääkseni näitä paremmin ja selvitäkseni tästä harjoitustyöstä. Tänä kesänä harjoitustyössä labyrintti piti luoda kahta eri algoritmiä hyödyntäen ja niiden suorituksia vertaillen. Kandityöni pohjalta oli helppo valita Kruskalin seuraksi Primin algoritmi tähän harjoitustyöhön, sillä sekin oli tullut jossain määrin tutuksi kandityötä tehdessäni. 

Kruskalin algoritmi tuottaa labyrintin luomalla pienimmän virittävän puun painotetussa verkossa, jossa jokaisen kaaren paino on keskenään yhtä suuri. Tästä syntyy labyrintti, joka tuottaa suht säännöllisiä kuvioita, jotka ovat melko helppoja ratkaista. Primin algoritmi tuottaa hyvin samannäköisen labyrintin, kun Kruskal, sillä ne molemmat toteuttavat labyrintin luomalla pienimmän virittävän puun verkossa. 

Pienin ulottuva puu (MST) on osajoukko yhtenäisen painotetun verkon kaarista, jotka yhdistävät kaikki verkon solmut ilman syklejä mahdollisimman pienellä kaarien kokonaispainolla.

Labyrintin ratkaisemista käytettiin tässä lähinnä labyrinttien testaamiseen. Siihen valikoitui A* algoritmi, joka on edelleen kehitelty versio Dijkstran algoritmista lyhyimmän reitin löytämiseen. A* -algoritmi hyödyntää arviota alku ja loppupisteen välisestä itseisarvosta, ns. linnuntiestä, jonka avulla se pystyy löytämään lyhimmän reitin vielä Dijkstran algoritmiäkin nopeammin. 

## Mitä syötteitä ohjelma saa ja miten näitä käytetään
Ohjelman voi ladata omalle koneelle ja käyttää sitä terminaalissa. Ohjelma kysyy käyttäjältä haluaako hän luoda algoritmin, ratkaista algoritmin vai lopettaa ohjelman käytön. Mikäli käyttäjä haluaa luoda algoritmin, käyttäjältä kysytään, minkä kokoisen labyrintin hän haluaa luoda. Ohjelma luo pyydetyn kokoisen algoritmin sekä Kruskalin (ylempi) ja Primin (alempi) algoritmejä hyödyntäen sekä kertoo, kumpi niistä loi algoritmin nopeammin. Halutessaan käyttäjä voi myös ratkaista algoritmin, jolloin hänelle näytetään, mikä oli lyhin reitti ulos labyrintistä.  

## Tavoitteena olevat aika- ja tilavaativuudet (m.m. O-analyysit)
Kruskalin algoritmi ratkaisee ongelman ajassa O(e log e) kun Primin algoritmille aikavaativuus on O(e log v). Kruskal sopii paremmin harvoille verkoille, sillä e viittaa tässä kaariin (edge) ja v solmuihin (vertex). 

## Lähteet
[Kruskalin algoritmi labyrintin luomiseen](https://en.wikipedia.org/wiki/Maze_generation_algorithm#Randomized_Kruskal's_algorithm)<br/>
[Primin algoritmi labyrintin luomiseen](https://en.wikipedia.org/wiki/Maze_generation_algorithm#Randomized_Prim's_algorithm)<br/>
[A* algoritmi labyrintin ratkaisuun](https://en.wikipedia.org/wiki/Maze-solving_algorithm#Shortest_path_algorithm)<br/>
[Pienin virittävä puu](https://en.wikipedia.org/wiki/Minimum_spanning_tree)


