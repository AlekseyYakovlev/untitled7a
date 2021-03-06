package ru.geekbrains.homework5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    public int getSpeed() {
        return speed;
    }
    private String name;
    public String getName() {
        return name;
    }

    public static CyclicBarrier readyCountDown;
    public static CountDownLatch inRaceCount;
    public static void setCounters() {
        Car.readyCountDown = new CyclicBarrier(CARS_COUNT+1);
        Car.inRaceCount = new CountDownLatch(CARS_COUNT+1);
        Tunnel.setSemaphore(CARS_COUNT);
    }




    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            readyCountDown.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);

        }
        inRaceCount.countDown();
    }
}

