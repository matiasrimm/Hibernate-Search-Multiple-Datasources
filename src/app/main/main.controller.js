(function() {
  'use strict';

  angular
    .module('ontpro')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController(ontService) {
    var vm = this;

    vm.testThingsForTest = [];
    vm.getTestDataControl = getTestDataControl;

    function getTestDataControl(search) {
      return ontService.getTestData(search).then(function(data) {
        vm.testThingsForTest = data;
        return vm.testThingsForTest;
      });
    }
  }

})();
