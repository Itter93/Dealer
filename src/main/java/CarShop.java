import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarShop {

    private static final int DELIVERY_TIME = 2000;
    private final int BUY_TIME = 500;
    private final int BATCH_CARS = 10;
    private final List<Car> cars = new ArrayList<>();
    private Lock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();

    public void receiveCar() {
        for (int i = 0; i < BATCH_CARS; i++) {
            try {
                Thread.sleep(DELIVERY_TIME);
                cars.add(new Car());
                System.out.println(Thread.currentThread().getName() + " выпустил 1 авто");
                lock.lock();
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    public void sellCar() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " зашел в автосалон");
            while (cars.isEmpty()) {
                System.out.println("Машин нет");
                condition.await();
            }
            Thread.sleep(BUY_TIME);
            System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто");
            cars.remove(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
}
