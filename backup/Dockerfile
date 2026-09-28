FROM postgres:15-alpine

ENV TZ=America/Sao_Paulo

RUN apk add --no-cache tzdata

COPY backup.sh /backup.sh

RUN chmod +x /backup.sh

RUN echo '0 0 * * * /backup.sh >> /var/log/backup.log 2>&1' \
    > /etc/crontabs/root

CMD ["crond", "-f", "-l", "2"]