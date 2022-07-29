import java.io.*;
import java.lang.String;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

public class ObjectEncrypt {
    //
    // Store object in a file for future use.
    //

    private static String readFromFile(String filename) throws Exception {
        FileInputStream fin = null;
        int i;
        String arr = "";
        try {
            fin = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("array index out of bound");
        }
        try {
            do {
                i = fin.read();
                if (i != -1) {
                    arr = arr + (char) i;
                }
            } while (i != -1);
        } catch (IOException e) {
            System.out.println("File Error");
        }
        return arr;
    }

    private static void writeToFile(String filename, Object object) throws Exception {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(new File(filename));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    public static void main(String args[]) throws Exception, IOException {
        int i;
        String arr = "";
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(args[0]);

        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            return;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("array index out of bound");
        }
        try {
            do {
                i = fin.read();
                if (i != -1) {
                    arr = arr + (char) i;
                }
            } while (i != -1);
        } catch (IOException e) {
            System.out.println("File Error");
        }
        System.out.println("your content are " + arr);

        //
        // Generating a temporary key and stire it in a file.
        //
        SecretKey key = KeyGenerator.getInstance("DES").generateKey();
        writeToFile("secretkey.txt", key);

        //
        // Preparing Cipher object for encryption.
        //
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        //
        // Here we seal (encrypt) a simple string message (a string object).
        //
        SealedObject sealedObject = new SealedObject(arr, cipher);
        System.out.println(sealedObject);
        //
        // Write the object out as a binary file.
        //
        writeToFile("sealed.txt", sealedObject);
        System.out.println("your encrypted file is \n" + readFromFile("sealed.txt"));
        fin.close();
    }
}
