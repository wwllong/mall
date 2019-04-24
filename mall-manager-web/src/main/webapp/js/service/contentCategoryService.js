/**
 * 内容/广告类目服务层
 * Created by Wwl on 2019/3/4
 */
app.service('contentCategoryService', function($http){

    //增加
    this.add = (entity) => $http.post('../contentCategory/add.do',entity);

    //修改
    this.update = (entity) => $http.post('../contentCategory/update.do',entity);

    //删除
    this.delete = (ids) => $http.get('../contentCategory/delete.do?ids='+ids);

    //查询实体
    this.findOne = (id) => $http.get('../contentCategory/findOne.do?id='+id);

    //查询列表
    this.findList = () => $http.get('../contentCategory/list.do');

    //分页查询
    this.findPage = (page,size) => $http.get('../contentCategory/listByPage.do?page=' + page + '&size=' + size);

    //搜索
    this.search = (page,size,searchEntity) => $http.post('../contentCategory/search.do?page='+page+'&size='+size,searchEntity);

    //下拉列表数据
    this.findOptionList = () => $http.get('../contentCategory/findOptionList.do');

});