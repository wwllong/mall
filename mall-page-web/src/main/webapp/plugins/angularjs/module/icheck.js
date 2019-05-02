/**
 * Created by Wwl on 2019/4/8
 * angular directive ng-icheck
 *
 * @require jquery, icheck
 * @example <input type="radio" ng-model="radioCheck" ng-icheck />
 *          <input type="checkbox" ng-model="checkboxCheck" ng-icheck />
 */

angular.module('icheck', []).directive('ngIcheck', function() {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {

                // ngModelCtrl.$setViewValue(false);

                scope.$watch(attrs['ngModel'], ()=>element.iCheck('update'));

                return element.iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green'

                }).on('ifChanged',function(event){
                    let type = element.attr('type');
                    if (type === 'checkbox' ) {
                        scope.$apply(() => ngModelCtrl.$setViewValue(event.target.checked));
                    }else {
                        scope.$apply(() => ngModelCtrl.$setViewValue(attrs['value']));
                    }
                });
        }
    };
});