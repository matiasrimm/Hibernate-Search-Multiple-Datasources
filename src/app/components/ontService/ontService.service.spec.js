(function() {
  'use strict';

  describe('service ontService', function() {
    var ontService;
    var $httpBackend;
    var $log;

    beforeEach(module('ontpro'));
    beforeEach(inject(function(_ontService_, _$httpBackend_, _$log_) {
      ontService = _ontService_;
      $httpBackend = _$httpBackend_;
      $log = _$log_;
    }));

    it('register', function() {
      expect(ontService).not.toEqual(null);
    });

    describe('api', function() {
      it('should exist', function() {
        expect(ontService.apiHost).not.toEqual(null);
      });
    });

    describe('function', function() {
      it('should exist', function() {
        expect(ontService.getTestData).not.toEqual(null);
      });

      it('should RETURN data queried', function() {
        $httpBackend.when('GET',  ontService.apiHost + '/?s=test').respond(200, []);
        var data;
        ontService.getTestData("test").then(function(fData) {
          data = fData;
        });
        $httpBackend.flush();
        expect(data).toEqual(jasmine.any(Array));
      });

      it('should print error', function() {
        $httpBackend.when('GET',  ontService.apiHost + '/?s=test').respond(500);
        ontService.getTestData("test");
        $httpBackend.flush();
        expect($log.error.logs).toEqual(jasmine.stringMatching('Error in backend'));
      });

    });
  });
})();
