package hash.code.io;

import java.io.File;
import java.io.PrintWriter;

public class Writer {
    private String outPath;

    public Writer(String name, String[] ingradients) {
        outPath = getOutputFileFullName(name);
        write(ingradients);
    }

    private void write(String[] ingradients) {
        File out = new File(outPath);
        try {
            PrintWriter writer = new PrintWriter(out);
            writer.print(ingradients.length + " ");
            for (String ingradient : ingradients) {
                writer.println(ingradient + " ");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getOutputFileFullName(String name) {
        return "output\\" + name + ".out.txt";
    }
}
