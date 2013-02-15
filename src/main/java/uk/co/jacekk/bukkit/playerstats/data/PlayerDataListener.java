package uk.co.jacekk.bukkit.playerstats.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import uk.co.jacekk.bukkit.baseplugin.v7.event.BaseListener;
import uk.co.jacekk.bukkit.playerstats.PlayerStats;
import uk.co.jacekk.bukkit.playerstats.QueryBuilder;

public class PlayerDataListener extends BaseListener<PlayerStats> {
	
	private Set<String> activePlayers = Collections.synchronizedSet(new HashSet<String>());
	
	public PlayerDataListener(final PlayerStats plugin){
		super(plugin);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			@Override
			public void run(){
				PlayerData data;
				for(String player : activePlayers){
					data = plugin.playerDataManager.getDataFor(player);
					if(data != null)
						data.activeTime++;
				}
				activePlayers.clear();
			}
		}, 400, 400);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		
		if (!plugin.playerDataManager.gotDataFor(player)){
			plugin.playerDataManager.registerPlayer(player);
		}else{
			plugin.playerDataManager.getDataFor(player).lastJoinTime =  System.currentTimeMillis() / 1000;
		}
		plugin.mysql.performQuery(QueryBuilder.startSession(event.getPlayer().getName()));
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
							String deadPlayer = ((Player) entity).getName();
							data.addPlayerKill(deadPlayer);
							if(plugin.playerDataManager.gotDataFor(deadPlayer)){
								plugin.playerDataManager.getDataFor(deadPlayer).addPlayerDeath(killerName);
							}
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
		
		if(!(event.getEntity() instanceof Player))
			return;
		
		switch(damageEvent.getCause()){
			case CONTACT:
			case ENTITY_ATTACK:
			case ENTITY_EXPLOSION:
			case CUSTOM:
			case PROJECTILE:
			case WITHER:
				break;
			default:
				String playername = ((Player)event.getEntity()).getName();
				if(plugin.playerDataManager.gotDataFor(playername)){
					plugin.playerDataManager.getDataFor(playername).addSuicideDeath(damageEvent.getCause());
				}
				break;
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onMove(PlayerMoveEvent event){
		this.activePlayers.add(event.getPlayer().getName());
		
		Location from = event.getFrom();
		Location to = event.getTo();
		
		if(from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ()){
			plugin.playerDataManager.getDataFor(event.getPlayer()).distanceTravelled++;
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event){
		plugin.playerDataManager.getDataFor(event.getPlayer()).logoutTime = System.currentTimeMillis() / 1000;
		plugin.mysql.performQuery(QueryBuilder.endSession(event.getPlayer().getName(), plugin.playerDataManager.getDataFor(event.getPlayer())));
	}
}
