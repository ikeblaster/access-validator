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

System for Automatic Checking of Student Works Created in MS Access

This thesis describes a design and implementation of a system for
automatic checking of database files created in Microsoft Access. The
goal is to automatically check structure and content of databases
against rules specified by a user. In addition, the system is also able
to detect plagiarism amongst databases. Presented solution consists of
an application with graphical user interface and a tool adapted for use
in cooperation with a student works validator, which is part of ZČU
portal. This thesis has an ambition to reduce amount of time needed for
manual checking of students' works with reference to assignment
instructions.

Abstrakt

Systém pro automatickou kontrolu samostatných prací vytvořených v MS
Access

Cílem této práce je navrhnout a implementovat systém, který umožní
automatické kontrolování databázových souborů vytvořených v aplikaci
Microsoft Access. Podstatou kontroly je ověření existence a struktury
objektů uložených v databázových souborech dle uživatelem zadaných
kritérií, další částí pak je detekce plagiarismu na základě podobnosti
databází. Vytvořené řešení se skládá z aplikace s grafickým rozhraním a
části adaptované pro použití v rámci validátoru studentských prací na
portálu ZČU. Výsledkem této práce je systém schopný automaticky
vyhodnocovat samostatné práce s ohledem na splnění zadání a vyučujícím
tak usnadnit jejich kontrolu.

Obsah

[1 Úvod 1](#úvod)

[2 Databázový software Microsoft Access
2](#databázový-software-microsoft-access)

[2.1 Základní informace 2](#základní-informace)

[2.2 Objekty uložené v databázi 2](#objekty-uložené-v-databázi)

[2.2.1 Tabulky 2](#tabulky)

[2.2.2 Dotazy 6](#dotazy)

[2.2.3 Formuláře 6](#formuláře)

[2.2.4 Sestavy 7](#sestavy)

[2.2.5 Skryté systémové tabulky 7](#skryté-systémové-tabulky)

[2.3 Metadata databázových souborů 7](#metadata-databázových-souborů)

[2.4 Formát ACCDB a možnosti jeho čtení
8](#formát-accdb-a-možnosti-jeho-čtení)

[2.4.1 ODBC 9](#odbc)

[2.4.2 Microsoft Office Interoperability
10](#microsoft-office-interoperability)

[2.4.3 MDB Tools 10](#mdb-tools)

[2.4.4 MDB Tools Java 11](#mdb-tools-java)

[2.4.5 Jackcess 11](#jackcess)

[2.4.6 JDBC 11](#jdbc)

[3 Portál ZČU 13](#portál-zču)

[3.1 Základní informace 13](#základní-informace-1)

[3.2 Validátor studentských prací 13](#validátor-studentských-prací)

[3.2.1 Validační servery 15](#validační-servery)

[3.2.2 Validační domény 15](#validační-domény)

[3.2.3 Princip validace práce 17](#princip-validace-práce)

[4 Analýza řešení kontroly prací 19](#analýza-řešení-kontroly-prací)

[4.1 Požadavky na řešení 19](#požadavky-na-řešení)

[4.2 Případy užití 20](#případy-užití)

[4.3 Metoda čtení databázových souborů
21](#metoda-čtení-databázových-souborů)

[4.4 Validace databázových souborů 22](#validace-databázových-souborů)

[4.5 Vyhodnocení plagiarismu 24](#vyhodnocení-plagiarismu)

[4.6 Grafické rozhraní 25](#grafické-rozhraní)

[4.7 Adaptace pro validátor studentských prací
26](#adaptace-pro-validátor-studentských-prací)

[4.8 Návrh struktury systému 26](#návrh-struktury-systému)

[5 Implementace systému pro automatickou kontrolu prací
28](#implementace-systému-pro-automatickou-kontrolu-prací)

[5.1 Použité technologie 28](#použité-technologie)

[5.2 Struktura implementovaného systému
28](#struktura-implementovaného-systému)

[5.2.1 Modul validator 29](#modul-validator)

[5.2.2 Modul configurator 31](#modul-configurator)

[5.3 Čtení databázových souborů 33](#čtení-databázových-souborů)

[5.4 Validace databáze 35](#validace-databáze)

[5.4.1 Implementovaná validační pravidla
36](#implementovaná-validační-pravidla)

[5.4.2 Ukládání nakonfigurovaných pravidel do souborů
37](#ukládání-nakonfigurovaných-pravidel-do-souborů)

[5.5 Hledání podobností a detekce plagiarismu
38](#hledání-podobností-a-detekce-plagiarismu)

[5.6 Grafické rozhraní 38](#grafické-rozhraní-1)

[5.7 Adaptace pro validátor portálu ZČU
38](#adaptace-pro-validátor-portálu-zču)

[6 Testování vytvořeného systému 39](#testování-vytvořeného-systému)

[6.1 Validace a validační pravidla 39](#validace-a-validační-pravidla)

[6.2 Detekce plagiarismu 39](#detekce-plagiarismu)

[6.3 Grafické rozhraní 39](#grafické-rozhraní-2)

[6.4 Konzolové rozhraní pro validátor portálu ZČU
39](#konzolové-rozhraní-pro-validátor-portálu-zču)

[7 Závěr 40](#závěr)

[Reference 41](#reference)

[Přílohy 44](#přílohy)

[A Uživatelská příručka 44](#a-uživatelská-příručka)

[Spuštění a kompilace nástroje 44](#spuštění-a-kompilace-nástroje)

[Obsluha nástroje 44](#obsluha-nástroje)

[B Obsah přiloženého média 44](#b-obsah-přiloženého-média)

Úvod
====

Databázový software Microsoft Access
====================================

Základní informace
------------------

Microsoft Access je nástroj řadící se mezi takzvané systémy řízení báze
dat (SŘBD či DBMS -- database management system). Jedná se o software,
který umožňuje práci s relačními databázemi. Je součástí kancelářského
balíku Microsoft Office, případně prodáván i samostatně. Pro vytváření a
správu databáze nabízí uživatelům přehledné grafické rozhraní
[1](#_toc_1)\[\].

Aplikace používá pro ukládání dat technologii Microsoft Jet Database
Engine, v novějších verzích poté nazývanou Access Database Engine.
Jednotlivé databáze jsou typicky uloženy v jediném souboru ve formátu
ACCDB, nebo MDB[2](#_toc_2) \[\].

Objekty uložené v databázi
--------------------------

V následujících podkapitolách jsou uvedeny různé objekty, které mohou
být uloženy v databázích vytvořených aplikací Microsoft Access.

### Tabulky

Tabulky jsou stěžejní součástí každé databáze. Lze je definovat jako
strukturovanou kolekci dat. Skládá se ze sloupců a řádků (též záznamů) a
v rámci databáze má unikátní název[1](#_toc_1) \[ str. AC 4\].

Sloupce tabulky

Struktura tabulky je definována sloupci, které mají specifikovaný název
(unikátní v rámci tabulky) a datový typ. Microsoft Access[^1] podporuje
následující datové typy[1](#_toc_1)[3](#_toc_3) \[ str. AC 57, \]:

-   *Automatické číslo* -- pro každý nový záznam se automaticky nastaví
    > na následující hodnotu posloupnosti, nebo na náhodné číslo (dle
    > nastavení). Typicky se využívá pro sloupec označený jako primární
    > klíč (viz dále).

-   *Číslo* -- rozsah a typ (celočíselné/s desetinnou čárkou) lze zvolit
    > ve vlastnostech sloupce.

-   *Krátký text* (dříve Text) -- text do délky 255 znaků.

-   *Dlouhý text* (dříve Memo) -- text do velikosti 1 GB.

-   *Datum a čas*.

-   *Měna*** **-- specializovaný případ číselného datového typu s fixní
    > desetinnou čárkou (uchovává 4 desetinná místa).

-   *Ano/ne* -- uchovává hodnotu -1 (Ano) nebo 0 (Ne); v rámci Microsoft
    > Access zobrazeno jako zaškrtávací pole (*checkbox*).

-   *Hypertextový odkaz*.

-   *Objekt OLE*** **-- umožňuje vložit speciální objekty, například
    > obrázek, jiný dokument, či odkaz na soubor.

-   *Příloha* -- umožňuje vložit libovolný soubor jako součást záznamu.
    > Jedná se o univerzálnější možnost k předchozímu.

-   *Počítané* -- automatické vložení hodnoty vypočítané na základě
    > zadaného vzorce.

Každému sloupci lze dále nastavit různé vlastnosti dle vybraného
datového typu -- typicky se jedná o ověřovací pravidla (validace vstupu
od uživatele ještě před přidáním záznamu do databáze), výchozí hodnotu a
dále nastavení zobrazení v tabulce (formátování, zarovnání, titulek po
najetí myší, atp.).

Primární klíč

Tabulka může mít primární klíč -- typicky se jedná o sloupec, jehož
hodnoty jsou unikátní a vždy zadané (tzv. *not null*). V případě, že
vytvoříme primární klíč pomocí více sloupců, nazýváme jej složeným
primárním klíčem[1](#_toc_1) \[ str. AC A4\].

Primární klíč slouží k jednoznačnému určení konkrétního záznamu v
tabulce, čehož se využívá při vytváření dotazů nebo tvoření relačních
vazeb mezi tabulkami. Pro vytváření primárních klíčů se obvykle používá
datový typ Automatické číslo, který každému záznamu přiřadí unikátní
celé číslo. Často bývá takový sloupec pojmenován „ID"
(*identification*)[1](#_toc_1) \[ str. AC A3-A4\].

Relace mezi tabulkami a cizí klíče

V případě, že chceme propojit více tabulek mezi sebou, využijeme tzv.
relačních vazeb, zkráceně relací. Jedná se o situaci, kdy záznam v
tabulce A odkazuje („má referenci") na jeden konkrétní záznam v tabulce
B. To lze obecně zajistit přidáním tzv. *cizího klíče* do tabulky
A -- sloupce, který bude obsahovat pouze hodnoty primárního klíče
tabulky B (příp. skupiny sloupců, pokud se jedná o složený primární
klíč). Rozlišují se tři typy relačních vazeb[1](#_toc_1)[4](#_toc_4) \[
str. AC A5-A8, str. 419\]:

-   *Relace typu 1:1* -- jednomu záznamu v tabulce A odpovídá žádný, či
    právě jeden záznam v tabulce B. Typicky je tato relace vhodná v
    situaci, kdy jen málo záznamů odkazuje do druhé tabulky. Pro cizí
    klíče zde platí, že jejich hodnoty jsou v rámci tabulky unikátní.
    Alternativně lze pro referencování použít primární klíče obou
    tabulek (viz obrázek 2.1). [4](#_toc_4)\[ str. 420\].

![](media/image1.emf){width="3.622047244094488in"
height="1.3385826771653544in"}

Obrázek . -- model relace 1:1 využívající v obou tabulkách primární
klíč.

-   *Relace typu 1:N* -- k více záznamům v tabulce A lze přiřadit jeden
    > záznam z tabulky B. Tato vazba je vždy realizována pomocí již
    > zmíněných cizích klíčů. Jedná se o nejčastěji využívanou
    > vazbu[4](#_toc_4) \[ str. 421\].

![](media/image2.emf){width="3.622047244094488in"
height="1.5708661417322836in"}

Obrázek . -- model relace 1:N; cizí klíč *Kolej ID* v tabulce *Student*\
referencuje primární klíč tabulky *Kolej*.

-   *Relace typu M:N* -- k M záznamům v tabulce A lze přiřadit N záznamů
    > z tabulky B. Relace se realizuje pomocí *spojové tabulky* (též
    > rozkladové) a dvojice relací 1:N. Spojová tabulka obvykle obsahuje
    > pouze sloupce cizích klíčů[4](#_toc_4) \[ str. 422\].

![](media/image3.emf){width="5.744094488188976in"
height="1.5708661417322836in"}

Obrázek . -- model relace M:N, která je tvořena dvěma\
relacemi 1:N na spojovou tabulku *StudentPředmět*.

Relace mezi tabulkami mohou zajišťovat *referenčním integritu*. Cílem je
zabránit odkazování na neexistující záznam (a rovněž tedy vzniku
osiřelých záznamů, na které byly všechny reference zrušeny). Integritní
pravidlo může dále zajistit kaskádovou aktualizaci polí -- pokud se
změní hodnota primárního klíče, změní se automaticky hodnota u všech
záznamů, které na záznam odkazují. Stejně tak může zajistit kaskádové
odstranění souvisejících záznamů -- v případě smazání záznamu budou
smazány i všechny další, které na právě tento záznam odkazovaly
[1](#_toc_1)\[ str. AC A11\].

### Dotazy

Dotazy slouží k získávání, přidávání, mazání či upravování dat v
databázi. Microsoft Access umožňuje ukládání dotazů do databáze -- lze
tedy vytvořit dotazy pro usnadnění následné práce s daty[1](#_toc_1) \[
str. AC 124\].

Dotazy mohou mít parametry, které lze využít např. pro filtrování
záznamů v rámci tabulky nebo nové hodnoty při vkládání/upravování
záznamů. Uživatel je pak při spuštění dotazu vyzván k zadání konkrétních
hodnot parametrů[1](#_toc_1) \[ str. AC 249\].

Podporovány jsou následující druhy dotazů:

-   *Výběrové (SELECT)* -- jedná se o dotaz, jehož výsledkem je množina
    > vybraných záznamů. Struktura je dána dotazem -- jednotlivé sloupce
    > mohou pocházet z různých tabulek, či být spočítané „za běhu".
    > Obecně lze považovat výběrový dotaz za analogii k databázovým
    > pohledům[1](#_toc_1) \[ str. AC 124-128\].

-   *Vytvářecí (MAKE TABLE)* -- pracuje na stejném principu jako
    > výběrový, výsledek dotazu však není ihned zobrazen uživateli, ale
    > uložen do nové tabulky[1](#_toc_1) \[ str. AC 500, 501\].

-   *Přidávací (APPEND) *-- slouží pro vkládání nových záznamů do
    > existujících tabulek[1](#_toc_1) \[ str. AC 500, 506\].

-   *Aktualizační (UPDATE)*** **-- umožňuje úpravu hodnot již
    > existujících záznamů v tabulkách [1](#_toc_1)\[ str. AC 500,
    > 512\].

-   *Křížový (CROSSTAB)*** **-- výsledkem dotazu je tzv. kontingenční
    > tabulka zobrazující data v kompaktní podobě. Typicky se používá
    > například pro sumarizaci hodnot, nalezení průměrů, maximálních
    > hodnot, atp. [1](#_toc_1)\[ str. AC 254, 256\].

### Formuláře

Formuláře poskytují přívětivé rozhraní pro vkládání či editaci záznamů v
tabulkách. Grafické rozhraní je plně konfigurovatelné a umožňuje tedy
jednotlivá pole záznamů různě seskupovat, přidat popisky, či některá
úplně skrýt. Formuláře jsou v databázi opět uloženy pod unikátním
názvem[1](#_toc_1) \[ str. AC 179\].

Access umožňují vytvořit formuláře různých druhů[1](#_toc_1) \[ str. AC
180-200\]:

-   Formuláře pro editaci jednotlivých záznamů (dále označované jako
    > standardní).

-   Navigační formuláře, které poskytují možnost přepínání mezi různými
    > formuláři a umožňují tak vytvořit komplexní rozhraní pro správu
    > celé databáze.

-   Formuláře zobrazující více položek (záznamů) najednou.

-   Datové listy, které vypadají podobně jako zobrazení tabulky (tedy
    > tabulka, kde každý řádek odpovídá jednomu záznamu), ale
    > zachovávají možnost upravovat zobrazená pole.

-   Rozdělené formuláře, které jsou kombinaci standardních formulářů v
    > jedné části a datového listu v druhé části obrazovky.

-   Modální dialogová okna, která mají stejné možnosti jako standardní
    > formuláře, ale zobrazují se v samostatném okně a jsou

### Sestavy

Sestavy slouží pro vytváření výpisů dat z databáze v přívětivé podobě,
zobrazující typicky více záznamů na jedné straně, na rozdíl od formulářů
ale neumožňuje editaci dat. Často se využívá pro následné vytisknutí.
Při návrhu se definuje záhlaví a zápatí stránek a rozložení prvků pro
každý záznam („řádek" sestavy)[1](#_toc_1) \[ str. AC 208\].

### Skryté systémové tabulky

Databáze dále obsahují několik skrytých systémových tabulek, jejichž
název vždy začíná prefixem MSys. Například v tabulce MSysRelationships
jsou uloženy všechny relace mezi objekty (tabulkami a dotazy). Jednou ze
zajímavějších tabulek je MSysObjects obsahující seznam všech objektů
databáze a k nim i různé další údaje, čehož lze využít při hledání
metadat (viz kapitola 2.3)[1](#_toc_1)[5](#_toc_5) \[ str. AC 582, \].

Metadata databázových souborů
-----------------------------

Kromě samotných uživatelských dat a databázových objektů lze v databázi
nalézt i další doplňující údaje, obecně označované jako
metadata[5](#_toc_5)[6](#_toc_6) \[, \]:

-   *Autor databáze* -- jméno uživatele a název organizace.

-   *Formát souboru* -- verze aplikace, ve které byla databáze
    > vytvořena, nastavení režimu kompatibility, atp.

-   *Údaje o databázi a objektech *-- datum vytvoření a poslední úpravy.

-   *Uživatelské nastavení aplikace*** **-- nastavení navigačního panelu
    > (řazení/seskupení/šířka/...) a jiných prvků GUI až např. grafické
    > rozvržení relací (viz obr. 4).

![](media/image4.png){width="5.658333333333333in"
height="3.622642169728784in"}

Obrázek . -- grafické rozvržení relací mezi tabulkami v aplikaci
Microsoft Access 2016,\
které lze rovněž považovat za metadata uložená v databázi.

Formát ACCDB a možnosti jeho čtení
----------------------------------

Nativním formátem pro ukládání Access databází je od verze 2007 ACCDB,
v předchozích verzích byl hlavním formátem MDB. Oba jsou založeny na
technologii Jet (u formátu ACCDB také označované jako Access Database
Engine) a jsou si tedy technologicky podobné. Z uživatelského hlediska
jsou rozdíly zejména v možnostech zabezpečení
dat[2](#_toc_2)[6](#_toc_6)[7](#_toc_7) \[, , \].

Jedná se o proprietární binární formáty vyvíjené společností Microsoft
bez dostupné specifikace, avšak je zřejmé, že součástí databázových
souborů ve formátu ACCDB, potažmo MDB, musí být uložené zmíněné objekty
a metadata [6](#_toc_6)\[\].

Jediným oficiálním nástrojem pro správu je právě Microsoft Access, pro
přístup k datům z jiných aplikací pak technologie ODBC a OLE DB. To
velmi omezuje možnosti programového přístupu k databázím -- pokud bychom
vzali v potaz pouze oficiální nástroje, jsme limitováni na systémy s
nainstalovanou aplikací Microsoft Access (a tím pádem i operačním
systémem Microsoft Windows). V současné době jsou však dostupné i
nástroje vzniklé na základě reverzního inženýrství formátů ACCDB/MDB bez
závislosti na programovém vybavení počítače [6](#_toc_6)\[\].

V následujících podkapitolách jsou zmíněny všechny možnosti čtení
souborů ACCDB včetně výhod a nevýhod, jaké přináší.

### ODBC

ODBC (Open Database Connectivity) je standardizované API pro přístup k
datům uloženým v databázích. Připojení ke konkrétním databázím je
zajištěno speciálními ovladači, které lze do systému doinstalovat. Pro
komunikaci skrze ODBC se typicky využívá jazyk SQL (Structured Query
Language), ovladač poté zajistí vykonání příkazu nad konkrétní
databází[8](#_toc_8) \[\].

Pro přístup k ACCDB databázím v rámci OS Microsoft Windows se využívají
ovladače Access Database Engine nainstalované spolu s aplikací Microsoft
Access, případně ze samostatného distribučního balíku. Pro další
platformy existují komerční Access ODBC ovladače[^2]. Vzniká zde tedy
závislost na dostupnosti ovladače, přičemž v určitých případech může být
problém jej do systému doplnit [2](#_toc_2)\[\].

Zásadní nevýhodou přístupu k datům přes ODBC API jsou omezení
vyplývající z univerzálnosti metody. Jednoduše lze pracovat pouze s daty
v tabulkách a není možné přímo přistupovat k dalším uloženým objektům.
Jedinou možnost je využít skryté systémové tabulky, pomocí kterých lze
zjistit alespoň existenci objektů [8](#_toc_8)\[\].

Novější obdobnou technologií je OLE DB (*Object Linking and Embedding,
Database*), vyvinuté firmou Microsoft původně jako nástupce ODBC; a dále
technologie ADO a ADO.NET stavící nad ODBC, resp. OLE DB[9](#_toc_9)
\[\]. Z hlediska způsobu použití a nabízených funkcí pro čtení souboru
ACCDB jsou však všechny technologie shodné.

### Microsoft Office Interoperability

Aplikace z balíku Microsoft Office lze programově ovládat pomocí technik
obecně označovaných jako *interoperability* (zkráceně *interop*).
Typicky se využívají proprietární technologie COM (Common Object Model)
a OLE (Object Linking and Embedding) vyvinuté firmou
Microsoft[9](#_toc_9) \[\]. Dále jsou poskytovány *Primary Interop
Assemblies *-- knihovny určené pro použití na platformě .NET (tedy v
tzv. řízeném kódu) obalující COM volání do objektového rozhraní. V
současné době jsou tyto knihovny nejjednodušší cestou pro programové
ovládání aplikací Microsoft Office (mj. se využívají i pro psaní
doplňků, *plug-inů*, pro jednotlivé Office
aplikace)[10](#_toc_10)[11](#_toc_11) \[, \].

Tato technika oproti ODBC umožňuje kompletní správu databáze vč. všech
dostupných objektů bez nutnosti analyzovat obsah systémových tabulek.
Zůstává zde však omezení na systémy, kde je nainstalovaný Microsoft
Access. Jde rovněž o poměrně pomalý přístup, jelikož „interop kód"
de-facto jen ovládá aplikaci Microsoft Access spuštěnou na pozadí.

### MDB Tools

Jedná se o open-source sadu nástrojů pro práci se soubory Microsoft
Access, respektive Jet databázemi ve formátu MDB, jejíž vývoj započal
již v roce 2000. Vzhledem k uzavřenosti formátu vznikla většina nástrojů
technikami reverzního inženýrství, není tedy zaručena stoprocentní
funkčnost a kompatibilita[12](#_toc_12) \[\].

Nástroje jsou napsány v jazyce C a mají konzolové rozhraní, dále
existuje několik grafických nadstaveb pro prohlížení Access souborů.
Součástí projektu je i dokument popisující strukturu a klíčové části Jet
databází[5](#_toc_5) \[\]. V posledních letech probíhá vývoj pomalým
tempem a podpora novějších verzí Access databází včetně formátu ACCDB
není zaručena[13](#_toc_13) \[\]. Hlavní výhodou nástrojů je nezávislost
na konkrétní platformě a externích knihovnách.

### MDB Tools Java

V roce 2004 začala *portace* nástrojů MDB Tools pro jazyk Java, vývoj
však již po roce ustal[14](#_toc_14) \[\]. Následně vzniklo několik
*forků* (projektů založených na kódu původního projektu), nejaktuálnější
z nich lze nalézt pod názvem OME MDB Tools. Vývoj těchto projektů je ale
spíše pomalý -- poslední větší aktualizace proběhla v roce 2016
[15](#_toc_15)\[\].

Oproti dále uvedené knihovně Jackcess nabízí méně možností a použité je
značně komplikované[16](#_toc_16) \[\]. Překážkou je chybějící
dokumentace jak samotných nástrojů, tak i jejich programového kódu.

### Jackcess

Jackcess je Java knihovna poskytující čisté objektové rozhraní pro práci
s Microsoft Access databázemi. Její vývoj započal v roce 2005 v rámci
open-source projektu OpenHMS zaštítěného firmou Health Market Science,
Inc a funguje na stejných principech jako nástroje MDB Tools, kterými se
vývojáři na počátku inspirovali[17](#_toc_17)[18](#_toc_18) \[, \].

Kromě čtení dat z tabulek umožňuje i základní editaci struktury
databáze, výpis všech relací mezi tabulkami a výpis uložených dotazů.
Díky přístupu ke skrytým systémovým tabulkám lze vyhledat i uložené
formuláře a sestavy (reálně však lze zjistit pouze jejich existenci).
Podporuje Access databáze ve verzích 2000 až 2016 (ve formátu MDB i
ACCDB) a ve verzi 97 v režimu pro čtení. Knihovna neobsahuje rozhraní
pro spouštění SQL dotazů, neumožňuje tedy ani vyhodnocení uložených
dotazů[18](#_toc_18) \[\].

Zásadního výhodou pro potřeby této práce je přenositelnost knihovny
(nezávislost na platformě) a aktivní vývoj -- tím pádem i podpora
nejnovějších verzí Access databází. Vzhledem k distribuci v podobě
samostatné Java knihovny (rovněž dostupné v Maven repozitářích) je její
použití ve vlastní aplikaci jednoduché[19](#_toc_19) \[\].

Pro knihovnu existuje rozšíření nazvané Jackcess Encrypt umožňující
správu databází zašifrovaných heslem. Podporuje některé formy šifer
aplikací Microsoft Access a Microsoft Money[20](#_toc_20) \[\].

### JDBC

Java Database Connectivity je API pro přístup k relačním databázím a
tedy obdobou technologie ODBC pro programovací jazyk Java. API je
standardní součástí platformy Java SE. Připojení ke konkrétní databázi
je opět zajištěno ovladači určenými pro konkrétní typ
databází[21](#_toc_21) \[\].

Microsoft neposkytuje vlastní JDBC ovladač pro práci s Access/Jet
databázemi, existují ale speciální ovladače, tzv. *JDBC-ODBC bridge,*
umožňující použití ODBC ovladačů. V rámci platformy Java byl takový
ovladač standardní součástí až do verze 7; do novějších verzí jej lze
překopírovat (jde ale o neoficiální postup bez záruky funkčnosti) nebo
nahradit komerčními alternativami[8](#_toc_8)[22](#_toc_22) \[, \].

Dále existuje několik JDBC „nativních" ovladačů pro práci s Microsoft
Access databázemi. Jde zejména o open-source projekt UCanAccess, který
využívá již zmíněnou knihovnu Jackcess[23](#_toc_23) \[\]. K dispozici
jsou rovněž komerční ovladače [^3]^,^[^4]^,^[^5].

Stejně jako u technologie ODBC jsou největší nevýhodou omezení v
důsledku univerzálnosti přístupu; jinými slovy -- jednoduše pracovat je
možné pouze s daty v tabulkách a k ostatním objektům databází je přístup
obtížný či nemožný[21](#_toc_21) \[\]. Výhodou je lepší přenositelnost
na jiné platformy.

Portál ZČU
==========

Obecně termínem portál označujeme webovou aplikaci, která uživateli
poskytuje jednotným způsobem a centralizovaně informace z různých
zdrojů, které uživatele zajímají nebo se ho nějakým způsobem
týkají[24](#_toc_24) \[\]. Následující podkapitoly se věnují portálu
Západočeské univerzity v Plzni (dále jen ZČU) a validátoru studentských
prací, který je s portálem úzce spjatý a který bude

Základní informace
------------------

Portál ZČU[^6] je informační zdroj pro studijní účely používaný studenty
i zaměstnanci Západočeské Univerzity. Zastřešuje různé klíčové aplikace
univerzity -- z pohledu studentů se jedná zejména o *IS/STAG*
(Informační systém studijní agendy, umožňující zápis předmětů, zkoušek,
prohlížení rozvrhů, hodnocení kvality výuky, atp.) a *Courseware* (místo
s materiály používanými v rámci výuky předmětů) [24](#_toc_24)\[\].

Jednotlivé stránky portálu se skládají z více či méně nezávislých částí,
které se nazývají portlety. Jedním z nich je *aplikace pro správu
semestrálních prací, jejich odevzdávání a hodnocení*. Vyučujícím slouží
k vypisování témat semestrálních prací, do nichž se studenti posléze
mohou přihlašovat a odevzdávat své práce. Vyučující si může práce
jednotlivě či hromadně stáhnout, schvalovat je nebo vracet k
přepracování a zanechávat studentům poznámky a hodnocení
[24](#_toc_24)[25](#_toc_25)\[, \].

Validátor studentských prací
----------------------------

Studenti často po odevzdání své práce čekají delší dobu na zkontrolování
a ohodnocení vyučujícím. Pokud práci shledá nevyhovující, vrací ji
studentovi k přepracování. Tento proces se navíc může i několikrát
opakovat, což znamená pro studenta čekání v nejistotě a pro vyučujícího
velkou časovou zátěž.

Cílem validátoru studentských prací (který je propojen s již zmíněnou
aplikací na portálu ZČU) je eliminovat tento proces a automaticky
kontrolovat, zda je práce „vyhovující" -- to může mít mnoho podob,
například[26](#_toc_26) \[\]:

-   **Práce splňuje formální náležitosti:**

    -   soubor má požadovaný formát,

    -   soubor je vhodně pojmenován,

    -   v odevzdaném archivu jsou požadované soubory ve správné
        > adresářové struktuře,

    -   v nahraném archivu nejsou žádné nadbytečné soubory.

-   **Práce je vyhovující kvalitativně:**

    -   dokument splňuje stanovený rozsah (typicky minimální počet slov
        > či stran),

    -   zdrojový kód lze zkompilovat,

    -   program má totožný výstup, jako referenční program připravený
        > vyučujícím,

    -   odevzdaný soubor obsahuje všechny prvky stanovené zadáním.

Automatizace této kontroly poté přináší mnoho výhod [26](#_toc_26)\[\]:

-   Student se okamžitě dozví, zda jeho práce splňuje základní kritéria
    > stanovená učitelem.

-   Protože je nevyhovující práce okamžitě odmítnuta, nemůže student
    > zneužít nahrání takové práce k získání dalšího času.

-   Pokud není omezen počet odevzdání, může student u určitých úloh
    > zkoušet i různá „inovativní" řešení, u kterých si není jist
    > správností.

-   Vyučující není zatěžován mechanickým kontrolováním formální
    > správnosti.

-   K hodnocení se dostanou jen práce s určitou minimální mírou kvality.

Mezi nevýhody naopak patří [26](#_toc_26)\[\]:

-   Časová náročnost přípravy a udržování konfigurace validátoru (v
    > případě úprav zadání).

-   Striktnost v hodnocení -- označení práce jako nevyhovující kvůli
    > zanedbatelnému problému (např. chybějící mezeře ve výstupu
    > programu). Lze předejít podrobným zadáním a poskytováním informací
    > o úskalích.

-   Zvýšení rizika plagiarismu, pokud vyučující hodnocení prací plně
    > ponechá na validátoru. Lze vyřešit na straně portálu napojením
    > aplikace pro odevzdávání na systém pro odhalování plagiátů.

### Validační servery

Validátor je realizován jako služba běžící na samostatném serveru, se
kterou aplikace pro odevzdávání semestrálních prací komunikuje. Příprava
validačních pravidel a různých nástrojů pro kontrolu prací tedy probíhá
odděleně na tomto serveru, který kromě konfigurace přes terminál
poskytuje i jednoduché webové rozhraní[^7] umožňující správu pravidel a
následné testování nad vzorovými pracemi. Souběžně s tímto serverem je
dostupný testovací validační server[^8] určený pro vývoj nových funkcí
a přípravu validačních pravidel, kde nehrozí nebezpečí poškození
aktuálně používaných dat, a je tedy vhodný i pro využití v rámci
realizace této práce. Služba validátoru studentských prací je napsána v
jazyce Java a je spouštěna ve webovém kontejneru Apache Tomcat na
serveru s OS Linux [26](#_toc_26)[27](#_toc_27)\[, \].

### Validační domény

Konkrétní postup pro vyhodnocení, zda je práce „vyhovující", je určen
tzv. *validační doménou*. Ta se skládá z libovolného počtu kroků, jimiž
se zajistí validace odevzdané práce, a tedy určení, zda bude práce
přijata, či vrácena studentovi. Domény jsou uloženy na validačním
serveru, kde je má vyučující možnost libovolně přidávat, upravovat a
odebírat. Každá je uložena pod vlastním názvem -- pomocí něj ji lze
následně nastavit v rámci portletu pro odevzdávání prací a tím zajistit
validaci odevzdaných prací. Na serveru je pro každou doménu vytvořen
adresář s konfigurací, kam lze umístit další soubory potřebné pro
validaci (např. referenční řešení, pomocné programy,
atp.)[26](#_toc_26)[28](#_toc_28) \[, \].

Jednotlivé kroky validace představují konkrétní akce, které se mají
provést. V rámci domény mají unikátní název a volitelný popis. Pro každý
krok lze určit podmínku, za které se má akce provést[28](#_toc_28) \[\]:

-   *Vždy*.

-   *Nikdy*.

-   *Validace ještě neobsahuje chybu* -- neboli žádný předchozí krok
    neskončil chybou.

-   *Validace již obsahuje chybu* -- alespoň jeden předchozí krok
    skončil chybou.

-   *Vlastní skript* -- lze napsat vlastní skript v jazyce JavaScript,
    > který například vyhodnotí výsledek předchozí akce a určí, zda se
    > má krok provést.

Akce lze rozdělit do několika kategorií[29](#_toc_29) \[\]:

-   *Textové výpisy *-- pro zlepšení orientace je vhodné vypisovat
    > relevantní informace k právě probíhané kontrole a jejím výsledkům
    > (tedy např. *„Kontrola počtu stran"* nebo *„Chyba -- očekáváno
    > alespoň 20 stran, odevzdaný soubor obsahuje 17"*). Výpisy mohou
    > být informativní, varovné či chybové.

-   *Spouštění vlastních akcí* -- jedná se o předem definované
    > komplexnější validace, které lze ovládat pomocí parametrů. V
    > současné době je dostupných 31 akcí rozdělených do několika
    > skupin, např.: kompilace souboru, rozbalení ZIP souboru, spočítání
    > slov/stran dokumentu, spuštění Java programu, kontrola názvu
    > souboru, nalezení souboru v adresáři, porovnání PNG souborů, ...
    > Validátor lze rozšířit o další vlastní akce naprogramováním a
    > nahráním tzv. *validačních modulů* na validační
    > server[27](#_toc_27)[30](#_toc_30) \[, \].

-   *Vlastní skript* -- opět lze napsat vlastní skript v jazyce
    > JavaScript. Pro spouštění skriptů se používá knihovna Rhino, která
    > umožňuje přistupovat i k běžným funkcím platformy
    > Java[27](#_toc_27)[28](#_toc_28) \[, \].

-   *Skok na jiný krok validace* a *ukončení validace* -- ve spojení s
    > podmínkami lze takto řídit průběh validace.

### Princip validace práce

Postup validace práce odevzdané studentem zahrnuje mnoho dílčích úkonů,
stručně jej lze popsat následovně (viz též diagram na obrázku 3.1)
[27](#_toc_27)\[\]:

1.  Validační server práci přijme a načte přiřazenou validační doménu.

2.  Proběhne úvodní kontrola dle konfigurace domény.

3.  Příprava na validaci -- vytvoření pracovního adresáře, do kterého je
    > práce nahrána a ve které probíhají akce definované doménou (např.
    > rozbalení archivu, atp.).

4.  Spuštění validačního procesu validační domény -- tedy zpracování
    > všech definovaných kroků. Průběžně se generuje výsledek validace,
    > který může obsahovat informativní nebo chybová hlášení.

5.  Ukončení validačního procesu, vyčištění pracovního adresáře (závisí
    > na nastavení validační domény).

6.  Výsledek validace je vrácen volající aplikaci -- obvykle tedy
    > aplikaci pro odevzdávání studentských prací, který jej zobrazí
    > studentovi. Ten má v tu chvíli možnost zjistit, zda byla jeho
    > práce vyhovující, či nikoliv.

![](media/image5.png){width="5.252404855643045in"
height="5.377358923884515in"}

Obrázek . -- diagram znázorňující proces validace práce odevzdané
studentem. Převzato z[27](#_toc_27) \[\].

Analýza řešení kontroly prací
=============================

Cílem této práce je vytvoření systému pro kontrolu samostatných prací
vytvořených v MS Access, konkrétně pro jejich validaci a rozpoznání
plagiarismu; v návaznosti poté systém adaptovat pro validátor
studentských prací. Následující podkapitoly se zabývají požadavky,
úvodní analýzou a návrhem řešení systému.

Požadavky na řešení
-------------------

Požadavky na řešení systému byly stanoveny s ohledem na využití
vyučujícími, s důrazem na intuitivní použití výsledného systému a
snadnou konfiguraci ve spojení s validátorem studentských prací.

Hlavní části požadovaného systému je aplikace umožňující *validaci
databázových souborů vytvořených v aplikaci Microsoft Access* -- neboli
kontrolu databází s ohledem na splnění zadání. Vzhledem k různorodosti
jednotlivých zadání musí aplikace umožnit pohodlnou konfiguraci této
kontroly. To bude spočívat v možnosti zahrnout do kontroly různá
pravidla testující obsah databází (dále též *validační pravidla*).
Výsledkem kontroly bude označení zadaného databázového souboru jako
*vyhovujícího* či *nevyhovujícího*; v druhém případě by měla aplikace
zároveň poskytnout informaci o pravidle, které „selhalo" (a zapříčinilo
tedy vyhodnocení databáze jako nevyhovující).

Pravidla musí umožňovat kontrolu existence tabulek, relací mezi
tabulkami různých typů, případně i dalších objektů uložených v databázi.
Dále musí být možné prověřit strukturu tabulek -- existenci sloupců
konkrétního názvu či datového typu, atp. Bude možné i ověřit počet řádků
v tabulce či tabulkách (zejména v podobě kontroly, zda všechny tabulky
obsahují alespoň určitý počet řádků).

Další částí aplikace bude detekce plagiarismu, respektive vyhledávání
podobností mezi databázovými soubory. Pro databáze může být obtížné
definovat, kdy se již jedná o plagiát -- například dvě databáze
obsahující stejně pojmenované tabulky mohou, ale nemusí být plagiátem. V
takovém případě může aplikace pouze upozorňovat na podobnost; pokud se
ale podaří na základě dalších informací jednoznačně určit, že se
o plagiát jedná, budou tak takové soubory označeny.

Aplikace bude uživatelům nabízet přívětivé grafické rozhraní, pomocí
kterého bude moci spravovat aktuální konfiguraci validace -- tedy
nastavení jednotlivých pravidel. Validaci musí být možné spustit
hromadně nad více databázemi, výsledek poté bude pro každou z nich určen
zvlášť.

Součástí systému bude dále aplikace přizpůsobená pro použití v rámci
validátoru studentských prací -- konkrétním řešením může být konzolová
aplikace, případně nový validační modul. S ohledem na jednoduchou
konfiguraci je nutné, aby hlavní část systému (tedy již popsaná aplikace
s grafickým rozhraním) umožňovala export konfigurace (pravidel validace)
pro použití v části adaptované pro validátor. Výstupem exportu může být
konfigurační soubor nebo textový řetězec.

Případy užití
-------------

Diagram na obrázku 4.1 znázorňuje případy užití celého systému z pohledu
uživatele i validátoru studentských prací.

Popis diagramu:

-   *Import validačních pravidel* -- uživatel může načíst dříve
    > vytvořenou sadu pravidel pro validaci databázových souborů ve
    > formátu ACCDB. Stejnou funkcionalitu využívá i část adaptovaná pro
    > validátor studentských prací, které jsou automaticky předávány
    > odevzdané studentské práce.

-   *Export validačních pravidel *-- uživatel může exportovat vytvořenou
    > konfiguraci validace, tedy sadu aktivních validačních pravidel.

-   *Nakonfigurování validačních pravidel *-- uživatel může vytvořit
    > sadu pravidel ověřující, zda jsou zadané databáze vyhovující.
    > Bližší popis konfigurace je uveden v kapitole 4.4.

-   *Validace jednoho či více databázových souborů ­*-- uživatel může
    > pomocí aplikace provést validaci databázových souborů. Výstupem
    > akce je informace, zda jednotlivé soubory vyhovují aktuálně
    > nakonfigurovaným pravidlům.

-   *Detekce plagiarismu mezi datovými soubory* -- uživatel může při
    > zadání více databázových souborů spustit vyhledání plagiátů.

-   *Validace jednoho databázového souboru *-- komunikace se systémem ze
    > strany validátoru studentských prací; do systému je předán
    > databázový soubor a konfigurace validačních pravidel. Výstupem je
    > opět informace, zda je soubor vyhovující pravidlům.

![](media/image6.emf){width="5.904440069991251in"
height="4.644444444444445in"}

Obrázek . -- diagram případů užití systému pro kontrolu samostatných
prací.

Metoda čtení databázových souborů
---------------------------------

Jak již bylo probráno v kapitole 2.4, existuje několik možností pro
čtení databázových souborů ve formátu ACCDB. Na základě požadavků na
řešení systému lze stanovit kritéria pro výběr metody čtení souborů v
podobě následujících vyžadovaných schopností:

-   Vypsání názvů tabulek v databázi.

-   Vypsání struktury tabulky -- zjištění názvů a datových typů sloupců,
    > informace o primárních klíčích.

-   Zjištění počtu řádků v tabulce, případně získání všech dat z
    > tabulky.

-   Nalezení relací mezi tabulkami v podobě, aby bylo možné určit typ.

-   Vypsání dotazů uložených v databázi včetně druhu.

Dalšími omezujícími kritérii jsou funkční požadavky na výsledný systém:

-   Spustitelnost na osobních počítačích s OS Microsoft Windows.

-   Spustitelnost části adaptované pro validátor na validačním serveru,
    > tj. kompatibilita se službou napsanou v jazyce Java na serveru s
    > OS Linux.

Některé nalezené metody pro čtení souborů ve formátu ACCDB je nutné na
základě stanovených kritérií vyřadit -- konkrétně se jedná o ODBC a
Microsoft Office Interop. Nesplňují zejména poslední zmíněný bod, tedy
spustitelnost na serveru s OS Linux (na kterém není dostupný Microsoft
Access ani ODBC ovladače). U metod přístupu prostřednictvím API ODBC a
JDBC je navíc problematické pracovat s relacemi a dotazy uloženými v
databázích.

Ze zbývajících možností je ideální volbou knihovna *Jackcess* pro
platformu Java a bude tedy použita pro implementaci systému. Vývoj v
jazyce Java přinese výhodu v multiplatformnosti a bude tak možné vyvíjet
obě části (hlavní aplikaci a část adaptovanou pro validátor) nad
společnými základy. Oproti ostatním metodám navíc knihovna Jackcess
poskytuje nejucelenější přístup k databázím, podporu i nejnovějších
verzí díky aktivnímu vývoji a podrobnou programovou i uživatelskou
dokumentaci.

Validace databázových souborů
-----------------------------

Validace databází bude spočívat ve vyhodnocení sady nakonfigurovaných
pravidel kontrolujících obsah a strukturu. Pokud budou všechna pravidla
splněna, označí se databáze jako *vyhovující*, v opačném případě jako
*nevyhovující*.

Na základě požadavků na řešení lze nyní definovat základní validační
pravidla potřebná pro kontrolu prací se zaměřením na splnění zadání a
možný princip jejich funkčnosti (zohledňující možnosti knihovny
Jackcess):

-   *Kontrola počtu tabulek v databázi *-- získání seznamu všech tabulek
    > v databázi (například jejich názvů) a ověření počtu vůči
    > požadovanému.

-   *Kontrola existence tabulky dle názvu *-- získání názvů všech
    > tabulek v databázi a ověření, že je mezi nimi hledaná tabulka.

-   *Kontrola počtu řádků v každé tabulce *-- získání seznamu všech
    > tabulek v databázi a pro každou z nich ověření počtu řádků vůči
    > požadovanému.

-   *Kontrola počtu sloupců v každé tabulce *-- získání seznamu všech
    > tabulek v databázi, pro každou z nich získání seznamu sloupců;
    > následně ověření počtu vůči požadovanému.

-   *Kontrola existence sloupce dle zadaných kritérií v každé tabulce
    > (kritériem může být název, datový typ nebo příslušnost k
    > primárnímu klíči) *-- získání seznamu všech tabulek v databázi,
    > pro každou z nich získání seznamu sloupců; následně ověření
    > existence sloupce dle zadaných kritérií.

-   *Kontrola počtu sloupců dle zadaných kritérií v každé
    > tabulce *-- získání seznamu všech tabulek v databázi, pro každou z
    > nich získání seznamu sloupců; následně ověření počtu sloupců
    > vyhovujících zadaným kritériím. Jedná se pouze o zobecnění
    > předchozího pravidla.

-   *Kontrola počtu tabulek obsahujících sloupec dle zadaných
    > kritérií *-- získání seznamu všech tabulek v databázi, pro každou
    > z nich získání seznamu sloupců; odebrání tabulek neobsahujích
    > sloupec dle zadaných kritérií; ověření počtu zbylých tabulek v
    > seznamu vůči požadovanému počtu.

-   *Kontrola počtu relací typu 1:1 *-- získání seznamu všech relací
    > mezi tabulkami v databázi, vyfiltrování relací označených jako
    > 1:1; ověření počtu vůči požadovanému.

-   *Kontrola počtu relací typu 1:N *-- získání všech relací mezi
    > tabulkami v databázi, odebrání relací označených jako 1:1; ověření
    > počtu vůči požadovanému. Tento postup zahrne do počtu i všechny
    > relace s rozkladovými tabulkami, které v důsledku tvořící relace
    > M:N.

-   *Kontrola počtu relací typu M:N* -- získání seznamu všech tabulek v
    > databázi, vyfiltrování rozkladových tabulek; ověření počtu zbylých
    > tabulek vůči požadovanému. Postup pro nalezení rozkladových
    > tabulek může spočívat v hledání tabulek, které mají cizí klíče
    > odkazující se na právě 2 další tabulky.

-   *Kontrola počtu dotazů dle zadaného druhu* -- získání seznamu všech
    > dotazů v databázi, vyfiltrování dle druhu a ověření počtu dle
    > požadovaného.

Kontroly na existenci či počty objektů v databázi lze zobecnit na
hledání počtu pomocí porovnávacích operátorů (tedy „rovná se", „větší
než" a „menší než"). Uživatel by měl možnost nakonfigurovat pravidla
například v následující podobě:

-   Databáze obsahuje právě jednu tabulku s názvem „články".

-   Databáze obsahuje méně než dvě tabulky obsahující méně než 5 řádků.

-   Databáze obsahuje nula relací typu 1:1.

-   Databáze obsahuje více než jednu relaci typu 1:N.

Vyhodnocení plagiarismu
-----------------------

Za plagiátorství lze označit úmyslné kopírování nebo celkové
napodobování prací jiných autorů a vydávání za vlastní. To platí i v
případě samostatných prací vytvořených v aplikaci Microsoft Access,
vzhledem k automatizaci kontroly je však nutné najít spolehlivý
a důvěryhodný postup pro označování prací jako plagiátů.

Samotné vyhodnocení plagiarismu mezi několika databázovými soubory může
spočívat v hledání různých společných „prvků podobnosti", přičemž každý
takovýto prvek může přispívat určitou „váhou" k označení dvou souborů
jako plagiátů (konkrétní rozlišení originálu a plagiátu je ponecháno na
posouzení uživatele). Příkladem takových prvků jsou:

-   Názvy a struktura tabulek (názvy sloupců, datové typy, ...).

-   Názvy a typy dotazů uložených v databázi.

-   Názvy dalších objektů (formulářů a sestav).

-   Metadata databázového souboru:

    -   Údaje o autorovi databáze (jméno a název organizace).

    -   Datum a čas vytvoření/poslední úpravy jednotlivých objektů.

    -   Datum a čas vytvoření/poslední úpravy databázového souboru.

    -   Nastavení uživatelského rozhraní.

    -   Grafické rozvržení relací mezi tabulkami (viz obrázek 2.4).

Z těchto prvků lze pro jednoznačné určení plagiátu využít zejména
grafické rozvržení relací mezi tabulkami, které je typicky pro každou
databázi unikátní.

Knihovna Jackcess podporuje získání některých metadat pomocí vlastních
funkcí v podobě hashovacích tabulek (jedná se právě o různé údaje typu
autor a název databáze, nastavení uživatelského rozhraní, informace o
formátu), další údaje je nutné získat ze skrytých systémových tabulek,
ke kterým knihovna umožňuje přistupovat stejně jako k uživatelským
tabulkám. Již zmíněné grafické rozvržení relací je uloženo v systémové
tabulce MSysObjects (záznam s typem -32758, sloupec LvExtra) v binární
podobě, díky reverznímu inženýrství je znám i formát těchto dat[^9].

Grafické rozhraní
-----------------

Grafické rozhraní hlavní aplikace bude rozděleno do několika částí,
přičemž všechny mohou být součástí jednoho okna:

-   Seznam dostupných validačních pravidel.

-   Seznam právě aktivních konfigurovatelných pravidel.

-   Konfigurační panel právě vybraného pravidla.

-   Seznam databázových souborů ke kontrole.

-   Nástrojová lišta s tlačítky pro ovládání programu.

Databázové soubory ke kontrole mohou být zobrazeny ve stromové
struktuře, přičemž v první úrovni stromu budou zobrazeny názvy souborů a
ve druhé informace o výsledcích kontroly. Jednotlivé položky stromu je
rovněž vhodné rozlišit pomocí různých ikon pro vyhovující a nevyhovující
databáze a dále takové, které byly vyhodnoceny jako plagiáty.

Adaptace pro validátor studentských prací
-----------------------------------------

Patrně nejjednodušší způsob adaptace vytvářeného systému pro validátor
studentských prací spočívá ve využití již existující akce validátoru pro
spuštění Java programu zabaleného do souboru formátu JAR. K tomu je
zapotřebí vytvořit aplikaci s konzolovým rozhraním, která bude nahrána
na validační server do složky příslušné validační domény. Aplikace se
bude ovládat pouze pomocí parametrů -- jedním z nich bude název
odevzdaného souboru (tj. studentské práce nahrané na portál), druhým pak
název souboru s validačními pravidly pro kontrolu správnosti zadání.
Výstupem aplikace bude textová informace o výsledku validace, v případě
neúspěchu dále vypíše podrobnosti o příčinách (neboli popis validačního
pravidla, které selhalo) na standardní chybový výstup.

Návrh struktury systému
-----------------------

Systém pro kontrolu samostatných prací bude napsán v programovacím
jazyce Java -- důvodem je volba knihovny Jackcess pro čtení souborů ve
formátu ACCDB (která je vytvořena právě na platformě Java), výhodou pak
výsledná multiplatformnost a snadné začlenění do validátoru studentských
prací (který rovněž využívá platformu Java). Jazyk podporuje rozdělení
kódu do oddělených jmenných prostorů nazývaných *balíky*, případně
vytvoření oddělených projektů. Lze tak dosáhnout rozdělení kódu na volně
spojené zapouzdřené části či komponenty.

Navrhovaný systém lze rozdělit do několika vzájemně propojených částí:

-   *Práce s daty obsaženými v databázi *-- zprostředkovává rozhraní pro
    > získávání údajů o tabulkách, relacích, atd., prostřednictvím
    > knihovny Jackcess.

-   *Validace databáze* -- obsahuje seznam dostupných validačních
    > pravidel a poskytuje rozhraní k nástroji na validaci databázových
    > souborů.

-   *Kontrola plagiarismu *-- poskytuje rozhraní k nástroji na
    > vyhodnocení plagiarismu mezi více databázovými soubory.

-   *Grafické rozhraní *-- slouží pro konfiguraci validačních pravidel,
    > jejich export, spouštění validace a kontroly plagiarismu nad
    > zadanými databázovými soubory.

-   *Konzolové rozhraní *-- umožňuje spustit validaci jedné databáze
    > pomocí validačních pravidel uložených v souboru.

Výsledný systém bude rozdělen do projektů tak, aby aplikace používaná na
validačním serveru obsahovala minimum dalších závislostí. Možným
způsobem je rozdělení na projekt obsahující tzv. aplikační logiku
systému (tedy vše potřebné pro kontrolu prací) spolu s konzolovým
rozhraním a projekt obsahující pouze grafické rozhraní určené pro
obsluhu uživatelem.

Implementace systému pro automatickou kontrolu prací
====================================================

Na základě předchozí analýzy jsou známy požadavky na vyvíjený systém, je
navržena základní struktura i funkčnost výsledných aplikací. Tato
kapitola se zaměřuje na samotnou implementaci. Popisuje, jak je výsledný
systém rozdělený do částí a komponent a zaměřuje se i na algoritmy
použité pro zajištění požadované funkčnosti.

Použité technologie
-------------------

Výsledný systém je naprogramován v jazyce Java 8. Grafické rozhraní
hlavní aplikace je postaveno na platformě JavaFX 8, dále se využívá
knihovna ControlsFX[^10] obsahující různé předpřipravené prvky
grafického uživatelského rozhraní. Pro čtení databázových souborů
vytvořených v aplikaci Microsoft Access se používá open-source knihovna
Jackcess ve verzi 2.1.10. Pro testování jsou využity nástroje JUnit
5[^11] a Mockito 2[^12]. Celý projekt je spravován nástrojem Apache
Maven[^13] a vyvíjen byl ve vývojovém prostředí JetBrains IntelliJ
IDEA[^14].

Struktura implementovaného systému
----------------------------------

Systém je rozdělen do dvou projektů či *modulů* (v terminologie nástroje
Apache Maven) dle návrhu v kapitole 4.8. Rozvržení je rovněž vidět na
obrázku 5.1, který zachycuje moduly a vnořené balíky. Konkrétní názvy
balíků a tříd v tomto i následujících diagramech tříd jsou uvedeny bez
nadřazeného balíku cz.zcu.kiv.accessvalidator.

![](media/image7.png){width="5.904861111111111in"
height="4.149305555555555in"}

Obrázek . -- diagram modulů a balíku implementované systému. \<TODO:
obrázek\>

### Modul validator

První modul nazvaný *validator* obsahuje tzv. aplikační logiku -- neboli
vše potřebné pro kontrolu samostatných prací. Dále je do tohoto modulu
začleněno konzolové rozhraní aplikace (viz kapitola 5.7). Modul je
členěn do 28 tříd rozdělených do 5 balíků, dále následuje popis
nejdůležitějších z nich. Rozvržení je vidět na obrázku 5.2.

![](media/image7.png){width="5.904861111111111in"
height="4.149305555555555in"}

Obrázek . -- diagram tříd v modulu validator. \<TODO: obrázek\>

Balík validator

Hlavní balík validator obsahuje dvě třídy reprezentující klíčové
nástroje pro kontrolu samostatných prací vytvořených v aplikaci
Microsoft Access:

-   Třída AccdbValidator -- slouží pro validaci databázových souborů
    neboli kontrolu se zaměřením na splnění zadání. Podrobnosti
    implementace jsou uvedeny v kapitole 5.4.

-   Třída AccdbSimilarityChecker -- slouží pro kontrolu samostatných
    prací se zaměřením na plagiarismus. Podrobnosti implementace jsou
    uvedeny v kapitole 5.5.

Balík dále obsahuje třídu s konzolovým rozhraním aplikace adaptovaným
pro validátor portálu ZČU (podrobnosti viz kapitola 5.7) a třídu
poskytující seznam se všemi validačními pravidly.

Balík database

Třídy obsažené v balíku database poskytují funkce pro práci
s databázovými soubory. Pro svoji funkčnost využívají knihovnu Jackcess
a oddělují tak použití této knihovny od zbytku systému.

Balík rules

Součástí balíku rules je deset tříd reprezentujících různá validační
pravidla. Každé pravidlo má definovaný obecný název (např. „Existence
tabulky dle názvu"), metodu vracející popis aktuální konfigurace
(„Existence tabulky s názvem ‚studenti'") a v neposlední řadě kolekci
různých vlastností, prostřednictvím nichž lze pravidlo nakonfigurovat
(pro zmíněné pravidlo půjde o vlastnost „Název tabulky"). Podrobnosti o
jednotlivých implementovaných pravidlech jsou uvedeny v kapitole 5.4.1.

Vnořený balík properties obsahuje právě třídy používané pro realizaci
vlastností validačních pravidel. Připraveny jsou dva typy vlastností --
jeden umožňuje uživatelům zadání libovolné hodnoty, druhý pak pouze
výběr jedné možnosti z několika předem definovaných. Součástí balíku je
několik výčtových tříd obsahujících např. seznam datových typů sloupců
používaných v databázích aplikace Microsoft Access. Tyto třídy se
používají právě ve zmíněném druhém typu vlastností.

Druhý vnořený balík serialization obsahuje jedinou třídu, která, jak již
název balíku napovídá, slouží k serializaci a deserializaci
nakonfigurovaných pravidel do souborů. Pro realizaci byl použit formát
XML.

### Modul configurator

Modul s názvem *configurator* obsahuje aplikaci s grafickým uživatelským
rozhraním, pomocí kterého mohou uživatelé spouštět a konfigurovat
kontrolu samostatných prací a prohlížet výsledky těchto kontrol. Dále
umožňuje exportovat (ukládat) a opět načítat konfiguraci validačních
pravidel ze souborů. Podrobnosti o implementaci rozhraní jsou uvedeny
v kapitole 5.6. Na obrázku 5.3 je opět znázorněno rozvržení tříd v
rámci modulu.

![](media/image7.png){width="5.904861111111111in"
height="4.149305555555555in"}

Obrázek . -- diagram tříd v modulu configurator. \<TODO: obrázek\>

Balík configurator

V balíku configurator je umístěna třída s hlavní metodou programu, která
spouští zmíněné grafické uživatelské rozhraní. Cílem této metody je
pouze inicializovat JavaFX aplikaci, jejíž obsluhu poté zajišťuje druhá
třída tohoto balíku, tzv. *controller*.

Balík common

Součástí balíku common jsou tři pomocné třídy. První z nich slouží
k otevírání souborů v asociovaném programu, respektive nadřazené složky
daného souboru v průzkumníku souborů. Druhá třída obsahuje metodu pro
zobrazení chybového dialogového okna. Poslední třída obaluje funkčnost
třídy FileChooser, která slouží pro výběr souborů pomocí dialogového
okna, a přidává schopnost pamatovat si poslední použitý adresář i po
restartu aplikace.

Balík components

Balík components je rozdělen na čtyři další balíky, z nichž každý
představuje jednu část uživatelského rozhraní zaměřenou na specifickou
oblast výsledné funkčnosti. Jedná se o panel se seznamem dostupných
validačních pravidel, panel s právě aktivní sadou validačních pravidel,
panel sloužící ke konfiguraci vybraného pravidla a panel s databázovými
soubory určenými k validaci. Každá část má svůj vlastní controller,
který zajišťuje funkčnost a definuje veřejné akce pro ovládání daného
panelu. Ty se využívají jako reakce na stisknutí tlačítka v grafickém
rozhraní, případně se přímo volají z jiných panelů.

Čtení databázových souborů
--------------------------

Čtení databázových souborů vytvořených v aplikaci Microsoft Access je
zajištěno knihovnou Jackcess. Pro oddělení závislosti na této knihovně
(a usnadnění případné výměny za jinou metodu čtení) bylo její použití
„zabaleno" do čtyř tříd.

Knihovna poskytuje plně objektové rozhraní pro práci s databázemi.
Instance třídy Database představují jednotlivé otevřené databázové
soubory, veškeré operace nad nimi, jako například získání seznamu
tabulek či relací, pak probíhají pomocí metod.

Třída reprezentující databázový soubor

Každý načtený databázový soubor je v rámci systému reprezentován
instancí třídy Accdb. Ta slouží zejména jako tzv. *továrna* pro
následující tři třídy, které zprostředkovávají přístup k objektům
uloženým v databázi. Dále třída obsahuje metodu pro porovnání s druhou
databází, která se využívá pro vyhodnocení podobnosti a detekci
plagiarismu mezi databázemi.

Repozitář tabulek uložených v databázi

Repozitáře v rámci programů obecně slouží pro oddělení přístupu k datům
od vlastní logiky aplikace. Obvykle poskytují rozhraní pro získávání dat
včetně možnosti vyhledávání konkrétním záznamů (resp. filtrování všech
dostupných) a rozhraní pro ukládání nových či aktualizovaných záznamů.

Implementovaná třída AccdbTableRepository slouží jako repozitář
zprostředkovávající uživatelské tabulky uložené v databázi. Při prvotní
inicializaci repozitáře se z databáze získá seznam názvů všech tabulek,
který je následně možné zavoláním příslušných metod filtrovat. Výsledný
seznam lze získat metodou getTables. Implementovány jsou například
následující možnosti filtrování:

-   *Dle názvu tabulky.* Ponechána je pouze tabulka, jejíž název se
    > přesně shoduje s parametrem filtru. V případě, že hledaná tabulka
    > neexistuje, zůstane seznam nalezených tabulek prázdný.

-   *Dle počtu sloupců vyhovujících parametrům.* Parametry jsou
    > porovnávací operátor[^15], požadovaný počet sloupců, název
    > sloupce, datový typ a údaj, zda sloupec má či nemá být součástí
    > primárního klíče. V případě, že je parametr názvu prázdný, pak se
    > pro filtrování neuplatňuje; obdobně lze vynechat i další dva
    > parametry.\
    > Interně tento filtr rovněž funguje jako malý repozitář. Pro každou
    > tabulku v seznamu jsou nejprve nalezeny údaje o všech sloupcích,
    > následně se provede filtrování dle názvu, poté dle datového typu a
    > nakonec dle příslušnosti k primárnímu klíči. Pokud zbylý počet
    > sloupců odpovídá požadovanému, je tabulka v seznamu ponechána.
    > V opačném případně dojde k odstranění.

-   *Nalezení rozkladových tabulek pro relace M:N.* Princip činnosti je
    > podobný předchozímu filtru, pouze místo sloupců se pracuje
    > s relacemi tabulky. Vyfiltrování rozkladových tabulek spočívá
    > v ověření, že má daná tabulka cizí klíče tvořící vazbu 1:N
    > s dalšími dvěma tabulkami (princip byl již zmíněn v analýze, viz
    > kapitola 4.4).

Repozitář relací mezi tabulkami

Druhý repozitář, implementovaný třídou AccdbRelationRepository, slouží
k získávání informací o relacích mezi tabulkami uloženými v databázi.
Funguje na stejném principu jako repozitář tabulek, neboli získání všech
relací při inicializaci a poté možnost jejich filtrování. K dispozici
jsou následující filtry:

-   *Vyhledání relací typu 1:1*. Ponechány jsou pouze relace označené
    > knihovnou Jackcess jako typ 1:1. Knihovna interně využívá příznak
    > uložený v objektu relace.

-   *Vyhledání relací typu 1:N*. Funguje na stejném principu jako
    > předchozí filtr, pouze v seznamu zachovává takové relace, které
    > zmíněný příznak nemají. Do výsledného seznamu jsou zahrnuty i
    > relace s rozkladovými tabulkami, které ve výsledku tvoří relace
    > typu M:N, neboť *de facto* jsou relacemi typu 1:N.

Filtrování relací typu M:N není do tohoto repozitáře zahrnuto, neboť se
jedná o vazbu, která vzniká až v důsledku aplikace jiných vazeb
(konkrétně dvou relací 1:N). Pokud chceme takové relace nalézt, lze
použít repozitář tabulek a filtr pro nalezení rozkladových tabulek,
které právě relaci M:N zajišťují.

Repozitář dotazů uložených v databázi

Poslední implementovaný repozitář ve třídě AccdbQueryRepository slouží
pro vyhledávání dotazů uložených v databázi. Opět je postaven na stejném
principu jako předchozí dva repozitáře, ale je z nich nejjednodušší,
neboť poskytuje pouze jednu „filtrační metodu":

-   *Vyhledání dotazu dle typu*. Ve výsledném seznamu jsou zachovány
    > pouze ty dotazy, které přesně odpovídají typu zadanému parametrem.
    > Informace o typu je opět zprostředkována knihovnou Jackcess.

Validace databáze
-----------------

Kontrola databází s ohledem na splnění zadání, neboli *validace
databází*, je realizována prostřednictvím různých validačních pravidel.
Každé pravidlo obsahuje metodu check, která přejímá referenci na objekt
reprezentující databází a vyhodnocuje, zda je pro danou databázi
splněno. Konkrétní implementace této metody se tedy samozřejmě u každého
pravidla liší, navíc je výsledek ovlivněn i aktuální konfigurací
pravidla. Ta je dána nastavením *vlastností*, které jsou součástí
pravidla.

Jedním z implementovaných pravidel je *skupinové pravidlo*, do kterého
lze „vnořit" další pravidla. Pro vyhodnocení pravidla se poté ověřují i
všechna vnořená. Pravidla tak ve výsledku tvoří stromovou strukturu.
Validace databáze ve výsledku tedy spočívá ve spuštění kontroly v
kořenovém pravidle.

Nástroj pro validaci poskytuje seznam pravidel, které nebyly během
validace splněné. Validace databáze ovšem obvykle běží pouze do prvního
pravidla, které „selže", výstupem tedy bývá pouze toto jedno pravidlo.

### Implementovaná validační pravidla

Implementováno bylo celkem deset různých validačních pravidel, přičemž
všechny jsou potomky abstraktní třídy Rule, která definuje jejich
základní podobu. Většina z nich byla navrhnuta v rámci analýzy řešení
(viz kapitola 4.4), omezíme se proto jen na jejich výčet a možnosti
konfigurace:

-   *Skupinové pravidlo* umožňující zanoření dalších pravidel.
    > Vyhodnocení pravidla je závislé na režimu, který lze zvolit: AND
    > -- je nutné splnit všechna vnořená pravidla. OR -- je nutné splnit
    > alespoň jedno vnořené pravidlo.

-   *Kontrola existence tabulky dle názvu.* Nakonfigurovat lze název
    > tabulky.

-   *Kontrola počtu sloupců v každé tabulce.* Nakonfigurovat lze
    > porovnávací operátor, požadovaný počet sloupců, filtr dle názvu
    > sloupce, filtr dle datového typu a filtr dle příslušnosti sloupce
    > k primárnímu klíči.

-   *Kontrola počtu řádků v každé tabulce*. Nakonfigurovat lze
    > porovnávací operátor a požadovaný počet řádků.

-   *Kontrola počtu tabulek, které obsahují sloupec dle zadaných
    > kritérií*. Nakonfigurovat lze porovnávací operátor, požadovaný
    > počet tabulek, datový typ sloupce, název sloupce a příslušnost
    > k primárnímu klíči.

-   *Komplexní kontrola počtu tabulek.* Nakonfigurovat lze porovnávací
    > operátor, požadovaný počet tabulek, filtr dle názvu sloupce, filtr
    > dle počtu sloupců (opět lze nastavit porovnávací operátor a
    > požadovaný počet) a filtr dle existence sloupce (dle názvu,
    > datového typu a příslušnosti k primárnímu klíči).\
    > Pravidlo umožňuje ověřit, že v databázi existuje požadovaný počet
    > tabulek, které mají určitý počet sloupců (libovolných vlastností)
    > a které obsahují sloupec dle požadovaných vlastností.

-   *Kontrola počtu dotazů dle zadaného druhu*. Nakonfigurovat lze
    > porovnávací operátor, požadovaný počet dotazů a druh dotazů. Lze
    > vybrat jeden z devíti druhů.

-   *Kontrola počtu relací typu 1:1*. Nakonfigurovat lze porovnávací
    > operátor a požadovaný počet relací.

-   *Kontrola počtu relací typu 1:N*. Detto.

-   *Kontrola počtu relací typu M:N*. Detto.

Validační pravidla využívají implementovaných repozitářů, uplatňuje se
zde tedy stejné chování jako u filtračních metod -- tj. v případě
nevyplnění vlastností *název*, *datový typ*, *typ dotazu*, apod. nebudou
tyto vlastnosti pro filtrování použity.

### Ukládání nakonfigurovaných pravidel do souborů

Systém uživatelům nabízí možnost ukládání a načítání nakonfigurovaných
validačních pravidel z/do souborů. Tím je uživatelům umožněno zálohovat
si jednotlivé konfigurace validace a používat je opakovaně. S ohledem na
využití dané konfigurace aplikací adaptovanou pro validátor portálu ZČU
mluvíme též o *exportování pravidel*.

Ukládání pravidel je implementováno jako tzv. *serializace* pravidel do
souborů. Serializace obecně představuje proces převodu objektů do
podoby, kterou lze uložit do paměti, souboru, nebo např. poslat po síti
-- striktně vzato lze říci, že se jedná o převod na posloupnost bytů.
Opačným procesem je pak deserializace, jejímž cílem je zpětně
rekonstruovat původní objekt (resp. vytvořit jeho identickou kopii).

V rámci realizovaného systému se pro serializaci využívá formát XML.
Každé pravidlo je ve výstupním souboru zastoupenou jedním elementem,
jehož atributy představují vlastnosti daného pravidla. Třída zajišťující
serializaci přejímá pouze jedno pravidlo, pro uložení více pravidel se
předpokládá využití skupinového pravidla. Vnořená pravidla se
zpracovávají rekurzivně a výsledný dokument tedy tvoří stromovou
strukturu ve stejné podobě, jako jsou validační pravidla.

Uložení konfigurace validátoru spočívá v serializaci kořenového
skupinového pravidla. Příklad konfigurace serializované do formátu XML
je uveden v ukázce 5.1.

**1** \<?xml version=\"1.0\" ?\>

**2** \<GroupRule mode=\"AND\"\>

**3** \<AllTablesHaveRowsRule count\_op=\"GTE\" count=\"5\" /\>

**4** \<AllTablesHaveColumnsRule count\_op=\"EQ\" count=\"1\"
column\_type=\"AUTO\_NUMBER\"

**5** column\_name=\"\" column\_primary=\"\_ANY\" /\>

**6** \<CountRelations11Rule count\_op=\"EQ\" count=\"0\" /\>

**7** \<ExistsTableByNameRule name=\"student\" /\>

**8** \</GroupRule\>

Ukázka . -- pět pravidla uložených do formátu XML.

Hledání podobností a detekce plagiarismu
----------------------------------------

Další důležitou částí systému je detekce plagiarismu mezi databázovými
soubory vytvořenými v aplikaci Microsoft Access. Způsob detekce
implementovaný v systému je založen na hledání stejných či podobných
částí mezi napříč všemi zadanými databázemi.

Třída zodpovědná za detekci plagiarismu přejímá seznam souborů určených
ke kontrole. Samotná kontrola probíhá v několika krocích:

1.  Vzájemné porovnání všech databází -- zavolání implementované metody,
    > která vrací množinu „prvků podobnosti".

2.  Zařazení souborů do skupin dle nalezených podobností. V každé
    > skupině tak jsou alespoň dva soubory mající právě jednu společnou
    > část, postupně do ní ale mohou být zařazovány i další soubory.

3.  Získání výsledků ve dvou různých podobách:

    -   Mapa, kde klíčem je databázový soubor (dále „hlavní") a hodnotou
        je třída spravující seznam jemu podobných souborů. Pro každý
        podobný soubor je dále k dispozici seznam podobností s hlavním
        souborem.

    -   Mapa, kde klíčem je databázový soubor a hodnotou kolekce „prvků
        podobnosti". Každý tento prvek pak v sobě nese seznam dalších
        souborů mající tento prvek.

Mezi databázemi se vyhledávají podobnosti pouze v rámci následujících
metadat:

-   Datum a čas poslední vytvoření systémové tabulky MSysDb. Odpovídá
    datu vytvoření samotného databázového souboru.

-   Datum a čas poslední úpravy systémové tabulky MSysDb. Odpovídá datu
    posledního uložení databáze.

-   Datum a čas poslední vytvoření systémové tabulky Admin. Odpovídá
    datu vytvoření relací mezi tabulkami v databázi.

-   Datum a čas poslední úpravy systémové tabulky Admin. Odpovídá datu
    poslední úpravy relací mezi tabulkami v databázi.

-   Údaje o autorovi databáze (jméno a název organizace). Vynechávají se
    údaje, které jsou v systémech často jako výchozí -- databáze
    s názvem „Database", jména autorů obsahující slovo „Windows" a jména
    organizací obsahující „Univer" (přidáno na základě testování).

-   Grafické rozvržení relací mezi tabulkami. Způsob získání byl popsán
    v závěru kapitoly 4.5.

Na základě testování bylo potvrzeno, že nalezení stejného grafického
rozvržení relací mezi tabulkami je dostatečně průkazný prvek pro
označení databáze jako plagiátu. Všechna ostatní pravidla způsobí pouze
upozornění uživatele na podobnost mezi databázemi, má tedy možnost
dodatečně zkontrolovat i další „podezřelé" databáze. Interně je
rozlišení mezi plagiátem a podobným souborem docíleno ohodnocením
nalezených prvků podobnosti, přičemž již zmíněné grafické rozvržení
relací je ohodnoceno číslem 100, zbylé číslem 1. Aplikace s grafickým
rozhraním pak označuje za plagiáty takové soubory, jejichž suma
podobnosti je větší nebo rovna hodnotě 100.

V případě testování většího množství databází se může objevit některý
prvek podobnosti, který způsobuje označení databáze jako podobné nebo
plagiátu, ale může být očekávaný a není tedy žádoucí jej vyhodnocovat.
Typicky může jít o databáze vytvořené v rámci jedné organizace a tedy
mající shodné údaje o autorovi. Dalším příkladem mohou být samostatné
práce v rámci výuky, u nichž je součástí zadání předpřipravený
databázový soubor. V tomto případě systém poskytuje možnost do nástroje
pro kontrolu předat seznam prvků podobnosti, které se mají ignorovat.
Při dalších kontrolách pak již nejsou součástí výsledků.

Grafické rozhraní
-----------------

Adaptace pro validátor portálu ZČU
----------------------------------

### Konfigurace validátoru

Testování vytvořeného systému
=============================

Validace a validační pravidla
-----------------------------

Detekce plagiarismu
-------------------

Grafické rozhraní
-----------------

Konzolové rozhraní pro validátor portálu ZČU
--------------------------------------------

Závěr
=====

Reference {#reference .ListParagraph}
=========

[]{#_toc_1 .anchor}

[]{#_toc_2
.anchor}<https://support.office.com/en-us/article/Introduction-to-the-Access-2007-file-format-8cf93630-0b68-4a40-a13c-7528b9f074b6>

[]{#_toc_3
.anchor}<https://support.office.com/en-us/article/data-types-for-access-desktop-databases-df2b83ba-cef6-436d-b679-3418f622e482>

[]{#_toc_4 .anchor}

[]{#_toc_5
.anchor}<https://github.com/brianb/mdbtools/blob/master/HACKING>

[]{#_toc_6
.anchor}<https://www.loc.gov/preservation/digital/formats/fdd/fdd000462.shtml>

[]{#_toc_7
.anchor}<https://support.office.com/en-us/article/which-access-file-format-should-i-use-012d9ab3-d14c-479e-b617-be66f9070b41>

[]{#_toc_8 .anchor}

[]{#_toc_9 .anchor}

[]{#_toc_10
.anchor}<https://msdn.microsoft.com/en-us/library/15s06t57.aspx>

[]{#_toc_11 .anchor}

[]{#_toc_12 .anchor}<https://github.com/brianb/mdbtools/>

[]{#_toc_13 .anchor}<https://github.com/brianb/mdbtools/issues/77>

[]{#_toc_14
.anchor}<https://sourceforge.net/p/mdbtools/discussion/6688/thread/a543445a/>

[]{#_toc_15 .anchor}<https://github.com/ome/ome-mdbtools>

[]{#_toc_16
.anchor}<https://github.com/ome/ome-mdbtools/blob/master/src/main/java/mdbtools/tests/ColumnTest.java>

[]{#_toc_17 .anchor}<http://jackcess.sourceforge.net/>

[]{#_toc_18 .anchor}<http://jackcess.sourceforge.net/faq.html>

[]{#_toc_19 .anchor}<http://jackcess.sourceforge.net/cookbook.html>

[]{#_toc_20 .anchor}<http://jackcessencrypt.sourceforge.net/>

[]{#_toc_21 .anchor}

[]{#_toc_22
.anchor}<https://docs.oracle.com/javase/7/docs/technotes/guides/jdbc/bridge.html>

[]{#_toc_23 .anchor}<http://ucanaccess.sourceforge.net/site.html>

[]{#_toc_24 .anchor}

[]{#_toc_25
.anchor}<https://is-stag.zcu.cz/napoveda/stag-v-portalu/spnew-studium_odevzdavani-praci.html>

[]{#_toc_26
.anchor}<https://validator-test.zcu.cz/vs/auth/doc/doc/validacni-server-uzivatelsky-popis-2.pdf>

[]{#_toc_27
.anchor}<https://validator-test.zcu.cz/vs/auth/doc/index.html>

[]{#_toc_28 .anchor}

[]{#_toc_29 .anchor}<https://validator-test.zcu.cz/>

[]{#_toc_30
.anchor}<https://students.kiv.zcu.cz:3443/projects/validator/wiki>

\[1\] ADAMSKI, Joseph J.; FINNEGAN, Kathy T. ; SCOLLARD, Sharon. *New
perspectives on Microsoft Access 2013: comprehensive.* Stamford, CT:
Cengage Learning, 2014. ISBN 978-1-285-09920-0. \[2\] Introduction to
the Access 2007 file format. *Microsoft Office help and training -
Office Support.* \[Online\] \[Citace: 20. 3. 2018\]. Dostupné z: \[3\]
Data types for Access desktop databases. *Microsoft Office help and
training - Office Support.* \[Online\] \[Citace: 22. 3. 2018\]. Dostupné
z: \[4\] CONNOLLY, Thomas; BEGG, Carolyn. *A Practical Approach to
Design, Implementation, and Management.* 6. Harlow: Pearson Education
Limited, 2014. ISBN 978-1-292-06118-4. \[5\] BRUNS, Brian. HACKING. *MDB
Tools repository.* \[Online\] \[Citace: 20. 4. 2018\]. Dostupné z: \[6\]
Microsoft Access ACCDB File Format Family. *Digital Preservation at the
Library of Congress.* \[Online\] \[Citace: 20. 3. 2018\]. Dostupné z:
\[7\] Which Access file format should I use? *Microsoft Office help and
training - Office Support.* \[Online\] \[Citace: 20. 3. 2018\]. Dostupné
z: \[8\] KYLE, Geiger. *Inside ODBC.* Redmond, WA: Microsoft Press,
1995. ISBN 978-1556158155. \[9\] ROFF, Jason T. *ADO: ActiveX Data
Objects.* místo neznámé: O\'Reilly Media, 2001. ISBN 9781491935576.
\[10\] Office Primary Interop Assemblies. *Microsoft Developer Network.*
\[Online\] \[Citace: 02. 04. 2017\]. Dostupné z: \[11\] WHITECHAPEL,
Andrew. *Microsoft .NET Development for Microsoft Office.* Redmond, WA:
Microsoft Press, 2005. ISBN 0-7356-2132-2. \[12\] BRUNS, Brian. *MDB
Tools repository.* \[Online\] \[Citace: 20. 4. 2018\]. Dostupné z:
\[13\] ---. Access 2013 support. *MDB Tools repository.* \[Online\]
\[Citace: 20. 4. 2018\]. Dostupné z: \[14\] SMITH, Calvin R. mdbtools is
being ported to java. *MDB Tools Discussion.* \[Online\] 2. 5. 2004
\[Citace: 20. 4. 2018\]. Dostupné z: \[15\] Open Microscopy Environment.
*OME MDB Tools.* \[Online\] \[Citace: 20. 4. 2018\]. Dostupné z: \[16\]
---. ColumnTest source code (ukázka použití). *OME MDB Tools.*
\[Online\] \[Citace: 20. 4. 2018\]. Dostupné z: \[17\] *Jackcess.*
\[Online\] Health Market Science, 31. 3. 2018 \[Citace: 20. 4. 2018\].
Dostupné z: \[18\] Frequently Asked Questions. *Jackcess.* \[Online\]
Health Market Science, 31. 3. 2018 \[Citace: 20. 4. 2018\]. Dostupné z:
\[19\] Cookbook. *Jackcess.* \[Online\] Health Market Science,
31. 3. 2018 \[Citace: 20. 4. 2018\]. Dostupné z: \[20\] *Jackcess
Encrypt.* \[Online\] Health Market Science, 9. 10. 2017 \[Citace:
20. 4. 2018\]. Dostupné z: \[21\] MAYDENE FISHER, Jon Ellis, Jonathan
Bruce. *JDBC™ API Tutorial and Reference.* Boston, MA: Addison Wesley,
2003. ISBN 0-321-17384-8. \[22\] ORACLE. JDBC-ODBC Bridge. *Java SE
Documentation.* \[Online\] \[Citace: 20. 4. 2018\]. Dostupné z: \[23\]
AMADEI, Marco. *UCanAccess.* \[Online\] \[Citace: 20. 4. 2018\].
Dostupné z: \[24\] Centrum informatizace a výpočetní techniky.
*Referenční příručka portálového rozhraní IS/STAG.* Plzeň: Západočeská
univerzita, 2009. ISBN 978-80-7043-807-7. \[25\] ---. Aplikace pro
správu semestrálních prací, jejich odevzdávání a hodnocení. *IS/STAG -
Helpcentrum.* \[Online\] Západočeská univerzita \[Citace: 23. 4. 2018\].
Dostupné z: \[26\] HEROUT, Pavel. *Validační server pro studentské
projekty.* \[Online\] \[Interní dokument\] \[Citace: 20. 4. 2018\].
Dostupné z: \[27\] VALENTA, Lukáš; DUDOVÁ, Veronika. *Validační server -
manuál.* \[Online\] \[Citace: 20. 4. 2018\]. Dostupné z: \[28\] DUDOVÁ,
Veronika. *Webová konfigurace validačního serveru.* Plzeň, 2010.
Bakalářská práce. Západočeská univerzita. Fakulta aplikovaných věd.
Katedra informatiky a výpočetní techniky. Vedoucí práce Pavel HEROUT.
\[29\] *Testovací validační server pro studentské projekty.* \[Online\]
\[Citace: 20. 4. 2018\]. Dostupné z: \[30\] *Wiki - Validační server a
jeho moduly - Redmine KIV ZČU.* \[Online\] \[Citace: 20. 4. 2018\].
Dostupné z:

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

[^2]: Např. viz WWW:
    https://www.easysoft.com/products/data\_access/odbc-access-driver/

[^3]: Viz WWW:
    <https://www.easysoft.com/products/data_access/jdbc-access-gateway/>

[^4]: Viz WWW: <http://www.hxtt.com/access.html>

[^5]: Viz WWW:
    http://sesamesoftware.com/relational-junction/jdbc-database-drivers-↩
    products/relational-junction-mdb-jdbc-driver/

[^6]: Dostupný na adrese <https://portal.zcu.cz/>

[^7]: Viz WWW: <https://validator.zcu.cz/>

[^8]: Viz WWW: <https://validator-test.zcu.cz/>

[^9]: Viz kód nástroje z webu
    http://www.lebans.com/saverelationshipview.htm

[^10]: Viz web knihovny ControlsFX:
    <http://fxexperience.com/controlsfx/>

[^11]: Viz web knihovny JUnit 5: <https://junit.org/junit5/>

[^12]: Viz web knihovny Mockito: <http://site.mockito.org/>

[^13]: Viz web nástroje Apache Maven: <https://maven.apache.org/>

[^14]: Viz web vývojového prostředí IntelliJ IDEA:
    <https://www.jetbrains.com/idea/>

[^15]: Podporované operátory jsou „rovná se", „větší nebo rovno" a
    „menší nebo rovno".
