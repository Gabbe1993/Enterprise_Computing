This exercise is based on the i-Car LoopBack app from Miroslav Bajtos.

## Prerequisites

You must have already installed [Node.js](https://nodejs.org/).

Install StrongLoop:
```
$ npm install -g strongloop
```

If you run into any issues, see [Installing StrongLoop](http://docs.strongloop.com/display/LB/Installing+StrongLoop)
for more information.

## EC Exercise 2.1

This application was created using LoopBack tools. 
See [Building from scratch](#building-from-scratch) below for more details.

### Run the application

Start the application back-end by running the following command:

```
$ node . 
```

Now open your browser and point it to
[http://127.0.0.1:3000/explorer](http://127.0.0.1:3000/explorer) to access the application UI.

## REST APIs

 - `/calendar` exposes a queryable (filter, sort) collection of available calendar items
    over HTTP / JSON

## Configuring the data source

By default, the sample application uses the memory connector and listens on
the port 3000 on all network interfaces.

You can configure other data sources by adding a new key to `DATASTORES`
object in `rest/datasources.local.js`:

```js
var DATASTORES = {
  custom: {
    connector: 'my-custom-connector',
    // configuration for the custom connector
  },
  memory: {
  },
  // etc.
};
```

The sample can be configured using the following environment variables:

- `DB`: The db type, use `memory`, `mongodb` or `oracle`
- `IP`: The http server listener ip address or hostname, default to `0.0.0.0`
   (any address)
- `PORT`: The http server listener port number, default to `3000`

For example,

 - To run the application at port 3001 with MongoDB:

   ```
   $ DB=mongodb PORT=3001 node .
   ```

 - To run the application at port 3002 with Oracle:

  ```
   $ DB=oracle PORT=3002 node .
  ```

## Project files

The project is composed from multiple components.

 - `models/` contains definition of models and implementation of custom model
   methods.

 - `rest/` contains the REST API server, it exposes the shared models
   via REST API.

 - `server/` is the main HTTP server that brings together all other components.

 - `test/` provides few basic unit-tests to verify that the server provides
  the expected API.

Refer to
[Creating an application](http://docs.strongloop.com/display/LB/Creating+an+application)
for more information.


One last thing. Just run npm install to install the required dependencies.
