(function() {
  'use strict';

  describe('controllers', function(){
    var vm;

    beforeEach(module('ontpro'));
    beforeEach(inject(function(_$controller_) {
      vm = _$controller_('MainController');
      spyOn(vm, 'getTestDataControl').and.callThrough();
      vm.getTestDataControl("test");

    }));

    it("calls a spy", function() {
      expect(vm.getTestDataControl).toHaveBeenCalled();
    });

    it("makes the rigth calls", function() {
      expect(vm.getTestDataControl).toHaveBeenCalledWith("test");
    });
    
  });
})();
