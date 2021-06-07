FROM node:latest
ENV PATH /app/node_modules/.bin:$PATH

# set working directory
WORKDIR /app

COPY ["package.json", "package-lock.json*", "./"]
COPY . ./

RUN npm install
RUN npm install react react-dom leaflet
RUN npm install react-leaflet
RUN npm install -D @types/leaflet

# port in the Runtime VM
EXPOSE 50003

# start app
CMD ["npm", "start"]
