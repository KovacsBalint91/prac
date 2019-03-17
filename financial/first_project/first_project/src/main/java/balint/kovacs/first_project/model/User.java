package balint.kovacs.first_project.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long id;
    private String username;
    private String name;
    private String password;
    private String confirmPassword;
    private List<Income> incomes = new ArrayList<>();
    private List<Spend> spendList = new ArrayList<>();
    private Role role;
    private long wallet;

    public User(){
    }

    public User(String username, String password, String confirmPassword){
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public User(long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public User(String username, String name, String password, String confirmPassword) {
        this(username, name, password);
        this.confirmPassword = confirmPassword;
    }

    public User(long id, String username, String name, Role role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public User(long id, String username, String name, String password, Role role) {
        this(id,username,name,role);
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    public List<Spend> getSpendList() {
        return spendList;
    }

    public void setSpendList(List<Spend> spendList) {
        this.spendList = spendList;
    }

    public Role getRole() {
        return role;
    }

    public long getWallet() {
        return wallet;
    }

    public void setWallet(long wallet) {
        this.wallet = wallet;
    }
}
