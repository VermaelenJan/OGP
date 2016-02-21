package hillbillies.part1.internal.ui.sprites;

import hillbillies.common.internal.providers.UnitInfoProvider;
import hillbillies.common.internal.ui.sprites.AbstractSprite;
import hillbillies.common.internal.ui.sprites.SpriteOrientation;
import hillbillies.model.Unit;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ogp.framework.ui.SimpleAnimationBuilder;
import ogp.framework.ui.SpriteAnimation;
import ogp.framework.ui.SpriteSheet;
import ogp.framework.util.internal.ResourceUtils;

public class UnitSprite extends AbstractSprite<Unit, UnitInfoProvider> {

	protected static final SpriteSheet defaultSpritesheet = new SpriteSheet(
			ResourceUtils.loadImage("resources/peasant_normal.png"), 72, 72, 1, 1);
	protected static final Duration defaultDuration = Duration.millis(150);
	protected static final SpriteAnimation NO_ANIMATION = new SpriteAnimation(defaultSpritesheet,
			i -> SimpleAnimationBuilder.create(i, defaultDuration, Interpolator.DISCRETE).atNext(0)
					.withCycleCount(Timeline.INDEFINITE));

	protected static final SpriteAnimation WALK_ANIMATION = new SpriteAnimation(defaultSpritesheet,
			i -> SimpleAnimationBuilder.create(i, defaultDuration, Interpolator.DISCRETE).atNext(5).atNext(10).atNext(0)
					.atNext(15).atNext(20).atNext(0).withCycleCount(Timeline.INDEFINITE));

	protected static final SpriteAnimation SPRINT_ANIMATION = new SpriteAnimation(defaultSpritesheet,
			i -> SimpleAnimationBuilder.create(i, defaultDuration.divide(2), Interpolator.DISCRETE).atNext(5).atNext(10)
					.atNext(0).atNext(15).atNext(20).atNext(0).withCycleCount(Timeline.INDEFINITE));

	protected static final SpriteAnimation ATTACK_ANIMATION = new SpriteAnimation(defaultSpritesheet,
			i -> SimpleAnimationBuilder.create(i, defaultDuration, Interpolator.DISCRETE).atNext(25).atNext(30)
					.atNext(35).atNext(40).atNext(45).atNext(25).withCycleCount(Timeline.INDEFINITE));

	private BooleanProperty selected = new SimpleBooleanProperty(false);

	private final Group group;

	private final ImageView imageView;

	private final Circle selectionCircle;

	private boolean optionShowPosition = false;

	private final IntegerProperty orientationOffset = new SimpleIntegerProperty();
	private final BooleanProperty hflip = new SimpleBooleanProperty();

	private SpriteAnimation currentAnimation = null;

	public UnitSprite(Unit unit, UnitInfoProvider infoProvider) {
		super(unit, infoProvider);
		this.group = new Group();
		group.setManaged(false);
		this.group.setUserData(this);

		this.selectionCircle = new Circle();
		group.getChildren().add(selectionCircle);
		selectionCircle.setFill(Color.CORNFLOWERBLUE.deriveColor(0, 1, 1, 0.3));
		selectionCircle.setStroke(Color.CORNFLOWERBLUE);
		selectionCircle.setStrokeWidth(2.0);
		selectionCircle.visibleProperty().bind(selected);
		selectionCircle.radiusProperty().bind(
				Bindings.max(screenSizeXProperty().divide(2), screenSizeYProperty().divide(2)).multiply(Math.sqrt(2)));

		this.imageView = defaultSpritesheet.createImageView(0);
		Glow glow = new Glow();
		glow.setLevel(0.8);
		imageView.effectProperty().bind(new When(selected).then(glow).otherwise((Glow) null));
		imageView.scaleXProperty().bind(new When(hflip).then(-1.0).otherwise(1.0));
		group.getChildren().add(imageView);

		if (optionShowPosition) {
			Rectangle positionRect = new Rectangle();
			positionRect.widthProperty().bind(screenSizeXProperty());
			positionRect.heightProperty().bind(screenSizeYProperty());
			positionRect.xProperty().bind(screenSizeXProperty().divide(2).negate());
			positionRect.yProperty().bind(screenSizeYProperty().divide(2).negate());
			group.getChildren().add(positionRect);
			positionRect.setStroke(Color.YELLOW.deriveColor(0, 1, 1, 0.3));
			positionRect.setFill(null);

			Circle positionCircle = new Circle();
			group.getChildren().add(positionCircle);
			positionCircle.setRadius(2);
			positionCircle.setFill(Color.WHITE);

			Label positionLabel = new Label();
			positionLabel.textProperty()
					.bind(Bindings.format("(%.2f,%.2f,%.2f)", worldXProperty(), worldYProperty(), worldZProperty()));
			positionLabel.setTranslateY(-positionLabel.getFont().getSize() - 3);
			positionLabel.setTextFill(Color.WHITE);
			group.getChildren().add(positionLabel);
		}

		orientationOffset.addListener(viewportListener);
		updateViewport();
	}

	public Unit getUnit() {
		return super.getObject();
	}

	@Override
	public void update() {
		updateWorldPosition();
		updateWorldSize();
		updateOrientation();
		updateSelected();
		updateAnimation();
	}

	protected void updateSelected() {
		selected.set(getInfoProvider().isSelected(getUnit()));
	}

	protected void updateOrientation() {
		SpriteOrientation orientation = SpriteOrientation
				.fromDegrees(getInfoProvider().getOrientationInDegrees(getUnit()));
		orientationOffset.set(orientation.getOffset());
		hflip.set(orientation.getHFlip());
	}

	protected void updateWorldSize() {
		worldSizeXProperty().set(getInfoProvider().getSizeX(getUnit()));
		worldSizeYProperty().set(getInfoProvider().getSizeY(getUnit()));
		worldSizeZProperty().set(getInfoProvider().getSizeZ(getUnit()));
	}

	protected void updateWorldPosition() {
		getInfoProvider().getPosition(getUnit()).ifPresent(position -> {
			worldXProperty().set(position[0]);
			worldYProperty().set(position[1]);
			worldZProperty().set(position[2]);
		});
	}

	protected SpriteAnimation calculateAnimation() {
		UnitInfoProvider provider = getInfoProvider();

		if (provider.isAttacking(getUnit())) {
			return ATTACK_ANIMATION;
		} else if (provider.isWorking(getUnit())) {
			// TODO: working animation
			return ATTACK_ANIMATION;
		} else if (provider.isWalking(getUnit())) {
			if (provider.isSprinting(getUnit())) {
				return SPRINT_ANIMATION;
			} else {
				return WALK_ANIMATION;
			}
		} else {
			return NO_ANIMATION;
		}
	}

	private final ChangeListener<? super Number> viewportListener = (c, x, newValue) -> updateViewport();

	private void updateAnimation() {
		SpriteAnimation newAnimation = calculateAnimation();

		if (currentAnimation == newAnimation) {
			return;
		}

		if (currentAnimation != null) {
			currentAnimation.getBaseIndex().removeListener(viewportListener);
			currentAnimation.getAnimation().stop();
		}
		currentAnimation = newAnimation;
		if (currentAnimation != null) {
			currentAnimation.getBaseIndex().addListener(viewportListener);
			currentAnimation.getAnimation().playFromStart();
		}
		updateViewport();
	}

	private void updateViewport() {
		Rectangle2D vp = getViewport();
		imageView.setImage(getCurrentSpritesheet().getImage());
		imageView.setViewport(vp);
		imageView.setLayoutX(-vp.getWidth() / 2);
		imageView.setLayoutY(-vp.getHeight() / 2);
	}

	private Rectangle2D getViewport() {
		if (currentAnimation == null) {
			return getCurrentSpritesheet().getViewport(orientationOffset.get());
		}
		int index = currentAnimation.getBaseIndex().get() + orientationOffset.get();
		return getCurrentSpritesheet().getViewport(index);
	}

	private SpriteSheet getCurrentSpritesheet() {
		if (currentAnimation != null) {
			return currentAnimation.getSpriteSheet();
		}
		return defaultSpritesheet;
	}

	@Override
	public Node getGraph() {
		return group;
	}

	public BooleanProperty selectedProperty() {
		return selected;
	}
}
