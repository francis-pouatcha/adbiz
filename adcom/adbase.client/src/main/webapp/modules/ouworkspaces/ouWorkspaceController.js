(function () {
    'use strict';
    angular.module('AdBase').controller('ouWorkspaceController',ouWorkspaceController);

    ouWorkspaceController.$inject = ['$scope', 'ouWorkspaceService','$location','$routeParams'];

    function ouWorkspaceController($scope,ouWorkspaceService, $location,$routeParams){
        var self = this ;
        self.ouWorkspaceDTOHolder= {};
        self.error = "";
        self.saveWorkspace = saveWorkspace;
        self.cachedDto = [];
        self.store = store;
        
        
        init();
        
        function init() {
            findActiveOuWorkspaces();
        }
        
        function findActiveOuWorkspaces(){
            var targetOuIdentif = $routeParams.targetOuIdentif;
            ouWorkspaceService.findActivesOuWorkspaces(targetOuIdentif).then(function(result){
                self.ouWorkspaceDTOHolder = result;
            },function(error){
                self.error = error;
            });
        };
        
        function saveWorkspace() {
            var copy = self.ouWorkspaceDTOHolder;
            copy.dtos = self.cachedDto;
            ouWorkspaceService.assignWorkspaces(copy).then(function(result){
                self.ouWorkspaceDTOHolder = result;
                self.cachedDto = [];
            },function(error){
                self.error = error;
            });
        }
        
        function store(dto) {
            if(self.cachedDto.length >= 1) {
                var ind ;
                angular.forEach(self.cachedDto,function(value,index){
                    if(value.identif === dto.identif) {
                        ind = index;
                    };
                  });
                
                if(ind) {
                    self.cachedDto.splice(ind);
                }else {
                    self.cachedDto.push(dto);    
                }
            }else {
                self.cachedDto.push(dto);
            }
        }
    };



})();