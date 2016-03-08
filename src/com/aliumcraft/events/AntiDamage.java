package com.aliumcraft.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.aliumcraft.Main;

public class AntiDamage implements Listener {
	Main plugin;
	
	public AntiDamage(Main is) {
		this.plugin = is;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if((e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) &&	(plugin.getConfig().getBoolean("Damage.Fall"))) {
			e.setCancelled(true);
		}
		if((e.getCause().equals(EntityDamageEvent.DamageCause.DROWNING)) &&	(plugin.getConfig().getBoolean("Damage.Drown"))) {
			e.setCancelled(true);
		}
		if((e.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION)) && (plugin.getConfig().getBoolean("Damage.Suffocation"))) {
			e.setCancelled(true);
		}
		if((e.getCause().equals(EntityDamageEvent.DamageCause.CONTACT)) &&	(plugin.getConfig().getBoolean("Damage.Contact"))) {
			e.setCancelled(true);
		}
		if((e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) && (plugin.getConfig().getBoolean("Damage.Contact"))) {
			e.setCancelled(true);
		}
		if((e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) &&	(plugin.getConfig().getBoolean("Damage.Contact"))) {
			e.setCancelled(true);
		}
		if((e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) && (plugin.getConfig().getBoolean("Damage.Contact"))) {
			e.setCancelled(true);
		}
	}
}
