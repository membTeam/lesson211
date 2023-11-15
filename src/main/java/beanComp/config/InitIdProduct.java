package beanComp.config;

public class InitIdProduct {
    private static int idStart = 100;
    public static int initId() {
        return ++idStart;
    }
}
