# BoxApplication
Oefening op:
Generics
Lambda Expressions
File.IO
FileAttributes
Collections
(multi) Threading

## Opgave

### Inleiding

Bijgevoegd vind je het bestand Boxes.txt dit bestand bevat 1100 regels met Box properties volgens het volgende patroon:

DoosSoort;breedte;hoogte;Kleur;gewicht;gevaargoed

De DoosSoort geeft het type doos weer.

### Klassen

Maak een generische klasse Doos, waarmee je het type kan specifiëren, vervolgens geef je voor iedere property (er zijn 5 properties - breedte, hoogte, kleur, gewicht, gevaargoed).

De types dozen zijn de volgende:
Wood (subklasse van Package)

Metal (subklasse van Package)

Paper (subklasse van Package)

Plastic (subklasse van Package)

### ENUM

Voor de parameter van de kleur van de door maak je een Enum met als opties YELLOW en BROWN.

### Doelstelling

Zorg ervoor dat je alleen Doos objecten kunt maken die een type hebben van een subklasse van Package.

Vervolgens lees je het het bestand Boxes.txt in en maak je voor iedere regel een Box object (denk eraan de eerste parameter van de txt file bevat het type doos (dit is dus geen property).
Deze Box objecten steek je in een gepaste collectie en je zorgt ervoor dat het inlezen op een aparte thread gebeurd.

Je sorteert/splitst de dozen per kleur en zorgt ervoor dat iedere kleur zijn eigen collectie met dozen heeft.

Je sorteert deze collecties op basis van het gewicht van de doos van licht naar zwaar.

Vervolgens schrijf je deze weg naar de bestanden BoxYellow.txt en BoxBrown.txt .

Schrijf de 50 zwaarste dozen weg naar het bestand Heavy.txt en zorg ervoor dat dit op een aparte thread gebeurd. (gebruik de juiste collectie, je moet tenslotte alle dozen hebben) en doe hetzelfde voor de 50 lichtste dozen.

Print vervolgens alle dozen af die een hoogte en breedte hebben van 10.0 (Stream)

Print alle dozen af die gevaarlijk zijn.

### FileAttributes - Opgave

Vervolgens vraag je het pad op van het bestand Heavy.txt en vraag je de volgende properties op van het bestand:

*isHidden

*grootte

*aanmaakdatum

*ReadOnly

Schrijf dit weg naar het bestand FileProperties.txt

### JAR

Als laatste zorg je ervoor dat je de applicatie kunt uitvoeren via een .JAR bestand.

### NOTA

Nota: Indien je Threads tegelijk kan laten lopen doe je dit, je hoeft niet te wachten tot iedere thread klaar is (denk zelf eens na welke thread moet wachten en welke niet.)



## Contributors

B.D - PXL Student
