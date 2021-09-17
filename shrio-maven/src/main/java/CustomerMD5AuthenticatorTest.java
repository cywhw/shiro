import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

public class CustomerMD5AuthenticatorTest {
    public static void main(String[] args) {
        //创建SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //设置自定义的加密Realm
        CustomerMD5Realm md5Realm = new CustomerMD5Realm();
        //为md5设置凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法
        credentialsMatcher.setHashAlgorithmName("md5");
        //设置hash次数
        credentialsMatcher.setHashIterations(1024);
        md5Realm.setCredentialsMatcher(credentialsMatcher);
        securityManager.setRealm(md5Realm);
        //设置安全工具类
        SecurityUtils.setSecurityManager(securityManager);
        //通过安全工具类获取subject
        Subject subject = SecurityUtils.getSubject();
        //创建token
        UsernamePasswordToken token = new UsernamePasswordToken("root", "111");
        try{
            //登录认证
            subject.login(token);
            System.out.println("认证状态："  + subject.isAuthenticated());
        }catch (UnknownAccountException e){
            System.out.println("认证失败：用户不存在!");
        }catch (IncorrectCredentialsException e){
            System.out.println("认证失败：密码不正确!");
        }catch (LockedAccountException e){
            System.out.println("认证失败：账号被锁定!");
        }catch (DisabledAccountException e){
            System.out.println("认证失败：账号被禁用!");
        }catch (ExcessiveAttemptsException e){
            System.out.println("认证失败 ：登录失败次数过多");
        }catch (ExpiredCredentialsException e){
            System.out.println("认证失败：凭证过期");
        }

        /**
         * 授权
         */
        //判断当前subject 是否授权过？
        if(subject.isAuthenticated()){
            //基于角色权限控制
            System.out.println("--------------->是否有super这个角色"+subject.hasRole("super"));

            //基于多个角色权限控制(同时具有)
            System.out.println("--------------->是否有super和admin这两个角色"+ subject.hasAllRoles(Arrays.asList("super","admin")));

            //是否拥有其中一个角色
            boolean[] booleans = subject.hasRoles(Arrays.asList("admin","super","user"));
            for (boolean b : booleans) {
                System.out.println("是否拥有其中一个角色" + b);
            }

            System.out.println("==============================================");

            //基于权限字符串的访问控制  资源标识符:操作:资源类型
            System.out.println("权限"+subject.isPermitted("user:update:01"));
            System.out.println("权限"+subject.isPermitted("product:create:02"));

            //分别具有那些权限
            boolean[] permitted = subject.isPermitted("user:*:01","order:*:10");
            for (boolean b : permitted) {
                System.out.println("分别具有那些权限："+b);
            }

            //同时具有那些权限
            boolean  permittedAll = subject.isPermittedAll("user:*:01","product:create:01");
            System.out.println("是否同时具有这些权限："+permittedAll);

        }

    }
}
