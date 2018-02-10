package cz.zcu.kiv.accessvalidator.validator;

import java.io.File;

/**
 * @author ike
 */
public class main {
    public static void main(String[] args) throws Exception {

        AccdbChecker adb = new AccdbChecker(new File("data/databaze.accdb"));

        System.out.println(adb.hasTable("Klient"));

        if(!adb.hasTable("Klient")) throw new Exception("Table Klient missing");
        if(!adb.hasTable("Pracovnik CK")) throw new Exception("Table Pracovnik CK missing");
        if(adb.tableColumnCount("Klient") != 7) throw new Exception("Table Klient doesn't have 7 columns");
        if(!adb.tableHasColumn("Klient", "Jmeno")) throw new Exception("Table Klient doesn't have column");


        /*
        Table table = db.getSystemTable("MSysObjects");

        for(Column column : table.getColumns()) {
            System.out.format("%-50s", column.getName());
        }

        System.out.println();

        for(Row row : table) {

            for(Column column : table.getColumns()) {
                System.out.format("%-50.38s", row.get(column.getName()));
            }
            System.out.println();

        }
        */
    }



}
