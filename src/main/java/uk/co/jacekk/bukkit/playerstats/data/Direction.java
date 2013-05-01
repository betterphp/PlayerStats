package uk.co.jacekk.bukkit.playerstats.data;

import org.bukkit.Location;

public class Direction {
	
	private float pitch;
	private float yaw;
	
	public Direction(Location location){
		this.pitch = location.getPitch();
		this.yaw = location.getYaw();
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof Direction){
			Direction direction = (Direction) object;
			
			return (this.pitch == direction.pitch && this.yaw == direction.yaw);
		}else if (object instanceof Location){
			Location location = (Location) object;
			
			return (this.pitch == location.getPitch() && this.yaw == location.getYaw());
		}
		
		return false;
	}
	
	@Override
	public int hashCode(){
		return (int) (this.pitch + (this.yaw * 1000));
	}
	
}
