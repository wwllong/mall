/**
 * 规格服务层
 * Created by Wwl on 2019/3/14
 */
app.service('specificationService',function ($http) {

    //增加
    this.add = function (entity) {
        return $http.post('../specification/add.do',entity);
    }

    //修改
    this.update = function (entity) {
        return $http.post('../specification/update.do',entity);
    }

    //删除
    this.delete = function (ids) {
        return $http.get('../specification/delete.do?ids='+ids);
    }

    //查询实体
    this.findOne = function (id) {
        return $http.get('../specification/findOne.do?id='+id);
    }

    //查询列表
    this.findList = function(){
        return $http.get('../specification/list.do');
    }

    //分页查询
    this.findPage = function(page, size){
        return $http.get('../specification/listByPage.do?page=' + page + '&size=' + size);
    }

    //搜索
    this.search = function (page,size,searchEntity) {
        return $http.post('../specification/search.do?page='+page+'&size='+size,searchEntity);
    }

    //下拉列表数据
    this.findSpecList = function () {
        return $http.get('../specification/findOptionList.do');
    }

});