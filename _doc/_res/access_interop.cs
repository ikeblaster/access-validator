
var accApp = new Microsoft.Office.Interop.Access.Application();
accApp.OpenCurrentDatabase("db.accdb");

Microsoft.Office.Interop.Access.Dao.Database cdb = accApp.CurrentDb();

foreach (TableDef item in cdb.TableDefs) {
	Console.WriteLine(item.Name);
}
