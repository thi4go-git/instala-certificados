set PGUSER=postgres
set PGPASSWORD=Vando243569@
set backup_dir=D:\Certificados\INSTALA_CERTIFICADOS\BACKUP BD\BACKUP-DIARIO
set timestamp=%DATE:/=-%_%TIME::=-%
set timestamp=%timestamp:,=.%
set backup_file=%backup_dir%\backup_%timestamp%.sql
"C:/Program Files/PostgreSQL/14/bin\"pg_dump.exe --host localhost --port 5432 --format custom --blobs --verbose --file "%backup_file%" "certificado"