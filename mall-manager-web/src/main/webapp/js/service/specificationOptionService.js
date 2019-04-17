/**
 * 规格选项服务层
 * Created by Wwl on 2019/3/14
 */
app.service('specificationOptionService', function($http){

    //增加
    this.add = (entity) => $http.post('../specificationOption/add.do',entity);

    //修改
    this.update = (entity) => $http.post('../specificationOption/update.do',entity);

    //删除
    this.delete = (ids) => $http.get('../specificationOption/delete.do?ids='+ids);

    //查询实体
    this.findOne = (id) => $http.get('../specificationOption/findOne.do?id='+id);


    //查询列表
    this.findList = ()=> $http.get('../specificationOption/list.do');

    //分页查询
    this.findPage = (page, size) => $http.get('../specificationOption/listByPage.do?page=' + page + '&size=' + size);

    //搜索
    this.search = (page,size,searchEntity) => $http.post('../specificationOption/search.do?page='+page+'&size='+size,searchEntity);

});