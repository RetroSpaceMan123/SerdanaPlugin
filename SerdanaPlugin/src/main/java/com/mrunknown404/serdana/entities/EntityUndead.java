package main.java.com.mrunknown404.serdana.entities;

import main.java.com.mrunknown404.serdana.entities.util.EntityMonsterBase;
import main.java.com.mrunknown404.serdana.entities.util.EnumCustomEntities;
import net.minecraft.server.v1_13_R2.World;

public class EntityUndead extends EntityMonsterBase {

	public EntityUndead(World world) {
		super(world, EnumCustomEntities.UNDEAD, 10, 20, 30);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		
	}
}