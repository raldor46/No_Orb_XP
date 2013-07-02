package com.gmail.raldor46.exp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class ExpOrbDeletion extends JavaPlugin implements Listener{
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
	}
	public void onDisable(){
		
	}
	
	//Events
	
	//when an entity is damaged by a player it will add him to a list stored in the entities metadata
	@EventHandler(priority = EventPriority.LOW)
	public void MyPlayerListGenerator(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Player){
			if(getEntityMetadata(event.getEntity(),"player list") == null){
				ArrayList<Player> players = new ArrayList<Player>(1);
				players.add((Player) event.getDamager());
				setEntityMetadata(event.getEntity(), "player list", players);
			}
			else{
				ArrayList<Player> players = (ArrayList)getEntityMetadata(event.getEntity(),"player list");
				boolean appears = false;
				for(Player p : players){
					if (p.getName().equals(((Player) event.getDamager()).getName()))
							appears = true;break;
				}
				if (appears == false){
					players.add((Player)event.getDamager());
					setEntityMetadata(event.getEntity(), "palyer list", players);
				}
			}
		}
		else if(event.getDamager() instanceof Projectile){
			if(((Projectile) event.getDamager()).getShooter() instanceof Player){
				if(getEntityMetadata(event.getEntity(),"player list") == null){
					ArrayList<Player> players = new ArrayList<Player>(1);
					players.add((Player) ((Projectile)event.getDamager()).getShooter());
					setEntityMetadata(event.getEntity(), "player list", players);
				}
				else{
					ArrayList<Player> players = (ArrayList)getEntityMetadata(event.getEntity(),"player list");
					boolean appears = false;
					for(Player p : players){
						if (p.getName().equals(((Player) ((Projectile)event.getDamager()).getShooter()).getName()))
							appears = true;break;
					}
					if (appears == false){
						players.add((Player) ((Projectile)event.getDamager()).getShooter());
						setEntityMetadata(event.getEntity(), "palyer list", players);
					}
				}
			}
		}
		
	}
	/*when the mob dies, amount of xp will be saved and the amount of actual dropped exp will be set to 0
	* and the exp will be distributed across the players who damaged it*/
	@EventHandler(priority = EventPriority.HIGHEST)
	public void MyExpDistributor(EntityDeathEvent event){
		final int exp = event.getDroppedExp();
		event.setDroppedExp(0);
		if(getEntityMetadata(event.getEntity(),"player list") != null){
			ArrayList<Player> players = (ArrayList)getEntityMetadata(event.getEntity(),"player list");
			for(Player p : players){
				//p.sendMessage("adding " + exp + " exp");
				p.giveExp(exp);
			}
		}
	}
	
	//Metadata Handlers
	
	public void setEntityMetadata(Entity entity, String key, Object value){
		entity.setMetadata(key,new FixedMetadataValue(this,value));
	}
	public Object getEntityMetadata(Entity entity, String key){
	  List<MetadataValue> values = entity.getMetadata(key);  
	  for(MetadataValue value : values){
	     if(value.getOwningPlugin().getDescription().getName().equals(this.getDescription().getName())){
	        return value.value();
	     }
	  }
	return null;
	}
}
