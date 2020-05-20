package de.lazybird.meliusscientia.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.lazybird.meliusscientia.MeliusScientia;
import de.lazybird.meliusscientia.container.CombustionGeneratorContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CombustionGeneratorScreen extends ContainerScreen<CombustionGeneratorContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(MeliusScientia.MODID, "textures/gui/combustion_generator.png");

    public CombustionGeneratorScreen(CombustionGeneratorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString(this.title.getFormattedText(), 8, 6, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8, ySize - 96 + 2, 4210752);
        this.font.drawString("Energy:", 79, 35, 4210752);
        this.font.drawString(String.valueOf(container.getTileEntity().energyStorage.getEnergyStored()), 79, 45, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(x, y, 0, 0, this.xSize, this.ySize);
        int k = container.getEnergyProgressionScaled();
        int l = container.getTimeProgressionScaled();
        int i = this.guiLeft;
        int j = this.guiTop;
        //energy progress bar
        this.blit(i + 149, j + 70 - k, 176, 24, 11, k);
        //burntime
        this.blit(i + 65, j + 56 - l, 176, 0, 6, l);
    }
}
