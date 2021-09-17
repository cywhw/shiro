import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * 自定义Realm 使用 MD5 加密
 */
public class CustomerMD5Realm extends AuthorizingRealm{

    private final Logger log = LoggerFactory.getLogger(getClass());

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从系统返回的身份信息集合中获取主身份信息（用户名）
        String primaryPirncipal = (String) principalCollection.getPrimaryPrincipal();
        log.info("---------------> 用户名：{}",primaryPirncipal);

        //根据用户名获取当前用户的角色信息，以及权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //将数据库中查询的角色信息赋值给权限对象
        simpleAuthorizationInfo.addRole("user");
        simpleAuthorizationInfo.addRole("admin");

        //将数据库中查询权限信息赋值给权限对象
        simpleAuthorizationInfo.addStringPermission("user:*:01");
        simpleAuthorizationInfo.addStringPermission("product:create");

        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从token中获取用户名
        String principal = (String) token.getPrincipal();
        log.info("----------> 当前用户名：{}",principal);
        if("root".equals(principal)){
            /**
             * 用户名
             * 加密后密码
             * 随即盐
             * 当前realm的名称
             * (双重加密，哭了。。。。)
             */
            Md5Hash md5Hash = new Md5Hash("111","1q2w3e",1024);
            return new SimpleAuthenticationInfo(principal,
                    md5Hash.toHex(),
                    ByteSource.Util.bytes("1q2w3e"),
                    this.getName());
        }
        return null;
    }
}
