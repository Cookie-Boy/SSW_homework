package ru.sibsutis.control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class Main {
    public static void main(String[] args) {
        List<Exchanger<Integer>> exchangers = new ArrayList<>(List.of(new Exchanger<>(), new Exchanger<>()));
        new Thread(new ElectricalStationThread(exchangers, 100, 1)).start();
        new Thread(new ElectricalStationThread(exchangers, 100, 2)).start();
        new Thread(new TransportThread(exchangers, new Storage())).start();
    }
}