package pattern.singleton;


import pattern.Instance;

/**
 * @author zi.you
 * @date 17/5/3
 */
public class SingletonDcl {

    private static volatile Instance instance;

    public static Instance getInstance(){
        if (instance == null) {
            synchronized (SingletonDcl.class) {
                if (instance == null)
                    instance = new Instance();
            }
        }
        return instance;
    }
}
