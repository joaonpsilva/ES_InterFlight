FROM node:alpine
# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install app dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install --silent
RUN chown -R node.node /app
RUN npm install react-scripts@3.4.1 -g --silent
RUN npm install react react-dom leaflet
RUN npm install react-leaflet
RUN npm install -D @types/leaflet

# add app
COPY . ./

EXPOSE 3000

# start app
CMD ["npm", "start"]

