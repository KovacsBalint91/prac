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

    public static class Builder {

        private long id;
        private String username;
        private String name;
        private String password;
        private String confirmPassword;
        private List<Income> incomes = new ArrayList<>();
        private List<Spend> spendList = new ArrayList<>();
        private Role role;
        private long wallet;

        public Builder id(long id){
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder confirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        public Builder incomes(List<Income> incomes){
            this.incomes = incomes;
            return this;
        }

        public Builder spendList(List<Spend> spendList){
            this.spendList = spendList;
            return this;
        }

        public Builder role(Role role){
            this.role = role;
            return this;
        }

        public Builder wallet(long wallet){
            this.wallet = wallet;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    private User(Builder builder){
        id = builder.id;
        name = builder.name;
        username = builder.username;
        password = builder.password;
        confirmPassword = builder.confirmPassword;
        incomes = builder.incomes;
        spendList = builder.spendList;
        role = builder.role;
        wallet = builder.wallet;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public long getWallet() {
        return wallet;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", incomes=" + incomes +
                ", spendList=" + spendList +
                ", role=" + role +
                ", wallet=" + wallet +
                '}';
    }
}
