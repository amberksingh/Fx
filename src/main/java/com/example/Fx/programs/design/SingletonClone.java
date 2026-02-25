package com.example.Fx.programs.design;

class PerfumeClone implements Cloneable {
    
    private static PerfumeClone perfumeClone;
    
    private PerfumeClone() {
        System.out.println("inside constructor..");
    }

    public static PerfumeClone getInstance() {
        System.out.println("inside getInstance()..");
        if (perfumeClone == null) {
            perfumeClone = new PerfumeClone();
        }
//        PerfumeClone copy = (PerfumeClone) perfumeClone.clone();//works well
//        System.out.println("copy.hashCode() = " + copy.hashCode());//no error
        return perfumeClone;
    }

    //Protected in different package is only accessible inside subclass — and only through subclass reference.
    //PREVENT CLONING
    //“Object.clone() is protected, so subclasses can call it only by overriding it.
    //Even though every class extends Object, protected methods of a superclass are not accessible on subclass
    //instances unless the subclass exposes them.
    //Therefore, you cannot call clone() unless the class overrides it and makes it accessible.”
    @Override
    protected PerfumeClone clone() throws CloneNotSupportedException {
        System.out.println("inside clone() of PerfumeClone class");
//        PerfumeClone clone = (PerfumeClone)super.clone();
//        return clone;
        throw new CloneNotSupportedException("Cannot clone Singleton");
    }
}

public class SingletonClone /*implements Cloneable*/ {

    public static void main(String[] args) throws CloneNotSupportedException {
        
        PerfumeClone perfumeClone1 = PerfumeClone.getInstance();
        System.out.println("perfumeClone1.hashCode() = " + perfumeClone1.hashCode());

        PerfumeClone perfumeClone2 = perfumeClone1.clone();
        System.out.println("perfumeClone2.hashCode() = " + perfumeClone2.hashCode());

        //rough
//        SingletonClone s = new SingletonClone();
//        System.out.println("s.hashCode() = " + s.hashCode());
//        SingletonClone clone1 = (SingletonClone)s.clone();
    }
}
