package com.gmail.raldor46.exp;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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
		
	}
	public void onDisable(){
		
	}
	
	//Events
	
	//when an entity is damaged by a player it will add him to a list stored in the entities metadata
	public void MyPlayerListGenerator(EntityDamageByEntityEvent event){
		
	}
	/*when the mob dies, amount of xp will be saved and the amount of actual dropped exp will be set to 0
	* and the exp will be distributed across the players who damaged it*/
	@EventHandler(priority = EventPriority.HIGH)
	public void MyExpDistributor(EntityDeathEvent event){
		
	}
	
	//Metadata Handlers
	
	public void setMetadata(Player player, String key, Object value){
		player.setMetadata(key,new FixedMetadataValue(this,value));
	}
	public Object getMetadata(Player player, String key){
	  List<MetadataValue> values = player.getMetadata(key);  
	  for(MetadataValue value : values){
	     if(value.getOwningPlugin().getDescription().getName().equals(this.getDescription().getName())){
	        return value.value();
	     }
	  }
	return null;
	}
}
