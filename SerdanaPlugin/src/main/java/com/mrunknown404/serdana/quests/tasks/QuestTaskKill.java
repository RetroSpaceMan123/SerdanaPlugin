package main.java.com.mrunknown404.serdana.quests.tasks;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import main.java.com.mrunknown404.serdana.util.enums.EnumTaskCheckType;

public class QuestTaskKill extends QuestTask implements ConfigurationSerializable {

	protected EntityType entityType;
	
	public QuestTaskKill(EntityType entityType, int amountNeeded, String[] description, String[] completionMessage, String[] scriptNames) {
		super(EnumTaskCheckType.entityDeath, amountNeeded, description, completionMessage, scriptNames);
		this.amountNeeded = amountNeeded;
		this.entityType = entityType;
	}
	
	public QuestTaskKill(Map<String, Object> map) {
		super(map);
		
		entityType = EntityType.valueOf((String) map.get("entityType"));
	}
	
	@Override
	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> result = (LinkedHashMap<String, Object>) super.serialize();
		result.put("entityType", entityType.toString());
		return result;
	}
	
	@Override
	public boolean checkForTask(Object obj) {
		if (checkType(obj)) {
			if (((Entity) obj).getType() == entityType) {
				amount++;
				return true;
			}
		}
		
		return false;
	}
}
