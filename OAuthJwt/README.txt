使用说明：
1、关于密钥生成
Jwt需要使用jks文件作为Token加密密钥，在终端输入以下命令：
keytool -genkeypair -alias fzp-jwt -validity 3650 -keyalg RSA -dname "CN=jwt, OU=jtw,O=jtw,L=zurich,S=zurich,C=CH" -keypass pwd123 -keystore fzp-jwt.jks -storepass pwd123
其中： fzp-jwt为名称，如果需要修改名称以及密码，需要修改程序：user-oauth-service中Oauth2Config中 jwtTokenEnhancer 函数中对应的名称和密码
       -validity 为过期时间，默认单位为天
       -keyalg 密钥算法，设置为RSA
       -dname 唯一判别名
       -keypass 密钥口令
       -keystore 密钥库名称，即保存文件名称
       -storepass 密钥库口令
2、解密密钥库获取公钥
keytool -list－ rfc --keystore fzp-jwt.jks | openssl x509 - inform pem -pubkey
将获取的公钥保存为public.cerb, 和之前生成的fzp-jwt.jks一并复制至resources目录下
如果名称有改动，需要修改：user-oauth-service//Oauth2Config类jwtTokenEnhancer中的文件名称

3、具体微服务(user-service)中密钥使用：
拷贝public.cerb到resources目录下，如果名称有改动，修改 config/JwtConfig类中jwtTokenEnhancer函数中的文件名

4、数据库生成
创建名称为oauth_jwt的数据库，导入 db.sql，生成原始数据库
修改user-oauth-service和user-service的application.yml中数据库配置部分，设置数据库名、用户名、密码

5、具体测试：
先启动Eureka-Server，在启动另外两个微服务

添加用户: 用户名wang 密码123456
curl -d "username=wang&password=123456" "localhost:9090/user/register"
获取结果为：
{"id":7,"username":"wang","password":"$2a$10$luV.j1rGTds9KFbW3c6Doe1TPww7Eb1bonOnYJSTxAyOzsjYAMyMa",
"authorities":null,"enabled":true,"accountNonExpired":true,"accountNonLocked":true,
"credentialsNonExpired":true}

获取用户的token：
curl user-service:123456@localhost:9008/oauth/token -d grant_type=password -d username=wang -d password=123456
结果为：
{"access_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzI1OTcxNTksInVzZXJfbmFtZSI6IndhbmciLCJqdGkiOiIzMWY0YTljZS1hNWQ5LTRjNDQtYWRlOS0yMDY4YzY0N2U3NWEiLCJjbGllbnRfaWQiOiJ1c2VyLXNlcnZpY2UiLCJzY29wZSI6WyJzZXJ2aWNlIl19.LAG-_apIp38o2N8uYN3hZC9qRxZrMGZGXpz3UpFqPCdj6oEU8HQ78t4lHv7GFczl05GMqcDubxTgoHNGWwzGtN_FROMXGuXpeQeMpteLOZkKYSmE_X7V0ZxFXwZis_eSd4QoVcEwVx3aePVRQnaqlBmWXg4oPussfy7k31boYRwCvQfibNdv-cNVNf_7a89q4gc-hHyowBkfEo-nOKqQ9ygEbRLWWHcWRzis18EZaIMrLxo25CapcbtwKX11EX9RfmHVXNa_nE_3EdfF3b2l7c9g-iWlrR_PWtoKkwh3GYLFNvevnHG_rFi7pda7y4cV--hEDehgk6QfdWDA3VnfPA",
"token_type":"bearer","refresh_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzUxODU1NTksInVzZXJfbmFtZSI6IndhbmciLCJqdGkiOiJmYjY5ZjlkNC05YmM4LTQ0NzAtODg2NS05MzE0Y2Y4YTdmNzciLCJjbGllbnRfaWQiOiJ1c2VyLXNlcnZpY2UiLCJzY29wZSI6WyJzZXJ2aWNlIl0sImF0aSI6IjMxZjRhOWNlLWE1ZDktNGM0NC1hZGU5LTIwNjhjNjQ3ZTc1YSJ9.mJ1GsGaX5adJ799z-VgXch1OV61aV3jqgsyjPKN20lqsUuurQYxty-EHZcJ30m9a-1nny6zbjSi37yJwo12Dkjd1o0iRYKMP4Ag7z27pjLsoGajGIrztVDUOVnCRlvPbueYP5lI8JuEkyhXKIaKb9JafaoBU2kGM1asBleMrOgxrf-e142cyQzk-Sm0R4tw4PY7GSYs9jSEUOOJOwsnRbL-URivnhHjvSh9Kjfc7pnret3V3whtoTJzvlzfPkVd7qhgVxOD02iGXp5ZmTfnuOmUtxl1BQsW02Rgk_eLDaZhDE0kKcgFfZQ1Z9KtyogVQlx5uQCq7rbppJK0vC1JmHw",
"expires_in":3599,"scope":"service","jti":"31f4a9ce-a5d9-4c44-ade9-2068c647e75a"}

发送请求：
curl -l -H "Authorization:Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzI1OTcxNTksInVzZXJfbmFtZSI6IndhbmciLCJqdGkiOiIzMWY0YTljZS1hNWQ5LTRjNDQtYWRlOS0yMDY4YzY0N2U3NWEiLCJjbGllbnRfaWQiOiJ1c2VyLXNlcnZpY2UiLCJzY29wZSI6WyJzZXJ2aWNlIl19.LAG-_apIp38o2N8uYN3hZC9qRxZrMGZGXpz3UpFqPCdj6oEU8HQ78t4lHv7GFczl05GMqcDubxTgoHNGWwzGtN_FROMXGuXpeQeMpteLOZkKYSmE_X7V0ZxFXwZis_eSd4QoVcEwVx3aePVRQnaqlBmWXg4oPussfy7k31boYRwCvQfibNdv-cNVNf_7a89q4gc-hHyowBkfEo-nOKqQ9ygEbRLWWHcWRzis18EZaIMrLxo25CapcbtwKX11EX9RfmHVXNa_nE_3EdfF3b2l7c9g-iWlrR_PWtoKkwh3GYLFNvevnHG_rFi7pda7y4cV--hEDehgk6QfdWDA3VnfPA" -X GET "localhost:9090/restest"
以上请求实为： curl -l -H "获取用户的token结果中的access_token" -X GET "localhost:9090/restest"
结果为：
{"error":"access_denied","error_description":"不允许访问"}
原因： user-service服务web/WebController中@PreAuthorize("hasAuthority('ROLE_ADMIN')")限定权限

数据库中添加权限：
insert into user_role values(用户Id， 2)  2为role表中ROLE_ADMIN的ID

发送请求：
curl -l -H "Authorization:Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzI1OTcxNTksInVzZXJfbmFtZSI6IndhbmciLCJqdGkiOiIzMWY0YTljZS1hNWQ5LTRjNDQtYWRlOS0yMDY4YzY0N2U3NWEiLCJjbGllbnRfaWQiOiJ1c2VyLXNlcnZpY2UiLCJzY29wZSI6WyJzZXJ2aWNlIl19.LAG-_apIp38o2N8uYN3hZC9qRxZrMGZGXpz3UpFqPCdj6oEU8HQ78t4lHv7GFczl05GMqcDubxTgoHNGWwzGtN_FROMXGuXpeQeMpteLOZkKYSmE_X7V0ZxFXwZis_eSd4QoVcEwVx3aePVRQnaqlBmWXg4oPussfy7k31boYRwCvQfibNdv-cNVNf_7a89q4gc-hHyowBkfEo-nOKqQ9ygEbRLWWHcWRzis18EZaIMrLxo25CapcbtwKX11EX9RfmHVXNa_nE_3EdfF3b2l7c9g-iWlrR_PWtoKkwh3GYLFNvevnHG_rFi7pda7y4cV--hEDehgk6QfdWDA3VnfPA" -X GET "localhost:9090/foo"
以上请求实为： curl -l -H "获取用户的token结果中的access_token" -X GET "localhost:9090/restest"
结果为：hello, restest, b258a864-b576-420a-aa95-b6e4e44bf61a
/web/WebController的正确响应。


关于后期整合：
1、user-oauth-service提供验证
2、向其他微服务请求时，先获取用户token，附加到请求中，发送实际请求，在微服务中进行token校验
3、在具体微服务中整合Feign，处理负载均衡以及熔断。