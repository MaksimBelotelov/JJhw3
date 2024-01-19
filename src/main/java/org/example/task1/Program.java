package org.example.task1;

import java.io.*;

public class Program {
    public static void main(String[] args) {

        UserData user = new UserData("Станислав", 37, "secret");

        try (FileOutputStream fileOut = new FileOutputStream("userdata.bin");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(user);
            System.out.println("Объект UserData сериализован.");

        } catch(IOException e) {
            e.printStackTrace();
        }

        try (FileInputStream fileIn = new FileInputStream("userdata.bin");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            user = (UserData)in.readObject();
            System.out.println("Объект UserData десериализован");
            System.out.println(user);

        }
        catch (IOException e) {
            e.getMessage();
        }
        catch (ClassNotFoundException e) {
            e.getMessage();
        }
    }
}
