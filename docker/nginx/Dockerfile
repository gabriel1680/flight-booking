FROM nginx:latest
RUN rm -f /var/log/nginx/access.log && rm -f /var/log/nginx/error.log
COPY default.conf /etc/nginx/conf.d/default.conf

# Download filebeat
RUN curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-8.13.4-arm64.deb && \
    dpkg -i filebeat-8.13.4-arm64.deb

# Bind filebeat to send the logs to ELK
COPY filebeat.yml /etc/filebeat/filebeat.yml
COPY nginx.yml /etc/filebeat/modules.d/nginx.yml
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x entrypoint.sh
ENTRYPOINT [ "/entrypoint.sh" ]
