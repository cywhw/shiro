import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义Realm
 */
public class CustomerRealm extends AuthorizingRealm {

    private final Logger log = LoggerFactory.getLogger(getClass());


    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
       //从token中获取用户名
        String principal =(String) token.getPrincipal();
        log.info("---------------->用户名：{}",principal);
        //模拟根据身份信息从数据库中查询
        if("admin".equals(principal)){
            //参数说明： 用户名 | 密码 | 当前realm的名字
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal,"111",this.getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }
}
