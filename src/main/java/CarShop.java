import java.util.ArrayList;
import java.util.List;

public class CarShop {

    private static final int DELIVERY_TIME = 2000;
    private final int BUY_TIME = 500;
    private final int BATCH_CARS = 10;
    private final List<Car> cars = new ArrayList<>();

    public void receiveCar() {
        for (int i = 0; i < BATCH_CARS; i++) {
            try {
                Thread.sleep(DELIVERY_TIME);
                cars.add(new Car());
                System.out.println(Thread.currentThread().getName() + " выпустил 1 авто");
                synchronized (this) {
                    notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void sellCar() {
        try {
            System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
            while (cars.isEmpty()) {
                System.out.println("Машин нет");
                wait();
            }
            Thread.sleep(BUY_TIME);
            System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто");
            cars.remove(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
