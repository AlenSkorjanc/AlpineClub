FROM node:current-alpine3.19
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3300
CMD [ "npm", "start" ]