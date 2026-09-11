#!/bin/sh

DATE=$(date +"%Y-%m-%d_%H-%M-%S")

echo "Iniciando backup em $(date)"

pg_dump \
    -h "$PGHOST" \
    -p "$PGPORT" \
    -U "$PGUSER" \
    -d "$PGDATABASE" \
    -F c \
    -f "/backups/estik_$DATE.dump"

if [ $? -eq 0 ]; then
    echo "Backup realizado com sucesso: estik_$DATE.dump"
else
    echo "ERRO ao realizar backup"
    exit 1
fi

# Apaga backups com mais de 30 dias
find /backups \
    -type f \
    -name "estik_*.dump" \
    -mtime +30 \
    -delete

echo "Backup finalizado em $(date)"