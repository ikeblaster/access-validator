Západočeská univerzita v Plzni

Fakulta aplikovaných věd

Katedra informatiky a výpočetní techniky

Diplomová práce

Systém pro automatickou kontrolu samostatných prací vytvořených v MS
Access

Plzeň, 2018 Vojtěch Kinkor

Zadání

1)  Seznamte se s formátem souboru MS Access .accdb a možnostmi jeho
    čtení.

2)  Seznamte se validátorem na portálu ZČU.

3)  Vytvořte konfigurovatelný systém pro kontrolu samostatných prací
    vytvořených v MS Access se zaměřením na splnění zadání a
    plagiarismus.

4)  Část systému pro kontrolu splnění zadání adaptujte pro validátor.

5)  Celý systém pečlivě otestujte.

**\< tato strana bude nahrazena originálním zadáním / kopií ! \>**

Prohlášení

Prohlašuji, že jsem diplomovou práci vypracoval samostatně a výhradně s
použitím citovaných pramenů.

V Plzni dne \<TODO: 0. 0. 2018\>

Vojtěch Kinkor

Abstract

**Systém pro automatickou kontrolu samostatných prací vytvořených\
v MS Access**

text

Abstrakt

**System for Automatic Checking of Student Works Created in MS Access**

text

Obsah

[1 Úvod 1](#úvod)

[2 Databázový software Microsoft Access
2](#databázový-software-microsoft-access)

[2.1 Základní informace 2](#základní-informace)

[2.2 Prvky databáze 2](#prvky-databáze)

[2.2.1 Tabulky 2](#tabulky)

[2.2.2 Vazby mezi tabulkami 2](#_Toc510188376)

[2.2.3 Uložené dotazy 2](#dotazy)

[2.2.4 Formuláře 2](#formuláře)

[2.2.5 Sestavy 2](#sestavy)

[2.3 Formát MDB a ACCDB 2](#formát-mdb-a-accdb)

[2.3.1 Metadata 2](#metadata)

[2.4 Možnosti čtení souborů ACCDB 2](#možnosti-čtení-souborů-accdb)

[2.4.1 ODBC 2](#odbc)

[2.4.2 Microsoft Office Interop 2](#microsoft-office-interop)

[2.4.3 MDBTools 2](#mdbtools)

[2.4.4 Jackcess 2](#jackcess)

[2.4.5 Další možnosti (JDBC) 2](#další-možnosti-jdbc)

[3 Portál ZČU 3](#portál-zču)

[3.1 Základní informace 3](#základní-informace-1)

[3.2 Validátor studentských prací 3](#validátor-studentských-prací)

[3.3 Vytvoření nové validační domény
3](#vytvoření-nové-validační-domény)

[4 Analýza řešení 4](#analýza-řešení)

[4.1 Požadavky na řešení 4](#požadavky-na-řešení)

[4.2 Případy užití 4](#případy-užití)

[4.3 Validace databáze 4](#validace-databáze)

[4.4 Vyhodnocení plagiarismu 4](#vyhodnocení-plagiarismu)

[5 Implementace 5](#implementace)

[5.1 Použité technologie 5](#použité-technologie)

[5.2 Struktura aplikace 5](#struktura-aplikace)

[5.3 Validace databáze 5](#validace-databáze-1)

[5.4 Implementovaná validační pravidla
5](#implementovaná-validační-pravidla)

[5.5 Hledání podobností a detekce plagiarismu
5](#hledání-podobností-a-detekce-plagiarismu)

[5.6 Grafické rozhraní 5](#grafické-rozhraní)

[5.7 Adaptace pro validátor portálu ZČU
5](#adaptace-pro-validátor-portálu-zču)

[6 Testování 6](#testování)

[6.1 Validační pravidla 6](#validační-pravidla)

[6.2 Konzolová aplikace pro validátor portálu ZČU
6](#konzolová-aplikace-pro-validátor-portálu-zču)

[6.3 Grafické rozhraní 6](#grafické-rozhraní-1)

[7 Závěr 7](#závěr)

[Reference 8](#reference)

[Přílohy 9](#_Toc510188411)

[A Uživatelská příručka 9](#a-uživatelská-příručka)

[Spuštění a kompilace nástroje 9](#spuštění-a-kompilace-nástroje)

[Obsluha nástroje 9](#obsluha-nástroje)

[B Obsah přiloženého média 9](#b-obsah-přiloženého-média)

Úvod
====

Databázový software Microsoft Access
====================================

Základní informace
------------------

Microsoft Access je nástroj řadící se mezi takzvané systémy řízení báze
dat (SŘBD či DBMS -- *database management systém*). Jedná se o software,
který umožňuje práci s relačními databázemi. Je součástí kancelářského
balíku Microsoft Office, případně prodáván i samostatně.

Aplikace používá pro ukládání dat technologii Microsoft Jet Database
Engine. Jednotlivé databáze jsou typicky uloženy v jediném souboru ve
formátu MDB, nebo ACCDB.

Prvky databáze
--------------

Dále jsou uvedeny různé prvky, které mohou být součástí databáze.

### Tabulky

Jedná se o stěžejní součást každé databáze. Tabulku lze definovat jako
strukturovanou kolekci dat. Skládá se ze sloupců a řádků (též záznamů) a
v rámci databáze má unikátní název.

Struktura tabulky

Struktura je definována pomocí sloupců, které mají specifikovaný název
(unikátní v rámci tabulky) a datový typ. Microsoft Access[^1] nabízí
následující datové typy \[1\]:

-   Krátký text (dříve Text) -- text do délky 255 znaků.

-   Dlouhý text (dříve Memo) -- text bez

-   Datum a čas

-   Měna

-   Automatické číslo

-   Ano/ne

-   Objekt OLE

-   Hypertextový odkaz

-   Příloha

-   Počítané

-   Průvodce vyhledáváním

Datové typy

[]{#_Toc510188376 .anchor}Relace mezi tabulkami

### Dotazy

### Formuláře

### Sestavy

Formát MDB a ACCDB
------------------

### Metadata

Možnosti čtení souborů ACCDB
----------------------------

### ODBC

### Microsoft Office Interop

### MDBTools

### Jackcess

### Další možnosti (JDBC)

Portál ZČU
==========

Základní informace
------------------

Validátor studentských prací
----------------------------

Vytvoření nové validační domény
-------------------------------

Analýza řešení
==============

Požadavky na řešení
-------------------

Případy užití
-------------

Validace databáze
-----------------

Vyhodnocení plagiarismu
-----------------------

Implementace
============

Použité technologie
-------------------

Struktura aplikace
------------------

Validace databáze
-----------------

Implementovaná validační pravidla
---------------------------------

Hledání podobností a detekce plagiarismu
----------------------------------------

Grafické rozhraní
-----------------

Adaptace pro validátor portálu ZČU
----------------------------------

Testování
=========

Validační pravidla
------------------

Konzolová aplikace pro validátor portálu ZČU
--------------------------------------------

Grafické rozhraní
-----------------

Závěr
=====

Reference {#reference .ListParagraph}
=========

<https://support.office.com/en-us/article/data-types-for-access-desktop-databases-df2b83ba-cef6-436d-b679-3418f622e482>

\[1\] Data types for Access desktop databases. *Microsoft Office help
and training - Office Support.* \[Online\] \[Citace: 22. 3. 2018\]. a
aaa Dostupné z:

Přílohy {#přílohy .ListParagraph}
=======

A Uživatelská příručka {#a-uživatelská-příručka .ListParagraph}
----------------------

### Spuštění a kompilace nástroje {#spuštění-a-kompilace-nástroje .ListParagraph}

### Obsluha nástroje {#obsluha-nástroje .ListParagraph}

Typický postup práce s nástrojem je následující:

1)  \...

B Obsah přiloženého média {#b-obsah-přiloženého-média .ListParagraph}
-------------------------

Součástí práce je přiložené paměťové médium (DVD) obsahující tyto
adresáře a soubory:

-   Project/ -- adresář obsahující projekt vytvořené aplikace,

-   Kinkor\_A16N0040P\_DP.pdf -- text této práce ve formátu PDF,

-   readme.txt -- textový soubor obsahující popis struktury DVD.

Obsah přiloženého média (včetně aktuální verze nástroje) je možné najít
též v repozitáři projektu v rámci služby GitHub na adrese:

<https://github.com/ikeblaster/access-validator/>

[^1]: Aktuálně ve verzi 2016.
