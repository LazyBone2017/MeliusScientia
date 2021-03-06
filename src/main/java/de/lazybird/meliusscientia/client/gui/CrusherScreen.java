package de.lazybird.meliusscientia.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.lazybird.meliusscientia.MeliusScientia;
import de.lazybird.meliusscientia.container.CombustionGeneratorContainer;
import de.lazybird.meliusscientia.container.CrusherContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrusherScreen extends ContainerScreen<CrusherContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(MeliusScientia.MODID, "textures/gui/crusher.png");

    public CrusherScreen(CrusherContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
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
        this.font.drawString(String.valueOf(container.getTileEntity().energyStorage.getEnergyStored()), 114, 63, 4210752);
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
        this.blit(i + 72, j + 31, 176, 0, 13, 23 - l);

    }
}
