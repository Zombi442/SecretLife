package me.zombi42.secretlife.Util;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

public class Button extends Location {
    ButtonType buttonType;

    public Button(@Nullable World world, double x, double y, double z, ButtonType buttonType) {
        super(world, x, y, z);
        this.buttonType = buttonType;
    }

    public Button(Location location, ButtonType buttonType){
        super(location.getWorld(), location.getX(), location.getY(), location.getZ());
        this.buttonType = buttonType;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    public void setButtonType(ButtonType buttonType) {
        this.buttonType = buttonType;
    }

    public Location getLocation(){
        return new Location(getWorld(), getX(), getY(), getZ());
    }
}

