package main.java.basic;

public class IntegerRefTest {
    public static void main(String[] args) {
        Integer a =1;
        System.out.println("a.hashCode() :"+a.hashCode());
        a=a+1;
        System.out.println("a.hashCode() :"+a.hashCode());
    }
}
