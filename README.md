
register-server: 注册中心
config-server: 配置中心
api-gateway: api网关
auth-server: 未使用（oauth2测试）
zauth-server: 
  1. jwt token获取与刷新
  2. 获取jwt token时的用户校验是通过MyUserDetailServiceImpl做的，若要更改自行实现UserDetailsService接口

common包：
  1. 加入jwt权限控制，所有引入common包的满足：/api/**/sec/**形式的api都将加入校验
  2. 开启了方法级的校验，使用@PreAuthorize("hasRole('ADMIN')")