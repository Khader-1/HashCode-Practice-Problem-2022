package hash.code;

import java.io.IOException;

import hash.code.io.Reader;
import hash.code.resources.Manager;

public class App {
    private static String[] names = new String[] {
            "a_an_example", 
            "b_basic", 
            "c_coarse", 
            "d_difficult",
            "e_elaborate",
         };

    public static void main(String[] args) throws IOException {

        for (final String name : names) {
            Thread thread = new Thread() {
                public void run() {
                    new Reader(name) {
                        private Manager manager;

                        @Override
                        public void onSizeRead(int size) {
                            manager = new Manager(size, name);
                        }

                        @Override
                        public void onCustomerRead(int index, String[] likes, String[] dislikes) {
                            manager.addCustomer(index, likes, dislikes);
                        }

                        @Override
                        public void onDone() {
                            manager.compute();
                        }
                    };
                }
            };
            thread.start();

        }
    }
}
