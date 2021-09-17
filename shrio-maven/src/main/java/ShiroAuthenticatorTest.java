import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

/**
 * shiro基本实现
 */
public class ShiroAuthenticatorTest {

    public static void main(String[] args) {
        //1、创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2、给安全管理器设置realms  这里来读取配置文件中设置的用户
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));
        //3、给全局安全工具类securityUtils设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //4、拿到当前的subject 指获取当前操作的用户(泛指：不规定一定是用户)的信息
        Subject subject = SecurityUtils.getSubject();
        //5、创建令牌  参数是用户输入用户名和密码，用来和你提前在配置文件中设置的用来比对的
        AuthenticationToken token = new UsernamePasswordToken("admin","111");
        try{
            //6、用户认证
            System.out.println("认证状态："  + subject.isAuthenticated());
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
    }
}
