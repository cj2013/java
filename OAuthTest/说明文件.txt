使用说明
创建spring_cloud_auth数据库
source  auth.sql

修改auth-service中application.yml中的数据库配置
修改hi-service中bootstrap.yml中的数据库配置

运行三个微服务

1、注册新用户
curl -d "username=newname&password=654321" "localhost:8762/user/registry"
返回：
{"id":10,"username":"newname","password":"$2a$10$wl7SqXnqN6fP85NVkSNd7uBEcUSkuiw99R/WX7EDah9.wOZ3oCP.a","authorities":null,"enabled":true,"accountNonExpired":true,"credentialsNonExpired":true,"accountNonLocked":true}

2、获取Token的API接口
curl service-hi:123456@localhost:5000/uaa/oauth/token -d grant_type=password -d username=newname -d password=654321
返回：
{"access_token":"fcf07bf9-dc72-416f-88b3-c8d057416954","token_type":"bearer","refresh_token":"565e0423-4ec2-4b5b-b37c-d0e1327c4dfa","expires_in":43199,"scope":"server"}

3、访问不需要权限的请求：
curl -l -H "Authorization:bearer fcf07bf9-dc72-416f-88b3-c8d057416954" -X GET "localhost:8762/hi"
返回：    hi :,i am from port:8762

4、访问需要有权限的接口
curl -l -H "Authorization:Bearer fcf07bf9-dc72-416f-88b3-c8d057416954" -X GET "localhost:8762/hello"
返回： {"error":"access_denied","error_description":"不允许访问"}

5、在表user_role中添加用户的 ROLE_ADMIN 权限
insert into user_role values (10, 2);

6、访问权限接口
curl -l -H "Authorization:Bearer fcf07bf9-dc72-416f-88b3-c8d057416954" -X GET "localhost:8762/hello"
返回：hello you!