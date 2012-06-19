package uk.co.jacekk.bukkit.playerstats;

import java.lang.reflect.Field;

import uk.co.jacekk.bukkit.baseplugin.BaseTask;
import uk.co.jacekk.bukkit.playerstats.data.PlayerData;

public class TestTask extends BaseTask<PlayerStats> {
	
	public TestTask(PlayerStats plugin){
		super(plugin);
	}
	
	public void run(){
		PlayerData stats = plugin.playerDataManager.getDataFor("wide_load");
		
		if (stats != null){
			StringBuilder message = new StringBuilder();
			
			for (Field field : stats.getClass().getFields()){
				message.append(field.getName());
				message.append(": ");
				
				try{
					message.append(field.get(stats));
				}catch (IllegalArgumentException e){
					e.printStackTrace();
				}catch (IllegalAccessException e){
					e.printStackTrace();
				}
				
				message.append(" ");
			}
			
			System.out.println(message.toString());
		}
	}
	
}
