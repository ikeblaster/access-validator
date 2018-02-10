package cz.zcu.kiv.accessvalidator.configurator.rules;

/**
 * @author ike
 */
public class TableExistsRule extends Rule {

    public TableExistsRule() {
        super();
        this.properties.add(new RuleProperty("name", "", "Název", "Název tabulky", ""));
        this.properties.add(new RuleProperty("count", Integer.valueOf(0), "Počet", "Počet tabulek", ""));
    }

    @Override
    public String toString() {
        return "Existuje tabulka";
    }

}
