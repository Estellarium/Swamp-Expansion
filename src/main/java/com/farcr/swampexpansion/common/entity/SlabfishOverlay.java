package com.farcr.swampexpansion.common.entity;

import java.util.Arrays;
import java.util.Comparator;

import net.minecraft.util.IStringSerializable;

public enum SlabfishOverlay implements IStringSerializable {
	NONE(0, "none"),
	MUDDY(1, "mud"),
	SNOWY(2, "snow"),
	EGG(3, "egg");

	private static final SlabfishOverlay[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(SlabfishOverlay::getId)).toArray((array) -> {
		return new SlabfishOverlay[array];
	});
	private final int id;
	private final String name;

	private SlabfishOverlay(int idIn, String name) {
		this.id = idIn;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}
	public static SlabfishOverlay byId(int id) {
		if (id < 0 || id >= VALUES.length) {
			id = 0;
		}
		return VALUES[id];
	}

	public static SlabfishOverlay byTranslationKey(String key, SlabfishOverlay type) {
		for(SlabfishOverlay slabfishtype : values()) {
			if (slabfishtype.name.equals(key)) {
				return slabfishtype;
			}
		}
		return type;
	}

	public String getName() {
		return this.name;
	}
}