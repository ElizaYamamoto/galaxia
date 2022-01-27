package io.github.elizayami.galaxia.common.block.tool_fuser;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContainerScreenTF extends ContainerScreen<ContainerTF>
{

	private ContainerTF containerFuser;

	public ContainerScreenTF(ContainerTF containerFuser, PlayerInventory playerInventory, ITextComponent title)
	{
		super(containerFuser, playerInventory, title);
		this.containerFuser = containerFuser;

		xSize = 176;
		ySize = 211;
	}
	final static int PICK_XPOS = 49;
	final static int PICK_YPOS = 60;

	final static int AXE_XPOS = 49;
	final static int AXE_YPOS = 60;
	
	final static int SHOVEL_XPOS = 49;
	final static int SHOVEL_YPOS = 60;
	
	final static int HOE_XPOS = 49;
	final static int HOE = 60;
	
	final static int FONT_Y_SPACING = 10;
	final static int PLAYER_INV_LABEL_XPOS = ContainerTF.PLAYER_INVENTORY_XPOS;
	final static int PLAYER_INV_LABEL_YPOS = ContainerTF.PLAYER_INVENTORY_YPOS - FONT_Y_SPACING;

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderHoveredTooltip(MatrixStack matrixStack, int mouseX, int mouseY)
	{
		if (!this.minecraft.player.inventory.getItemStack().isEmpty())
			return;

		List<ITextComponent> hoveringText = new ArrayList<ITextComponent>();

		if (!hoveringText.isEmpty())
		{
			func_243308_b(matrixStack, hoveringText, mouseX, mouseY); // renderToolTip
		} else
		{
			super.renderHoveredTooltip(matrixStack, mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y)
	{
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);

		int edgeSpacingX = (this.width - this.xSize) / 2;
		int edgeSpacingY = (this.height - this.ySize) / 2;
		this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY)
	{
		final int LABEL_XPOS = 5;
		final int LABEL_YPOS = 5;
		this.font.func_243248_b(matrixStack, this.title, LABEL_XPOS, LABEL_YPOS, Color.darkGray.getRGB());

		this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), PLAYER_INV_LABEL_XPOS,
				PLAYER_INV_LABEL_YPOS, Color.darkGray.getRGB());
	}

	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY)
	{
		return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
	}

	private static final ResourceLocation TEXTURE = new ResourceLocation("galaxia", "textures/tool_fuser.png");
}
