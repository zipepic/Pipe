package podpivasniki.shortfy.site.branchedpipeline;

public class ClassSingleton {

    private static ClassSingleton INSTANCE;

    public static ClassSingleton getInstance(){
        return Single.getInstance();
    }

    private static class Single{

        static {
            System.out.println("Hi");
        }
        public static ClassSingleton getInstance(){
            return new ClassSingleton();
        }
    }

}
