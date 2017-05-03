package pattern.singleton;


import pattern.Instance;

/**
 * @author zi.you
 * @date 17/5/3
 */
public class SingletonStaticHolder {

    private static class InstanceHolder{
        private static Instance instance = new Instance();
    }

    public static Instance getInstance(){
        return InstanceHolder.instance;
    }
}
