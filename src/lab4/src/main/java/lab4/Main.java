package main.java.lab4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        try (PrintWriter out = new PrintWriter(System.out)) {
            try (DBService dbService = new DBService(out)) { ;
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
