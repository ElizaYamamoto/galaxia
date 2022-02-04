package io.github.elizayami.galaxia.common.abstracts.tileentities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

import java.util.function.Predicate;

public class FuserZoneContents implements IInventory
{

	public static FuserZoneContents 
	
	createForTileEntity(int size, Predicate<PlayerEntity> canPlayerAccessInventoryLambda, Notify markDirtyNotificationLambda)
	{
		return new FuserZoneContents(size, canPlayerAccessInventoryLambda, markDirtyNotificationLambda);
	}

	public static FuserZoneContents createForClientSideContainer(int size)
	{
		return new FuserZoneContents(size);
	}

	public CompoundNBT serializeNBT()
	{
		return furnaceComponentContents.serializeNBT();
	}

	public void deserializeNBT(CompoundNBT nbt)
	{
		furnaceComponentContents.deserializeNBT(nbt);
	}

	public void setCanPlayerAccessInventoryLambda(Predicate<PlayerEntity> canPlayerAccessInventoryLambda)
	{
		this.canPlayerAccessInventoryLambda = canPlayerAccessInventoryLambda;
	}

	public void setMarkDirtyNotificationLambda(Notify markDirtyNotificationLambda)
	{
		this.markDirtyNotificationLambda = markDirtyNotificationLambda;
	}

	public void setOpenInventoryNotificationLambda(Notify openInventoryNotificationLambda)
	{
		this.openInventoryNotificationLambda = openInventoryNotificationLambda;
	}

	public void setCloseInventoryNotificationLambda(Notify closeInventoryNotificationLambda)
	{
		this.closeInventoryNotificationLambda = closeInventoryNotificationLambda;
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player)
	{
		return canPlayerAccessInventoryLambda.test(player);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return furnaceComponentContents.isItemValid(index, stack);
	}

	@FunctionalInterface
	public interface Notify
	{
		void invoke();
	}

	@Override
	public void markDirty()
	{
		markDirtyNotificationLambda.invoke();
	}

	@Override
	public void openInventory(PlayerEntity player)
	{
		openInventoryNotificationLambda.invoke();
	}

	@Override
	public void closeInventory(PlayerEntity player)
	{
		closeInventoryNotificationLambda.invoke();
	}

	@Override
	public int getSizeInventory()
	{
		return furnaceComponentContents.getSlots();
	}

	@Override
	public boolean isEmpty()
	{
		for (int i = 0; i < furnaceComponentContents.getSlots(); ++i)
		{
			if (!furnaceComponentContents.getStackInSlot(i).isEmpty())
				return false;
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return furnaceComponentContents.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (count < 0)
			throw new IllegalArgumentException("count should be >= 0:" + count);
		return furnaceComponentContents.extractItem(index, count, false);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		int maxPossibleItemStackSize = furnaceComponentContents.getSlotLimit(index);
		return furnaceComponentContents.extractItem(index, maxPossibleItemStackSize, false);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		furnaceComponentContents.setStackInSlot(index, stack);
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < furnaceComponentContents.getSlots(); ++i)
		{
			furnaceComponentContents.setStackInSlot(i, ItemStack.EMPTY);
		}
	}

	public ItemStack increaseStackSize(int index, ItemStack itemStackToInsert)
	{
		ItemStack leftoverItemStack = furnaceComponentContents.insertItem(index, itemStackToInsert, false);
		return leftoverItemStack;
	}

	public boolean doesItemStackFit(int index, ItemStack itemStackToInsert)
	{
		ItemStack leftoverItemStack = furnaceComponentContents.insertItem(index, itemStackToInsert, true);
		return leftoverItemStack.isEmpty();
	}

	private FuserZoneContents(int size)
	{
		this.furnaceComponentContents = new ItemStackHandler(size);
	}

	private FuserZoneContents(int size, Predicate<PlayerEntity> canPlayerAccessInventoryLambda,
			Notify markDirtyNotificationLambda)
	{
		this.furnaceComponentContents = new ItemStackHandler(size);
		this.canPlayerAccessInventoryLambda = canPlayerAccessInventoryLambda;
		this.markDirtyNotificationLambda = markDirtyNotificationLambda;
	}

	private Predicate<PlayerEntity> canPlayerAccessInventoryLambda = x -> true;

	private Notify markDirtyNotificationLambda = () ->
	{
	};

	private Notify openInventoryNotificationLambda = () ->
	{
	};

	private Notify closeInventoryNotificationLambda = () ->
	{
	};

	private final ItemStackHandler furnaceComponentContents;
}
