package main.java.com.mrunknown404.serdana.quests.tasks;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.nisovin.shopkeepers.api.shopkeeper.Shopkeeper;

import main.java.com.mrunknown404.serdana.scripts.InitScripts;
import main.java.com.mrunknown404.serdana.scripts.ScriptHandler;
import main.java.com.mrunknown404.serdana.scripts.ScriptInfo;
import main.java.com.mrunknown404.serdana.util.enums.EnumScriptStartType;
import main.java.com.mrunknown404.serdana.util.enums.EnumTaskCheckType;

public abstract class QuestTask implements ConfigurationSerializable {
	protected EnumTaskCheckType type;
	protected String[] description, completionMessage, scriptNames;
	protected int amount = 0, amountNeeded;
	
	public QuestTask(EnumTaskCheckType type, int amountNeeded, String[] description, String[] completionMessage, String[] scriptNames) {
		this.type = type;
		this.description = description;
		this.completionMessage = completionMessage;
		this.amountNeeded = amountNeeded;
		this.scriptNames = scriptNames;
	}
	
	@SuppressWarnings("unchecked")
	public QuestTask(Map<String, Object> map) {
		type = EnumTaskCheckType.valueOf((String) map.get("type"));
		amount = (int) map.get("amount");
		amountNeeded = (int) map.get("amountNeeded");
		description = ((List<String>) map.get("description")).toArray(new String[0]);
		completionMessage = ((List<String>) map.get("completionMessage")).toArray(new String[0]);
		scriptNames = ((List<String>) map.get("scriptNames")).toArray(new String[0]);
	}
	
	@Override
	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("type", type.toString());
		result.put("amount", amount);
		result.put("amountNeeded", amountNeeded);
		result.put("description", description);
		result.put("completionMessage", completionMessage);
		result.put("scriptNames", scriptNames);
		return result;
	}
	
	/** Runs a Script found using the given variables
	 * @param p Player thats running the Script
	 * @param type Script start Type
	 * @param id Script Task ID to run
	 */
	public void doScript(Player p, EnumScriptStartType type, int id) {
		for (ScriptInfo scr : InitScripts.getScripts(scriptNames)) {
			if (scr.getScriptTaskID() == id && scr.getStartType() == type) {
				ScriptHandler.read(scr, p);
			}
		}
	}
	
	/** Checks if the {@link QuestTask} was successful
	 * @param obj Object to check
	 * @return true if task was successful, otherwise false
	 */
	public abstract boolean checkForTask(Object obj);
	
	/** Checks if the {@link QuestTask} is finished
	 * @return true if the task is finished, otherwise false
	 */
	public boolean checkForFinishedTask() {
		if (amount >= amountNeeded) {
			return true;
		}
		
		return false;
	}
	
	/** Checks if the given {@link Object} is the right type
	 * @param obj Object to check
	 * @return true if the given Object is the right type, otherwise false
	 */
	protected boolean checkType(Object obj) {
		switch (type) {
			case entityDeath:
				if (obj instanceof Entity) {
					return true;
				}
			case playerTick:
				if (obj instanceof Player) {
					return true;
				}
			case shopTalk:
				if (obj instanceof Shopkeeper) {
					return true;
				}
		}
		
		return false;
	}
	
	public void increaseAmmount() {
		amount++;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int getAmountNeeded() {
		return amountNeeded;
	}
	
	public String[] getDescription() {
		return description;
	}
	
	public String[] getCompletionMessage() {
		return completionMessage;
	}
	
	public EnumTaskCheckType getTaskCheckType() {
		return type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof QuestTask) {
			QuestTask task = (QuestTask) obj;
			
			return task.type == type && areEqual(task.description, description) && areEqual(task.completionMessage, completionMessage) &&
					areEqual(task.scriptNames, scriptNames) && task.amountNeeded == amountNeeded ? true : false;
		}
		
		return false;
	}
	
	private boolean areEqual(Object[] arr1, Object[] arr2) {
		if (arr1.length != arr2.length) {
			return false;
		}
		
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		
		for (int i = 0; i < arr1.length; i++) {
			if (!arr1[i].equals(arr2[i])) {
				System.out.println("These are out of date : " + arr1[i] + ":" + arr2[i]);
				return false;
			}
		}
		return true;
	}
}
