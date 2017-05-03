package pattern.singleton;


import pattern.Instance;

/**
 * @author zi.you
 * @date 17/5/3
 */
public class SingletonStaticHolder {

    private static class InstanceHolder{
        private static Instance instance = new Instance(); // 不能加final ，加了就不是延迟初始化了
    }

    public static Instance getInstance(){
        return InstanceHolder.instance;
    }
}
