package com.example.Fx.programs.design;

public class SingletonEnumDemo {

    //1. What is an enum?
//
//enum = enumeration, a special Java type for defining a fixed set of constants.
//
//Example: days of the week, directions, traffic lights.
//
//Enums are more powerful than simple constants because they are full-fledged classes (with fields, methods, constructors).
//
//2. Basic example
//enum Day {
//    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
//}
//
//public class EnumDemo {
//    public static void main(String[] args) {
//        Day today = Day.MONDAY;
//        System.out.println(today);  // prints "MONDAY"
//
//        // loop over all enum values
//        for (Day d : Day.values()) {
//            System.out.println(d);
//        }
//    }
//}
//
//Output
//MONDAY
//MONDAY
//TUESDAY
//WEDNESDAY
//THURSDAY
//FRIDAY
//SATURDAY
//SUNDAY
//
//3. Enum is more than constants
//
//EACH ENUM CONSTANT IS ACTUALLY A PUBLIC STATIC FINAL OBJECT OF THE ENUM TYPE.
//SO DAY.MONDAY IS AN INSTANCE OF DAY.======>Important
//
//4. Enums with fields and methods
//enum Color {
//    RED("#FF0000"),
//    GREEN("#00FF00"),
//    BLUE("#0000FF");
//
//    private final String hex;
//
//    // constructor (must be private)
//    Color(String hex) {
//        this.hex = hex;
//    }
//
//    public String getHex() {
//        return hex;
//    }
//}
//
//public class EnumDemo2 {
//    public static void main(String[] args) {
//        System.out.println(Color.RED.getHex());   // #FF0000
//    }
//}
//
//5. Useful built-in methods
//
//Every enum has:
//
//values() → returns an array of all constants.
//
//valueOf(String) → get enum constant by name.
//
//ordinal() → position (0-based index).
//
//Example:
//
//Day d = Day.valueOf("FRIDAY");
//System.out.println(d);          // FRIDAY
//System.out.println(d.ordinal()); // 4
//
//6. Enums in switch statements
//switch (today) {
//    case MONDAY -> System.out.println("Start of week!");
//    case FRIDAY -> System.out.println("Almost weekend!");
//    case SUNDAY -> System.out.println("Rest day!");
//    default -> System.out.println("Midweek day");
//}
//
//
//✅ Summary:
//
//enum = type-safe way to define fixed constants.
//
//Enums can have fields, methods, and constructors.
//
//Each enum constant is a singleton instance.
//
//Use values(), valueOf(), ordinal() for utility.
//
//Useful in switch cases, collections, and modeling fixed sets.

    enum Singleton {

        //Constructor argument strings
        //
        //"monday", "tuesday", … "friday"
        //
        //These are just string values you passed into the enum constructor.
        //
        //They are not used for valueOf. They are simply data you chose to associate with the constant.
        MONDAY("monday"),
        TUESDAY("tuesday"),
        WEDNESDAY("wednesday"),
        THURSDAY("thursday"),
        FRIDAY("friday"),
        SATURDAY("saturday"),
        SUNDAY("sunday");
        int value;
        private String dayName;

        public String getDayName() {
            return dayName;
        }

        //Runs once per constant, at class-load time.
        //
        //So when the enum class is first referenced, you’ll see:
        //Singleton constructor = monday
        //Singleton constructor = tuesday
        //...
        Singleton(String dayName) {
            this.dayName = dayName;
            System.out.println("Singleton constructor = " + this.dayName);
        }

        void setValue(int value) {

            this.value = value;
        }

        int getValue() {

            return this.value;
        }

    }

    public static void main(String[] args) {

        System.out.println("==============================");
        Singleton monday = Singleton.MONDAY;
        System.out.println("monday = " + monday);
        System.out.println("monday hashcode = " + monday.hashCode());
        System.out.println("monday dayName = " + monday.dayName);
        monday.setValue(10);
        System.out.println("monday value = " + monday.getValue());//10

        Singleton monday2 = Singleton.MONDAY;
        System.out.println("monday2 = " + monday2);
        System.out.println("monday2 hashcode = " + monday2.hashCode());
        System.out.println("monday2 dayName = " + monday2.dayName);
        System.out.println("monday2 value = " + monday2.getValue());//10

        monday2.setValue(20);
        System.out.println("monday1.getValue() = " + monday.getValue());//20
        System.out.println("monday2.getValue() = " + monday2.getValue());//20

        System.out.println("==============================");

        Singleton tuesday = Singleton.TUESDAY;
        Singleton wednesday = Singleton.WEDNESDAY;
        System.out.println("tuesday = " + tuesday.hashCode());//diff
        System.out.println("wednesday = " + wednesday.hashCode());//diff

        tuesday.setValue(40);
        System.out.println("tuesday.getValue() = " + tuesday.getValue());//40
        System.out.println("wednesday.getValue() = " + wednesday.getValue());//0

        wednesday.setValue(50);
        System.out.println("tuesday.getValue() = " + tuesday.getValue());//40
        System.out.println("wednesday.getValue() = " + wednesday.getValue());//50

        System.out.println("==============================");

        for (Singleton day : Singleton.values()) {
            System.out.println("day = " + day);
            System.out.println("day.ordinal = " + day.ordinal());
            System.out.println("day.name = " + day.name());
        }

        Singleton friday = Singleton.valueOf("FRIDAY");
        System.out.println("friday = " + friday);
        //searches for caps "FRIDAY" not constructor args small case string
        //FRIDAY should be in caps here to match enum Constant not constructor values
        //If you write "friday" (lowercase), you’ll get:
        //
        //Exception in thread "main" java.lang.IllegalArgumentException: No enum constant Singleton.friday


        System.out.println("=====================");
        //Singleton s = new Singleton("wednesday");

        //1️⃣ Why does this fail?
        //
        //Singleton s = new Singleton("wednesday");
        //
        //2️⃣ Why do all weekday constructor prints appear at start?
        //
        //Let’s go step-by-step.
        //
        //1️⃣ Why last line gives error?
        //Singleton s = new Singleton("wednesday");
        //
        //❌ Compile-time error:
        //
        //Cannot instantiate the type Singleton
        //Why?
        //
        //Because:
        //
        //👉 Enums cannot be instantiated using new.
        //
        //Even though enum looks like a class, Java internally makes it:
        //
        //final class Singleton extends java.lang.Enum<Singleton>
        //
        //Important facts:
        //
        //Enum constructors are always implicitly private
        //
        //JVM controls object creation
        //
        //You cannot create new instances
        //
        //So this is illegal:
        //
        //new Singleton(...)
        //
        //Because Java guarantees:
        //
        //Enum instances are created ONLY by JVM at class loading time.
        //
        //This is why enum is the safest Singleton implementation.
        //
        //2️⃣ Why do constructor prints appear at start?
        //
        //You saw:
        //
        //Singleton constructor = monday
        //Singleton constructor = tuesday
        //...
        //Singleton constructor = sunday
        //
        //Before you even did:
        //
        //Singleton monday = Singleton.MONDAY;
        //Why?
        //
        //Because:
        //
        //👉 Enum constants are created when the enum class is loaded.
        //
        //When JVM loads the enum class, it internally does something like:
        //
        //public static final Singleton MONDAY = new Singleton("monday");
        //public static final Singleton TUESDAY = new Singleton("tuesday");
        //...
        //
        //So at class load time:
        //
        //1️⃣ MONDAY object created → constructor runs
        //2️⃣ TUESDAY object created → constructor runs
        //3️⃣ WEDNESDAY created
        //...
        //4️⃣ SUNDAY created
        //
        //Only once.
        //
        //That’s why all constructor messages print together.
        //
        //🔥 Important Concept
        //
        //Each enum constant is:
        //
        //public static final Singleton MONDAY
        //
        //So:
        //
        //Singleton monday1 = Singleton.MONDAY;
        //Singleton monday2 = Singleton.MONDAY;
        //
        //Both point to same object.
        //
        //That’s why:
        //
        //monday.setValue(10);
        //monday2.getValue(); // 10
        //
        //Because they are same instance.
        //
        //3️⃣ Why enum is perfect Singleton?
        //
        //Because Java prevents:
        //
        //Problem	Enum prevents?
        //Reflection attack	✅ Yes
        //Cloning	✅ Yes
        //Serialization creating new instance	✅ Yes
        //Multiple instantiation	✅ Yes
        //
        //Normal singleton can be broken by:
        //
        //Reflection
        //
        //Clone
        //
        //Serialization
        //
        //Enum cannot.
        //
        //4️⃣ Why valueOf needs CAPS?
        //Singleton.valueOf("FRIDAY")
        //
        //Matches enum constant name exactly.
        //
        //NOT constructor argument.
        //
        //Constructor argument "friday" is just data stored in field.
        //
        //Enum constant name is:
        //
        //FRIDAY
        //
        //So "friday" fails.
        //
        //🎯 Final Summary
        //❌ You cannot instantiate enum using new
        //
        //Because:
        //
        //Constructor is implicitly private
        //
        //JVM controls instance creation
        //
        //Enum extends java.lang.Enum
        //
        //✅ Constructor runs once per constant
        //
        //At class load time.
        //
        //✅ Each enum constant is static final object
        //
        //Singleton per constant.
    }
}
