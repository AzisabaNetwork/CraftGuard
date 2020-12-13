package amata1219.craft.guard;

public enum Permission {

    ADMIN("craft.guard.admin");

    public final String permission;

    private Permission(String permission) {
        this.permission = permission;
    }

}
