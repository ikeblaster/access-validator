// browser_switch.cpp : Defines the entry point for the console application.
//

#include <tchar.h>
#include <string>
//#include <fstream>
#include <Windows.h>

using namespace std;


int _tWinMain(HINSTANCE hInstance, HINSTANCE, LPTSTR lpCmdLine, int cmdShow)
{
    // cesta bez nazvu exe
    HMODULE hModule = GetModuleHandleW(NULL);
    TCHAR path[1024];
    GetModuleFileName(hModule, path, 1024);

    char *ptr = strrchr(path,'\\');
    if(ptr != NULL) *ptr = '\0';

    
    string process = "";   
    STARTUPINFO si;
    PROCESS_INFORMATION pi;

    ZeroMemory( &si, sizeof(si) );
    ZeroMemory( &pi, sizeof(pi) );

    si.cb = sizeof(STARTUPINFO);
    si.dwFlags = STARTF_USESHOWWINDOW;
    //si.wShowWindow = SW_MAXIMIZE;

    process.append("\"");
    process.append(path);
    process.append("\\run.bat\" ");
    process.append(lpCmdLine);
    
    TCHAR cmd[1024];
    int i;
    for (i=0; i<process.length(); i++) {
        cmd[i] = process[i];
    }
    cmd[i] = '\0';
    
    CreateProcess( NULL,   // No module name (use command line)
        cmd,  
        NULL,           // Process handle not inheritable
        NULL,           // Thread handle not inheritable
        FALSE,          // Set handle inheritance to FALSE
        0,              // No creation flags
        NULL,           // Use parent's environment block
        NULL,           // Use parent's starting directory 
        &si,            // Pointer to STARTUPINFO structure
        &pi );          // Pointer to PROCESS_INFORMATION structure
    
    /*ofstream out("C:\\output.txt"); 
    out << process;
    out.close();*/

    
    return 0;
}
