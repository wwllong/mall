/**
 * 规格选项服务层
 * Created by Wwl on 2019/3/14
 */
app.service('specificationOptionService',function ($http) {

    //增加
    this.add = function (entity) {
        return $http.post('../specificationOption/add.do',entity);
    }

    //修改
    this.update = function (entity) {
        return $http.post('../specificationOption/update.do',entity);
    }

    //删除
    this.delete = function (ids) {
        return $http.get('../specificationOption/delete.do?ids='+ids);
    }

    //查询实体
    this.findOne = function (id) {
        return $http.get('../specificationOption/findOne.do?id='+id);
    }

    //查询列表
    this.findList = function(){
        return $http.get('../specificationOption/list.do');
    }

    //分页查询
    this.findPage = function(page, size){
        return $http.get('../specificationOption/listByPage.do?page=' + page + '&size=' + size);
    }

    //搜索
    this.search = function (page,size,searchEntity) {
        return $http.post('../specificationOption/search.do?page='+page+'&size='+size,searchEntity);
    }

});