package hillel;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class PetrolStation {
    private static double fullGalone = 1000.0;

    public PetrolStation(double fullGalone) {
        this.fullGalone = fullGalone;
    }

    public double getFullGalone() {
        return fullGalone;
    }

    public void setFullGalone(double fullGalone) {
        this.fullGalone = fullGalone;
    }

    @Override
    public String toString() {
        return "PetrolStation{" +
                "fullGalone=" + fullGalone +
                '}';
    }

    public static double doRefuel (double gallon){
        return fullGalone -= gallon;
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        Scanner scr = new Scanner(System.in);
        Runnable runnable = () -> {
            try {
                System.out.printf("Refueling start %s.\n", Thread.currentThread().getName());

                semaphore.acquire();
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
        };
        Thread myThread1 = new Thread(runnable, "Car 1");
        Thread myThread2 = new Thread(runnable, "Car 2");
        Thread myThread3 = new Thread(runnable, "Car 3");

        myThread1.start();
        System.out.println("How much gasoline do you want: ");
        double gallon = scr.nextDouble();
        doRefuel(gallon);

        myThread2.start();
        System.out.println("How much gasoline do you want: ");
        double gallon1 = scr.nextDouble();
        doRefuel(gallon1);

        myThread3.start();
        System.out.println("How much gasoline do you want: ");
        double gallon2 = scr.nextDouble();
        doRefuel(gallon2);

        System.out.println("All gasoline left: " + fullGalone);
    }

}
