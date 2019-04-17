/**
 * 类型模板服务层
 * Created by Wwl on 2019/3/4
 */
app.service('typeTemplateService',function ($http) {

    //增加
    this.add = function (entity) {
        return $http.post('../typeTemplate/add.do',entity);
    }

    //修改
    this.update = function (entity) {
        return $http.post('../typeTemplate/update.do',entity);
    }

    //删除
    this.delete = function (ids) {
        return $http.get('../typeTemplate/delete.do?ids='+ids);
    }

    //查询实体
    this.findOne = function (id) {
        return $http.get('../typeTemplate/findOne.do?id='+id);
    }

    //查询列表
    this.findList = function(){
        return $http.get('../typeTemplate/list.do');
    }

    //分页查询
    this.findPage = function(page, size){
        return $http.get('../typeTemplate/listByPage.do?page=' + page + '&size=' + size);
    }

    //搜索
    this.search = function (page,size,searchEntity) {
        return $http.post('../typeTemplate/search.do?page='+page+'&size='+size,searchEntity);
    }

});