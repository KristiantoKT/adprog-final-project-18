package advprog.acronym.bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AcronymOperations {

    public static void add(Acronym acronym, File file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file, true)));
        String yangDicari = acronym.getKependekan() + ";" + acronym.getKepanjangan();
        String i;
        boolean ketemu = false;
        while ((i = br.readLine()) != null) {
            String[] a = i.split(";");
            if (acronym.getKependekan().equals(a[0])) {
                ketemu = true;
                break;
            }
        }
        if (!ketemu) {
            bw.append(yangDicari + "\n");
        }
        br.close();
        bw.close();
        return;
    }

    public static void update(Acronym acronym, String newKepanjangan, File file)
            throws IOException {
        File fileBaru;
        File dir = new File(file.getParent());
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(
                        fileBaru = new File(
                                File.createTempFile(
                                        "asdf", "asdf", dir)
                                        .getPath()))));
        String i;
        acronym.setKepanjangan(newKepanjangan);
        while ((i = br.readLine()) != null) {
            String[] a = i.split(";");
            if (acronym.getKependekan().equals(a[0])) {
                bw.write(a[0] + ";" + newKepanjangan + "\n");
                continue;
            }
            bw.write(i + "\n");
        }
        bw.close();
        br.close();
        boolean filedelete = file.delete();
        boolean berhasil = fileBaru.renameTo(file);;

        System.out.println(berhasil + " " + filedelete);
        return;
    }

    public static void delete(Acronym acronym, File file)
            throws IOException {
        File fileBaru;
        File dir = new File(file.getParent());
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(
                        fileBaru = new File(
                                File.createTempFile(
                                        "asdf", "asdf", dir)
                                        .getPath()))));
        String i;
        while ((i = br.readLine()) != null) {
            String[] a = i.split(";");
            if (acronym.getKependekan().equals(a[0])) {
                continue;
            }
            bw.write(i + "\n");
        }
        bw.close();
        br.close();
        boolean filedelete = file.delete();
        boolean berhasil = fileBaru.renameTo(file);

        System.out.println(berhasil + " " + filedelete);
        return;
    }

    public static ArrayList<Acronym> addToArrayList(File file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String i;
        ArrayList<Acronym> arrayListBaru = new ArrayList<>();
        while ((i = br.readLine()) != null) {
            String[] isplit = i.split(";");
            Acronym acronymBaru = new Acronym(isplit[0], isplit[1]);
            arrayListBaru.add(acronymBaru);
        }

        return arrayListBaru;
    }
}
