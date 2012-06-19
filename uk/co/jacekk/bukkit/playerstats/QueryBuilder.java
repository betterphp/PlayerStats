package uk.co.jacekk.bukkit.playerstats;

import java.util.ArrayList;
import java.util.Map.Entry;

import uk.co.jacekk.bukkit.playerstats.data.PlayerData;

public class QueryBuilder {
	
	public static String updatePlayers(ArrayList<Entry<String, PlayerData>> entries){
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO `stats_players` (`player_name`, `player_first_join`, `player_last_join`, `player_time_online`, `player_total_commands`, `player_total_chat`) ");
		sql.append("VALUES ");
		
		for (Entry<String, PlayerData> entry : entries){
			String playerName = entry.getKey();
			PlayerData data = entry.getValue();
			
			sql.append("('");
			sql.append(playerName);
			sql.append("', ");
			sql.append(data.lastJoinTime);
			sql.append(", ");
			sql.append(data.lastJoinTime);
			sql.append(", ");
			sql.append((System.currentTimeMillis() / 1000) - data.lastUpdate);
			sql.append(", ");
			sql.append(data.totalCommands);
			sql.append(", ");
			sql.append(data.totalChatMessages);
			sql.append("), ");
		}
		
		sql.replace(sql.length() - 2, sql.length(), " ");
		
		sql.append("ON DUPLICATE KEY UPDATE ");
		sql.append("`player_name` = VALUES(`player_name`), ");
		sql.append("`player_last_join` = VALUES(`player_last_join`), ");
		sql.append("`player_time_online` = `player_time_online` + VALUES(`player_time_online`),");
		sql.append("`player_total_commands` = VALUES(`player_total_commands`),");
		sql.append("`player_total_chat` = VALUES(`player_total_chat`)");
		
		System.out.println(sql.toString());
		
		return sql.toString();
	}
	
}
