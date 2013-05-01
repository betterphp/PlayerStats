package uk.co.jacekk.bukkit.playerstats;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import uk.co.jacekk.bukkit.playerstats.data.PlayerData;

public class QueryBuilder {
	
	public static String updatePlayers(HashMap<String, PlayerData> players){
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO `stats_players` (`player_name`, `player_first_join`, `player_last_join`, `player_time_online`, `player_total_commands`, `player_total_chat`, `player_active_time`, `player_moved_distance`) ");
		sql.append("VALUES ");
		
		for (Entry<String, PlayerData> entry : players.entrySet()){
			String playerName = entry.getKey();
			PlayerData data = entry.getValue();
			
			sql.append("('");
			sql.append(playerName);
			sql.append("', ");
			sql.append(data.lastJoinTime);
			sql.append(", ");
			sql.append(data.lastJoinTime);
			sql.append(", ");
			sql.append((!data.lastDirection.equals(data.getPlayer().getLocation())) ? ((System.currentTimeMillis() / 1000) - data.lastUpdate) : 0);
			sql.append(", ");
			sql.append(data.totalCommands);
			sql.append(", ");
			sql.append(data.totalChatMessages);
			sql.append(", ");
			sql.append(data.activeTime);
			sql.append(", ");
			sql.append(data.distanceTravelled);
			sql.append("), ");
		}
		
		sql.replace(sql.length() - 2, sql.length(), " ");
		
		sql.append("ON DUPLICATE KEY UPDATE ");
		sql.append("`player_last_join` = VALUES(`player_last_join`), ");
		sql.append("`player_time_online` = `player_time_online` + VALUES(`player_time_online`),");
		sql.append("`player_total_commands` = `player_total_commands` + VALUES(`player_total_commands`),");
		sql.append("`player_total_chat` = `player_total_chat` + VALUES(`player_total_chat`),");
		sql.append("`player_active_time` = `player_active_time` + VALUES(`player_active_time`),");
		sql.append("`player_moved_distance` = `player_moved_distance` + VALUES(`player_moved_distance`)");
		
		return sql.toString();
	}
	
	public static String updateBlocksPlaced(HashMap<String, PlayerData> players){
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO `stats_blocks_placed` (`player_id`, `block_id`, `total`) ");
		sql.append("VALUES ");
		
		for (Entry<String, PlayerData> entry : players.entrySet()){
			String playerName = entry.getKey();
			PlayerData data = entry.getValue();
			
			for (Entry<Material, Integer> blocks : data.blocksPlaced.entrySet()){
				sql.append("(");
				sql.append("(SELECT `player_id` FROM `stats_players` WHERE `player_name` = '");
				sql.append(playerName);
				sql.append("'), ");
				sql.append(blocks.getKey().getId());
				sql.append(", ");
				sql.append(blocks.getValue());
				sql.append("), ");
			}
		}
		
		sql.replace(sql.length() - 2, sql.length(), " ");
		
		sql.append("ON DUPLICATE KEY UPDATE ");
		sql.append("`total` = `total` + VALUES(`total`)");
		
		return sql.toString();
	}
	
	public static String updateBlocksBroken(HashMap<String, PlayerData> players){
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO `stats_blocks_broken` (`player_id`, `block_id`, `total`) ");
		sql.append("VALUES ");
		
		for (Entry<String, PlayerData> entry : players.entrySet()){
			String playerName = entry.getKey();
			PlayerData data = entry.getValue();
			
			for (Entry<Material, Integer> blocks : data.blocksBroken.entrySet()){
				sql.append("(");
				sql.append("(SELECT `player_id` FROM `stats_players` WHERE `player_name` = '");
				sql.append(playerName);
				sql.append("'), ");
				sql.append(blocks.getKey().getId());
				sql.append(", ");
				sql.append(blocks.getValue());
				sql.append("), ");
			}
		}
		
		sql.replace(sql.length() - 2, sql.length(), " ");
		
		sql.append("ON DUPLICATE KEY UPDATE ");
		sql.append("`total` = `total` + VALUES(`total`)");
		
		return sql.toString();
	}
	
	public static String updateMobKills(HashMap<String, PlayerData> players){
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO `stats_mob_kills` (`player_id`, `mob_name`, `total`) ");
		sql.append("VALUES ");
		
		for (Entry<String, PlayerData> entry : players.entrySet()){
			String playerName = entry.getKey();
			PlayerData data = entry.getValue();
			
			for (Entry<EntityType, Integer> mobs : data.mobsKilled.entrySet()){
				sql.append("(");
				sql.append("(SELECT `player_id` FROM `stats_players` WHERE `player_name` = '");
				sql.append(playerName);
				sql.append("'), '");
				sql.append(mobs.getKey().name());
				sql.append("', ");
				sql.append(mobs.getValue());
				sql.append("), ");
			}
		}
		
		sql.replace(sql.length() - 2, sql.length(), " ");
		
		sql.append("ON DUPLICATE KEY UPDATE ");
		sql.append("`total` = `total` + VALUES(`total`)");
		
		return sql.toString();
	}
	
	public static String updateMobDeaths(HashMap<String, PlayerData> players){
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO `stats_mob_deaths` (`player_id`, `mob_name`, `total`) ");
		sql.append("VALUES ");
		
		for (Entry<String, PlayerData> entry : players.entrySet()){
			String playerName = entry.getKey();
			PlayerData data = entry.getValue();
			
			for (Entry<EntityType, Integer> mobs : data.mobDeaths.entrySet()){
				sql.append("(");
				sql.append("(SELECT `player_id` FROM `stats_players` WHERE `player_name` = '");
				sql.append(playerName);
				sql.append("'), '");
				sql.append(mobs.getKey().name());
				sql.append("', ");
				sql.append(mobs.getValue());
				sql.append("), ");
			}
		}
		
		sql.replace(sql.length() - 2, sql.length(), " ");
		
		sql.append("ON DUPLICATE KEY UPDATE ");
		sql.append("`total` = `total` + VALUES(`total`)");
		
		return sql.toString();
	}
	
	public static String updatePlayerKills(HashMap<String, PlayerData> players){
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO `stats_player_kills` (`player_id`, `killed_player_id`, `total`) ");
		sql.append("VALUES ");
		
		for (Entry<String, PlayerData> entry : players.entrySet()){
			String playerName = entry.getKey();
			PlayerData data = entry.getValue();
			
			for (Entry<String, Integer> player : data.playersKilled.entrySet()){
				sql.append("(");
				sql.append("(SELECT `player_id` FROM `stats_players` WHERE `player_name` = '");
				sql.append(playerName);
				sql.append("'), (SELECT `player_id` FROM `stats_players` WHERE `player_name` = '");
				sql.append(player.getKey());
				sql.append("'), ");
				sql.append(player.getValue());
				sql.append("), ");
			}
		}
		
		sql.replace(sql.length() - 2, sql.length(), " ");
		
		sql.append("ON DUPLICATE KEY UPDATE ");
		sql.append("`total` = `total` + VALUES(`total`)");
		
		return sql.toString();
	}
	
	public static String updatePlayerDeaths(HashMap<String, PlayerData> players){
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO `stats_player_deaths` (`player_id`, `killed_by_id`, `total`) ");
		sql.append("VALUES ");
		
		for (Entry<String, PlayerData> entry : players.entrySet()){
			String playerName = entry.getKey();
			PlayerData data = entry.getValue();
			
			for (Entry<String, Integer> player : data.playerDeaths.entrySet()){
				sql.append("(");
				sql.append("(SELECT `player_id` FROM `stats_players` WHERE `player_name` = '");
				sql.append(playerName);
				sql.append("'), (SELECT `player_id` FROM `stats_players` WHERE `player_name` = '");
				sql.append(player.getKey());
				sql.append("'), ");
				sql.append(player.getValue());
				sql.append("), ");
			}
		}
		
		sql.replace(sql.length() - 2, sql.length(), " ");
		
		sql.append("ON DUPLICATE KEY UPDATE ");
		sql.append("`total` = `total` + VALUES(`total`)");
		
		return sql.toString();
	}
	
	public static String updatePlayerSuicides(HashMap<String, PlayerData> players){
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO `stats_player_suicides` (`player_id`, `death_type`, `total`) ");
		sql.append("VALUES ");
		
		for (Entry<String, PlayerData> entry : players.entrySet()){
			String playerName = entry.getKey();
			PlayerData data = entry.getValue();
			
			for (Entry<DamageCause, Integer> deaths : data.suicides.entrySet()){
				sql.append("(");
				sql.append("(SELECT `player_id` FROM `stats_players` WHERE `player_name` = '");
				sql.append(playerName);
				sql.append("'), '");
				sql.append(deaths.getKey().toString());
				sql.append("', ");
				sql.append(deaths.getValue());
				sql.append("), ");
			}
		}
		
		sql.replace(sql.length() - 2, sql.length(), " ");
		
		sql.append("ON DUPLICATE KEY UPDATE ");
		sql.append("`total` = `total` + VALUES(`total`)");
		
		return sql.toString();
	}
	
	public static String updateSession(HashMap<String, PlayerData> players){
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO `stats_sessions` (`player_id`, `session_time`, `session_blocks_placed`, `session_blocks_broken`, `session_time_spend`, `session_time_active`, `session_distance_travelled`, `session_mob_deaths`, `session_player_deaths`, `session_suicides`) ");
		sb.append("VALUES");
		for(Entry<String, PlayerData> entry : players.entrySet()){
			PlayerData data = entry.getValue();
			sb.append("(");
			sb.append("(SELECT `player_id` FROM `stats_players` WHERE `player_name` = '");
			sb.append(entry.getKey());
			sb.append("'), ");
			sb.append(data.lastJoinTime);
			sb.append(", ");
			sb.append(PlayerData.getValueSum(data.blocksPlaced));
			sb.append(", ");
			sb.append(PlayerData.getValueSum(data.blocksBroken));
			sb.append(", ");
			sb.append((System.currentTimeMillis() / 1000) - data.lastJoinTime);
			sb.append(", ");
			sb.append(data.activeTime);
			sb.append(", ");
			sb.append(data.distanceTravelled);
			sb.append(", ");
			sb.append(PlayerData.getValueSum(data.mobDeaths));
			sb.append(", ");
			sb.append(PlayerData.getValueSum(data.playerDeaths));
			sb.append(", ");
			sb.append(PlayerData.getValueSum(data.suicides));
			sb.append("), ");
		}
		sb.replace(sb.length() - 2, sb.length(), " ");
		sb.append("ON DUPLICATE KEY UPDATE ");
		sb.append("`session_blocks_placed` = `session_blocks_placed` + VALUES(`session_blocks_placed`), ");
		sb.append("`session_blocks_broken` = `session_blocks_broken` + VALUES(`session_blocks_broken`), ");
		sb.append("`session_time_spend` = VALUES(`session_time_spend`), ");
		sb.append("`session_time_active` = `session_time_active` + VALUES(`session_time_active`), ");
		sb.append("`session_distance_travelled` = `session_distance_travelled` + VALUES(`session_distance_travelled`), ");
		sb.append("`session_mob_deaths` = `session_mob_deaths` + VALUES(`session_mob_deaths`), ");
		sb.append("`session_player_deaths` = `session_player_deaths` + VALUES(`session_player_deaths`), ");
		sb.append("`session_suicides` = `session_suicides` + VALUES(`session_suicides`)");
		
		return sb.toString();
	}
	
	public static String endSession(String player, PlayerData data){
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE `stats_sessions` SET ");
		sb.append("`session_status` = 0,");
		sb.append("`session_time_spend` = ");
		sb.append(data.logoutTime - data.lastJoinTime);
		sb.append(" ");
		sb.append(" WHERE `player_id` = (SELECT `player_id` FROM `stats_players` WHERE `player_name` = '");
		sb.append(player);
		sb.append("') AND `session_time` = ");
		sb.append(data.lastJoinTime);
		
		return sb.toString();
	}
	
	public static String startSession(String player){
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO `stats_sessions` (`player_id`, `session_time`) VALUES(");
		sb.append("(SELECT `player_id` FROM `stats_players` WHERE `player_name` = '");
		sb.append(player);
		sb.append("'), ");
		sb.append(System.currentTimeMillis() / 1000);
		sb.append(")");
		
		return sb.toString();
	}
}
