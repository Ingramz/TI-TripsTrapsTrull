Trips-traps-trull
=================

Programmeerimisülesanne aines Tehisintellekt I (MTAT.06.008)

3x3 trips-traps-trull arvuti vastu, mis kasutab "Ideaalse mängu" strateegiat - 8 sammu,
mille kohta leiab rohkem infot [Wikipediast] (http://en.wikipedia.org/wiki/Tic-tac-toe#Strategy).

## Kompileerimine

Kõik vajalikud Java failid on src kaustas, käivitatav main meetod asub Main klassis. See kuidas failid kompileeritakse, ei oma tähtsust. Kui ei ole võimalust endal kompileerida, sisaldab repositoorium valmisehitatud versiooni viimasest koodist.

## Käivitamine

Tõmmatud JAR-i puhul kasutada käsurealt ``java -jar TTT.jar`` kaustas, kus antud fail asub või käivitada tavalisel viisil.

Käivitamisel kuvatakse dialoogaken kus saab valida kes alustab või väljuda mängust.

Mängu alustamisel avatakse uus aken, kus algab mäng ning kasutaja teeb oma käigu ühte tühjadest ruutudest.

Mängu lõppedes antakse mängijale sellest märku koos tulemusega, uut mängu saab alustada kui sulgeda mängu aken, mille järel minnakse algsesse dialoogaknasse tagasi.

## Algoritm

Nagu eelnevalt mainitud, on algoritmi sammud kirjas [Wikipedias] (http://en.wikipedia.org/wiki/Tic-tac-toe#Strategy), kus kõik peale sammude 3 ja 4 on intuitiivsed, seega välja uuesti tooma ei hakka.

Samm 3 - _fork_ tähendab olukorra loomist, kus oleks võimalik ühe käiguga luua kahes reas "kaks või enam" tingimus. Sõltumata vastase käigust, jääb üks rida alati pärast seda vabaks, mille kaudu on võimalik võita. Kui _fork_ on loodud, siis saab ka selle loonud mängija järgmise käiguga võita.

Samm 4 - _fork_-i blokeerimine on tegevus, kus ennetatakse vastase samm 3 tegemist. Kui selliseid kohti on üks, siis käiakse sinna, rohkema juhul tuleb luua vastasele "kaks või enam" rida enda märgenditest nii, et vastase kaitsev käik ei looks uut _fork_-i.

Kaheksast sammust üks on alati üks võimalik (sammud 5,7,8) ja sellise strateegiaga arvuti kaotada ei tohiks, sest _fork_-idel tekkida ei lasta (samm 4) ning muul juhul ei lasta kaotada sammu 2 tõttu.

Väidan et algoritm on intelligentne (võrreldes mõnede teiste strateegiatega, nagu näiteks täiesti juhuslik käigu tegemine). Eesmärgiks on mängida keskmisest paremini või samal tasemel, mis on saavutatud sellega, et mängitakse viiki või võidetakse (inimesel on võimalik eksida, algoritmi järgides eksida ei saa ning armu ei anta).
