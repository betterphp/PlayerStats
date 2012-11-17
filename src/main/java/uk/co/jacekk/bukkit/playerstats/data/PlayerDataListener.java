package uk.co.jacekk.bukkit.playerstats.data;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import uk.co.jacekk.bukkit.baseplugin.v5.event.BaseListener;
import uk.co.jacekk.bukkit.playerstats.PlayerStats;

public class PlayerDataListener extends BaseListener<PlayerStats> {
	
	public PlayerDataListener(PlayerStats plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		
		if (!plugin.playerDataManager.gotDataFor(player)){
			plugin.playerDataManager.registerPlayer(player);
		}else{
			plugin.playerDataManager.getDataFor(player).lastJoinTime =  System.currentTimeMillis() / 1000;
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerChat(AsyncPlayerChatEvent event){
		String playerName = event.getPlayer().getName();
		
		if (plugin.playerDataManager.gotDataFor(playerName)){
			PlayerData data = plugin.playerDataManager.getDataFor(playerName);
			
			++data.totalChatMessages;
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event){
		String playerName = event.getPlayer().getName();
		
		if (plugin.playerDataManager.gotDataFor(playerName)){
			PlayerData data = plugin.playerDataManager.getDataFor(playerName);
			++data.totalCommands;
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){
		String playerName = event.getPlayer().getName();
		
		if (plugin.playerDataManager.gotDataFor(playerName)){
			PlayerData data = plugin.playerDataManager.getDataFor(playerName);
			data.addBlockBreak(event.getBlock().getType());
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBucketFill(PlayerBucketFillEvent event){
		String playerName = event.getPlayer().getName();
		
		if (plugin.playerDataManager.gotDataFor(playerName)){
			PlayerData data = plugin.playerDataManager.getDataFor(playerName);
			data.addBlockBreak(event.getBlockClicked().getRelative(event.getBlockFace()).getType());
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event){
		String playerName = event.getPlayer().getName();
		
		if (plugin.playerDataManager.gotDataFor(playerName)){
			PlayerData data = plugin.playerDataManager.getDataFor(playerName);
			data.addBlockPlace(event.getBlock().getType());
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBucketEmpty(PlayerBucketEmptyEvent event){
		String playerName = event.getPlayer().getName();
		
		if (plugin.playerDataManager.gotDataFor(playerName)){
			PlayerData data = plugin.playerDataManager.getDataFor(playerName);
			
			Material bucketType = event.getPlayer().getItemInHand().getType();
			
			data.addBlockPlace((bucketType == Material.WATER_BUCKET) ? Material.WATER : Material.LAVA);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityDeath(EntityDeathEvent event){
		LivingEntity entity = event.getEntity();
		EntityDamageEvent damageEvent = entity.getLastDamageCause();
		
		if (damageEvent instanceof EntityDamageByEntityEvent){
			EntityDamageByEntityEvent entityDamageEvent = (EntityDamageByEntityEvent) damageEvent;
			
			Entity killer = entityDamageEvent.getDamager();
			
			if (killer instanceof LivingEntity){
				if (killer instanceof Player){
					String killerName = ((Player) killer).getName();
					
					if (plugin.playerDataManager.gotDataFor(killerName)){
						PlayerData data = plugin.playerDataManager.getDataFor(killerName);
						
						if (entity instanceof Player){
							// player killed player
							data.addPlayerKill(((Player) entity).getName());
						}else{
							// player killed mob
							data.addMobKill(entity.getType());
						}
					}
				}else{
					if (entity instanceof Player){
						// player killed mob
						String playerName = ((Player) entity).getName();
						
						if (plugin.playerDataManager.gotDataFor(playerName)){
							PlayerData data = plugin.playerDataManager.getDataFor(playerName);
							data.addMobDeath(killer.getType());
						}
					}
				}
			}
		}
	}
	
}
