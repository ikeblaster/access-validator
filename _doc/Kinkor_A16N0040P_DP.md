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

**System for Automatic Checking of Student Works Created in MS Access**

text

Abstrakt

**Systém pro automatickou kontrolu samostatných prací vytvořených\
v MS Access**

text

Obsah

[1 Úvod 1](#úvod)

[2 Databázový software Microsoft Access
2](#databázový-software-microsoft-access)

[2.1 Základní informace 2](#základní-informace)

[2.2 Objekty uložené v databázi 2](#objekty-uložené-v-databázi)

[2.2.1 Tabulky 2](#tabulky)

[2.2.2 Dotazy 5](#dotazy)

[2.2.3 Formuláře 6](#formuláře)

[2.2.4 Sestavy 7](#sestavy)

[2.2.5 Skryté systémové tabulky 7](#skryté-systémové-tabulky)

[2.3 Metadata databázových souborů 7](#metadata-databázových-souborů)

[2.4 Možnosti čtení souborů ACCDB 8](#možnosti-čtení-souborů-accdb)

[2.4.1 Formát ACCDB 8](#formát-accdb)

[2.4.2 ODBC 9](#odbc)

[2.4.3 Microsoft Office Interop 9](#microsoft-office-interop)

[2.4.4 MDB Tools 10](#mdb-tools)

[2.4.5 MDB Tools Java 10](#mdb-tools-java)

[2.4.6 Jackcess 11](#jackcess)

[2.4.7 JDBC 11](#jdbc)

[3 Portál ZČU 13](#portál-zču)

[3.1 Základní informace 13](#základní-informace-1)

[3.2 Validátor studentských prací 13](#validátor-studentských-prací)

[3.3 Vytvoření nové validační domény
15](#vytvoření-nové-validační-domény)

[4 Analýza kontroly prací 16](#analýza-kontroly-prací)

[4.1 Požadavky na řešení 16](#požadavky-na-řešení)

[4.2 Případy užití 16](#případy-užití)

[4.3 Validace databáze 16](#validace-databáze)

[4.4 Vyhodnocení plagiarismu 16](#vyhodnocení-plagiarismu)

[5 Implementace systému pro automatickou kontrolu prací
17](#implementace-systému-pro-automatickou-kontrolu-prací)

[5.1 Použité technologie 17](#použité-technologie)

[5.2 Struktura aplikace 17](#struktura-aplikace)

[5.3 Validace databáze 17](#validace-databáze-1)

[5.4 Implementovaná validační pravidla
17](#implementovaná-validační-pravidla)

[5.5 Hledání podobností a detekce plagiarismu
17](#hledání-podobností-a-detekce-plagiarismu)

[5.6 Grafické rozhraní 17](#grafické-rozhraní)

[5.7 Adaptace pro validátor portálu ZČU
17](#adaptace-pro-validátor-portálu-zču)

[6 Testování vytvořeného systému 18](#testování-vytvořeného-systému)

[6.1 Validační pravidla 18](#validační-pravidla)

[6.2 Konzolová aplikace pro validátor portálu ZČU
18](#konzolová-aplikace-pro-validátor-portálu-zču)

[6.3 Grafické rozhraní 18](#grafické-rozhraní-1)

[7 Závěr 19](#závěr)

[Reference 20](#reference)

[Přílohy 22](#přílohy)

[A Uživatelská příručka 22](#a-uživatelská-příručka)

[Spuštění a kompilace nástroje 22](#spuštění-a-kompilace-nástroje)

[Obsluha nástroje 22](#obsluha-nástroje)

[B Obsah přiloženého média 22](#b-obsah-přiloženého-média)

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
být součástí databáze.

### Tabulky

Jedná se o stěžejní součást každé databáze. Tabulku lze definovat jako
strukturovanou kolekci dat. Skládá se ze sloupců a řádků (též záznamů) a
v rámci databáze má unikátní název[1](#_toc_1) \[ str. AC 4\].

Sloupce tabulky

Struktura tabulky je definována pomocí sloupců, které mají specifikovaný
název (unikátní v rámci tabulky) a datový typ. Microsoft Access[^1]
podporuje následující datové typy[1](#_toc_1)[3](#_toc_3) \[ str. AC 57,
\]:

-   *Automatické číslo* -- pro každý nový záznam se automaticky nastaví
    na následující hodnotu posloupnosti, nebo na náhodné číslo (dle
    nastavení). Typicky se využívá pro sloupec označený jako primární
    klíč (viz dále).

-   *Číslo* -- rozsah a typ (celočíselné/s desetinnou čárkou) lze zvolit
    ve vlastnostech sloupce.

-   *Krátký text* (dříve Text) -- text do délky 255 znaků.

-   *Dlouhý text* (dříve Memo) -- text do velikosti 1 GB.

-   *Datum a čas*.

-   *Měna*** **-- specializovaný případ číselného datového typu s fixní
    desetinnou čárkou (uchovává 4 desetinná místa).

-   *Ano/ne* -- uchovává hodnotu -1 (Ano) nebo 0 (Ne); v rámci Microsoft
    Access zobrazeno jako zaškrtávací pole (*checkbox*).

-   *Hypertextový odkaz*.

-   *Objekt OLE*** **-- umožňuje vložit speciální objekty, například
    obrázek, jiný dokument, či odkaz na soubor.

-   *Příloha* -- umožňuje vložit libovolný soubor jako součást záznamu.
    Jedná se o univerzálnější možnost k předchozímu.

-   *Počítané* -- automatické vložení hodnoty vypočítané na základě
    zadaného vzorce.

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

Primární klíč slouží pro odkázání na jeden konkrétní záznam v tabulce,
čehož se využívá při vytváření dotazů nebo tvoření relací mezi
tabulkami. Pro vytváření primárních klíčů se obvykle využívá datový typ
Automatické číslo, který každému záznamu přiřadí unikátní celé číslo.
Často bývá takový sloupec pojmenován „ID" (*Identification*)[1](#_toc_1)
\[ str. AC A3-A4\].

Relace mezi tabulkami a cizí klíče

V případě, že chceme propojit více tabulek mezi sebou, využijeme tzv.
relačních vazeb. Jedná se o situaci, kdy záznam v tabulce odkazuje („má
referenci") na jeden konkrétní záznam z druhé tabulky.

Rozlišují se tři druhy relačních vazeb[1](#_toc_1)[4](#_toc_4) \[ str.
AC A5-A8, str. 419\].

-   *Relace typu 1:1* -- jednomu záznamu v tabulce A odpovídá žádný, či
    právě jeden záznam v tabulce B. To lze zajistit přidáním tzv.
    *cizího klíče* do tabulky A -- sloupce, který bude obsahovat pouze
    hodnoty primárního klíče z tabulky B (příp. skupiny sloupců, pokud
    se jedná o složený primární klíč). Alternativně lze referencování
    použít primární klíče obou tabulek (viz obrázek 1) -- typicky je
    tento postup vhodný v situaci, kdy jen málo záznamů v tabulce A má i
    záznam v tabulce B[4](#_toc_4) \[ str. 420\].

![](media/image1.emf)

Obrázek 1 -- model relace 1:1

-   *Relace typu 1:N* -- k více záznamům v tabulce A lze přiřadit jeden
    záznam z tabulky B. Tato vazba je vždy realizována pomocí zmíněných
    cizích klíčů. Jedná se o nejčastěji využívanou vazbu[4](#_toc_4) \[
    str. 421\].

![](media/image2.emf)

Obrázek 2 -- model relace 1:N

-   *Relace typu M:N* -- k M záznamům v tabulce A lze přiřadit N záznamů
    z tabulky B. Relace se realizuje pomocí spojové tabulky (též
    mezitabulky) a dvojicí relací 1:N. Spojová tabulka obvykle obsahuje
    pouze sloupce cizích klíčů[4](#_toc_4) \[ str. 422\].

![](media/image3.emf)

Obrázek 3 -- model relace M:N

Relace mezi tabulkami mohou zajišťovat *referenčním integritu*. Cílem je
zabránit odkazování na neexistující záznam (a rovněž tedy vzniku
osiřelých záznamů, na které byly všechny reference zrušeny). Integritní
pravidlo může dále zajistit kaskádovou aktualizaci polí -- pokud se
změní hodnota primárního klíče, změní se automaticky hodnota u všech
záznamů, které na záznam odkazují. Dále může zajistit kaskádové
odstranění souvisejících záznamů -- v případě smazání záznamu budou
smazány i všechny další, které na právě tento záznam odkazovaly
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
    vybraných záznamů. Struktura je dána dotazem -- jednotlivé sloupce
    mohou pocházet z různých tabulek, či být spočítané „za běhu". Obecně
    lze považovat výběrový dotaz za analogii k databázovým
    pohledům[1](#_toc_1) \[ str. AC 124-128\].

-   *Vytvářecí (MAKE TABLE)* -- pracuje na stejném principu jako
    výběrový, výsledek dotazu však není ihned zobrazen uživateli, ale
    uložen do nové tabulky[1](#_toc_1) \[ str. AC 500, 501\].

-   *Přidávací (APPEND) *-- slouží pro vkládání nových záznamů do
    existujících tabulek[1](#_toc_1) \[ str. AC 500, 506\].

-   *Aktualizační (UPDATE)*** **-- umožňuje úpravu hodnot již
    existujících záznamů v tabulkách [1](#_toc_1)\[ str. AC 500, 512\].

-   *Křížový (CROSSTAB)*** **-- výsledkem dotazu je tzv. kontingenční
    tabulka zobrazující data v kompaktní podobě. Typicky se používá
    například pro sumarizaci hodnot, nalezení průměrů, maximálních
    hodnot, atp [1](#_toc_1)\[ str. AC 254, 256\].

### Formuláře

Formuláře poskytují přívětivé rozhraní pro vkládání či editaci záznamů v
tabulkách. Grafické rozhraní je plně konfigurovatelné a umožňuje tedy
jednotlivá pole záznamů různě seskupovat, přidat popisky, či některá
úplně skrýt. Formuláře jsou v databázi opět uloženy pod unikátním
názvem[1](#_toc_1) \[ str. AC 179\].

Access umožňují vytvořit formuláře různých druhů[1](#_toc_1) \[ str. AC
180-200\]:

-   Formuláře pro editaci jednotlivých záznamů (dále označované jako
    standardní).

-   Navigační formuláře, které poskytují možnost přepínání mezi různými
    formuláři a umožňují tak vytvořit komplexní rozhraní pro správu celé
    databáze.

-   Formuláře zobrazující více položek (záznamů) najednou.

-   Datové listy, které vypadají podobně jako zobrazení tabulky (tedy
    tabulka, kde každý řádek odpovídá jednomu záznamu), ale zachovávají
    možnost upravovat zobrazená pole.

-   Rozdělené formuláře, které jsou kombinaci standardních formulářů v
    jedné části a datového listu v druhé části obrazovky.

-   Modální dialogová okna, která mají stejné možnosti jako standardní
    formuláře, ale zobrazují se v samostatném okně a jsou

### Sestavy

Sestavy slouží pro vytváření výpisů dat z databáze v přívětivé podobě,
zobrazující typicky více záznamů na jedné straně, na rozdíl od formulářů
ale neumožňuje editaci dat. Často se využívá pro následné vytisknutí.
Při návrhu se definuje záhlaví a zápatí stránek a rozložení prvků pro
každý záznam („řádek" sestavy)[1](#_toc_1) \[ str. AC 208\].

### Skryté systémové tabulky

Databáze dále obsahují několik skrytých systémových tabulek, jejichž
název vždy začíná MSys. Například v tabulce MSysRelationships jsou
uloženy všechny relace mezi objekty (tabulkami a dotazy). Jednou ze
zajímavějších tabulek je MSysObjects obsahující všechny objekty databáze
a k nim i různé další údaje, čehož lze využít při hledání metadat (viz
následující kapitola)**Je zadán neplatný pramen.**.

Metadata databázových souborů
-----------------------------

Kromě samotných uživatelských dat lze v databázi naléz i další
doplňující údaje, obecně označované jako metadata**Je zadán neplatný
pramen.**:

-   *Autor databáze* -- jméno uživatele a název organizace.

-   *Formát souboru* -- verze aplikace, ve které byla databáze
    vytvořena, nastavení režimu kompatibility, atp.

-   *Údaje o databázi a objektech *-- datum vytvoření a poslední úpravy.

-   *Uživatelské nastavení aplikace*** **-- nastavení navigačního panelu
    (řazení/seskupení/šířka/...) a jiných prvků GUI až např. grafické
    rozvržení relací (viz obr. 4).

![](media/image4.png){width="4.969515529308836in"
height="3.1037740594925634in"}

Obrázek 4 -- grafické rozvržení relací mezi tabulkami,\
které lze považovat za metadata databáze.

Možnosti čtení souborů ACCDB
----------------------------

### Formát ACCDB

Nativním formátem pro ukládání Access databází je od verze 2007 ACCDB, v
předchozích verzích byl hlavním formátem MDB. Oba jsou založeny na
technologii Jet (u formátu ACCDB také označované jako Access Database
Engine) a jsou si tedy technologicky podobné. Z uživatelského hlediska
jsou rozdíly zejména v možnostech zabezpečení
dat[2](#_toc_2)[5](#_toc_5)[6](#_toc_6) \[, , \].

Jedná se o proprietární binární formáty vyvíjený společností Microsoft
bez dostupné specifikace. Jediným oficiálním nástrojem pro správu je
právě Microsoft Access, pro přístup k datům z jiných aplikací pak
technologie ODBC a OLE DB [5](#_toc_5)\[\].

To velmi omezuje možnosti programového přístupu k databázím -- pokud
bychom vzali v potaz pouze oficiální nástroje, jsme limitováni na
systémy s nainstalovanou aplikací Microsoft Access (a tím pádem i
operačním systémem Microsoft Windows). V současné době jsou však
dostupné i nástroje vzniklé na základě reverzního inženýrství formátů
MDB/ACCDB bez závislosti na programovém vybavení počítače.

V následujících podkapitolách jsou zmíněny všechny možnosti čtení
souborů ACCDB včetně výhod a nevýhod, jaké přináší.

### ODBC

ODBC (Open Database Connectivity) je standardizované API pro přístup k
datům uloženým v databázích. Připojení ke konkrétním databázím je
zajištěno speciálními ovladači, které lze do systému doinstalovat. Pro
komunikaci skrze ODBC se typicky využívá jazyk SQL (Structured Query
Language), ovladač poté zajistí vykonání příkazu nad konkrétní
databází[7](#_toc_7) \[\].

Pro přístup k ACCDB databázím v rámci OS Microsoft Windows se využívají
ovladače Access Database Engine nainstalované spolu s aplikací Microsoft
Access, případně ze samostatného distribučního balíku[2](#_toc_2) \[\].
Pro další platformy existují komerční Access ODBC ovladače[^2]. Vzniká
zde tedy závislost na dostupnosti ovladače, přičemž v určitých případech
může být problém jej do systému doplnit.

Zásadní nevýhodou přístupu k datům přes ODBC API jsou omezení
vyplývající z univerzálnosti metody. Jednoduše lze pracovat pouze s daty
v tabulkách a není možné přímo přistupovat k dalším uloženým
objektům[7](#_toc_7) \[\]. Jedinou možnost je využít skryté systémové
tabulky, pomocí kterých lze zjistit alespoň existenci objektů.

Novější obdobnou technologií je OLE DB (*Object Linking and Embedding,
Database*), vyvinuté firmou Microsoft původně jako nástupce ODBC; a dále
technologie ADO a ADO.NET stavící nad ODBC, resp. OLE DB[8](#_toc_8)
\[\]. Z hlediska způsobu použití a nabízených funkcí pro čtení souboru
ACCDB jsou však všechny technologie shodné.

### Microsoft Office Interop

Aplikace z balíku Microsoft Office lze programově ovládat pomocí technik
obecně označovaných jako *interoperability* (zkráceně *interop*).
Typicky se využívají proprietární technologie COM (Common Object Model)
a OLE (Object Linking and Embedding) vyvinuté firmou
Microsoft[8](#_toc_8) \[\]. Dále jsou poskytovány *Primary Interop
Assemblies *-- knihovny určené pro použití na platformě .NET (tedy v
tzv. řízeném kódu) obalující COM volání do objektového rozhraní. V
současné době jsou tyto knihovny nejjednodušší cestou pro programové
ovládání aplikací Microsoft Office (mj. se využívají i pro psaní
doplňků, *plug-inů*, pro jednotlivé Office aplikace)[9](#_toc_9) \[\].

Tato technika oproti ODBC umožňuje kompletní správu databáze vč. všech
dostupných objektů bez nutnosti analyzovat obsah systémových tabulek.
Zůstává zde však omezení na systémy, kde je nainstalovaný Microsoft
Access. Jde rovněž o poměrně pomalý přístup, jelikož interop kód
de-facto jen ovládá aplikaci Microsoft Access spuštěnou na pozadí.

### MDB Tools

Jedná se o open-source sadu nástrojů pro práci se soubory Microsoft
Access, respektive Jet databázemi ve formátu MDB, jejíž vývoj započal
již v roce 2000. Vzhledem k uzavřenosti formátu vznikla většina nástrojů
technikami reverzního inženýrství, není tedy zaručena stoprocentní
funkčnost a kompatibilita[10](#_toc_10) \[\].

Nástroje jsou napsány v jazyce C a mají konzolové rozhraní, dále
existuje několik grafických nadstaveb pro prohlížení Access souborů.
Součástí projektu je i dokument popisující strukturu a klíčové části Jet
databází[11](#_toc_11) \[\]. V posledních letech probíhá vývoj pomalým
tempem a podpora novějších verzí Access databází včetně formátu ACCDB
není zaručena[12](#_toc_12) \[\]. Hlavní výhodou nástrojů je nezávislost
na konkrétní platformě a externích knihovnách.

### MDB Tools Java

V roce 2004 začala *portace* nástrojů MDB Tools pro jazyk Java, vývoj
však již po roce ustal[13](#_toc_13) \[\]. Následně vzniklo několik
*forků* (projektů založených na kódu původního projektu), nejaktuálnější
z nich lze nalézt pod názvem OME MDB Tools[14](#_toc_14) \[\]. Vývoj
těchto projektů je ale spíše pomalý -- poslední větší aktualizace
proběhla v roce 2016.

Oproti dále uvedené knihovně Jackcess nabízí méně možností a použité je
značně komplikované[15](#_toc_15) \[\]. Překážkou je chybějící
dokumentace jak pro použití nástrojů, tak i samotného programového kódu.

### Jackcess

Jackcess je Java knihovna poskytující čisté objektové rozhraní pro práci
s Microsoft Access databázemi. Její vývoj započal v roce 2005 v rámci
open-source projektu OpenHMS zaštítěného firmou Health Market Science,
Inc a funguje na stejných principech jako nástroje MDB Tools, kterými se
vývojáři na počátku inspirovali[16](#_toc_16)[17](#_toc_17) \[, \].

Kromě čtení dat z tabulek umožňuje i základní editaci struktury
databáze, výpis všech relací mezi tabulkami a výpis uložených dotazů.
Díky přístupu ke skrytým systémovým tabulkám lze vyhledat i uložené
formuláře a sestavy (reálně však lze zjistit pouze jejich existenci).
Podporuje Access databáze ve verzích 2000 až 2016 (ve formátu MDB i
ACCDB) a ve verzi 97 v režimu pro čtení. Knihovna neobsahuje rozhraní
pro spouštění SQL dotazů, neumožňuje tedy ani vyhodnocení uložených
dotazů[17](#_toc_17) \[\].

Zásadního výhodou pro potřeby této práce je přenositelnost knihovny
(nezávislost na platformě), aktivní vývoj -- tím pádem i podpora
nejnovějších verzí Access databází. Vzhledem k distribuci v podobě
samostatné Java knihovny je rovněž její použití ve vlastní aplikaci
jednoduché.

Pro knihovnu existuje rozšíření nazvané Jackcess Encrypt umožňující
správu databází zašifrovaných heslem. Podporuje některé formy šifer
aplikací Microsoft Access a Microsoft Money[18](#_toc_18) \[\].

### JDBC

Java Database Connectivity je API pro přístup k relačním databázím a
tedy obdobou technologie ODBC pro programovací jazyk Java. API je
standardní součástí platformy Java SE. Připojení ke konkrétní databázi
je opět zajištěno ovladači určenými pro konkrétní typ
databází[19](#_toc_19) \[\].

Microsoft neposkytuje vlastní JDBC ovladač pro práci s Access/Jet
databázemi, existují ale speciální ovladače, tzv. *JDBC-ODBC bridge,*
umožňující použití ODBC ovladačů. V rámci platformy Java SE do verze 1.7
byl takový ovladač standardní součástí; do novějších verzí jej lze
překopírovat (jde ale o neoficiální postup bez záruky funkčnosti) nebo
nahradit komerčními produkty[7](#_toc_7)[20](#_toc_20) \[, \].

Dále existuje několik JDBC „nativních" ovladačů pro práci s Microsoft
Access databázemi. Jde zejména o open-source projekt UCanAccess, který
využívá již zmíněnou knihovnu Jackcess[21](#_toc_21) \[\]. K dispozici
jsou rovněž komerční produkty [^3]^,^[^4]^,^[^5].

Stejně jako u technologie ODBC jsou největší nevýhodou omezení v
důsledku univerzálnosti přístupu, jinými slovy -- jednoduše pracovat je
možné pouze s daty v tabulkách a k ostatním objektům databází je přístup
obtížný či nemožný[19](#_toc_19) \[\]. Výhodou je lepší přenositelnost
na jiné platformy.

Portál ZČU
==========

Základní informace
------------------

Obecně termínem portál označujeme webovou aplikaci, která uživateli
poskytuje jednotným způsobem a centralizovaně informace z různých
zdrojů, které uživatele zajímají nebo se ho nějakým způsobem
týkají[21](#_toc_21) \[\].

Portál ZČU[^6] je informační zdroj pro studijní účely používaný studenty
i zaměstnanci Západočeské Univerzity. Zastřešuje různé klíčové aplikace
univerzity -- z pohledu studentů se jedná zejména o *IS/STAG*
(Informační systém studijní agendy, umožňující zápis předmětů, zkoušek,
prohlížení rozvrhů, hodnocení kvality výuky, atp.) a *Courseware* (místo
s materiály používanými v rámci výuky předmětů).

Jednotlivé stránky portálu se skládájí z více či méně nezávislých částí,
které se nazývají portlety. Jedním z nich je *aplikace pro správu
semestrálních prací, jejich odevzdávání a hodnocení*. Referenční
příručka IS/STAG uvádí následující [21](#_toc_21)\[\]:

> *Aplikace slouží vyučujícím k vypisování témat semestrálních prací v
> jimi vyučovaných předmětech. Studenti se na tato témata posléze mohou
> přihlašovat a elektronicky odevzdávat své práce --- vyučující si
> odevzdané práce může jednotlivě či hromadně stáhnout. Vyučující pak
> dle své vůle může využít i dalších možností aplikace, například
> odevzdané práce studentovi schvalovat či vracet k přepracování atd. *

Validátor studentských prací
----------------------------

V případě, že studenti odevzdávají své práce elektronicky
prostřednictvím zmíněného portletu, často po odevzdání čekají delší dobu
na zkontrolování práce vyučujícím. Pokud shledá práci nevyhovující,
vrací ji studentovi a proces se může následně i několikrát opakovat, což
znamená pro vyučujícího velkou časovou zátěž.

Cílem validačního serveru studentských prací (dále jen validátor) je
eliminovat tento proces a již při nahrání automatizovaně kontrolovat,
zda je práce „vyhovující" -- to může mít mnoho podob,
například[22](#_toc_22) \[\]:

-   **Práce splňuje formální náležitosti:**

    -   soubor má požadovaný formát,

    -   soubor je vhodně pojmenován,

    -   v nahraném archivu jsou požadované soubory ve správné adresářové
        struktuře,

    -   v nahraném archivu nejsou žádné nadbytečné soubory.

-   **Práce je vyhovující kvalitativně:**

    -   odevzdaný dokument splňuje stanovený rozsah (typicky minimální
        počet slov či stran),

    -   nahraný zdrojový kód lze zkompilovat,

    -   nahraná program má totožný výstup, jako připravený referenční.

Automatizace této kontroly poté přináší mnoho výhod [22](#_toc_22)\[\]:

-   Student se okamžitě dozví, zda jeho práce splňuje základní kritéria
    stanovená učitelem.

-   Protože je nevyhovující práce okamžitě odmítnuta, nemůže student
    zneužít nahrání takové práce k získání dalšího času.

-   Pokud není omezen počet odevzdání, může student u určitých úloh
    zkoušet různá inovativní řešení, u kterých si není jist správností.

-   Vyučující není zatěžován mechanickým kontrolováním formální
    správnosti.

-   K hodnocení se dostanou jen práce s určitou minimální mírou kvality.

...

Validátor běží jako samostatná služba, se kterým portlet aplikace
semestrálních prací komunikuje. \< konkrétní validační pravidla
nastavuje učitel na serveru, říká se jim validační doména \>

Vytvoření nové validační domény
-------------------------------

Analýza kontroly prací
======================

Požadavky na řešení
-------------------

Případy užití
-------------

Validace databáze
-----------------

Vyhodnocení plagiarismu
-----------------------

Implementace systému pro automatickou kontrolu prací
====================================================

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

Testování vytvořeného systému
=============================

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

[]{#_toc_2
.anchor}<https://support.office.com/en-us/article/Introduction-to-the-Access-2007-file-format-8cf93630-0b68-4a40-a13c-7528b9f074b6>

[]{#_toc_3
.anchor}<https://support.office.com/en-us/article/data-types-for-access-desktop-databases-df2b83ba-cef6-436d-b679-3418f622e482>

[]{#_toc_4 .anchor}

[]{#_toc_5
.anchor}<https://www.loc.gov/preservation/digital/formats/fdd/fdd000462.shtml>

[]{#_toc_6
.anchor}<https://support.office.com/en-us/article/which-access-file-format-should-i-use-012d9ab3-d14c-479e-b617-be66f9070b41>

[]{#_toc_8 .anchor}

[]{#_toc_9
.anchor}<https://msdn.microsoft.com/en-us/library/15s06t57.aspx>

[]{#_toc_10 .anchor}<https://github.com/brianb/mdbtools/>

[]{#_toc_11
.anchor}<https://github.com/brianb/mdbtools/blob/master/HACKING>

<https://github.com/brianb/mdbtools/issues/77>

<https://sourceforge.net/p/mdbtools/discussion/6688/thread/a543445a/>

[]{#_toc_14 .anchor}<https://github.com/ome/ome-mdbtools>

<https://github.com/ome/ome-mdbtools/blob/master/src/main/java/mdbtools/tests/ColumnTest.java>

[]{#_toc_16 .anchor}<http://jackcess.sourceforge.net/>

[]{#_toc_17 .anchor}<http://jackcess.sourceforge.net/faq.html>

[]{#_toc_18 .anchor}<http://jackcessencrypt.sourceforge.net/>

[]{#_toc_19 .anchor}

[]{#_toc_20
.anchor}<https://docs.oracle.com/javase/7/docs/technotes/guides/jdbc/bridge.html>

[]{#_toc_21 .anchor}

<https://validator-test.zcu.cz/vs/auth/doc/doc/validacni-server-uzivatelsky-popis-2.pdf>

\[1\] ADAMSKI, Joseph J.; FINNEGAN, Kathy T. ; SCOLLARD, Sharon. *New
perspectives on Microsoft Access 2013: comprehensive.* Stamford, CT:
Cengage Learning, 2014. ISBN 978-1-285-09920-0.\[2\] Introduction to the
Access 2007 file format. *Microsoft Office help and training - Office
Support.* \[Online\] \[Citace: 20. 3. 2018\]. Dostupné z: \[3\] Data
types for Access desktop databases. *Microsoft Office help and training
- Office Support.* \[Online\] \[Citace: 22. 3. 2018\]. Dostupné z: \[4\]
CONNOLLY, Thomas; BEGG, Carolyn. *A Practical Approach to Design,
Implementation, and Management.* 6. Harlow: Pearson Education Limited,
2014. ISBN 978-1-292-06118-4.\[5\] Microsoft Access ACCDB File Format
Family. *Digital Preservation at the Library of Congress.* \[Online\]
\[Citace: 20. 3. 2018\]. Dostupné z: \[6\] Which Access file format
should I use? *Microsoft Office help and training - Office Support.*
\[Online\] \[Citace: 20. 3. 2018\]. Dostupné z: \[7\] KYLE, Geiger.
*Inside ODBC.* Redmond, WA: Microsoft Press, 1995. ISBN
978-1556158155.\[8\] ROFF, Jason T. *ADO: ActiveX Data Objects.* místo
neznámé: O\'Reilly Media, 2001. ISBN 9781491935576.\[9\] Office Primary
Interop Assemblies. *Microsoft Developer Network.* \[Online\] \[Citace:
02. 04. 2017\]. Dostupné z: \[10\] BRUNS, Brian. *MDB Tools repository.*
\[Online\] \[Citace: 20. 4. 2018\]. Dostupné z: \[11\] ---. HACKING.
*MDB Tools repository.* \[Online\] \[Citace: 20. 4. 2018\]. Dostupné z:
\[12\] ---. Access 2013 support. *MDB Tools repository.* \[Online\]
\[Citace: 20. 4. 2018\]. Dostupné z: \[13\] SMITH, Calvin R. mdbtools is
being ported to java. *MDB Tools Discussion.* \[Online\] 2. 5. 2004
\[Citace: 20. 4. 2018\]. Dostupné z: \[14\] Open Microscopy Environment.
*OME MDB Tools.* \[Online\] \[Citace: 20. 4. 2018\]. Dostupné z: \[15\]
---. ColumnTest source code (ukázka použití). *OME MDB Tools.*
\[Online\] \[Citace: 20. 4. 2018\]. Dostupné z: \[16\] *Jackcess.*
\[Online\] Health Market Science, 31. 3. 2018 \[Citace: 20. 4. 2018\].
Dostupné z: \[17\] Frequently Asked Questions. *Jackcess.* \[Online\]
Health Market Science, 31. 3. 2018 \[Citace: 20. 4. 2018\]. Dostupné z:
\[18\] *Jackcess Encrypt.* \[Online\] Health Market Science, 9. 10. 2017
\[Citace: 20. 4. 2018\]. Dostupné z: \[19\] MAYDENE FISHER, Jon Ellis,
Jonathan Bruce. *JDBC™ API Tutorial and Reference.* Boston, MA: Addison
Wesley, 2003. ISBN 0-321-17384-8.\[20\] ORACLE. JDBC-ODBC Bridge. *Java
SE Documentation.* \[Online\] \[Citace: 20. 4. 2018\]. Dostupné z:
\[21\] Centrum informatizace a výpočetní techniky. *Referenční příručka
portálového rozhraní IS/STAG.* Plzeň: Západočeská univerzita, 2009. ISBN
978-80-7043-807-7.\[22\] HEROUT, Pavel. *Validační server pro studentské
projekty.* \[Online\] \[Interní dokument\] \[Citace: 20. 4. 2018\].
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

[^2]: Viz WWW:
    https://www.easysoft.com/products/data\_access/odbc-access-driver/

[^3]: Viz WWW:
    <https://www.easysoft.com/products/data_access/jdbc-access-gateway/>

[^4]: Viz WWW: <http://www.hxtt.com/access.html>

[^5]: Viz WWW:
    http://sesamesoftware.com/relational-junction/jdbc-database-drivers-↩
    products/relational-junction-mdb-jdbc-driver/

[^6]: Dostupný na adrese <https://portal.zcu.cz/>
