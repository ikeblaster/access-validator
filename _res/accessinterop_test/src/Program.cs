using Microsoft.Office.Interop.Access.Dao;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace accessinteroptest
{
    class Program
    {
        static void Main(string[] args)
        {
            var accApp = new Microsoft.Office.Interop.Access.Application();
            accApp.OpenCurrentDatabase(new FileInfo("db.accdb").FullName);
            Microsoft.Office.Interop.Access.Dao.Database cdb = accApp.CurrentDb();

            foreach (TableDef item in cdb.TableDefs)
            {
                Console.WriteLine(item.Name);
            }

        }
    }
} 
