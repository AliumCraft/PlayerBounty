package com.aliumcraft.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;


public class firePaintball implements Listener {
	 
	
	@EventHandler
	public static void shoot(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		ItemStack i = p.getItemInHand();
		if(i.hasItemMeta())
		{
			if (i.getItemMeta().getDisplayName().contains("Ammo"))
			{
				ItemStack ammo = new ItemStack(Material.SNOW_BALL);
				ammo.setAmount(1);
			
				Location target = p.getEyeLocation();
				
				
				Item shotItem = p.getWorld().dropItem(target, ammo);
				
				shotItem.setVelocity(target.getDirection());
				
				i.setAmount(i.getAmount() - 1);
				p.setItemInHand(i);
				
				
			}
		}
	}
	
	public static void changeProjectileEntity(ProjectileLaunchEvent e)
	{
		Entity entity = e.getEntity();
		
		if (entity instanceof Snowball)
		{
			
		}
		
	}
}
	
