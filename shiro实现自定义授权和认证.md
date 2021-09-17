# Shiro自定义认证和授权

学习网址：https://blog.csdn.net/bbxylqf126com/article/details/110501155?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522163185963616780265421879%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=163185963616780265421879&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v1~rank_v31_ecpm-1-110501155.pc_search_result_cache&utm_term=shiro&spm=1018.2226.3001.4187

## 一、认证

1、用户的认证是在SimpleAccountRealm的doGetAuthenticationInfo的方法中完成的，
而SimpleAccountRealm继承自AuthorizingRealm，而AuthorizingRealm中有一个抽象方法

```java
protected abstract AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection var1);
```

SimpleAccountRealm就是复写了AuthorizingRealm中的这个抽象方法实现的用户认证，所以后面我们需要自定义认证的时候我们就可以自定义一个realm继承自AuthorizingRealm来复写doGetAuthorizationInfo，在这个方法里面实现我们自己的认证逻辑

## 二、授权

`AuthorizingRealm`是继承自`AuthenticatingRealm`，而AuthenticatingRealm中有个抽象方法

```java
protected abstract AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken var1) throws AuthenticationException;
```

这个方法是实现用户授权的方法。

也就是说通过我们自定义realm继承`AuthorizingRealm`就可以同时复写认证和授权两个方法

### 1、Shiro中的授权

什么是授权
授权可简单理解为who对what(which)进行How操作：

==Who，即主体（Subject）==，主体需要访问系统中的资源。

==What，即资源（Resource)==，如系统菜单、页面、按钮、类方法、系统商品信息等。资源包括==资源类型==和==资源实例==，比如==商品信息为资源类型==，类型为t01的商品为==资源实例==，编号为001的商品信息也属于资源实例。

==How，权限/许可（Permission)==，规定了主体对资源的操作许可，权限离开资源没有意义，如用户查询权限、用户添加权限、某个类方法的调用权限、编号为001用户的修改权限等，通过权限可知主体对哪些资源都有哪些操作许可。



### 2、授权方式

==基于角色的访问控制==
RBAC基于角色的访问控制（Role-Based Access Control）是以角色为中心进行访问控制

```java
if(subject.hasRole("admin")){
   //操作什么资源
}
```


==基于资源的访问控制==
RBAC基于资源的访问控制（Resource-Based Access Control）是以资源为中心进行访问控制

```java
if(subject.isPermission("user:update:01")){ //资源实例
  //对01用户进行修改
}
if(subject.isPermission("user:update:*")){  //资源类型
  //对01用户进行修改
}


```

### 3、权限字符串

==权限字符串的规则是：资源标识符：操作：资源实例标识符，意思是对哪个资源的哪个实例具有什么操作，“:”是资源/操作/实例的分割符，权限字符串也可以使用==

通配符。

例子：

用户创建权限：user:create，或user:create:*
用户修改实例001的权限：user:update:001
用户实例001的所有权限：user:*：001



### 4、权限的编码方式

#### 编程式

```java
Subject subject = SecurityUtils.getSubject();
if(subject.hasRole("admin")) {
	//有权限
} else {
	//无权限
}
```

#### 注解式

```java
@RequiresRoles("admin")
public void hello() {
	//有权限
}
```

#### 标签式

```jsp
JSP/GSP 标签：在JSP/GSP 页面通过相应的标签完成：
<shiro:hasRole name="admin">
	<!— 有权限—>
</shiro:hasRole>
注意: Thymeleaf 中使用shiro需要额外集成!
```





## 三、盐加密

Shiro中密码的加密策略
实际应用中用户的密码并不是明文存储在数据库中的，而是采用一种加密算法将密码加密后存储在数据库中。Shiro中提供了一整套的加密算法，并且提供了随机盐。shiro使用指定的加密算法将用户密码和随机盐进行加密，并按照指定的散列次数将散列后的密码存储在数据库中。由于随机盐每个用户可以不同，这就极大的提高了密码的安全性。
