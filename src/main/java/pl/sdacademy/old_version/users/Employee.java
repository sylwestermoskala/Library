package pl.sdacademy.old_version.users;

public class Employee extends User{

    public Employee(int id, String firstName, String lastName, String login, String password, Permission permission) {
        super(id, firstName, lastName, login, password);
        this.permission = permission;
    }

    public Employee() {

    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }


}
