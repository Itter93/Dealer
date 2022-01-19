public class Main {
    public static final int BUYERS = 10;

    public static void main(String[] args) {
        final CarShop carShop = new CarShop();

        for (int i = 1; i <= BUYERS; i++) {
            new Thread(null, carShop::sellCar, "Покупатель " + i).start();
        }
        new Thread(null, carShop::receiveCar, "Производитель Toyota ").start();
    }
}
