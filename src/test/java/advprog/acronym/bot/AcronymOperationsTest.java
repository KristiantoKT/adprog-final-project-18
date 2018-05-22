package advprog.acronym.bot;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertTrue;

public class AcronymOperationsTest {

    static File file;
    static FileInputStream fileInputStream;
    static String dummyInput = "NB;Notebook";
    static Acronym acronym;
    static BufferedReader bufferedReader;
    static Acronym[] acronyms;

    @Before
    public void setUp() throws IOException {
        String[] dummyInputSplit = dummyInput.split(";");
        file = File.createTempFile("temp", "csv");
        acronym = new Acronym(dummyInputSplit[0], dummyInputSplit[1]);
        acronyms = new Acronym[5];
        for (int i = 0; i < acronyms.length; i++) {
            acronyms[i] = new Acronym(Character.toString((char)('A' + i)),
                    Character.toString((char)('A' + i)).concat("KKK"));
        }
    }

    @Test
    public void testAddNewInput() throws IOException {
        for (int i = 0; i < acronyms.length; i++) {
            AcronymOperations.add(acronyms[i], file);
        }
        String y;
        boolean ketemu = false;
        fileInputStream = new FileInputStream(file);
        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        while((y = bufferedReader.readLine()) != null) {
            if(y.equals(acronyms[2].getKependekan() + ";" + acronyms[2].getKepanjangan())) {
                ketemu = true;
                break;
            }
        }
        assertTrue(ketemu);
        bufferedReader.close();
    }

    @Test
    public void testAddInputSudahAda() throws IOException {
        for (int i = 0; i < acronyms.length; i++) {
            AcronymOperations.add(acronyms[i], file);
        }
        AcronymOperations.add(acronyms[2], file);
        String y;
        boolean ketemu = false;
        fileInputStream = new FileInputStream(file);
        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        while((y = bufferedReader.readLine()) != null) {
            if(y.equals(acronyms[2].getKependekan() + ";" + acronyms[2].getKepanjangan())) {
                ketemu = true;
                break;
            }
        }
        assertTrue(ketemu);
        bufferedReader.close();
    }

    @Test
    public void testUpdate() throws IOException {
        String changedString = "Not Bad";
        AcronymOperations.add(acronym, file);
        AcronymOperations.update(acronym, changedString, file);
        String y;
        boolean ketemu = false;
        fileInputStream = new FileInputStream(file);
        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        while((y = bufferedReader.readLine()) != null) {
            if(y.equals("NB;Not Bad")) {
                ketemu = true;
                break;
            }
        }
        assertTrue(ketemu);
        bufferedReader.close();
    }

    @Test
    public void testUpdateMoreThanOne() throws IOException {
        String changedString = "Not Bad";
        AcronymOperations.add(acronyms[0], file);
        AcronymOperations.add(acronym, file);
        AcronymOperations.add(acronyms[1], file);
        AcronymOperations.update(acronym, changedString, file);
        String y;
        boolean ketemu = false;
        fileInputStream = new FileInputStream(file);
        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        while((y = bufferedReader.readLine()) != null) {
            if(y.equals("NB;Not Bad")) {
                ketemu = true;
                break;
            }
        }
        assertTrue(ketemu);
        bufferedReader.close();
    }

    @Test
    public void testDelete() throws IOException {
        AcronymOperations.add(acronym, file);
        AcronymOperations.add(acronyms[0], file);
        AcronymOperations.delete(acronym, file);
        fileInputStream = new FileInputStream(file);
        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        boolean ketemu = false;
        String y;
        while((y = bufferedReader.readLine()) != null) {
            if(y.equals(dummyInput)) {
                ketemu = true;
                break;
            }
        }
        assertTrue(!ketemu);
    }
}
