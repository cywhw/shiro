import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * shiroMD5加密
 */
public class ShiroMD5Test {
    public static void main(String[] args) {

        //MD5加密，无随机盐，无散列（网络在线解密可解）
        Md5Hash md5Hash = new Md5Hash("123456"); //密码
        System.out.println("---------------->无随机盐，无散列，加密后: "+md5Hash.toHex());

        //MD5 + 随机盐机密，无散列（网络在线解密可解）
        Md5Hash md5Hash1 = new Md5Hash("123456","1q2w3e");//密码 ， 随即盐
        System.out.println("---------------->有随机盐，无散列，加密后: "+md5Hash1.toHex());

        //MD5 +  随即盐加密 + 散列1024（网络在线解密无解）
        Md5Hash md5Hash2 = new Md5Hash("111","1q2w3e",1024);//密码 ， 随即盐 , 散列
        System.out.println("---------------->有随机盐，有散列，加密后: "+md5Hash2.toHex());

    }
}
