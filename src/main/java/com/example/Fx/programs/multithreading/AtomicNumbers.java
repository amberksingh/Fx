package com.example.Fx.programs.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicNumbers {

    //Because count++ is actually:
    //
    //read
    //
    //modify
    //
    //write
    //
    //Two threads can interleave steps incorrectly → race condition.
    //
    //🚀 AtomicInteger Fix
    //
    //AtomicInteger uses CAS (Compare And Swap) → a single atomic CPU instruction.
    //
    //Operations cannot be interleaved → thread-safe without locking.

    public static void main(String[] args) {

        //AtomicInteger
        //AtomicLong
        //AtomicBoolean
        //AtomicReference
        //AtomicIntegerArray
        //AtomicLongArray

        AtomicInteger atomicInteger = new AtomicInteger(10);//10
        System.out.println("atomicInteger starting value = " + atomicInteger);//10
        System.out.println("atomicInteger.get() = " + atomicInteger.get());//10

        System.out.println("atomicInteger.getAndIncrement() = " + atomicInteger.getAndIncrement());//10 then 11
        System.out.println("atomicInteger.get() = "+atomicInteger.get());//11

        System.out.println("atomicInteger.incrementAndGet() = "+atomicInteger.incrementAndGet());//12

        System.out.println("atomicInteger.addAndGet() = "+atomicInteger.addAndGet(5));//17
        System.out.println("atomicInteger.get() = "+atomicInteger.get());//17

        System.out.println("atomicInteger.getAndAdd() = "+atomicInteger.getAndAdd(2));//17 then 19
        System.out.println("atomicInteger.get() = "+atomicInteger.get());//19

        System.out.println("atomicInteger.getAndSet() = "+atomicInteger.getAndSet(25));//19 then 25
        System.out.println("atomicInteger.get() = "+atomicInteger.get());//25

        System.out.println("atomicInteger.compareAndSet() = "+atomicInteger.compareAndSet(20, 30));//false still 25
        System.out.println("atomicInteger.get() = "+atomicInteger.get());//25

        System.out.println("atomicInteger.compareAndSet() = "+atomicInteger.compareAndSet(25, 50));//true still 50
        System.out.println("atomicInteger.get() = "+atomicInteger.get());//50

        //how cas keeps the values intact without synchronized internally?
        //ChatGPT said:
        //
        //Great — now we go one level deeper, but still beginner-friendly, and clearly explain:
        //
        //⭐ How CAS (Compare-And-Swap) keeps values correct WITHOUT using synchronized?
        //
        //This is the heart of ALL lock-free concurrency in Java.
        //
        //✅ 1. FIRST: What problem does CAS solve?
        //
        //With normal int:
        //
        //count++;   // NOT ATOMIC
        //
        //
        //Three separate steps happen:
        //
        //read value
        //
        //calculate new value
        //
        //write value
        //
        //Two threads can interleave these and overwrite each other.
        //
        //✅ 2. What does CAS do?
        //
        //CAS uses a single CPU instruction to do the 3 steps as one operation.
        //
        //You call:
        //
        //compareAndSet(expectedValue, newValue)
        //
        //
        //CPU does this:
        //
        //IF (current == expectedValue)
        //    current = newValue   // update
        //ELSE
        //    FAIL (someone else changed it)
        //
        //
        //This whole check+update happens in one atomic step at the hardware level.
        //
        //⭐ KEY IDEA:
        //CAS is performed by the CPU, not Java.
        //
        //No Java locking needed.
        //
        //Modern CPUs (x86, ARM) have built-in instructions like:
        //
        //CMPXCHG (compare-exchange)
        //
        //LL/SC (load linked / store conditional)
        //
        //These guarantee atomicity at the hardware level.
        //
        //🔥 3. Why does CAS NOT need synchronized?
        //
        //Because CPU makes sure no other thread can modify that memory location while CAS is executing.
        //
        //CAS acts like a tiny lock on ONE memory cell, but:
        //
        //Only for a nanosecond
        //
        //Without blocking other threads
        //
        //Without OS-level mutex
        //
        //Without JVM monitor locks
        //
        //So CAS is lock-free, but still atomic.
        //
        //🔍 4. CAS internally does this:
        //
        //Let’s say:
        //
        //ai.value = 10
        //
        //
        //Thread A wants to set it to 11:
        //
        //ai.compareAndSet(10, 11)
        //
        //
        //CPU does:
        //
        //Load memory location
        //
        //Compare with expected
        //
        //Swap if same
        //
        //Return success/failure
        //
        //No other thread can modify that location during this step.
        //It is protected by CPU hardware.
        //
        //🧠 5. What happens if two threads use CAS at the same time?
        //
        //Example:
        //
        //Thread A: compareAndSet(10, 11)
        //Thread B: compareAndSet(10, 15)
        //
        //
        //CPU ensures:
        //
        //One succeeds
        //
        //One fails
        //
        //No corruption
        //
        //No overwriting each other incorrectly
        //
        //If A executes first:
        //
        //ai.value = 11   (success)
        //
        //
        //Then B tries:
        //
        //current = 11 ≠ expected 10  → CAS FAILS
        //
        //
        //Thread B must retry (Java atomic classes automatically retry in a loop).
        //
        //🔁 6. How retry works (AtomicInteger uses this pattern)
        //
        //Pseudo-code for increment:
        //
        //do {
        //    oldValue = value;           // read
        //    newValue = oldValue + 1;    // compute
        //} while (!compareAndSet(oldValue, newValue));  // retry until CAS succeeds
        //
        //
        //CAS is used to ensure safe update.
        //
        //If another thread sneaks in and changes the value:
        //
        //CAS fails
        //
        //loop retries
        //
        //repeats until success
        //
        //no corruption
        //
        //⭐ 7. Why is CAS faster than synchronized?
        //synchronized:
        //
        //Heavy
        //
        //Causes blocking
        //
        //One thread at a time
        //
        //Context switching
        //
        //JVM monitor enter/exit overhead
        //
        //Possible deadlocks
        //
        //CAS:
        //
        //Non-blocking
        //
        //No waiting
        //
        //No OS involvement
        //
        //Very fast CPU instruction
        //
        //No thread suspension
        //
        //🔥 8. A SIMPLE VISUAL EXPLANATION
        //
        //Imagine a box with number 10.
        //
        //Two people (threads) want to update it.
        //
        //Normal write (count++) is like:
        //
        //Person A:
        //
        //Looks at value → 10
        //
        //Plans to change → 11
        //
        //Writes → 11
        //
        //Person B:
        //
        //Looks at value → 10
        //
        //Plans to change → 11
        //
        //Writes → 11
        //
        //Final value: 11 ❌ (wrong)
        //
        //CAS is like:
        //
        //Person A:
        //
        //“IF value is still 10, write 11, otherwise fail.”
        //
        //Person B:
        //
        //“IF value is still 10, write 11, otherwise fail.”
        //
        //If Person A wins:
        //
        //Box becomes 11
        //
        //Person B sees box ≠ 10 → retry
        //
        //Final value: 12 ✔ correct
        //
        //⭐ 9. Final One-Line Interview Answer
        //
        //“CAS ensures atomicity using a hardware instruction that compares the current value with an expected value
        // and updates it only if they match. This happens in a single, indivisible CPU operation, so no synchronized
        // is needed. If CAS fails due to another thread modifying the value, the operation retries.
        // This gives lock-free, non-blocking concurrency.”
    }
}
