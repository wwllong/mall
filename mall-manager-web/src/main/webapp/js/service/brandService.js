/**
 * 品牌服务层
 * Created by Wwl on 2019/3/4
 */
app.service('brandService',function ($http) {

    //增加
    this.add = function (entity) {
        return $http.post('../brand/add.do',entity);
    }

    //修改
    this.update = function (entity) {
        return $http.post('../brand/update.do',entity);
    }

    //删除
    this.delete = function (ids) {
        return $http.get('../brand/delete.do?ids='+ids);
    }

    //查询实体
    this.findOne = function (id) {
        return $http.get('../brand/findOne.do?id='+id);
    }

    //查询列表
    this.findList = function(){
        return $http.get('../brand/list.do');
    }

    //分页查询
    this.findPage = function(page, size){
        return $http.get('../brand/listByPage.do?page=' + page + '&size=' + size);
    }

    //搜索
    this.search = function (page,size,searchEntity) {
        return $http.post('../brand/search.do?page='+page+'&size='+size,searchEntity);
    }

    //下拉列表数据
    this.selectOptionList = function () {
        return $http.get('../brand/selectOptionList.do');
    }

});