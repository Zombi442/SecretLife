/*
This file is part of SecretLife.
https://github.com/Zombi442

SecretLife is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

SecretLife is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with SecretLife.
If not, see <https://www.gnu.org/licenses/>.
 */
package me.zombi42.secretlife.Util;

import me.zombi42.secretlife.Enum.ButtonType;
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

