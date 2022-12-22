package hillel;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

public class ThreadSafeList {
    public static void main(String[] args) {
        CopyOnWriteArraySet<Integer> arrayList = new CopyOnWriteArraySet<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);

        Runnable runnable1 = () -> {
            Iterator<Integer> iterable = arrayList.iterator();
            while (iterable.hasNext()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(iterable.next());
            }
        };

        Runnable runnable2 = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            arrayList.remove(2);
            arrayList.add(6);

            System.err.println(arrayList);
        };

        System.out.println(arrayList);
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(arrayList);
        thread1 = new Thread(runnable1);
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
