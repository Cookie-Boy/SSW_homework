package ru.sibsutis.control;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.Exchanger;

@Getter
@Setter
public class TransportThread implements Runnable {

    private List<Exchanger<Integer>> exchangers;
    private Storage storage;

    public TransportThread(List<Exchanger<Integer>> exchangers, Storage storage) {
        this.exchangers = exchangers;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            int fuel = storage.getFuel();
            System.out.println("[Transport] Got the fuel!");
            Thread.sleep(5000);
            for (Exchanger<Integer> exchanger : exchangers) {
                exchanger.exchange(fuel);
            }
        }
        catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
