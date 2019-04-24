/**
 * 搜索服务层
 * Created by Wwl 2019/4/24
 */
app.service("searchService", function ($http) {

    //搜索服务
    this.search = (searchMap) => ($http.post('itemSearch/search.do',searchMap));

});