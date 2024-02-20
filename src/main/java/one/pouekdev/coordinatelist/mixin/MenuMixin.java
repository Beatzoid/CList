package one.pouekdev.coordinatelist.mixin;

import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import one.pouekdev.coordinatelist.CListWaypointScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class MenuMixin extends Screen {
	protected MenuMixin(Text title) {
		super(title);
	}
	@Inject(at = @At("HEAD"), method = "initWidgets")
	private void initWidgets(CallbackInfo ci) {
		int buttonWidth = 204;
		int buttonHeight = 20;

		// Create a margin for all widgets
		int margin = 4;

		// Create a button at the specified position
		ButtonWidget clistButton = new ButtonWidget(
				this.width / 2 - buttonWidth / 2, // x-coordinate
				this.height / 4 + margin,          // y-coordinate
				buttonWidth,
				buttonHeight,
				Text.literal("CList"),
				button -> this.client.setScreen(new CListWaypointScreen(Text.literal("Waypoints")))
		);

		addDrawableChild(clistButton);
	}
}

