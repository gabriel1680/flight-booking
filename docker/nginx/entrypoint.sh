#!/bin/bash

/docker-entrypoint.sh
filebeat setup
service filebeat start
nginx -g 'daemon off;'
