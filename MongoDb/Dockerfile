FROM --platform=linux/amd64 mongo:7.0.10-rc0-jammy

COPY ./init-mongo.js /docker-entrypoint-initdb.d/

EXPOSE 27017

CMD ["mongod"]