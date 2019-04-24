/**
 * 上传服务
 * Created by Wwl on 2019/4/20
 */
app.service('uploadService', function($http){

    //上传文件
    this.uploadFile = function () {
        //H5新增的类，表单数据
        let formData = new FormData();
        //约定表单中第一个name=file文件
        formData.append("file",file.files[0]);
        return $http({
            url:'../upload/fast.do',
            method:'post',
            data:formData,
            headers:{ 'Content-Type':undefined },//上传为文件 ，指定为undefined， 浏览器设为multipart/form-data
            transformRequest:angular.identity //二进制序列化
        });
    }

});