(function() {
  'use strict';

  angular
    .module('ontpro')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log) {

    $log.debug('runBlock end');
  }

})();
