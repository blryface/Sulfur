package ca.worldwidepixel.sulfur.mixin;

import ca.worldwidepixel.sulfur.Sulfur;
import ca.worldwidepixel.sulfur.shader.SuperSecret;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(TitleScreen.class)
public class PremiumMixin extends Screen {
    protected PremiumMixin(Text title) {
        super(title);
    }

    @Inject(
            method = "initWidgetsNormal",
            at = @At("HEAD")
    )
    private void initWidgetsNormal(int y, int spacingY, CallbackInfo ci) {
        int l = this.height / 4 + 48;
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.premium"), (button) -> {
            Sulfur.LOGGER.info("WOW");
        }).dimensions(this.width / 2 + 2, y, 98, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.secret"), (button) -> {
            SuperSecret.changeShader();
        }).dimensions(this.width / 2 + 2, y + spacingY, 98, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.rick"), (button) -> {
            Util.getOperatingSystem().open("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        }).dimensions(this.width / 2 + 2, y + spacingY * 2, 98, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("DEBUG MENU"), (button) -> {
            this.client.setScreen(new GameMenuScreen(true));
        }).dimensions(this.width / 2 + 2, y + spacingY * 3, 98, 20).build());
    }

    @ModifyArg(
            method = "initWidgetsNormal",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;dimensions(IIII)Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;"
            ),
            index = 2
    )
    public int width(int x) {
        return 98;
    }

    @Override
    public Optional<Element> hoveredElement(double mouseX, double mouseY) {
        return super.hoveredElement(mouseX, mouseY);
    }
}