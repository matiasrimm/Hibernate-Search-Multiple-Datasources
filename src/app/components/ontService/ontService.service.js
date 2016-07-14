(function(){
  'use strict';

  angular
    .module('ontpro')
    .factory('ontService', ontService);

    /** @ngInject */
    function ontService($log, $http) {
      var apiHost = 'http://localhost:8080';

      var service = {
        apiHost: apiHost,
        getTestData: getTestData
      };

      return service;

      function getTestData(search) {

        return $http.get(apiHost + '/?s=' + search)
          .then(getOnts)
          .catch(ontServiceError);

        function getOnts(response) {
          return response.data;
        }

        function ontServiceError(error) {
          $log.error('Error in backend' + angular.toJson(error.data, true));
        }
      }
    }

})();
