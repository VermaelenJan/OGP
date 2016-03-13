package hillbillies.part2.internal.ui.sprites;

import static hillbillies.part2.internal.ui.sprites.SpriteSheets.boulderPerFaction;
import static hillbillies.part2.internal.ui.sprites.SpriteSheets.defaultPerFaction;
import static hillbillies.part2.internal.ui.sprites.SpriteSheets.woodPerFaction;

import hillbillies.model.Unit;
import hillbillies.part1.internal.ui.sprites.UnitSprite;
import hillbillies.part2.internal.providers.IGameObjectInfoProvider;
import ogp.framework.ui.SpriteSheet;

public class UnitSprite2 extends UnitSprite {

	private int factionIndex = -1;
	private boolean carriesBoulder;
	private boolean carriesLog;

	public UnitSprite2(Unit unit, IGameObjectInfoProvider infoProvider) {
		super(unit, infoProvider);
	}

	@Override
	protected SpriteSheet getNoAnimationSpritesheet() {
		if (carriesBoulder) {
			return boulderPerFaction.get(factionIndex);
		} else if (carriesLog) {
			return woodPerFaction.get(factionIndex);
		} else {
			return defaultPerFaction.get(factionIndex);
		}
	}
	
	@Override
	protected SpriteSheet getWalkAnimationSpritesheet() {
		if (carriesBoulder) {
			return boulderPerFaction.get(factionIndex);
		} else if (carriesLog) {
			return woodPerFaction.get(factionIndex);
		} else {
			return defaultPerFaction.get(factionIndex);
		}
	}
	
	@Override
	protected SpriteSheet getSprintAnimationSpritesheet() {
		if (carriesBoulder) {
			return boulderPerFaction.get(factionIndex);
		} else if (carriesLog) {
			return woodPerFaction.get(factionIndex);
		} else {
			return defaultPerFaction.get(factionIndex);
		}
	}
	
	@Override
	protected SpriteSheet getAttackAnimationSpritesheet() {
		return defaultPerFaction.get(factionIndex);
	}

	@Override
	public IGameObjectInfoProvider getInfoProvider() {
		return (IGameObjectInfoProvider) super.getInfoProvider();
	}

	@Override
	public void update() {
		updateFaction();
		updateCarries();
		super.update();
	}

	protected void updateCarries() {
		this.carriesBoulder = getInfoProvider().isCarryingBoulder(getUnit());
		this.carriesLog = getInfoProvider().isCarryingLog(getUnit());
	}

	protected void updateFaction() {
		this.factionIndex = getInfoProvider().getFactionIndex(getInfoProvider().getFaction(getUnit()));
	}

}
