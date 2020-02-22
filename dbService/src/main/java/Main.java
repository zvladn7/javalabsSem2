import dbservices.DBService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Main {
    private static void generate(DBService dbService, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; ++i) {
            sb.append("/add ").append("product").append(i).append(" ").append(i*10);
            dbService.apply(sb.toString());
            sb.delete(0, sb.length());
        }
    }

    private static int getNumberOfGeneratedProducts(final BufferedReader br, final PrintWriter out) throws IOException {
        int n = 0;
        while (true) {
            try {
                n = Integer.parseInt(br.readLine());
            } catch (NumberFormatException ex) {
                out.println("You've send the wrong format of number. Please type integer number");
                out.flush();
                continue;
            }
            break;
        }
        return n;
    }


    public static void main(String[] args) {
        try (PrintWriter out = new PrintWriter(System.out)) {
            try (DBService dbService = new DBService(out)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                out.println("Type number of firstly generated products:");
                out.flush();
                int n = getNumberOfGeneratedProducts(br, out);
                generate(dbService, n);
                out.println("The products are successfully generated!");
                out.flush();
                String next;
                while ((next = br.readLine()) != null) {
                    dbService.apply(next);
                    out.flush();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
