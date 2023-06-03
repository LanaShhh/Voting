FROM node:20
COPY web web
WORKDIR web
ENTRYPOINT ["sh", "-c", "npm start"]
