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

Jeres system fokuserer på konsistensen af knapper. Dette er vigtigt for at minimere den kognitive belastning og maksimere indlæringsevnen. I hele systemet er der derfor fokus på konsistensen af knapper.

Her er nogle typiske knapper, I anvender:

* Create: Denne knap bruges til at oprette et projekt eller en modul.
* Delete: Denne knap bruges til at slette et projekt eller en modul.
* View: Denne knap bruges til at se, hvad et projekt eller en modul indeholder.
* Update: Denne knap bruges til at opdatere både projekter og moduler.
* Login: Logger ind i systemet.
* Log ud: Logger ud af systemet.
* Go back: Går tilbage til den forrige side.
* Assign employees: Projektledere kan tilknytte medarbejdere til moduler.

Applikationen er deployet på cloud så den er offentligt tilgængelig i linket under Web Service.
Applikationen ligger på Render.com som bruger vores docker file til at bygge et image af programmet og køre applikationen. 
Vi har valgt at køre programmet på en cloud, da det gør det væsentligt lettere for brugerne at kunne benytte systemet uden at skulle bekymre sig om de mange softwareforudsætninger, som tidligere nævnt.

# Web Service
https://alpha-solutions-eksamensprojekt.onrender.com
