# EksamensProjekt
Datamatiker 2023 eksamensprojekt i 2. semester

# Introduktion

Programmet er et projektstyringsværktøj, som er et system der giver overblik over en liste af vilkårlige projekter samt moduler under disse. Systemet er designet til vores Product Owner (PO) og derfor har det overblik over de nødvendigheder, de skal kunne benytte sig af.

Dette er de forudsætninger, der benyttes for at køre vores applikation lokalt på ens egen maskine.

# Software forudsætninger
Software forudsætninger til at køre programmet lokalt:
* Java - 17 eller 19
* MySql Workbench - 8.0.32
* Springboot -  3.0.6
* Thymeleaf - 3.0.6
* HTML - HTML5
* CSS - CSS3
* Maven - 4.0.0

# Simpel Bruger anvisning

Vores system har fokus på konsistents af knapper, dette har vi gjort, da vi mener det utroligt vigtigt at minimere kognitiv belastning for at maksimere indlæringsevnen, derfor har vi igennem hele systemet fokus på konsistense knapper.

Typiske knapper:

* Create - Denne knap benyttes til at skabe et projekt eller module.
* Delete - Denne knap benyttes til at slette et projekt eller module.
* View - Denne knap benyttes til at se hvad et projekt eller module indholder.
* Update - Denne knap benyttes til at updatere både projekter og modules.
* Login - Logger ind i systemet.
* Log ud - Logger ud af systemet.
* Go back - Går tilbage til forrige side.
* Assign employes - Projekt manegers kan tilkoble ansatte til modules.


Applikationen er deployet på cloud så den er offentligt tilgængelig i linket under Web Service.
Applikationen ligger på Render.com som bruger vores docker file til at bygge et image af programmet og køre applikationen. 
Vi har valgt at køre programmet på en cloud, da det gør det væsentligt lettere for brugerne at kunne benytte systemet uden at skulle bekymre sig om de mange softwareforudsætninger, som tidligere nævnt.

# Web Service
https://alpha-solutions-eksamensprojekt.onrender.com
