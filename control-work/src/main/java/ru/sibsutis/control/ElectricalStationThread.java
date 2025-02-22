package ru.sibsutis.control;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;

@Getter
@Setter
public class ElectricalStationThread implements Runnable {

    private List<Exchanger<Integer>> exchangers;
    private int fuel;
    private int threadNumber;

    public ElectricalStationThread(List<Exchanger<Integer>> exchangers, int fuel, int threadNumber) {
        this.exchangers = exchangers;
        this.fuel = fuel;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        try {
            while (fuel > 0) {
                fuel -= 10;
            }
            int random = new Random().nextInt(10000);
            System.out.println("[ElectricalStation" + threadNumber + "] Time for spending all the fuel: " + random);
            Thread.sleep(random);
            System.out.println("[ElectricalStation" + threadNumber + "] I don't have any fuel: " + fuel);

            Thread.sleep(1000);

            fuel = exchangers.get(threadNumber - 1).exchange(fuel);

            System.out.println("[ElectricalStation" + threadNumber + "] Got the fuel: " + fuel);
        }
        catch (InterruptedException ex){
            System.out.println(ex.getMessage());
        }
    }
}
