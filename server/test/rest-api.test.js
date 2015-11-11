/*global describe, it, before */
/**
 * REST API Tests
 */
var request = require('supertest');
var app = require('../server');
var assert = require('assert');

before(function importSampleData(done) {
  this.timeout(50000);
  if (app.importing) {
    app.on('import done', done);
  } else {
    done();
  }
});

function json(verb, url) {
  return request(app)[verb](url)
    .set('Content-Type', 'application/json')
    .set('Accept', 'application/json')
    .expect('Content-Type', /json/);
}

describe('REST', function() {
  this.timeout(30000);

  /**
   * Expected Input Tests
   */

});
