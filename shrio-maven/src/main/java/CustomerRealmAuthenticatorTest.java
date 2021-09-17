import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * 测试自定义realm实现认证
 */
public class CustomerRealmAuthenticatorTest {
    public static void main(String[] args) {
        //创建securityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //设置自定义realm
        securityManager.setRealm(new CustomerRealm());
        //设置安全工具类
        SecurityUtils.setSecurityManager(securityManager);
        //通过安全工具类获取Subject
        Subject subject = SecurityUtils.getSubject();
        //创建token  //这里模拟根据用户输入的用户信息和你自定义的用户信息是否一致
        UsernamePasswordToken token = new UsernamePasswordToken("root","123");
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
    }
}
