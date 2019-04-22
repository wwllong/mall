/**
 * 类型模板服务层
 * Created by Wwl on 2019/3/4
 */
app.service('typeTemplateService',function($http){

    //增加
    this.add = (entity) => $http.post('../typeTemplate/add.do',entity);

    //修改
    this.update = (entity) => $http.post('../typeTemplate/update.do',entity);

    //删除
    this.delete = (ids) => $http.get('../typeTemplate/delete.do?ids='+ids);

    //查询实体
    this.findOne = (id) => $http.get('../typeTemplate/findOne.do?id='+id);

    //查询列表
    this.findList = () => $http.get('../typeTemplate/list.do');

    //分页查询
    this.findPage = (page, size) => $http.get('../typeTemplate/listByPage.do?page=' + page + '&size=' + size);

    //搜索
    this.search = (page,size,searchEntity) => $http.post('../typeTemplate/search.do?page='+page+'&size='+size,searchEntity);

    //下拉列表数据
    this.findOptionList = () => $http.get('../typeTemplate/findOptionList.do');

    //返回规格列表以及各个规格的选项
    this.findSpecListWithOptions = (id) => $http.get('../typeTemplate/findSpecListWithOptions.do?id='+id);

});