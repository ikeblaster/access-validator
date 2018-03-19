package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

/**
 * @author ike
 */
public class TablesRowsCountRule extends Rule {

    private ChoiceProperty<ComparisonOperator> countOp;
    private Property<Integer> count;

    public TablesRowsCountRule() {
        super();

        this.countOp = new ChoiceProperty<>(
                ComparisonOperator.class,
                "count_op", ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Tabulky obsahují řádků", "Operátor pro ověření počtu řádků v tabulkách", "Počet řádků v tabulkách");

        this.count = new Property<>(
                Integer.class,
                "count", Integer.valueOf(1),
                "...", "Počet řádků v tabulkách", "Počet řádků v tabulkách");

        this.properties.add(this.countOp);
        this.properties.add(this.count);
    }

    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();

        int tablesInDb = repository.getTables().size();
        repository.filterByRowsCount(this.count.getValue(), this.countOp.getValue());

        return repository.getTables().size() == tablesInDb;
    }

    @Override
    public String toString() {
        return "Počet řádků v tabulkách";
    }

}
