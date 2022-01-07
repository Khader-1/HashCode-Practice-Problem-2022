package hash.code.io;

import java.io.File;
import java.util.Scanner;

public abstract class Reader {
    private final String inPath;

    public Reader(String name) {
        this.inPath = getInputFileFullName(name);
        read();
    }

    private String getInputFileFullName(String name) {
        return "input\\" + name + ".in.txt";
    }

    private void read() {
        File in = new File(inPath);
        try {
            Scanner reader = new Scanner(in);
            int size = reader.nextInt();
            onSizeRead(size);
            for (int i = 0; i < size; i++) {
                String[] likes = onIngradientsRead(reader.nextInt(), reader);
                String[] dislikes = onIngradientsRead(reader.nextInt(), reader);
                onCustomerRead(i, likes, dislikes);
            }
            onDone();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] onIngradientsRead(int size, Scanner reader) {
        String[] ingradients = new String[size];
        for (int i = 0; i < size; i++) {
            ingradients[i] = reader.next();
        }
        return ingradients;
    }

    protected void onSizeRead(int size) {
    }

    public abstract void onCustomerRead(int index, String[] likes, String[] dislikes);

    public abstract void onDone();
}
