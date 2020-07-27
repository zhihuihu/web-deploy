
var layHeight ='full';
var secondLayHeight ='full-135';
var layRequest ={
    pageName: 'currentPage' //页码的参数名称，默认：page
    ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
};
var layResponse={
    statusName: "code" //数据状态的字段名称，默认：code
    ,statusCode: "000000" //成功的状态码，默认：0
    ,msgName: 'msg' //状态信息的字段名称，默认：msg
    ,countName: 'totalLine' //数据总数的字段名称，默认：count
    ,dataName: 'msg' //数据列表的字段名称，默认：data
};


/*弹出层*/

/*
参数解释：
title   标题
url     请求的url
id      需要操作的数据id
w       弹出层宽度（缺省调默认值）
h       弹出层高度（缺省调默认值）
*/
function showPage(title, url, w, h) {
    if (title == null || title == '') {
        title = false;
    }
    ;
    if (url == null || url == '') {
        url = "404.html";
    }
    ;
    if (w == null || w == '') {
        w = ($(window).width() * 0.9);
    }
    ;
    if (h == null || h == '') {
        h = ($(window).height() * 0.9);
    }
    ;
    layer.open({
        type: 2,
        area: [w + 'px', h + 'px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: true,
        shade: 0.4,
        title: title,
        content: url
    });
}

layui.use('form', function(){
    var form = layui.form;
    form.verify({
        username: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                return '用户名不能有特殊字符';
            }
            if(/(^\_)|(\__)|(\_+$)/.test(value)){
                return '用户名首尾不能出现下划线\'_\'';
            }
            if(/^\d+\d+\d$/.test(value)){
                return '用户名不能全为数字';
            }
        }

        //我们既支持上述函数式的方式，也支持下述数组的形式
        //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
        ,pass: [
            /^[\S]{6,12}$/
            ,'密码必须6到12位，且不能出现空格'
        ]
        ,telephone: [
            /^((0\d{2,3}-\d{7,8})|(0\d{8,11})|(1\d{10}))$/
            ,'请输入正确的电话号码'
        ]
    });
});
