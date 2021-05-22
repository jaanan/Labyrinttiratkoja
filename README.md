# Labyrinttiratkoja
Tiralabra alkukesä 2021

## Aihe    
Verkot ja polun etsintä; miten löydetään tehokkaasti nopein/lyhin reitti labyrintistä ulos.    
## Ohjelmointikieli  
Java  
## Työn laajuus  
Ohjelma, joka luo käyttäjän toivoman kokoisen satunnaisen labyrintin ja sitten ratkaisee sen.<br/>

[Määrittelydokumentti](https://github.com/jaanan/labyrinttiratkoja/blob/master/dokumentaatio/m%C3%A4%C3%A4rittelydokumentti.md)

[Viikkoraportti 1](https://github.com/jaanan/labyrinttiratkoja/blob/d240f530ac72d38cb9af57dda6739d9a1252f5a7/dokumentaatio/viikkoraportti1.md)

[Viikkoraportti 1](https://github.com/jaanan/labyrinttiratkoja/blob/29074ed820674eb80dcaf1bd422deead33b0b225/dokumentaatio/viikkoraportti2.md)

## Käyttöohjeet

Labyrinttiratkoja on Java-sovellus. Tietokoneella tulee olla asennettuna JDK 11, jotta sovellus toimii. 

### 1. Kloonaa sovellus

git clone https://github.com/jaanan/labyrinttiratkoja.git  
cd labyrinttiratkoja

### 2. Kokoa

javac -sourcepath ./src -d bin src/labyrintti/Main.java

### 3. Aja

java -classpath ./bin labyrintti.Main

Jos labyrintti näyttää hassulta, niin vedä terminaali-ikkunaa isommaksi.
