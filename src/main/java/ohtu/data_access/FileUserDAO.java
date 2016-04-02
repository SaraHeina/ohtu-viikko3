package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ohtu.domain.User;

public class FileUserDAO implements UserDao {

    private File tiedosto;
    private Scanner lukija;
    private List<User> users;
    private FileWriter kirjottaja;

    public FileUserDAO(String filename) {
        tiedosto = new File(filename);
        users = new ArrayList<User>();
        try {
            lukija = new Scanner(tiedosto);
        } catch (FileNotFoundException e) {
            System.out.println("Error to found data file");
        }
        while (lukija.hasNext()) {
            String name = lukija.next();
            String password = lukija.next();
            User user = new User(name, password);
            users.add(user);
        }
        lukija.close();
    }

    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(User user) {
        users.add(user);
        try {
            kirjottaja = new FileWriter(tiedosto, true);
            kirjottaja.append(user.getUsername() + " " + user.getPassword() + "\n");
            kirjottaja.close();
        } catch (IOException e) {
            System.out.println("Error writing user data");
        }
    }

}
