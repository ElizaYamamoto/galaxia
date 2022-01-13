package io.github.elizayami.galaxia.common.block.dragonfire_furnace;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContainerScreenDFF extends ContainerScreen<ContainerDFF>
{

	private ContainerDFF containerFurnace;

	public ContainerScreenDFF(ContainerDFF containerFurnace, PlayerInventory playerInventory, ITextComponent title)
	{
		super(containerFurnace, playerInventory, title);
		this.containerFurnace = containerFurnace;

		xSize = 176;
		ySize = 207;
	}
	final static int COOK_BAR_XPOS = 49;
	final static int COOK_BAR_YPOS = 60;
	final static int COOK_BAR_ICON_U = 0;
	final static int COOK_BAR_ICON_V = 207;
	final static int COOK_BAR_WIDTH = 80;
	final static int COOK_BAR_HEIGHT = 17;

	final static int FLAME_XPOS = 54;
	final static int FLAME_YPOS = 80;
	final static int FLAME_ICON_U = 176;
	final static int FLAME_ICON_V = 0;
	final static int FLAME_WIDTH = 14;
	final static int FLAME_HEIGHT = 14;
	final static int FLAME_X_SPACING = 18;

	final static int FONT_Y_SPACING = 10;
	final static int PLAYER_INV_LABEL_XPOS = ContainerDFF.PLAYER_INVENTORY_XPOS;
	final static int PLAYER_INV_LABEL_YPOS = ContainerDFF.PLAYER_INVENTORY_YPOS - FONT_Y_SPACING;

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

		if (isInRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_WIDTH, COOK_BAR_HEIGHT, mouseX, mouseY))
		{
			hoveringText.add(new StringTextComponent("Progress:"));
			int cookPercentage = (int) (containerFurnace.fractionOfCookTimeComplete() * 100);
			hoveringText.add(new StringTextComponent(cookPercentage + "%"));
		}

		for (int i = 0; i < containerFurnace.FUEL_SLOTS_COUNT; ++i)
		{
			if (isInRect(guiLeft + FLAME_XPOS + FLAME_X_SPACING * i, guiTop + FLAME_YPOS, FLAME_WIDTH, FLAME_HEIGHT,
					mouseX, mouseY))
			{
				hoveringText.add(new StringTextComponent("Fuel Time:"));
				hoveringText.add(new StringTextComponent(containerFurnace.secondsOfFuelRemaining(i) + "s"));
			}
		}

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

		double cookProgress = containerFurnace.fractionOfCookTimeComplete();
		this.blit(matrixStack, guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_ICON_U, COOK_BAR_ICON_V,
				(int) (cookProgress * COOK_BAR_WIDTH), COOK_BAR_HEIGHT);

		for (int i = 0; i < containerFurnace.FUEL_SLOTS_COUNT; ++i)
		{
			double burnRemaining = containerFurnace.fractionOfFuelRemaining(i);
			int yOffset = (int) ((1.0 - burnRemaining) * FLAME_HEIGHT);
			this.blit(matrixStack, guiLeft + FLAME_XPOS + FLAME_X_SPACING * i, guiTop + FLAME_YPOS + yOffset,
					FLAME_ICON_U, FLAME_ICON_V + yOffset, FLAME_WIDTH, FLAME_HEIGHT - yOffset);
		}
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

	private static final ResourceLocation TEXTURE = new ResourceLocation("galaxia", "textures/dragonfurnace.png");
}
