FOR /F "tokens=5 delims= " %%P IN ('netstat -a -n -o ^| findstr :8081') DO TaskKill.exe /PID %%P
FOR /F "tokens=5 delims= " %%P IN ('netstat -a -n -o ^| findstr :8082') DO TaskKill.exe /PID %%P
FOR /F "tokens=5 delims= " %%P IN ('netstat -a -n -o ^| findstr :8083') DO TaskKill.exe /PID %%P
FOR /F "tokens=5 delims= " %%P IN ('netstat -a -n -o ^| findstr :9191') DO TaskKill.exe /PID %%P
FOR /F "tokens=5 delims= " %%P IN ('netstat -a -n -o ^| findstr :9296') DO TaskKill.exe /PID %%P
FOR /F "tokens=5 delims= " %%P IN ('netstat -a -n -o ^| findstr :8761') DO TaskKill.exe /PID %%P